package com.terry.springjpa.service;

import static org.testng.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.terry.springjpa.config.root.RootContextMain;
import com.terry.springjpa.vo.UnitedBoardVO;

@ContextConfiguration(classes={RootContextMain.class})
@ActiveProfiles("local")
@TransactionConfiguration
public class UnitedBoardServiceTest extends AbstractTransactionalTestNGSpringContextTests {
	
	@Autowired
	UnitedBoardService service;

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
	public void 자유게시판_사용자3_글조회() throws Exception {
		// Given
		UnitedBoardVO comparedBoard = new UnitedBoardVO();
		comparedBoard.setIdx(10L);
		comparedBoard.setBoardTypeIdx(1L);
		comparedBoard.setMemberLoginId("loginId3");
		comparedBoard.setTitle("자유게시판 제목10");
		comparedBoard.setContents("자유게시판 내용10");
		comparedBoard.setViewCnt(0);
		
		// When
		UnitedBoardVO result = service.view(10L);
		
		// Then
		boolean blCompare = true;
		if(comparedBoard.getIdx() != result.getIdx()) blCompare = false;
		if(comparedBoard.getBoardTypeIdx() != result.getBoardTypeIdx()) blCompare = false;
		if(!comparedBoard.getMemberLoginId().equals(result.getMemberLoginId())) blCompare = false;
		if(!comparedBoard.getTitle().equals(result.getTitle())) blCompare = false;
		if(!comparedBoard.getContents().equals(result.getContents())) blCompare = false;
		if(comparedBoard.getViewCnt() != result.getViewCnt()) blCompare = false;
		
		assertTrue(blCompare, "자유게시판_사용자3_글조회 실패");
	}
}
