package com.terry.springjpa.config.root;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = {"com.terry.springjpa.repository"})
public class JpaConfig {

	@Autowired
	Environment environment;
	
	/*
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
		LocalContainerEntityManagerFactoryBean lcemf = new LocalContainerEntityManagerFactoryBean();
		lcemf.setDataSource(dataSource);
		lcemf.setPackagesToScan("com.terry.springjpa.entity");
		lcemf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lcemf.setJpaProperties(jpaProperties());
		
		return lcemf;
	}
	*/
	
	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource, @Qualifier("jpaProperties") Properties jpaProperties){
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);
        
		LocalContainerEntityManagerFactoryBean lcemf = new LocalContainerEntityManagerFactoryBean();
		lcemf.setDataSource(dataSource);
		lcemf.setPackagesToScan("com.terry.springjpa.entity");
		lcemf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lcemf.setJpaProperties(jpaProperties);
		lcemf.afterPropertiesSet();
		
		EntityManagerFactory emf = lcemf.getObject();
		
		return emf;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	/*
	private Properties jpaProperties(){
		Properties properties = new Properties();
		
		// properties.put("javax.persistence.schema-generation.database.action", "none");
		// properties.put("javax.persistence.schema-generation.scripts.action", environment.getRequiredProperty("javax.persistence.schema-generation.scripts.action"));
		// properties.put("javax.persistence.schema-generation.scripts.create-target", environment.getRequiredProperty("javax.persistence.schema-generation.scripts.create-target"));
		// properties.put("javax.persistence.schema-generation.scripts.drop-target", environment.getRequiredProperty("javax.persistence.schema-generation.scripts.drop-target"));
		// properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		// properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		// properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		// properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		
		
		properties.put("javax.persistence.schema-generation.scripts.action", "drop-and-create");
		properties.put("javax.persistence.schema-generation.scripts.create-source", "metadata");
		properties.put("javax.persistence.schema-generation.scripts.create-target", "src/main/resources/com/terry/springjpa/resources/local/ddlscript/create.ddl");
		properties.put("javax.persistence.schema-generation.scripts.drop-target", "src/main/resources/com/terry/springjpa/resources/local/ddlscript/drop.ddl");
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		
		return properties;
	}
	*/
}
