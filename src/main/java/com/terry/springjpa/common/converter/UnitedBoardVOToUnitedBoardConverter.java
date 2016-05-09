package com.terry.springjpa.common.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.JpaRepository;

import com.terry.springjpa.entity.BoardType;
import com.terry.springjpa.entity.Member;
import com.terry.springjpa.entity.UnitedBoard;
import com.terry.springjpa.vo.UnitedBoardVO;

public class UnitedBoardVOToUnitedBoardConverter implements Converter<UnitedBoardVO, UnitedBoard> {

	private JpaRepository<UnitedBoard, Long> unitedBoardRepository;
	private JpaRepository<BoardType, Long> boardTypeRepository;
	private JpaRepository<Member, Long> memberRepository;
	
	public UnitedBoardVOToUnitedBoardConverter(){
		
	}
	
	public UnitedBoardVOToUnitedBoardConverter(JpaRepository<UnitedBoard, Long> unitedBoardRepository, JpaRepository<BoardType, Long> boardTypeRepository, JpaRepository<Member, Long> memberRepository){
		this.unitedBoardRepository = unitedBoardRepository;
		this.boardTypeRepository = boardTypeRepository;
		this.memberRepository = memberRepository;
	}
	
	public void setUnitedBoardRepository(JpaRepository<UnitedBoard, Long> unitedBoardRepository) {
		this.unitedBoardRepository = unitedBoardRepository;
	}

	public void setBoardTypeRepository(JpaRepository<BoardType, Long> boardTypeRepository) {
		this.boardTypeRepository = boardTypeRepository;
	}

	public void setMemberRepository(JpaRepository<Member, Long> memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public UnitedBoard convert(UnitedBoardVO unitedBoardVO) {
		// TODO Auto-generated method stub
		UnitedBoard result = null;
		if(unitedBoardVO != null){
			if(unitedBoardVO.getIdx() == null){
				result = new UnitedBoard();
			}else{
				result = unitedBoardRepository.findOne(unitedBoardVO.getIdx());
			}
			
			BoardType boardType = null;
			
			if(unitedBoardVO.getBoardTypeIdx() == null){
				boardType = new BoardType();
			}else{
				boardType = boardTypeRepository.getOne(unitedBoardVO.getBoardTypeIdx());
			}
			
			result.setBoardType(boardType);
			
			Member member = null;
			
			if(unitedBoardVO.getMemberIdx() == null){
				member = new Member();
			}else{
				member = memberRepository.getOne(unitedBoardVO.getMemberIdx());
			}
			
			result.setMember(member);
			
			result.setTitle(unitedBoardVO.getTitle());
			result.setContents(unitedBoardVO.getContents());
			
			// int 타입인 viewCnt 멤버변수의 경우 0이 아니란것은 외부에서 0이 아닌 값을 입력한 것이므로
			// 외부에서 입력한 viewCnt를 업데이트 한다는 의미로 접근해서 Converter 작업에 참여시킨다
			if(unitedBoardVO.getViewCnt() != 0){
				result.setViewCnt(unitedBoardVO.getViewCnt());
			}
		}
		
		return result;
	}

}
