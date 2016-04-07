package com.terry.springjpa.config.servlet;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.terry.springjpa.common.annotation.ProfileDev;

@Configuration
@ProfileDev
// @Profile("local")
@PropertySource("classpath:/com/terry/springjpa/resources/local/properties/servlet/environment.properties")
public class LocalConfig {

}
