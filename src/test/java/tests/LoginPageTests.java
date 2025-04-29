package tests;

import Base.BasePage;
import Page.ContactUsPage;
import Page.HomePage;
import Page.LoginPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.DataProviders;
import utils.ExtentReportManager;
import utils.TestListener;

import java.util.Map;

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
		Assert.assertEquals(Base.DriverManager.getDriver().getTitle(), "Swag Labs");

		homePage.SortingItems("hilo");
		By SortMsg = By.cssSelector("select.product_sort_container option[value='hilo']");
		Assert.assertEquals(Base.DriverManager.getDriver().findElement(SortMsg).getText(),"Price (high to low)");

		ExtentReportManager.getTest().pass("Sorting Check Success");
	}


	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
	public void InValidSortItemFromHighToLow(Map<String, String> data) {

		ExtentReportManager.getTest().info("Testing Home Page High to Low Soritng ");
		LoginPage login = new LoginPage();
		HomePage homePage = new HomePage();

		String username = data.get("username");
		String password = data.get("password");

		login.Login(username, password);
		Assert.assertEquals(Base.DriverManager.getDriver().getTitle(), "Swag Labs");

		homePage.SortingItems("lohi");
		By SortMsg = By.cssSelector("select.product_sort_container option[value='lohi']");
		Assert.assertEquals(Base.DriverManager.getDriver().findElement(SortMsg).getText(),"Price (low to high)");

		ExtentReportManager.getTest().pass("InValid Sorting Check Success");
	}

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
	public void ValidatingFacebookUrl(Map<String, String> data) {

		ExtentReportManager.getTest().info("Testing Websites Multiple Pages");
		LoginPage login = new LoginPage();
		HomePage homePage = new HomePage();
		ContactUsPage contactUsPage = new ContactUsPage();


		String username = data.get("username");
		String password = data.get("password");
		String email = data.get("email");
		String company = data.get("company");
		String comment = data.get("comment");

		login.Login(username, password);
		Assert.assertEquals(Base.DriverManager.getDriver().getTitle(), "Swag Labs");
		homePage.CrossPageCheck();
		contactUsPage.FillForm(email,company,comment);

		String CurrentURL = Base.DriverManager.getDriver().getCurrentUrl();
        Assert.assertNotNull(CurrentURL);
        Assert.assertTrue(CurrentURL.contains("facebook.com/saucelabs"), "URL does not contain Facebook link!");

		ExtentReportManager.getTest().pass("Multiple Test Check Success");
	}
}


