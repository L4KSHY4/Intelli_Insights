package com.tii.utils.common;

import static com.tii.utils.common.Constants.LOG_DESIGN;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * It contains all excel related operations.
 *
 */
public class ExcelUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

	/**
	 * It will return excel sheet data in a HashMap with first column value of a row
	 * as key and whole column values of a row as key-value pair. For e.g if we have
	 * headers as: Id, Name DATA_ID1 Brook than here DATA_ID1 is key and value is a
	 * map having key pair as {Id=DATA_ID1, Name=Brook} DATA_ID1={Id=DATA_ID1,
	 * Name=Brook}
	 * 
	 * @param workBookLocation
	 * @param sheetName
	 * @return sheetData in a map
	 */
	public static Map<String, Map<String, String>> getSheetData(String workBookLocation, String sheetName) {
		LOGGER.info(LOG_DESIGN + "Getting data of sheet: {} from workbook: {}", sheetName, workBookLocation);

		Map<String, Map<String, String>> sheetData = new LinkedHashMap<>();

		File inputFile = new File(workBookLocation);
		FileInputStream fileInputStream;
		XSSFWorkbook workbook = null;
		try {
			fileInputStream = new FileInputStream(inputFile);
			workbook = new XSSFWorkbook(fileInputStream);
		} catch (Exception e) {
			LOGGER.error(LOG_DESIGN + "Exception occurred while reading excel workbook : {}", e.getMessage());
		}

		XSSFSheet sheet = workbook.getSheet(sheetName);
		Map<String, Integer> headerMap = new LinkedHashMap<String, Integer>(); // Create map of headers with
																				// [name,index] as key value pair
		XSSFRow headerRow = sheet.getRow(0); // Get first row

		short minColIx = headerRow.getFirstCellNum(); // get the first column index for a row
		short maxColIx = headerRow.getLastCellNum(); // get the last column index for a row
		for (short colIx = minColIx; colIx < maxColIx; colIx++) { // loop from first to last index
			XSSFCell cell = headerRow.getCell(colIx); // get the cell
			headerMap.put(cell.getStringCellValue(), cell.getColumnIndex()); // add the cell contents (name of column)
		}

		// getting all headers in a list
		List<String> headerValues = headerMap.keySet().stream().collect(Collectors.toList());
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Map<String, String> rowData = new LinkedHashMap<>();
			if (row.getRowNum() != 0) {
				IntStream.range(0, headerMap.size()).forEach(index -> {
					row.getCell(index, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(CellType.STRING);
					rowData.put(headerValues.get(index), row.getCell(index).getStringCellValue());
				});
				// putting every row's first column value as key and rest as value in key-value
				// pair (colName,colValue)
				sheetData.put(rowData.entrySet().stream().findFirst().get().getValue(), rowData);
			}
		}
		LOGGER.info(LOG_DESIGN + "Recorded Sheet Data Successfully. : {}", sheetData);

		return sheetData;
	}

}
