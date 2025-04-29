package Base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ExtentReportManager;
import utils.ScreenshotUtils;

import java.time.Duration;

public class BasePage {

	protected static ExtentReports extent;
	public DriverManager DriverManager;
	protected ExtentTest test;

	// Runs once before all tests — used to start ExtentReport
	@BeforeSuite
	public void setupReport() {
		extent = ExtentReportManager.getReportInstance();
	}

	// Runs before each test method — used to initialize WebDriver
	@BeforeMethod
	public void setup() {
		Base.DriverManager.setup(); // Initialize WebDriver from DriverManager
	}

	// Generic function to click any element
	public void Click(WebElement element, String elementName) {
		element.click();
		try {
			ExtentReportManager.getTest().info("Clicked on " + elementName);
			String screenshotPath = ScreenshotUtils.captureScreenshot(Base.DriverManager.getDriver());
			String imageTag = "<a href='" + screenshotPath + "' target='_blank'><img src='" + screenshotPath + "' width='300' height='200' /></a>";
			ExtentReportManager.getTest().info("Screenshot after clicking " + elementName + ": " + imageTag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Generic function to enter text into any input field
	public void Write(WebElement element, String text, String fieldName) {
		element.sendKeys(text);
		try {
			ExtentReportManager.getTest().info("Entered " + fieldName + ": " + text);
			String screenshotPath = ScreenshotUtils.captureScreenshot(Base.DriverManager.getDriver());
			String imageTag = "<a href='" + screenshotPath + "' target='_blank'><img src='" + screenshotPath + "' width='300' height='200' /></a>";
			ExtentReportManager.getTest().info("Screenshot after entering " + fieldName + ": " + imageTag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Scrolls the page until the element is in view
	public void scrollToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) Base.DriverManager.getDriver();
		js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
	}

	// Waits until the element is clickable within the specified timeout
	public void waitForElementToBeClickable(WebElement element, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(Base.DriverManager.getDriver(), Duration.ofSeconds(timeoutInSeconds));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	// Runs after each test method — takes screenshot, logs result, closes browser
	@AfterMethod
	public void clean(ITestResult result) {
		ExtentReportManager.updateTestStats(result);
		Base.DriverManager.quit();  // Close browser and cleanup WebDriver
	}

	// Runs once after all tests — flushes the report and sends it via email
	@AfterSuite
	public void flushReport() {
		extent.flush();
		int totalTests = ExtentReportManager.getTotalTests();
		int passedTests = ExtentReportManager.getPassedTests();
		int failedTests = ExtentReportManager.getFailedTests();

		System.out.println("Failed Test: " + failedTests);
		boolean isTestFailure = failedTests > 0;

		// Send email only if there are failed tests
		if (isTestFailure) {
			String reportPath = ExtentReportManager.reportPath;
			// Call email sending method with failure status
			// EmailUtils.sendTestReport(reportPath, isTestFailure, totalTests, passedTests, failedTests);
		}
	}
}
