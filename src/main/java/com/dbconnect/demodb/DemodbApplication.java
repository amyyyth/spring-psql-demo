package com.dbconnect.demodb;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@PropertySource(value = "file:C:\\Users\\amith\\Documents\\application.properties", ignoreResourceNotFound = true)
@SpringBootApplication
//@PropertySource(value = "file:C:/DPR/ums/application.properties", ignoreResourceNotFound = true)
public class DemodbApplication extends SpringBootServletInitializer implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}


	

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}


	private static Class<DemodbApplication> applicationClass = DemodbApplication.class;
}