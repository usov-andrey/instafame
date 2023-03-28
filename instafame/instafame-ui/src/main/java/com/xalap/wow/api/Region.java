package com.xalap.wow.api;

/**
 * @author Усов Андрей
 * @since 10/08/2019
 * <p>
 * TBC:
 * {
 * "key": {
 * "href": "https://eu.api.blizzard.com/data/wow/connected-realm/4474?namespace=dynamic-classic-eu"
 * },
 * "data": {
 * "realms": [
 * {
 * "is_tournament": false,
 * "timezone": "Europe/Paris",
 * "name": {
 * "ru_RU": "Пламегор",
 * "en_GB": "Flamegor",
 * "zh_TW": "弗萊格爾",
 * "ko_KR": "Flamegor",
 * "en_US": "Flamegor",
 * "es_MX": "Flamegor",
 * "pt_BR": "Flamegor",
 * "es_ES": "Flamegor",
 * "zh_CN": "弗莱格尔",
 * "fr_FR": "Flamegor",
 * "de_DE": "Пламегор"
 * },
 * "id": 4474,
 * "region": {
 * "name": {
 * "ru_RU": "Classic (Европа)",
 * "en_GB": "Classic Europe",
 * "zh_TW": "經典歐洲",
 * "ko_KR": "클래식 유럽",
 * "en_US": "Classic Europe",
 * "es_MX": "Classic Europa",
 * "pt_BR": "Classic - Europa",
 * "es_ES": "Classic Europa",
 * "zh_CN": "经典怀旧（欧洲）",
 * "fr_FR": "Classic Europe",
 * "de_DE": "Classic - Europa"
 * },
 * "id": 43
 * },
 * "category": {
 * "ru_RU": "Русский",
 * "en_GB": "Russian",
 * "zh_TW": "俄羅斯",
 * "ko_KR": "러시아어",
 * "en_US": "Russian",
 * "es_MX": "Ruso",
 * "pt_BR": "Russo",
 * "es_ES": "Ruso",
 * "zh_CN": "俄语",
 * "fr_FR": "Russe",
 * "de_DE": "Russisch"
 * },
 * "locale": "ruRU",
 * "type": {
 * "name": {
 * "ru_RU": "PvP",
 * "en_GB": "PvP",
 * "zh_TW": "PvP",
 * "ko_KR": "전쟁",
 * "en_US": "PvP",
 * "es_MX": "JcJ",
 * "pt_BR": "JxJ",
 * "es_ES": "JcJ",
 * "zh_CN": "PvP",
 * "fr_FR": "JcJ",
 * "de_DE": "PvP"
 * },
 * "type": "PVP"
 * },
 * "slug": "flamegor"
 * }
 * ],
 * "id": 4474,
 * "has_queue": false,
 * "status": {
 * "name": {
 * "ru_RU": "Работает",
 * "en_GB": "Up",
 * "zh_TW": "正常",
 * "ko_KR": "정상",
 * "en_US": "Up",
 * "es_MX": "Disponible",
 * "pt_BR": "Para Cima",
 * "es_ES": "Activo",
 * "zh_CN": "正常",
 * "fr_FR": "En ligne",
 * "de_DE": "Verfügbar"
 * },
 * "type": "UP"
 * },
 * "population": {
 * "name": {
 * "ru_RU": "Высокая",
 * "en_GB": "High",
 * "zh_TW": "高",
 * "ko_KR": "혼잡",
 * "en_US": "High",
 * "es_MX": "Alto",
 * "pt_BR": "Alto",
 * "es_ES": "Alto",
 * "zh_CN": "高",
 * "fr_FR": "Élevée",
 * "de_DE": "Hoch"
 * },
 * "type": "HIGH"
 * }
 * }
 * }
 */
public enum Region {

    flamegor(4474, 6)
    /*
    Аукционы
    https://eu.api.blizzard.com/data/wow/connected-realm/4474/auctions/index?namespace=dynamic-classic-eu&locale=en_US&access_token=USDIRiABHqhyz72OqfoW4y4Pb6Y18VLJYw
    {
  "_links": {
    "self": {
      "href": "https://eu.api.blizzard.com/data/wow/connected-realm/4474/auctions/?namespace=dynamic-classic-eu"
    }
  },
  "auctions": [
    {
      "key": {
        "href": "https://eu.api.blizzard.com/data/wow/connected-realm/4474/auctions/2?namespace=dynamic-classic-eu"
      },
      "name": "Alliance Auction House",
      "id": 2
    },
    {
      "key": {
        "href": "https://eu.api.blizzard.com/data/wow/connected-realm/4474/auctions/6?namespace=dynamic-classic-eu"
      },
      "name": "Horde Auction House",
      "id": 6
    },
    {
      "key": {
        "href": "https://eu.api.blizzard.com/data/wow/connected-realm/4474/auctions/7?namespace=dynamic-classic-eu"
      },
      "name": "Blackwater Auction House",
      "id": 7
    }
  ]
}
     */;

    private final int realmId;
    private final int auctionId;

    Region(int realmId, int auctionId) {
        this.realmId = realmId;
        this.auctionId = auctionId;
    }

    public int getRealmId() {
        return realmId;
    }

    public int getAuctionId() {
        return auctionId;
    }
}
