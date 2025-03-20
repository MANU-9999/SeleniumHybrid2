package core_configuration.testutils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import core_configuration.constants.Browser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BrowserActionsUtility {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();  // ThreadLocal to store WebDriver for each thread
    private static Logger logger = LoggerUtility.getLogger(BrowserActionsUtility.class);
    private static WebDriverWait wait;

    // Constructor for initializing WebDriver
    public BrowserActionsUtility(WebDriver driver) {
        BrowserActionsUtility.driver.set(driver);  // Assign WebDriver to ThreadLocal
        wait = new WebDriverWait(driver, Duration.ofSeconds(30L));
    }

    // Constructor to initialize WebDriver with a specific browser
    public BrowserActionsUtility(Browser browserName) {
        if (browserName == Browser.CHROME) {
            driver.set(new ChromeDriver());  // Initialize ChromeDriver and set it in ThreadLocal
        } else if (browserName == Browser.FIREFOX) {
            driver.set(new FirefoxDriver());  // Initialize FirefoxDriver and set it in ThreadLocal
        } else if (browserName == Browser.EDGE) {
            driver.set(new EdgeDriver());  // Initialize EdgeDriver and set it in ThreadLocal
        }
        wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));
    }

    // Constructor for initializing WebDriver with a specific browser and headless option
    public BrowserActionsUtility(Browser browserName, boolean isHeadless) {
        if (browserName == Browser.CHROME) {
            ChromeOptions options = new ChromeOptions();
            if (isHeadless) {
                options.addArguments("--headless", "--window-size=1920x1080");
            }
            driver.set(new ChromeDriver(options));  // Initialize ChromeDriver with options
        } else if (browserName == Browser.EDGE) {
            EdgeOptions options = new EdgeOptions();
            if (isHeadless) {
                options.addArguments("--headless", "--window-size=1920x1080");
            }
            driver.set(new EdgeDriver(options));  // Initialize EdgeDriver with options
        } else if (browserName == Browser.FIREFOX) {
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) {
                options.addArguments("--headless");
            }
            driver.set(new FirefoxDriver(options));  // Initialize FirefoxDriver with options
        }
        wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));
    }

    // Get the WebDriver instance for the current thread
    public WebDriver driver() {
        return driver.get();  // Use ThreadLocal.get() to get the WebDriver for the current thread
    }

    // Navigate to a URL
    public void navigateTo(String url) {
        driver.get().get(url);  // Use WebDriver from ThreadLocal
    }

    // Maximize the browser window
    public void maximizeWindow() {
        driver.get().manage().window().maximize();
    }

    // Click on an element using locator
    public static void clickOn(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        logger.info("Element found and now performing click");
        element.click();
    }

    // Enter text in a field using locator
    public static void enterText(By locator, String textToEnter) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        logger.info("Element found and now entering text: " + textToEnter);
        element.sendKeys(textToEnter);
    }

    // Clear text in a field using locator
    public void clearText(By textBoxLocator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(textBoxLocator));
        logger.info("Element found and clearing the text box");
        element.clear();
    }

    // Select an option from a dropdown using visible text
    public void selectFromDropdown(By dropDownLocator, String optionToSelect) {
        WebElement element = driver.get().findElement(dropDownLocator);
        Select select = new Select(element);
        select.selectByVisibleText(optionToSelect);
    }

    // Get visible text from an element
    public String getVisibleText(By locator) {
        WebElement element = driver.get().findElement(locator);
        return element.getText();
    }

    // Capture a screenshot
    public static String captureScreenshot(String testName) {
        String SCREENSHOT_DIR = "/target/testScreenshotsFiles/";
        File screenshot = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);  // Capture screenshot
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = System.getProperty("user.dir") + SCREENSHOT_DIR + testName + "_screenshot_" + timestamp + ".PNG";

        try {
            FileUtils.copyFile(screenshot, new File(filePath));  // Save screenshot
        } catch (Exception e) {
            logger.error("Error while capturing screenshot", e);
        }
        return filePath;
    }

    // Quit the browser and clean up
    public static void quit() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();  // Clean up the WebDriver from ThreadLocal
        }
    }
}
