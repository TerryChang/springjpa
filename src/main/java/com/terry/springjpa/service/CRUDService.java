package com.terry.springjpa.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CRUDService<T, L> {
	
	public Page<T> list(Pageable pageable) throws UnsupportedOperationException, DataAccessException;
	public List<T> listAll() throws UnsupportedOperationException, DataAccessException;
	public T view(L idx) throws UnsupportedOperationException, DataAccessException;
	public void insert(T t) throws UnsupportedOperationException, DataAccessException;
	public void update(T t) throws UnsupportedOperationException, DataAccessException;
	public void delete(L idx) throws UnsupportedOperationException, DataAccessException;

}
