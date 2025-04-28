package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import utils.Config;
import utils.EmailUtils;
import utils.ExtentReportManager;
import utils.ScreenshotUtils;

import java.util.Objects;

public class BasePage {

	protected static ExtentReports extent;
	protected ExtentTest test;
	private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	// Returns the WebDriver for current thread/test
	public WebDriver getDriver() {
		return driver.get();
	}

	// Runs once before all tests — used to start ExtentReport
	@BeforeSuite
	public void SetupReport() {
		extent = ExtentReportManager.getReportInstance();
	}

	// Generic function to enter text into any input field
	public static void enterText(WebElement element, String text, String fieldName) {
		element.sendKeys(text);
		try {
			ExtentReportManager.getTest().info("Entered " + fieldName + ": " + text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Generic function to click any element
	public static void clickElement(WebElement element, String elementName) {
		element.click();
		try {
			ExtentReportManager.getTest().info("Clicked on " + elementName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Runs before each test method — used to launch browser and open the site
	@BeforeMethod
	public void setup() {
		String browser = Config.getConfigValue("browser.name",String.class);
		boolean headless = Boolean.TRUE.equals(Config.getConfigValue("browser.headless", Boolean.class));

		if (browser == null)
		{
			throw new IllegalStateException("Browser name not found in config file.");
		}

		switch (Objects.requireNonNull(browser).toLowerCase()) {
			case "chrome":
				ChromeOptions chromeOptions = new ChromeOptions();
				if (headless) chromeOptions.addArguments("--headless");
				driver.set(new ChromeDriver(chromeOptions));
				break;
			case "firefox":
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				if (headless) firefoxOptions.addArguments("-headless");
				driver.set(new FirefoxDriver(firefoxOptions));
				break;
			case "edge":
				EdgeOptions edgeOptions = new EdgeOptions();
				if (headless) edgeOptions.addArguments("--headless");
				driver.set(new EdgeDriver(edgeOptions));
				break;
			default:
				throw new IllegalStateException("Unsupported browser: " + browser);
		}

		// Open the test website
		getDriver().get(Objects.requireNonNull(Config.getConfigValue("website.url", String.class)));
	}

	// Runs after each test method — takes screenshot, logs result, closes browser
	@AfterMethod
	public void Clean(ITestResult result) {
		ExtentReportManager.updateTestStats(result);

		if (getDriver() != null) {
			getDriver().quit();  // Close browser
			driver.remove();     // Remove driver from ThreadLocal
		}

	}

	// Runs once after all tests — flushes the report and sends it via email
	@AfterSuite
	public void FlushReport() {
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
			EmailUtils.sendTestReport(reportPath, isTestFailure, totalTests, passedTests, failedTests);
		}

	}
}
