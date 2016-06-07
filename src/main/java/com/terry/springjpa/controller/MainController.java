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
	 * @param uri					에러가 발생한 uri
	 * @param exceptionClass		예외 클래스 이름
	 * @param exceptionMessage		예외 메시지
	 * @param username				Spring Security 관련 예외 발생시 로그인 아이디를 보여준다
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/error")
	public String error(Model model){
		return "/error";
	}
}
