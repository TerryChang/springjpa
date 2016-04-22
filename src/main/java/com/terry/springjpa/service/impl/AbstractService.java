package com.terry.springjpa.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

public abstract class AbstractService<V, E> {
	
	public List<V> convertEntityListToVOList(List<E> entityResult, Converter<E,V> converter){
		List<V> result = new ArrayList<V>();
		Iterator<E> iterator = entityResult.iterator();
		while(iterator.hasNext()){
			E e = iterator.next();
			V v = converter.convert(e);
			result.add(v);
			iterator.remove();
		}
		entityResult = null;
		return result;
	}
}
