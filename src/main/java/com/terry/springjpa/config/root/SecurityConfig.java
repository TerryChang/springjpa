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
	
	/**
	 * loginPage 메소드를 이용해서 로그인 페이지 URL을 따로 지정할 경우 loginPage 메소드가 AbstractAuthenticationFilterConfigurer 클래스의 
	 * updateAuthenticationDefaults 메소드를 실행시키면서 Spring Security에 기본적으로 설정되는 값을 loginPage 메소드를 통해 입력한 값으로 수정하게 된다.
	 * 예를 들어 loginPage 메소드를 통해 /login.do로 입력했으면 
	 *
	 * 1. 로그인 처리 URL을 /login.do 로 수정
	 * 2. 로그인 실패시 이동해야 할 url을 /login.do?failure로 수정
	 * 3. 로그아웃 한 뒤에 이동해야 할 url을 /login.do?logout 으로 수정
	 * 
	 * 이렇게 작업을 하게 된다. 때문에 loginPage 메소드를 사용해서 로그인 페이지 URL을 수정할 경우 이러한 기본값 수정 작업을 한다는 생각을 가져야 한다.
	 * 기본값 수정에 맞춰서 자신의 URL을 수정하거나
	 * 아니면 일일이 자신이 각 항목의 기본값을 수정하는 식으로 접근해야 한다 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.addFilterBefore(filterSecurityInterceptor, FilterSecurityInterceptor.class)
			.csrf().disable()
			.authorizeRequests()
			.and()
			.formLogin()
				.usernameParameter("loginId")
				.passwordParameter("loginPwd")
				.loginPage("/login.do").permitAll()								// loginPage 메소드를 이용해서 로그인 페이지를 지정할 경우 로그인 처리를 하는 URL도 이 
				// .loginProcessingUrl("/j_spring_security_check").permitAll()
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
