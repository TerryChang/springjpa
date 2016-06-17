package com.terry.springjpa.common.converter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;

import org.springframework.core.convert.converter.Converter;

import com.terry.springjpa.common.util.Util;
import com.terry.springjpa.entity.BoardType;
import com.terry.springjpa.entity.Member;
import com.terry.springjpa.entity.UnitedBoard;
import com.terry.springjpa.vo.UnitedBoardVO;

public class UnitedBoardToUnitedBoardVOConverter implements Converter<UnitedBoard, UnitedBoardVO> {
	
	private EntityManager em;
	
	public UnitedBoardToUnitedBoardVOConverter(){
		
	}
	
	public UnitedBoardToUnitedBoardVOConverter(EntityManager em){
		this.em = em;
	}

	@Override
	public UnitedBoardVO convert(UnitedBoard unitedBoard) {
		// TODO Auto-generated method stub
		UnitedBoardVO result = null;
		PersistenceUnitUtil persistenceUnitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
		
		if(unitedBoard != null){
			result = new UnitedBoardVO();
			result.setIdx(unitedBoard.getIdx());
			result.setTitle(unitedBoard.getTitle());
			result.setContents(unitedBoard.getContents());
			result.setViewCnt(unitedBoard.getViewCnt());
			
			BoardType boardType = unitedBoard.getBoardType();
			
			if(boardType != null){
				result.setBoardTypeIdx(boardType.getIdx());
				
				// 게시판 타입 멤버변수가 엔티티이거나 초기화 된 프록시 클래스 객체이면 실제 값을 가져올수 있기 때문에 실제 값을 가져온다
				if(Util.checkEntityOrNot(boardType, "BoardType") || persistenceUnitUtil.isLoaded(boardType)){
					result.setBoardTypeName(boardType.getBoardTypeName());
					result.setBoardTypeUrl(boardType.getUrl());
				}
			}
			
			Member member = unitedBoard.getMember();
			if(member != null){
				result.setMemberIdx(member.getIdx());
				
				if(Util.checkEntityOrNot(member, "Member") || persistenceUnitUtil.isLoaded(member)){
					result.setMemberLoginId(member.getLoginId());
				}
			}
		}
		
		return result;
	}

}
