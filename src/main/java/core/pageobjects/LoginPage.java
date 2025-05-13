package core.pageobjects;

import core.testutils.BrowserActionsUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class LoginPage extends BrowserActionsUtility {
    private static final By userName = By.id("userName");
    private static final By password = By.id("password");
    private static final By login = By.id("login");
    private static final By alertMessage = By.cssSelector("p#name");
    private static final By logOut = By.xpath("//button[contains(text(),'Log out')]");
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    public static void performLogin(String username, String pswd) {
        BrowserActionsUtility.enterText(userName, username);
        BrowserActionsUtility.enterText(password, pswd);
        BrowserActionsUtility.clickOn(login);
    }
    public static void ValidLogin(String username, String pswd) {
        performLogin(username, pswd);
    }
    public static void loginFromExcelData(String username, String pswd) {
        performLogin(username, pswd);
        String alertText = null;
        try {
            alertText = BrowserActionsUtility.getTextIfElementPresent(alertMessage);
        } catch (NoSuchElementException e) {
            System.out.println("No alert message displayed, assuming login is valid.");
        }
        if (alertText != null && alertText.equalsIgnoreCase("Invalid username or password!")) {
            BrowserActionsUtility.clearText(userName);
            BrowserActionsUtility.clearText(password);
        } else {
            try {
                BrowserActionsUtility.clickOn(logOut);
            } catch (ElementClickInterceptedException e) {
                BrowserActionsUtility.clickWithJS(logOut); // Fallback using JS
            }
        }
    }
    public static void loginFromExcel() {

        List<Map<String, String>> users = null;
        try {
            users = BrowserActionsUtility.readExcelData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Map<String, String> user : users) {
            String username = user.get("UserName");
            String password = user.get("Password");

            loginFromExcelData(username, password);

        }
    }


}