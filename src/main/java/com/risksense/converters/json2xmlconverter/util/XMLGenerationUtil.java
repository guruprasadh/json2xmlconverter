package com.risksense.converters.json2xmlconverter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLGenerationUtil {

	private static final String ATTRIBUTE_ID = "name";

	private static final Logger LOGGER = LoggerFactory.getLogger(XMLGenerationUtil.class);

	/**
	 * This method creates a new root element as part of the given document object
	 * 
	 * @param parentName
	 * @param doc
	 * @param value
	 * @return
	 */
	public static Element createParent(String parentName, Document doc, String value) {
		Element rootElement = doc.createElement(parentName.toLowerCase());
		rootElement.setTextContent(value);
		doc.appendChild(rootElement);
		LOGGER.trace("Created Parent node for {}", parentName);
		return rootElement;
	}

	/**
	 * This method creates and populates a new child element and appends it with the
	 * given parent element
	 * 
	 * @param doc
	 * @param childName
	 * @param attributeValue
	 * @param parent
	 * @param value
	 * @return
	 */
	public static Element createChlild(Document doc, String childName, String attributeValue, Element parent,
			String value) {
		Element childElement = doc.createElement(childName.toLowerCase());
		if (null != value && !("null").equals(value)) {
			childElement.setTextContent(value);
		}
		if (null != attributeValue && !("").equals(attributeValue)) {
			childElement.setAttribute(ATTRIBUTE_ID, attributeValue);
		}
		parent.appendChild(childElement);
		LOGGER.trace("Created child node for {}", childName);
		return childElement;
	}

}
