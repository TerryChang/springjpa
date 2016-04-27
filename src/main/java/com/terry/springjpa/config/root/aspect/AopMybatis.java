package com.terry.springjpa.config.root.aspect;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class AopMybatis {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	EntityManager em;
	
	@Pointcut("execution(* org.mybatis.spring.SqlSessionTemplate.*(..))")
	public void mybatisOperation(){}
	
	@Before("mybatisOperation()")
	public void doBeforeMybatisOperation(JoinPoint thisJoinPoint){
		em.flush();
		String className = thisJoinPoint.getClass().getName();
		String methodName = thisJoinPoint.getSignature().getName();
		logger.debug("execution doBeforeMybatisOperation : className=" + className + ", methodName=" + methodName);
	}
}
