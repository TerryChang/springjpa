package com.terry.springjpa.repository.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.QTuple;
import com.terry.springjpa.entity.QSecuredResourcesAuthority;
import com.terry.springjpa.entity.SecuredResources;

@Repository
public class SecuredObjectRepositoryImpl extends QueryDslRepositorySupport {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public SecuredObjectRepositoryImpl() {
		super(SecuredResources.class);
		// TODO Auto-generated constructor stub
	}
	
	public List<Tuple> getSqlRolesAndUrl(){
		QSecuredResourcesAuthority securedResourcesAuthority = QSecuredResourcesAuthority.securedResourcesAuthority;
		JPQLQuery query = from(securedResourcesAuthority);
		List<Tuple> result = query.where(securedResourcesAuthority.securedResources.resourceType.eq("URL"))
													.orderBy(securedResourcesAuthority.securedResources.sortOrder.asc(), securedResourcesAuthority.securedResources.resourcePattern.asc())
													.list(new QTuple(securedResourcesAuthority.securedResources.resourcePattern, securedResourcesAuthority.authority.authorityName));
		return result;
	}
	
	public List<Tuple> getSqlRolesAndMethod(){
		QSecuredResourcesAuthority securedResourcesAuthority = QSecuredResourcesAuthority.securedResourcesAuthority;
		JPQLQuery query = from(securedResourcesAuthority);
		List<Tuple> result = query.where(securedResourcesAuthority.securedResources.resourceType.eq("METHOD"))
													.orderBy(securedResourcesAuthority.securedResources.sortOrder.asc(), securedResourcesAuthority.securedResources.resourcePattern.asc())
													.list(new QTuple(securedResourcesAuthority.securedResources.resourcePattern, securedResourcesAuthority.authority.authorityName));
		return result;
	}
	

	
}
