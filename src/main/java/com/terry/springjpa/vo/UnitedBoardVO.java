package com.terry.springjpa.vo;

import org.springframework.data.repository.CrudRepository;

import com.terry.springjpa.entity.UnitedBoard;

public class UnitedBoardVO implements VOConvert<UnitedBoard, Long> {

	@Override
	public UnitedBoard convertToEntity(CrudRepository<UnitedBoard, Long> repository)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

}
