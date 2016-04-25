package com.terry.springjpa.repository;

import static org.testng.Assert.*;

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
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.terry.springjpa.config.root.RootContextMain;
import com.terry.springjpa.entity.Member;

@ContextConfiguration(classes={RootContextMain.class})
@ActiveProfiles("local")
@TransactionConfiguration
public class MemberRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	MemberRepository repository;
	
	@BeforeClass
	public void initTest(){
		String sql = "insert into member(idx, loginid, password, name, email, insertdt, updatedt) "
					+ "values (member_sequence.nextval, ?, ?, ?, ?, current_timestamp(), null)";
		for(int i=0; i < 12; i++){
			jdbcTemplate.update(sql,"로그인 id" + i, "로그인 password" + i, "이름" + i, "a@b.com");
		}
	}
	
	@Test
	public void 회원등록() throws Exception {
		// Given
		Member member = new Member("로그인 id 등록", "로그인 password 등록", "로그인 이름 등록", "a@b.com");
		
		// When
		repository.saveAndFlush(member);
		
		// Then
		Member selectMember = em.find(Member.class, member.getIdx());
		assertEquals(selectMember, member, "회원등록 실패");
		
	}
	
	@Test
	public void 회원수정() throws Exception {
		// '로그인 id5', '로그인 password5', '이름 5'
		// Given
		Member member = new Member("로그인 id5 수정", "로그인 password5 수정", "이름 5 수정", "a1@b.com");
		member.setIdx(6L);
		Member selectMember = em.find(Member.class, 6L);
		selectMember.setLoginId("로그인 id5 수정");
		selectMember.setPassword("로그인 password5 수정");
		selectMember.setName("이름 5 수정");
		selectMember.setEmail("a1@b.com");

		// When
		repository.saveAndFlush(selectMember);
		
		// Then
		assertEquals(selectMember, member, "회원수정 실패");
	}
	
	@Test
	public void 회원삭제() throws Exception {
		// Given
		// When
		repository.delete(2L);
		// Then
		Member member = em.find(Member.class, 2L);
		assertNull(member, "회원삭제 실패");
	}
	
	@Test
	public void 회원목록조회_idx정순() throws Exception {
		
		// Given
		List<Member> list = new ArrayList<Member>();
		for(int i=10; i < 12; i++){
			Member member = new Member("로그인 id"+i, "로그인 password"+i, "이름"+i, "a@b.com");
			member.setIdx((long)i+1);
			list.add(member);
		}
		
		// When
		// 2페이지 조회
		Pageable pageable = new PageRequest(1, 10, new Sort(Direction.ASC, "idx"));
		Page<Member> result = repository.findAll(pageable);
		
		// Then
		// idx정순조회이기 때문에 2페이지에는 idx값이 11, 12인 것이 들어가 있어야 한다
		assertEquals(12, result.getTotalElements(), "회원목록조회_idx정순 전체레코드 갯수 체크 실패");
		assertEquals(list, result.getContent(), "회원목록조회_idx정순 조회 내용 체크 실패");
		
	}
	
	@Test
	public void 회원목록조회_idx역순() throws Exception {
		
		// Given
		List<Member> list = new ArrayList<Member>();
		for(int i=2; i > 0; i--){
			Member member = new Member("로그인 id"+(i-1), "로그인 password"+(i-1), "이름"+(i-1), "a@b.com");
			member.setIdx((long)i);
			list.add(member);
		}
		
		// When
		// 2페이지 조회
		Pageable pageable = new PageRequest(2, 10, new Sort(Direction.DESC, "idx"));
		Page<Member> result = repository.findAll(pageable);
		
		// Then
		// idx역순조회이기 때문에 2페이지에는 idx값이 2, 1인 것이 들어가 있어야 한다
		assertEquals(12, result.getTotalElements(), "회원목록조회_idx역순 전체레코드 갯수 체크 실패");
		assertEquals(list, result.getContent(), "회원목록조회_idx역순 조회 내용 체크 실패");
		
	}
	
	@Test
	public void 회원로그인아이디Like목록조회_idx정순() throws Exception {
		
		// Given
		List<Member> list = new ArrayList<Member>();
		Member member1 = new Member("로그인 id1", "로그인 password1", "이름1", "a@b.com");
		member1.setIdx(2L);
		Member member2 = new Member("로그인 id10", "로그인 password10", "이름10", "a@b.com");
		member2.setIdx(11L);
		Member member3 = new Member("로그인 id11", "로그인 password11", "이름11", "a@b.com");
		member3.setIdx(12L);
		
		list.add(member1);
		list.add(member2);
		list.add(member3);
		
		// When
		// 1페이지 조회
		Pageable pageable = new PageRequest(1, 10, new Sort(Direction.ASC, "idx"));
		Page<Member> result = repository.findByLoginIdContaining("1", pageable);
		
		// Then
		// idx정순조회이기 때문에 2페이지에는 idx값이 11, 12인 것이 들어가 있어야 한다
		assertEquals(3, result.getTotalElements(), "회원로그인아이디Like목록조회_idx정순 전체레코드 갯수 체크 실패");
		assertEquals(list, result.getContent(), "회원로그인아이디Like목록조회_idx정순 조회 내용 체크 실패");
		
	}
	
	@Test
	public void 회원로그인아이디Like목록조회_idx역순() throws Exception {
		
		// Given
		List<Member> list = new ArrayList<Member>();
		Member member1 = new Member("로그인 id1", "로그인 password1", "이름1", "a@b.com");
		member1.setIdx(2L);
		Member member2 = new Member("로그인 id10", "로그인 password10", "이름10", "a@b.com");
		member2.setIdx(11L);
		Member member3 = new Member("로그인 id11", "로그인 password11", "이름11", "a@b.com");
		member3.setIdx(12L);
		
		list.add(member3);
		list.add(member2);
		list.add(member1);
		
		// When
		// 1페이지 조회
		Pageable pageable = new PageRequest(1, 10, new Sort(Direction.DESC, "idx"));
		Page<Member> result = repository.findByLoginIdContaining("1", pageable);
		
		// Then
		// idx정순조회이기 때문에 2페이지에는 idx값이 11, 12인 것이 들어가 있어야 한다
		assertEquals(3, result.getTotalElements(), "회원로그인아이디Like목록조회_idx역순 전체레코드 갯수 체크 실패");
		assertEquals(list, result.getContent(), "회원로그인아이디Like목록조회_idx역순 조회 내용 체크 실패");
		
	}
	
	@Test
	public void 회원상세조회() throws Exception {
		// Given
		Member member1 = new Member("로그인 id1", "로그인 password1", "이름1", "a@b.com");
		member1.setIdx(2L);
		
		// When
		Member selectMember = repository.findOne(2L);
		
		// Then
		assertEquals(member1, selectMember, "회원상세조회 실패");
		
	}
	
	@AfterClass
	public void finalizeTest(){
		deleteFromTables("member");
	}
	
}
