package com.terry.springjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping(value="/main")
	public String main(){
		return "/main";
	}
	
	@RequestMapping(value="/login")
	public String login(){
		return "/login";
	}
	
	@RequestMapping(value="/encryptPassword", method=RequestMethod.GET)
	public String passwordEncrypt(){
		return "/encryptPassword";
	}
	
	@RequestMapping(value="/encryptPassword", method=RequestMethod.POST)
	public String encryptPassword(@RequestParam(value="password") String password, Model model){
		String encryptPassword = bCryptPasswordEncoder.encode(password);
		model.addAttribute("password", password);
		model.addAttribute("passwordResult", encryptPassword);
		return "/encryptPassword";
	}
}
