package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.Config;

import java.util.Objects;

public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Returns the WebDriver for current thread/test
    public static WebDriver getDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver == null) {
            throw new IllegalStateException("WebDriver is not initialized!");
        }
        return currentDriver;
    }

    // Initializes WebDriver based on browser configuration
    public static void setup() {
        String browser = Config.getConfigValue("browser.name", String.class);
        boolean headless = Boolean.TRUE.equals(Config.getConfigValue("browser.headless", Boolean.class));

        if (browser == null) {
            throw new IllegalStateException("Browser name not found in config file.");
        }

        switch (Objects.requireNonNull(browser).toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                } else {
                    chromeOptions.addArguments("start-maximized");
                }
                driver.set(new ChromeDriver(chromeOptions));
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("-headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }
                driver.set(new FirefoxDriver(firefoxOptions));
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--window-size=1920,1080");
                } else {
                    edgeOptions.addArguments("start-maximized");
                }
                driver.set(new EdgeDriver(edgeOptions));
                break;
            default:
                throw new IllegalStateException("Unsupported browser: " + browser);
        }

        // Maximize the browser window if not headless
        if (!headless) {
            getDriver().manage().window().maximize();
        }

        // Open website
        getDriver().get(Objects.requireNonNull(Config.getConfigValue("website.url", String.class)));

    }

    // Closes the WebDriver after each test
    public static void quit() {
        WebDriver driverInstance = driver.get();
        if (driverInstance != null) {
            driverInstance.quit();  // Close browser
            driver.remove();        // Remove driver from ThreadLocal
        }
    }
}
