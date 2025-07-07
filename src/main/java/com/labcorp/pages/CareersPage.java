package com.labcorp.pages;

import java.time.Duration;
import java.util.*;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Page Object Model representing LabCorp Careers Page.
 * This class encapsulates all logic related to:
 * - Navigating the careers portal
 * - Searching jobs
 * - Extracting job detail metadata
 * - Parsing job description content
 */
public class CareersPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Stored expected values from the listing result card
    private String expectedJobTitle;
    private String expectedJobLocation;
    private String expectedJobId;

    // Actual values extracted from job detail JSON
    private String lastJobTitle;
    private String lastJobLocation;
    private String lastJobId;

    // Parsed third paragraph sentence in job description
    private String thirdParagraphFirstSentence;

    // Parsed job description document (from embedded JSON)
    private Document jobDescriptionDoc;

    // Map to hold second bullets grouped by their preceding header
    private final Map<String, String> secondBulletsByHeader = new HashMap<>();

    // Saved Careers page URL to navigate back
    private String careersPageUrl;

    // Locators
    private final By searchInputBox = By.cssSelector("input[placeholder='Search job title or location']");
    private final By searchButton = By.cssSelector("button[aria-label='Search']");

    /**
     * Constructor to initialize driver and explicit wait.
     */
    public CareersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigates to LabCorp home page and maximizes the browser window.
     */
    public void goToHomePage() {
        driver.get("https://www.labcorp.com");
        driver.manage().window().maximize();
    }

    /**
     * Navigates to the Careers page by clicking the "Careers" link.
     * Stores the current page URL for future navigation.
     */
    public void navigateToCareers() {
        WebElement careersLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Careers"))
        );
        careersLink.click();
        careersPageUrl = driver.getCurrentUrl();
    }

    /**
     * Navigates back to the saved Careers page URL.
     */
    public void returnToCareersPage() {
        if (careersPageUrl != null) {
            driver.get(careersPageUrl);
        }
    }

    /**
     * Searches for a job using the given job title.
     *
     * @param jobTitle the job title to search for
     */
    public void searchForJob(String jobTitle) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputBox))
                .sendKeys(jobTitle);
        driver.findElement(searchButton).click();
    }

    /**
     * Clicks on the first job result on the careers listing page,
     * extracts job metadata, and parses the job description from the embedded JSON.
     */
    public void clickFirstJobResult() {
        String selector = "span[data-ph-id='ph-page-element-page11-CRdnpK'] a.au-target:first-of-type";
        WebElement link = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector))
        );

        // Capture listing metadata
        expectedJobTitle = link.getAttribute("data-ph-at-job-title-text");
        expectedJobLocation = link.getAttribute("data-ph-at-job-location-text");
        expectedJobId = link.getAttribute("data-ph-at-job-id-text");

        String href = link.getAttribute("href");
        if (href == null || href.isEmpty()) {
            throw new RuntimeException("Job href was empty");
        }

        // Navigate to job detail page
        driver.get(href);

        // Wait for the embedded job JSON to be available
        wait.until(webDriver -> {
            String script = (String) ((JavascriptExecutor) webDriver).executeScript(
                    "return document.evaluate(\"/html/head/script[2]/text()\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue?.nodeValue;"
            );
            return script != null && script.trim().startsWith("{\"identifier\"");
        });

        // Extract the embedded job metadata JSON
        String scriptContent = (String) ((JavascriptExecutor) driver).executeScript(
                "return document.evaluate(\"/html/head/script[2]/text()\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.nodeValue;"
        );
        JSONObject jobData = new JSONObject(scriptContent);

        // Parse actual job metadata
        lastJobTitle = jobData.getString("title");
        lastJobId = jobData.getJSONObject("identifier").getString("value");

        var address = jobData.getJSONObject("jobLocation").getJSONObject("address");
        lastJobLocation = String.join(", ",
                address.optString("addressLocality", "").trim(),
                address.optString("addressRegion", "").trim(),
                address.optString("addressCountry", "").trim()
        ).replaceAll(", ,", ",");

        // Parse job description HTML
        String descriptionHtml = jobData.getString("description");
        parseDescriptionContent(descriptionHtml);
    }

    /**
     * Finds and clicks the "Apply Now" anchor or navigates directly using href.
     */
    public void clickApplyNow() {
        try {
            System.out.println("🔄 Looking for Apply Now link via CSS selector...");
            By applyNowLinkSelector = By.cssSelector("a.btn.primary-button.au-target");

            WebElement applyLink = wait.until(ExpectedConditions.presenceOfElementLocated(applyNowLinkSelector));
            String href = applyLink.getAttribute("href");

            if (href != null && !href.isEmpty()) {
                System.out.println("✅ Navigating to Apply Now URL: " + href);
                driver.get(href);
            } else {
                System.err.println("❌ Apply Now href is missing or empty.");
            }
        } catch (TimeoutException te) {
            System.err.println("❌ Timed out waiting for Apply Now link: " + te.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error during Apply Now navigation: " + e.getMessage());
        }
    }

    /**
     * Parses the embedded HTML job description and prepares a DOM object.
     *
     * @param html raw job description HTML (escaped)
     */
    private void parseDescriptionContent(String html) {
        String decoded = StringEscapeUtils.unescapeHtml4(html);
        jobDescriptionDoc = Jsoup.parse(decoded);

        // Remove empty paragraphs
        jobDescriptionDoc.select("p").removeIf(p -> p.text().trim().isEmpty());

        extractThirdParagraphSentence();
        extractAllSecondBulletsByHeader();
    }

    /**
     * Extracts the first sentence of the third paragraph in the job description.
     */
    private void extractThirdParagraphSentence() {
        List<String> paras = jobDescriptionDoc.select("p").stream()
                .map(Element::text)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        if (paras.size() >= 3) {
            String[] sentences = paras.get(2).split("(?<=\\.)\\s+");
            thirdParagraphFirstSentence = sentences[0].trim();
        }
    }

    /**
     * Finds all <ul> elements and maps their second bullet point
     * using the preceding header text (e.g., "Responsibilities", "Skills").
     */
    private void extractAllSecondBulletsByHeader() {
        Elements uls = jobDescriptionDoc.select("ul");
        for (Element ul : uls) {
            Element prev = ul.previousElementSibling();
            if (prev != null) {
                String header = prev.text().trim();
                Elements items = ul.select("li");
                if (items.size() >= 2) {
                    secondBulletsByHeader.put(header.toLowerCase(), items.get(1).text().trim());
                }
            }
        }
    }

    // ——— Public Getters ———

    public String getExpectedJobTitle() {
        return expectedJobTitle;
    }

    public String getExpectedJobLocation() {
        return expectedJobLocation;
    }

    public String getExpectedJobId() {
        return expectedJobId;
    }

    public String getLastJobTitle() {
        return lastJobTitle;
    }

    public String getLastJobLocation() {
        return lastJobLocation;
    }

    public String getLastJobId() {
        return lastJobId;
    }

    public String getThirdParagraphFirstSentence() {
        return thirdParagraphFirstSentence;
    }

    /**
     * Returns the second bullet item under a given section header (case-insensitive).
     *
     * @param headerText the header under which to find the bullet
     * @return the second bullet text, or null if not found
     */
    public String getSecondBulletUnderHeader(String headerText) {
        return secondBulletsByHeader.get(headerText.toLowerCase());
    }
}
