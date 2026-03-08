package com.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);
    private static String reportPath;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            reportPath = "reports/AmazonTestReport_" + timestamp + ".html";

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("Amazon Data-Driven Test Report");
            sparkReporter.config().setReportName("Amazon Search Test Automation");
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Project", "Amazon Data-Driven Framework");
            extent.setSystemInfo("Framework", "Selenium + TestNG + Apache POI");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Environment", "Amazon India (amazon.in)");
            extent.setSystemInfo("Author", "STA Mini Project");

            logger.info("Extent report initialized: " + reportPath);
        }
        return extent;
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void setTest(ExtentTest extentTest) {
        test.set(extentTest);
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            logger.info("Report flushed to: " + reportPath);
        }
    }

    public static String getReportPath() {
        return reportPath;
    }
}
