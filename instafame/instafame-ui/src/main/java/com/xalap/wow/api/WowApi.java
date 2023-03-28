package com.xalap.wow.api;

import com.xalap.framework.json.JsonService;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.HttpClientUtils;
import com.xalap.framework.utils.StringHelper;
import com.xalap.wow.api.auction.AuctionsFilesResponse;
import com.xalap.wow.api.classic.auction.ClassicAuctionHouse;
import com.xalap.wow.api.classic.item.ClassicItem;
import com.xalap.wow.api.item.ItemResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 10/08/2019
 */
@Service
public class WowApi {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String AUCTION_URL = "https://eu.api.blizzard.com/data/wow/connected-realm/%s/auctions/%s?namespace=dynamic-classic-eu&locale=en_US&";
    private final String ITEM_URL = "https://eu.api.blizzard.com/data/wow/item/%s?namespace=static-classic-eu&locale=ru_RU";

    private String accessToken;
    private Date accessTokenRefreshTime;
    private final JsonService jsonService;

    public WowApi(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    public AuctionsFilesResponse auction(Region realm) {
        Request auth = auth(Request.Get(String.format(AUCTION_URL, realm.getRealmId(), realm.getAuctionId())));
        String response = HttpClientUtils.execDefaultStringResponse(auth);
        return jsonService.getJson(response, AuctionsFilesResponse.class);
    }

    /**
     * Возвращает информайцию об аукционе
     * Аукционы по какой-то причине могут дублироваться
     */
    public ClassicAuctionHouse classicAuction(Region realm) {
        Request request = auth(Request.Get(String.format(AUCTION_URL, realm.getRealmId(), realm.getAuctionId())));
        Response response1 = HttpClientUtils.exec(request, 150);
        String response = HttpClientUtils.stringResponse(response1);
        return jsonService.getJson(response, ClassicAuctionHouse.class);
    }

    /**
     * Возвращаем null, в случае если предмет не найден или был удален из игры
     */
    public ItemResponse getItem(long itemId) {
        Request request = auth(Request.Get(String.format(ITEM_URL, itemId)));
        try {
            Response response = HttpClientUtils.execDefault(request);
            HttpResponse httpResponse = response.returnResponse();
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String responseString = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            if (statusCode == HttpStatus.SC_NOT_FOUND) {
                log.warn("Not found item by id:" + itemId + " response:" + responseString);
                return null;
            }
            if (statusCode != HttpStatus.SC_OK) {
                throw new IllegalStateException("Error on get item by id:" + itemId + " response:" + responseString);
            }
            return jsonService.getJson(responseString, ItemResponse.class);
        } catch (IOException e) {
            throw new IllegalStateException("Error on get item by id:" + itemId + " request:" + request);
        }
    }

    private Request auth(Request http) {
        http.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());
        http.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        return http;
    }

    public String getAccessToken() {
        if (accessTokenRefreshTime == null || new Date().after(accessTokenRefreshTime)) {
            //curl -u d834f5cc04254f3886577baa771fcd21:x3mnxjQOyymxVhCvRFHcQsBkVgRAzaSG -d grant_type=client_credentials https://us.battle.net/oauth/token
            Request httpPost = Request.Post("https://us.battle.net/oauth/token");

            String auth = "d834f5cc04254f3886577baa771fcd21:x3mnxjQOyymxVhCvRFHcQsBkVgRAzaSG";
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
            httpPost.bodyForm(new BasicNameValuePair("grant_type", "client_credentials"));


            String html = HttpClientUtils.execDefaultStringResponse(httpPost);
            accessToken = StringHelper.getStringBetween(html, "access_token\":\"", "\"");
            accessTokenRefreshTime = DateHelper.incDays(new Date(), 1);
        }
        return accessToken;
    }

    public ClassicItem getClassicItem(long itemId) {
        Request request = auth(Request.Get(String.format(ITEM_URL, itemId)));
        try {
            Response response = HttpClientUtils.execDefault(request);
            HttpResponse httpResponse = response.returnResponse();
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String responseString = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            if (statusCode == HttpStatus.SC_NOT_FOUND) {
                log.warn("Not found item by id:" + itemId + " response:" + responseString);
                return null;
            }
            if (statusCode != HttpStatus.SC_OK) {
                throw new IllegalStateException("Error on get item by id:" + itemId + " response:" + responseString);
            }
            log.debug("Item:" + responseString);
            return jsonService.getJson(responseString, ClassicItem.class);
        } catch (IOException e) {
            throw new IllegalStateException("Error on get item by id:" + itemId + " request:" + request);
        }
    }
}
