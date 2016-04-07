package com.terry.springjpa.repository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.terry.springjpa.config.root.RootContextMain;
import com.terry.springjpa.entity.BoardType;

@ContextConfiguration(classes={RootContextMain.class})
@ActiveProfiles("local")
@TransactionConfiguration
public class BoardTypeRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	BoardTypeRepository repository;
	
	@BeforeClass
	public void initTest(){
		for(int i=0; i < 12; i++){
			/*
			BoardType bt = new BoardType();
			bt.setBoardTypeName("테스트 Board 타입" + i);
			em.persist(bt);
			*/
			jdbcTemplate.update("insert into boardtype(idx, name) values (boardtype_sequence.nextval, ?)","테스트 Board 타입" + i);
		}
	}
	
	@Test
	public void 게시판타입등록() throws Exception {
		// Given
		BoardType bt = new BoardType();
		bt.setBoardTypeName("테스트 Board 타입");
		
		// When
		repository.saveAndFlush(bt);
		
		// Then
		BoardType selectbt = em.find(BoardType.class, bt.getIdx());
		assertEquals(bt, selectbt, "BoardType 등록 실패");
	}
	
	@Test
	public void 게시판타입수정() throws Exception {
		//Given
		BoardType bt = new BoardType();
		bt.setIdx(2L);
		bt.setBoardTypeName("테스트 Board 타입1_수정");
		
		// When
		BoardType selectbt = em.find(BoardType.class, 2L);
		selectbt.setBoardTypeName("테스트 Board 타입1_수정");
		repository.saveAndFlush(selectbt);
		
		// Then
		assertEquals(bt, selectbt, "BoardType 수정 실패");
	}
	
	@Test
	public void 게시판타입삭제() throws Exception {
		//Given
		/*BoardType bt = new BoardType();
		bt.setIdx(2L);
		bt.setBoardTypeName("테스트 Board 타입1_수정");*/
		
		// When
		repository.delete(2L);
		repository.flush();
		
		// Then
		BoardType selectbt = em.find(BoardType.class, 2L);
		
		assertNull(selectbt, "BoardType 수정 실패");
	}
	
	@Test
	public void 게시판타입목록조회_idx정순() throws Exception {
		
		// Given
		List<BoardType> list = new ArrayList<BoardType>();
		for(int i=10; i < 12; i++){
			BoardType bt = new BoardType();
			bt.setIdx((long)i+1);
			bt.setBoardTypeName("테스트 Board 타입" + i);
			list.add(bt);
		}
		
		// When
		// 2페이지 조회
		Pageable pageable = new PageRequest(1, 10, new Sort(Direction.ASC, "idx"));
		Page<BoardType> result = repository.findAll(pageable);
		
		// Then
		// idx정순조회이기 때문에 2페이지에는 idx값이 11, 12인 것이 들어가 있어야 한다
		assertEquals(12, result.getTotalElements(), "게시판타입목록조회_idx정순 전체레코드 갯수 체크 실패");
		assertEquals(list, result.getContent(), "게시판타입목록조회_idx정순 조회 내용 체크 실패");
		
	}
	
	@Test
	public void 게시판타입목록조회_idx역순() throws Exception {
		
		// Given
		List<BoardType> list = new ArrayList<BoardType>();
		for(int i=2; i > 0; i--){
			BoardType bt = new BoardType();
			bt.setIdx((long)i);
			bt.setBoardTypeName("테스트 Board 타입" + (i-1));
			list.add(bt);
		}
		
		// When
		// 2페이지 조회
		Pageable pageable = new PageRequest(1, 10, new Sort(Direction.DESC, "idx"));
		Page<BoardType> result = repository.findAll(pageable);
		
		// Then
		// idx역순조회이기 때문에 2페이지에는 idx값이 2, 1인 것이 들어가 있어야 한다
		assertEquals(12, result.getTotalElements(), "게시판타입목록조회_idx역순 전체레코드 갯수 체크 실패");
		assertEquals(list, result.getContent(), "게시판타입목록조회_idx역순 조회 내용 체크 실패");
		
	}
	
	@Test
	public void 게시판타입상세조회() throws Exception {
		// Given
		BoardType bt = new BoardType();
		bt.setIdx(2L);
		bt.setBoardTypeName("테스트 Board 타입1");
		
		// When
		BoardType selectBt = repository.findOne(2L);
		
		// Then
		assertEquals(bt, selectBt, "게시판타입상세조회 실패");
		
	}
	
	@AfterClass
	public void finalizeTest(){
		deleteFromTables("boardtype");
	}
}
