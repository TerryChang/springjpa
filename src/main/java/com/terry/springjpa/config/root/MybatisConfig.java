package com.terry.springjpa.config.root;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.terry.springjpa.common.mybatis.CustomSqlSessionFactoryBean;

@Configuration
public class MybatisConfig {
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws IOException, Exception{
		CustomSqlSessionFactoryBean sqlSessionFactoryBean = null;
		SqlSessionFactory sqlSessionFactory = null;
		
		sqlSessionFactoryBean = new CustomSqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("com/terry/springjpa/resources/mybatis/config/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/com/terry/springjpa/resources/mybatis/mapper/**/*.xml"));
		sqlSessionFactoryBean.setCheckInterval(60000);			// 1분마다 한번씩 xml mapper 수정여부 체크
		sqlSessionFactory = sqlSessionFactoryBean.getObject();
		
		return sqlSessionFactory;
		
	}

	@Bean
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory){
		SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);		
		return template;
	}
}
