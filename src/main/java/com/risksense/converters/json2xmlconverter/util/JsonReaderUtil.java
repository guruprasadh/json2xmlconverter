package com.risksense.converters.json2xmlconverter.util;

import java.util.Iterator;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonReaderUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonReaderUtil.class);

	/**
	 * Iterates over each and every field in the given Json node recursively and
	 * processes every field based on the type
	 * 
	 * @param node
	 * @param nodeName
	 * @param parent
	 * @param doc
	 */
	public static void processNode(JsonNode node, String nodeName, Element parent, Document doc) {
		// Process parent node
		if (null == parent) {
			String parentName = node.getNodeType().name();
			parent = XMLGenerationUtil.createParent(parentName, doc, node.asText());
			LOGGER.info("Created Parent node");
		}
		// Process children
		if (node.isValueNode()) {
			processChildNode(node, nodeName, parent, doc);
		} else {
			final Element tempParent = XMLGenerationUtil.createChlild(doc, node.getNodeType().name(), nodeName, parent,
					null);
			// JSON Arrays will return null for iterator
			Iterator<String> iter = node.fieldNames();
			Consumer<JsonNode> jsonNodeConsumer = (JsonNode localNode) -> processNode(localNode,
					(iter.hasNext()) ? iter.next() : null, tempParent, doc);
			node.forEach(jsonNodeConsumer);
		}
	}

	/**
	 * This method is used to process the child node
	 * 
	 * @param node
	 * @param nodeName
	 * @param parent
	 * @param doc
	 */
	private static void processChildNode(JsonNode node, String nodeName, Element parent, Document doc) {
		XMLGenerationUtil.createChlild(doc, node.getNodeType().name(), nodeName, parent, node.asText());
	}

}
