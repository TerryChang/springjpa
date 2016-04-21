package com.terry.springjpa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.terry.springjpa.common.vo.CommonResultVO;
import com.terry.springjpa.entity.Member;
import com.terry.springjpa.service.CRUDService;
import com.terry.springjpa.vo.MemberVO;
import com.terry.springjpa.vo.SearchVO;

@Controller
public class MemberController {

	@Autowired
	CRUDService<Member, Long, MemberVO> service;
	
	@RequestMapping(value="/member/memberList")
	public String memberList(@ModelAttribute(value="searchVO") SearchVO searchVO, @PageableDefault(size=10, sort="loginId", direction=Sort.Direction.DESC) Pageable pageable, Model model){
		Page<Member> list = service.list(searchVO, pageable);
		model.addAttribute("result", list);
		return "/member/memberList";
	}
	
	@RequestMapping(value="/member/memberInsertUpdate", method=RequestMethod.GET)
	public String memberInsertUpdate(@RequestParam(value="idx", required=false) Member member, Model model){
		if(member == null) member = new Member();
		MemberVO memberVO = member.convertToVO();
		model.addAttribute("member", memberVO);
		return "/member/memberInsertUpdate";
	}
	
	@RequestMapping(value="/member/memberInsertUpdate", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVO memberInsertUpdate(@RequestBody @Valid MemberVO memberVO){
		CommonResultVO result = new CommonResultVO();
		
		// idx값이 null 일 경우엔 신규 등록을 의미하는 것이고
		// null이 아닌 경우엔 기존 값을 수정한다는 의미이다
		if(memberVO.getIdx() == null){
			service.insert(memberVO);
		}else{
			service.update(memberVO);
		}

		return result;
	}
	
	@RequestMapping(value="/member/memberDelete", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVO memberDelete(@RequestParam(value="idx", required=true) Long idx){
		CommonResultVO result = new CommonResultVO();
		
		service.delete(idx);

		return result;
	}
}
