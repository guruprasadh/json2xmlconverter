package com.risksense.converters.json2xmlconverter.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.w3c.dom.Document;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(JUnit4.class)
public class JsonReaderUtilTest {

	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testProcessNodeWithArray() throws IOException, ParserConfigurationException {
		String input = "[1,21,3,\"asd\"]";
		Document doc = getParentDocument();
		JsonReaderUtil.processNode(mapper.readTree(input.getBytes()), null, null, doc);
		assertEquals("array", doc.getFirstChild().getLastChild().getNodeName());
	}

	@Test
	public void testProcessNodeWithObject() throws IOException, ParserConfigurationException {
		String input = "{\"name\":\"hello\",\"nme1\":\"qwe\"}";
		Document doc = getParentDocument();
		JsonReaderUtil.processNode(mapper.readTree(input.getBytes()), null, null, doc);
		assertEquals("object", doc.getFirstChild().getLastChild().getNodeName());
	}

	private Document getParentDocument() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		docBuilder = docFactory.newDocumentBuilder();
		return docBuilder.newDocument();
	}

}
