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

public class NewInitiativeDemo extends WebUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsAndReportingActions.class);	

	public static void clickNewIniative() {

		WebUtils.waitForElementVisibility("home_page_New_Initiative_tab_xpath", 10);
		WebUtils.jsClick("home_page_New_Initiative_tab_xpath");
		ExtentReporter.info("Clicked on New Initiative tab");
	}
}