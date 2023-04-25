package com.tii.tests.mobile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.tii.reporter.ExtentReporter;
import com.tii.utils.common.CustomAssertion;
import com.tii.utils.selenium.MobUtils;
import com.tii.utils.selenium.WebUtils;

import com.tii.core.BaseConfiguration;
import com.tii.core.DataRecorder;

public class SampleTest_aMobile extends BaseConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleTest_aMobile.class);

	@Test(testName = "ADMIN_TEST_183")
	public void searchTechnicianWithApostrophe() {
		Map<String, String> testData = DataRecorder.getTestData("ADMIN_TEST_183");
		

	}
}

	