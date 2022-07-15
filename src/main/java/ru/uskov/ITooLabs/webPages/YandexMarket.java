package ru.uskov.ITooLabs.webPages;

import java.util.logging.Logger;

public class YandexMarket {

    private Logger LOGGER = Logger.getLogger(YandexMarket.class.getName());

    private final String MARKET_SEARCH_ID = "header-search";

    public String getMarketSearchId() {
        return MARKET_SEARCH_ID;
    }
}
