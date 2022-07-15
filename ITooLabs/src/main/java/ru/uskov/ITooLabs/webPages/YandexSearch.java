package ru.uskov.ITooLabs.webPages;

import ru.uskov.ITooLabs.configuration.DriverSettings;

import java.util.logging.Logger;

public class YandexSearch {

    private Logger LOGGER = Logger.getLogger(YandexSearch.class.getName());

    private final String MARKET_LINK_XPATH = ".//*[local-name()='a' and @data-id='market']";

    public String getMarketLinkXPath() {
        return MARKET_LINK_XPATH;
    }
}
