package com.terry.springjpa.config.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.terry.springjpa.common.converter.MemberToMemberVOConverter;
import com.terry.springjpa.repository.LoginAuthorities;
import com.terry.springjpa.security.SecuredObjectService;
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
	public FilterSecurityInterceptor customFilterSecurityInterceptor(){
		return null;
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
				// .successHandler(null)			// Login Success Handler 설정
				// .failureHandler(null)			// Login Failure Handler 설정
				
			.and()
			.anonymous()
				.authorities("ANONYMOUS")
			.and()
			// .exceptionHandling().accessDeniedHandler(null)		// Access Denied Handler 설정
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
