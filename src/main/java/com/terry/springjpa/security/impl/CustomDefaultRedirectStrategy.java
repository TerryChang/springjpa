package com.terry.springjpa.security.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terry.springjpa.common.util.Util;
import com.terry.springjpa.common.vo.CommonResultVO;

/**
 * 기존 Spring Security에서 제공하고 있는 URL Redirect 정책을 정하는 인터페이스인 RedirectStrategy를 구현한 DefaultRedirectStrategy 클래스를
 * Ajax Login시의 Redirect 정책까지 동작하도록 수정한 클래스이다. 코드 기반은 DefaultRedirectStrategy 클래스를 기반으로 했다
 * @author TerryChang
 *
 */
public class CustomDefaultRedirectStrategy implements RedirectStrategy {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

    private boolean contextRelative;
    
    private String ajaxHeaderKey;			// ajax 사용시 Http Header로 넣을 key 값(이 key에 value를 true 로 넣어줘야 동작한다)
    
    public CustomDefaultRedirectStrategy(boolean contextRelative, String ajaxHeaderKey){
    	this.contextRelative = contextRelative;
    	this.ajaxHeaderKey = ajaxHeaderKey;
    }
    
    public String getAjaxHeaderKey() {
		return ajaxHeaderKey;
	}

	public void setAjaxHeaderKey(String ajaxHeaderKey) {
		this.ajaxHeaderKey = ajaxHeaderKey;
	}

	public boolean isContextRelative() {
		return contextRelative;
	}

	/**
     * Redirects the response to the supplied URL.
     * <p>
     * If <tt>contextRelative</tt> is set, the redirect value will be the value after the request context path. Note
     * that this will result in the loss of protocol information (HTTP or HTTPS), so will cause problems if a
     * redirect is being performed to change to HTTPS, for example.
     */
	@Override
	public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		// TODO Auto-generated method stub
		Util.printRequestHeaderAll(request, logger);
		
		String redirectUrl = calculateRedirectUrl(request.getContextPath(), url);
        redirectUrl = response.encodeRedirectURL(redirectUrl);

        String ajaxHeader = request.getHeader(ajaxHeaderKey);
        
        if(ajaxHeader == null){
        	if (logger.isDebugEnabled()) {
                logger.debug("Redirecting to '" + redirectUrl + "'");
            }
    
            response.sendRedirect(redirectUrl);
        }else{
        	CommonResultVO commonResultVO = new CommonResultVO();
        	commonResultVO.setJob(CommonResultVO.Ajax);
        	Map<String, String> resultMap = new HashMap<String, String>();
        	ObjectMapper objectMapper = new ObjectMapper();
        	
        	String result = "";
        	if("true".equals(ajaxHeader)){								// true로 값을 받았다는 것은 ajax로 접근했음을 의미한다(redirect URL로 redirect 하면 된다)
        		commonResultVO.setResult(CommonResultVO.OK);
        		resultMap.put("redirectUrl", redirectUrl);
        		logger.debug("Ajax Redirecting to '" + redirectUrl + "'");
				// result = "{\"result\" : \"fail\", \"message\" : \"" + accessDeniedException.getMessage() + "\"}";
			}else{														// 헤더 변수는 있으나 값이 틀린 경우이므로 헤더값이 틀렸다는 의미로 돌려준다
				response.setStatus(HttpStatus.BAD_REQUEST.value());		// Http Status Code를 Bad Request(400)으로 설정함으로써 Http 상태 코드로 에러 제어를 하도록 한다
				commonResultVO.setResult(CommonResultVO.FAIL);
				resultMap.put("message", "Header(" + ajaxHeaderKey + ") Value Mismatch");
			}
        	
        	commonResultVO.setResultMap(resultMap);
        	
        	result = objectMapper.writeValueAsString(commonResultVO);
			response.getWriter().print(result);
			response.getWriter().flush();
        }
	}

	private String calculateRedirectUrl(String contextPath, String url) {
        if (!UrlUtils.isAbsoluteUrl(url)) {
            if (contextRelative) {
                return url;
            } else {
                return contextPath + url;
            }
        }

        // Full URL, including http(s)://

        if (!contextRelative) {
            return url;
        }

        // Calculate the relative URL from the fully qualified URL, minus the last
        // occurrence of the scheme and base context.
        url = url.substring(url.lastIndexOf("://") + 3); // strip off scheme
        url = url.substring(url.indexOf(contextPath) + contextPath.length());

        if (url.length() > 1 && url.charAt(0) == '/') {
            url = url.substring(1);
        }

        return url;
    }

    /**
     * If <tt>true</tt>, causes any redirection URLs to be calculated minus the protocol
     * and context path (defaults to <tt>false</tt>).
     */
    public void setContextRelative(boolean useRelativeContext) {
        this.contextRelative = useRelativeContext;
    }
}
