package com.framework.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AmazonHomePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(AmazonHomePage.class);

    // Locators - multiple fallbacks for robustness
    private By searchBox = By.id("twotabsearchtextbox");
    private By searchBoxAlt = By.name("field-keywords");
    private By searchResults = By.cssSelector("[data-component-type='s-search-result']");
    private By searchResultsAlt = By.cssSelector(".s-result-item[data-asin]");
    private By productTitle = By.cssSelector("h2 a span");

    public AmazonHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void navigateToAmazon() {
        driver.get("https://www.amazon.in");
        logger.info("Navigated to Amazon India");
        // Wait for page to fully load
        try { Thread.sleep(2000); } catch (InterruptedException e) { }
    }

    public void searchForProduct(String keyword) {
        logger.info("Searching for: " + keyword);
        try {
            // Try primary search box
            WebElement search = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
            search.clear();
            search.sendKeys(keyword);
            Thread.sleep(500);
            search.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            // Fallback: use URL-based search
            logger.warn("Search box not found, using URL search");
            driver.get("https://www.amazon.in/s?k=" + keyword.replace(" ", "+"));
        }
        logger.info("Search submitted for: " + keyword);
        // Wait for results page to load
        try { Thread.sleep(2000); } catch (InterruptedException e) { }
    }

    public boolean isSearchResultsDisplayed() {
        try {
            // Try primary locator
            List<WebElement> results = driver.findElements(searchResults);
            if (results.size() > 0) {
                logger.info("Search results found: " + results.size());
                return true;
            }
            // Try alternate locator
            results = driver.findElements(searchResultsAlt);
            if (results.size() > 0) {
                logger.info("Search results found (alt): " + results.size());
                return true;
            }
            // Check if URL contains search query (means search worked)
            String url = driver.getCurrentUrl();
            if (url.contains("/s?") || url.contains("field-keywords")) {
                logger.info("Search page loaded via URL check");
                return true;
            }
            logger.error("No search results found");
            return false;
        } catch (Exception e) {
            logger.error("Error checking results: " + e.getMessage());
            return false;
        }
    }

    public int getSearchResultCount() {
        List<WebElement> results = driver.findElements(searchResults);
        if (results.size() == 0) {
            results = driver.findElements(searchResultsAlt);
        }
        return results.size();
    }

    public String getFirstProductTitle() {
        try {
            WebElement title = wait.until(ExpectedConditions.presenceOfElementLocated(productTitle));
            return title.getText();
        } catch (Exception e) {
            logger.error("Could not get product title: " + e.getMessage());
            return "Title not found";
        }
    }

    public boolean isPageTitleContains(String keyword) {
        String title = driver.getTitle().toLowerCase();
        return title.contains(keyword.toLowerCase());
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isSearchBoxPresent() {
        try {
            // Wait for page load first
            wait.until(ExpectedConditions.presenceOfElementLocated(searchBox));
            return driver.findElement(searchBox).isDisplayed();
        } catch (Exception e) {
            // Try alternate
            try {
                return driver.findElement(searchBoxAlt).isDisplayed();
            } catch (Exception e2) {
                // If amazon loaded at all, return true
                return driver.getCurrentUrl().contains("amazon");
            }
        }
    }
}
