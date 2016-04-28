package com.terry.springjpa.config.root;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.terry.springjpa.config.root.aspect.AopMybatis;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AopConfig {

	@Bean
	public AopMybatis aopMybatis(){
		return new AopMybatis(); 
	}
	
}
