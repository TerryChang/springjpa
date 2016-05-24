package com.terry.springjpa.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Expression;
import com.mysema.query.types.Projections;
import com.mysema.query.types.QTuple;
import com.terry.springjpa.entity.QAuthority;
import com.terry.springjpa.entity.QSecuredResources;
import com.terry.springjpa.entity.QSecuredResourcesAuthority;
import com.terry.springjpa.entity.SecuredResources;

@Repository
public class SecuredObjectRepositoryImpl extends QueryDslRepositorySupport {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public SecuredObjectRepositoryImpl() {
		super(SecuredResources.class);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> convertTupleToMap(List<Tuple> tuple, @SuppressWarnings("rawtypes") Expression [] tupleKeys, String [] mapKeys){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int tupleKeysLength = tupleKeys.length;
		for(Tuple item : tuple){
			Map<String, Object> mapItem = new HashMap<String, Object>();
			for(int i = 0; i < tupleKeysLength; i++){
				mapItem.put(mapKeys[i], item.get(tupleKeys[i]));
			}
			result.add(mapItem);
		}
		return result;
	}
	
	public List<Map<String, Object>> getSqlRolesAndUrl(){
		
		QSecuredResources securedResources = QSecuredResources.securedResources;

		/*
		query = query.innerJoin(securedResources.securedResourcesAuthorityList, securedResourcesAuthority).fetch()
					.innerJoin(securedResourcesAuthority.authority, authority).fetch();
		*/
		
		/*
		List<SecuredResources> tempResult = query.where(securedResources.resourceType.eq("URL"))
												.orderBy(securedResources.sortOrder.asc(), securedResources.resourcePattern.asc())
												.list(securedResources);
		*/
		
		List<Tuple> tupleResult = from(securedResources).where(securedResources.resourceType.eq("URL"))
										.orderBy(securedResources.sortOrder.asc(), securedResources.resourcePattern.asc())
										.list(securedResources.resourcePattern, securedResources.securedResourcesAuthorityList.any().authority.authorityName);
		
		List<Map<String, Object>> result = convertTupleToMap(tupleResult
														, new Expression[]{securedResources.resourcePattern, securedResources.securedResourcesAuthorityList.any().authority.authorityName}
														, new String[]{"url", "authority"});
		
		
		return result;
	}
	
	public List<Map<String, Object>> getSqlRolesAndMethod(){
		
		QSecuredResources securedResources = QSecuredResources.securedResources;
		
		List<Tuple> tupleResult = from(securedResources).where(securedResources.resourceType.eq("METHOD"))
										.orderBy(securedResources.sortOrder.asc(), securedResources.resourcePattern.asc())
										.list(securedResources.resourcePattern, securedResources.securedResourcesAuthorityList.any().authority.authorityName);
		
		List<Map<String, Object>> result = convertTupleToMap(tupleResult
														, new Expression[]{securedResources.resourcePattern, securedResources.securedResourcesAuthorityList.any().authority.authorityName}
														, new String[]{"method", "authority"});
		
		
		return result;
	}
}
