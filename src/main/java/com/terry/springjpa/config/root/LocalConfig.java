package com.terry.springjpa.config.root;

import java.io.IOException;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.terry.springjpa.common.annotation.ProfileLocal;

import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;

@Configuration
@ProfileLocal
// @Profile("local")
@PropertySource(value={"classpath:/com/terry/springjpa/resources/local/properties/root/environment.properties"})
public class LocalConfig {

private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
	config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        config.setConnectionTestQuery("SELECT NOW()");
        config.addDataSourceProperty("URL", "jdbc:h2:~/someDatabase;MODE=MySQL");
        config.addDataSourceProperty("user", "someUser");
        config.addDataSourceProperty("password", "somePassword");
	 */



	/**
	 * 개발용 DataSource
	 * SQL 로그를 찍기 위한 기능이 들어가 있다
	 * @return
	 * @throws NamingException
	 */
	/*
	@Bean
	public DataSource dataSource() throws NamingException{
		DataSource dataSource = null;
		HikariConfig config = new HikariConfig();
		// config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
		config.setDriverClassName("org.h2.Driver");
		config.setJdbcUrl("jdbc:h2:tcp://localhost/~/springjpa;MVCC=TRUE");
		config.setUsername("springjpa");
		config.setPassword("springjpa");
		config.setAutoCommit(false);

		HikariDataSource ds = new HikariDataSource(config);
		dataSource = new DataSourceSpy(ds);
		
		return dataSource;
	}
	*/
	
	@Bean
	public DataSource dataSource(){
		DataSource dataSource = null;
		EmbeddedDatabaseBuilder edb = new EmbeddedDatabaseBuilder();
		edb.setType(EmbeddedDatabaseType.H2);
		edb.addScript("classpath:com/terry/springjpa/resources/local/ddlscript/createdb.sql");
		EmbeddedDatabase ed = edb.build();
		dataSource = new DataSourceSpy(ed);
		return dataSource;
		
		/*
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:com/terry/springjpa/resources/local/ddlscript/createdb.sql")
				.build();
		*/
	}
	
	@Bean
	public Properties jpaProperties() throws IOException{
		Resource resource = new ClassPathResource("com/terry/springjpa/resources/local/properties/root/jpa.properties");
		EncodedResource er = new EncodedResource(resource, "UTF-8");
		Properties properties = PropertiesLoaderUtils.loadProperties(er);
		return properties;
	}
}
