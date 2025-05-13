package core.testutils;

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
        logger.error(result.getMethod().getMethodName() + " FAILED");
        logger.error("Error Message: " + result.getThrowable().getMessage());
        ExtentReportUtility.getTest().log(Status.FAIL, result.getMethod().getMethodName() + " FAILED");
        ExtentReportUtility.getTest().log(Status.FAIL, result.getThrowable().getMessage());

        logger.info("Capturing Screenshot for the failed test: " + result.getMethod().getMethodName());
        String screenshotPath = BrowserActionsUtility.captureScreenshot(result.getMethod().getMethodName());

        if (screenshotPath != null) {
            logger.info("Attaching Screenshot to HTML Report");
            ExtentReportUtility.getTest().addScreenCaptureFromPath(screenshotPath);
        } else {
            logger.error("Screenshot capture failed.");
        }
    }

    public void onTestSkipped(ITestResult result) {
        logger.warn(result.getMethod().getMethodName() + " SKIPPED");
        ExtentReportUtility.getTest().log(Status.SKIP, result.getMethod().getMethodName() + " SKIPPED");
    }

    // Called before the test suite starts
    public void onStart(ITestContext context) {
        logger.info("Test Suite Started: " + context.getName());
        // Initialize the reporter at the start of the suite
        ExtentReportUtility.setupSparkReporter("Report.html");
    }

    // Called after the test suite finishes
    public void onFinish(ITestContext context) {
        logger.info("Test Suite Completed: " + context.getName());
        // Flush the report at the end of the suite
        ExtentReportUtility.flushReport();
    }
}
