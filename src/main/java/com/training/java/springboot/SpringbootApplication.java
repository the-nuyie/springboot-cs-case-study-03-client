package com.training.java.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.TimeZone;

@SpringBootApplication
		//(scanBasePackages = {"com.cs.backend", "com.training.java.springboot"})
public class SpringbootApplication extends SpringBootServletInitializer {
	// If we want to use servlet, must extends SpringBootServletInitializer
	// and override method configure

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringbootApplication.class);
	}

	public static void main(String[] args) {
		// TimeZone.setDefault(TimeZone.getTimeZone("GMT+07:00"));
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
