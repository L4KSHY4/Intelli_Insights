package com.tii.actions.web;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tii.reporter.ExtentReporter;
import com.tii.utils.common.CustomAssertion;
import com.tii.utils.selenium.WebUtils;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class AnalyticsAndReportingActions extends WebUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsAndReportingActions.class);	

	public static void clickAnalyticsAndReportingTab() {

		WebUtils.waitForElementVisibility("home_page_analytics_&_reporting_tab_xpath", 10);
		WebUtils.jsClick("home_page_analytics_&_reporting_tab_xpath");
		ExtentReporter.info("Clicked on Analytics & Reporting tab");
	}

	public static void verifyAnalyticsAndReportingSubmenus() {

		WebUtils.isElementDisplayed(WebUtils.getElement("analytics_&_reporting_submenu_program_reporting_xpath"));
		ExtentReporter.pass("Program Reporting option is displayed");
		WebUtils.isElementDisplayed(WebUtils.getElement("analytics_&_reporting_submenu_business_reporting_xpath"));
		ExtentReporter.pass("Business Reporting option is displayed");
		WebUtils.isElementDisplayed(WebUtils.getElement("analytics_&_reporting_submenu_support_xpath"));
		ExtentReporter.pass("Support option is displayed");
	}

	public static void graphValidationBotPerformance() {

		WebUtils.waitForElementVisibility("bot_transaction_status_graph_xpath", 10);
		if(WebUtils.isElementDisplayed(WebUtils.getElement("bot_transaction_status_graph_xpath"))) {
			ExtentReporter.pass("Bot Performance Graph is displayed");
			WebUtils.waitForElementVisibility("bot_transaction_status_graph_complete_legend_all_years_data_xpath", 10);
			int totalCompleteBot=WebUtils.getElements("bot_transaction_status_graph_complete_legend_all_years_data_xpath").size();
			int totalCompleteBotWithException=WebUtils.getElements("bot_transaction_status_graph_completeWithExceptions_legend_all_years_data_xpath").size();
			int totalIncompleteBot=WebUtils.getElements("bot_transaction_status_graph_incomplete_legend_all_years_data_xpath").size();
			if(totalCompleteBot>0||totalCompleteBotWithException>0||totalIncompleteBot>0) {
				ExtentReporter.pass("Data displayed on graph");
			}else {
				ExtentReporter.fail("Data not displayed on graph");
			}
		}else {
			ExtentReporter.fail("Bot Performance graph not displayed ");
		}
	}

	public static void downloadReports(String threeLineBar, String exportAsFile  ) {

		WebUtils.click(threeLineBar);
		ExtentReporter.info("Clicked on three line bar to download report");
		WebUtils.mouseHover(WebUtils.getElement(exportAsFile));
		WebUtils.click(exportAsFile);
		ExtentReporter.info("Clicked on Export option");
	}



	public static void downloadAndValidateReport(String threeLineBarLocator,String downloadFileLocator,String fileNameString, String fileType) {

		WebUtils.waitForElementPresence(threeLineBarLocator, 10);
		String chromeDownloadFolderLocation = System.getProperty("user.home") + "\\Downloads\\";
		final String fileName= fileNameString;
		LOGGER.info("Generated file name is :" + fileName);
		// deleting if any previous file is present with same name
		Arrays.stream(new File(chromeDownloadFolderLocation).listFiles())
		.filter(file -> file.getName().contains(fileName)).forEach(File::delete);
		WebUtils.click(threeLineBarLocator);
		ExtentReporter.info("Clicked on three line bar to download report");
		WebUtils.click(downloadFileLocator);
		ExtentReporter.info("Clicked on Export button");
		WebUtils.sleep(2);
		WebUtils.waitForJavascriptLoading(20);
		boolean isFilePresent = Arrays.stream(new File(chromeDownloadFolderLocation).listFiles())
				.filter(file -> file.getName().contains(fileName)).findAny().isPresent();		
		CustomAssertion.assertTrue(isFilePresent, "File downloaded successfully in " +fileType+" format");

	}

	public static String getDownloadsPath() {

		String downloadPath = System.getProperty("user.home");
		File file = new File(downloadPath + "/Downloads/");
		ExtentReporter.info("Saved downloaded file: "+file.getAbsolutePath());
		return file.getAbsolutePath();
	}

	public static void selectLegend(String legendlocator, String legendGraphDataLocator, String legendName) {

		WebUtils.waitForElementVisibility(legendlocator, 10);
		WebUtils.click(WebUtils.scrollingToElementofAPage(WebUtils.getElement(legendlocator)));
		ExtentReporter.info(legendName+" Legend selected");		
		WebUtils.waitForElementInVisibility(legendGraphDataLocator, 10);
		CustomAssertion.assertFalse(WebUtils.isElementDisplayed(WebUtils.getElement(legendGraphDataLocator)),"Graph filtered successfully");

	}

	public static void graphValidationBotReports() {
		WebUtils.waitForElementVisibility("bot_report_graph_xpath", 10);
		if(WebUtils.isElementDisplayed(WebUtils.getElement("bot_report_graph_xpath"))) {
			ExtentReporter.pass("BOT Transactions Threshold graph is displayed");			
			WebUtils.waitForElementVisibility("bot_report_maximum_graph_data_xpath", 10);
			int maximumBotTransaction=WebUtils.getElements("bot_report_maximum_graph_data_xpath").size();
			int minimumBotTransaction=WebUtils.getElements("bot_report_minimum_graph_data_xpath").size();
			int averageBotTransaction=WebUtils.getElements("bot_report_average_graph_data_xpath").size();
			int botTransaction=WebUtils.getElements("bot_report_bot_transaction_data_graph_xpath").size();
			if(maximumBotTransaction>0||minimumBotTransaction>0||averageBotTransaction>0||botTransaction>0) {
				ExtentReporter.pass("Data displayed on graph");
			}else {
				ExtentReporter.fail("Data not displayed on graph");
			}
		}else {
			ExtentReporter.fail("BOT Transactions Threshold graph not displayed ");
		}
	}

	public static void clickFilter(String filterLocator) {

		WebUtils.waitForElementPresence(filterLocator, 10);
		WebUtils.jsClick("bot_reports_filter_button_xpath");
		//WebUtils.click(WebUtils.scrollingToElementofAPage(WebUtils.getElement(filterLocator)));
		ExtentReporter.info("Filter Button is clicked");
	}

	public static void clickDropDown(String dropDownLocator) {
		WebUtils.click(dropDownLocator);
		WebUtils.isElementEnabled(WebUtils.getElement(dropDownLocator));
		ExtentReporter.pass("Drop down is enabled");
	}

	public static void selectBotNameUsingFilter(String botNameToSelect) {

		WebUtils.click(WebUtils.getDisplayedWebElement(WebUtils.getLocatorValue("bot_reports_filter_bot_name_xpath").replaceAll("botName",botNameToSelect) + "_xpath", 10));
		ExtentReporter.info("Bot name selected: "+botNameToSelect);

	}

	public static void applyFilterAndValidate(String okButtonLocator) {

		WebUtils.click(okButtonLocator);
		WebUtils.waitForElementVisibility("bot_reports_bot_transactions_threshold_graph_xpath",20);
		if(WebUtils.isElementDisplayed(WebUtils.getElement("bot_reports_bot_transactions_threshold_graph_xpath"))){
			ExtentReporter.pass("filter applied");
		} else {
			ExtentReporter.fail("filter not applied");
		}
	}

	public static void resetFilterAndValidate(String resetButtonlocator) {

		WebUtils.jsClick(resetButtonlocator);
		WebUtils.waitForElementVisibility("bot_reports_bot_transactions_threshold_graph_xpath",10);
		if(WebUtils.isElementDisplayed(WebUtils.getElement("bot_reports_bot_transactions_threshold_graph_xpath"))){
			ExtentReporter.pass("Successfully reset the Filter");
		} else {
			ExtentReporter.fail("Unable to reset Filter");
		}
	}

	public static void getAllBotsName(String downArrowLocator,String listOfbotNameLocator) {
		WebUtils.click(downArrowLocator);
		List<WebElement> allbots=WebUtils.getElements(listOfbotNameLocator);
		for (WebElement webElement : allbots) {
			String name = webElement.getText();
			System.out.println(name);
		}
	}
		public static void clickNewIniative() {

			WebUtils.waitForElementVisibility("home_page_New_Initiative_tab_xpath", 10);
			WebUtils.jsClick("home_page_New_Initiative_tab_xpath");
			ExtentReporter.info("Clicked on New Initiative tab");
		}
		
		
