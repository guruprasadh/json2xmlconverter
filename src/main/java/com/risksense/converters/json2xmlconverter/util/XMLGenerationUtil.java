package com.risksense.converters.json2xmlconverter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLGenerationUtil {

	private static final String ATTRIBUTE_ID = "name";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XMLGenerationUtil.class);

	public static Element createParent(String parentName, String attributeName, Document doc, String value) {
		Element rootElement = doc.createElement(parentName.toLowerCase());
		rootElement.setTextContent(value);
		doc.appendChild(rootElement);
		LOGGER.trace("Created Parent node for %s", parentName);
		return rootElement;
	}

	public static Element createChlild(Document doc, String childName, String attributeValue, Element parent, String value) {
		Element childElement = doc.createElement(childName.toLowerCase());
		if(null!=value && !("null").equals(value)) {
			childElement.setTextContent(value);
		}
		if (null != attributeValue && !("").equals(attributeValue)) {
			childElement.setAttribute(ATTRIBUTE_ID, attributeValue);
		}
		parent.appendChild(childElement);
		LOGGER.trace("Created child node for %s", childName);
		return childElement;
	}

}
