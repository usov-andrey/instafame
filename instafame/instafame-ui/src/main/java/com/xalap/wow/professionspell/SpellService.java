package com.xalap.wow.professionspell;

import com.xalap.framework.data.CrudService;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.framework.utils.HttpClientUtils;
import com.xalap.framework.utils.StringHelper;
import com.xalap.wow.api.auction.Auction;
import com.xalap.wow.auction.AuctionDataOld;
import com.xalap.wow.auction.AuctionService;
import com.xalap.wow.craft.Profession;
import com.xalap.wow.item.ItemBean;
import com.xalap.wow.item.ItemService;
import com.xalap.wow.professionspell.reagent.ReceiptReagentBean;
import com.xalap.wow.professionspell.reagent.ReceiptReagentService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Вобщем по каждому spell нужно парсить, что за результат этого спела
 * https://www.wowdb.com/spells/298016-accord-of-haste
 *
 * @author Усов Андрей
 * @since 28.06.2018
 */
@Service
public class SpellService extends CrudService<SpellBean, SpellRepository, Integer> {

    @Autowired
    private ReceiptReagentService receiptReagentService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private AuctionService auctionService;

    public void readReceipts() {
        for (Profession profession : Profession.values()) {
            readReceipts(profession);
        }
    }

    public void readReceipts(Profession profession) {
        String url = "https://www.wowdb.com/spells/professions/" + profession.name();
        readReceipts(url, profession);
    }


    private void readReceipts(String url, Profession profession) {
        String html = getHtml(url);
        Document doc = Jsoup.parse(html);
        Elements table = doc.select("#spells");
        Elements trList = table.select("tr");
        for (Element element : trList) {
            Elements tdList = element.select("td");

            if (!tdList.isEmpty() &&
                    !element.attr("class").isEmpty()) {//Из-за того, что там таблица внутри таблицы
                Element td = tdList.get(1);
                //Количество производимое
                String count = td.text();
                Integer itemCount = count.isEmpty() ? 1 : Integer.parseInt(count);
                //Ссылка на проивзодимый элемент или скилл
                Elements aList = td.select("a");
                Element receiptA = aList.get(0);
                String receiptUrl = receiptA.attr("href");
                td = tdList.get(2);
                Elements span = td.select("span");
                String rank = "";
                if (span != null) {
                    rank = span.text().trim().replace("Rank ", "");
                }
                SpellData spellData = new SpellData();
                if (receiptUrl.contains("/items/")) {
                    //Значит производится вещь и мы по этой строчке можем считать полностью информацию о крафте
                    spellData.setItem(item(receiptA));
                } else if (receiptUrl.contains("/spells/")) {
                    //Значит нужно провалиться внутрь и считать информацию о вещи, которую можно сделать с помощью этого спела
                    readItemFromSpell(spellData, receiptUrl);
                } else {
                    throw new IllegalStateException("unknown");
                }
                if (spellData.getItem() == null) {
                    continue;
                }
                Elements skillDivs = tdList.get(5).select("div");
                String skill = skillDivs.get(0).text();
                String skillReq = skillDivs.size() > 1 ? skillDivs.get(1).select("span").get(0).text() : "";
                Integer skillRequirement = StringHelper.isNotEmpty(skillReq) ? Integer.parseInt(skillReq) : null;

                boolean byTrainer = "Trainer".equals(tdList.get(4).text());

                SpellBean receiptBean = getOrCreateReceipt(spellData, itemCount, rank, skill, skillRequirement, profession, byTrainer);
                for (Element a : tdList.get(3).select("a")) {
                    int reagentCount = Integer.parseInt(a.text());
                    receiptReagentService.getOrCreateReagent(receiptBean, item(a), reagentCount);

                }
                //System.out.println("Url:" + receiptUrl);
            }
        }
        //Обрабатываем следующие страницы
        String pageLoc = "page=";
        if (!url.contains(pageLoc)) {
            int page = 1;
            for (Element a : doc.select("div.b-pagination").select("a")) {
                String pageUrl = a.attr("href");
                int curPage = Integer.parseInt(StringHelper.getStringAfter(pageUrl, pageLoc));
                if (curPage > page) {
                    page = curPage;
                }
            }
            for (int i = 2; i <= page; i++) {
                readReceipts(url + "?" + pageLoc + i, profession);
            }
        }
    }

    private String getHtml(String url) {
        try {
            return HttpClientUtils.get(url);
        } catch (IOException e) {
            throw new IllegalStateException("Error on get url:" + url);
        }
    }

