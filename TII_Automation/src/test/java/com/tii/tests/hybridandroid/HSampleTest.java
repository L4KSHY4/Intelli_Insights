package com.tii.tests.hybridandroid;

import java.util.ArrayList;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.tii.reporter.ExtentReporter;
import com.tii.utils.common.Config;
import com.tii.utils.common.CustomAssertion;
import com.tii.utils.common.DateUtils;
import com.tii.utils.selenium.AndroidMobUtils;
import com.tii.utils.selenium.HybridMobUtils;
import com.tii.utils.selenium.WebUtils;
import com.tii.actions.hybridandroid.HSampleActions;


import com.tii.core.BaseConfiguration;
import com.tii.core.DataRecorder;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;

public class HSampleTest extends BaseConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(HSampleTest.class);
	private static String skillLabel = "";
	
	@Test(testName = "HSAMPLE_TEST_100")
	public void add_new_work_skill() {
		Map<String, String> testData = DataRecorder.getTestData("HSAMPLE_TEST_100");
		String deviceOrientation = DataRecorder.getTCMappingData("HSAMPLE_TEST_100").get("DEVICE_ORIENTATION");
		
		
	}
	
}