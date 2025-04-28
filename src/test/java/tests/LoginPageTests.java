package tests;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BasePage;
import Page.LoginPage;
import utils.ExtentReportManager;
import utils.Log;
import utils.DataProviders;
import utils.TestListener;

@Listeners(TestListener.class)
public class LoginPageTests extends BasePage {

	@Test(dataProvider = "LoginData2", dataProviderClass = DataProviders.class)
	public void ValidInLoginTestStatic(String username, String password) {

		ExtentReportManager.getTest().info("Testing Invalid Login Page with Static Data");
		LoginPage login = new LoginPage(getDriver());
		login.Login(username, password);

		By errorMessage = By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3");

		Assert.assertEquals(getDriver().findElement(errorMessage).getText(),
				"Epic sadface: Username and password do not match any user in this service",
				"Error message text is not as expected");

		ExtentReportManager.getTest().pass("Invalid Login Test Completed Successfully");
	}

	@Test(dataProvider = "LoginData2", dataProviderClass = DataProviders.class)
	public void InValidInLoginTestStatic(String username, String password) {

		ExtentReportManager.getTest().info("Testing Invalid Login Page with Static Data");
		LoginPage login = new LoginPage(getDriver());
		login.Login(username, password);

		By errorMessage = By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3");

		Assert.assertEquals(getDriver().findElement(errorMessage).getText(),
				"Epic sadface: Username and password do not match any user in this service",
				"Error message text is not as expected");

		ExtentReportManager.getTest().pass("Invalid Login Test Completed Successfully");
	}

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
	public void ValidLoginTestDynamic(Map<String, String> data) {

		ExtentReportManager.getTest().info("Testing Login Page with Dynamic Excel Data");
		LoginPage login = new LoginPage(getDriver());

		String username = data.get("username");
		String password = data.get("password");

		login.Login(username, password);

		Assert.assertEquals(getDriver().getTitle(), "Swag Labs");
		ExtentReportManager.getTest().pass("Login Success Dynamic");
	}
}
