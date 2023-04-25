package com.tii.reporter;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tii.utils.common.Constants;
import com.tii.utils.common.ExcelUtils;

/**
 * This class is responsible for updating the test execution status in excel
 * sheet.
 *
 */
public class ExcelReporter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReporter.class);
	public static Map<String, String> TEST_RESULT_MAP = new HashMap<>();
	public static String originalFilePath = Constants.CONFIG_FOLDER_PATH + File.separator + "TestData.xlsx";
	public static String resultFilePath = Constants.CONFIG_FOLDER_PATH + File.separator + "TestData_WithResult.xlsx";
	public static int scenarioIdCellNo;
	public static int executionResultCellNo;
	public static int testIdCounter;

	/**
	 * It will update the test results to a new sheet
	 * 
	 * @param testResultMap
	 * @throws IOException
	 */
	public static void updateTestResults(Map<String, String> testResultMap) {
		LOGGER.info("{} Updating test results to sheet after execution finished {} ", resultFilePath);
		LOGGER.info(
				"###################################################### Test Result Map that will be updated in excel data sheet #################################################################");
		LOGGER.info("{}", testResultMap);
		LOGGER.info(
				"#######################################################################################################################################");
		try {
			File resultFile = new File(resultFilePath);
			if (resultFile.exists()) {
				LOGGER.info("Result excel file [{}] already existes. Deleting it first for fresh results.",
						resultFile.getAbsolutePath());
				FileUtils.forceDelete(resultFile);
				LOGGER.info("Old result file deleted successfully.");
			}
			FileUtils.copyFile(new File(originalFilePath), resultFile);
			FileInputStream fileInputStream = new FileInputStream(resultFile);
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheet("TC_MAPPING");
			int rowCount = sheet.getLastRowNum();
			System.out.println("###################----------rowCount"+rowCount);
			Iterator<Row> iterator = sheet.iterator();

			Map<String, Map<String, String>> tcMappingData = ExcelUtils.getSheetData(resultFilePath, "TC_MAPPING");
			List<String> tcIds = new ArrayList<>(tcMappingData.keySet());
			Map<String, String> sortedMapAccToIds = new LinkedHashMap<>();
			tcIds.forEach(id -> sortedMapAccToIds.put(id, testResultMap.get(id)));
			List<String> testIdKeys = new ArrayList<>(sortedMapAccToIds.keySet());

			// Add additional column for results
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				XSSFCellStyle style = workbook.createCellStyle();

				// setting color for newly created column headers
				XSSFColor myColor = new XSSFColor(Color.YELLOW);
				style.setFillForegroundColor(myColor);
				// style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				style.setBorderTop(BorderStyle.THIN);
				style.setBorderBottom(BorderStyle.THIN);
				style.setBorderLeft(BorderStyle.THIN);
				style.setBorderRight(BorderStyle.THIN);
								
				if (currentRow.getRowNum() == 0) {
					// creating column headers
					String columnName1 = "SCENARIO_ID";
					String columnName2 = "EXECUTION_RESULT";
					LOGGER.info("Creating new column :[{}]", columnName1);
					Cell cell = currentRow.createCell(currentRow.getLastCellNum(), CellType.STRING);
					cell.setCellValue("SCENARIO_ID");
					cell.setCellStyle(style);
					scenarioIdCellNo = cell.getColumnIndex();
					LOGGER.info("Creating new column :[{}]", columnName2);
					cell = currentRow.createCell(currentRow.getLastCellNum(), CellType.STRING);
					cell.setCellValue("EXECUTION_RESULT");
					cell.setCellStyle(style);
					executionResultCellNo = cell.getColumnIndex();
					//executionResultCellNo = maxCell;
				} else {
					String tcID = testIdKeys.get(testIdCounter);
					String testResult = Objects.isNull(sortedMapAccToIds.get(tcID)) ? "" : sortedMapAccToIds.get(tcID);
					String scId[] = tcID.split("_");
					String scenarioId = scId[scId.length - 1];
					currentRow.createCell(scenarioIdCellNo, CellType.STRING).setCellValue(scenarioId);
					Cell executionResultCell = currentRow.createCell(executionResultCellNo, CellType.STRING);
					// currentRow.createCell(executionResultCellNo,
					// CellType.STRING).setCellValue(testResult);

					// updating test result for each test id present in excel data sheet
					switch (testResult) {
					case "PASSED":
						LOGGER.info("Updating: [{}] [{}]", tcID, testResult);
						XSSFCellStyle stylePassed = workbook.createCellStyle();
						stylePassed.setFillForegroundColor(new XSSFColor(Color.GREEN.darker()));
						stylePassed.setFillPattern(FillPatternType.SOLID_FOREGROUND);
						executionResultCell.setCellStyle(stylePassed);
						executionResultCell.setCellValue(testResult);
						break;
					case "FAILED":
						LOGGER.info("Updating: [{}] [{}]", tcID, testResult);
						XSSFCellStyle styleFailed = workbook.createCellStyle();
						styleFailed.setFillForegroundColor(new XSSFColor(Color.RED));
						styleFailed.setFillPattern(FillPatternType.SOLID_FOREGROUND);
						executionResultCell.setCellStyle(styleFailed);
						executionResultCell.setCellValue(testResult);
						break;
					case "SKIPPED":
						LOGGER.info("Updating: [{}] [{}]", tcID, testResult);
						XSSFCellStyle styleSkipped = workbook.createCellStyle();
						styleSkipped.setFillForegroundColor(new XSSFColor(Color.ORANGE));
						styleSkipped.setFillPattern(FillPatternType.SOLID_FOREGROUND);
						executionResultCell.setCellStyle(styleSkipped);
						executionResultCell.setCellValue(testResult);
						break;
					}

					testIdCounter++;
				}
			}
			fileInputStream.close();
			FileOutputStream output_file = new FileOutputStream(resultFile);
			workbook.write(output_file);
			output_file.close();
			workbook.close();
		} catch (Exception e) {
			LOGGER.info("Error occurred while updating test result to Excel sheet : {}", e.getMessage());
		}

	}
}
