package com.terry.springjpa.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.terry.springjpa.common.annotation.FirstEntityManagerFlush;

@Repository
@FirstEntityManagerFlush
public class ValidateDao {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	public int existCheck(String tableName, String columnName, String value){
		Map<String, String> param = new HashMap<String, String>();
		param.put("tableName", tableName);
		param.put("columnName", columnName);
		param.put("value", value);
		return sqlSession.selectOne("validate.checkCnt", param);
	}
}
