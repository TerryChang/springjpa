package com.terry.springjpa.common.converter;

import org.springframework.core.convert.converter.Converter;

import com.terry.springjpa.entity.Member;
import com.terry.springjpa.vo.MemberVO;

public class MemberToMemberVOConverter implements Converter<Member, MemberVO> {

	@Override
	public MemberVO convert(Member member) {
		// TODO Auto-generated method stub
		MemberVO result = null;
		if(member != null){
			result = new MemberVO();
			result.setIdx(member.getIdx());
			result.setLoginId(member.getLoginId());
			result.setPassword(member.getPassword());
			result.setName(member.getName());
			result.setEmail(member.getEmail());
			if(member.getInsertUpdateDT() != null){
				result.setInsertDateTime(member.getInsertUpdateDT().getInsertDateTime());
				result.setUpdateDateTime(member.getInsertUpdateDT().getUpdateDateTime());
			}
		}

		return result;
	}

}
