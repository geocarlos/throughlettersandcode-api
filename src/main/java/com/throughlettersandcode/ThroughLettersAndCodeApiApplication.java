package com.throughlettersandcode;

import com.throughlettersandcode.config.property.ThroughLettersAndCodeApiProperty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(ThroughLettersAndCodeApiProperty.class)
public class ThroughLettersAndCodeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThroughLettersAndCodeApiApplication.class, args);
	}

}
