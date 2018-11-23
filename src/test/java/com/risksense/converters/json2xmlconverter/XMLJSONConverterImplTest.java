package com.risksense.converters.json2xmlconverter;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.risksense.converters.ConverterFactory;
import com.risksense.converters.XMLJSONConverterI;

@RunWith(JUnit4.class)
public class XMLJSONConverterImplTest {

	private XMLJSONConverterI xmljsonConverter = ConverterFactory.createXMLJSONConverter();

	@Test
	public void testConvertJSONtoXML() throws IOException, URISyntaxException {
		File input = Paths.get(ClassLoader.getSystemResource("example.json").toURI()).toFile();
		File output = Paths.get(ClassLoader.getSystemResource("example.XML").toURI()).toFile();
		Files.write(output.toPath(), "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		xmljsonConverter.convertJSONtoXML(input, output);
		String content = new String(Files.readAllBytes(Paths.get(output.getPath())));
		assertTrue(content.contains("security_related"));
	}

	@Test
	public void testConvertJSONtoXMLWithEmpty() throws IOException, URISyntaxException {
		File input = Paths.get(ClassLoader.getSystemResource("example1.json").toURI()).toFile();
		File output = Paths.get(ClassLoader.getSystemResource("example1.XML").toURI()).toFile();
		Files.write(output.toPath(), "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		xmljsonConverter.convertJSONtoXML(input, output);
		String content = new String(Files.readAllBytes(Paths.get(output.getPath())));
		assertTrue(("").equals(content));
	}

	@Test
	public void testConvertJSONtoXMLWithArray() throws IOException, URISyntaxException {
		File input = Paths.get(ClassLoader.getSystemResource("example-array.json").toURI()).toFile();
		File output = Paths.get(ClassLoader.getSystemResource("example-array.XML").toURI()).toFile();
		Files.write(output.toPath(), "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		xmljsonConverter.convertJSONtoXML(input, output);
		String content = new String(Files.readAllBytes(Paths.get(output.getPath())));
		assertTrue(content.contains("<array>"));
	}

	@Test
	public void testConvertJSONtoXMLWithNull() throws IOException, URISyntaxException {
		File input = Paths.get(ClassLoader.getSystemResource("example-null.json").toURI()).toFile();
		File output = Paths.get(ClassLoader.getSystemResource("example-null.XML").toURI()).toFile();
		Files.write(output.toPath(), "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		xmljsonConverter.convertJSONtoXML(input, output);
		String content = new String(Files.readAllBytes(Paths.get(output.getPath())));
		assertTrue(content.contains("<null/>"));
	}

}
