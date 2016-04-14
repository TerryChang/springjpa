package com.terry.springjpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.terry.springjpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Page<Member> findByLoginIdContaining(String containing, Pageable pageable);
	Page<Member> findByNameContaining(String containing, Pageable pageable);

}
