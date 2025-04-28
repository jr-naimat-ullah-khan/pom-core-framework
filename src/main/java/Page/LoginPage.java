	package Page;

	import Base.BasePage;
	import com.aventstack.extentreports.ExtentTest;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.FindBy;
	import org.openqa.selenium.support.PageFactory;
	import utils.ExtentReportManager;
	import utils.ScreenshotUtils;

	public class LoginPage {

		private final WebDriver driver;
		private static ExtentTest test;


		@FindBy(id = "user-name")
		private WebElement emailField;

		@FindBy(id = "password")
		private WebElement passwordField;

		@FindBy(id = "login-button")
		private WebElement loginButton;

		public LoginPage(WebDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
		}

		public LoginPage Login(String email, String password) {
			BasePage.enterText(emailField, email, "Email");
			BasePage.enterText(passwordField, password, "Password");
			BasePage.clickElement(loginButton, "Login Button");
			return this;
		}



	}
