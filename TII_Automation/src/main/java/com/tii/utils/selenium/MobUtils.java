package com.tii.utils.selenium;

import static com.tii.utils.common.Constants.LOG_DESIGN;
import static java.util.Objects.isNull;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tii.utils.common.Config;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

/**
 * This class is responsible for performing all actions required to automate
 * mobile devices. i.e Android/iPad/tablets etc.
 * 
 */
public class MobUtils extends WebUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(MobUtils.class);

	public static AndroidDriver<WebElement> driver;

	/**
	 * It will rotate the android device screen to LANDSCAPE mode.
	 * 
	 */
	public static void setDeviceOrientation(String orientation) {
		ScreenOrientation screenOrientation = orientation.equalsIgnoreCase("PORTRAIT") ? ScreenOrientation.PORTRAIT
				: ScreenOrientation.LANDSCAPE;
		driver.rotate(screenOrientation);
	}

	/**
	 * It will get element.
	 * 
	 * @param locator
	 * @return WebElement
	 */
	public static WebElement getElement(String locator) {
		String locatorValue = isNull(Config.getProperty(locator)) ? locator : Config.getProperty(locator);
		LOGGER.info(LOG_DESIGN + "Getting element for :-- [{}] : [{}]", locator, locatorValue);
		WebElement element = null;
		try {
			waitForElementPresence(locator, Long.valueOf(Config.getProperty("minTimeOut")));
			element = driver.findElement(getByObject(locator.trim()));
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : {}: ", e.getMessage());
		}

		return element;
	}

	/**
	 * It will get list of elements of a specific locator.
	 * 
	 * @param locator
	 * @return List<WebElement>
	 */
	public static List<WebElement> getElements(String locator) {
		String locatorValue = isNull(Config.getProperty(locator)) ? locator : Config.getProperty(locator);
		LOGGER.info(LOG_DESIGN + "Getting elements for :-- [{}] : [{}]", locator, locatorValue);
		List<WebElement> elements = null;
		try {
			waitForElementPresence(locator, Long.valueOf(Config.getProperty("minTimeOut")));
			elements = driver.findElements(getByObject(locator.trim()));
		} catch (Exception e) {
			elements = driver.findElements(getByObject(locator.trim()));
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : {}: {}", e.getMessage(),
					"Returning Web Elements whether the count is 0 or more");
		}

		return elements;
	}

	/**
	 * It will get InnerText of element using JavaScript.
	 * 
	 * @param element
	 * @return innerText of element.
	 */
	public static String getInnerText(WebElement element) {
		LOGGER.info(LOG_DESIGN + "Getting innerText using javascript, WebElement : [{}]", element);
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;", element);
	}

	/**
	 * It will get InnerText of element using JavaScript.
	 * 
	 * @param locator
	 * @return innerText of element.
	 */
	public static String getInnerText(String locator) {
		WebElement element = getElement(locator);
		LOGGER.info(LOG_DESIGN + "Getting innerText using javascript, WebElement : [{}]", element);
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;", element);
	}

	/**
	 * It will switch to native context if present and will return the original
	 * context so that we can switch back to original.
	 * 
	 */
	public static String switchToNativeContext() {
		LOGGER.info("Switching to NATIVE context...");
		String webContext = driver.getContext();
		Set<String> contexts = driver.getContextHandles();
		for (String context : contexts) {
			if (context.contains("NATIVE_APP")) {
				LOGGER.info("Switching from WEB CONTEXT to NATIVE CONTEXT.");
				driver.context(context);
				LOGGER.info("Done switching from WEB CONTEXT to NATIVE CONTEXT.");
				break;
			}
		}

		return webContext;
	}

	/**
	 * It will switch to web context from.
	 * 
	 */
	public static void switchToWebContext(String webContext) {
		LOGGER.info("Switching to Web context...");
		driver.context(webContext);
		LOGGER.info("Done switching to WEB CONTEXT");
	}

	/**
	 * It will set the airplane mode as ON/OFF
	 * 
	 * @param status
	 *            true for turning on airplane mode and vice versa
	 */
	public static void setAndroidDeviceAirplaneMode(boolean status) {
		try {
			String airplaneModeStatus = "";
			if (status) {
				airplaneModeStatus = "1";
			} else {
				airplaneModeStatus = "0";
			}
			// String sdkPath = System.getenv("ANDROID_HOME") + "/platform-tools/";
			String sdkPath = "D:\\jaikant\\platform-tools_r28.0.1-windows\\platform-tools\\"; // will be updated later
																								// for relative path
																								// once reliability of
																								// method is checked.
			Runtime.getRuntime().exec(sdkPath + "adb shell settings put global airplane_mode_on " + airplaneModeStatus);
			Thread.sleep(1000);
			Process process = Runtime.getRuntime()
					.exec(sdkPath + "adb shell am broadcast -a android.intent.action.AIRPLANE_MODE");
			process.waitFor();
			Thread.sleep(4000);
			if (status) {
				LOGGER.info("Android device Airplane mode status is set to ON");
			} else {
				LOGGER.info("Android device Airplane mode status is set to OFF");
			}
		} catch (Exception e) {
			LOGGER.error("Unable to set android device Airplane mode.[{}]", e.getMessage());
		}

	}

	/**
	 * It will press the back button in android device.
	 * 
	 */
	public static void pressBackButton() {
		LOGGER.info(LOG_DESIGN + "Pressing BACK key.");
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
	}
	
	/**
	 * It will clear all the cookies.
	 * 
	 */
	public static void clearCookies() {
		LOGGER.info(LOG_DESIGN + "Clearing all cookies of browser.");
		driver.manage().deleteAllCookies();
	}

}
