package com.terry.springjpa.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.terry.springjpa.common.vo.CommonResultVO;
import com.terry.springjpa.entity.BoardType;
import com.terry.springjpa.service.CRUDService;
import com.terry.springjpa.vo.BoardTypeVO;

@Controller
public class BoardTypeController {

	@Autowired
	CRUDService<BoardType, Long, BoardTypeVO> service;
	
	@RequestMapping(value="/boardType/boardTypeList")
	public String boardTypeList(Model model){
		List<BoardTypeVO> list = service.listAll(null);
		model.addAttribute("result", list);
		return "/boardType/boardTypeList";
	}
	
	/**
	 * Spring Data JPA 에서 제공하는 기능 중 하나인 도메인 클래스 컨버터를 이용해서 Controller 단에서 아예 조회를 해서 그 결과를 가져오는 기능을 사용하고 있다
	 * @RequestParam(value="idx", required=false) BoardType boardType 를 사용하는 시점에 이때에 영속성 컨텍스트에서 엔티티를 조회해서 가져온다
	 * 
	 * @param boardType idx 파라미터로 넘어온 값을 key로 이용해서 영속성 컨텍스트에서 BoardType 엔티티를 조회한 객체
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/boardType/boardTypeInsertUpdate", method=RequestMethod.GET)
	public String boardTypeInsertUpdate(@RequestParam(value="idx", required=false) BoardType boardType, Model model){
		if(boardType == null) boardType = new BoardType();
		BoardTypeVO boardTypeVO = boardType.convertToVO();
		model.addAttribute("result", boardTypeVO);
		return "/boardType/boardTypeInsertUpdate";
	}
	
	@RequestMapping(value="/boardType/boardTypeInsertUpdate", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVO boardTypeInsertUpdate(@RequestBody @Valid BoardTypeVO boardTypeVO){
		CommonResultVO result = new CommonResultVO();
		
		// idx값이 null 일 경우엔 신규 등록을 의미하는 것이고
		// null이 아닌 경우엔 기존 값을 수정한다는 의미이다
		if(boardTypeVO.getIdx() == null){
			service.insert(boardTypeVO);
		}else{
			service.update(boardTypeVO);
		}

		return result;
	}
	
	@RequestMapping(value="/boardType/boardTypeDelete", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVO boardTypeDelete(@RequestParam(value="idx", required=true) Long idx){
		CommonResultVO result = new CommonResultVO();
		
		service.delete(idx);

		return result;
	}
	
	@RequestMapping(value="/boardType/boardTypeInsertUpdate2", method=RequestMethod.GET)
	public String boardTypeInsert2(@ModelAttribute("boardType") BoardType boardType){
		return "/boardType/boardTypeInsertUpdate2";
	}
	
	@RequestMapping(value="/boardType/boardTypeInsertUpdate2", method=RequestMethod.POST)
	public String boardTypeInsert2Proc(@ModelAttribute("boardType") @Valid BoardTypeVO boardTypeVO, BindingResult bindingResult){
		String url = "";
		if(bindingResult.hasErrors()){
			url = "/boardType/boardTypeInsertUpdate2";
		}else{
			service.insert(boardTypeVO);
			url = "redirect:/boardType/boardTypeList";
		}
		return url;
	}
}
