package core.testutils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentReportUtility {

    // ThreadLocal to ensure each test has its own instance of ExtentTest
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static ExtentReports extentReports;

    // Setup the SparkReporter and attach it to the ExtentReports instance
    public static void setupSparkReporter(String reportName) {
        // Ensure that ExtentReports is initialized only once
        if (extentReports == null) {
            String reportPath = System.getProperty("user.dir") + File.separator + reportName;

            // Create and configure the ExtentSparkReporter
            ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(reportPath);
            extentReports = new ExtentReports();
            extentReports.attachReporter(extentSparkReporter);

            extentSparkReporter.config().setDocumentTitle("Automation Test Report");
            extentSparkReporter.config().setReportName("Selenium Test Results with ExtentReports");
        }
    }

    // Create a new ExtentTest with the given test name
    public static void createExtentTest(String testName) {
        ExtentTest test = extentReports.createTest(testName);
        extentTest.set(test);  // Store the test instance for the current thread
    }

    // Get the current ExtentTest instance for the current thread
    public static ExtentTest getTest() {
        ExtentTest test = extentTest.get();
        if (test == null) {
            throw new RuntimeException("ExtentTest not initialized for this thread.");
        }
        return test;
    }

    // Flush the reports to the output file (ensure this is called once after all tests)
    public static void flushReport() {
        if (extentReports != null) {
            extentReports.flush();  // Write the report to disk
        } else {
            throw new IllegalStateException("ExtentReports not initialized.");
        }
    }
}
