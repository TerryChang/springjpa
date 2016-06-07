package com.terry.springjpa.config.root;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.terry.springjpa.common.converter.MemberToMemberVOConverter;
import com.terry.springjpa.repository.LoginAuthorities;
import com.terry.springjpa.repository.impl.SecuredObjectRepositoryImpl;
import com.terry.springjpa.security.SecuredObjectService;
import com.terry.springjpa.security.impl.CustomAccessDeniedHandler;
import com.terry.springjpa.security.impl.CustomAuthenticationFailureHandler;
import com.terry.springjpa.security.impl.CustomAuthenticationSuccessHandler;
import com.terry.springjpa.security.impl.CustomDefaultRedirectStrategy;
import com.terry.springjpa.security.impl.ReloadableFilterInvocationSecurityMetadataSource;
import com.terry.springjpa.security.impl.SecuredObjectServiceImpl;
import com.terry.springjpa.security.impl.UrlResourcesMapFactoryBean;
import com.terry.springjpa.security.impl.UserDetailsServiceImpl;

@Configuration
public class SecurityBeanConfig {
	
	@Autowired
	LoginAuthorities loginAuthorities;
	
	@Autowired
	MemberToMemberVOConverter memberToMemberVOConverter;
	
	private String ajaxHeaderKey = "X-Ajax-Call";
	
	@Bean
	public SecuredObjectServiceImpl SecuredObjectServiceImpl(SecuredObjectRepositoryImpl securedObjectRepositoryImpl){
		SecuredObjectServiceImpl securedObjectServiceImpl = new SecuredObjectServiceImpl();
		securedObjectServiceImpl.setSecuredObjectRepositoryImpl(securedObjectRepositoryImpl);
		return securedObjectServiceImpl;
	}
	
	@Bean
	public UserDetailsServiceImpl userDetailsServiceImpl(){
		UserDetailsServiceImpl userDetailsServiceImpl = new UserDetailsServiceImpl(true, false, "");
		userDetailsServiceImpl.setLoginAuthorities(loginAuthorities);
		userDetailsServiceImpl.setMemberToMemberVOConverter(memberToMemberVOConverter);
		return userDetailsServiceImpl;
	}
	
	@Bean
	public AccessDecisionManager accessDecisionManager(UserDetailsServiceImpl userDetailsServiceImpl, SecuredObjectService securedObjectService) throws Exception{
		AffirmativeBased affirmativeBased = null;
		List<AccessDecisionVoter> decisionVoterList = new ArrayList<AccessDecisionVoter>();
		RoleVoter roleVoter = new RoleVoter();
		roleVoter.setRolePrefix(userDetailsServiceImpl.getRolePrefix());
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy(securedObjectService.getRolesHierarchy());
		RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchyImpl);
		roleHierarchyVoter.setRolePrefix(userDetailsServiceImpl.getRolePrefix());
		decisionVoterList.add(roleVoter);
		decisionVoterList.add(roleHierarchyVoter);
		affirmativeBased = new AffirmativeBased(decisionVoterList);
		affirmativeBased.setAllowIfAllAbstainDecisions(false);		// voter가 모두 기권할 경우 이것을 권한 허용으로 볼지의 여부(true이면 모두 기권할 경우 이것을 권한 허용으로 본다)
		return affirmativeBased;
	}
	
	@Bean(initMethod="init")
	public UrlResourcesMapFactoryBean urlResourcesMapFactoryBean(SecuredObjectService securedObjectService){
		UrlResourcesMapFactoryBean urmfb = new UrlResourcesMapFactoryBean();
		urmfb.setSecuredObjectService(securedObjectService);
		return urmfb;
	}
	
	@Bean
	public ReloadableFilterInvocationSecurityMetadataSource reloadableFilterInvocationSecurityMetadataSource(UrlResourcesMapFactoryBean urmfb, SecuredObjectServiceImpl sosi) throws Exception{
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> destMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>(urmfb.getObject());
		ReloadableFilterInvocationSecurityMetadataSource rfism = new ReloadableFilterInvocationSecurityMetadataSource(destMap);
		rfism.setSecuredObjectService(sosi);
		return rfism;
	}
	
	@Bean
	public CustomDefaultRedirectStrategy customDefaultRedirectStrategy(){
		return new CustomDefaultRedirectStrategy(false, ajaxHeaderKey);
	}
	
	@Bean
	public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler(){
		CustomAuthenticationSuccessHandler cash = new CustomAuthenticationSuccessHandler("loginRedirect", "/main.do", false, customDefaultRedirectStrategy());
		return cash;
	}
	
	@Bean
	public CustomAuthenticationFailureHandler customAuthenticationFailureHandler(){
		CustomAuthenticationFailureHandler cafh = new CustomAuthenticationFailureHandler("loginId", "loginPwd", "securityexceptionmsg", "/login.do", ajaxHeaderKey);
		return cafh;
	}
	
	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler(){
		CustomAccessDeniedHandler cadh = new CustomAccessDeniedHandler("/error.do", ajaxHeaderKey);
		return cadh;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
