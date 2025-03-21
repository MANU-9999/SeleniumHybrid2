package core_configuration.testutils;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerUtility implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportUtility.createExtentTest(result.getMethod().getMethodName());
        ExtentReportUtility.getTest().info("Test started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportUtility.getTest().log(Status.PASS, result.getMethod().getMethodName() + " PASSED");
        String screenshotPath = BrowserActionsUtility.captureScreenshot(result.getMethod().getMethodName());
        ExtentReportUtility.getTest().addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportUtility.getTest().log(Status.FAIL, result.getMethod().getMethodName() + " FAILED");
        String screenshotPath = BrowserActionsUtility.captureScreenshot(result.getMethod().getMethodName());
        ExtentReportUtility.getTest().addScreenCaptureFromPath(screenshotPath);
        ExtentReportUtility.getTest().fail(result.getThrowable());
    }
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportUtility.getTest().log(Status.SKIP, result.getMethod().getMethodName() + " SKIPPED");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
        ExtentReportUtility.setupSparkReporter("Report_"+context.getName()+"_.html");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportUtility.flushReport();
    }

}
