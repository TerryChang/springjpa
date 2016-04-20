package com.terry.springjpa.vo;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

public interface VOConvert<T, K extends Serializable> {

	/**
	 * CrudRepository 인터페이스 구현 클래스 객체를 파라미터로 받아서
	 * 해당 인터페이스가 변환해야 할 Entity를 return 한다
	 * 엔티티를 return 할 때 영속성 컨텍스트에서 찾아서 return 해야 할 경우엔
	 * (VO 클래스 객체 값을 DB에서 수정할때 이 객체와 매핑되는 엔티티를 수정하는 상황에서 아마 이럴것이다) 
	 * 파라미터로 입력받은 CrudRepository 인터페이스 구현 객체를 이용해서 영속성 컨텍스트에서 엔티티를 찾아서 return 하고
	 * 그렇지 않을 경우 엔티티를 생성해서 return 한다(등록하는 경우)
	 * @param repository
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public T convertToEntity(CrudRepository<T, K> repository) throws UnsupportedOperationException;
}
