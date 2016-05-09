package com.terry.springjpa.common.exception;

public class ClassMismatchException extends RuntimeException {

	private static final long serialVersionUID = 6037137986443365378L;

	public ClassMismatchException(){
		super();
	}
	
	public ClassMismatchException(String msg){
		super(msg);
	}
}
