package com.terry.springjpa.config.root;

import javax.naming.NamingException;
import javax.sql.DataSource;

import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jndi.JndiTemplate;

import com.terry.springjpa.common.annotation.ProfileProduction;

@Configuration
@ProfileProduction
//@Profile("production")
@PropertySource(value={"classpath:/com/terry/springjpa/resources/production/properties/root/environment.properties"
					, "classpath:/com/terry/springjpa/resources/production/properties/root/jpa.properties"
})
public class ProductionConfig {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 운영용 DataSource
	 * SQL 로그 찍는 DataSource가 아닌 순수 Jndi DataSource
	 * @return
	 * @throws NamingException
	 */
	/*
	@Bean
	public DataSource dataSource() throws NamingException{
		DataSource dataSource = null;
		JndiTemplate jndi = new JndiTemplate();
		dataSource = (DataSource)jndi.lookup("java:comp/env/jdbc/mytest");
		return dataSource;
	}
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
}
