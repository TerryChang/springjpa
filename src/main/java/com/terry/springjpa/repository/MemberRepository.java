package com.terry.springjpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.terry.springjpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	// 로그인 아이디 또는 이름으로 like 검색할려고 findByLoginIdContainingOrNameContaining(String searchWrd, Pageable pageable) 로 선언했다
	// 왜냐면 검색어(searchWrd)를 받아서 loginId 멤버변수와 name 멤버변수에 각각 like 검색을 할려고 했는데
	// 이게 생각한 대로 동작할려면 searchWrd를 2개를 받아야 동작한다(컬럼을 2개 사용해서 조건을 걸것이기 때문에 이에 대한 바인딩 파라미터가 2개가 생길텐데
	// 이거에 맞춰서 파라미터도 2개로 정의해서 설정해야 한다)
	// 이런 이유로 2개로 설정했으며 만약 이걸 1개만 설정하면 java.util.NoSuchElementException 예외가 발생한다
	Page<Member> findByLoginIdContainingOrNameContaining(String searchWrd1, String searchWrd2, Pageable pageable);
	Page<Member> findByLoginIdContaining(String searchWrd, Pageable pageable);
	Page<Member> findByNameContaining(String searchWrd, Pageable pageable);

}
