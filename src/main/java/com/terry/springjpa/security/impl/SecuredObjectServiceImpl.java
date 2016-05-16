package com.terry.springjpa.security.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import com.terry.springjpa.repository.impl.SecuredObjectRepositoryImpl;
import com.terry.springjpa.security.SecuredObjectService;

@Service
public class SecuredObjectServiceImpl implements SecuredObjectService {
	
	@Autowired
	SecuredObjectRepositoryImpl repository;

	@Override
	public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getRolesAndUrl() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap<String, List<ConfigAttribute>> getRolesAndMethod() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
