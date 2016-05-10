package com.terry.springjpa.repository.impl;

import java.util.List;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import com.mysema.query.jpa.JPQLQuery;
import com.terry.springjpa.entity.Authority;
import com.terry.springjpa.entity.Member;
import com.terry.springjpa.entity.QAuthority;
import com.terry.springjpa.entity.QGroups;
import com.terry.springjpa.entity.QGroupsAuthority;
import com.terry.springjpa.entity.QMember;
import com.terry.springjpa.entity.QMemberAuthority;
import com.terry.springjpa.entity.QMemberGroups;
import com.terry.springjpa.repository.LoginAuthorities;

public class LoginAuthoritiesImpl extends QueryDslRepositorySupport implements LoginAuthorities {

	public LoginAuthoritiesImpl() {
		super(Member.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Member> loadUsersByLoginId(String loginId) {
		// TODO Auto-generated method stub
		QMember member = QMember.member;
		JPQLQuery query = from(member);
		query = query.where(member.loginId.eq(loginId));
		return query.list(member);
	}

	@Override
	public List<Authority> loadUserAuthorities(String loginId) {
		// TODO Auto-generated method stub
		QMember member = QMember.member;
		QAuthority authority = QAuthority.authority;
		QMemberAuthority memberAuthority = QMemberAuthority.memberAuthority;
		
		JPQLQuery query = from(authority).innerJoin(authority.authorityMembers, memberAuthority).fetch()
						.innerJoin(memberAuthority.member, member).fetch()
						.where(member.loginId.eq(loginId));
		
		return query.list(authority);
	}
	
	

	@Override
	public List<Authority> loadGroupAuthorities(String loginId) {
		// TODO Auto-generated method stub
		QMember member = QMember.member;
		QAuthority authority = QAuthority.authority;
		QGroups groups = QGroups.groups;
		QGroupsAuthority groupsAuthority = QGroupsAuthority.groupsAuthority;
		QMemberGroups memberGroups = QMemberGroups.memberGroups;
		
		JPQLQuery query = from(authority).innerJoin(authority.authorityGroups, groupsAuthority).fetch()
						.innerJoin(groupsAuthority.groups, groups).fetch()
						.innerJoin(groups, memberGroups.groups).fetch()
						.innerJoin(memberGroups.member, member).fetch()
						.where(member.loginId.eq(loginId));
		
		return query.list(authority);
	}
	
}
