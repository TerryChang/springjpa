package com.terry.springjpa.config.servlet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalInitBinder {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		// binder.registerCustomEditor(MultipartFile.class, new MultipartFilePropertyEditor());
		
	}
}
