package com.tii.core;

import static com.tii.utils.selenium.MobileDriverPool.triesToCreateDriver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;




import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.tii.utils.common.Config;
import com.tii.utils.selenium.DriverManager;
import com.tii.utils.selenium.DriverPool;
import com.tii.utils.selenium.HybridMobUtils;
import com.tii.utils.selenium.MobUtils;
import com.tii.utils.selenium.MobileDriverPool;
import com.tii.utils.selenium.WebUtils;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;


public class BaseConfiguration {
   
	
	public static WebDriver driver;
	private ITestContext context;
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseConfiguration.class);
//	private static final String APPLICATION_URL = Config.getProperty("appURL");
	private static final String APPLICATION_URL = Config.getProperty("appURLMobility");
	private static final String APPLICATION_URL_WITHOUT_INSTANCE = Config.getProperty("appURLMobilityWithoutInstance");
	public static boolean notificationBlocked;
	

	@Parameters({ "browser", "nodeURL", "deviceOrientation" })
	@BeforeClass
	public void setup(@Optional("CHROME")String browser, @Optional("")String nodeURL, @Optional("LANDSCAPE") String deviceOrientation, ITestContext ctx) {
		driver = null;
		notificationBlocked=false;
		triesToCreateDriver = 1;
		try {
			if (browser.contains("mobile")) {
				driver = (AndroidDriver<WebElement>)DriverPool.getDriver(browser, nodeURL);
				WebUtils.setDriver(driver);
				MobUtils.setDeviceOrientation(deviceOrientation);
				WebUtils.navigateToURL(APPLICATION_URL);
			} else if (browser.contains("android_hybridapp")) {
				driver = (AndroidDriver<WebElement>)MobileDriverPool
						.getAndroidHybridAppDriver();
				HybridMobUtils.setDriver(driver);
				HybridMobUtils.setDeviceOrientation("PORTRAIT");
//				HOtherActions.toggle_wifi();
			}else if (browser.contains("gigafox_chrome_android")) {
				driver = (AndroidDriver<WebElement>)MobileDriverPool
						.getGigafoxAndroidChromeDriver();
				WebUtils.setDriver(driver);
				MobUtils.setDeviceOrientation("PORTRAIT");
				WebUtils.navigateToURL(APPLICATION_URL);
			} else if (browser.contains("gigafox_safari_ios")) {
				driver = (IOSDriver<WebElement>) MobileDriverPool.getGigafoxSafariDriver();
				WebUtils.setDriver(driver);
				HybridMobUtils.setDriver(driver);
				//IOtherActions.navigateToUrl(APPLICATION_URL_WITHOUT_INSTANCE);
			} else if (browser.contains("ios_hybridapp")) {
				driver = (IOSDriver<WebElement>) MobileDriverPool.getIOSDriver(browser);
				HybridMobUtils.setDriver(driver);
			} else {
				driver = DriverPool.getDriver(browser, nodeURL);
				WebUtils.setDriver(driver);
				driver.manage().window().maximize();
//				driver.manage().window().setSize(new Dimension(1366,768));
				WebUtils.navigateToURL(APPLICATION_URL);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured {} ", e.getMessage());
			throw new WebDriverException(e.getMessage());
		}

		this.context = DriverManager.setupContext(driver, ctx, browser, nodeURL);
	}
	
}
