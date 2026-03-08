package com.framework.tests;

import com.aventstack.extentreports.Status;
import com.framework.base.BaseTest;
import com.framework.pages.AmazonHomePage;
import com.framework.utils.ExtentReportManager;
import com.framework.utils.ScreenshotUtils;
import com.framework.utils.TestDataProvider;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class AmazonSearchTest extends BaseTest {

    private AmazonHomePage amazonPage;

    @BeforeSuite
    public void setUpReport() {
        ExtentReportManager.getInstance();
        logger.info("=== Amazon Data-Driven Test Suite Started ===");
    }

    @BeforeMethod
    @Parameters({"browser"})
    @Override
    public void setUp(@Optional("chrome") String browser) {
        super.setUp(browser);
        amazonPage = new AmazonHomePage(getDriver());
        amazonPage.navigateToAmazon();
    }

    @Test(dataProvider = "amazonSearchData", dataProviderClass = TestDataProvider.class,
          description = "Verify Amazon search returns results for various product keywords")
    public void testAmazonProductSearch(String keyword, String category,
                                        String expectedResult, String minResults,
                                        String description) {

        // Create test in ExtentReport
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest(
                "Search: " + keyword,
                description + " | Category: " + category
            )
        );

        ExtentReportManager.getTest().log(Status.INFO,
            "Test Data → Keyword: " + keyword +
            " | Category: " + category +
            " | Expected: " + expectedResult);

        try {
            // Step 1: Verify Amazon loaded
            Assert.assertTrue(amazonPage.isSearchBoxPresent(),
                "Amazon should be accessible");
            ExtentReportManager.getTest().log(Status.PASS, "Amazon homepage loaded successfully");

            // Step 2: Search for product directly via URL (most reliable)
            amazonPage.searchForProduct(keyword);
            ExtentReportManager.getTest().log(Status.INFO, "Searched for: " + keyword);

            // Step 3: Check results based on expected outcome
            if (expectedResult.equalsIgnoreCase("pass")) {
                boolean resultsDisplayed = amazonPage.isSearchResultsDisplayed();
                Assert.assertTrue(resultsDisplayed,
                    "Search page should load for keyword: " + keyword);

                ExtentReportManager.getTest().log(Status.PASS,
                    "Search successful for: " + keyword);

                String firstProduct = amazonPage.getFirstProductTitle();
                if (!firstProduct.isEmpty()) {
                    ExtentReportManager.getTest().log(Status.INFO,
                        "First result: " + firstProduct);
                }

            } else {
                // For 'fail' expected — just verify page didn't crash
                ExtentReportManager.getTest().log(Status.INFO,
                    "Testing edge case search: " + keyword);
                Assert.assertNotNull(amazonPage.getCurrentUrl(),
                    "Page URL should not be null");
                ExtentReportManager.getTest().log(Status.PASS,
                    "Edge case handled correctly for: " + keyword);
            }

        } catch (AssertionError | Exception e) {
            String screenshot = ScreenshotUtils.captureScreenshot("AmazonSearch_" + keyword.replaceAll("\\s+", "_"));
            if (screenshot != null) {
                try {
                    ExtentReportManager.getTest().addScreenCaptureFromPath(screenshot);
                } catch (Exception ex) {
                    logger.error("Could not attach screenshot: " + ex.getMessage());
                }
            }
            ExtentReportManager.getTest().log(Status.FAIL, "Test FAILED: " + e.getMessage());
            logger.error("Test failed for keyword: " + keyword + " | Error: " + e.getMessage(), e);
            throw e;
        }
    }

    @AfterMethod
    public void logTestResult(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            logger.info("TEST PASSED: " + result.getName());
        } else if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("TEST FAILED: " + result.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.warn("TEST SKIPPED: " + result.getName());
            if (ExtentReportManager.getTest() != null) {
                ExtentReportManager.getTest().log(Status.SKIP, "Test skipped");
            }
        }
        super.tearDown();
    }

    @AfterSuite
    public void tearDownReport() {
        ExtentReportManager.flushReports();
        logger.info("=== Amazon Data-Driven Test Suite Completed ===");
        logger.info("Report saved to: " + ExtentReportManager.getReportPath());
    }
}
