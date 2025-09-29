package com.ui;

import core.constants.Browser;
import core.constants.Env;
import core.env_config.Environment;
import core.pageobjects.HomePage;
import core.pageobjects.LoginPage;
import core.testutils.BrowserActionsUtility;
import core.testutils.JSONUtility;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class TestBase {
    HomePage homePage;
    @Parameters({"browser", "QA", "isHeadless"})
    @BeforeMethod(description = "Loading the test base")
    public void setup(@Optional("chrome") String browser, @Optional("QA") String env,
                      @Optional("false") boolean isHeadless, ITestResult result) {
        homePage = new HomePage(Browser.valueOf(browser.toUpperCase()), isHeadless);
    }
    @AfterMethod()
    public void tearDown() {
        homePage.quit();
    }


}
