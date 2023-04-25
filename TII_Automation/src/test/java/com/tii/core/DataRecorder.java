package com.tii.core;

import static com.tii.utils.common.Constants.CONFIG_FOLDER_PATH;
import static com.tii.utils.common.Constants.LOG_DESIGN;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.tii.utils.common.ExcelUtils;;

/**
 * This class is responsible for recording the data from the excel data file.
 *
 */
public class DataRecorder {

	public static Gson gson;
	private static final Logger LOGGER = LoggerFactory.getLogger(DataRecorder.class);
	public static final String dataFilePath = CONFIG_FOLDER_PATH + File.separator + "TestData.xlsx";

	public static Map<String, Map<String, String>> TESTSCRIPTS_CACHE = new LinkedHashMap<>();
	public static Map<String, Map<String, String>> TC_MAPPING_CACHE = new LinkedHashMap<>();
	public static Map<String, Map<String, String>> TESTDATA_CACHE = new LinkedHashMap<>();
	public static Map<String, Map<String, String>> PROFILES_CACHE = new LinkedHashMap<>();

	/**
	 * It will record all the data from data sheets in respective maps.
	 */
	public static void recordData() {
		TESTSCRIPTS_CACHE = ExcelUtils.getSheetData(dataFilePath, "TestScripts");
		TC_MAPPING_CACHE = ExcelUtils.getSheetData(dataFilePath, "TC_MAPPING");
		TESTDATA_CACHE = ExcelUtils.getSheetData(dataFilePath, "TESTDATA");
		PROFILES_CACHE = ExcelUtils.getSheetData(dataFilePath, "Profiles");
	}

	/**
	 * It will return test case data based on its test id or TC_ID as defined in
	 * TC_MAPPING sheet.
	 * 
	 * @param tcId
	 *            test case id e.g "ADMIN_TEST_100"
	 * @return test data map
	 */
	public static Map<String, String> getTestData(String tcId) {
		String dataId = TC_MAPPING_CACHE.get(tcId).get("DATA_ID").isEmpty() ? "DEFAULT_DATA"
				: TC_MAPPING_CACHE.get(tcId).get("DATA_ID");
		Map<String, String> testData = TESTDATA_CACHE.get(dataId);
		LOGGER.info(LOG_DESIGN + "TEST DATA : {}", testData);

		return testData.entrySet().stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	/**
	 * It will return data of TC_MAPPING sheet.
	 * 
	 * @param tcId
	 * @return TC_MAPPING map
	 */
	public static Map<String, String> getTCMappingData(String tcId) {
		Map<String, String> tcMappingData = TC_MAPPING_CACHE.get(tcId);
		LOGGER.info(LOG_DESIGN + "TC_MAPPING DATA : {}", tcMappingData);

		return tcMappingData.entrySet().stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	/**
	 * It will return data of Profiles sheet.
	 * 
	 * @param profileName
	 *            e.g ADMIN, CAPACITY_MANAGER etc.
	 * @return Profiles map
	 */
	public static Map<String, String> getProfileData(String profileName) {
		Map<String, String> tcMappingData = PROFILES_CACHE.get(profileName);

		return tcMappingData.entrySet().stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	/**
	 * It will convert the JSON string in key value format. For e.g JSON String is
	 * {1=hello,2=bye,3=happy} you can get it using jsonObject.get(1) etc.
	 * 
	 * @param validJsonString
	 * @return JsonObject
	 */
	public static HashMap<String, String> getJsonKeyValuePair(String validJsonString) {
		if (Objects.isNull(gson)) {
			gson = new Gson();
		}
		return gson.fromJson(validJsonString, HashMap.class);
	}

}
