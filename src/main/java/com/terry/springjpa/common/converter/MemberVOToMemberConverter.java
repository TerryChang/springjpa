package com.terry.springjpa.common.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.JpaRepository;

import com.terry.springjpa.entity.Member;
import com.terry.springjpa.vo.MemberVO;

public class MemberVOToMemberConverter implements Converter<MemberVO, Member> {

	private JpaRepository<Member, Long> repository;
	
	public MemberVOToMemberConverter(){
		
	}
	
	public MemberVOToMemberConverter(JpaRepository<Member, Long> repository){
		this.repository = repository;
	}
	
	public void setRepository(JpaRepository<Member, Long> repository){
		this.repository = repository;
	}
	
	@Override
	public Member convert(MemberVO memberVO) {
		// TODO Auto-generated method stub
		Member result = null;
		if(memberVO != null){
			if(memberVO.getIdx() == null){
				result = new Member();
			}else{
				result = repository.findOne(memberVO.getIdx());
			}
			result.setLoginId(memberVO.getLoginId());
			result.setPassword(memberVO.getPassword());
			result.setName(memberVO.getName());
			result.setEmail(memberVO.getEmail());
		}
		return result;
	}

}
