package core.pageobjects;

import core.constants.Browser;
import core.constants.Env;
import core.testutils.BrowserActionsUtility;
import core.testutils.JSONUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;


public class HomePage extends BrowserActionsUtility {

    private static final By login = By.id("login");
    private static final By bookStoreTab = By.xpath("//div/h5[contains(text(),'Book Store Application')]");
    public HomePage(WebDriver driver) {
        super(driver);
        navigateToWebsite(JSONUtility.readEnvironmentConfig(Env.QA).getUrl());
        maximizeWindow();
    }
    public HomePage(Browser browserName, boolean isHeadless) {
        super(browserName, isHeadless);
        navigateToWebsite(JSONUtility.readEnvironmentConfig(Env.QA).getUrl());
        maximizeWindow();
    }
    public void navigateToLoginPageScreen() {
        BrowserActionsUtility.clickOn(bookStoreTab);
        BrowserActionsUtility.clickOn(login);
    }

}
