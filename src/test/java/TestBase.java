import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import core_configuration.constants.Browser;
import core_configuration.env_config.Environment;
import core_configuration.testutils.BrowserActionsUtility;
import core_configuration.testutils.JSONUtility;
import core_configuration.testutils.LoggerUtility;
import org.testng.ITestResult;

public class TestBase {
    protected WebDriver driver;
    protected String baseUrl;
    protected int maxAttempts;
    //Logger logger = LoggerUtility.getLogger(this.getClass());


    @Parameters({"browser", "QA", "isHeadless"})
    @BeforeMethod(description = "Loading the test base")
    public void setup(@Optional("chrome") String browser, @Optional("QA") String env,
                      @Optional("false") boolean isHeadless, ITestResult result) {
        Environment environment = JSONUtility.readEnvironmentConfig(env);
        if (environment != null) {
            baseUrl = environment.getUrl();
            maxAttempts = environment.getMax_attempts();
        }
        driver = new BrowserActionsUtility(Browser.valueOf(browser.toUpperCase()), isHeadless).driver();
        driver.get(baseUrl);

    }

    @AfterMethod()
    public void tearDown() {
        if (driver != null) {
            BrowserActionsUtility.quit();
        }
    }
}
