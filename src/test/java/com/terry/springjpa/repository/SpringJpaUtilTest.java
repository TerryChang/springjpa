package com.terry.springjpa.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.testng.Assert.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.Test;

import com.terry.springjpa.common.util.Util;
import com.terry.springjpa.config.root.RootContextMain;
import com.terry.springjpa.entity.Member;

@ContextConfiguration(classes={RootContextMain.class})
@ActiveProfiles("local")
@TransactionConfiguration
public class SpringJpaUtilTest  extends AbstractTransactionalTestNGSpringContextTests {

private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void checkEntityOrNot_메소드_테스트_Entity체크(){
		Member testEntity = em.find(Member.class, 2L);
		String simpleClassName = testEntity.getClass().getSimpleName();
		logger.debug("Simple Class Name : " + simpleClassName);
		boolean check = Util.checkEntityOrNot(testEntity, "Member");
		assertEquals(true, check, "checkEntityOrNot_메소드_테스트_Entity체크_테스트 실패");
	}
	
	@Test
	public void checkEntityOrNot_메소드_테스트_Proxy체크(){
		Member testEntity = em.getReference(Member.class, 2L);
		String simpleClassName = testEntity.getClass().getSimpleName();
		logger.debug("Simple Class Name : " + simpleClassName);
		boolean check = Util.checkEntityOrNot(testEntity, "Member");
		assertEquals(false, check, "checkEntityOrNot_메소드_테스트_Proxy체크_테스트 실패");
	}
}
