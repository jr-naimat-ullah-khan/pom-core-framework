

# Selenium Test Automation Framework

A clean, scalable Test Automation Framework using Java + Selenium WebDriver + TestNG + ExtentReports for Web UI testing.

---

## Features

- Page Object Model (POM) design pattern
- Thread-safe WebDriver using ThreadLocal (parallel execution support)
- ExtentReports for detailed reporting
- Custom TestNG Listeners for capturing test status and screenshots
- Parameterized Tests using TestNG DataProviders (Static and Dynamic using Excel)
- Parallel execution ready through TestNG XML
- Utility functions for common WebDriver actions
- Config-driven setup using JSON
- Browser support for Chrome, Firefox, and Edge
- Email Notifications after test execution (optional)

---

## Project Structure

```
|-- src
|    |-- main
|    |    |-- Base
|    |    |    └── BasePage.java          # Browser setup, generic utilities
|    |    |-- Page
|    |    |    └── LoginPage.java         # Page Object for Login Page
|    |    |-- utils
|    |    |    ├── Config.java            # Configuration management
|    |    |    ├── ScreenshotUtils.java   # Screenshot capturing
|    |    |    ├── ExtentReportManager.java # Extent report management
|    |    |    ├── EmailUtils.java         # Email sending utility
|    |    |    └── DataProviders.java     # Data Providers (static and Excel based)
|    |    |    └── ExcelUtils.java           # Excel file utilities
|    |         └── Log.java                       # Logging utility
|    |         └── TestListener.java                  # Custom TestNG Listener
|    |
|    |-- resources
|    |    ├── config.json                 # Configurations for browser, website URL, credentials
|    TestData
|    └── LoginData.xlsx         # Excel file for dynamic data-driven tests
|-- src
|    |-- test
|    |    |-- tests
|    |    |    └── LoginPageTests.java     # Test Class for login functionality
|-- testng.xml                             # TestNG Suite file
```

---

## Pre-requisites

- Java JDK 8 or higher
- Maven
- IntelliJ IDEA or Eclipse IDE
- ChromeDriver, GeckoDriver (Firefox), EdgeDriver
- TestNG plugin installed in IDE

---

## How to Set Up

1. **Clone the Repository**

   ```bash
   git clone https://github.com/Cyber-Naimo/selenium-test-automation-framework.git
   ```

2. **Open in IntelliJ IDEA or Eclipse**

3. **Install Maven Dependencies**

   Maven will automatically download all dependencies from the `pom.xml` file.

4. **Update Configuration**

   Navigate to:  
   `src/main/resources/config.json`

   Example `config.json`:

   ```json
   {
     "browser": {
       "name": "chrome",
       "headless": false
     },
     "website": {
       "url": "https://yourwebsite.com/"
     },
     "emailUtils": {
       "senderEmail": "sender@gmailcom",
       "appPassword": "yourapppassword",
       "recipientEmail": "recipient@gmail.com"
     }
   }
   ```

    - Set the correct browser name
    - Provide your website URL
    - Set email credentials if email report sending is enabled

5. **Excel Test Data**

   Located at:  
   `src/main/resources/TestData/LoginData.xlsx`

   This file contains dynamic test data used for login tests through DataProvider.

6. **Framework Usage**

   This framework is designed to be **general-purpose**.  
   To add your own test coverage:

    - **Add Pages** in:  
      `src/main/java/Page/`

    - **Add Test Classes** in:  
      `src/test/java/tests/`

   The framework will automatically handle browser launching, reporting, and closing.

---

## How to Run Tests

- Using **TestNG plugin**: Right-click on `testng.xml` → Run
- Using **Maven command line**:

  ```bash
  mvn test
  ```

---

## TestNG XML Details

- The `testng.xml` file is already configured for:
    - Parallel execution
    - Grouping of tests
    - Listeners for reporting and screenshots

Example snippet:

```xml
<suite name="AutomationSuite" parallel="tests" thread-count="2">
    <listeners>
        <listener class-name="listeners.TestListener" />
    </listeners>
    <test name="LoginTests">
        <classes>
            <class name="tests.LoginPageTests" />
        </classes>
    </test>
</suite>
```

---

## Reporting

- Test execution generates a detailed HTML report using ExtentReports inside the `/reports/` directory.
- Failed tests will capture a screenshot automatically and attach it to the report.
- Email functionality is available to send the test report if configured.

---

## How to Contribute

1. **Fork the repository**

2. **Create a new branch**
   ```bash
   git checkout -b feature/YourFeatureName
   ```

3. **Commit your changes**
   ```bash
   git commit -m "Added Feature XYZ"
   ```

4. **Push to your branch**
   ```bash
   git push origin feature/YourFeatureName
   ```

5. **Create a Pull Request**

---

## Important Notes

- Always create a separate **branch** for new features.
- Follow existing **coding standards** (naming conventions, method organization).
- Raise a **Pull Request (PR)** with a meaningful description.
- Update the `README.md` or related documentation if your change affects setup or usage.

---

## Contact

- **Name**: Muhammad Naimatullah Khan
- **GitHub**: [@naimatullahkhan](https://github.com/Cyber-Naimo)
- **Email**:  [muhammadnaimatullahkhan@gmail.com](mailto:muhammadnaimatullahkhan@gmail.com)
---

# Final Summary Table

| Setup Area | Status |
|:-----------|:-------|
| POM Architecture | Complete |
| ThreadSafe WebDriver | Configured |
| Static and Excel DataProviders | Implemented |
| Configurations via JSON | Implemented |
| Parallel Execution | Ready |
| Reporting and Screenshots | Available |
| Email Notification on Failures | Configured |

---
