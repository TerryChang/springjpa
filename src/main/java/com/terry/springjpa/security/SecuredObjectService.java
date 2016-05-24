package com.terry.springjpa.security;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

public interface SecuredObjectService {
	/**
     * 롤에 대한 URL의 매핑 정보를 얻어온다.
     * @return
     * @throws Exception
     */
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getRolesAndUrl() throws Exception;
 
    /**
     * 롤에 대한 메소드의 매핑 정보를 얻어온다.
     * @return
     * @throws Exception
     */
    public LinkedHashMap<String, List<ConfigAttribute>> getRolesAndMethod() throws Exception;
    
    /**
     * 롤에 대한 계층 구조를 문자열로 return 한다
     * @return
     * @throws Exception
     */
    public String getRolesHierarchy() throws Exception;
}
