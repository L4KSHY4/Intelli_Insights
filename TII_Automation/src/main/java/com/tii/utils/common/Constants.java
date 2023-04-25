package com.tii.utils.common;

import java.io.File;

/**
 * It contains all constants that are common throughout the framework and can be
 * reused.
 */
public class Constants {

	public static final String USER_DIR = "user.dir";

	// src/main/resources folder names
	public static final String RESOURCES = "resources";
	public static final String DRIVERS = "drivers";
	public static final String EXTENSIONS = "extensions";
	public static final String CONFIG = "config";
	public static final String TEST_SUITE_XMLS = "testSuiteXmls";
	public static final String INNER_TEXT = "innerText";
	public static final String TEXT_CONTENT = "textContent";
	public static final String TITLE = "title";

	// webdriver's path
	public static final String CHROMEDRIVER_PATH = System.getProperty(USER_DIR) + File.separator + "src"
			+ File.separator + "main" + File.separator + "resources" + File.separator + "drivers" + File.separator
			+ "chromedriver.exe";
	public static final String IEDRIVER_PATH = System.getProperty(USER_DIR) + File.separator + "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator + "drivers" + File.separator
			+ "IEDriverServer.exe";
	public static final String GECKODRIVER_PATH = System.getProperty(USER_DIR) + File.separator + "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator + "drivers" + File.separator
			+ "geckodriver19.1.exe";

	// property files path
	public static final String CONFIG_FOLDER_PATH = System.getProperty(USER_DIR) + File.separator + "src"
			+ File.separator + "main" + File.separator + "resources" + File.separator + "config";
	public static final String SUITE_XML_PATH = System.getProperty(USER_DIR) + File.separator + "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator + "testSuiteXmls";

	// test results and report paths
	public static final String TEST_RESULT_DIRECTORY_PATH = System.getProperty(USER_DIR) + File.separatorChar
			+ "test-result";
	public static final String TEST_CASE_SCREENSHOTS_PATH = TEST_RESULT_DIRECTORY_PATH + File.separatorChar
			+ "testcase-screenshots";
	public static final String EXECUTION_REPORTS_PATH = TEST_RESULT_DIRECTORY_PATH + File.separatorChar
			+ "execution-reports";
	public static final String CURRENT_DAY_EXECUTION_REPORT = EXECUTION_REPORTS_PATH + File.separator
			+ DateUtils.getCurrentDate();
	public static final String EXTENT_REPORT_HTML = CURRENT_DAY_EXECUTION_REPORT + File.separator + "execution-report "
			+ DateUtils.getCurrentDateWithTime().replaceAll(":", "") + ".html";
	public static final String TEST_VIDEO_PATH = TEST_RESULT_DIRECTORY_PATH + File.separatorChar
			+ "testcase-video-recorder";
	public static final String LOG_DESIGN = "******************  ";

	// file extentions
	public static final String PNG_EXTENSION = ".png";
	public static final String XML_EXTENSION = ".xml";

	public static final String DRIVER = "driver";
	public static final String MOBILE = "mobile";

	// browser constants
	public static final String FIREFOX = "firefox";
	public static final String CHROME = "chrome";
	public static final String INTERNET_EXPLORER = "internetexplorer";
	public static final String CHROME_EMULATOR = "chrome_emulator";
	public static final String MOBILE_CHROME_ANDROID = "mobile_chrome_android";
	public static final String MOBILE_CHROME_IOS = "mobile_chrome_ios";

}
