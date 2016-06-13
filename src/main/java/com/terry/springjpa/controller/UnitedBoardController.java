package com.terry.springjpa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.terry.springjpa.common.vo.CommonResultVO;
import com.terry.springjpa.entity.UnitedBoard;
import com.terry.springjpa.service.CRUDService;
import com.terry.springjpa.service.CommonService;
import com.terry.springjpa.service.impl.UnitedBoardServiceImpl;
import com.terry.springjpa.vo.BoardTypeVO;
import com.terry.springjpa.vo.MemberVO;
import com.terry.springjpa.vo.SearchVO;
import com.terry.springjpa.vo.UnitedBoardVO;

@Controller
public class UnitedBoardController {

	@Autowired
	UnitedBoardServiceImpl service;
	
	@Autowired
	CRUDService<BoardTypeVO, Long> boardTypeService;
	
	@Autowired
	Converter<UnitedBoard, UnitedBoardVO> unitedBoardToUnitedBoardVOConverter;
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping(value="/unitedBoard/unitedBoardList")
	public String unitedBoardList(@RequestParam(value="boardTypeIdx") Long boardTypeIdx, @ModelAttribute(value="searchVO") SearchVO searchVO, @PageableDefault(size=10, sort="idx", direction=Sort.Direction.DESC) Pageable pageable, Model model){
		BoardTypeVO boardType = boardTypeService.view(boardTypeIdx);
		Page<UnitedBoardVO> list = service.list(boardTypeIdx, searchVO, pageable);
		model.addAttribute("boardType", boardType);
		model.addAttribute("result", list);
		return "/unitedBoard/unitedBoardList";
	}
	
	/**
	 * 게시물을 보는 경우엔 조회수를 증가시켜줘야 하는 작업을 진행해야 하는 관계로 Spring Data의 Domain Converter 기능을 이용해서 바로 조회하는 기능을 사용할 수 없다
	 * Service를 이용해서 조회하는 기능을 사용한다
	 * @param idx
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/unitedBoard/unitedBoardInsertUpdate", method=RequestMethod.GET)
	public String unitedBoardInsertUpdate(@RequestParam(value="boardTypeIdx") Long boardTypeIdx
			, @RequestParam(value="idx", required=false) Long idx
			, @ModelAttribute(value="searchVO") SearchVO searchVO
			, @AuthenticationPrincipal MemberVO member
			, Model model){
		BoardTypeVO boardType = boardTypeService.view(boardTypeIdx);
		UnitedBoardVO unitedBoardVO = new UnitedBoardVO();
		if(idx == null){
			unitedBoardVO.setMemberIdx(member.getIdx());
			unitedBoardVO.setMemberLoginId(member.getLoginId());
		}else{
			unitedBoardVO = service.view(idx);
		}
		model.addAttribute("boardType", boardType);
		model.addAttribute("member", member);
		model.addAttribute("unitedBoard", unitedBoardVO);
		return "/unitedBoard/unitedBoardInsertUpdate";
	}
	
	@RequestMapping(value="/unitedBoard/unitedBoardInsertUpdate", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVO unitedBoardInsertUpdate(@RequestBody @Valid UnitedBoardVO unitedBoardVO){
		CommonResultVO result = new CommonResultVO();
		
		// idx값이 null 일 경우엔 신규 등록을 의미하는 것이고
		// null이 아닌 경우엔 기존 값을 수정한다는 의미이다
		if(unitedBoardVO.getIdx() == null){
			service.insert(unitedBoardVO);
		}else{
			service.update(unitedBoardVO);
		}

		return result;
	}
	
	@RequestMapping(value="/unitedBoard/unitedBoardDelete", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVO unitedBoardDelete(@RequestParam(value="idx", required=true) Long idx){
		CommonResultVO result = new CommonResultVO();
		
		service.delete(idx);

		return result;
	}
}
