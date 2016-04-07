package com.terry.springjpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.terry.springjpa.entity.BoardType;
import com.terry.springjpa.service.CRUDService;
import com.terry.springjpa.vo.SearchVO;

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
	public String boardTypeInsert(@ModelAttribute("searchVO") SearchVO searchVO, Model model){
		return "/boardType/boardTypeInsert";
	}
	
	@RequestMapping(value="/boardType/boardTypeInsert", method=RequestMethod.POST)
	public String boardTypeInsert(BoardType boardType){
		service.insert(boardType);
		return "/boardType/boardTypeInsert";
	}
}
