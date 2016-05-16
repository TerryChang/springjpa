package com.terry.springjpa.repository;

import java.util.List;

import com.terry.springjpa.entity.Authority;
import com.terry.springjpa.entity.Member;

public interface LoginAuthorities {

	public List<Member> loadUsersByLoginId(String loginId);
	public List<Authority> loadUserAuthorities(String loginId);
	public List<Authority> loadGroupAuthorities(String loginId);
}
