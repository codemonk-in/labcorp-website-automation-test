package com.labcorp.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * TestRunner for executing LabCorp feature files using Cucumber.
 * Specifies feature file location, step definitions, and output report plugins.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",   // Feature file directory
        glue = {"com.labcorp.stepdefs", "com.labcorp.hooks"}, // Step defs & hooks
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json"
        },
        monochrome = true // Improves readability in console
)
public class TestRunner {
        // No additional logic required here
}
