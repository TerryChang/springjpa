package com.terry.springjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.terry.springjpa.entity.UnitedBoardComment;

public interface UnitedBoardCommentRepository extends JpaRepository<UnitedBoardComment, Long> {

	@Query(value="select u from UnitedBoardComment u join fetch u.unitedBoard join fetch u.member where u.idx = :commentIdx")
	public UnitedBoardComment findUnitedBoardComment(@Param("commentIdx") Long idx);
	
	@Query(value="select u from UnitedBoardComment u join fetch u.unitedBoard join fetch u.member where u.unitedBoard.idx = :unitedBoardIdx")
	public List<UnitedBoardComment> listUnitedBoardCommentAll(@Param("unitedBoardIdx") Long idx);
}
