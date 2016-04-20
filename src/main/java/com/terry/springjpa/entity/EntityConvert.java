package com.terry.springjpa.entity;

public interface EntityConvert<T> {

	/**
	 * 해당 엔티티를 명시된 타입의 VO 객체로 변형해서 return 해준다
	 * 이때 해당 엔티티는 프록시 타입이어선 안된다
	 * 이거는 생각해보면 답이 나온다. 무슨 뜻이냐면 VO 객체로 변환한다는 의미는 프록시가 아닌 실제 값을 직접 사용한다는 의미이기 때문에
	 * 엔티티가 프록시일수는 없다는 뜻이다
	 * @return
	 */
	public T convertToVO() throws UnsupportedOperationException;
	
}
