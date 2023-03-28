package com.xalap.wow.professionspell;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.valueprovider.IntegerValueProvider;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.wow.auction.AuctionDataOld;
import com.xalap.wow.auction.AuctionService;
import com.xalap.wow.craft.Profession;
import com.xalap.wow.craft.ProfessionFilter;
import com.xalap.wow.professionspell.reagent.ReagentListComponent;
import com.xalap.wow.professionspell.reagent.ReceiptReagentBean;
import com.xalap.wow.professionspell.reagent.ReceiptReagentService;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Усов Андрей
 * @since 14/08/2019
 */
@Route(value = SpellListFrame.VIEW_NAME, layout = MainLayout.class)
public class SpellListFrame extends RootEntityListFrame<SpellBean> {
    public static final String VIEW_NAME = "receiptList";

    @Autowired
    public SpellListFrame(ServiceRef<SpellService> service, ServiceRef<AuctionService> auctionServiceRef,
                          ServiceRef<ReceiptReagentService> receiptReagentService) {
        super(service);

        for (Profession profession : Profession.values()) {
            addTab(profession.name());
        }

        gridPanel.filters().addText("Поиск", (bean, s) -> s.isEmpty() ||
                bean.getItem().getName().toLowerCase().contains(s.toLowerCase()));

        ProfessionFilter<SpellBean> professionFilter = new ProfessionFilter<>(gridPanel);
        Map<SpellBean, List<ReceiptReagentBean>> receiptBeanListMap = new HashMap<>();

        setMemoryDataProvider(() -> {
            Profession profession = getProfession();
            SpellService spellService = service.get();
            List<SpellBean> receipts = spellService.getReceiptList(profession);
            professionFilter.setFilterValue(profession, receipts, bean -> bean);
            receiptBeanListMap.clear();
            receiptBeanListMap.putAll(spellService.getReceiptMap(profession));
            return receipts;
        });

        GridColumns<SpellBean> columns = gridPanel.columns();
        columns.addLink("Название", SpellBean::name, SpellBean::getId, SpellFrame.class);
        columns.footer(columns.addColumn("Профессия", bean -> bean.getProfession().name()),
                receiptBeen -> Integer.toString(receiptBeen.size()));
        columns.addColumn("Скилл", SpellBean::getSkill).setSortable(true);

        columns.addColumn("Требование", new IntegerValueProvider<>(SpellBean::getSkillRequirement)).setSortable(true);
        AuctionService auctionService = auctionServiceRef.get();
        AuctionDataOld auction = auctionService.getAuction();

        columns.addComponent("Реагенты", bean -> {
            List<ReceiptReagentBean> receiptReagentBeen = receiptBeanListMap.get(bean);
            if (receiptReagentBeen == null) {
                return new Label("Нет реагентов");
            }

            return new ReagentListComponent(auction, auctionService, receiptReagentService.get(),
                    receiptReagentBeen);
        });

        gridPanel.buttons()
                .addWithReload("Загрузить рецепты", () -> service.get().readReceipts(getProfession()));
    }

    private Profession getProfession() {
        return Profession.valueOf(getSelectedTab().getLabel());
    }

}