package com.tii.utils.selenium;

import static com.tii.utils.common.Constants.LOG_DESIGN;
import static org.testng.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tii.utils.common.Config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;

/**
 * It contains pool of web drivers for mobile devices.
 *
 */
public class MobileDriverPool {

	private static final Logger LOGGER = LoggerFactory.getLogger(MobileDriverPool.class);
	public static int triesToCreateDriver = 1;
	private static AppiumDriver<WebElement> driver = null;
	public static final String gigafoxHost = Config.getProperty("gigafoxURL");
	private static final String serviceUrl = "http://" + gigafoxHost + "/Appium";

	/**
	 * It will return driver for mobile chrome browser.
	 * 
	 * @param nodeURL
	 * @return driver for mobile chrome browser.
	 * @throws MalformedURLException
	 */
	public static AndroidDriver<WebElement> getAndroidChromeDriver(String nodeURL) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", "chrome");
		capabilities.setCapability("deviceName", Config.getProperty(String.valueOf("deviceName")));
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main");
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("resetKeyboard", true);
		capabilities.setCapability("newCommandTimeout", 60 * 5); // command timeout set to 5 minutes
		LOGGER.info(LOG_DESIGN + "Launching android chrome driver with capabilities : {}", capabilities);

		return new AndroidDriver<WebElement>(new URL(nodeURL), capabilities);
	}
	
	/**
	 * It will return driver for gigafox mobile chrome browser.
	 * 
	 * @param nodeURL
	 * @return driver for mobile chrome browser.
	 * @throws MalformedURLException
	 */
	public static AndroidDriver<WebElement> getGigafoxAndroidChromeDriver() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		capabilities.setCapability("browserName", "Chrome");
	    capabilities.setCapability("gigafox:automationName", "UiAutomator2");
	    capabilities.setCapability("gigafox:username", Config.getProperty(String.valueOf("gigafoxUser")));
		capabilities.setCapability("gigafox:apiKey", Config.getProperty(String.valueOf("gigafoxApiKey")));
		capabilities.setCapability("gigafox:device", Config.getProperty(String.valueOf("gigafoxDeviceID")));
		capabilities.setCapability("gigafox:skipInstall", "true");
		capabilities.setCapability("noReset", "true"); 
		capabilities.setCapability("fullReset", "false");
		capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS,true);
		capabilities.setCapability("newCommandTimeout", 60 * 5);
		// command timeout set to 5 minutes
		LOGGER.info(LOG_DESIGN + "Launching android chrome driver on gigafox device with capabilities : {}", capabilities);

		return new AndroidDriver<WebElement>(new URL(serviceUrl), capabilities);
	}
	
	/**
	 * It will return driver for Safari browser for device on gigafox.
	 * 
	 * @param nodeURL
	 * @return driver for mobile ios safari browser.
	 * @throws MalformedURLException
	 */
	public static IOSDriver<WebElement> getGigafoxSafariDriver() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		capabilities.setCapability("browserName", "Safari");
	    capabilities.setCapability("gigafox:automationName", "XCUITest");
	    capabilities.setCapability("gigafox:username", Config.getProperty(String.valueOf("gigafoxUser")));
		capabilities.setCapability("gigafox:apiKey", Config.getProperty(String.valueOf("gigafoxApiKey")));
		capabilities.setCapability("gigafox:device", Config.getProperty(String.valueOf("gigafoxDeviceIDIphone")));
		capabilities.setCapability("gigafox:skipInstall", "true");
		capabilities.setCapability("autoAcceptAlerts", "true");
		capabilities.setCapability("noReset", "true"); 
		capabilities.setCapability("fullReset", "false");
		capabilities.setCapability("newCommandTimeout", 60 * 5);
		LOGGER.info(LOG_DESIGN + "Launching IOS driver for Safari Browser with capabilities : {}", capabilities);
		
		return new IOSDriver<WebElement>(new URL(serviceUrl), capabilities);
	}
	
	/**
	 * It will set Android Driver for Android native-app
	 * 
	 * @param browser
	 *            browser name
	 * @return AndroidDriver corresponding to the given browser value
	 */
	public static void setAndroidHybridAppDriver() {
		DesiredCapabilities capabilities;
		URL gigafoxServerURL = null;

		if (triesToCreateDriver <= 3) {
			LOGGER.info(LOG_DESIGN + "Creating Android driver.");
//			System.out.println("try " + triesToCreateDriver);

			if (driver == null) {
				capabilities = new DesiredCapabilities();
				
				capabilities.setCapability("gigafox:username", Config.getProperty(String.valueOf("gigafoxUser")));
				capabilities.setCapability("gigafox:apiKey", Config.getProperty(String.valueOf("gigafoxApiKey")));
				capabilities.setCapability("gigafox:device", Config.getProperty(String.valueOf("gigafoxDeviceID")));
				capabilities.setCapability("gigafox:skipInstall", "true");
				capabilities.setCapability("appPackage", Config.getProperty(String.valueOf("appPackageName")));
				capabilities.setCapability("appActivity", Config.getProperty(String.valueOf("appActivity")));
				capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS,true);
				try {
					// The ip address is fixed
					gigafoxServerURL = new URL(serviceUrl);
				} catch (IOException e) {
					fail(e.getMessage());
				} catch (Exception e) {
					fail(e.getMessage());
				}
				try {
					Thread.sleep(1000);
					driver = new AndroidDriver<WebElement>(gigafoxServerURL, capabilities) {
					};
					Thread.sleep(1000);

				} catch (SessionNotCreatedException e) {
					LOGGER.info(LOG_DESIGN + e.getMessage());
				} catch (UnreachableBrowserException e) {
					LOGGER.info(LOG_DESIGN + e.getMessage());
				} catch (Exception e) {
					LOGGER.info(LOG_DESIGN + e.getMessage());
				} finally {
					if (driver == null) {
						LOGGER.info(LOG_DESIGN + "Driver not created successfully.");
						triesToCreateDriver++;
						setAndroidHybridAppDriver();
					} else {
						LOGGER.info(LOG_DESIGN + "Driver created successfully.");
						LOGGER.info(LOG_DESIGN + "Sessionid = " + driver.getSessionId().toString());
						LOGGER.info(LOG_DESIGN + "Changing session setting 'ALLOW_INVISIBLE_ELEMENTS' = true");
						driver.setSetting(Setting.ALLOW_INVISIBLE_ELEMENTS, true);
					}
				}
			} else {
				LOGGER.info(LOG_DESIGN + "Driver is already defined.");
				LOGGER.info(LOG_DESIGN + "Setting Driver to null");
				HybridMobUtils.terminateHybridAppDriver();
				driver = null;
				triesToCreateDriver++;
				setAndroidHybridAppDriver();
			}
			// driver = androidDriver;
			// return androidDriver;
		} else {
			LOGGER.info(LOG_DESIGN + "Driver not created successfully.");
			fail("Unable to create driver");
		}
	}
	
	/**
	 * It will return the driver for Android Native App
	 * 
	 * @param browser
	 * @return AndroidDriver<WebElement>
	 */
	public static AndroidDriver<WebElement> getAndroidHybridAppDriver() {
		setAndroidHybridAppDriver();
		return (AndroidDriver<WebElement>) driver;
	}
	
	/**
	 * It will set IOS Driver for IOS Device
	 * 
	 * @param browser
	 *            browser name
	 * @return AndroidDriver corresponding to the given browser value
	 */
	public static void setIOSDriver(String browser) {
		DesiredCapabilities capabilities;
		URL gigafoxServerURL = null;

		if (triesToCreateDriver <= 3) {
			LOGGER.info(LOG_DESIGN + "Creating iOS driver.");
			System.out.println("try " + triesToCreateDriver);

			if (driver == null) {
				capabilities = new DesiredCapabilities();

				capabilities.setCapability("gigafox:automationName", "XCUITest");
				capabilities.setCapability("bundleId", Config.getProperty(String.valueOf("bundleId")));
				capabilities.setCapability("gigafox:username", Config.getProperty(String.valueOf("gigafoxUser")));
				capabilities.setCapability("gigafox:apiKey", Config.getProperty(String.valueOf("gigafoxApiKey")));
				capabilities.setCapability("gigafox:device", Config.getProperty(String.valueOf("gigafoxDeviceIDIphone")));
				capabilities.setCapability("gigafox:skipInstall", "true");
				capabilities.setCapability("autoAcceptAlerts", true);
				capabilities.setCapability("newCommandTimeout", 60 * 5); // command timeout set to 5 minutes

				try {
					// The ip address is fixed
					gigafoxServerURL = new URL(serviceUrl);
				} catch (IOException e) {
					fail(e.getMessage());
				} catch (Exception e) {
					fail(e.getMessage());
				}
				try {
					Thread.sleep(1000);
					driver = new IOSDriver<WebElement>(gigafoxServerURL, capabilities);
					Thread.sleep(1000);

				} catch (SessionNotCreatedException e) {
					System.out.println(e.getMessage());
				} catch (UnreachableBrowserException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				} finally {

					if (driver == null) {

						System.out.println("IOS Driver not created successfully.");
						triesToCreateDriver++;
						setIOSDriver(browser);
					} else {
						System.out.println("IOS Driver created successfully.");
						System.out.println("Sessionid = " + driver.getSessionId().toString());
						LOGGER.info(LOG_DESIGN + "Changing session setting 'ALLOW_INVISIBLE_ELEMENTS' = true");
						driver.setSetting(Setting.ALLOW_INVISIBLE_ELEMENTS, true);
					}
				}
			} else {
				System.out.println("IOS Driver is already defined.");
				driver = null;
				triesToCreateDriver++;
				System.out.println("IOS Driver not created successfully.");
				setIOSDriver(browser);
			}
		} else {
			fail("Unable to create driver");
		}
	}
	
	/**
	 * It will return the driver for iOS App.
	 * 
	 * @param browser
	 * @return IOSDriver<WebElement>
	 */
	public static IOSDriver<WebElement> getIOSDriver(String browser) {
		setIOSDriver(browser);
		return (IOSDriver<WebElement>) driver;
	}

}
