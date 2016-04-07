package com.terry.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.terry.springjpa.entity.BoardType;

public interface BoardTypeRepository extends JpaRepository<BoardType, Long> {

}
