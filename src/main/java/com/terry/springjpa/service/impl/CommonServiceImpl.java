package com.terry.springjpa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terry.springjpa.dao.ValidateDao;
import com.terry.springjpa.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	ValidateDao validateDao;

	@Override
	@Transactional(readOnly=true)
	public boolean existCheck(String tableName, String columnName, String value) throws DataAccessException {
		// TODO Auto-generated method stub
		int cnt = validateDao.existCheck(tableName, columnName, value);
		return cnt == 0 ? false : true;
	}
	
	
}
