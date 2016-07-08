package com.terry.springjpa.config.root;

import java.io.IOException;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jndi.JndiTemplate;

import com.terry.springjpa.common.annotation.ProfileDev;

@Configuration
@ProfileDev
// @Profile("dev")
@PropertySource(value={"classpath:/com/terry/springjpa/resources/dev/properties/root/environment.properties"
					, "classpath:/com/terry/springjpa/resources/dev/properties/root/jpa.properties"
})
public class DevConfig {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 개발용 DataSource
	 * SQL 로그를 찍기 위한 기능이 들어가 있다
	 * @return
	 * @throws NamingException
	 */
	@Bean
	public DataSource dataSource() throws NamingException{
		DataSource dataSource = null;
		DataSource jndiDataSource = null;
		JndiTemplate jndi = new JndiTemplate();
	
		try{
			jndiDataSource = (DataSource)jndi.lookup("java:comp/env/jdbc/mytest");
			dataSource = new DataSourceSpy(jndiDataSource);
		}catch(NamingException ne){
			logger.error("Cannot find DataSource JNDI Name : {}", "java:comp/env/jdbc/mytest");
			throw ne;
		}
		return dataSource;
	}
	
	@Bean
	public Properties jpaProperties() throws IOException{
		Resource resource = new ClassPathResource("com/terry/springjpa/resources/dev/properties/root/jpa.properties");
		EncodedResource er = new EncodedResource(resource, "UTF-8");
		Properties properties = PropertiesLoaderUtils.loadProperties(er);
		return properties;
	}
}
