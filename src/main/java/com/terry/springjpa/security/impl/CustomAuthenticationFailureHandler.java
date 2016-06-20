package com.terry.springjpa.security.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terry.springjpa.common.vo.CommonResultVO;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String loginidname;				// 로그인 id값이 들어오는 input 태그 name
	private String loginpasswdname;			// 로그인 password 값이 들어오는 input 태그 name
	private String exceptionmsgname;		// 예외 메시지를 request의 Attribute에 저장할 때 사용될 key 값
	private String defaultFailureUrl;		// 화면에 보여줄 URL(로그인 화면)
	private String ajaxHeaderKey;			// ajax 사용시 Http Header로 넣을 key 값(이 key에 value를 true 로 넣어줘야 동작한다)
	
	public CustomAuthenticationFailureHandler(){
		this.loginidname = "j_username";
		this.loginpasswdname = "j_password";
		this.exceptionmsgname = "securityexceptionmessage";
		this.defaultFailureUrl = "/login.do";
		this.ajaxHeaderKey = "X-Ajax-Call";
	}
	
	public CustomAuthenticationFailureHandler(String loginidname, String loginpasswdname, String exceptionmsgname, String defaultFailureUrl, String ajaxHeaderKey){
		this.loginidname = loginidname;
		this.loginpasswdname = loginpasswdname;
		this.exceptionmsgname = exceptionmsgname;
		this.defaultFailureUrl = defaultFailureUrl;
		this.ajaxHeaderKey = ajaxHeaderKey;
	}

	public String getLoginidname() {
		return loginidname;
	}

	public void setLoginidname(String loginidname) {
		this.loginidname = loginidname;
	}

	public String getLoginpasswdname() {
		return loginpasswdname;
	}

	public void setLoginpasswdname(String loginpasswdname) {
		this.loginpasswdname = loginpasswdname;
	}

	public String getExceptionmsgname() {
		return exceptionmsgname;
	}

	public void setExceptionmsgname(String exceptionmsgname) {
		this.exceptionmsgname = exceptionmsgname;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// 먼저 Ajax로 로그인을 시도했는지를 확인해서 그것에 맞춰서 로그인 실패에 따른 처리를 진행하도록 한다
		
		String ajaxHeader = request.getHeader(ajaxHeaderKey);
		
		String loginid = request.getParameter(loginidname);
		String loginpasswd = request.getParameter(loginpasswdname);
		
		if(ajaxHeader == null){					// Ajax로 로그인을 한 것이 아니면 그냥 로그인 실패 페이지로 redirect 한다
			
			// Request 객체의 Attribute에 사용자가 실패시 입력했던 로그인 ID와 비밀번호를 저장해두어 로그인 페이지에서 이를 접근하도록 한다
			request.setAttribute(loginidname, loginid);
			request.setAttribute(loginpasswdname, loginpasswd);
			
			// Request 객체의 Attribute에 예외 메시지 저장
			request.setAttribute(exceptionmsgname, exception.getMessage());
			
			request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
			
		}else{									// Ajax로 로그인 한 것이면 로그인 실패했다는 의미를 response message로 전달하도록 한다
			
			CommonResultVO commonResultVO = new CommonResultVO();
        	commonResultVO.setJob(CommonResultVO.Ajax);
        	commonResultVO.setResult(CommonResultVO.FAIL);
        	Map<String, String> resultMap = new HashMap<String, String>();
			
			ObjectMapper objectMapper = new ObjectMapper();
	    	
	    	String result = "";
	    	if("true".equals(ajaxHeader)){		// true로 값을 받았다는 것은 ajax로 접근했음을 의미한다
	    		resultMap.put(exceptionmsgname, exception.getMessage());
			}else{								// 헤더 변수는 있으나 값이 틀린 경우이므로 헤더값이 틀렸다는 의미로 돌려준다
				response.setStatus(HttpStatus.BAD_REQUEST.value());			// Http Status Code를 Bad Request(400)으로 설정함으로써 Http 상태 코드로 에러 제어를 하도록 한다
				resultMap.put("message", "Header(" + ajaxHeaderKey + ") Value Mismatch");
			}
	    	
	    	commonResultVO.setResultMap(resultMap);
	    	
	    	result = objectMapper.writeValueAsString(commonResultVO);
			response.getWriter().print(result);
			response.getWriter().flush();
		}
	}

}
