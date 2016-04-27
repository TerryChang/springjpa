package com.terry.springjpa.config.root.aspect;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class AopMybatis {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	EntityManager em;
	
	// @Pointcut("@target(com.terry.springjpa.common.annotation.FirstEntityManagerFlush) || @within(com.terry.springjpa.common.annotation.FirstEntityManagerFlush)")
	// private void firstEntityManagerFlush(){}
	
	// @Before(value = "firstEntityManagerFlush")
	@Before("@target(com.terry.springjpa.common.annotation.FirstEntityManagerFlush) || @within(com.terry.springjpa.common.annotation.FirstEntityManagerFlush)")
	public void doBeforeMybatisDaoMethod(JoinPoint thisJoinPoint){
		em.flush();
		String className = thisJoinPoint.getClass().getName();
		String methodName = thisJoinPoint.getSignature().getName();
		logger.debug("execution doBeforeMybatisDaoMethod : className=" + className + ", methodName=" + methodName);
	}
}
