	package Page;

	import Base.BasePage;
	import Base.DriverManager;
	import com.aventstack.extentreports.ExtentTest;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.FindBy;
	import org.openqa.selenium.support.PageFactory;

	public class LoginPage extends DriverManager {

        private static ExtentTest test;

		BasePage basePage = new BasePage();
		public LoginPage()
		{
			PageFactory.initElements(getDriver(), this);
		}
		@FindBy(id = "user-name")
		private WebElement emailField;

		@FindBy(id = "password")
		private WebElement passwordField;

		@FindBy(id = "login-button")
		private WebElement loginButton;



		public LoginPage Login(String email, String password) {
			basePage.Write(emailField, email, "Email");
			basePage.Write(passwordField, password, "Password");
			basePage.Click(loginButton, "Login Button");
			return this;
		}



	}
