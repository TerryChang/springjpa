package com.terry.springjpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping(value="/main")
	public String main(){
		return "/main";
	}
	
	@RequestMapping(value="/login")
	public String login(){
		return "/login";
	}
}
