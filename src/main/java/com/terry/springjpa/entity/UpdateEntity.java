package com.terry.springjpa.entity;

public interface UpdateEntity<T> {

	public void entityUpdate(T entity) throws UnsupportedOperationException;
}
