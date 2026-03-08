package com.framework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import java.util.List;

public class TestDataProvider {

    private static final Logger logger = LogManager.getLogger(TestDataProvider.class);
    private static final String TEST_DATA_PATH = "testdata/AmazonTestData.xlsx";

    @DataProvider(name = "amazonSearchData")
    public static Object[][] getAmazonSearchData() {
        List<String[]> data = ExcelUtils.getTestData(TEST_DATA_PATH, "SearchData");
        Object[][] testData = new Object[data.size()][5];

        for (int i = 0; i < data.size(); i++) {
            testData[i][0] = data.get(i)[0]; // SearchKeyword
            testData[i][1] = data.get(i)[1]; // Category
            testData[i][2] = data.get(i)[2]; // ExpectedResult (pass/fail)
            testData[i][3] = data.get(i)[3]; // MinResults
            testData[i][4] = data.get(i)[4]; // Description
        }

        logger.info("Loaded " + testData.length + " Amazon search test cases");
        return testData;
    }
}
