package com.tii.tests.hybridios;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.tii.reporter.ExtentReporter;
import com.tii.utils.common.CustomAssertion;
import com.tii.utils.selenium.HybridMobUtils;
import com.tii.utils.selenium.IOSMobUtils;
import com.tii.utils.selenium.WebUtils;
import com.tii.core.BaseConfiguration;
import com.tii.core.DataRecorder;

import io.appium.java_client.MobileElement;

public class ISampleTest extends BaseConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(ISampleTest.class);

	private static String skillLabel = "";

	@Test(testName = "ISampleTest_100")
	public void add_new_work_skill() {
		Map<String, String> testData = DataRecorder.getTestData("ISampleTest_100");
		String deviceOrientation = DataRecorder.getTCMappingData("ISampleTest_100").get("DEVICE_ORIENTATION");
		
	}
}