package com.terry.springjpa.repository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Projections;
import com.terry.springjpa.config.root.RootContextMain;
import com.terry.springjpa.entity.BoardType;
import com.terry.springjpa.entity.Member;
import com.terry.springjpa.entity.QBoardType;
import com.terry.springjpa.entity.QMember;
import com.terry.springjpa.entity.QUnitedBoard;
import com.terry.springjpa.entity.UnitedBoard;
import com.terry.springjpa.entity.embed.InsertUpdateDT;
import com.terry.springjpa.vo.SearchVO;
import com.terry.springjpa.vo.UnitedBoardVO;

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
		String sql2 = "insert into member(idx, loginid, password, name, email, insertdt, updatedt) "
				+ "values (member_sequence.nextval, ?, ?, ?, ?, current_timestamp(), null)";
		String sql3 = "insert into unitedboard(idx, boardtype_idx, member_idx, title, contents, viewcnt, insertdt, updatedt)"
				+ "values (unitedboard_sequence.nextval, ?, ?, ?, ?, ?, current_timestamp(), null)";
		// 게시판 타입1 등록
		jdbcTemplate.update(sql1,"자유게시판", "http://localhost/sampleboard1.do");
		// 게시판 타입2 등록
		jdbcTemplate.update(sql1,"동영상 게시판", "http://localhost/sampleboard1.do");
		
		// 사용자1 등록
		jdbcTemplate.update(sql2, "loginId1", "loginPassword1", "이름1", "user1@aaa.com");
		// 사용자2 등록
		jdbcTemplate.update(sql2, "loginId2", "loginPassword2", "이름2", "user2@aaa.com");
		// 사용자3 등록
		jdbcTemplate.update(sql2, "loginId3", "loginPassword3", "이름3", "user3@aaa.com");
		
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
		BoardType boardType = em.getReference(BoardType.class, 1L);
		Member member = em.getReference(Member.class, 1L);
		
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
		BoardType boardType = em.getReference(BoardType.class, 2L);
		Member member = em.getReference(Member.class, 3L);
		
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
		UnitedBoard selectBoard = em.getReference(UnitedBoard.class, 4L);
		selectBoard.setTitle("자유게시판 제목4 수정");
		selectBoard.setContents("자유게시판 내용4 수정");
		repository.saveAndFlush(selectBoard);
		
		// Then
		assertEquals(selectBoard, unitedBoard, "자유게시판_사용자2_글수정 실패");
	}
	
	@Test
	public void 동영상게시판_사용자1_글수정() throws Exception {  
		// Given
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
		UnitedBoard selectBoard = repository.findOne(20L);
		selectBoard.setTitle("동영상 게시판 제목8 수정");
		selectBoard.setContents("동영상 게시판 내용8 수정");
		
		repository.saveAndFlush(selectBoard);
		
		// Then
		assertEquals(selectBoard, unitedBoard, "동영상게시판_사용자1_글수정 실패");
	}
	
	/*
	  EntityManager 클래스의 find 메소드를 이용해서 엔티티를 찾거나
	  Spring Data JPA의 JpaRepository 인터페이스에서 제공하는 findOne을 이용해서 엔티티를 찾을 경우 
	  엔티티를 찾지 못하면 null이 리턴되지만
	  EntityManager 클래스의 getReference 메소드를 이용해서 엔티티를 찾거나
	  Spring Data JPA의 JpaRepository 인터페이스에서 제공하는 getOne을 이용해서 엔티티를 찾을 경우
	  (이 두 경우는 실제 엔티티가 아닌 프록시가 return 된다)
	  엔티티가 존재하지 않으면 EntityNotFoundException 예외가 발생한다
	  두 개를 직접 테스트 해보기 위해 동영상게시판_사용자2_글삭제 메소드는 프록시를 조회하게 하고 
	  예외를 던지는 테스트를 진행하도록 했다
	 */
	@Test
	public void 자유게시판_사용자3_글삭제() throws Exception {
		// When
		repository.delete(3L);
		
		// Then
		UnitedBoard selectBoard = em.find(UnitedBoard.class, 3L);
		assertNull(selectBoard, "자유게시판_사용자3_글삭제 실패");
	}
	
	@Test(expectedExceptions={EntityNotFoundException.class})
	public void 동영상게시판_사용자2_글삭제() throws Exception {
		// When
		repository.delete(14L);
		
		// Then
		UnitedBoard selectBoard = repository.getOne(14L);
		assertNull(selectBoard, "동영상게시판_사용자2_글삭제 실패");
	}
	
	@Test
	public void 게시판_글목록조회() throws Exception {
		// Given
		
		// When
		SearchVO searchVO = new SearchVO();
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC, "idx"));
		Page<UnitedBoardVO> result = repository.selectList(null, searchVO, pageable);
		
		// Then
		assertEquals(27, result.getTotalElements(), "게시판_글목록조회 실패");
	}
	
	@Test
	public void 자유게시판_사용자1_글목록조회() throws Exception {
		// Given
		
		List<UnitedBoardVO> compareList = new ArrayList<UnitedBoardVO>();
		UnitedBoardVO board1 = new UnitedBoardVO();
		board1.setIdx(12L);
		board1.setBoardTypeName("자유게시판");
		board1.setMemberLoginId("loginId1");
		board1.setTitle("자유게시판 제목12");
		// board1.setContents("자유게시판 내용12");
		board1.setViewCnt(0);
		UnitedBoardVO board2 = new UnitedBoardVO();
		board2.setIdx(5L);
		board2.setBoardTypeName("자유게시판");
		board2.setMemberLoginId("loginId1");
		board2.setTitle("자유게시판 제목5");
		// board2.setContents("자유게시판 내용5");
		board2.setViewCnt(0);
		UnitedBoardVO board3 = new UnitedBoardVO();
		board3.setIdx(2L);
		board3.setBoardTypeName("자유게시판");
		board3.setMemberLoginId("loginId1");
		board3.setTitle("자유게시판 제목2");
		// board3.setContents("자유게시판 내용2");
		board3.setViewCnt(0);
		compareList.add(board1);
		compareList.add(board2);
		compareList.add(board3);
		
		// When
		SearchVO searchVO = new SearchVO();
		searchVO.setSearchCnd("loginId");
		searchVO.setSearchWrd("loginId1");
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC, "idx"));
		Page<UnitedBoardVO> result = repository.selectList(1L, searchVO, pageable);
		
		// Then
		assertEquals(3, result.getTotalElements(), "자유게시판_사용자1_글목록조회 실패");
		assertEquals(result.getContent(), compareList, "자유게시판_사용자1_글목록조회 실패");
	}
	
	@Test
	public void QueryDSL_Fetch_Join_테스트(){
		
		QUnitedBoard unitedBoard = QUnitedBoard.unitedBoard;
		QBoardType boardType = QBoardType.boardType;
		QMember member = QMember.member;
		
		JPAQuery query = new JPAQuery(em);
		List<UnitedBoard> result = query.from(unitedBoard)
				.join(unitedBoard.boardType, boardType).fetch()
				.join(unitedBoard.member, member).fetch()
				.where(unitedBoard.member.loginId.eq("loginId1"))
				.list(unitedBoard);
		
		assertEquals(7, result.size(), "QueryDSL_Fetch_Join_테스트 실패");
		/*
		JPQLQuery query = from(unitedBoard);
		
		query = query.innerJoin(unitedBoard.boardType).fetch()
				.innerJoin(unitedBoard.member).fetch();
		
		long testCount = query.count();
		*/
		
	}
	
	@Test
	public void QueryDSL_Fetch_Join_Projections_테스트(){
		
		QUnitedBoard unitedBoard = QUnitedBoard.unitedBoard;
		QBoardType boardType = QBoardType.boardType;
		QMember member = QMember.member;
		
		JPAQuery query = new JPAQuery(em);
		List<UnitedBoardVO> result = query.from(unitedBoard)
				.innerJoin(unitedBoard.boardType, boardType).fetch()
				.where(unitedBoard.member.loginId.eq("loginId1"))
				.list(Projections.fields(UnitedBoardVO.class, boardType.boardTypeName, unitedBoard.title, unitedBoard.member.loginId.as("memberLoginId"), unitedBoard.viewCnt, unitedBoard.insertUpdateDT.insertDateTime));
		
		assertEquals(7, result.size(), "QueryDSL_Fetch_Join_Projections_테스트 실패");
		/*
		JPQLQuery query = from(unitedBoard);
		
		query = query.innerJoin(unitedBoard.boardType).fetch()
				.innerJoin(unitedBoard.member).fetch();
		
		long testCount = query.count();
		*/
		
	}
	
	@Test
	public void JPQL_Proxy_fettch_Join_테스트(){

		String strQuery = "select u from UnitedBoard u join fetch u.boardType "
				+ "join fetch u.member "
				+ "where u.member.loginId=?1 ";
		
		TypedQuery<UnitedBoard> query = em.createQuery(strQuery, UnitedBoard.class).setParameter(1, "loginId1");
		List<UnitedBoard> result = query.getResultList();
		
		assertEquals(7, result.size(), "JPQL_Proxy_fettch_Join_테스트 실패");
	}
	/*
	@Test
	public void 동영상게시판_사용자3_글목록조회() throws Exception {
		// Given
		BoardType boardType = em.find(BoardType.class, 2L);
		Member member = em.find(Member.class, 3L);
		
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
	public void 동영상게시판_사용자2_글조회() throws Exception {
		// Given
		BoardType boardType = em.find(BoardType.class, 2L);
		Member member = em.find(Member.class, 2L);
		
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
		Member member1 = em.find(Member.class, 1L);
		Member member2 = em.find(Member.class, 2L);
		Member member3 = em.find(Member.class, 3L);
		
		List<UnitedBoard> compareList = new ArrayList<UnitedBoard>();
		
		UnitedBoard board1 = new UnitedBoard();
		board1.setIdx(12L);
		board1.setBoardType(boardType);
		board1.setMember(member1);
		board1.setTitle("자유게시판 제목12");
		board1.setContents("자유게시판 내용12");
		board1.setViewCnt(0);
		
		UnitedBoard board2 = new UnitedBoard();
		board2.setIdx(11L);
		board2.setBoardType(boardType);
		board2.setMember(member2);
		board2.setTitle("자유게시판 제목11");
		board2.setContents("자유게시판 내용11");
		board2.setViewCnt(0);
		
		UnitedBoard board3 = new UnitedBoard();
		board3.setIdx(10L);
		board3.setBoardType(boardType);
		board3.setMember(member3);
		board3.setTitle("자유게시판 제목10");
		board3.setContents("자유게시판 내용10");
		board3.setViewCnt(0);
		
		UnitedBoard board4 = new UnitedBoard();
		board4.setIdx(1L);
		board4.setBoardType(boardType);
		board4.setMember(member2);
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
	// -------------------------------
	@Test
	public void 동영상게시판_내용_글목록조회() throws Exception {
		
		// jdbcTemplate.update(sql3, 2, 3, "동영상 게시판 제목1", "동영상 게시판 내용1", 0);
		// jdbcTemplate.update(sql3, 2, 2, "동영상 게시판 제목10", "동영상 게시판 내용10", 0);
		// jdbcTemplate.update(sql3, 2, 3, "동영상 게시판 제목11", "동영상 게시판 내용11", 0);
		// jdbcTemplate.update(sql3, 2, 2, "동영상 게시판 제목12", "동영상 게시판 내용12", 0);
		// jdbcTemplate.update(sql3, 2, 1, "동영상 게시판 제목13", "동영상 게시판 내용13", 0);
		// jdbcTemplate.update(sql3, 2, 3, "동영상 게시판 제목14", "동영상 게시판 내용14", 0);
		// jdbcTemplate.update(sql3, 2, 2, "동영상 게시판 제목15", "동영상 게시판 내용15", 0);
		
		// Given
		BoardType boardType = em.getReference(BoardType.class, 2L);
		Member member1 = em.getReference(Member.class, 1L);
		Member member2 = em.getReference(Member.class, 2L);
		Member member3 = em.getReference(Member.class, 3L);
		
		List<UnitedBoard> compareList = new ArrayList<UnitedBoard>();
		
		UnitedBoard board1 = new UnitedBoard();
		board1.setIdx(27L);
		board1.setBoardType(boardType);
		board1.setMember(member2);
		board1.setTitle("동영상 게시판 제목15");
		board1.setContents("동영상 게시판 내용15");
		board1.setViewCnt(0);
		
		UnitedBoard board2 = new UnitedBoard();
		board2.setIdx(26L);
		board2.setBoardType(boardType);
		board2.setMember(member3);
		board2.setTitle("동영상 게시판 제목14");
		board2.setContents("동영상 게시판 내용14");
		board2.setViewCnt(0);
		
		UnitedBoard board3 = new UnitedBoard();
		board3.setIdx(25L);
		board3.setBoardType(boardType);
		board3.setMember(member1);
		board3.setTitle("동영상 게시판 제목13");
		board3.setContents("동영상 게시판 내용13");
		board3.setViewCnt(0);
		
		UnitedBoard board4 = new UnitedBoard();
		board4.setIdx(24L);
		board4.setBoardType(boardType);
		board4.setMember(member2);
		board4.setTitle("동영상 게시판 제목12");
		board4.setContents("동영상 게시판 내용12");
		board4.setViewCnt(0);
		
		UnitedBoard board5 = new UnitedBoard();
		board5.setIdx(23L);
		board5.setBoardType(boardType);
		board5.setMember(member3);
		board5.setTitle("동영상 게시판 제목11");
		board5.setContents("동영상 게시판 내용11");
		board5.setViewCnt(0);
		
		UnitedBoard board6 = new UnitedBoard();
		board6.setIdx(22L);
		board6.setBoardType(boardType);
		board6.setMember(member2);
		board6.setTitle("동영상 게시판 제목10");
		board6.setContents("동영상 게시판 내용10");
		board6.setViewCnt(0);
		
		UnitedBoard board7 = new UnitedBoard();
		board7.setIdx(13L);
		board7.setBoardType(boardType);
		board7.setMember(member3);
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
	*/
	
	@AfterClass
	public void finalizeTest(){
		deleteFromTables("unitedboard", "member", "boardtype");
	}
}
