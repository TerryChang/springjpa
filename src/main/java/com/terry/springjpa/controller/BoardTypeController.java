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
import org.springframework.web.bind.annotation.ResponseBody;

import com.terry.springjpa.common.vo.CommonResultVO;
import com.terry.springjpa.entity.BoardType;
import com.terry.springjpa.service.CRUDService;

@Controller
public class BoardTypeController {

	@Autowired
	CRUDService<BoardType, Long> service;
	
	@RequestMapping(value="/boardType/boardTypeList")
	public String boardTypeList(Model model){
		List<BoardType> list = service.listAll();
		model.addAttribute("result", list);
		return "/boardType/boardTypeList";
	}
	
	@RequestMapping(value="/boardType/boardTypeInsert", method=RequestMethod.GET)
	public String boardTypeInsert(){
		return "/boardType/boardTypeInsert";
	}
	
	@RequestMapping(value="/boardType/boardTypeInsert", method=RequestMethod.POST)
	@ResponseBody
	public CommonResultVO boardTypeInsert(@RequestBody @Valid BoardType boardType){
		CommonResultVO result = new CommonResultVO();
		service.insert(boardType);

		return result;
	}
	
	@RequestMapping(value="/boardType/boardTypeInsert2", method=RequestMethod.GET)
	public String boardTypeInsert2(@ModelAttribute("boardType") BoardType boardType){
		return "/boardType/boardTypeInsert2";
	}
	
	@RequestMapping(value="/boardType/boardTypeInsert2", method=RequestMethod.POST)
	public String boardTypeInsert2Proc(@ModelAttribute("boardType") @Valid BoardType boardType, BindingResult bindingResult){
		String url = "";
		if(bindingResult.hasErrors()){
			url = "/boardType/boardTypeInsert2";
		}else{
			service.insert(boardType);
			url = "redirect:/boardType/boardTypeInsert";
		}
		return url;
	}
}
