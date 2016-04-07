package com.terry.springjpa.common.vo;

import java.util.Map;

public class CommonResultVO {
	
	public static final String OK = "OK";
	public static final String FAIL = "FAIL";

	private String result = CommonResultVO.OK;
	private Map<String, String> errorMessageMap;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Map<String, String> getErrorMessageMap() {
		return errorMessageMap;
	}
	public void setErrorMessageMap(Map<String, String> errorMessageMap) {
		this.errorMessageMap = errorMessageMap;
	}
}
