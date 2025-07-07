package com.labcorp.base;

import com.labcorp.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

/**
 * BaseTest is an abstract class for shared test setup and teardown logic.
 * Logs scenario names and statuses and manages WebDriver lifecycle.
 */
public class BaseTest {

    protected WebDriver driver;

    /**
     * Runs before each scenario, initializes WebDriver.
     * @param scenario Cucumber scenario object.
     */
    @Before
    public void setUp(Scenario scenario) {
        System.out.println(">>> Starting Scenario: " + scenario.getName());
        DriverFactory.initializeDriver();
        this.driver = DriverFactory.getDriver();
    }

    /**
     * Runs after each scenario, quits WebDriver.
     * @param scenario Cucumber scenario object.
     */
    @After
    public void tearDown(Scenario scenario) {
        System.out.println("<<< Finished Scenario: " + scenario.getName() + " | Status: " + scenario.getStatus());
        DriverFactory.quitDriver();
    }
}
