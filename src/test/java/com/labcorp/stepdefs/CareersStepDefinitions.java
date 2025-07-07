package com.labcorp.stepdefs;

import com.labcorp.pages.CareersPage;
import com.labcorp.utils.TestLogger;
import io.cucumber.java.en.*;
import org.junit.Assert;

import static com.labcorp.driver.DriverFactory.getDriver;

/**
 * Step Definitions for LabCorp Careers feature steps.
 * Each method maps to a Gherkin step and delegates interaction
 * to the CareersPage (Page Object Model).
 */
public class CareersStepDefinitions {

    private final CareersPage careersPage = new CareersPage(getDriver());

    @Given("the user is on the LabCorp home page")
    public void openHomePage() {
        careersPage.goToHomePage();
        System.out.println("✅ Navigated to LabCorp home page.");
        TestLogger.log("✅ Navigated to LabCorp home page.");
    }

    @When("the user navigates to the Careers page")
    public void navigateToCareers() {
        careersPage.navigateToCareers();
        System.out.println("✅ Navigated to Careers page.");
        TestLogger.log("✅ Navigated to Careers page.");
    }

    @And("the user searches for {string}")
    public void searchForJob(String title) {
        careersPage.searchForJob(title);
        System.out.println("✅ Searched for job: " + title);
        TestLogger.log("✅ Searched for job: " + title);
    }

    @And("the user clicks on the first job result")
    public void clickFirstJobResult() {
        careersPage.clickFirstJobResult();
        System.out.println("✅ Clicked on the first job result.");
        TestLogger.log("✅ Clicked on the first job result.");
    }

    @Then("the job title should match the result listing")
    public void validateJobTitleMatch() {
        Assert.assertEquals(
                "❌ Job title mismatch!",
                careersPage.getExpectedJobTitle(),
                careersPage.getLastJobTitle()
        );
        System.out.println("✅ Job title matches the result listing.");
        TestLogger.log("✅ Job title matches the result listing.");
    }

    @And("the job location should match the result listing")
    public void validateJobLocationMatch() {
        Assert.assertEquals(
                "❌ Job location mismatch!",
                careersPage.getExpectedJobLocation(),
                careersPage.getLastJobLocation()
        );
        System.out.println("✅ Job location matches the result listing.");
        TestLogger.log("✅ Job location matches the result listing.");
    }

    @And("the job ID should match the result listing")
    public void validateJobIdMatch() {
        Assert.assertEquals(
                "❌ Job ID mismatch!",
                careersPage.getExpectedJobId(),
                careersPage.getLastJobId()
        );
        System.out.println("✅ Job ID matches the result listing.");
        TestLogger.log("✅ Job ID matches the result listing.");
    }

    @Then("the job description third paragraph first sentence should be:")
    public void validateThirdParagraphSentence(String expectedSentence) {
        String actual = careersPage.getThirdParagraphFirstSentence();
        Assert.assertEquals(
                "❌ Third paragraph sentence mismatch!",
                expectedSentence.trim(),
                actual.trim()
        );
        System.out.println("✅ Third paragraph sentence validated.");
        TestLogger.log("✅ Third paragraph sentence validated.");
    }

    @Then("the job description second bullet under {string} should be:")
    public void validateSecondBulletUnderHeader(String header, String expectedBullet) {
        String actual = careersPage.getSecondBulletUnderHeader(header);
        Assert.assertNotNull("❌ Section '" + header + "' not found!", actual);
        Assert.assertEquals(
                "❌ Bullet under '" + header + "' did not match!",
                expectedBullet.trim(),
                actual.trim()
        );
        System.out.println("✅ Bullet under '" + header + "' matched.");
        TestLogger.log("✅ Bullet under '" + header + "' matched.");
    }

    @Then("the job description second bullet under {string} should contain {string}")
    public void validateSecondBulletContainsKeyword(String header, String keyword) {
        String actual = careersPage.getSecondBulletUnderHeader(header);
        Assert.assertNotNull("❌ Section '" + header + "' not found!", actual);
        Assert.assertTrue(
                "❌ Bullet under '" + header + "' does not contain keyword '" + keyword + "'!",
                actual.toLowerCase().contains(keyword.toLowerCase())
        );
        System.out.println("✅ Bullet under '" + header + "' contains keyword '" + keyword + "'.");
        TestLogger.log("✅ Bullet under '" + header + "' contains keyword '" + keyword + "'.");
    }

    @And("the user clicks on Apply Now button")
    public void clickApplyNowButton() {
        careersPage.clickApplyNow();
        System.out.println("✅ Clicked on Apply Now.");
        TestLogger.log("✅ Clicked on Apply Now.");
    }

    @Then("the user is redirected back to the Careers page")
    public void returnToCareersPage() {
        careersPage.returnToCareersPage();
        System.out.println("✅ Returned to Careers page.");
        TestLogger.log("✅ Returned to Careers page.");
    }
}
