package core_configuration.testutils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.AfterSuite;

import java.io.File;

public class ExtentReportUtility {
    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    public static void setupSparkReporter(String reportName) {

        String reportPath = System.getProperty("user.dir") + File.separator + reportName;
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(reportPath);
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        extentSparkReporter.config().setDocumentTitle("Automation Test Report");
        extentSparkReporter.config().setReportName("Selenium Test ResultsExtentReports");
    }
    public static void createExtentTest(String testName) {
        ExtentTest test = extentReports.createTest(testName);
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void flushReport() {
        extentReports.flush();
    }
}
