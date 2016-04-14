package com.terry.springjpa.entity;

/**
 * 각 엔티티에 이 인터페이스를 구현하게끔 해서 업데이트 할 때 작업을 할 수 있도록 한다
 * @author Terry Chang
 *
 * @param <T>
 */
public interface UpdateEntity<T> {

	/**
	 * 영속성 컨텍스트에서 조회된 엔티티에서
	 * 이 메소드를 이용해 엔티티를 update한다
	 * 파라미터로 받은 같은 클래스 객체의 멤버변수 값을 엔티티 멤버변수 값에 update 함으로써 영속성 컨텍스트에서 조회된 엔티티를 업데이트하는 작업을 하도록 한다
	 * @param entity	엔티티와 같은 클래스의 객체로 이 객체는 영속성 컨텍스트에 속하지 않은 순수 클래스 객체이다
	 * @throws UnsupportedOperationException
	 */
	public void entityUpdate(T entity) throws UnsupportedOperationException;
}
