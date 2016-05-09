package com.terry.springjpa.common.util;

import com.terry.springjpa.common.exception.ClassMismatchException;

public class SpringJpaUtil {

	/**
	 * 엔티티 클래스 또는 프록시 클래스 객체와 클래스 이름을 받아서 엔티티 클래스 객체인지 프록시 클래스 객체인지를 체크하는 메소드이다.
	 * 엔티티 클래스 객체가 파라미터로 들어올 경우엔 입력받은 클래스 이름과 엔티티 클래스 이름이 동일하지만
	 * 프로기 클래스 객체가 파라미터로 들어온 경우엔 클래스 이름이 엔티티 클래스 이름뒤에 _$$_jvst가 따라 붙는다
	 * (예를 들어 Member 엔티티 클래스 객체일 경우엔 클래스 이름이 Member로 나오지만
	 * Member 프록시 클래스 객체일 경우엔 클래스 이름이 Member_$$_jvst로 클래스 이름이 시작된다)
	 * 이걸 이용해서 판단한다
	 * @param obj							체크해야 할 엔티티 또는 프록시 클래스 객체
	 * @param className						판단해야 할 클래스 이름
	 * @return								엔티티일 경우 true, 프록시일 경우 false가 return 된다
	 * @throws ClassMismatchException		엔티티, 프록시 모두 아닐 경우 판단해야 할 객체를 잘못 던졌다는 의미의 예외를 던진다
	 */
	public static boolean checkEntityOrNot(Object obj, String className) throws ClassMismatchException {
		String proxyCheckString = "_$$_jvst";
		boolean result = true;
		String objClassName = obj.getClass().getSimpleName();
		if(objClassName.equals(className)){
			result = true;
		}else{
			String proxyCheckClassName = className + proxyCheckString;
			if(objClassName.startsWith(proxyCheckClassName)){
				result = false;
			}else{
				throw new ClassMismatchException("parameter obj is not check class");
			}
		}
		
		return result;
	}
}