public static void ideaSubmissionInputForm() {

	WebUtils.waitForElementVisibility("home_page_New_Initiative_tab_xpath", 10);
	WebUtils.jsClick("home_page_New_Initiative_tab_xpath");
	ExtentReporter.info("Clicked on New Initiative tab");
}

/*public static void jdbcConnections(String[] args) {
	Connection conn = null;
    try {
        String dbURL = "jdbc:postgresql:IntelliDB?user=postgres&password=Telus@123";
        conn = DriverManager.getConnection(dbURL);
        
        // Retrieve data from an XPath
        WebElement element = WebUtils.getElement("home_page_Bot_Card_xpath");
        String botId = element.getText();
        
        // Store the data in the Bot_ID column of the database
        Statement stmt = conn.createStatement();
        String sql = "INSERT INTO Bots (Bot_ID) VALUES ('" + botId + "')";
        stmt.executeUpdate(sql);
        System.out.println("Data inserted successfully");
        
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    }*/

/*
 * public static void createOrRetrieveTableAndPrintData(Connection conn) throws SQLException {
 * 
 */
    /*DatabaseMetaData meta = conn.getMetaData();
    ResultSet rsTables = meta.getTables(null, null, "intelligent", new String[] {"TABLE"});
    if (rsTables.next()) {
        System.out.println("Table already exists");
    } else {
        Statement stmt = conn.createStatement();
        String sql = "CREATE TABLE intelligent (Bot_ID INTEGER, Name VARCHAR(255), Email VARCHAR(255))";
        stmt.executeUpdate(sql);
        System.out.println("Table created successfully");
    }
    
    Statement selectStmt = conn.createStatement();
    ResultSet rs = selectStmt.executeQuery("SELECT * FROM intelligent");
    while (rs.next()) {
        int botID = rs.getInt("Bot_ID");
        String name = rs.getString("Name");
        String email = rs.getString("Email");
        System.out.println("Bot_ID: " + botID + ", Name: " + name + ", Email: " + email);
    }*/
