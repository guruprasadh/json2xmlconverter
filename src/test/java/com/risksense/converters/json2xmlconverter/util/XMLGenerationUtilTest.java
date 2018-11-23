package com.risksense.converters.json2xmlconverter.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@RunWith(JUnit4.class)
public class XMLGenerationUtilTest {

	@Test
	public void testCreateParent() throws ParserConfigurationException {
		Document doc = getParentDocument();
		Element parent = XMLGenerationUtil.createParent("object", doc, null);
		assertEquals("object", parent.getNodeName());
	}

	@Test
	public void testCreateChild() throws ParserConfigurationException {
		Document doc = getParentDocument();
		Element parent = XMLGenerationUtil.createParent("object", doc, null);
		XMLGenerationUtil.createChlild(doc, "string", "name1", parent, "test");
		assertNotNull(doc.getElementsByTagName("string"));
	}

	private Document getParentDocument() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		docBuilder = docFactory.newDocumentBuilder();
		return docBuilder.newDocument();
	}

}
