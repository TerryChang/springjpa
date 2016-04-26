package com.terry.springjpa.service;

import org.springframework.dao.DataAccessException;

public interface CommonService {

	public boolean existCheck(String tableName, String columnName, String value) throws DataAccessException;
}
