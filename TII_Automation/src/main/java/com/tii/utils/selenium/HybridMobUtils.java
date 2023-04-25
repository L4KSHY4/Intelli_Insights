package com.tii.utils.selenium; 


import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.testng.Assert.fail;

import java.io.File;

import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofSeconds;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static com.tii.utils.common.Constants.LOG_DESIGN;
import static com.tii.utils.common.Constants.PNG_EXTENSION;
import static com.tii.utils.common.Constants.TEST_CASE_SCREENSHOTS_PATH;
import static io.appium.java_client.touch.WaitOptions.waitOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tii.reporter.ExtentReporter;
import com.tii.reporter.ScreenShotGenerator;
import com.tii.utils.common.Config;
import com.tii.utils.common.DateUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;



/**
 * This class will contain all the utils for an Hybrid App
 * @author himanshu.mehrota
 *
 */
public class HybridMobUtils {

	private static final Logger LOGGER =LoggerFactory.getLogger(HybridMobUtils.class);
	public static AppiumDriver<WebElement> driver = null;


	/**
	 * This method is responsible for setting up driver for this class. 
	 * 
	 * @param driver
	 */
	public static void setDriver(WebDriver driver) {
		HybridMobUtils.driver = (AppiumDriver<WebElement>) driver;
		if(driver.toString().contains("ANDROID")) {
			AndroidMobUtils.driver= (AndroidDriver<WebElement>) driver;
		}else if(driver.toString().contains("IOSDriver")) {
			IOSMobUtils.driver = (IOSDriver<WebElement>) driver;
		}
	}

	/**
	 * It will Terminate hybrid app driver 
	 * 
	 * @param string
	 */
	public static void terminateHybridAppDriver() {
		if (nonNull(driver)) {
			System.out.println("Quitting Hybrid App Driver");
			driver.quit();
			HybridMobUtils.driver=null;
		}
	}

	/**
	 * It will check if Web Element is displayed or not.
	 * 
	 * @param element
	 * @return enabled status
	 */
	public static boolean isElementDisplayed(WebElement element) {
		LOGGER.info(LOG_DESIGN + "Checking if element is displayed, element : [{}]", element);

		return element.isDisplayed();
	}


	/**
	 * It will check if Mobile Element is displayed or not.
	 * 
	 * @param element
	 * @return enabled status
	 */
	public static boolean isElementDisplayed(MobileElement element) {
		LOGGER.info(LOG_DESIGN + "Checking if element is displayed, element : [{}]", element);

		return element.isDisplayed();
	}

	/**
	 * It will give the object of "By" :
	 * 
	 * @param locator
	 * @return By object
	 */
	public static By getByObject(String locator) {
		String locatorValue = isNull(Config.getProperty(locator)) ? locator : Config.getProperty(locator).trim();
		locatorValue = locatorValue.trim();
		By byObj = null;
		if (locator.endsWith("_xpath")) {
			byObj = By.xpath(locatorValue.replaceAll("_xpath", ""));
		} else if (locator.endsWith("_css")) {
			byObj = By.cssSelector(locatorValue.replaceAll("_css", ""));
		} else if (locator.endsWith("_id")) {
			byObj = By.id(locatorValue.replaceAll("_id", ""));
		} else if (locator.endsWith("_linkText")) {
			byObj = By.linkText(locatorValue.replaceAll("__linkText", ""));
		} else if (locator.endsWith("_name")) {
			byObj = By.name(locatorValue.replaceAll("_name", ""));
		}else if (locator.endsWith("_accessibilityid")) {
			byObj = MobileBy.AccessibilityId(locatorValue.replaceAll("_accessibilityid", ""));
		}
		return byObj;

	}

