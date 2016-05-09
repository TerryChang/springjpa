package com.terry.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.terry.springjpa.entity.UnitedBoard;

public interface UnitedBoardRepository extends JpaRepository<UnitedBoard, Long>, UnitedBoardCustom {

	// @Query("select u from UnitedBoard u join fetch u.member join fetch u.boardType where u.idx = :idx")
	@Query("select u from UnitedBoard u join fetch u.member where u.idx = :idx")
	public UnitedBoard findByIdx(@Param("idx") Long idx);
	
}
