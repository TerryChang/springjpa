package com.terry.springjpa.common.converter;

import org.springframework.core.convert.converter.Converter;

import com.terry.springjpa.entity.BoardType;
import com.terry.springjpa.vo.BoardTypeVO;

public class BoardTypeToBoardTypeVOConverter implements Converter<BoardType, BoardTypeVO> {

	@Override
	public BoardTypeVO convert(BoardType boardType) {
		// TODO Auto-generated method stub
		BoardTypeVO result = null;
		if(boardType != null){
			result = new BoardTypeVO();
			result.setIdx(boardType.getIdx());
			result.setBoardTypeName(boardType.getBoardTypeName());
			result.setUrl(boardType.getUrl());
			if(boardType.getInsertUpdateDT() != null){
				result.setInsertDateTime(boardType.getInsertUpdateDT().getInsertDateTime());
				result.setUpdateDateTime(boardType.getInsertUpdateDT().getUpdateDateTime());
			}
		}
		return result;
	}
}
