package com.terry.springjpa.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.terry.springjpa.entity.EntityConvert;

public abstract class AbstractService<E extends EntityConvert<V>, V> {
	
	public List<V> convertEntityListToVOList(List<E> entityResult){
		List<V> result = new ArrayList<V>();
		Iterator<E> iterator = entityResult.iterator();
		while(iterator.hasNext()){
			E e = iterator.next();
			EntityConvert<V> entityConvert = (EntityConvert<V>)e;
			V v = entityConvert.convertToVO();
			result.add(v);
			iterator.remove();
		}
		entityResult = null;
		return result;
	}
	
	/*
	public List<V> convertEntityListToVOList(List<EntityConvert<V>> entityResult){
		List<V> result = new ArrayList<V>();
		Iterator<EntityConvert<V>> iterator = entityResult.iterator();
		while(iterator.hasNext()){
			EntityConvert<V> entityConvert = iterator.next();
			V v = entityConvert.convertToVO();
			result.add(v);
			iterator.remove();
		}
		entityResult = null;
		return result;
	}
	 */

}
