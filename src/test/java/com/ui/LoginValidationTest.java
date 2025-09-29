package com.ui;

import core.pageobjects.LoginPage;
import core.testutils.ListenerUtility;
import org.testng.annotations.*;

@Listeners(ListenerUtility.class)
public class LoginValidationTest extends TestBase {
   // @Test(dataProvider = "ValidLogin", dataProviderClass = TestDataProvider.class, groups = "smoke")
    public void ValidTest(String userName, String pswd) {
        LoginPage.ValidLogin(userName, pswd);
    }

    @Test(enabled = true, groups = "smoke")
    public void loginFromExcel()
    {
        LoginPage.loginFromExcel();
    }
}
