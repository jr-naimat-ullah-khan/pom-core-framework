package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestResult;

public class ExtentReportManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	public static String reportPath;
	private static int totalTests = 0;
	private static int passedTests = 0;
	private static int failedTests = 0;


	public static ExtentReports getReportInstance() {
		if (extent == null) {
			String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
			reportPath = "reports/ExtentReport_" + timestamp + ".html";
			ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);

			reporter.config().setDocumentTitle("Automation Test Report");
			reporter.config().setReportName("Test Execution Report");



			extent = new ExtentReports();
			extent.attachReporter(reporter);

			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("Browser", Config.getConfigValue("browser.name",String.class));

		}
		return extent;
	}

	public static ExtentTest createTest(String testname) {
		ExtentTest newTest = getReportInstance().createTest(testname);
		setTest(newTest);
		return newTest;
	}

	// Update captureScreenshot method to return an absolute path
	public static void setTest(ExtentTest extentTest) {
		test.set(extentTest);
	}

	public static ExtentTest getTest() {
		return test.get();
	}

	// Track the number of tests and results
	public static void updateTestStats(ITestResult result) {
		totalTests++;
		if (result.getStatus() == ITestResult.SUCCESS) {
			passedTests++;
		} else if (result.getStatus() == ITestResult.FAILURE) {
			failedTests++;
		}
	}

	public static int getTotalTests() {
		return totalTests;
	}

	public static int getPassedTests() {
		return passedTests;
	}

	public static int getFailedTests() {
		return failedTests;
	}

	// Reset stats after each suite (optional)
	public static void resetStats() {
		totalTests = 0;
		passedTests = 0;
		failedTests = 0;
	}


}
