package Page;

import Base.BasePage;
import Base.DriverManager;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends DriverManager {


    private static ExtentTest test;

    BasePage basePage = new BasePage();
    public HomePage()
    {
        PageFactory.initElements(getDriver(), this);
    }

    // Home Page
    @FindBy(className = "product_sort_container")
    private WebElement productSortContainer;

    @FindBy(css = "select.product_sort_container option[value='hilo']")
    private WebElement hightolowSort;

    @FindBy(css = "select.product_sort_container option[value='liho']")
    private WebElement lowtohighSort;

    @FindBy(css = "select.product_sort_container option[value='az']")
    private WebElement AtoZSort;

    @FindBy(css = "select.product_sort_container option[value='za']")
    private WebElement ZtoASort;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement MenuButton;

    @FindBy(id = "about_sidebar_link")
    private WebElement AboutButton;


    @FindBy(xpath = "//span[text()='Contact us']")
    private WebElement ContactUsButton;

    public void     waitForNewPageToLoad() {
        WebDriver driver = getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }
    public void switchToNewWindow() {
        WebDriver driver = getDriver(); // get from ThreadLocal
        String originalWindow = driver.getWindowHandle();

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public HomePage CrossPageCheck() {
        basePage.Click(MenuButton, "Menu Button");

        basePage.waitForElementToBeClickable(AboutButton, 5);
        basePage.Click(AboutButton, "AboutUs Text");



        basePage.waitForElementToBeClickable(ContactUsButton, 5);

        basePage.scrollToElement(ContactUsButton);
        basePage.Click(ContactUsButton, "ContactUs Text");

        return this;
    }




    public HomePage SortingItems(String SortingOption)
    {
        basePage.Click(productSortContainer,"Sort Container");
        switch (SortingOption){
            case "hilo":
                basePage.Click(hightolowSort,"High to Low");
                break;
            case "lohi":
                basePage.Click(lowtohighSort,"Low to High");
            case "az":
               basePage.Click(AtoZSort,"A to Z");
                break;
            case "za":
                basePage.Click(ZtoASort,"Z to A");
        }


        return this;
    }
}
