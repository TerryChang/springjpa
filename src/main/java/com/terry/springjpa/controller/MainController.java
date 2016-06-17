package com.terry.springjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.terry.springjpa.vo.MemberVO;

@Controller
public class MainController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping(value="/main")
	public String main(@AuthenticationPrincipal MemberVO member, Model model){
		model.addAttribute("member", member);
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
	
	/**
	 * 예외 발생시 에러 페이지를 보여주는 메소드
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/error")
	public String error(Model model){
		return "/error";
	}
	
	/**
	 * Spring Security 세션 관련 에러 발생시 에러 페이지를 보여주는 메소드
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/sessionError")
	public String sessionError(@RequestParam(value="error", required=false, defaultValue="") String error, Model model){
		String errorMessage = "";
		switch(error){
			case "expired" :
				errorMessage = "Expired Session";
				break;
			case "invalid" :
				errorMessage = "Invalid Session";
				break;
			default :
				errorMessage = "Unknown";
		}
		
		model.addAttribute("sessionError", errorMessage);
		return "/sessionError";
	}
}
