package com.tii.utils.selenium;

import static com.tii.utils.common.Constants.LOG_DESIGN;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tii.utils.common.Config;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class IOSMobUtils extends HybridMobUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(IOSMobUtils.class);
	public static IOSDriver<WebElement> driver;
	

	/**
	 * It will put the application to foreground if it is running in the background.
	 * 
	 */
	public static void openApp() {
		driver.resetApp();
		HashMap<String, Object> args = new HashMap<>();
	    args.put("bundleId", Config.getProperty("bundleId"));
	    driver.executeScript("mobile: activateApp", args);
	}
	
	/**
	 * It will launch the app. If the app is already running then it would be activated.
	 * 
	 */
	public static void launchApp() {
		HashMap<String, Object> args = new HashMap<>();
	    args.put("bundleId", Config.getProperty("bundleId"));
	    driver.executeScript("mobile: launchApp", args);
	}
	
	/**
	 * It will terminate the app.
	 * 
	 */
	public static void terminateApp() {
		HashMap<String, Object> args = new HashMap<>();
	    args.put("bundleId", Config.getProperty("bundleId"));
	    driver.executeScript("mobile: terminateApp", args);
	}
	
	/**
	 * It will get the state of the app. For e.g
	 * 		0: The current application state cannot be determined/is unknown
	 * 		1: The application is not running
	 * 		2: The application is running in the background and is suspended
	 * 		3: The application is running in the background and is not suspended
	 * 		4: The application is running in the foreground
	 * 
	 */
	public static int getAppState(String bundleId) {
		LOGGER.info(LOG_DESIGN + "Getting app state of an application with bundleId : [{}]", bundleId);
		
		HashMap<String, Object> args = new HashMap<>();
	    args.put("bundleId", bundleId);
	    int state = Integer.valueOf(driver.executeScript("mobile: queryAppState", args).toString());
	    return state;
	}
	
	/**
	 * This method performs a swipe operation.
	 * 
	 * @param direction
	 * 		e.g. - "up", "down"
	 */
	public static void performSwipe(String direction) {
		LOGGER.info(LOG_DESIGN + "Performing swipe operation in following direction : [{}]", direction);
		Map<String, Object> args = new HashMap<>();
		args.put("direction", direction.trim());
		driver.executeScript("mobile: swipe", args);
	}
	
	/**
	 * It will tap on an Mobile Element based on a given locator.
	 * 
	 * @param locator
	 */
	public static void jsTap(String locator) {
		try {
			Map<String, Object> args = new HashMap<>();
			MobileElement element = getMobileElement(locator);
			LOGGER.info(LOG_DESIGN + "Performing Tap using JavaScript on : [{}] : [{}]", locator, Config.getProperty(locator));
			args.put("element", element.getId());
			args.put("x", 2);
			args.put("y", 2);
			driver.executeScript("mobile: tap", args);
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while tapping : [{}]", e.getMessage());
		}
	}
	
	/**
	 * It will select the value present in PickerWheel.
	 * 
	 * @param text
	 * 			- text of the option we want to select
	 */
	public static void selectPickerWheelValue(String text) {
		try {
			MobileElement element = getMobileElement("//XCUIElementTypePickerWheel_xpath");
			LOGGER.info(LOG_DESIGN + "Selecting dropdown value : [{}] Text is :[{}]", text);
			element.sendKeys(text);
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while clicking : [{}]", e.getMessage());
		}
	}
	
	/**
	 * It will return the page source.
	 * @return
	 * 		- String
	 */
	public static String getPageSource() {
		LOGGER.info(LOG_DESIGN + "Getting Page Source.");
		Map<String, Object> args = new HashMap<>();
		args.put("format", "xml");
		return (String) driver.executeScript("mobile: source", args);
	}
	
	/**
	 * This method performs a scroll down operation till element is visible
	 * based on given locator
	 * 
	 * @param locator
	 */
	public static void scrollDownToElement(String locator) {
		LOGGER.info(LOG_DESIGN + "Scrolling down to an element using label: [{}]", locator);
//		MobileElement elmnt = getMobileElement("//XCUIElementTypeScrollView_xpath");
		MobileElement elmnt = getMobileElement("//XCUIElementTypeWebView_xpath");
		//if elmnt not visible
		if (!isElementVisible(locator)) {
			Map<String, Object> args = new HashMap<>();
			args.put("element", elmnt.getId());
			args.put("direction", "down");
			driver.executeScript("mobile: scroll", args);
			sleep(2);
			final int maxScroll = 5; // limit maximum swipes
			for (int i = 0; i < maxScroll; i++) {
				try {
					if (isElementVisible(locator))
						break;
				} catch (Exception e) {
					// ignore
				}
				driver.executeScript("mobile: scroll", args);
				sleep(3);
			}
		}
	}
	
	public static void actionsSwipeDownToElement(String locator) {
		LOGGER.info(LOG_DESIGN + "Swiping down to an element : [{}]", locator);
		//if elmnt not visible
		if (!isElementVisible(locator)) {
			swipeBottomToTop("");
			sleep(2);
			final int maxScroll = 5; // limit maximum swipes
			for (int i = 0; i < maxScroll; i++) {
				try {
					if (isElementVisible(locator))
						break;
				} catch (Exception e) {
					// ignore
				}
				swipeBottomToTop("");
				sleep(3);
			}
		}
	}
	
	/**
	 * This method performs a scroll right operation till element is visible
	 * based on given locator
	 * 
	 * @param locator
	 */
	public static void scrollRightToElement(String locator) {
		LOGGER.info(LOG_DESIGN + "Scrolling to an element using label: [{}]", locator);
		waitForElementPrsence("//XCUIElementTypeScrollView_xpath", 5);
		MobileElement elmnt = getMobileElement("//XCUIElementTypeScrollView_xpath");
		//if elmnt not visible
		if (!isElementHorizontallyVisible(locator)) {
			Map<String, Object> args = new HashMap<>();
			args.put("element", elmnt.getId());
			args.put("direction", "right");
			driver.executeScript("mobile: scroll", args);
			sleep(2);
			final int maxScroll = 5; // limit maximum swipes
			for (int i = 0; i < maxScroll; i++) {
				try {
					if (IOSMobUtils.isElementHorizontallyVisible(locator))
						break;
				} catch (Exception e) {
					// ignore
				}
				IOSMobUtils.driver.executeScript("mobile: scroll", args);
				IOSMobUtils.sleep(2);
			}
		}
	}
	
	/**
	 * This method performs a scroll Up operation till element is visible
	 * based on given locator
	 * 
	 * @param locator
	 */
	public static void scrollUpToElement(String locator) {
		LOGGER.info(LOG_DESIGN + "Scrolling down to an element using label: [{}]", locator);
		MobileElement elmnt = getMobileElement("//XCUIElementTypeScrollView_xpath");
		if (!isElementVisible(locator)) {
			Map<String, Object> args = new HashMap<>();
			args.put("element", elmnt.getId());
			args.put("direction", "up");
			driver.executeScript("mobile: scroll", args);
			sleep(2);
			final int maxScroll = 5; // limit maximum swipes
			for (int i = 0; i < maxScroll; i++) {
				try {
					if (isElementVisible(locator))
						break;
				} catch (Exception e) {
					// ignore
				}
				driver.executeScript("mobile: scroll", args);
				sleep(3);
			}
		}
	}
	
	/**
	 * It will check if the element is visible or not while scrolling vertically.
	 * 
	 * @param locator
	 * @return 
	 * 		boolean
	 */
	public static boolean isElementVisible(String locator) {
		MobileElement element = null;
		boolean isVisible = false;
		Dimension dimensions = getScreenSize();
		int height = dimensions.getHeight();
		try {
			element = (io.appium.java_client.MobileElement) driver.findElement(getByObject(locator.trim()));
			int elementY = element.getLocation().getY();
			isVisible = elementY < height;
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : {}: ", e.getMessage());
		}
		return isVisible;
	}
	
	/**
	 * It will check if the element is visible or not while scrolling horizontally.
	 * 
	 * @param locator
	 * @return 
	 * 		boolean
	 */
	public static boolean isElementHorizontallyVisible(String locator) {
		MobileElement element = null;
		boolean isVisible = false;
		Dimension dimensions = getScreenSize();
		int width = dimensions.getWidth();
		try {
			element = (io.appium.java_client.MobileElement) driver.findElement(getByObject(locator.trim()));
			int elementX = element.getLocation().getX();
			isVisible = elementX < width;
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : {}: ", e.getMessage());
		}
		return isVisible;
	}
	
	/**
	 * It will tap on the given Element.
	 * 
	 * @param element
	 */
	public static void jsTap(MobileElement element) {
		LOGGER.info(LOG_DESIGN + "Performing Tap on element: [{}] using Javascript", element);
		Map<String, Object> args = new HashMap<>();
		args.put("element", element.getId());
		args.put("x", 2);
		args.put("y", 2);
		driver.executeScript("mobile: tap", args);
	}
	
	/**
	 * This method will performs a swipe up operation till element is visible.
	 * 
	 * @param locator
	 */
	public static void swipeDownToElement(String locator) {
		LOGGER.info(LOG_DESIGN + "Performing swipe operation in following direction : [{}]", locator);
		Map<String, Object> args = new HashMap<>();
		args.put("direction", "up");
		final int MAX_SWIPES = 5; // limit maximum swipes

		for (int i = 0; i < MAX_SWIPES; i++) {
			
			try {
				if (!isElementVisible("//*[@label='" + locator + "']_xpath"))
					break;
			} catch (Exception e) {
				// ignore
			}
			driver.executeScript("mobile: swipe", args);
			sleep(2);
		}
	}
	
	/**
	 * It will double tap on an Mobile Element based on a given locator.
	 * 
	 * @param locator
	 */
	public static void doubleTap(String locator) {
		try {
			Map<String, Object> args = new HashMap<>();
			MobileElement element = getMobileElement(locator);
			LOGGER.info(LOG_DESIGN + "Performing Double Tap using JavaScript on : [{}] : [{}]", locator, Config.getProperty(locator));
			args.put("element", element.getId());
			driver.executeScript("mobile: doubleTap", args);
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while double tapping : [{}]", e.getMessage());
		}
	}
	
	/**
	 * It performs long press gesture on an Mobile Element based on a given locator.
	 * 
	 * @param locator
	 */
	public static void touchAndHold(String locator) {
		try {
			Map<String, Object> args = new HashMap<>();
			MobileElement element = getMobileElement(locator);
			LOGGER.info(LOG_DESIGN + "Performing long press gesture using JavaScript on : [{}] : [{}]", locator, Config.getProperty(locator));
			args.put("element", element.getId());
			args.put("duration", 2.0);
			driver.executeScript("mobile: touchAndHold", args);
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while long pressing : [{}]", e.getMessage());
		}
	}
	
	/**
	 * This method performs a swipe operation.
	 * 
	 * @param direction
	 * 		e.g. - "up", "down"
	 */
	public static void swipeTillEndOfPage() {
		LOGGER.info(LOG_DESIGN + "Performing swipe operation till end of Page direction : [{}]");
		waitForElementPrsence("ioshybrid_scrollable_container_xpath", 5);
		MobileElement elmnt = getMobileElement("ioshybrid_scrollable_container_xpath");
		Map<String, Object> args = new HashMap<>();
		args.put("element", elmnt.getId());
		args.put("direction", "up");
		args.put("velocity", 4000);
		for(int i = 0; i<4; i++) {
			driver.executeScript("mobile: swipe", args);
		}
		
	}
	
	/**
	 * It will switch to 2nd tab.
	 */
	public static void switchToNextTab() {
		ArrayList<String> contexts = new ArrayList<String>(driver.getContextHandles());
		driver.context(contexts.get(2));
		sleep(2);
	}

	/**
	 * It will switch to 1st tab.
	 */
	public static void switchToPreviousTab() {
		ArrayList<String> contexts = new ArrayList<String>(driver.getContextHandles());
		driver.context(contexts.get(1));
		sleep(2);
	}
	
	/**
	 * It will long press/tap on an Mobile Element based on a given locator.
	 * 
	 * @param locator
	 */
	public static void longPress(String locator) {
		LOGGER.info(LOG_DESIGN + "Long Pressing on : [{}] : [{}]",
				locator, Config.getProperty(locator));
		TouchAction action = new TouchAction(IOSMobUtils.driver);
		action.longPress(LongPressOptions.longPressOptions()
				.withElement(ElementOption.element (getMobileElement(locator))))
		.release().perform();
	}
	
	/**
	 * It will switch to the last tab present.
	 */
	public static void switchToLastSafariTab() {
		ArrayList<String> contexts = new ArrayList<String>(driver.getContextHandles());
		driver.context(contexts.get(contexts.size() - 1));
	}
	
	
}
