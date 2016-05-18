package com.terry.springjpa.security;

import static org.testng.Assert.*;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.terry.springjpa.config.root.RootContextMain;
import com.terry.springjpa.repository.impl.SecuredObjectRepositoryImpl;

@ContextConfiguration(classes={RootContextMain.class})
@ActiveProfiles("local")
@TransactionConfiguration
public class SecurityObjectRepositoryImplTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	SecuredObjectRepositoryImpl repository;
	
	/*
insert into authority(idx, authority_name, authority_desc) values(authority_sequence.nextval, 'ADMIN', '관리자');
insert into authority(idx, authority_name, authority_desc) values(authority_sequence.nextval, 'MEMBER', '회원');
insert into authority(idx, authority_name, authority_desc) values(authority_sequence.nextval, 'ANONYMOUS', '비회원');

insert into secured_resources(idx, resource_name, resource_pattern, resource_type, sort_order) values(secured_resources_sequence.nextval, '사용자 목록', '/user/userList.do', 'url', 1);
insert into secured_resources(idx, resource_name, resource_pattern, resource_type, sort_order) values(secured_resources_sequence.nextval, '사용자 등록', '/user/userInsert.do', 'url', 1);
insert into secured_resources(idx, resource_name, resource_pattern, resource_type, sort_order) values(secured_resources_sequence.nextval, '사용자 삭제', '/user/userDelete.do', 'url', 1);
insert into secured_resources(idx, resource_name, resource_pattern, resource_type, sort_order) values(secured_resources_sequence.nextval, '사용자 전체', '/user/*.do', 'url', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_type, sort_order) values(secured_resources_sequence.nextval, '관리자 목록', '/admin/adminList.do', 'url', 1);
insert into secured_resources(idx, resource_name, resource_pattern, resource_type, sort_order) values(secured_resources_sequence.nextval, '관리자 등록', '/admin/adminInsert.do', 'url', 1);
insert into secured_resources(idx, resource_name, resource_pattern, resource_type, sort_order) values(secured_resources_sequence.nextval, '관리자 삭제', '/admin/adminDelete.do', 'url', 1);
insert into secured_resources(idx, resource_name, resource_pattern, resource_type, sort_order) values(secured_resources_sequence.nextval, '관리자 전체', '/admin/*.do', 'url', 2);
insert into secured_resources(idx, resource_name, resource_pattern, resource_type, sort_order) values(secured_resources_sequence.nextval, '공지 목록', '/notice/noticeList.do', 'url', 1);
insert into secured_resources(idx, resource_name, resource_pattern, resource_type, sort_order) values(secured_resources_sequence.nextval, '공지 조회', '/notice/noticeView.do', 'url', 1);
insert into secured_resources(idx, resource_name, resource_pattern, resource_type, sort_order) values(secured_resources_sequence.nextval, '공지 전체', '/notice/notice*.do', 'url', 2);

insert into secured_resources_authority(resources_idx, authority_idx) values(5, 1);
insert into secured_resources_authority(resources_idx, authority_idx) values(6, 1);
insert into secured_resources_authority(resources_idx, authority_idx) values(7, 1);
insert into secured_resources_authority(resources_idx, authority_idx) values(4, 2);
insert into secured_resources_authority(resources_idx, authority_idx) values(9, 3);
insert into secured_resources_authority(resources_idx, authority_idx) values(10, 3);

	 */
	
	@BeforeClass
	public void initTest(){
		String sql1 = "insert into authority(idx, authority_name, authority_desc) values(authority_sequence.nextval, ?, ?)";
		String sql2 = "insert into secured_resources(idx, resource_name, resource_pattern, resource_type, sort_order) values(secured_resources_sequence.nextval, ?, ?, ?, ?);";
		String sql3 = "insert into secured_resources_authority(resources_idx, authority_idx) values(?, ?)";
		
		jdbcTemplate.update(sql1, "ADMIN", "관리자");
		jdbcTemplate.update(sql1, "MEMBER", "회원");
		jdbcTemplate.update(sql1, "ANONYMOUS", "비회원");
		
		jdbcTemplate.update(sql2, "사용자 목록", "/user/userList.do", "url", 1);
		jdbcTemplate.update(sql2, "사용자 등록", "/user/userInsert.do", "url", 1);
		jdbcTemplate.update(sql2, "사용자 삭제", "/user/userDelete.do", "url", 1);
		jdbcTemplate.update(sql2, "사용자 전체", "/user/*.do", "url", 2);
		jdbcTemplate.update(sql2, "관리자 목록", "/admin/adminList.do", "url", 1);
		jdbcTemplate.update(sql2, "관리자 등록", "/admin/adminInsert.do", "url", 1);
		jdbcTemplate.update(sql2, "관리자 삭제", "/admin/adminDelete.do", "url", 1);
		jdbcTemplate.update(sql2, "관리자 전체", "/admin/*.do", "url", 2);
		jdbcTemplate.update(sql2, "공지 목록", "/notice/noticeList.do", "url", 1);
		jdbcTemplate.update(sql2, "공지 조회", "/notice/noticeView.do", "url", 1);
		jdbcTemplate.update(sql2, "공지 전체", "/notice/notice*.do", "url", 2);
		
		jdbcTemplate.update(sql3, 5, 1);
		jdbcTemplate.update(sql3, 6, 1);
		jdbcTemplate.update(sql3, 7, 1);
		jdbcTemplate.update(sql3, 4, 2);
		jdbcTemplate.update(sql3, 9, 3);
		jdbcTemplate.update(sql3, 10, 3);
	}
	
	@Test
	public void URL별_권한조회(){
		List<Map<String, Object>> result = repository.getSqlRolesAndUrl();
		assertEquals(result.size(), 6);
	}
}
