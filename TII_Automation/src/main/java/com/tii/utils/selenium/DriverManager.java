package com.tii.utils.selenium;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

/**
 * This class is responsible for managing driver level operations.
 * 
 */
public class DriverManager {

	/**
	 * It sets the context attributes.
	 * 
	 * @param driver
	 * @param ctx
	 * @return ITestContext
	 */
	public static ITestContext setupContext(WebDriver driver, ITestContext ctx, String browser, String nodeURL) {
		ctx.setAttribute("driver", driver);
		ctx.setAttribute("browser", browser);
		ctx.setAttribute("nodeURL", nodeURL);

		return ctx;
	}
}
