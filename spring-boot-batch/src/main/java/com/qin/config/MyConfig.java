package com.qin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {
	@Autowired
	private RestTemplateBuilder rtb;
	@Bean
	public RestTemplate getRTemplate() {
		return rtb.build();
	}
}
