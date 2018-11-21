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

	public static void processNode(JsonNode node, String nodeName, Element parent, Document doc) {
		// Process parent node
		if (null == parent) {
			String parentName = node.getNodeType().name();
			parent = XMLGenerationUtil.createParent(parentName, null, doc, node.asText());
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
			final String nextNodeName = (iter.hasNext()) ? iter.next() : null;
			Consumer<JsonNode> jsonNodeConsumer = (JsonNode localNode) -> processNode(localNode, nextNodeName,
					tempParent, doc);
			node.forEach(jsonNodeConsumer);
		}
	}

	private static void processChildNode(JsonNode node, String nodeName, Element parent, Document doc) {
		XMLGenerationUtil.createChlild(doc, node.getNodeType().name(), nodeName, parent, node.asText());
	}

}
