package com.tii.tests.web;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.tii.reporter.ExtentReporter;
import com.tii.utils.common.CustomAssertion;
import com.tii.utils.selenium.WebUtils;
import com.tii.actions.web.AnalyticsAndReportingActions;
import com.tii.actions.web.LoginActions;
import com.tii.core.BaseConfiguration;
import com.tii.core.DataRecorder;

public class AdminTestDemo extends BaseConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminTest.class);
	private static String expectedGraphHeadingOnClickBotPerformance="BOT Transactions Status";
	private static String expectedFilterHeading="Filter";
	private static String fileNameBotPerformance="BOT Transactions By Status";
	private static String fileNameBotReport="BOT forecasted transactions By Month";
	private static String fileFormatCsv="csv";
	private static String botPerformanceCompleteLegendName="Complete";
	private static String botReportMaximumLegendName="Maximum";
	private static String expectedGraphHeadingOnClickBotReport="BOT Transactions Threshold";
	
	@Test(testName = "ADMIN_TEST_13")
    public void accessBotPerformanceYearly() throws InterruptedException {
		
        Map<String, String> profile = DataRecorder.getProfileData("ADMIN");
        WebUtils.refreshBrowser();  
        LoginActions.login(profile);
        WebUtils.waitForElementVisibility("home_page_title_xpath", 10);
        WebUtils.isElementSelected(WebUtils.getElement("home_page_title_xpath"));
        ExtentReporter.pass("User landed on Home page");  
        AnalyticsAndReportingActions.clickNewIniative();
}
}
