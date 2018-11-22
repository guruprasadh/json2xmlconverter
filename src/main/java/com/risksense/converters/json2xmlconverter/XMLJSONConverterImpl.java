package com.risksense.converters.json2xmlconverter;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.risksense.converters.XMLJSONConverterI;
import com.risksense.converters.json2xmlconverter.util.JsonReaderUtil;

public class XMLJSONConverterImpl implements XMLJSONConverterI {

	private static ObjectMapper mapper = new ObjectMapper();
	private static final Logger LOGGER = LoggerFactory.getLogger(XMLJSONConverterI.class);

	@Override
	public void convertJSONtoXML(File json, File xml) throws IOException {
		LOGGER.info("Started processing");
		byte[] fileContent = Files.readAllBytes(json.toPath());
		String xmlContent = readJson(new String(fileContent));
		Files.write(xml.toPath(), xmlContent.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		LOGGER.info("Finished writing to xml file {}", xml.getName());
	}

	private String readJson(String jsonInput) {
		try {
			LOGGER.trace("File read success for the json string {}", jsonInput);
			Document doc = getParentDocument();
			JsonReaderUtil.processNode(mapper.readTree(jsonInput), null, null, doc);
			LOGGER.info("Finished processing Json node");
			return parseDocumentToString(doc);
		} catch (IOException e) {
			LOGGER.error(Json2XmlConverterConstants.INVALIDJSON_ERROR, " {}", e.getMessage());
			return Json2XmlConverterConstants.INVALIDJSON_ERROR;
		} catch (ParserConfigurationException e) {
			LOGGER.error(Json2XmlConverterConstants.DOCFACTORY_ERROR, " {}", e.getMessage());
			return Json2XmlConverterConstants.DOCFACTORY_ERROR;
		}

	}

	private String parseDocumentToString(Document doc) {
		try {
			LOGGER.trace("Document to string - begin");
			DOMSource domSource = new DOMSource(doc.getFirstChild().getLastChild());
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			LOGGER.trace("Document to string - end");
			return writer.toString();
		} catch (TransformerException e) {
			LOGGER.error(Json2XmlConverterConstants.XML2STRING_ERROR, " {}", e.getMessage());
			return Json2XmlConverterConstants.XML2STRING_ERROR;
		}
	}

	private Document getParentDocument() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		docBuilder = docFactory.newDocumentBuilder();
		return docBuilder.newDocument();
	}

}
