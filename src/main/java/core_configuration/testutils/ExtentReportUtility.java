package core_configuration.testutils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentReportUtility {

    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    public static void setupSparkReporter(String reportName) {

        String reportPath = System.getProperty("user.dir") + File.separator + reportName;
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(reportPath);
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);

        extentSparkReporter.config().setDocumentTitle("Automation Test Report");
        extentSparkReporter.config().setReportName("Selenium Test Results with ExtentReports");
    }

    public static void createExtentTest(String testName) {
        ExtentTest test = extentReports.createTest(testName);
        extentTest.set(test);  // Use ThreadLocal to store the ExtentTest instance for the current thread
    }

    // Retrieve the ExtentTest instance for the current thread
    public static ExtentTest getTest() {
        ExtentTest test = extentTest.get();
        if (test == null) {
            throw new RuntimeException("ExtentTest not initialized for this thread.");
        }
        return test;
    }

    // Flush the reports to the final output file
    public static void flushReport() {
        if (extentReports != null) {
            extentReports.flush();
        } else {
            throw new IllegalStateException("ExtentReports not initialized.");
        }
    }
}
