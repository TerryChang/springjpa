package com.terry.springjpa.config.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.terry.springjpa.common.exception.DataNotFoundException;
import com.terry.springjpa.common.vo.CommonResultVO;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);
 
        //If the message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }
 
        return localizedErrorMessage;
    }
	
	/*
	Ajax를 이용해서 값을 전송할 경우에는 form submit 개념이 아니기 때문에
	@Valid 어노테이션을 사용할 경우 Validate에서 문제가 발생하면 MethodArgumentNotValidExcetion이 발생한다
	그러나 form submit을 통해 데이터를 전송할 경우 Validate에서 문제가 발생하면 BindException이 발생한다 
	ExceptionHandler 어노테이션 사용시 함수의 파라미터로 Model 객체를 사용하지 말것
	Model을 사용하지 말고 함수 내부에서 ModelAndView 객체를 생성해서 거기에 값을 셋팅할것
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public CommonResultVO processValidateError(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException ex){
		CommonResultVO result = new CommonResultVO();
		
		MethodArgumentNotValidException mane = (MethodArgumentNotValidException)ex;
	
		result.setResult(CommonResultVO.FAIL);
	
		BindingResult bindingResult = mane.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		Map<String, String> errorMessageMap = new HashMap<String, String>();
		
		for(FieldError fieldError : fieldErrors){
			String fieldName = fieldError.getField();
			String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
			if(errorMessageMap.containsKey(fieldName)){	// 검색된 에러의 멤버변수가 이미 들어가 있는 상태면 기존 에러메시지에 \n을 덧붙여서 이를 연결한다
				errorMessageMap.put(fieldName, errorMessageMap.get(fieldName) + "\n" + localizedErrorMessage);
			}else{
				errorMessageMap.put(fieldName, localizedErrorMessage);
			}
		}
		
		result.setErrorMessageMap(errorMessageMap);
		
		return result;
	}
	
	/**
	 * 예외가 던져질 경우 이를 처리할 ExceptionHandler를 정의
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value={Exception.class})
	public ModelAndView processException(HttpServletRequest request, HttpServletResponse response, Exception ex){
		String uri = request.getRequestURI();
		String contentType = request.getHeader("content-type");
		
		ModelAndView result = null;
		if("application/json".equals(contentType)){									// content-type이 application/json일 경우 json으로 예외 메시지를 보내도록 한다
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			Map<String, String> errorMessageMap = new HashMap<String, String>();
			errorMessageMap.put("uri", uri);
			errorMessageMap.put("exceptionClass", ex.getClass().getName());
			errorMessageMap.put("exceptionMessage", ex.getMessage());
			CommonResultVO resultVO = new CommonResultVO();
			resultVO.setResult(CommonResultVO.FAIL);
			resultVO.setErrorMessageMap(errorMessageMap);
			
			result = new ModelAndView();
			MappingJackson2JsonView mj2jv = new MappingJackson2JsonView();
			mj2jv.setExtractValueFromSingleKeyModel(true);							// Model에 객체가 1개만 들어가기 때문에 변수명으로 json key를 잡는 것을 방지하기 위해 이 메소드에 true를 준다
			result.addObject(resultVO);
			result.setView(mj2jv);
		}else{																		// 일반적인 상황은 web page로 보여주도록 한다 
			result = new ModelAndView("/error/handleException");
			result.addObject("uri", uri);
			result.addObject("exceptionClass", ex.getClass().getName());
			result.addObject("exceptionMessage", ex.getMessage());
		}
		
		return result;
	}
}
