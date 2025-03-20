package core_configuration.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import core_configuration.testutils.BrowserActionsUtility;

public class SignUpPage extends BrowserActionsUtility {
    private static final By mobileNo = By.xpath("//input[@id='username' and @type='text']");

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public static void doSignUpWith(String mobileNumber) {
        BrowserActionsUtility.enterText(mobileNo, mobileNumber);
//        BrowserActionsUtility.roboActions();
    }
}