public static void jdbcC(String[] args) {
    // create a connection to the database
    Connection conn = null;
    try {
        String dbURL = "jdbc:postgresql:IntelliDB?user=postgres&password=Telus@123";
        conn = DriverManager.getConnection(dbURL);
        if (conn != null) {
            System.out.println("Connected to database");
        }

     // check if table already exists
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet rsTables = meta.getTables(null, null, "intelligent", new String[] {"TABLE"});
        if (rsTables.next()) {
            System.out.println("Table already exists");
        } else {
            // create a new table with a column named Bot_ID
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE intelligent (Bot_ID INTEGER)";
            stmt.executeUpdate(sql);
            System.out.println("Table created successfully");
        }

     // read data from the table
        Statement selectStmt = conn.createStatement();
        ResultSet rs = selectStmt.executeQuery("SELECT * FROM intelligent");
        while (rs.next()) {
            int botID = rs.getInt("Bot_ID");
            String name = rs.getString("Name");
            String email = rs.getString("Email");
            System.out.println("Bot_ID: " + botID + ", Name: " + name + ", Email: " + email);
        }

        /*// create a new table with a column named Bot_ID
        Statement stmt = conn.createStatement();
        String sql = "CREATE TABLE intelligent (Bot_ID INTEGER)";
        stmt.executeUpdate(sql);
        System.out.println("Table created successfully");*/
        

    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }







}
}




