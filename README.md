# 🧪 LabCorp Careers Website Automation Test Framework

This project automates testing for the LabCorp Careers portal using **Java**, **Selenium**, and **Cucumber (BDD)**. It validates job search functionality, job description parsing, and redirection to the application page.

---

## 🚀 Features

- ✅ Page Object Model (POM) architecture
- 🔎 Search for a job and validate job details (title, location, ID)
- 📜 Extract and assert job description bullets and paragraphs
- 🔗 Test “Apply Now” redirection flow
- 🧾 Test execution logging to timestamped log file
- 🧪 Unit tests for critical logic using JUnit

---

## 📁 Project Structure

src
├── main
│ └── java
│ └── com.labcorp
│ ├── base // BaseTest setup
│ ├── driver // WebDriverFactory using WebDriverManager
│ └── pages // Page Objects: CareersPage, HomePage
├── test
│ ├── java
│ │ └── com.labcorp
│ │ ├── hooks // Cucumber Hooks for setup/teardown
│ │ ├── runner // Cucumber TestRunner (JUnit)
│ │ ├── stepdefs // Cucumber Step Definitions
│ │ ├── unit // Unit Tests (JUnit)
│ │ └── utils // TestLogger (writes timestamped logs)
│ └── resources
│ └── features // Cucumber Feature Files


---

## ⚙️ Prerequisites

- Java 11 or higher
- Maven
- Chrome browser
- Internet access for WebDriverManager
- Git (if cloning)

---

## ▶️ How to Run the Tests

### ✅ 1. Clone the Repository

```bash
git clone https://github.com/codemonk-in/labcorp-website-automation-test.git
cd labcorp-website-automation-test

### ✅ 2. Run BDD Tests - mvn clean test
This will:
Open the LabCorp homepage
Navigate to the Careers section
Search for a job
Click on the first result
Validate job details & description
Navigate to the Apply Now page

📝 Logs will be written to: target/test-execution-log-[timestamp].txt

📊 Reports will be saved as:
target/cucumber-reports.html
target/cucumber.json

🧪 Running Unit Tests Only
mvn test -Dtest=com.labcorp.unit.CareersPageTest

🧾 Logging
All test execution is logged with timestamps to a uniquely named file under: target/test-execution-log-<yyyyMMdd_HHmmss>.txt

Example log snippet:
[2025-07-06 16:30:01] 🚀 Starting Scenario: Job Search and Apply Flow
[2025-07-06 16:30:09] ✅ Job title matches the result listing.
[2025-07-06 16:30:15] 🏁 Finished Scenario: Job Search and Apply Flow | Status: PASSED

🧪 Sample Feature File
Feature: LabCorp Careers Job Search

  Scenario: Validate job search and apply flow
    Given the user is on the LabCorp home page
    When the user navigates to the Careers page
    And the user searches for "QA Test Automation Engineer"
    And the user clicks on the first job result
    Then the job title should match the result listing
    And the job location should match the result listing
    And the job ID should match the result listing
    Then the job description third paragraph first sentence should be:
      """
      The right candidate for this role will participate in the test automation technology development and best practice models.
      """
    Then the job description second bullet under "Management Support" should contain "test plans"
    And the user clicks on Apply Now button
    Then the user is redirected back to the Careers page

📦 Dependencies
Selenium WebDriver
WebDriverManager
Cucumber-Java
JUnit 4
JSoup (for parsing HTML job descriptions)
Apache Commons Text

🔐 Authentication Notes
This test navigates public URLs and does not require authentication. If authentication is introduced in future:
Consider using Basic Auth headers or cookie injection in Selenium
Secure credentials using environment variables

📦 Dependencies
Selenium WebDriver
WebDriverManager
Cucumber-Java
JUnit 4
JSoup (for parsing HTML job descriptions)
Apache Commons Text

🔐 Authentication Notes
This test navigates public URLs and does not require authentication. If authentication is introduced in future:
Consider using Basic Auth headers or cookie injection in Selenium
Secure credentials using environment variables

💡 Tips
If you're behind a proxy, configure WebDriverManager accordingly
You can run the test in headless mode by uncommenting: options.addArguments("--headless");

📣 Author
Apurv Mishra
Senior Test Automation Engineer
GitHub: @codemonk-in

📝 License
This project is licensed under the MIT License - feel free to use and modify as needed.
