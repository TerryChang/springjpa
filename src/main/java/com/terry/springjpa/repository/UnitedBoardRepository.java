package com.terry.springjpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.terry.springjpa.entity.UnitedBoard;

public interface UnitedBoardRepository extends JpaRepository<UnitedBoard, Long> {

	@Query(value="select u from UnitedBoard u join fetch u.boardType join fetch u.member where u.idx = :boardIdx")
	public UnitedBoard findUnitedBoard(@Param("boardIdx") Long idx);
	
	// Spring Data에서 Fetch Join을 이용해서 Page 클래스 객체를 return 받는 식으로 만들 경우
	// Paging을 구현하기 위해 구하게 되는 전체 Count 쿼리를 하는 작업에서 문제가 발생한다.
	// 이를 해결하기 위해 Count 하는 부분에 대해서는 별도로 Count Query를 설정해서 이 부분을 해결하도록 한다
	// 참조 Link : http://stackoverflow.com/questions/14949526/jpql-join-fetch-not-working-in-manytoone-association-in-spring-data
	//             http://stackoverflow.com/questions/21549480/spring-data-fetch-join-with-paging-is-not-working
	/**
	 * 통합게시판 검색시 로그인 ID를 입력받아 이를 이용해서 하는 검색
	 * @param boardTypeIdx	게시판 타입 idx
	 * @param loginId		로그인 ID
	 * @param pageable		Spring Data Pageable 인터페이스를 구현한 클래스(PageRequest 클래스 객체가 올것이다)
	 * @return
	 */
	@Query(
			value="select u from UnitedBoard u join fetch u.boardType b join fetch u.member m where u.boardType.idx = :boardTypeIdx and u.member.loginId = :loginId order by u.idx desc"
			, countQuery="select count(u) from UnitedBoard u where u.boardType.idx = :boardTypeIdx and u.member.loginId = :loginId"
	)
	public Page<UnitedBoard> findUserAll(@Param("boardTypeIdx") Long boardTypeIdx,@Param("loginId") String loginId, Pageable pageable);
	
	
	// @Query(value="select u from UnitedBoard u join fetch u.boardType join fetch u.member where u.boardType.idx = ?1 and u.tltle like concat('%', concat(?2, '%')) order by u.idx desc")
	
	/**
	 * 통합게시판 검색시 제목을 입력받아 이를 이용해서 하는 검색
	 * @param boardTypeIdx
	 * @param title
	 * @param pageable
	 * @return
	 */
	@Query(
			value="select u from UnitedBoard u join fetch u.boardType b join fetch u.member m where u.boardType.idx = :boardTypeIdx and u.title like concat('%', concat(:title, '%')) order by u.idx desc"
			, countQuery="select count(u) from UnitedBoard u where u.boardType.idx = :boardTypeIdx and u.title like concat('%', concat(:title, '%'))"
	)
	public Page<UnitedBoard> findTitleAll(@Param("boardTypeIdx") Long boardTypeIdx, @Param("title") String title, Pageable pageable);
	
	
	/**
	 * 통합게시판 검색시 내용을 입력받아 이를 이용해서 하는 검색
	 * @param boardTypeIdx
	 * @param contents
	 * @param pageable
	 * @return
	 */
	@Query(
			value="select u from UnitedBoard u join fetch u.boardType b join fetch u.member m where u.boardType.idx = :boardTypeIdx and u.contents like concat('%', concat(:contents, '%')) order by u.idx desc"
			, countQuery="select count(u) from UnitedBoard u where u.boardType.idx = :boardTypeIdx and u.contents like concat('%', concat(:contents, '%'))"
	)
	public Page<UnitedBoard> findContentsAll(@Param("boardTypeIdx") Long boardTypeIdx, @Param("contents") String contents, Pageable pageable);
	
	
}
