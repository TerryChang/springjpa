package com.terry.springjpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.terry.springjpa.vo.SearchVO;
import com.terry.springjpa.vo.UnitedBoardVO;

public interface UnitedBoardCustom {
	public Page<UnitedBoardVO> selectList(Long boardTypeIdx, SearchVO searchVO, Pageable pageable);
	public void updateViewCnt(Long idx);
}