    private void readItemFromSpell(SpellData spellData, String url) {
        String html = getHtml(url);
        Document doc = Jsoup.parse(html);
        Elements trList = doc.select("table.info-table").select("tr");
        for (Element element : trList) {
            String caption = element.select("td").get(0).text();
            if (caption.startsWith("Effect")) {
                Elements a = element.select("td").get(1).select("a");
                if (!a.isEmpty()) {
                    spellData.setItem(item(a.get(0)));
                    //Смотрим может этот скилл учится через рецепты
                    for (Element div : doc.select("div.tab-taught-by-item")) {
                        for (Element a1 : div.select("a")) {
                            if (!a1.classNames().contains("listing-icon")) {
                                ItemBean receipt = item(a1);
                                spellData.getRecipes().add(receipt);
                            }
                        }

                    }
                }
            }
        }
    }

    private SpellBean getOrCreateReceipt(SpellData spellData, int quantity, String rank, String skill, Integer skillReq,
                                         Profession profession, boolean byTrainer) {
        ItemBean itemBean = spellData.getItem();
        SpellBean bean = repository().findByItemAndRank(itemBean, rank);
        if (bean == null) {
            bean = new SpellBean();
        }
        bean.setItem(itemBean);
        bean.setQuantity(quantity);
        bean.setRank(rank);
        if (skillReq != null) {
            skill = StringHelper.replace(skill, "(" + skillReq + ")", "").trim();
        }
        bean.setSkill(skill);
        bean.setSkillRequirement(skillReq);
        bean.setProfession(profession);
        bean.setByTrainer(byTrainer);
        bean = save(bean);
        for (ItemBean receipt : spellData.getRecipes()) {
            receipt.setCreates(bean);
            itemService.save(receipt);
        }

        return bean;
    }

    private ItemBean item(Element a) {
        String url = a.attr("href");
        long id = Long.parseLong(StringHelper.getStringBetween(url, "/items/", "-"));
        return itemService.getItem(id);
    }

    public List<SpellBean> getReceiptList(Profession profession) {
        return repository().findByProfession(profession);
    }

    public Map<SpellBean, List<ReceiptReagentBean>> getAllReceiptMap() {
        return getReceiptMap(receiptReagentService.repository().findAll());
    }

    public Map<ItemBean, List<ReceiptReagentBean>> getAllItemReagentMap() {
        return getItemReagentsMap(receiptReagentService.repository().findAll());
    }

    public Map<SpellBean, List<ReceiptReagentBean>> getReceiptMap(Profession profession) {
        return getReceiptMap(receiptReagentService.repository().findByProfession(profession));
    }

    private Map<SpellBean, List<ReceiptReagentBean>> getReceiptMap(List<ReceiptReagentBean> reagentBeanList) {
        Map<SpellBean, List<ReceiptReagentBean>> result = new HashMap<>();
        for (ReceiptReagentBean receiptReagentBean : reagentBeanList) {
            CollectionHelper.getArrayListOrCreate(result, receiptReagentBean.getReceipt()).add(receiptReagentBean);
        }
        return result;
    }

    private Map<ItemBean, List<ReceiptReagentBean>> getItemReagentsMap(List<ReceiptReagentBean> reagentBeanList) {
        Map<ItemBean, List<ReceiptReagentBean>> result = new HashMap<>();
        for (ReceiptReagentBean receiptReagentBean : reagentBeanList) {
            SpellBean receipt = receiptReagentBean.getReceipt();

            if (receipt.isRank1Or2()) {
                continue;
            }

            CollectionHelper.getArrayListOrCreate(result, receipt.getItem()).add(receiptReagentBean);
        }
        return result;
    }

    /**
     * return Стоимость крафта
     */
    public long getCraftCost(AuctionDataOld auctionData, List<ReceiptReagentBean> reagentBeanList) {
        List<Auction> reagentsAuctions = auctionService.getReagentsAuctions(auctionData, reagentBeanList);
        return getCraftCost(auctionData, reagentBeanList, reagentsAuctions);
    }

    public long getCraftCost(AuctionDataOld auctionData, List<ReceiptReagentBean> reagentBeanList, List<Auction> reagentsAuctions) {
        long price = 0;
        for (ReceiptReagentBean receiptReagentBean : reagentBeanList) {
            long reagentPrice = receiptReagentService.getPrice(auctionData, receiptReagentBean, reagentsAuctions);
            price += reagentPrice;
        }
        return price;
    }


}
