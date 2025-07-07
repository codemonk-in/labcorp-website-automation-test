package com.labcorp.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * DriverFactory is responsible for initializing and managing WebDriver instances.
 * Uses ThreadLocal to ensure thread safety in parallel executions.
 */
public class DriverFactory {

    // ThreadLocal for parallel test execution support
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Initializes a ChromeDriver instance if not already present for the current thread.
     */
    public static void initializeDriver() {
        if (driver.get() == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            // options.addArguments("--headless"); // Enable this for headless runs
            driver.set(new ChromeDriver(options));
        }
    }

    /**
     * Returns the WebDriver instance for the current thread.
     * @return WebDriver
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Quits and removes the WebDriver instance for the current thread.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
