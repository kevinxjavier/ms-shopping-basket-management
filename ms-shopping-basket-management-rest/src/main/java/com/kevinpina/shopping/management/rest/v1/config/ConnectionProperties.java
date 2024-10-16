package com.kevinpina.shopping.management.rest.v1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
public class ConnectionProperties {

	@Bean
	@ConfigurationProperties(prefix = "csv.remote")
	public Map<String, String> getProperties() {
		return new HashMap<>();
	}
}
