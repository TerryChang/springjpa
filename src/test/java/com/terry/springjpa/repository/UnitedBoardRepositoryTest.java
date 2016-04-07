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
import com.terry.springjpa.entity.Member;
import com.terry.springjpa.entity.UnitedBoard;
import com.terry.springjpa.entity.embed.InsertUpdateDT;

@ContextConfiguration(classes={RootContextMain.class})
@ActiveProfiles("local")
@TransactionConfiguration
public class UnitedBoardRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	UnitedBoardRepository repository;
	
	@BeforeClass
	public void initTest(){
		String sql1 = "insert into boardtype(idx, name, url, insertdt, updatedt) values (boardtype_sequence.nextval, ?, ?, current_timestamp(), null)";
		String sql2 = "insert into member(idx, loginid, password, name, insertdt, updatedt) "
				+ "values (member_sequence.nextval, ?, ?, ?, current_timestamp(), null)";
		String sql3 = "insert into unitedboard(idx, boardtype_idx, member_idx, title, contents, viewcnt, insertdt, updatedt)"
				+ "values (unitedboard_sequence.nextval, ?, ?, ?, ?, ?, current_timestamp(), null)";
		// 게시판 타입1 등록
		jdbcTemplate.update(sql1,"자유게시판", "http://localhost/sampleboard1.do");
		// 게시판 타입2 등록
		jdbcTemplate.update(sql1,"동영상 게시판", "http://localhost/sampleboard1.do");
		
		// 사용자1 등록
		jdbcTemplate.update(sql2, "loginId1", "loginPassword1", "이름1");
		// 사용자2 등록
		jdbcTemplate.update(sql2, "loginId2", "loginPassword2", "이름2");
		// 사용자3 등록
		jdbcTemplate.update(sql2, "loginId3", "loginPassword3", "이름3");
		
		// 게시판 타입1 글 12개(사용자1 3개, 사용자2 5개, 사용자3 4개) 등록
		jdbcTemplate.update(sql3, 1, 2, "자유게시판 제목1", "자유게시판 내용1", 0);
		jdbcTemplate.update(sql3, 1, 1, "자유게시판 제목2", "자유게시판 내용2", 0);
		jdbcTemplate.update(sql3, 1, 3, "자유게시판 제목3", "자유게시판 내용3", 0);
		jdbcTemplate.update(sql3, 1, 2, "자유게시판 제목4", "자유게시판 내용4", 0);
		jdbcTemplate.update(sql3, 1, 1, "자유게시판 제목5", "자유게시판 내용5", 0);
		jdbcTemplate.update(sql3, 1, 3, "자유게시판 제목6", "자유게시판 내용6", 0);
		jdbcTemplate.update(sql3, 1, 2, "자유게시판 제목7", "자유게시판 내용7", 0);
		jdbcTemplate.update(sql3, 1, 3, "자유게시판 제목8", "자유게시판 내용8", 0);
		jdbcTemplate.update(sql3, 1, 2, "자유게시판 제목9", "자유게시판 내용9", 0);
		jdbcTemplate.update(sql3, 1, 3, "자유게시판 제목10", "자유게시판 내용10", 0);
		jdbcTemplate.update(sql3, 1, 2, "자유게시판 제목11", "자유게시판 내용11", 0);
		jdbcTemplate.update(sql3, 1, 1, "자유게시판 제목12", "자유게시판 내용12", 0);
		
		// 게시판 타입2 글 15개(사용자1 4개, 사용자2 6개, 사용자3 5개) 등록
		jdbcTemplate.update(sql3, 2, 3, "동영상 게시판 제목1", "동영상 게시판 내용1", 0);
		jdbcTemplate.update(sql3, 2, 2, "동영상 게시판 제목2", "동영상 게시판 내용2", 0);
		jdbcTemplate.update(sql3, 2, 3, "동영상 게시판 제목3", "동영상 게시판 내용3", 0);
		jdbcTemplate.update(sql3, 2, 1, "동영상 게시판 제목4", "동영상 게시판 내용4", 0);
		jdbcTemplate.update(sql3, 2, 2, "동영상 게시판 제목5", "동영상 게시판 내용5", 0);
		jdbcTemplate.update(sql3, 2, 1, "동영상 게시판 제목6", "동영상 게시판 내용6", 0);
		jdbcTemplate.update(sql3, 2, 3, "동영상 게시판 제목7", "동영상 게시판 내용7", 0);
		jdbcTemplate.update(sql3, 2, 1, "동영상 게시판 제목8", "동영상 게시판 내용8", 0);
		jdbcTemplate.update(sql3, 2, 2, "동영상 게시판 제목9", "동영상 게시판 내용9", 0);
		jdbcTemplate.update(sql3, 2, 2, "동영상 게시판 제목10", "동영상 게시판 내용10", 0);
		jdbcTemplate.update(sql3, 2, 3, "동영상 게시판 제목11", "동영상 게시판 내용11", 0);
		jdbcTemplate.update(sql3, 2, 2, "동영상 게시판 제목12", "동영상 게시판 내용12", 0);
		jdbcTemplate.update(sql3, 2, 1, "동영상 게시판 제목13", "동영상 게시판 내용13", 0);
		jdbcTemplate.update(sql3, 2, 3, "동영상 게시판 제목14", "동영상 게시판 내용14", 0);
		jdbcTemplate.update(sql3, 2, 2, "동영상 게시판 제목15", "동영상 게시판 내용15", 0);
	}
	
	@Test
	public void 자유게시판_사용자1_글등록() throws Exception {
		// Given
		BoardType boardType = new BoardType();
		boardType.setIdx(1L);
		boardType.setBoardTypeName("자유게시판");
		Member member = new Member();
		member.setIdx(1L);
		member.setLoginId("loginId1");
		member.setPassword("loginPassword1");
		member.setName("이름1");
		UnitedBoard unitedBoard = new UnitedBoard();
		unitedBoard.setBoardType(boardType);
		unitedBoard.setMember(member);
		unitedBoard.setTitle("자유게시판 등록 테스트");
		unitedBoard.setContents("자유게시판 내용 테스트");
		unitedBoard.setViewCnt(0);
		
		// When
		repository.saveAndFlush(unitedBoard);
		
		// Then
		UnitedBoard selectUnitedBoard = em.find(UnitedBoard.class, unitedBoard.getIdx());
		assertEquals(unitedBoard, selectUnitedBoard, "자유게시판_사용자1_글등록 실패");
		
	}
	
	@Test
	public void 동영상게시판_사용자3_글등록() throws Exception {
		// Given
		BoardType boardType = new BoardType();
		boardType.setIdx(2L);
		boardType.setBoardTypeName("동영상 게시판");
		Member member = new Member();
		member.setIdx(3L);
		member.setLoginId("loginId3");
		member.setPassword("loginPassword3");
		member.setName("이름3");
		UnitedBoard unitedBoard = new UnitedBoard();
		unitedBoard.setBoardType(boardType);
		unitedBoard.setMember(member);
		unitedBoard.setTitle("동영상게시판 등록 테스트");
		unitedBoard.setContents("동영상게시판 내용 테스트");
		unitedBoard.setViewCnt(0);
		
		// When
		repository.saveAndFlush(unitedBoard);
		
		// Then
		UnitedBoard selectUnitedBoard = em.find(UnitedBoard.class, unitedBoard.getIdx());
		assertEquals(unitedBoard, selectUnitedBoard, "동영상게시판_사용자3_글등록 실패");
		
	}
	
	@Test
	public void 자유게시판_사용자2_글수정() throws Exception {
		// Given
		UnitedBoard selectBoard = repository.findUnitedBoard(4L);
		
		BoardType boardType = em.getReference(BoardType.class, 1L);
		Member member = em.getReference(Member.class, 2L);
		
		UnitedBoard unitedBoard = new UnitedBoard();
		unitedBoard.setIdx(4L);
		unitedBoard.setBoardType(boardType);
		unitedBoard.setMember(member);
		unitedBoard.setTitle("자유게시판 제목4 수정");
		unitedBoard.setContents("자유게시판 내용4 수정");
		unitedBoard.setViewCnt(0);
		
		// When
		repository.saveAndFlush(selectBoard);
		
		// Then
		assertEquals(selectBoard, unitedBoard, "자유게시판_사용자2_글수정 실패");
	}
	
	@Test
	public void 동영상게시판_사용자1_글수정() throws Exception {  
		// Given
		UnitedBoard selectBoard = repository.findUnitedBoard(20L);
		BoardType boardType = em.getReference(BoardType.class, 2L);
		Member member = em.getReference(Member.class, 1L);
		UnitedBoard unitedBoard = new UnitedBoard();
		unitedBoard.setIdx(20L);
		unitedBoard.setBoardType(boardType);
		unitedBoard.setMember(member);
		unitedBoard.setTitle("동영상 게시판 제목8 수정");
		unitedBoard.setContents("동영상 게시판 내용8 수정");
		unitedBoard.setViewCnt(0);
		unitedBoard.setInsertUpdateDT(new InsertUpdateDT());
		
		// When
		repository.saveAndFlush(unitedBoard);
		
		// Then
		assertEquals(selectBoard, unitedBoard, "동영상게시판_사용자1_글수정 실패");
	}
	
	@Test
	public void 동영상게시판_사용자1_글수정_실패() throws Exception {
		BoardType boardType = em.getReference(BoardType.class, 2L);
		Member member = em.getReference(Member.class, 1L);
		UnitedBoard unitedBoard = new UnitedBoard();
		unitedBoard.setIdx(20L);
		unitedBoard.setBoardType(boardType);
		unitedBoard.setMember(member);
		unitedBoard.setTitle("동영상 게시판 제목8 수정");
		unitedBoard.setContents("동영상 게시판 내용8 수정");
		unitedBoard.setViewCnt(0);
		unitedBoard.setInsertUpdateDT(new InsertUpdateDT());
		
		// When
		repository.saveAndFlush(unitedBoard);
		
		// Then
		UnitedBoard selectBoard = repository.findUnitedBoard(20L);
		assertEquals(selectBoard, unitedBoard, "동영상게시판_사용자1_글수정 실패");
	}
	
	@Test
	public void 자유게시판_사용자3_글삭제() throws Exception {
		// When
		repository.delete(3L);
		
		// Then
		UnitedBoard selectBoard = repository.findUnitedBoard(3L);
		assertNull(selectBoard, "자유게시판_사용자3_글삭제 실패");
	}
	
	@Test
	public void 동영상게시판_사용자2_글삭제() throws Exception {
		// When
		repository.delete(14L);
		
		// Then
		UnitedBoard selectBoard = repository.findUnitedBoard(14L);
		assertNull(selectBoard, "동영상게시판_사용자2_글삭제 실패");
	}
	
	@Test
	public void 자유게시판_사용자1_글목록조회() throws Exception {
		// Given
		BoardType boardType = em.find(BoardType.class, 1L); 
				
		Member member = new Member();
		member.setIdx(1L);
		member.setLoginId("loginId1");
		member.setPassword("loginPassword1");
		member.setName("이름1");
		List<UnitedBoard> compareList = new ArrayList<UnitedBoard>();
		UnitedBoard board1 = new UnitedBoard();
		board1.setIdx(12L);
		board1.setBoardType(boardType);
		board1.setMember(member);
		board1.setTitle("자유게시판 제목12");
		board1.setContents("자유게시판 내용12");
		board1.setViewCnt(0);
		UnitedBoard board2 = new UnitedBoard();
		board2.setIdx(5L);
		board2.setBoardType(boardType);
		board2.setMember(member);
		board2.setTitle("자유게시판 제목5");
		board2.setContents("자유게시판 내용5");
		board2.setViewCnt(0);
		UnitedBoard board3 = new UnitedBoard();
		board3.setIdx(2L);
		board3.setBoardType(boardType);
		board3.setMember(member);
		board3.setTitle("자유게시판 제목2");
		board3.setContents("자유게시판 내용2");
		board3.setViewCnt(0);
		compareList.add(board1);
		compareList.add(board2);
		compareList.add(board3);
		
		// When
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC, "idx"));
		Page<UnitedBoard> result = repository.findUserAll(1L, "loginId1", pageable);
		
		// Then
		assertEquals(3, result.getTotalElements(), "자유게시판_사용자1_글목록조회 실패");
		assertEquals(result.getContent(), compareList, "자유게시판_사용자1_글목록조회 실패");
	}
	
	@Test
	public void 동영상게시판_사용자3_글목록조회() throws Exception {
		// Given
		BoardType boardType = em.find(BoardType.class, 2L);
		
		Member member = new Member();
		member.setIdx(3L);
		member.setLoginId("loginId3");
		member.setPassword("loginPassword3");
		member.setName("이름3");
		List<UnitedBoard> compareList = new ArrayList<UnitedBoard>();
		UnitedBoard board1 = new UnitedBoard();
		board1.setIdx(26L);
		board1.setBoardType(boardType);
		board1.setMember(member);
		board1.setTitle("동영상 게시판 제목14");
		board1.setContents("동영상 게시판 내용14");
		board1.setViewCnt(0);
		UnitedBoard board2 = new UnitedBoard();
		board2.setIdx(23L);
		board2.setBoardType(boardType);
		board2.setMember(member);
		board2.setTitle("동영상 게시판 제목11");
		board2.setContents("동영상 게시판 내용11");
		board2.setViewCnt(0);
		UnitedBoard board3 = new UnitedBoard();
		board3.setIdx(19L);
		board3.setBoardType(boardType);
		board3.setMember(member);
		board3.setTitle("동영상 게시판 제목7");
		board3.setContents("동영상 게시판 내용7");
		board3.setViewCnt(0);
		UnitedBoard board4 = new UnitedBoard();
		board4.setIdx(15L);
		board4.setBoardType(boardType);
		board4.setMember(member);
		board4.setTitle("동영상 게시판 제목3");
		board4.setContents("동영상 게시판 내용3");
		board4.setViewCnt(0);
		UnitedBoard board5 = new UnitedBoard();
		board5.setIdx(13L);
		board5.setBoardType(boardType);
		board5.setMember(member);
		board5.setTitle("동영상 게시판 제목1");
		board5.setContents("동영상 게시판 내용1");
		board5.setViewCnt(0);
		compareList.add(board1);
		compareList.add(board2);
		compareList.add(board3);
		compareList.add(board4);
		compareList.add(board5);
		
		// When
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC, "idx"));
		Page<UnitedBoard> result = repository.findUserAll(2L, "loginId3", pageable);
		
		// Then
		assertEquals(5, result.getTotalElements(), "동영상게시판_사용자3_글목록조회 실패");
		assertEquals(result.getContent(), compareList, "동영상게시판_사용자3_글목록조회 실패");
	}
	
	@Test
	public void 자유게시판_사용자3_글조회() throws Exception {
		// Given
		BoardType boardType = em.find(BoardType.class, 1L);
		Member member = em.find(Member.class, 3L);
		
		UnitedBoard unitedBoard = new UnitedBoard();
		unitedBoard.setIdx(10L);
		unitedBoard.setBoardType(boardType);
		unitedBoard.setMember(member);
		unitedBoard.setTitle("자유게시판 제목10");
		unitedBoard.setContents("자유게시판 내용10");
		unitedBoard.setViewCnt(1);
		
		// When
		UnitedBoard selectBoard = repository.findUnitedBoard(10L);
		// 조회 카운트를 증가시킨다
		selectBoard.setViewCnt(selectBoard.getViewCnt()+1);
		repository.saveAndFlush(selectBoard);
		
		// Then
		assertEquals(selectBoard, unitedBoard, "자유게시판_사용자3_글조회 실패");
	}
	
	@Test
	public void 동영상게시판_사용자2_글조회() throws Exception {
		// Given
		BoardType boardType = em.find(BoardType.class, 2L);
		
		Member member = new Member();
		member.setIdx(2L);
		member.setLoginId("loginId2");
		member.setPassword("loginPassword2");
		member.setName("이름2");
		UnitedBoard unitedBoard = new UnitedBoard();
		unitedBoard.setIdx(17L);
		unitedBoard.setBoardType(boardType);
		unitedBoard.setMember(member);
		unitedBoard.setTitle("동영상 게시판 제목5");
		unitedBoard.setContents("동영상 게시판 내용5");
		unitedBoard.setViewCnt(1);
		
		// When
		UnitedBoard selectBoard = repository.findUnitedBoard(17L);
		// 조회 카운트를 증가시킨다
		selectBoard.setViewCnt(selectBoard.getViewCnt()+1);
		repository.saveAndFlush(selectBoard);
		
		// Then
		assertEquals(selectBoard, unitedBoard, "동영상게시판_사용자2_글조회 실패");
	}
	
	@Test
	public void 자유게시판_제목_글목록조회() throws Exception {
		// Given
		BoardType boardType = em.find(BoardType.class, 1L);
		
		List<UnitedBoard> compareList = new ArrayList<UnitedBoard>();
		
		Member member1 = new Member();
		member1.setIdx(1L);
		member1.setLoginId("loginId1");
		member1.setPassword("loginPassword1");
		member1.setName("이름1");
		UnitedBoard board1 = new UnitedBoard();
		board1.setIdx(12L);
		board1.setBoardType(boardType);
		board1.setMember(member1);
		board1.setTitle("자유게시판 제목12");
		board1.setContents("자유게시판 내용12");
		board1.setViewCnt(0);
		
		Member member2 = new Member();
		member2.setIdx(2L);
		member2.setLoginId("loginId2");
		member2.setPassword("loginPassword2");
		member2.setName("이름2");
		UnitedBoard board2 = new UnitedBoard();
		board2.setIdx(11L);
		board2.setBoardType(boardType);
		board2.setMember(member2);
		board2.setTitle("자유게시판 제목11");
		board2.setContents("자유게시판 내용11");
		board2.setViewCnt(0);
		
		Member member3 = new Member();
		member3.setIdx(3L);
		member3.setLoginId("loginId3");
		member3.setPassword("loginPassword3");
		member3.setName("이름3");
		UnitedBoard board3 = new UnitedBoard();
		board3.setIdx(10L);
		board3.setBoardType(boardType);
		board3.setMember(member3);
		board3.setTitle("자유게시판 제목10");
		board3.setContents("자유게시판 내용10");
		board3.setViewCnt(0);
		
		Member member4 = new Member();
		member4.setIdx(2L);
		member4.setLoginId("loginId2");
		member4.setPassword("loginPassword2");
		member4.setName("이름2");
		UnitedBoard board4 = new UnitedBoard();
		board4.setIdx(1L);
		board4.setBoardType(boardType);
		board4.setMember(member4);
		board4.setTitle("자유게시판 제목1");
		board4.setContents("자유게시판 내용1");
		board4.setViewCnt(0);
		compareList.add(board1);
		compareList.add(board2);
		compareList.add(board3);
		compareList.add(board4);
		
		// When
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC, "idx"));
		Page<UnitedBoard> result = repository.findTitleAll(1L, "자유게시판 제목1", pageable);
		
		// Then
		assertEquals(4, result.getTotalElements(), "자유게시판_사용자1_글목록조회 실패");
		assertEquals(result.getContent(), compareList, "자유게시판_사용자1_글목록조회 실패");
	}
	
	@Test
	public void 동영상게시판_내용_글목록조회() throws Exception {
		/*
		jdbcTemplate.update(sql3, 2, 3, "동영상 게시판 제목1", "동영상 게시판 내용1", 0);
		jdbcTemplate.update(sql3, 2, 2, "동영상 게시판 제목10", "동영상 게시판 내용10", 0);
		jdbcTemplate.update(sql3, 2, 3, "동영상 게시판 제목11", "동영상 게시판 내용11", 0);
		jdbcTemplate.update(sql3, 2, 2, "동영상 게시판 제목12", "동영상 게시판 내용12", 0);
		jdbcTemplate.update(sql3, 2, 1, "동영상 게시판 제목13", "동영상 게시판 내용13", 0);
		jdbcTemplate.update(sql3, 2, 3, "동영상 게시판 제목14", "동영상 게시판 내용14", 0);
		jdbcTemplate.update(sql3, 2, 2, "동영상 게시판 제목15", "동영상 게시판 내용15", 0);
		 */
		// Given
		BoardType boardType = em.find(BoardType.class, 2L);
		
		List<UnitedBoard> compareList = new ArrayList<UnitedBoard>();
		
		Member member1 = new Member();
		member1.setIdx(2L);
		member1.setLoginId("loginId2");
		member1.setPassword("loginPassword2");
		member1.setName("이름2");
		UnitedBoard board1 = new UnitedBoard();
		board1.setIdx(27L);
		board1.setBoardType(boardType);
		board1.setMember(member1);
		board1.setTitle("동영상 게시판 제목15");
		board1.setContents("동영상 게시판 내용15");
		board1.setViewCnt(0);
		
		Member member2 = new Member();
		member2.setIdx(3L);
		member2.setLoginId("loginId3");
		member2.setPassword("loginPassword3");
		member2.setName("이름3");
		UnitedBoard board2 = new UnitedBoard();
		board2.setIdx(26L);
		board2.setBoardType(boardType);
		board2.setMember(member2);
		board2.setTitle("동영상 게시판 제목14");
		board2.setContents("동영상 게시판 내용14");
		board2.setViewCnt(0);
		
		Member member3 = new Member();
		member3.setIdx(1L);
		member3.setLoginId("loginId1");
		member3.setPassword("loginPassword1");
		member3.setName("이름1");
		UnitedBoard board3 = new UnitedBoard();
		board3.setIdx(25L);
		board3.setBoardType(boardType);
		board3.setMember(member3);
		board3.setTitle("동영상 게시판 제목13");
		board3.setContents("동영상 게시판 내용13");
		board3.setViewCnt(0);
		
		Member member4 = new Member();
		member4.setIdx(2L);
		member4.setLoginId("loginId2");
		member4.setPassword("loginPassword2");
		member4.setName("이름2");
		UnitedBoard board4 = new UnitedBoard();
		board4.setIdx(24L);
		board4.setBoardType(boardType);
		board4.setMember(member4);
		board4.setTitle("동영상 게시판 제목12");
		board4.setContents("동영상 게시판 내용12");
		board4.setViewCnt(0);
		
		Member member5 = new Member();
		member5.setIdx(3L);
		member5.setLoginId("loginId3");
		member5.setPassword("loginPassword3");
		member5.setName("이름3");
		UnitedBoard board5 = new UnitedBoard();
		board5.setIdx(23L);
		board5.setBoardType(boardType);
		board5.setMember(member5);
		board5.setTitle("동영상 게시판 제목11");
		board5.setContents("동영상 게시판 내용11");
		board5.setViewCnt(0);
		
		Member member6 = new Member();
		member6.setIdx(2L);
		member6.setLoginId("loginId2");
		member6.setPassword("loginPassword2");
		member6.setName("이름2");
		UnitedBoard board6 = new UnitedBoard();
		board6.setIdx(22L);
		board6.setBoardType(boardType);
		board6.setMember(member6);
		board6.setTitle("동영상 게시판 제목10");
		board6.setContents("동영상 게시판 내용10");
		board6.setViewCnt(0);
		
		Member member7 = new Member();
		member7.setIdx(3L);
		member7.setLoginId("loginId3");
		member7.setPassword("loginPassword3");
		member7.setName("이름3");
		UnitedBoard board7 = new UnitedBoard();
		board7.setIdx(13L);
		board7.setBoardType(boardType);
		board7.setMember(member7);
		board7.setTitle("동영상 게시판 제목1");
		board7.setContents("동영상 게시판 내용1");
		board7.setViewCnt(0);
		
		
		compareList.add(board1);
		compareList.add(board2);
		compareList.add(board3);
		compareList.add(board4);
		compareList.add(board5);
		compareList.add(board6);
		compareList.add(board7);
		
		// When
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC, "idx"));
		Page<UnitedBoard> result = repository.findContentsAll(2L, "동영상 게시판 내용1", pageable);
		
		// Then
		assertEquals(7, result.getTotalElements(), "동영상게시판_내용_글목록조회 실패");
		assertEquals(result.getContent(), compareList, "동영상게시판_내용_글목록조회 실패");
	}
	
	
	@AfterClass
	public void finalizeTest(){
		deleteFromTables("unitedboard", "member", "boardtype");
	}
}
