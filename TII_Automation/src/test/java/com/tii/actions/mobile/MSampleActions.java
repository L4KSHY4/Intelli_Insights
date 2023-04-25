package com.tii.actions.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tii.reporter.ExtentReporter;
import com.tii.actions.hybridandroid.HSampleActions;
import com.tii.actions.web.AnalyticsAndReportingActions;


/**
 * Add comments 
 *
 */
public class MSampleActions extends AnalyticsAndReportingActions {

	private static final Logger LOGGER = LoggerFactory.getLogger(MSampleActions.class);

	/**
	 * add comments
	 */
	public static void addNewWorkSkill() {
		ExtentReporter.info(
				"Add comments");

		
	}
}

