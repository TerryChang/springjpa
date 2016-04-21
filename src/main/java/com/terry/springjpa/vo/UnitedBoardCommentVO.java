package com.terry.springjpa.vo;

import org.springframework.data.repository.CrudRepository;

import com.terry.springjpa.entity.UnitedBoardComment;

public class UnitedBoardCommentVO implements VOConvert<UnitedBoardComment, Long> {

	@Override
	public UnitedBoardComment convertToEntity(CrudRepository<UnitedBoardComment, Long> repository)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

}
