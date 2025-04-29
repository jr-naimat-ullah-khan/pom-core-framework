package utils;

import Base.BasePage;
import Base.DriverManager;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        // Initialize the ExtentTest and set it into the ExtentReportManager
        ExtentTest test = ExtentReportManager.getReportInstance().createTest(result.getMethod().getMethodName());
        ExtentReportManager.setTest(test);
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        // Log that the test passed
        ExtentReportManager.getTest().log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());

        // Capture screenshot after the test
        Object currentClass = result.getInstance();
        WebDriver driver = DriverManager.getDriver();
        String screenshotPath = ScreenshotUtils.captureScreenshot(driver);

        try {
            // Add the screenshot as a clickable element inside the test details
            String imageTag = "<a href='" + screenshotPath + "' target='_blank'><img src='" + screenshotPath + "' width='300' height='200' /></a>";
            ExtentReportManager.getTest().log(Status.PASS, "Test Passed with Screenshot" + imageTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTestFailure(ITestResult result) {
        // Log that the test failed
        ExtentReportManager.getTest().log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
        ExtentReportManager.getTest().log(Status.FAIL, result.getThrowable());

        // Capture Screenshot on Failure
        Object currentClass = result.getInstance();
        WebDriver driver = ((BasePage) currentClass).DriverManager.getDriver();
        String screenshotPath = ScreenshotUtils.captureScreenshot(driver);
        try {
            // Add the screenshot as a clickable element inside the test details
            String imageTag = "<a href='" + screenshotPath + "' target='_blank'><img src='" + screenshotPath + "' width='100' height='150' /></a>";
            ExtentReportManager.getTest().log(Status.FAIL, "Test Failed with Screenshot" + imageTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
    }



    @Override
    public void onFinish(ITestContext context) {
        // Finalize the report after all tests finish
        ExtentReportManager.getReportInstance().flush();
    }
}
