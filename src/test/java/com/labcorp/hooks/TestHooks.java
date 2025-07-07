package com.labcorp.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import com.labcorp.driver.DriverFactory;
import com.labcorp.utils.TestLogger;

/**
 * Cucumber Test Hooks for setting up and tearing down WebDriver before/after each scenario.
 */
public class TestHooks {

    /**
     * This method runs before each scenario.
     * Initializes the WebDriver and logs setup activity.
     */
    @Before
    public void setUp(Scenario scenario) {
        DriverFactory.initializeDriver();
        WebDriver driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        TestLogger.logScenarioStart(scenario.getName());
    }

    /**
     * This method runs after each scenario.
     * Quits the WebDriver and logs teardown activity.
     */
    @After
    public void tearDown(Scenario scenario) {
        TestLogger.logScenarioEnd(scenario.getName(), scenario.getStatus().name());
        DriverFactory.quitDriver();
    }
}
