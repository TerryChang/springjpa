package com.terry.springjpa.security.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terry.springjpa.common.util.Util;
import com.terry.springjpa.common.vo.CommonResultVO;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String errorPage;
	private String ajaxHeaderKey;			// ajax 사용시 Http Header로 넣을 key 값(이 key에 value를 true 로 넣어줘야 동작한다)
	
	public CustomAccessDeniedHandler(String errorPage, String ajaxHeaderKey){
		if ((errorPage != null) && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
        }
		
		this.errorPage = errorPage;
		this.ajaxHeaderKey = ajaxHeaderKey;
	}
	
	public String getErrorPage(){
		return errorPage;
	}
	
	public void setErrorPage(String errorPage) {
        if ((errorPage != null) && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
        }

        this.errorPage = errorPage;
    }
	
	public String getAjaxHeaderKey() {
		return ajaxHeaderKey;
	}

	public void setAjaxHeaderKey(String ajaxHeaderKey) {
		this.ajaxHeaderKey = ajaxHeaderKey;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// Ajax를 통해 들어온것인지 파악한다
		
		Util.printRequestHeaderAll(request, logger);
		
		String ajaxHeader = request.getHeader(ajaxHeaderKey);
		String result = "";
		
		String url = request.getRequestURI();
		String queryString = request.getQueryString();
		if(queryString != null){
			url += "?" + queryString;
		}
		String exceptionClass = accessDeniedException.getClass().getName();
		String exceptionMessage = accessDeniedException.getMessage();
		
		// response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 일반적인 에러 페이지에 통합적으로 구현할 것이어서 Status Code를 403으로 설정하지는 않았다
		response.setCharacterEncoding("UTF-8");
		
		if(ajaxHeader == null){					// null로 받은 경우는 X-Ajax-call 헤더 변수가 없다는 의미이기 때문에 ajax가 아닌 일반적인 방법으로 접근했음을 의미한다
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Object principal = auth.getPrincipal();
			if (principal instanceof UserDetails) {
				String username = ((UserDetails) principal).getUsername();
				request.setAttribute("username", username);
			}
			
			request.setAttribute("url", url);
			request.setAttribute("exceptionClass", exceptionClass);
			request.setAttribute("exceptionMessage", exceptionMessage);
			
			request.getRequestDispatcher(errorPage).forward(request, response);
		}else{
			
			CommonResultVO commonResultVO = new CommonResultVO();
        	commonResultVO.setJob(CommonResultVO.Ajax);
        	commonResultVO.setResult(CommonResultVO.FAIL);
        	Map<String, String> resultMap = new HashMap<String, String>();
        	
			ObjectMapper objectMapper = new ObjectMapper();
	    	if("true".equals(ajaxHeader)){		// true로 값을 받았다는 것은 ajax로 접근했음을 의미한다
	    		resultMap.put("securityexceptionmessage", exceptionMessage);
			}else{								// 헤더 변수는 있으나 값이 틀린 경우이므로 헤더값이 틀렸다는 의미로 돌려준다
				resultMap.put("securityexceptionmessage", "Header(" + ajaxHeaderKey + ") Value Mismatch");
			}
	    	
	    	commonResultVO.setResultMap(resultMap);
	    	
	    	result = objectMapper.writeValueAsString(commonResultVO);
			response.getWriter().print(result);
			response.getWriter().flush();
		}
	}

}
