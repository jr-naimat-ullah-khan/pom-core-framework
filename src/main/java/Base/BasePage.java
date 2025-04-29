package Base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import utils.ExtentReportManager;
import java.time.Duration;
import utils.ScreenshotUtils;

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
		DriverManager.setup(); // Initialize WebDriver from DriverManager
	}

	// Generic function to click any element
	public void Click(WebElement element, String elementName) {
		element.click();
		try {
			String screenshotPath = ScreenshotUtils.captureScreenshot(DriverManager.getDriver());
			ExtentReportManager.getTest().info("Clicked on " + elementName)
					.addScreenCaptureFromPath(screenshotPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Generic function to enter text into any input field
	public void Write(WebElement element, String text, String fieldName) {
		element.sendKeys(text);
		try {
			String screenshotPath = ScreenshotUtils.captureScreenshot(DriverManager.getDriver());
			ExtentReportManager.getTest().info("Entered " + fieldName + ": " + text)
					.addScreenCaptureFromPath(screenshotPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scrollToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
	}

	public void waitForElementToBeClickable(WebElement element, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutInSeconds));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	// Runs after each test method — takes screenshot, logs result, closes browser
	@AfterMethod
	public void clean(ITestResult result) {
		ExtentReportManager.updateTestStats(result);
		DriverManager.quit();  // Close browser and cleanup WebDriver
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