	/**
	 * It will hard wait for the given seconds.
	 * 
	 * @param seconds
	 */
	public static void sleep(int seconds) {
		LOGGER.info(LOG_DESIGN + "Waiting for {} seconds", seconds);
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			LOGGER.info(LOG_DESIGN + "Exception occurred while waiting for {} seconds ", seconds);
		}

	}


	/**
	 * It check if an element is present in the Page Source.
	 * 
	 * @param locator
	 * @param seconds
	 * 
	 * @return MobileElement
	 */
	public static MobileElement waitForElementPresence(String locator, long seconds) {
		LOGGER.info(LOG_DESIGN + "waiting for presence of element [{}] for {} seconds", locator, seconds);
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		return (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(getByObject(locator)));
	}

	/**
	 * It will check page source with the string given.
	 * 
	 * @return 
	 * 		Boolean - true or false
	 */
	public static Boolean pageSourceContains(String text) {
		return driver.getPageSource().contains(text);
	}

	/**
	 * Performs single tap on the location provided.
	 * 
	 * @param x
	 *            - xPoint
	 * @param y
	 *            - yPoint
	 */
	public static void tapLocation(int x, int y) {
		TouchAction action = new TouchAction(driver);
		PointOption pnt = new PointOption();
		action.tap(pnt.point(x, y)).perform();
	}
	
	/**
	 * Performs single tap on the location provided.
	 * 
	 * @param String
	 *            x - xPoint
	 * @param String
	 *            y - yPoint
	 */
	public static void tapLocation(String x, String y) {
		int X = Integer.parseInt(x);
		int Y = Integer.parseInt(y);
		tapLocation(X, Y);
	}

	/**
	 * It will get list of Mobile elements of a specific locator.
	 * 
	 * @param locator
	 * @return List<MobileElement>
	 */
	public static <MobileElement> List<MobileElement> getMobileElements(String locator) {
		String locatorValue = isNull(Config.getProperty(locator)) ? locator : Config.getProperty(locator).trim();
		LOGGER.info(LOG_DESIGN + "Getting elements for :-- [{}] : [{}]", locator, locatorValue);
		List<MobileElement> elements = null;
		try {
			elements = (List<MobileElement>) driver.findElements(getByObject(locator.trim()));
		}catch (StaleElementReferenceException e) {
			LOGGER.error(LOG_DESIGN + "StaleElementRefrenceException occurred while getting : [{}]", locator);
			elements = null;
			sleep(2);
			LOGGER.info(LOG_DESIGN + "Retrying to get elements after Stale Element Exception : [{}] : [{}]", locator, Config.getProperty(locator));
			elements = (List<MobileElement>) driver.findElements(getByObject(locator.trim()));
		}catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : {}: ", e.getMessage());
		}

		return elements;
	}

	/**
	 * It will get list of Web elements of a specific locator.
	 * 
	 * @param locator
	 * @return List<MobileElement>
	 */
	public static <WebElement> List<WebElement> getWebElements(String locator) {
		String locatorValue = isNull(Config.getProperty(locator)) ? locator : Config.getProperty(locator).trim();
		LOGGER.info(LOG_DESIGN + "Getting elements for :-- [{}] : [{}]", locator, locatorValue);
		List<WebElement> elements = null;
		try {
			elements = (List<WebElement>) driver.findElements(getByObject(locator.trim()));
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : {}: ", e.getMessage());
		}

		return elements;
	}

	/**
	 * It will get size of the screen.
	 * 
	 * @return Dimension 
	 */
	public static Dimension getScreenSize() {
		Dimension size;
		return size = driver.manage().window().getSize();
	}

	/**
	 * This method performs a scroll action from bottom to top
	 * 
	 */
	public static void swipeTopToBottom() {
		Dimension dimensions = getScreenSize();
		int Anchor = dimensions.getWidth() / 2;

		Double startH = dimensions.getHeight() * 0.3;
		int scrollStart = startH.intValue();

		Double endH = dimensions.getHeight() * 0.7;
		int scrollEnd = endH.intValue();

		TouchAction t = new TouchAction(driver);
		t.press(PointOption.point(Anchor, scrollStart)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
		.moveTo(PointOption.point(Anchor, scrollEnd)).release().perform();
	}

	/**
	 * It will get Mobile element.
	 * 
	 * @param locator
	 * @return WebElement
	 */
	public static MobileElement getMobileElement(String locator) {
		String locatorValue = isNull(Config.getProperty(locator)) ? locator : Config.getProperty(locator).trim();
		LOGGER.info(LOG_DESIGN + "Getting element for :-- [{}] : [{}]", locator, locatorValue);
		MobileElement element = null;
		try {
//			waitForElementVisibility(locator, Long.valueOf(Config.getProperty("minTimeOut")));
//			waitForElementPresence(locator, Long.valueOf(Config.getProperty("minTimeOut")));
			element = (io.appium.java_client.MobileElement) driver.findElement(getByObject(locator.trim()));
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : {}: ", e.getMessage());
			fail(e.getMessage());
		}

		return element;
	}

	/**
	 * It will rotate the android device screen to LANDSCAPE mode.
	 * 
	 */
	public static void setDeviceOrientation(String orientation) {
		ScreenOrientation screenOrientation = orientation.equalsIgnoreCase("PORTRAIT") ? ScreenOrientation.PORTRAIT
				: ScreenOrientation.LANDSCAPE;
		LOGGER.info(LOG_DESIGN + "Changing Screen Orientation to : {}: ", orientation);
		driver.rotate(screenOrientation);
	}

	/**
	 * It will switch to web context if present and will return the original
	 * context so that we can switch back to original.
	 * 
	 */
	public static String switchToWebContext() {
		LOGGER.info("Switching to WEB context...");
		String nativeContext = driver.getContext();
		Set<String> contexts = driver.getContextHandles();
		for (String context : contexts) {
			if (context.contains("WEBVIEW")) {
				LOGGER.info("Switching from NATIVE CONTEXT to WEB CONTEXT.");
				driver.context(context);
				LOGGER.info("Done switching from NATIVE CONTEXT to WEB CONTEXT.");
				break;
			}
		}

		return nativeContext;
	}

	/**
	 * It will get an element's text using any attribute. For e.g attribute can be :
	 * innerText,textContent,title etc as defined in the element html code
	 * 
	 * @param locator
	 * @param attribute
	 * @return
	 */
	public static String getTextUsingAttribute(String locator, String attribute) {
		MobileElement element = getMobileElement(locator);
		String elementText = element.getAttribute(attribute);
		LOGGER.info(LOG_DESIGN + "Text found for : [{}] is : [{}]", locator, elementText);

		return elementText;

	}

	/**
	 * It will get Web element.
	 * 
	 * @param locator
	 * @return WebElement
	 */	
	public static WebElement getWebElement(String locator) {
		String locatorValue = isNull(Config.getProperty(locator)) ? locator : Config.getProperty(locator).trim();
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
	 * It enters the value in text box based on mobile element
	 * 
	 * @param locator
	 * @param text
	 */
	public static void enterTextMob(String locator, String text) {
		try {
			MobileElement element = getMobileElement(locator);
			// highlightWebElement(element);
			LOGGER.info(LOG_DESIGN + "Entering text for element: [{}] Text is :[{}]", locator, text);
			element.clear(); // clearing if any text is present in text box.
			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while clicking : [{}]", e.getMessage());
		}
	}

	/**
	 * It just waits till element is not visible on Page Source.
	 * 
	 * @param locator
	 * @param seconds
	 */
	public static void waitUntilElementInvisibility(String locator) {
			LOGGER.info(LOG_DESIGN + "waiting for invisiblity of element [{}] for {} seconds", locator,
					Long.valueOf(Config.getProperty("minTimeOut")));
			WebDriverWait wait = new WebDriverWait(driver, Long.valueOf(Config.getProperty("minTimeOut")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(getByObject(locator)));	
	}
	
	/**
	 * It just waits till element is not visible on Page Source.
	 * 
	 * @param locator
	 * @param seconds
	 */
	public static void waitUntilElementInvisibility(String locator, long seconds) {
			LOGGER.info(LOG_DESIGN + "waiting for invisiblity of element [{}] for {} seconds", locator,
					seconds);
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(getByObject(locator)));	
	}

	/**
	 * It returns the text present in element. And then clicks on a given locator.
	 * 
	 * @param locator
	 * @return element's text value
	 */

	public static String getTextAndClick(String locator) {
		String elementText = null;
		try {
			MobileElement element = getMobileElement(locator);

			elementText = element.getText();
			if (elementText != null) {
				LOGGER.info(LOG_DESIGN + "Text found for : [{}] is : [{}]", locator, elementText);
			} else {
				LOGGER.error(LOG_DESIGN + "Exception occurred while getting text for element : [{}]", locator);
			}

			LOGGER.info(LOG_DESIGN + "Clicking on : [{}] : [{}]", locator, Config.getProperty(locator));
			element.click();
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while clicking : [{}]", e.getMessage());
		}

		return elementText;

	}

	/**
	 * It enters the value in text box for a Mobile Element
	 * 
	 * @param locator
	 * @param text
	 */
	public static void enterText(MobileElement element, String text) {
		element.clear(); // clearing if any text is present in text box.
		LOGGER.info(LOG_DESIGN + "Entering text for element: [{}] Text is :[{}]", element, text);
		element.sendKeys(text);

	}

	/**
	 * It will click on a given locator based on Locator for mobile element
	 * 
	 * @param locator
	 */
	public static void appClick(String locator) {
		try {
			MobileElement element = getMobileElement(locator);
			LOGGER.info(LOG_DESIGN + "Clicking on : [{}] : [{}]", locator, Config.getProperty(locator));
			element.click();
		}catch (StaleElementReferenceException e) {
			sleep(3);
			LOGGER.error(LOG_DESIGN + "StaleElementRefrenceException occurred while clicking : [{}]", locator);
			MobileElement element = getMobileElement(locator);
			LOGGER.info(LOG_DESIGN + "Retrying click after Stale Element Exception : [{}] : [{}]", locator, Config.getProperty(locator));
			element.click();
		}catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while clicking : [{}]",
					e.getMessage());
		}
	}

	/**
	 * It will tap on a given locator based on Mobile element
	 * 
	 * @param locator
	 */
	public static void tap(String locator) {
		try {
			MobileElement element = getMobileElement(locator);
			LOGGER.info(LOG_DESIGN + "Clicking on : [{}] : [{}]", locator, Config.getProperty(locator));
			TouchAction action = new TouchAction(driver);
			action.tap(TapOptions.tapOptions().withElement(element(element))).perform();
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while tapping : [{}]", e.getMessage());
		}
	}

	/**
	 * It enters the value in text box for a WebElement
	 * 
	 * @param locator
	 * @param text
	 */
	public static void enterText(WebElement element, String text) {
		element.clear(); // clearing if any text is present in text box.
		LOGGER.info(LOG_DESIGN + "Entering text for element: [{}] Text is :[{}]", element, text);
		element.sendKeys(text);

	}

	/**
	 * It will return locator value present in or.properties file.
	 * 
	 * @param locatorName
	 * @return locator value
	 */
	public static String getLocatorValue(String locatorName) {
		return Config.getProperty(locatorName).trim();
	}

	/**
	 * It will click on a given locator based on locator for web Element
	 * 
	 * @param locator
	 */
	public static void webClick(String locator) {
		try {
			WebElement element = getWebElement(locator);
			LOGGER.info(LOG_DESIGN + "Clicking on : [{}] : [{}]", locator, Config.getProperty(locator));
			element.click();
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while clicking : [{}]",
					e.getMessage());
		}
	}

	/**
	 * It will press the coordinates of an element based on mobile element
	 * 
	 * @param element
	 */
	public void pressByCoordinates(MobileElement element) {

		Point point = element.getLocation();
		new TouchAction(driver).press(point(point.x + 20, point.y + 30)).waitAction(waitOptions(ofSeconds(1))).release()
		.perform();
	}

	/**
	 * It enters the value in text box for a web element
	 * 
	 * @param locator
	 * @param text
	 */
	public static void enterTextWeb(String locator, String text) {
		try {
			WebElement element = getWebElement(locator);
			LOGGER.info(LOG_DESIGN + "Entering text for element: [{}] Text is :[{}]", element, text);
			element.clear(); // clearing if any text is present in text box.
			element.sendKeys(text);
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while clicking : [{}]", e.getMessage());
		}
	}

	/**
	 * It will click on a given locator based on Mobile element
	 * 
	 * @param locator
	 */
	public static void appClick(MobileElement element) {
		try {
			LOGGER.info(LOG_DESIGN + "Clicking on : [{}]", element);
			element.click();
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while clicking : [{}]", e.getMessage());
		}
	}
	
	/**
	 * This method performs a swipe action from bottom to top
	 * 	depending on the sides provides. By default it will swipe in the middle.
	 * 
	 * @param side
	 */
	public static void swipeBottomToTop(String side) {
		Dimension dimensions = getScreenSize();
		int Anchor;
		switch (side.toLowerCase()) {
		case "right":
			Anchor = (int) (dimensions.getWidth() / 1.3);
			break;
		case "left":
			Anchor = dimensions.getWidth() / 3;
			break;
			
		default:
			Anchor = dimensions.getWidth() / 2;
			break;
		}
		Double startH = dimensions.getHeight() * 0.8;
		int scrollStart = startH.intValue();

		Double endH = dimensions.getHeight() * 0.2;
		int scrollEnd = endH.intValue();

		TouchAction t = new TouchAction(driver);
		t.press(PointOption.point(Anchor, scrollStart)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
		.moveTo(PointOption.point(Anchor, scrollEnd)).release().perform();
	}

	/**
	 * It will click on a given locator based on Web element
	 * 
	 * @param locator
	 */
	public static void webClick(WebElement element) {
		try {
			LOGGER.info(LOG_DESIGN + "Clicking on : [{}]", element);
			element.click();
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while clicking : [{}]", e.getMessage());
		}
	}

	/**
	 * It will switch to native context from.
	 * 
	 */
	public static void switchToNativeContext(String nativeContext) {
		LOGGER.info("Switching to NATIVE context...");
		driver.context(nativeContext);
		LOGGER.info("Done switching to NATIVE CONTEXT");
	}

	/**
	 * It will tap on the given x-y coordinates and use android keyboard to send text (str)
	 * 
	 * @param x
	 * @param y
	 * @param str
	 */
	public static void tapAndEnterText(String x, String y, String str) {
		int X = Integer.parseInt(x);
		int Y = Integer.parseInt(y);
		tapAndEnterText(X, Y, str);
	}

	/**
	 * It will tap on the given x-y coordinates and use android keyboard to send text (str)
	 * 
	 * @param x
	 * @param y
	 * @param str
	 */
	public static void tapAndEnterText(int x, int y, String str) {
		tapLocation(x, y);
		HybridMobUtils.sleep(1);
		LOGGER.info(LOG_DESIGN + "Entering Text Using Android Keyboard: [{}]", str);
		driver.getKeyboard().sendKeys(str);
		LOGGER.info(LOG_DESIGN + "Entered Text Using Android Keyboard: [{}]", str);

	}
	
	/**
	 * It will send the keys using android keyboard
	 * 
	 * @param str
	 */
	public static void enterKeysUsingKeyboard(String str) {
		LOGGER.info(LOG_DESIGN + "Entering Text Using Keyboard: [{}]", str);
		driver.getKeyboard().sendKeys(str);
	}


	/**
	 * This method will find the element. If it is not present, it will swipeUp (max 5 times) and then clicks on the element.
	 * 
	 * @param locator
	 */
	public static void scrollAndClick(String locator) {
		int retry = 0;
		while(retry <= 5) {
			HybridMobUtils.sleep(1);
			List<MobileElement> el = getMobileElements(locator);
			if (el.size() > 0 && el.get(0).isDisplayed() && isElementVisible(locator)) {
				LOGGER.info(LOG_DESIGN + "Clicking on : [{}] : [{}]", locator, Config.getProperty(locator));
				el.get(0).click();
				break;
			}else {
				LOGGER.info(LOG_DESIGN + "Swiping UP");
				swipeBottomToTop("right");
				retry++;
			}
		}
		if(retry > 6) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : Not able to find : [{}] : [{}]", locator, Config.getProperty(locator));
			fail("Not able to find : " + locator +" : " + Config.getProperty(locator));
		}
	}
	
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
	 * It will give the text present in element.
	 * 
	 * @return element's text value
	 */
	public static String getText(String locator) {
		MobileElement element = getMobileElement(locator);
		String elementText = element.getText();
		LOGGER.info(LOG_DESIGN + "Text found for : [{}] is : [{}]", locator, elementText);
		return elementText;

	}
	
	/**
	 * It will fetch the the coordinates of the element and then tap on those coordinates
	 * 
	 * @param locator
	 */
	public static void tapByCoordinates(String locator) {
		MobileElement element = getMobileElement(locator);
		Point point = element.getLocation();
		tapLocation(point.x + 20, point.y + 30);
	}
	
	/**
	 * Will wait for an element to be present for a given time .
	 * 
	 * @param locator
	 * @param seconds
	 * 
	 * @return MobileElement
	 */
	public static void waitForElementPrsence(String locator, long seconds) {
		LOGGER.info(LOG_DESIGN + "waiting for presence of element [{}] for {} seconds", locator, seconds);
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		try {
//			 wait.until(ExpectedConditions.visibilityOfElementLocated(getByObject(locator)));
			 wait.until(ExpectedConditions.presenceOfElementLocated(getByObject(locator)));
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : {}: ", e.getMessage());
		}
	}
	
	/**
	 * It will check that an element is present on the DOM of a page and visible.
	 * 
	 * @param locator
	 * @param seconds
	 */
	public static void waitForElementVisibility(String locator, long seconds) {
		LOGGER.info(LOG_DESIGN + "waiting for visibility of element [{}] for {} seconds", locator, seconds);
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(getByObject(locator)));
		}catch (StaleElementReferenceException e) {	
			LOGGER.error(LOG_DESIGN + "StaleElementRefrenceException occurred while waiting for visibility of element: [{}] ,[{}]", locator, e.getMessage());
			sleep(3);
			wait.until(ExpectedConditions.visibilityOfElementLocated(getByObject(locator)));
		}
	}
	
	/**
	 * It will check that an element is present on the DOM of a page and enabled.
	 * 
	 * @param locator
	 * @param seconds
	 */
	public static void waitForElementEnabled(String locator, long seconds) {
		MobileElement element = getMobileElement(locator);
		LOGGER.info(LOG_DESIGN + "waiting for element to be enabled [{}] for {} seconds", locator, seconds);
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until((ExpectedCondition<Boolean>) driver -> element.isEnabled());	
	}
	
	/**
	 * It will hide the keyboard if displayed.
	 */
	public static void hideKeyboard() {
		LOGGER.info(LOG_DESIGN + "Hiding Native Keyboard.");
		try {
			driver.hideKeyboard();
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception in hiding keyboard" + e.getMessage());
		}
	}
	
	/**
	 * It will attach the screenshot to extentReports
	 * 
	 * @param testCaseName
	 */
	public static void attachScreenShot(String testCaseName) {
		String screenShotPath = TEST_CASE_SCREENSHOTS_PATH + File.separatorChar + testCaseName
				+ DateUtils.getCurrentDateWithTime().replaceAll(":", "")+ "_2" + PNG_EXTENSION;
		ScreenShotGenerator.captureScreenshot(driver, screenShotPath);
		ExtentReporter.attachScreenshot(screenShotPath);
	}
	
	/**
	 * For MobileElement : It will return only visible element (neglecting hidden
	 * elements of same locator).
	 *  
	 * @param locator
	 * @param timeOut
	 * @return MobileElement
	 */
	public static MobileElement getDisplayedMobileElement(String locator, long timeOut) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(3)).ignoring(org.openqa.selenium.NoSuchElementException.class)
				.ignoring(NoSuchElementException.class);
		MobileElement element = wait.until(new Function<WebDriver, MobileElement>() {
			public MobileElement apply(WebDriver driver) {
				LOGGER.info(LOG_DESIGN + "Polling for element untill displayed for :-- [{}]", locator);
				List<WebElement> elements = driver.findElements(getByObject(locator));
				return (io.appium.java_client.MobileElement) elements.stream()
						.filter(element -> element.isDisplayed()).findFirst().get();
			}
		});
		return element;
	}
	
	/**
	 * It will perform the drag drop operation using Touch Actions class.
	 * 
	 * @param sourceElement
	 *            element that is to be dragged.
	 * @param targetElement
	 *            element on which we have to drag.
	 * @param timeToHoldInSeconds
	 */
	public static void dragDropUsingTouchActions(MobileElement sourceElement, MobileElement targetElement,
			long timeToHoldInSeconds) {
		LOGGER.info(LOG_DESIGN + "Performing drag and drop operation on elements using Touch Actions class,"
				+ " dragElement : [{}] and dropElement : [{}]", sourceElement, targetElement);
		DragDropUtils.dragDropUsingTouchActions(driver, sourceElement, targetElement, timeToHoldInSeconds);
	}
	
	/**
	 * This method performs a scroll action from bottom to top
	 * 
	 */
	public static void swipeHorizontally() {
		Dimension dimensions = getScreenSize();
		int Anchor = dimensions.getHeight() / 2;

		Double startH = dimensions.getWidth() * 0.9;
		int scrollStart = startH.intValue();

		Double endH = dimensions.getWidth() * 0.1;
		int scrollEnd = endH.intValue();

		TouchAction t = new TouchAction(driver);
		t.press(PointOption.point(scrollStart, Anchor)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
		.moveTo(PointOption.point(scrollEnd, Anchor)).release().perform();
	}
	
	/**
	 * This method will find the element. If it is not present, it will swipeUp (max 5 times) and then clicks on the element.
	 * 
	 * @param locator
	 */
	public static void scrollHorizontallyAndClick(String locator) {
		int retry = 0;
		while(retry <= 10) {
			HybridMobUtils.sleep(1);
			List<MobileElement> el = getMobileElements(locator);
			if (el.size() > 0 && el.get(0).isDisplayed()) {
				LOGGER.info(LOG_DESIGN + "Clicking on : [{}] : [{}]", locator, Config.getProperty(locator));
				el.get(0).click();
				break;
			}else {
				LOGGER.info(LOG_DESIGN + "Swiping Left");
				swipeHorizontally();
				retry++;
			}
		}
		if(retry > 10) {
			LOGGER.error(LOG_DESIGN + "!!!!!! Exception Occurred : Not able to find : [{}] : [{}]", locator, Config.getProperty(locator));
			fail("Not able to find : " + locator +" : " + Config.getProperty(locator));
		}
	}
}
