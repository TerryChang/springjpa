package com.terry.springjpa.config.servlet;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.terry.springjpa.common.annotation.ProfileProduction;

@Configuration
@ProfileProduction
//@Profile("production")
@PropertySource(value={"classpath:/com/terry/springjpa/resources/production/properties/servlet/environment.properties"})
public class ProductionConfig {
	
}
