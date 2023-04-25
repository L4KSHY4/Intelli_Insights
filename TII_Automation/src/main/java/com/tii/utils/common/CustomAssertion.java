package com.tii.utils.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.tii.reporter.ExtentReporter;

/**
 * It contains all methods for assertion or validation with added log info.
 *
 */
public class CustomAssertion {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAssertion.class);

	/**
	 * It will make assertion of two strings.
	 * 
	 * @param actual
	 * @param expected
	 */
	public static void assertEquals(final String actual, final String expected) {
		LOGGER.info("Validating :- Actual [{}] : " + "Expected [{}]", actual, expected);
		Assert.assertEquals(actual, expected);
	}

	/**
	 * It will make assertion of two strings.
	 * 
	 * @param actual
	 * @param expected
	 */
	public static void assertEquals(final String actual, final String expected, final String message) {
		LOGGER.info("Validating :- Actual [{}] : " + "Expected [{}]", actual, expected);
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			ExtentReporter.fail("Failed while validating step: [" + message + "]");
			throw e;
		}
		ExtentReporter.pass(message);
	}

	/**
	 * It will make assertion of two boolean.
	 * 
	 * @param actual
	 * @param expected
	 */
	public static void assertEquals(final boolean actual, final boolean expected, final String message) {
		LOGGER.info("Validating :- Actual [{}] : " + "Expected [{}]", actual, expected);
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			ExtentReporter.fail("Failed while validating step: [" + message + "]");
			throw e;
		}
		ExtentReporter.pass(message);
	}

	/**
	 * It will make assertion of true statement.
	 * 
	 * @param actual
	 */
	public static void assertTrue(final boolean actual, final String message) {
		LOGGER.info("Validating :- Actual [{}] : as assertTrue", actual);
		try {
			Assert.assertTrue(actual);
		} catch (AssertionError e) {
			ExtentReporter.fail("Failed while validating step: [" + message + "]");
			throw e;
		}
		ExtentReporter.pass(message);
	}

	/**
	 * It will make assertion of false statement.
	 * 
	 * @param actual
	 */
	public static void assertFalse(final boolean actual, final String message) {
		LOGGER.info("Validating :- Actual [{}] : as assertFalse", actual);
		try {
			Assert.assertFalse(actual);
		} catch (AssertionError e) {
			ExtentReporter.fail("Failed while validating step: [" + message + "]");
			throw e;
		}
		ExtentReporter.pass(message);
	}

	/**
	 * It will make assertion of two list.
	 * 
	 * @param actual
	 * @param expected
	 */
	public static void assertEquals(List<String> actual, List<String> expected, String message) {
		LOGGER.info("Validating :- Actual [{}] : " + "Expected [{}]", actual, expected);
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			ExtentReporter.fail("Failed while validating step: [" + message + "]");
			throw e;
		}
		ExtentReporter.pass(message);

	}

	/**
	 * It will make assertion of two integer.
	 * 
	 * @param actual
	 * @param expected
	 */
	public static void assertEquals(int actual, int expected, String message) {
		LOGGER.info("Validating :- Actual [{}] : " + "Expected [{}]", actual, expected);
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			ExtentReporter.fail("Failed while validating step: [" + message + "]");
			throw e;
		}
		ExtentReporter.pass(message);

	}
	
	/**
	 * It will make assertion of two strings.
	 * 
	 * @param actual
	 * @param expected
	 */
	public static void assertNotEquals(final String actual, final String expected, String message) {
		LOGGER.info("Validating :- Actual [{}] : " + "Expected [{}]", actual, expected);
		try {
			Assert.assertNotEquals(actual, expected);
		} catch (AssertionError e) {
			ExtentReporter.fail("Failed while validating step: [" + message + "]");
			throw e;
		}
		ExtentReporter.pass(message);
	}

}
