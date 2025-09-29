package core.testutils;

import core.constants.Browser;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.*;

public class BrowserActionsUtility {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();  // ThreadLocal to store WebDriver for each thread
    private static final Logger logger = LoggerUtility.getLogger(BrowserActionsUtility.class);
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
    public static void navigateToWebsite(String url) {
        logger.info("Navigating to the website" + url);
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

    public static void clickWithJS(By locator) {
        try {
            wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            ((JavascriptExecutor) driver.get()).executeScript("arguments[0].scrollIntoView(true);", locator);
            ((JavascriptExecutor) driver.get()).executeScript("arguments[0].click()", locator);
        } catch (Exception e) {
            System.out.println("JS clicked failed for" + locator);
        }
    }

    // Enter text in a field using locator
    public static void enterText(By locator, String textToEnter) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        logger.info("Element found and now entering text: " + textToEnter);
        element.sendKeys(textToEnter);
    }
    // Clear text in a field using locator
    public static void clearText(By textBoxLocator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(textBoxLocator));
        logger.info("Element found and clearing the text box");
        element.clear();
    }
    // Select an option from a dropdown using visible text
    public static void selectFromDropdown(By dropDownLocator, String optionToSelect) {
        WebElement element = driver.get().findElement(dropDownLocator);
        Select select = new Select(element);
        select.selectByVisibleText(optionToSelect);
    }
    // Get visible text from an element
    public static String getVisibleText(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }
    public static String getTextIfElementPresent(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            if (element.isDisplayed()) {
                return element.getText().trim();
            }
        } catch (TimeoutException | NoSuchElementException e) {
            // Element not found — return null
        }
        return null;
    }
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

    public static void excel() throws IOException {
        FileInputStream fis = new FileInputStream(new File("sheet.xlsx"));
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            Iterator<Cell> cellIterator = row.iterator(); // Corrected this line
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                // Handle different cell types (optional safety)
                String cellValue = "";
                if (cell.getCellType() == CellType.STRING) {
                    cellValue = cell.getStringCellValue();
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                }

                // ✅ Switch Case Validation
                switch (cellValue) {
                    case "Pass":
                        System.out.println("The test result is PASS ✅");
                        break;
                    case "Fail":
                        System.out.println("The test result is FAIL ❌");
                        break;
                    case "Pending":
                        System.out.println("The test result is PENDING ⏳");
                        break;
                    default:
                        System.out.println("Unknown status ❓ -> " + cellValue);
                        break;
                }
            }
        }

        // Close resources outside the loop
        workbook.close();
        fis.close();
    }
    public static List<Map<String, String>> readExcelData() throws IOException {
        String path = "//src//main//java//core//testdata//Excel_Imports//Credentials.xlsx";
        String sheetName = "sheet1";
        File src = new File(System.getProperty("user.dir") + path);

        List<Map<String, String>> credentials = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(src);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow headerRow = sheet.getRow(0);
            int columns = headerRow.getLastCellNum();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> userData = new HashMap<>();
                for (int j = 0; j < columns; j++) {
                    XSSFCell keyCell = headerRow.getCell(j);
                    XSSFCell valueCell = row.getCell(j);
                    if (keyCell != null && valueCell != null) {
                        userData.put(keyCell.getStringCellValue().trim(), valueCell.getStringCellValue().trim());
                    }
                }

                if (!userData.isEmpty()) {
                    credentials.add(userData);
                }
            }

        } catch (Exception e) {
            System.out.println("Error while reading the file" + e.getMessage());
        }
        return credentials;
    }


    public void quit() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();  // Clean up the WebDriver from ThreadLocal
        }
    }
}
