package com.tii.tests.web;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.tii.reporter.ExtentReporter;
import com.tii.utils.common.CustomAssertion;
import com.tii.utils.selenium.WebUtils;
import com.tii.actions.web.LoginActions;
import com.tii.core.BaseConfiguration;
import com.tii.core.DataRecorder;

public class NonAdminTest extends BaseConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(NonAdminTest.class);
	private static String expectedHomePageTitle="Intelligent Insights";
	
	@Test(testName = "NON_ADMIN_TEST_100")
	public void non_admin_login() throws InterruptedException {
		
		Map<String, String> profile = DataRecorder.getProfileData("Non_ADMIN");
		WebUtils.refreshBrowser();	
		LoginActions.login(profile);
		CustomAssertion.assertEquals(WebUtils.getPageTitle(), expectedHomePageTitle);
		ExtentReporter.pass("User logged in successfully");
	}
}