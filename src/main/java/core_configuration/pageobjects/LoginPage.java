package core_configuration.pageobjects;

import core_configuration.testutils.BrowserActionsUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BrowserActionsUtility {

    private static final By passowordField = By.xpath("//form/input[2][contains(@class, 'flt-text-editing') and @type='password']");
    private static final By signIn = By.xpath("//flutter-view");
    private static final By alertMessage = By.xpath("//span[contains(text(),'incorrect password')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }
    public static void ValidLogin(String validPassword) {
        BrowserActionsUtility.clickOn(passowordField);
        BrowserActionsUtility.enterText(passowordField, validPassword);
    }

    public static void InValidLogin(String inValidPassword) {
        {
            BrowserActionsUtility.clickOn(passowordField);
            BrowserActionsUtility.enterText(passowordField, inValidPassword);

        }
    }

}