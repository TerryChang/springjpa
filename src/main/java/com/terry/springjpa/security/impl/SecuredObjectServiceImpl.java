package com.terry.springjpa.security.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.terry.springjpa.repository.impl.SecuredObjectRepositoryImpl;
import com.terry.springjpa.security.SecuredObjectService;

public class SecuredObjectServiceImpl implements SecuredObjectService {
	
	SecuredObjectRepositoryImpl securedObjectRepositoryImpl;

	public SecuredObjectRepositoryImpl getSecuredObjectRepositoryImpl() {
		return securedObjectRepositoryImpl;
	}

	public void setSecuredObjectRepositoryImpl(SecuredObjectRepositoryImpl securedObjectRepositoryImpl) {
		this.securedObjectRepositoryImpl = securedObjectRepositoryImpl;
	}

	@Override
	public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getRolesAndUrl() throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<RequestMatcher, List<ConfigAttribute>>();
		
		List<Map<String, Object>> roleResult = securedObjectRepositoryImpl.getSqlRolesAndUrl();
		LinkedHashMap<Object, List<ConfigAttribute>> rolesAndResources = getRolesAndResources("url", roleResult);
		
		Set<Object> keys = rolesAndResources.keySet();
		for(Object key : keys){
			result.put((AntPathRequestMatcher)key, rolesAndResources.get(key));
		}
		return result;
	}

	@Override
	public LinkedHashMap<String, List<ConfigAttribute>> getRolesAndMethod() throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, List<ConfigAttribute>> result = new LinkedHashMap<String, List<ConfigAttribute>>();
		
		List<Map<String, Object>> roleResult = securedObjectRepositoryImpl.getSqlRolesAndMethod();
		LinkedHashMap<Object, List<ConfigAttribute>> rolesAndResources = getRolesAndResources("method", roleResult);
		
		Set<Object> keys = rolesAndResources.keySet();
		for(Object key : keys){
			result.put((String)key, rolesAndResources.get(key));
		}
		return result;
	}
	
	@Override
	public String getRolesHierarchy() throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> roleResult = securedObjectRepositoryImpl.getSqlRoleHierarchy();
		StringBuilder builder = new StringBuilder();
		String result = null;
		for(Map<String, Object> map : roleResult){
			String parentAuthority = (String)map.get("parentAuthority");
			String childAuthority = (String)map.get("childAuthority");
			if(builder.length() != 0){
				builder.append(" and ");
			}
			builder.append(parentAuthority);
			builder.append(" > ");
			builder.append(childAuthority);
		}
		
		result = builder.toString();
		return result;
	}

	private LinkedHashMap<Object, List<ConfigAttribute>> getRolesAndResources(String resourceType, List<Map<String, Object>> sqlResult) {
		LinkedHashMap<Object, List<ConfigAttribute>> resourcesMap = new LinkedHashMap<Object, List<ConfigAttribute>>();
		
		boolean isResourcesUrl = true;
			
		if("method".equals(resourceType)){	
			isResourcesUrl = false;
		}
		
		Iterator<Map<String, Object>> itr = sqlResult.iterator();
		Map<String, Object> tempMap;
        String preResource = null;
        String presentResourceStr;
        Object presentResource;
        while (itr.hasNext()) {
        	tempMap = itr.next();
        	
        	presentResourceStr = (String) tempMap.get(resourceType);
            // url 인 경우 RequestKey 형식의 key를 Map에 담아야 함
            presentResource = isResourcesUrl ? new AntPathRequestMatcher(presentResourceStr, null, true) : presentResourceStr;
            List<ConfigAttribute> configList = new LinkedList<ConfigAttribute>();
            
            // 이미 requestMap 에 해당 Resource 에 대한 Role 이 하나 이상 맵핑되어 있었던 경우, 
            // sort_order 는 resource(Resource) 에 대해 매겨지므로 같은 Resource 에 대한 Role 맵핑은 연속으로 조회됨.
            // 해당 맵핑 Role List (SecurityConfig) 의 데이터를 재활용하여 새롭게 데이터 구축
            if (preResource != null && presentResourceStr.equals(preResource)) {
                List<ConfigAttribute> preAuthList = resourcesMap.get(presentResource);
                Iterator<ConfigAttribute> preAuthItr = preAuthList.iterator();
                while (preAuthItr.hasNext()) {
                    SecurityConfig tempConfig = (SecurityConfig) preAuthItr.next();
                    configList.add(tempConfig);
                }
            }

            configList.add(new SecurityConfig((String) tempMap.get("authority")));
            
            // 만약 동일한 Resource 에 대해 한개 이상의 Role 맵핑 추가인 경우 
            // 이전 resourceKey 에 현재 새로 계산된 Role 맵핑 리스트로 덮어쓰게 됨.
            resourcesMap.put(presentResource, configList);

            // 이전 resource 비교위해 저장
            preResource = presentResourceStr;

        }
		return resourcesMap;
	}

}
