package com.tii.utils.selenium;

import static com.tii.utils.common.Constants.LOG_DESIGN;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tii.utils.common.Config;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class AndroidMobUtils extends HybridMobUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidMobUtils.class);
	public static AndroidDriver<WebElement> driver;
	
	/**
	 * This method scrolls the element into View and then clicks. 
	 * 
	 * @param resourceId
	 *            - resource-id of the parent that is scrollable.
	 * @param text
	 *            - identify the element by text
	 */
	public static void scrollElementIntoViewByTextAndClick(String resourceId, String text) {
		LOGGER.info(LOG_DESIGN + "Scrolling element into view using text attribute: [{}]", text);
		MobileElement elementToClick = (MobileElement) driver
				.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"" + resourceId
						+ "\")).scrollIntoView(" + "new UiSelector().text(\"" + text + "\"));");
		LOGGER.info(LOG_DESIGN + "Clicking on element with text : [{}]", text);
		elementToClick.click();
	}
	
	public static void scrollHorizontallyByTextAndClick(String resourceId, String text) {
		LOGGER.info(LOG_DESIGN + "Horizontal Scrolling element into view using text attribute: [{}]");
		MobileElement elementToClick = (MobileElement) driver.findElement(
				MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"
						+ ".setAsHorizontalList().scrollIntoView(new UiSelector().text(\""+text+"\"))"));
		LOGGER.info(LOG_DESIGN + "Clicking on element with text : [{}]");
		elementToClick.click();
	}
	
	/**
	 * This method scrolls the element into View Using Text attribute. 
	 * 
	 * @param resourceId
	 *            - resource-id of the parent that is scrollable.
	 * @param text
	 *            - identify the element by text
	 */
	public static MobileElement scrollElementIntoViewByText(String resourceId, String text) {
		LOGGER.info(LOG_DESIGN + "Scrolling element into view using text attribute: [{}]", text);
		MobileElement element = (MobileElement) driver
				.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"" + resourceId
						+ "\")).scrollIntoView(" + "new UiSelector().text(\"" + text + "\"));");
		return element;
	}
	
	/**
	 * It will click element using text
	 * 
	 * @param text
	 */
	public static void uiAutomatorClickByText(String text) {
	
		LOGGER.info("Clicking on text using AndroidUIAutomator: "+ text);
		driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + text + "\")").click();
	}
	
	/**
	 * It will launch the ofsc app.
	 * 
	 */
	public static void switchToOFSApp() {
		Activity activity = new Activity(Config.getProperty(String.valueOf("appPackageName")), Config.getProperty(String.valueOf("appActivity")));
		activity.setStopApp(false);
		driver.startActivity(activity);
	}
	
	/**
		 * It will switch to chrome browser app.
		 * 
		 */
		public static void switchToChrome() {
			Activity activity = new Activity(Config.getProperty(String.valueOf("chromeAppPackageName").trim()),
					Config.getProperty(String.valueOf("chromeAppActivity").trim()));
	//		activity.setStopApp(false);
			driver.startActivity(activity);
		}
		
	/**
	 * It will scroll to element using contains text and then click on the element.
	 * 
	 * @param resourceId
	 *            - scrollable containers resource-id
	 * @param text
	 *            - text attribute value
	 */
	public static void scrollElementIntoViewByContainsTextAndClick(String resourceId, String text) {
		MobileElement elementToClick = (MobileElement) driver
				.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"" + resourceId
						+ "\")).scrollIntoView(" + "new UiSelector().textContains(\"" + text + "\"));");
		LOGGER.info(LOG_DESIGN + "Clicking on Element containing text : [{}]", text);
		elementToClick.click();
	}
	
	/**
	 * It will reset app data and launch the ofsc app.
	 * 
	 */
	public static void openApp() {
		driver.resetApp();
		Activity activity = new Activity(Config.getProperty(String.valueOf("appPackageName")),
		Config.getProperty(String.valueOf("appActivity")));
		activity.setStopApp(true);
		driver.startActivity(activity);
	}
	
	public static void back() {
		LOGGER.info(LOG_DESIGN + "Pressing Back Button");
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
	}
	
	/**
	 * It will scroll to element using contains text.
	 * 
	 * @param resourceId
	 *            - scrollable containers resource-id
	 * @param text
	 *            - text attribute value
	 */
	public static MobileElement scrollElementIntoViewByContainsText(String resourceId, String text) {
		MobileElement elementToClick = (MobileElement) driver
				.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()" + ".resourceId(\"" + resourceId
						+ "\")).scrollIntoView(" + "new UiSelector().textContains(\"" + text + "\"));");
		return elementToClick;
		
	}
	
	/**
	 * It will launch the ofsc app.
	 * 
	 */
	public static void switchToOFSApp(Boolean value) {
		Activity activity = new Activity(Config.getProperty(String.valueOf("appPackageName")),
				Config.getProperty(String.valueOf("appActivity")));
		activity.setStopApp(value);
		driver.startActivity(activity);
	}
	
	/**
	 * It will start the given activity of a particular package.
	 * 
	 * @param packageName
	 * @param activity
	 */
	public static void startActivity(String packageName, String activity) {
		Activity activity1 = new Activity(packageName, activity);
		driver.startActivity(activity1);
	}
	
	/**
	 * It will return current activity.
	 * 
	 * @return 
	 * 		a current activity being run on the mobile device.
	 */
	public static String getCurrentActivity() {
		return driver.currentActivity().trim();
	}
	
	/**
	 * It will press the enter key of android keyboard.
	 * 
	 */
	public static void androidKeyBoardEnter() {
		LOGGER.info(LOG_DESIGN + "Pressing ENTER key on android keyboard.");
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
	}
	
	/**
	 * It will press any key on the android device. 
	 * 
	 * @param key
	 * 		AndroidKey - Key to be pressed
	 */
	public static void pressPhysicalKey(AndroidKey key) {
		LOGGER.info(LOG_DESIGN + "Pressing key [{}]", key);
		driver.pressKey(new KeyEvent(key));
	}
	
	/**
	 * It will toggle(on/off) wifi service.
	 * 
	 */
	public static void toggleWifi() {
		LOGGER.info(LOG_DESIGN + "Toggling wifi service");
		driver.toggleWifi();
	}
}
