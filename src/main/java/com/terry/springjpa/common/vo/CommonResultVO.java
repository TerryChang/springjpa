package com.terry.springjpa.common.vo;

import java.util.Map;

public class CommonResultVO {
	
	public static final String Validate = "Validate";
	public static final String Ajax = "Ajax";
	
	public static final String OK = "OK";
	public static final String FAIL = "FAIL";

	private String job = "";
	private String result = CommonResultVO.OK;
	private Map<String, String> resultMap;

	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Map<String, String> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}
}
