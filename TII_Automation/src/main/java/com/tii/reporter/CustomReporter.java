package com.tii.reporter;

import static com.tii.utils.common.Constants.CURRENT_DAY_EXECUTION_REPORT;
import static com.tii.utils.common.Constants.DRIVER;
import static com.tii.utils.common.Constants.EXECUTION_REPORTS_PATH;
import static com.tii.utils.common.Constants.LOG_DESIGN;
import static com.tii.utils.common.Constants.PNG_EXTENSION;
import static com.tii.utils.common.Constants.TEST_CASE_SCREENSHOTS_PATH;
import static com.tii.utils.common.Constants.TEST_RESULT_DIRECTORY_PATH;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IExecutionListener;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.tii.utils.common.Config;
import com.tii.utils.common.DateUtils;
import com.tii.utils.common.FileUtil;
import com.tii.utils.selenium.HybridMobUtils;
import com.tii.utils.selenium.WebUtils;


/**
 * It provides complete reporting of the suite execution.
 *
 */
public class CustomReporter implements IReporter, ITestListener, IExecutionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomReporter.class);
	public static Map<String, String> TEST_RESULT_MAP = new HashMap<>();
//	public static ATUTestRecorder recorder;
	

	@Override
	public void onStart(ITestContext testContext) {
		ExtentReporter.onStart(testContext);

	}

	@Override
	public void onFinish(ITestContext testContext) {
		ExtentReporter.onFinish(testContext);
		HybridMobUtils.terminateHybridAppDriver();
		WebUtils.terminateBrowser();
	}

	@Override
	public void onTestFailure(ITestResult testResult) {
		ExtentReporter.onTestFail(testResult);
		LOGGER.error(LOG_DESIGN + "Test Case Failed: {}", testResult.getMethod().getMethodName());
		LOGGER.error(LOG_DESIGN + "Failure Reason : ", testResult.getThrowable());
		onTestCompletion(testResult, "FAILED");

	}

	@Override
	public void onTestSkipped(ITestResult testResult) {
		ExtentReporter.onTestSkip(testResult);
		LOGGER.info(LOG_DESIGN + "Test Case Skipped: {}", testResult.getMethod().getMethodName());
		onTestCompletion(testResult, "SKIPPED");

	}

	@Override
	public void onTestStart(ITestResult testResult) {
		LOGGER.info(
				"######################################## TEST CASE STARTED :  [{}] ###################################################################",
				testResult.getMethod().getMethodName());
		ExtentReporter.onTestStart(testResult);
		LOGGER.info(LOG_DESIGN + "Execution Started For Test: {}", testResult.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult testResult) {
		ExtentReporter.onTestPass(testResult);
		LOGGER.info(LOG_DESIGN + "Test Case Passed : {}", testResult.getMethod().getMethodName());
		onTestCompletion(testResult, "PASSED");

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult testResult) {

	}

	@Override
	public void onExecutionStart() {
		LOGGER.info("                            ##########################                                 ");
		LOGGER.info("############################ SUITE EXECUTION STARTED ################################");
		LOGGER.info("                            ##########################                                 ");
		ExtentReporter.onExceutionStart();

		FileUtil.createDirectory(TEST_RESULT_DIRECTORY_PATH);
		FileUtil.createDirectory(TEST_CASE_SCREENSHOTS_PATH);
		FileUtil.createDirectory(EXECUTION_REPORTS_PATH);
		FileUtil.createDirectory(CURRENT_DAY_EXECUTION_REPORT);

		LOGGER.info(LOG_DESIGN + "Test Result Directories created.");
//		String screenrecorderpath = TEST_VIDEO_PATH;
//		File directory = new File(String.valueOf(screenrecorderpath));
//		if (!directory.exists()) {
//			directory.mkdir();
//		}
//		if (Config.getProperty("videoRecording").equalsIgnoreCase("True")) {
//			try {
//				recorder = new ATUTestRecorder(screenrecorderpath,
//						"Test_Result_Video " + DateUtils.getCurrentDateWithTime().replaceAll(":", ""), false);
//			} catch (Exception e) {
//			}
//			try {
//				recorder.start();
//			} catch (Exception e) {
//				LOGGER.info("Exception occured video recorder of test Execution" + e.getMessage());
//			}
//		}
	}

	@Override
	public void onExecutionFinish() { 
		ExcelReporter.updateTestResults(TEST_RESULT_MAP);
		LOGGER.info("                            ##########################                                 ");
		LOGGER.info("############################ SUITE EXECUTION FINISHED ################################");
		LOGGER.info("                            ##########################                                 ");
	}

	@Override
	public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1, String arg2) {

	}

	/**
	 * It will be used to perform the after test steps if any.
	 * 
	 * @param testResult
	 */
	private void onTestCompletion(ITestResult testResult, String testStatus) {
		ExtentReporter.setExecutionTime(testResult);
		String testCaseName = testResult.getMethod().getMethodName();
		ITestContext context = testResult.getTestContext();
		WebDriver driver = (WebDriver) context.getAttribute(DRIVER);
		String screenShotPath = TEST_CASE_SCREENSHOTS_PATH + File.separatorChar + testCaseName
				+ DateUtils.getCurrentDateWithTime().replaceAll(":", "") + PNG_EXTENSION;
		ScreenShotGenerator.captureScreenshot(driver, screenShotPath);
		ExtentReporter.attachScreenshot(screenShotPath);
		String testId = testResult.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(org.testng.annotations.Test.class).testName();
		TEST_RESULT_MAP.put(testId, testStatus);
		LOGGER.info(
				"######################################## TEST CASE [{}] : {} ###################################################################",
				testCaseName, testStatus);
		LOGGER.info("");
	}
}
