package com.risksense.converters.json2xmlconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(value = "com.risksense.*")
public class Json2xmlconverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(Json2xmlconverterApplication.class, args);
	}
}
