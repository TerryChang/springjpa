/**
 * BoardTypeVO 클래스 객체를 BoardType Entity 객체로 변환하는 Converter 클래스
 * VO 클래스 객체를 Entity 클래스 객체로 변환하는 과정해서는 생각해야 하는 부분이 있는데..
 * VO 클래스의 내용을 DB에 반영한다는 생각하에서 움직여야 한다는 것이다.
 * 즉 VO 클래스 객체에서 DB의 key로 사용되는 필드값을 읽어 이를 DB에서 읽어온뒤
 * 이렇게 읽어온 Entity 객체에 VO 클래스 객체에서 읽어온 내용을 넣어줘야 한다는 것이다.
 * 그래야 나중에 이것을 이용해서 DB에서 insert를 하든 update를 하든 할 것이다.
 * 이를 위해 생성자에 Entity 클래스 겍체를 조회하는 repository를 받아와서 이를 사용하는 방법으로 접근한다 
 */
package com.terry.springjpa.common.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.JpaRepository;

import com.terry.springjpa.entity.BoardType;
import com.terry.springjpa.vo.BoardTypeVO;

public class BoardTypeVOToBoardTypeConverter implements Converter<BoardTypeVO, BoardType> {

	private JpaRepository<BoardType, Long> repository;
	
	public BoardTypeVOToBoardTypeConverter(){
		
	}
	
	public BoardTypeVOToBoardTypeConverter(JpaRepository<BoardType, Long> repository){
		this.repository = repository;
	}
	
	public void setRepository(JpaRepository<BoardType, Long> repository){
		this.repository = repository;
	}
	
	@Override
	public BoardType convert(BoardTypeVO boardTypeVO) {
		// TODO Auto-generated method stub
		BoardType result = null;
		if(boardTypeVO != null){
			if(boardTypeVO.getIdx() == null){
				result = new BoardType();
			}else{
				result = repository.findOne(boardTypeVO.getIdx());
			}
			result.setBoardTypeName(boardTypeVO.getBoardTypeName());
			result.setUrl(boardTypeVO.getUrl());
		}
		return result;
	}

}
