package com.risksense.converters.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.risksense.converters.ConverterFactory;
import com.risksense.converters.XMLJSONConverterI;
import com.risksense.converters.json2xmlconverter.Json2XmlConverterConstants;

/**
 * This controller exposes the Json2Xml conversion as a rest service
 *
 */
@RestController
public class Json2XmlController {

	private static final Logger LOGGER = LoggerFactory.getLogger(Json2XmlController.class);

	/**
	 * This is method holding the logic to read and process based on the input
	 * 
	 * @param input
	 * @param output
	 * @return
	 */
	@GetMapping("/json2xml")
	public String convertJson2Xml(@RequestParam(value = "json", required = true) String input,
			@RequestParam(value = "xml", required = true) String output) {
		LOGGER.info("Started processing");
		XMLJSONConverterI converter = ConverterFactory.createXMLJSONConverter();
		File inputFile = getInputFile(input);
		File outputFile = getOutputFile(output);
		if (inputFile != null && outputFile != null) {
			try {
				converter.convertJSONtoXML(inputFile, outputFile);
				return "Processing completed - please refer the output file";
			} catch (IOException e) {
				LOGGER.error("Error while reading/writing the file {}", e.getMessage());
				return "Error while reading/writing the file - please refer logs for more details";
			}
		} else {
			return Json2XmlConverterConstants.IONOTFOUND;
		}
	}

	/**
	 * Method to validate and load the given input file.
	 * 
	 * 
	 * @param input
	 * @return
	 */
	private File getInputFile(String input) {
		try {
			return ResourceUtils.getFile(input);
		} catch (FileNotFoundException e) {
			LOGGER.error("Input file not found {}", input);
			return null;
		}
	}

	/**
	 * Method to validate and load the given output file
	 * 
	 * @param output
	 * @return
	 */
	private File getOutputFile(String output) {
		try {
			File outputFile = ResourceUtils.getFile(output);
			if (!outputFile.exists()) {
				LOGGER.warn("The output file {} does not exist. Hence creating a new file", output);
				return Files.createFile(Paths.get(output)).toFile();
			}
			return outputFile;
		} catch (IOException e) {
			LOGGER.warn("The output file {} does not exist and also unable to create a new one at the given path",
					output);
			return null;
		}
	}
}
