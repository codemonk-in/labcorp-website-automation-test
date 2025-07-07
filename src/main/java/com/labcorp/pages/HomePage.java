package com.labcorp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents LabCorp's Home Page.
 * Provides access to elements like the "Careers" link and page title.
 */
public class HomePage {

    private WebDriver driver;

    // Locator for the "Careers" navigation link
    private By careersLink = By.linkText("Careers");

    /**
     * Constructor initializes WebDriver for the page.
     * @param driver WebDriver instance.
     */
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Clicks the "Careers" link on the homepage.
     */
    public void clickCareers() {
        driver.findElement(careersLink).click();
    }

    /**
     * Gets the current page title.
     * @return The title of the current web page.
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    // Extend with other actions if needed
}
