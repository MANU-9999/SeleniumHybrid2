import core_configuration.pageobjects.SignUpPage;
import core_configuration.testdata.TestDataProvider;
import core_configuration.testutils.ListenerUtility;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ListenerUtility.class)
public class LoginValidationTest extends TestBase {

    @Test(dataProvider = "signUpAndValidLogin", dataProviderClass = TestDataProvider.class, enabled = true, groups = "smoke")
    public void signUpAndValidTest(String mobileNumber, String password) {
        SignUpPage.doSignUpWith(mobileNumber);
    }

    @Test(dataProvider = "inValidLogin", dataProviderClass = TestDataProvider.class, enabled = false, groups = "smoke")
    public void signUpAndInValidTest(String mobileNumber, String password) {
        SignUpPage.doSignUpWith(mobileNumber);
    }


}