package com.tii.actions.hybridios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tii.reporter.ExtentReporter;
import com.tii.utils.selenium.IOSMobUtils;

public class ISampleActions extends IOSMobUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ISampleActions.class);
	
	/**
	 *Add comments 
	 */
	public static void SampleMethod() {
		ExtentReporter.info("Sample method ");

	}	
	}
	