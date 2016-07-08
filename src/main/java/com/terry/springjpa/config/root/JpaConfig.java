package com.terry.springjpa.config.root;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = {"com.terry.springjpa.repository"})
public class JpaConfig {
	
	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource, @Qualifier("jpaProperties") Properties jpaProperties){
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
}
