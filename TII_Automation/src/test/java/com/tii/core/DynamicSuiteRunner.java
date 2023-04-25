package com.tii.core;

import static com.tii.utils.common.Constants.SUITE_XML_PATH;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.tii.reporter.CustomReporter;
import com.tii.utils.common.Config;
import com.tii.utils.common.FileUtil;

public class DynamicSuiteRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(DynamicSuiteRunner.class);
	private static final String LOG_DESIGN = "####################################################################################################";
	public static Map<String, Map<String, String>> testAndClasses = new HashMap<>();
	public static String packagePath = "";
	public static final String WEB_PACKAGE = "com.tii.tests.web";
	public static final String MOBILE_PACKAGE = "com.tii.tests.mobile";
	public static final String HYBRID_PACKAGE = "com.tii.tests.hybridandroid";
	public static final String GCHROME_PACKAGE = "com.tii.tests.gchrome";
	public static final String HYBRID_IOS_PACKAGE = "com.tii.tests.hybridios";
	public static final String GSAFARI_PACKAGE = "com.tii.tests.gsafari";
	
	public static void main(String[] args) throws IOException {
		String dynamicRun = Config.getProperty("dynamicRun");
		String suiteName = Config.getProperty("suiteName");
		if (dynamicRun.equalsIgnoreCase("All") || dynamicRun.isEmpty()) {
		testAndClasses = ReflectionUtils.getClassAndTests(WEB_PACKAGE);
		testAndClasses.putAll(ReflectionUtils.getClassAndTests(MOBILE_PACKAGE));
		testAndClasses.putAll(ReflectionUtils.getClassAndTests(HYBRID_PACKAGE));
		testAndClasses.putAll(ReflectionUtils.getClassAndTests(GCHROME_PACKAGE));
		testAndClasses.putAll(ReflectionUtils.getClassAndTests(HYBRID_IOS_PACKAGE));
		testAndClasses.putAll(ReflectionUtils.getClassAndTests(GSAFARI_PACKAGE));
		} else if (dynamicRun.equalsIgnoreCase("mobile")) {
		String packageName = MOBILE_PACKAGE;
		testAndClasses = ReflectionUtils.getClassAndTests(packageName);
		} else if (dynamicRun.equalsIgnoreCase("android")){
		String packageName = HYBRID_PACKAGE;
		testAndClasses = ReflectionUtils.getClassAndTests(packageName);
		} else if (dynamicRun.equalsIgnoreCase("ios_hybridapp")) {
			String packageName = HYBRID_IOS_PACKAGE;
			testAndClasses = ReflectionUtils.getClassAndTests(packageName);
		} else {
			String packageName = WEB_PACKAGE;
			testAndClasses = ReflectionUtils.getClassAndTests(packageName);
		}
		DataRecorder.recordData();
		genrateTestNGAndRun(suiteName, testAndClasses);
		}


	/**
	 * It will create a dynamic testng xml form excel sheet and run.
	 * 
	 * @param suiteName
	 *            name to be given to suite e.g Smoke,Regression etc.
	 * @param testAndClasses
	 *            all @Test annotated methods and their respective classes
	 */
	public static void genrateTestNGAndRun(String suiteName, Map<String, Map<String, String>> testAndClasses) {
		// first getting all classes or modules(where test cases are written) and their
		// test cases in a map.
		// e.g map : AdminTest={KEY="ADMIN_TEST__100", value= "add_new_work_skill"}

		// setting suite name and verbose in xml
		XmlSuite suite = new XmlSuite();
		suite.setName(suiteName);
		suite.setVerbose(8);

		List<String> testClassesEnabledInExcel = DataRecorder.TESTSCRIPTS_CACHE.keySet().stream()
				.filter(key -> DataRecorder.TESTSCRIPTS_CACHE.get(key).get("RunMode").equalsIgnoreCase("Y"))
				.collect(Collectors.toList());
		List<String> testClassesToRun = testAndClasses.keySet().stream()
				.filter(className -> testClassesEnabledInExcel.contains(className)).collect(Collectors.toList());
		testClassesToRun.sort(Comparator.comparingInt(testClassesEnabledInExcel::indexOf)); // sorting test classes as
																							// defined in TestData.xlsx

		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		LOGGER.info(LOG_DESIGN);

		// iterating each class (where test cases are present e.g AdminTest) and writing
		// all @Test annotated methods to testng xml which have RunMode as "Y" in excel
		// data sheet.
		// here we are making each class as e.g <test name="AdminTest"> and writing
		// parameters and test methods (i.e @Test methods) in that <test> tag.
		testClassesToRun.forEach(className -> {
			// here "className" value is like : AdminTest,QuotaTest etc.
			List<XmlInclude> includedMethods = new ArrayList<>();

			// getting all test case ids e.g (@Test (name="ADMIN_TEST_100"))
			// here "ADMIN_TEST_100" is testId
			List<String> testIdsPresentInClass = testAndClasses.get(className).keySet().stream()
					.collect(Collectors.toList());
			LOGGER.info("Test Ids present in class : [{}] are : {}", className, testIdsPresentInClass);

			List<String> testIdsInExcel = DataRecorder.TC_MAPPING_CACHE.keySet().stream().collect(Collectors.toList());

			// filtering test ids which have RunMode enabled in excel sheet.
			List<String> filteredTestId = testIdsPresentInClass.stream()
					.filter(id -> Objects.nonNull(DataRecorder.TC_MAPPING_CACHE.get(id))
							&& DataRecorder.TC_MAPPING_CACHE.get(id).get("RUNMODE").equalsIgnoreCase("Y"))
					.collect(Collectors.toList());
			filteredTestId.sort(Comparator.comparingInt(testIdsInExcel::indexOf)); // sorting according test ids as
																					// defined in excel sheet.

			LOGGER.info("Test Ids that are enabled in Excel data sheet are : {}", filteredTestId);

			// adding filtered test cases to the testng xml.
			filteredTestId.forEach(id -> includedMethods.add(new XmlInclude(testAndClasses.get(className).get(id))));

			if (!includedMethods.isEmpty()) {
				// getting test configuration like browser,runmode etc related to that class as
				// defined in excel sheet.
				Map<String, String> testScriptConfig = DataRecorder.TESTSCRIPTS_CACHE.get(className);
				LOGGER.info("TestScript Configuration for class [{}] is {}", className, testScriptConfig);

				List<XmlClass> classes = new ArrayList<XmlClass>();
				XmlTest test = new XmlTest(suite);
				test.setName(className); // e.g <test name="AdminTest">

				Map<String, String> parameters = new HashMap<>();
				parameters.put("browser", testScriptConfig.get("Browser")); // e.g <parameter name="browser"
																			// value="chrome"></parameter>
				parameters.put("nodeURL", testScriptConfig.get("NodeURL"));
				if (testScriptConfig.get("Browser").contains("mobile")) {
					parameters.put("deviceOrientation", testScriptConfig.get("DeviceOrientation"));
				}
				if (testScriptConfig.get("Browser").contains("mobile")) {
					packagePath = MOBILE_PACKAGE;
				} else if (testScriptConfig.get("Browser").contains("android_hybridapp")) {
					packagePath = HYBRID_PACKAGE;
				}else if (testScriptConfig.get("Browser").contains("gigafox_chrome_android")) {
					packagePath = GCHROME_PACKAGE;
				} else if (testScriptConfig.get("Browser").contains("ios_hybridapp")) {
					packagePath = HYBRID_IOS_PACKAGE;
				} else if (testScriptConfig.get("Browser").contains("gigafox_safari_ios")) {
					packagePath = GSAFARI_PACKAGE;
				}  else {
					packagePath = WEB_PACKAGE;
				}
				test.setParameters(parameters);
				XmlClass xmlClass = new XmlClass(packagePath + "." + className); // e.g <class
																					// name="com.wfm.tests.web.AdminTest">
				xmlClass.setIncludedMethods(includedMethods); // e.g <include name="add_new_work_skill"></include>
				classes.add(xmlClass);
				test.setXmlClasses(classes);
				LOGGER.info(LOG_DESIGN);
			}
		});
		suites.add(suite);

		// creating testng xml for debug purpose.
		// it will be created in src/main/resources/testSuiteXmls folder
		FileUtil.generateXmlFile(SUITE_XML_PATH + File.separator + "dynamic_suite.xml", suite.toXml());

		TestNG tng = new TestNG();
		List<Class<? extends ITestNGListener>> listenerClasses = Arrays.asList(CustomReporter.class);
		List<String> suitesToRun = Arrays.asList(SUITE_XML_PATH + File.separator + "dynamic_suite.xml");
		tng.setTestSuites(suitesToRun); // setting suites
		/* tng.setXmlSuites(suites); */
		tng.setListenerClasses(listenerClasses); // setting listener classes.
		tng.run();
	}

}
