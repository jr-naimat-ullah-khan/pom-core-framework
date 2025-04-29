package Page;

import Base.BasePage;
import Base.DriverManager;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactUsPage extends DriverManager {

    private static ExtentTest test;
    BasePage basePage = new BasePage();

    public ContactUsPage()
    {
        PageFactory.initElements(getDriver(), this);
    }
    // Contact Us Page
    @FindBy(id = "Email")
    private WebElement EmailField;

    @FindBy(id = "Company")
    private WebElement CompanyField;

    @FindBy(id = "Solution_Interest__c")
    private WebElement DropDownArrow;

    @FindBy(xpath = "//select[@id='Solution_Interest__c']/option[@value='Scalable Test Automation']")
    private WebElement scalableTestAutomationOption;

    @FindBy(id = "Sales_Contact_Comments__c")
    private WebElement CommentField;

    @FindBy (id = "LblmktoCheckbox_45779_0")
    private WebElement CheckBox;

    // Facebook icon image
    @FindBy(xpath = "//img[@alt='Facebook']")
    private WebElement facebookIcon;


    public ContactUsPage FillForm(String email,String company,String comment)
    {
        basePage.waitForElementToBeClickable(EmailField,5);
        basePage.Write(EmailField,email,"Email");
        basePage.Write(CommentField,company,"Company");
        basePage.Click(DropDownArrow,"Drop Down");
        basePage.Click(scalableTestAutomationOption,"Scalable Test Automation");
        basePage.Write(CommentField,comment,"Comment");
        basePage.Click(CheckBox,"Ticked Checkbox");

        getDriver().navigate().back();


        // Check if cookie button exists and click it
        try {
            WebElement acceptCookiesBtn = getDriver().findElement(By.id("onetrust-accept-btn-handler"));
            if (acceptCookiesBtn.isDisplayed()) {
                acceptCookiesBtn.click();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Cookie button not found, moving ahead...");
        }


        basePage.scrollToElement(facebookIcon);
        basePage.Click(facebookIcon,"Facebook Icon");

        return this;
    }
}
