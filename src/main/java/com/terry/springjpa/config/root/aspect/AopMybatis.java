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
	
	/*
	Mybatis 작업시 주된 근간이 되는 클래스는 SqlSessionTemplate 클래스이기 때문에 이 클래스를 사용할 경우에 대해
	EntityManager 객체의 flush 메소드를 실행시키려 할 경우 지금 주석처리된 이 AOP 를 사용한다
	@Pointcut("execution(* org.mybatis.spring.SqlSessionTemplate.*(..))")
	public void mybatisOperation(){}
	
	@Before("mybatisOperation()")
	public void doBeforeMybatisOperation(JoinPoint thisJoinPoint){
		em.flush();
		String className = thisJoinPoint.getClass().getName();
		String methodName = thisJoinPoint.getSignature().getName();
		logger.debug("execution doBeforeMybatisOperation : className=" + className + ", methodName=" + methodName);
	}
	*/
	
	/*
	Mybatis 작업시 EntityManager 객체의 flush 메소드를 먼저 호출시켜줘야 하는데..
	이 작업을 하게끔 만든 AOP 설정이다. 즉 클래스 레벨이나 메소드 레벨에 @FirstEntityManagerFlush 어노테이션을 붙이면
	이 AopMybatis 클래스에 Injection 받은 EntityManager 클래스 객체에서 flush 메소드를 호출해서 
	영속성 컨텍스트에서 가지고 있던 작업들의 쿼리를 DB에 날리게 된다
	anyFirstEntityManagerFlushClassType() 에 설정한 @Pointcut 내용은 @FirstEntityManagerFlush 어노테이션이 클래스 레벨에 붙었는지 체크하는 포인트컷이고
	anyFirstEntityManagerFlushMethodType() 에 설정한 @Pointcut 내용은 @FirstEntityManagerFlush 어노테이션이 메소드 레벨에 붙었는지 체크하는 포인트컷이다
	마지막에 @Before 어노테이션에서 이 둘의 메소드를 OR(||)조건으로 연결해주기 때문에 두 포인트컷 중 하나만 만족하면 
	@Before 어노테이션 관련 동작을 하게끔 설정한 것이다.
	
	작업과 관련된 내용은 아래 링크를 참조해서 만들었다
	http://www.java-allandsundry.com/2013/01/aspectj-pointcut-based-on-annotation-on.html 
	*/
	@Pointcut(value="execution(* (@com.terry.springjpa.common.annotation.FirstEntityManagerFlush com.terry.springjpa..*).*(..))")
    public void anyFirstEntityManagerFlushClassType() {}
	
	@Pointcut(value="execution(@com.terry.springjpa.common.annotation.FirstEntityManagerFlush * com.terry.springjpa..*.*(..))")
    public void anyFirstEntityManagerFlushMethodType() {}

	@Before("anyFirstEntityManagerFlushClassType() || anyFirstEntityManagerFlushMethodType()")
	public void doBeforeMybatisOperation(JoinPoint thisJoinPoint){
		em.flush();
		String className = thisJoinPoint.getClass().getName();
		String methodName = thisJoinPoint.getSignature().getName();
		logger.debug("execution doBeforeMybatisOperation : className=" + className + ", methodName=" + methodName);
	}
}
