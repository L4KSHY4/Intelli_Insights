package com.tii.utils.common;

import static com.tii.utils.common.Constants.LOG_DESIGN;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * It contains utility methods for performing file related operations (e.g
 * create,read etc.).
 *
 */
public class FileUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * It creates directory or folder at provided path
	 * 
	 * @param directoryPath
	 */
	public static void createDirectory(String directoryPath) {
		LOGGER.info(LOG_DESIGN + "Creating directory : {}", directoryPath);
		File directory = new File(directoryPath);
		if (!directory.exists())
			directory.mkdirs();
	}

	/**
	 * It will generate a xml file with the provided xml string.
	 * 
	 * @param file
	 *            complete file name e.g src//main//resources//dynamicXml.xml i.e
	 *            where we want to create the file along with its name.
	 * @param xmlString
	 */
	public static void generateXmlFile(String file, String xmlString) {
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(xmlString);
			fw.close();
			LOGGER.info(LOG_DESIGN + "Successfully generated XML file : {}", file);
		} catch (IOException e) {
			LOGGER.error(LOG_DESIGN + "!!!! Exception occurred while copying xml string to file : {}", e.getMessage());
		}
	}

	/**
	 * It will read a csv and will put its content into a list [It will also remove
	 * the double quotes while reading csv].
	 * 
	 * @param file
	 *            CSV file
	 * @return list
	 */
	public static List<String> readCSVToList(File file) {
		BufferedReader reader;
		List<String> lines = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine().replace("\"", "")) != null) {
				lines.add(line);
			}
			reader.close();
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}

		return lines;
	}

}
