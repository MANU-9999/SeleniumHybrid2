package core_configuration.testdata;

import org.testng.annotations.DataProvider;

public class TestDataProvider {
    @DataProvider(name = "signUpAndValidLogin")
    public Object[][] signUpAndValidLogin() {
        return new Object[][]{{"7672003173", "Runo@03062025"}};
    }

    @DataProvider(name = "inValidLogin")
    public Object[][] inValidLogin() {
        return new Object[][]{{"Runo@03062025X"}};
    }
}
