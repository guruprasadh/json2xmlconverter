package com.risksense.converters.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.risksense.converters.ConverterFactory;
import com.risksense.converters.XMLJSONConverterI;

@RestController
public class Json2XmlController {

	private static final Logger LOGGER = LoggerFactory.getLogger(Json2XmlController.class);
	
	@RequestMapping("/json2xml")
	public String convertJson2Xml(@RequestParam(value = "json", required = true) String input,
			@RequestParam(value = "xml", defaultValue = "output.xml") String output) {
		XMLJSONConverterI converter = ConverterFactory.createXMLJSONConverter();
		File inputFile = getInputFile(input);
		File outputFile = getOutputFile(output);
		if(input != null) {
			try {
				converter.convertJSONtoXML(inputFile, outputFile);
				return "Processing completed - please refer the output file";
			} catch (IOException e) {
				LOGGER.error("Error while reading/writing the file");
				return "Error while reading/writing the file - please refer logs for more details";
			}
		}else {
			return "Input file not found";
		}
	}
	
	private File getInputFile(String input) {
		try {
			return ResourceUtils.getFile(input);
		} catch (FileNotFoundException e) {
			LOGGER.error("Input file not found %s", input);
			return null;
		}
	}
	
	private File getOutputFile(String output) {
		try {
			return ResourceUtils.getFile(output);
		} catch (FileNotFoundException e) {
			LOGGER.warn("The output file %s does not exist. Hence creating a new file", output);
			return new File(output);
		}
	}
}
