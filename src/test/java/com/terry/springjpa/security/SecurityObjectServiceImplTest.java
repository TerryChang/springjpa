package com.terry.springjpa.security;

import static org.testng.Assert.*;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.terry.springjpa.config.root.RootContextMain;

@ContextConfiguration(classes={RootContextMain.class})
@ActiveProfiles("local")
@TransactionConfiguration
public class SecurityObjectServiceImplTest extends AbstractTransactionalTestNGSpringContextTests  {

	@Autowired
	SecuredObjectService service;
	
	@BeforeClass
	public void initTest(){
		String sql1 = "insert into authority(idx, authority_name, authority_desc) values(authority_sequence.nextval, ?, ?)";
		String sql2 = "insert into secured_resources(idx, resource_name, resource_pattern, resource_type, sort_order) values(secured_resources_sequence.nextval, ?, ?, ?, ?);";
		String sql3 = "insert into secured_resources_authority(resources_idx, authority_idx) values(?, ?)";
		String sql4 = "insert into authority_hierarchy(parent_authority_idx, child_authority_idx) values(?, ?)";
		
		jdbcTemplate.update(sql1, "ADMIN", "관리자");
		jdbcTemplate.update(sql1, "MEMBER", "회원");
		jdbcTemplate.update(sql1, "ANONYMOUS", "비회원");
		
		jdbcTemplate.update(sql2, "사용자 목록", "/user/userList.do", "URL", 1);
		jdbcTemplate.update(sql2, "사용자 등록", "/user/userInsert.do", "URL", 1);
		jdbcTemplate.update(sql2, "사용자 삭제", "/user/userDelete.do", "URL", 1);
		jdbcTemplate.update(sql2, "사용자 전체", "/user/*.do", "URL", 2);
		jdbcTemplate.update(sql2, "관리자 목록", "/admin/adminList.do", "URL", 1);
		jdbcTemplate.update(sql2, "관리자 등록", "/admin/adminInsert.do", "URL", 1);
		jdbcTemplate.update(sql2, "관리자 삭제", "/admin/adminDelete.do", "URL", 1);
		jdbcTemplate.update(sql2, "관리자 전체", "/admin/*.do", "URL", 2);
		jdbcTemplate.update(sql2, "공지 목록", "/notice/noticeList.do", "URL", 1);
		jdbcTemplate.update(sql2, "공지 조회", "/notice/noticeView.do", "URL", 1);
		jdbcTemplate.update(sql2, "공지 전체", "/notice/notice*.do", "URL", 2);
		
		jdbcTemplate.update(sql3, 5, 1);
		jdbcTemplate.update(sql3, 6, 1);
		jdbcTemplate.update(sql3, 7, 1);
		jdbcTemplate.update(sql3, 4, 1);
		jdbcTemplate.update(sql3, 4, 2);
		jdbcTemplate.update(sql3, 9, 3);
		jdbcTemplate.update(sql3, 10, 3);
		
		jdbcTemplate.update(sql4, 1, 2);
		jdbcTemplate.update(sql4, 2, 3);
	}
	
	@Test
	public void URL별_권한조회_use_matcher() throws Exception{
		LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = service.getRolesAndUrl();
		assertEquals(result.size(), 6); // /user/*.do 자원을 ADMIN과 MEMBER가 같이 사용하기 때문에 6개가 나와야 한다
	}
	
	@Test
	public void 부모자식_권한조회() throws Exception{
		String result = service.getRolesHierarchy();
		assertEquals(result, "ADMIN > MEMBER and MEMBER > ANONYMOUS");
	}
}
