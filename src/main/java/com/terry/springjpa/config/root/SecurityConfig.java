package com.terry.springjpa.config.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.terry.springjpa.common.converter.MemberToMemberVOConverter;
import com.terry.springjpa.repository.LoginAuthorities;
import com.terry.springjpa.security.impl.CustomAccessDeniedHandler;
import com.terry.springjpa.security.impl.CustomAuthenticationFailureHandler;
import com.terry.springjpa.security.impl.CustomAuthenticationSuccessHandler;
import com.terry.springjpa.security.impl.ReloadableFilterInvocationSecurityMetadataSource;
import com.terry.springjpa.security.impl.UserDetailsServiceImpl;

@Configuration
@Import({SecurityBeanConfig.class})
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Autowired
	CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Autowired
	FilterSecurityInterceptor filterSecurityInterceptor;
	

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		// super.configure(web);
		web.ignoring().antMatchers("/bootstrap/**/*", "/js/**/*");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.addFilterBefore(filterSecurityInterceptor, FilterSecurityInterceptor.class)
			.authorizeRequests()
			.and()
			.formLogin()
				.usernameParameter("loginId")
				.passwordParameter("loginPwd")
				.loginPage("/login.do").permitAll()
				.defaultSuccessUrl("/main.do").permitAll()
				.successHandler(customAuthenticationSuccessHandler)			// Login Success Handler 설정
				.failureHandler(customAuthenticationFailureHandler)			// Login Failure Handler 설정
			.and()
			.anonymous()
				.authorities("ANONYMOUS")
			.and()
			.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)		// Access Denied Handler 설정
			.and()
			.logout()
				.logoutSuccessUrl("/main.do").permitAll()
				.deleteCookies("JSESSIONID");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsServiceImpl);	// Password Encoder 작업 추가할것
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		AuthenticationManager authenticationManager = super.authenticationManagerBean();
		return authenticationManager;
	}
	
	@Bean
	public FilterSecurityInterceptor customFilterSecurityInterceptor(AuthenticationManager authenticationManager, AccessDecisionManager accessDecisionManager, ReloadableFilterInvocationSecurityMetadataSource rfisms){
		FilterSecurityInterceptor customFilterSecurityInterceptor = new FilterSecurityInterceptor();
		customFilterSecurityInterceptor.setAuthenticationManager(authenticationManager);
		customFilterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager);						// AccessDecisionManager 설정
		customFilterSecurityInterceptor.setSecurityMetadataSource(rfisms);										// SecurityMetadataSource 설정
		return customFilterSecurityInterceptor;
	}

	
}
