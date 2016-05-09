package com.terry.springjpa.common.exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4173266888080755679L;

	public DataNotFoundException(){
		super();
	}
	
	public DataNotFoundException(String msg){
		super(msg);
	}
}
