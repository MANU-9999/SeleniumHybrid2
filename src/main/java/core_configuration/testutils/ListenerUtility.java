package core_configuration.testutils;

import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class ListenerUtility implements ITestListener {
    Logger logger = LoggerUtility.getLogger(this.getClass());

    public void onTestStart(ITestResult result) {
        logger.info(result.getMethod().getMethodName());
        logger.info(result.getMethod().getDescription());
        logger.info(Arrays.toString(result.getMethod().getGroups()));
        ExtentReportUtility.createExtentTest(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        logger.info(result.getMethod().getMethodName() + " " + "PASSED");
        ExtentReportUtility.getTest().log(Status.PASS, result.getMethod().getMethodName() + " " + "PASSED");
    }

    public void onTestFailure(ITestResult result) {
        logger.error(result.getMethod().getMethodName() + " " + "FAILED");
        logger.error(result.getThrowable().getMessage());
        ExtentReportUtility.getTest().log(Status.FAIL, result.getMethod().getMethodName() + " " + "FAILED");
        ExtentReportUtility.getTest().log(Status.FAIL, result.getThrowable().getMessage());

        logger.info("Capturing Screenshot for the failed tests");
        String screenshotPath = BrowserActionsUtility.captureScreenshot(result.getMethod().getMethodName());
        logger.info("Attaching the Screenshot to the HTML File");
        ExtentReportUtility.getTest().addScreenCaptureFromPath(screenshotPath);
        ExtentReportUtility.getTest().fail(result.getThrowable());
    }

    public void onTestSkipped(ITestResult result) {
        logger.warn(result.getMethod().getMethodName() + " " + "SKIPPED");
        ExtentReportUtility.getTest().log(Status.SKIP, result.getMethod().getMethodName() + " " + "SKIPPED");
    }

    public void onStart(ITestContext context) {
        logger.info("Test Suite Started");
        ExtentReportUtility.setupSparkReporter("Report.html");
    }

    public void onFinish(ITestContext context) {
        logger.info("Test Suite Completed");
        ExtentReportUtility.flushReport();
    }

}
