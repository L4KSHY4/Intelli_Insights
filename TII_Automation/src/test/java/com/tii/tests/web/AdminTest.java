package com.tii.tests.web;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.tii.reporter.ExtentReporter;
import com.tii.utils.common.CustomAssertion;
import com.tii.utils.selenium.WebUtils;
import com.tii.actions.web.AnalyticsAndReportingActions;
import com.tii.actions.web.JdbcConnection;
import com.tii.actions.web.LoginActions;
import com.tii.core.BaseConfiguration;
import com.tii.core.DataRecorder;

public class AdminTest extends BaseConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminTest.class);
	private static String expectedGraphHeadingOnClickBotPerformance="BOT Transactions Status";
	private static String expectedFilterHeading="Filter";
	private static String fileNameBotPerformance="BOT Transactions By Status";
	private static String fileNameBotReport="BOT forecasted transactions By Month";
	private static String fileFormatCsv="csv";
	private static String botPerformanceCompleteLegendName="Complete";
	private static String botReportMaximumLegendName="Maximum";
	private static String expectedGraphHeadingOnClickBotReport="BOT Transactions Threshold";

	/*@Test(testName = "ADMIN_TEST_101")
    public void accessBotPerformanceYearly() throws InterruptedException {

        Map<String, String> profile = DataRecorder.getProfileData("ADMIN");
        WebUtils.refreshBrowser();  
        LoginActions.login(profile);
        WebUtils.waitForElementVisibility("home_page_title_xpath", 10);
        WebUtils.isElementSelected(WebUtils.getElement("home_page_title_xpath"));
        ExtentReporter.pass("User landed on Home page successfully");
        AnalyticsAndReportingActions.clickAnalyticsAndReportingTab();
        AnalyticsAndReportingActions.verifyAnalyticsAndReportingSubmenus();
        WebUtils.jsClick("analytics_&_reporting_submenu_bot_performance_xpath");
        ExtentReporter.info("Clicked on Bot Performance option");
        WebUtils.isElementSelected(WebUtils.getElement("bot_performance_tab_xpath"));
        ExtentReporter.pass("User landed on Bot Performance page successfully");
        WebUtils.isElementSelected(WebUtils.getElement("bot_performance_yearly_tab_xpath"));
        ExtentReporter.pass("Yearly tab is selected");
        CustomAssertion.assertEquals(WebUtils.getText("bot_performance_label_xpath"),expectedGraphHeadingOnClickBotPerformance);
        ExtentReporter.pass("BOT Transactions status heading is visible");
        WebUtils.waitForJavascriptLoading(30);
        AnalyticsAndReportingActions.graphValidationBotPerformance();
        WebUtils.refreshBrowser();
        AnalyticsAndReportingActions.downloadAndValidateReport("bot_performance_three_line_bar_icon_xpath","bot_performance_export_as_csv_icon_xpath",fileNameBotPerformance,fileFormatCsv);    
        AnalyticsAndReportingActions.selectLegend("bot_performance_complete_legend_xpath","bot_transaction_status_graph_complete_legend_all_years_data_xpath",botPerformanceCompleteLegendName);*/


	@Test(testName = "ADMIN_TEST_101")
	public void accessBotPerformanceYearly() throws InterruptedException {

		Map<String, String> profile = DataRecorder.getProfileData("ADMIN");
		WebUtils.refreshBrowser();  
		LoginActions.login(profile);
		WebUtils.waitForElementVisibility("home_page_title_xpath", 10);
		WebUtils.isElementSelected(WebUtils.getElement("home_page_title_xpath"));
		ExtentReporter.pass("User landed on Home page successfully");
		AnalyticsAndReportingActions.clickNewIniative();
		
	}

	@Test(testName = "ADMIN_TEST_102")
	public void accessBotReports() throws InterruptedException {

		Map<String, String> testData = DataRecorder.getTestData("ADMIN_TEST_102");
		Map<String, String> profile = DataRecorder.getProfileData("ADMIN");
		String botName=testData.get("BotName");
		WebUtils.refreshBrowser();    
		LoginActions.login(profile);
		WebUtils.waitForElementVisibility("home_page_title_xpath", 10);
		WebUtils.isElementSelected(WebUtils.getElement("home_page_title_xpath"));
		ExtentReporter.pass("User landed on Home page");        
		AnalyticsAndReportingActions.clickAnalyticsAndReportingTab();
		AnalyticsAndReportingActions.verifyAnalyticsAndReportingSubmenus();
		WebUtils.jsClick("programe_reporting_submenu_bot_reports_xpath");
		ExtentReporter.info("Clicked on Bot Report option");
		WebUtils.jsClick("bot_reports_tab_xpath");
		WebUtils.isElementSelected(WebUtils.getElement("bot_reports_tab_xpath"));
		ExtentReporter.pass("User landed on BOT Report page");
		CustomAssertion.assertEquals(WebUtils.getText("bot_report_lebel_xpath"),expectedGraphHeadingOnClickBotReport);
		ExtentReporter.pass("BOT Transactions Threshold heading is visible");
		AnalyticsAndReportingActions.graphValidationBotReports();
		AnalyticsAndReportingActions.downloadAndValidateReport("bot_report_three_line_bar_icon_xpath", "bot_report_export_as_csv_icon_xpath",fileNameBotReport,fileFormatCsv);
		AnalyticsAndReportingActions.selectLegend("bot_report_maximum_graph_legend_xpath", "bot_report_maximum_graph_data_xpath",botReportMaximumLegendName);
		AnalyticsAndReportingActions.clickFilter("bot_reports_filter_button_xpath");
		WebUtils.waitForElementVisibility("bot_reports_filter_heading_xpath",10);
		CustomAssertion.assertEquals(WebUtils.getText("bot_reports_filter_heading_xpath"), expectedFilterHeading);
		ExtentReporter.pass("User landed on Filter Pop up page");		
		AnalyticsAndReportingActions.clickDropDown("bot_reports_bot_name_filter_down_arrow_xpath");
		AnalyticsAndReportingActions.selectBotNameUsingFilter(botName);
		AnalyticsAndReportingActions.applyFilterAndValidate("bot_reports_filter_ok_button_xpath");
		AnalyticsAndReportingActions.clickFilter("bot_reports_filter_button_xpath");
		WebUtils.waitForElementVisibility("bot_reports_filter_heading_xpath",10);
		CustomAssertion.assertEquals(WebUtils.getText("bot_reports_filter_heading_xpath"), expectedFilterHeading);
		ExtentReporter.pass("User landed on Filter Pop up page");
		AnalyticsAndReportingActions.resetFilterAndValidate("bot_reports_filter_reset_button_xpath");
	}
}