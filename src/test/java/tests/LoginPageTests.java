package tests;

import java.io.IOException;
import java.util.Map;

import Base.DriverManager;
import Page.ContactUsPage;
import Page.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
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
	public void InValidLoginTest(String username, String password) 	{

		ExtentReportManager.getTest().info("Testing Invalid Login Scenario with Static Data");
		LoginPage login = new LoginPage();
		login.Login(username, password);

		By errorMessage = By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3");

		Assert.assertEquals(DriverManager.getDriver().findElement(errorMessage).getText(),
				"Epic sadface: Username and password do not match any user in this service",
				"Error message text is not as expected");

		ExtentReportManager.getTest().pass("Invalid Login Test Completed Successfully");
	}


	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
	public void ValidLoginTest(Map<String, String> data) {

		ExtentReportManager.getTest().info("Testing Login Page with Dynamic Excel Data");
		LoginPage login = new LoginPage();

		String username = data.get("username");
		String password = data.get("password");

		login.Login(username, password);

		Assert.assertEquals(DriverManager.getDriver().getTitle(), "Swag Labs");
		ExtentReportManager.getTest().pass("Login Success Dynamic");
	}

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
	public void SortItemFromHighToLow(Map<String, String> data) {

		ExtentReportManager.getTest().info("Testing Home Page High to Low Soritng ");
		LoginPage login = new LoginPage();
		HomePage homePage = new HomePage();

		String username = data.get("username");
		String password = data.get("password");

		login.Login(username, password);
		Assert.assertEquals(DriverManager.getDriver().getTitle(), "Swag Labs");

		homePage.SortingItems("hilo");
		By SortMsg = By.cssSelector("select.product_sort_container option[value='hilo']");
		Assert.assertEquals(DriverManager.getDriver().findElement(SortMsg).getText(),"Price (high to low)");

		ExtentReportManager.getTest().pass("Sorting Check Success");
	}

//	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
//	public void ValidatingFacebookUrl(Map<String, String> data) {
//
//		ExtentReportManager.getTest().info("Testing Websites Multiple Pages");
//		LoginPage login = new LoginPage(DriverManager.getDriver());
//		HomePage homePage = new HomePage(DriverManager.getDriver());
//		ContactUsPage contactUsPage = new ContactUsPage(DriverManager.getDriver());
//
//
//		String username = data.get("username");
//		String password = data.get("password");
//		String email = data.get("email");
//		String company = data.get("company");
//		String comment = data.get("comment");
//
//		login.Login(username, password);
//		Assert.assertEquals(DriverManager.getDriver().getTitle(), "Swag Labs");
//		homePage.CrossPageCheck();
//		contactUsPage.FillForm(email,company,comment);
//
//		String CurrentURL = DriverManager.getDriver().getCurrentUrl();
//		Assert.assertTrue(CurrentURL.contains("facebook.com/saucelabs"), "URL does not contain Facebook link!");
//
//		ExtentReportManager.getTest().pass("Multiple Test Check Success");
//	}

	@Test
	public void ValidatingFacebookUrl() {

		ExtentReportManager.getTest().info("Testing Websites Multiple Pages");
		LoginPage login = new LoginPage();
		HomePage homePage = new HomePage();
		ContactUsPage contactUsPage = new ContactUsPage();


		String username = "standard_user";
		String password = "secret_sauce";
		String email = "hello@gmail.com";
		String company ="hELLO";
		String comment ="vENTURECIJISJS";

		login.Login(username, password);
		Assert.assertEquals(DriverManager.getDriver().getTitle(), "Swag Labs");
		homePage.CrossPageCheck();
		contactUsPage.FillForm(email,company,comment);

		String CurrentURL = DriverManager.getDriver().getCurrentUrl();
		Assert.assertTrue(CurrentURL.contains("facebook.com/saucelabs"), "URL does not contain Facebook link!");

		ExtentReportManager.getTest().pass("Multiple Test Check Success");
	}
}


