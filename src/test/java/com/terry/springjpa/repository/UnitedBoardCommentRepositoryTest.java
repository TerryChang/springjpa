package com.terry.springjpa.repository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.terry.springjpa.config.root.RootContextMain;
import com.terry.springjpa.entity.Member;
import com.terry.springjpa.entity.UnitedBoard;
import com.terry.springjpa.entity.UnitedBoardComment;

@ContextConfiguration(classes={RootContextMain.class})
@ActiveProfiles("local")
@TransactionConfiguration
public class UnitedBoardCommentRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	UnitedBoardRepository unitedBoardrepository;
	
	@Autowired
	UnitedBoardCommentRepository repository;
	
	@BeforeClass
	public void initTest(){
		String sql1 = "insert into boardtype(idx, name) values (boardtype_sequence.nextval, ?)";
		String sql2 = "insert into member(idx, loginid, password, name, insertdt, updatedt) "
				+ "values (member_sequence.nextval, ?, ?, ?, current_timestamp(), null)";
		String sql3 = "insert into unitedboard(idx, boardtype_idx, member_idx, title, contents, viewcnt, insertdt, updatedt)"
				+ "values (unitedboard_sequence.nextval, ?, ?, ?, ?, ?, current_timestamp(), null)";
		String sql4 = "insert into unitedboard_comment(idx, unitedboard_idx, member_idx, comment, insertdt) "
				+ "values(unitedboard_comment_sequence.nextval, ?, ?, ?, current_timestamp())";
		
		// 게시판 타입1 등록
		jdbcTemplate.update(sql1,"자유게시판");
		// 게시판 타입2 등록
		jdbcTemplate.update(sql1,"동영상 게시판");
		
		// 사용자1 등록
		jdbcTemplate.update(sql2, "loginId1", "loginPassword1", "이름1");
		// 사용자2 등록
		jdbcTemplate.update(sql2, "loginId2", "loginPassword2", "이름2");
		// 사용자3 등록
		jdbcTemplate.update(sql2, "loginId3", "loginPassword3", "이름3");
		
		// 게시판 타입1 글 12개(사용자1 3개, 사용자2 5개, 사용자3 4개) 등록
		jdbcTemplate.update(sql3, 1, 2, "자유게시판 제목1", "자유게시판 내용1", 0);
		jdbcTemplate.update(sql3, 1, 1, "자유게시판 제목2", "자유게시판 내용2", 0);
		
		// 게시판 타입1의 글에 대한 댓글 사용
		jdbcTemplate.update(sql4, 2, 3, "자유게시판 댓글1");
		jdbcTemplate.update(sql4, 1, 1, "자유게시판 댓글2");
		jdbcTemplate.update(sql4, 2, 2, "자유게시판 댓글3");
		jdbcTemplate.update(sql4, 2, 2, "자유게시판 댓글4");
		
		
		// 게시판 타입2 글 15개(사용자1 4개, 사용자2 6개, 사용자3 5개) 등록
		jdbcTemplate.update(sql3, 2, 3, "동영상 게시판 제목1", "동영상 게시판 내용1", 0);
		jdbcTemplate.update(sql3, 2, 2, "동영상 게시판 제목2", "동영상 게시판 내용2", 0);
		
		// 게시판 타입2의 글에 대한 댓글 사용
		jdbcTemplate.update(sql4, 3, 1, "동영상 게시판 댓글1");
		jdbcTemplate.update(sql4, 4, 3, "동영상 게시판 댓글2");
		jdbcTemplate.update(sql4, 4, 3, "동영상 게시판 댓글3");
		jdbcTemplate.update(sql4, 3, 2, "동영상 게시판 댓글4");
		
	}
	
	@Test
	public void 자유게시판_댓글등록() throws Exception {
		// Given
		UnitedBoard unitedBoard = em.find(UnitedBoard.class, 1L);
		Member member = em.find(Member.class, 1L);
		UnitedBoardComment comment = new UnitedBoardComment();
		comment.setUnitedBoard(unitedBoard);
		comment.setMember(member);
		comment.setComment("자유게시판 댓글 테스트");
		comment.setInsertDateTime(LocalDateTime.now());
		
		// When
		repository.saveAndFlush(comment);
		
		// Then
		UnitedBoardComment commentResult = em.find(UnitedBoardComment.class, comment.getIdx());
		assertEquals(comment, commentResult, "자유게시판_댓글등록 실패");
		
	}
	
	@Test
	public void 자유게시판_댓글수정() throws Exception {
		// Given
		UnitedBoard unitedBoard = em.find(UnitedBoard.class, 1L);
		Member member = em.find(Member.class, 1L);
		UnitedBoardComment comment = new UnitedBoardComment();
		comment.setIdx(2L);
		comment.setUnitedBoard(unitedBoard);
		comment.setMember(member);
		comment.setComment("자유게시판 댓글 테스트 수정");
		comment.setInsertDateTime(LocalDateTime.now());
		
		// When
		UnitedBoardComment ubc = repository.findUnitedBoardComment(2L);
		ubc.setComment("자유게시판 댓글 테스트 수정");
		repository.saveAndFlush(ubc);
		
		// Then
		assertEquals(comment, ubc, "자유게시판_댓글수정_실패");
		
	}
	
	@Test
	public void 자유게시판_댓글삭제() throws Exception {
		// When
		repository.delete(2L);
		
		// Then
		UnitedBoardComment comment = repository.findUnitedBoardComment(2L);
		assertNull(comment, "자유게시판_댓글삭제_실패");
		
	}
	
	@Test
	public void 자유게시판_댓글목록_조회() throws Exception {
		/*
		 jdbcTemplate.update(sql4, 2, 3, "자유게시판 댓글1");
		jdbcTemplate.update(sql4, 1, 1, "자유게시판 댓글2");
		jdbcTemplate.update(sql4, 2, 2, "자유게시판 댓글3");
		jdbcTemplate.update(sql4, 2, 2, "자유게시판 댓글4");
		 */
		// Given
		List<UnitedBoardComment> compareList = new ArrayList<UnitedBoardComment>();
		UnitedBoard board = em.find(UnitedBoard.class, 2L);
		Member member3 = em.find(Member.class, 3L);
		Member member2 = em.find(Member.class, 2L);
		UnitedBoardComment comment1 = new UnitedBoardComment();
		comment1.setIdx(1L);
		comment1.setUnitedBoard(board);
		comment1.setMember(member3);
		comment1.setComment("자유게시판 댓글1");
		UnitedBoardComment comment2 = new UnitedBoardComment();
		comment2.setIdx(3L);
		comment2.setUnitedBoard(board);
		comment2.setMember(member2);
		comment2.setComment("자유게시판 댓글3");
		UnitedBoardComment comment3 = new UnitedBoardComment();
		comment3.setIdx(4L);
		comment3.setUnitedBoard(board);
		comment3.setMember(member2);
		comment3.setComment("자유게시판 댓글4");
		compareList.add(comment1);
		compareList.add(comment2);
		compareList.add(comment3);
		
		// When
		List<UnitedBoardComment> result = repository.listUnitedBoardCommentAll(2L);
		
		// Then
		assertEquals(compareList, result, "자유게시판_댓글목록_조회_실패");
	}
	
	@AfterClass
	public void finalizeTest(){
		deleteFromTables("unitedboard_comment", "unitedboard", "member", "boardtype");
	}
}
