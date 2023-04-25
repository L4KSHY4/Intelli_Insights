package com.tii.actions.web;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import com.tii.reporter.ExtentReporter;
import com.tii.utils.common.Config;
import com.tii.utils.selenium.WebUtils;

public class LoginActions extends WebUtils {

	private static final String APPLICATION_URL_NON_ADMIN = Config.getProperty("appURLMobilityNonAdmin");
	
	public static void login(Map<String, String> profile) {

        List<WebElement> homepageProfileDropdownButton = WebUtils.getElements("profile_dropdown_arrow_xpath");
        String profileName=profile.get("Profile");
        
        if(profileName.equals("ADMIN")) {    
            if(homepageProfileDropdownButton.size()>0) {
                WebUtils.navigateToURL("https://telus-qa-ii.itia.ai/landingpage");
            }else {
                WebUtils.enterText("web_login_page_username_textbox_xpath", profile.get("username"));
                WebUtils.enterText("web_login_page_password_textbox_xpath", profile.get("password"));
                ExtentReporter.info("Entered username and password");
                WebUtils.click("web_login_page_continue_button_xpath");
                ExtentReporter.info("Sign-in button clicked");

            }
        }else {
            if(homepageProfileDropdownButton.size()>0) {
                WebUtils.navigateToURL("https://telus-qa-ii.itia.ai/landingpage");
            }else {
                WebUtils.navigateToURL(APPLICATION_URL_NON_ADMIN);
                ExtentReporter.info("Entering username and password");
                WebUtils.enterText("web_login_page_username_textbox_xpath", profile.get("username"));
                WebUtils.click("web_login_page_username_continue_button_xpath");
                WebUtils.refreshBrowser();
                WebUtils.waitForElementVisibility("web_login_page_username2_textbox_xpath", 30);
                WebUtils.enterText("web_login_page_username2_textbox_xpath", profile.get("username"));
                WebUtils.click("web_login_page_username2_continue_button_xpath");
                WebUtils.waitForElementPresence("web_login_page_password2_textbox_xpath", 30);
                WebUtils.enterText("web_login_page_password2_textbox_xpath", profile.get("password"));
                WebUtils.click("web_login_page_password_continue_button_xpath");
                ExtentReporter.info("Sign-in button clicked");
                //WebUtils.waitForElementVisibility("profile_dropdown_arrow_xpath", 30);
                WebUtils.waitForElementPresence("profile_dropdown_arrow_xpath");
            }
        }
    }
}
