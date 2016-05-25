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
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.terry.springjpa.common.converter.MemberToMemberVOConverter;
import com.terry.springjpa.repository.LoginAuthorities;
import com.terry.springjpa.repository.impl.SecuredObjectRepositoryImpl;
import com.terry.springjpa.security.SecuredObjectService;
import com.terry.springjpa.security.impl.CustomAccessDeniedHandler;
import com.terry.springjpa.security.impl.CustomAuthenticationFailureHandler;
import com.terry.springjpa.security.impl.CustomAuthenticationSuccessHandler;
import com.terry.springjpa.security.impl.ReloadableFilterInvocationSecurityMetadataSource;
import com.terry.springjpa.security.impl.SecuredObjectServiceImpl;
import com.terry.springjpa.security.impl.UrlResourcesMapFactoryBean;
import com.terry.springjpa.security.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	SecuredObjectService securedObjectService;
	
	@Autowired
	LoginAuthorities loginAuthorities;
	
	@Autowired
	MemberToMemberVOConverter memberToMemberVOConverter;
	
	@Bean
	public UserDetailsService userDetailsService(){
		UserDetailsServiceImpl userDetailsServiceImpl = new UserDetailsServiceImpl(true, true, "");
		userDetailsServiceImpl.setLoginAuthorities(loginAuthorities);
		userDetailsServiceImpl.setMemberToMemberVOConverter(memberToMemberVOConverter);
		return userDetailsServiceImpl;
	}
	
	@Bean
	public FilterSecurityInterceptor customFilterSecurityInterceptor(AuthenticationManager authenticationManager, AccessDecisionManager accessDecisionManager, ReloadableFilterInvocationSecurityMetadataSource rfisms){
		FilterSecurityInterceptor customFilterSecurityInterceptor = new FilterSecurityInterceptor();
		customFilterSecurityInterceptor.setAuthenticationManager(authenticationManager);
		customFilterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager);						// AccessDecisionManager 설정
		customFilterSecurityInterceptor.setSecurityMetadataSource(rfisms);										// SecurityMetadataSource 설정
		return customFilterSecurityInterceptor;
	}
	
	@Bean
	public AccessDecisionManager accessDecisionManager(UserDetailsServiceImpl userDetailsServiceImpl){
		AffirmativeBased affirmativeBased = null;
		List<AccessDecisionVoter> decisionVoterList = new ArrayList<AccessDecisionVoter>();
		RoleVoter roleVoter = new RoleVoter();
		roleVoter.setRolePrefix(userDetailsServiceImpl.getRolePrefix());
		decisionVoterList.add(roleVoter);
		affirmativeBased = new AffirmativeBased(decisionVoterList);
		affirmativeBased.setAllowIfAllAbstainDecisions(false);		// voter가 모두 기권할 경우 이것을 권한 허용으로 볼지의 여부(true이면 모두 기권할 경우 이것을 권한 허용으로 본다)
		return affirmativeBased;
	}
	
	@Bean
	public SecuredObjectServiceImpl SecuredObjectServiceImpl(SecuredObjectRepositoryImpl securedObjectRepositoryImpl){
		SecuredObjectServiceImpl securedObjectServiceImpl = new SecuredObjectServiceImpl();
		securedObjectServiceImpl.setSecuredObjectRepositoryImpl(securedObjectRepositoryImpl);
		return securedObjectServiceImpl;
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
	public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler(){
		CustomAuthenticationSuccessHandler cash = new CustomAuthenticationSuccessHandler("loginRedirect", "/main.do", false);
		return cash;
	}
	
	@Bean
	public CustomAuthenticationFailureHandler customAuthenticationFailureHandler(){
		CustomAuthenticationFailureHandler cafh = new CustomAuthenticationFailureHandler("loginid", "loginpwd", "loginRedirect", "securityexceptionmsg", "/login.do?fail=true");
		return cafh;
	}
	
	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler(){
		CustomAccessDeniedHandler cadh = new CustomAccessDeniedHandler("/common/access_denied2.do", "X-Ajax-call");
		return cadh;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		// super.configure(web);
		web.ignoring().antMatchers("/bootstrap/**/*", "/js/**/*");
	}
	
	/*
	 <http auto-config="true" use-expressions="true">
		<!-- 
		<intercept-url pattern="/admin/**" access="hasRole('ROLE_ADMIN')" />
		<intercept-url pattern="/login.do" access="isAnonymous()" />
		<intercept-url pattern="/main.do" access="permitAll" />
		<intercept-url pattern="/**" access="permitAll"/>
		-->
		<form-login
			username-parameter="loginid"
			password-parameter="loginpwd" 
			login-page="/login.do"
			default-target-url="/main.do"
			authentication-success-handler-ref="customAuthenticationSuccessHandler"
			authentication-failure-handler-ref="customAuthenticationFailureHandler"
		/>
		<anonymous granted-authority="ANONYMOUS" />
		<logout 
			logout-success-url="/main.do"
			delete-cookies="JSESSIONID"
		/>
		<access-denied-handler ref="accessDenied"/>
		<custom-filter before="FILTER_SECURITY_INTERCEPTOR" ref="filterSecurityInterceptor"/>        
	</http>
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		// super.configure(http);
		http
			// .addFilterBefore(filter, FilterSecurityInterceptor.class)
			.formLogin()
				.usernameParameter("loginId")
				.passwordParameter("loginPwd")
				.loginPage("/login.do")
				.defaultSuccessUrl("/main.do")
				.successHandler(customAuthenticationSuccessHandler())			// Login Success Handler 설정
				.failureHandler(customAuthenticationFailureHandler())			// Login Failure Handler 설정
			.and()
			.anonymous()
				.authorities("ANONYMOUS")
			.and()
			.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())		// Access Denied Handler 설정
			.and()
			.logout()
				.logoutSuccessUrl("/main.do")
				.deleteCookies("JSESSIONID");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsService());	// Password Encoder 작업 추가할것
	}
	
	
	
}
