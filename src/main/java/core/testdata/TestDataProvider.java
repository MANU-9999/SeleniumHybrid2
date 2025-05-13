package core.testdata;

import org.testng.annotations.DataProvider;

public class TestDataProvider {
    @DataProvider(name = "ValidLogin")
    public Object[][] ValidLogin() {
        return new Object[][]{{"ManojKumarCH", "Password@123"}};
    }  @DataProvider(name = "InValidLogin")
    public Object[][] InValidLogin() {
        return new Object[][]{{"dummy_user", "dummy_password"}};
    }
}
