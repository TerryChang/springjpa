package com.terry.springjpa.config.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private AccessDecisionManager accessDecisionManager;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private FilterSecurityInterceptor filterSecurityInterceptor;
	
	@Autowired
	private RoleHierarchy roleHierarchy;

	/**
	 * jstl을 이용해서 권한 체크를 할때(ex : <sec:authorize access="hasRole('MEMBER')">) 사용하는 것이 SecurityExpressionHandler 인터페이스를 구현한 클래스인데
	 * 권한에 대한 상위, 하위 개념이 존재할 경우 이 클래스에 RoleHierarchy 를 설정해주면 된다
	 * 이러한 설정 없이 그냥 사용하면 jstl 사용할 때 찾고자 하는 권한에 대한 상위 하위 개념이 없기 때문에..
	 * 단순히 이 권한을 가지고 있느냐로만 판단하게 된다.
	 * 위의 예를 가지고 다시 설명하자면 로그인 한 사람이 ADMIN 권한이 있고 ADMIN이 MEMBER 권한의 상위 권한일 경우 <sec:authorize access="hasRole('MEMBER')">를 만족하기 때문에 이 태그로 감싼것을 보여줘야 하는 것이 올바른 동작이지만
	 * SecurityExpressionHandler 인터페이스를 구현한 클래스에 RoleHierarchy 를 설정하지 않으면 ADMIN 권한을 갖고 있는 것이지 MEMBER 권한을 가지고 있는 것은 아니라고 판단하고 이 태그로 감싼것을 보여주지 않게 된다.
	 * SecurityExpressionHandler 인터페이스를 구현한 클래스를 WebSecurity에 설정해주면 되는데 이때 주의사항이 있다
	 * SecurityExpressionHandler 인터페이스를 구현한 클래스를 Bean으로 설정하지 말고 객체로 만들어서 설정해야 한다.
	 * AbstractAuthorizeTag 클래스의 getExpressionHandler 메소드를 이용해서 이 SecurityExpressionHandler 인터페이스를 구현한 클래스를 가져오게 되는데..
	 * Bean으로 만들어서 이를 설정할 경우 getExpressionHandler 메소드에서 내가 Bean으로 만들어서 설정한 것과 Spring Security가 기본적으로 만든것 이렇게 2개가 등록되기 때문에..
	 * 설정에 오해가 생기는 상황이 오게 된다. 추측엔 Spring Security가 기본적으로 등록하는거와 Bean으로 설정한것 이렇게 자동으로 등록시켜버리는다고 추측된다
	 * 그래서 bean으로 이 클래스를 등록하지 말고 configure 메소드 안에서 객체로 만들어서 설정하도록 한다
	 * 
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		// super.configure(web);
		DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy);
		
		web.ignoring().antMatchers("/bootstrap/**/*", "/js/**/*")
			.and()
			.privilegeEvaluator(webInvocationPrivilegeEvaluator()) 		// webInvocationPrivilegeEvaluator() 메소드에 주석으로 설명되어 있으니 참조할 것
			.expressionHandler(defaultWebSecurityExpressionHandler);	
	}
	
	/**
	 * Spring Security를 Java Config 방식으로 설정하려 하는 경우  CSRF 공격에 대한 방어 기능 설정이 default로 이루어지게 된다.
	 * 그렇기 때문에 이 기능을 끌려면 HttpSecurity 객체에서 csrf() 메소드를 호출한뒤 disable() 메소드를 호출해줘야 CSRF 공격에 대한 방어 기능 설정을 안하게끔 할 수 있다
	 * 
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
	 * 
	 * CSRF 기능이 활성화될 경우 logout을 시도할려면 POST 방식으로 해야 한다. 
	 * 이 부분을 GET 방식으로 하게끔 할려면 logoutRequestMatcher메소드를 이용해서 메소드와 상관없이 동작하게끔 설정하면 된다
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.addFilterBefore(filterSecurityInterceptor, FilterSecurityInterceptor.class)
			// .csrf().disable()			// Spring Security를 Java Config 방식으로 설정할 경우 CSRF 공격 방어 기능은 default로 동작한다. 이 설정을 하지 않을려고 할 경우에 사용한다
			.authorizeRequests().anyRequest().authenticated()
			.and()
			.formLogin()
				.usernameParameter("loginId")
				.passwordParameter("loginPwd")
				.loginPage("/login.do").permitAll()								// loginPage 메소드를 이용해서 로그인 페이지를 지정할 경우 로그인 처리를 하는 URL도 이 
				.loginProcessingUrl("/j_spring_security_check").permitAll()
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
				// .logoutUrl("/logout.do")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout.do"))		// CSRF 기능 활성화된 상태에서 logout을 GET 방식으로 하게끔 할려면 logoutRequestMatcher메소드를 이용해서 메소드와 상관없이 동작하게끔 설정하면 된다
				.logoutSuccessUrl("/main.do").permitAll()
				.deleteCookies("JSESSIONID");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder);	// Password Encoder 작업 추가할것
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		AuthenticationManager authenticationManager = super.authenticationManagerBean();
		return authenticationManager;
	}
	
	/**
	 * Cutom FilterSecurityInterceptor 객체를 bean으로 등록한다
	 * 눈여겨 봐야 할 코드가 customFilterSecurityInterceptor.setRejectPublicInvocations(false) 이 부분이다
	 * 이 메소드에 인자를 true로 주면 우리가 관리대상으로 삼은 자원(URL, Method)들이 아닌 다른 자원들에 대한 접근이 이루어졌을때 
	 * 접근을 하지 못하도록 예외(IllegalArgumentExcption)을 던지게 된다
	 * 그러나 false로 지정하면 다른 자원에 대한 접근이 이루어졌을 경우 이를 public 자원이라 판단하고 자원 접근을 허용하게 된다
	 * 이것이 왜 필요하냐면 우리가 관리대상이 되는 자원(URL, 메소드)를 만든뒤에 Spring Security에 관리대상으로 등록하지 않으면
	 * 예외가 발생되게끔 해야 하기 때문에 그렇다.(만약 만들기만 하고 Spring Security에 관리 대상으로 등록시키지 않게 되면 권한 여부와는 상관없이 모두 접근되기 때문에 이를 막기 위한 속성으로 있는 것이다)
	 * 개발할때는 개발의 편리성 때문에 이것을 false로 하지만 나중에 완성된 뒤에 테스트 하는 시점에서는 이 부분을 true로 해주어야 보안에 더 철저해진 테스트를 진행할 수 있으리라 생각한다
	 * 이 메소드가 설정하게 되는 rejectPublicInvocations 속성은 기본값이 false 이기 때문에 이 코드를 명시하지 않아도 되지만
	 * 이 부분에 대한 설명을 하기 위해 추가해두었다
	 * @param authenticationManager
	 * @param accessDecisionManager
	 * @param rfisms
	 * @return
	 */
	@Bean
	public FilterSecurityInterceptor customFilterSecurityInterceptor(AuthenticationManager authenticationManager, AccessDecisionManager accessDecisionManager, ReloadableFilterInvocationSecurityMetadataSource rfisms){
		FilterSecurityInterceptor customFilterSecurityInterceptor = new FilterSecurityInterceptor();
		customFilterSecurityInterceptor.setAuthenticationManager(authenticationManager);
		customFilterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager);						// AccessDecisionManager 설정
		customFilterSecurityInterceptor.setSecurityMetadataSource(rfisms);										// SecurityMetadataSource 설정
		customFilterSecurityInterceptor.setRejectPublicInvocations(false);										
		return customFilterSecurityInterceptor;
	}

	/**
	 * spring security에서 제공하는 custom tag에서 다음과 같이 url을 이용해서 접근 권한을 체크할 수 있다
	 * &lt;sec:authorize url="/unitedBoard/unitedBoardList.do?boardTypeIdx=1"&gt;
	 *			공지사항
	 * &lt;/sec:authorize&gt;
	 * 위의 예는 /unitedBoard/unitedBoardList.do?boardTypeIdx=1 이 url을 접근할 수 있는 권한이 있을 경우 sec:authorize 태그로 감싸진 부분을 브라우저로 표현하게 된다
	 * &lt;sec:authorize access="hasRole('MEMBER')"&gt; 같이 hasRole을 이용한 권한 체크를 url로 대신 체크하는 것이다.
	 * 즉 정리하면 주어진 url에 접근가능한지의 여부에 따라 동작하는 태그 기능이라 볼 수 있는데
	 * 이 기능을 수행할려면 WebInvocationPrivilegeEvaluator 인터페이스를 구현한 Bean을 등록해서 사용해야 한다
	 * Spring Security에서는 기본적으로 WebInvocationPrivilegeEvaluator 인터페이스를 구현한 클래스로 DefaultWebInvocationPrivilegeEvaluator를 제공하는데
	 * 이 클래스를 이용해서 등록했다
	 *  
	 * 원래 이 bean을 등록하지 않아도 Spring Security는 Java Config 방식으로 등록할때 자동으로  WebInvocationPrivilegeEvaluator 인터페이스를 구현한 클래스가
	 * 만들어져 이 기능을 수행할 수 있다. 
	 * 그러나 이렇게 자동으로 만들어질 경우 우리가 커스터마이징한 FilterSecurityInterceptor 클래스를 이용해서 권한 접근 체크를 하지 않기 때문에
	 * 원하지 않는 결과가 나오게된다
	 * 그래서 우리가 커스터마이징한 FilterSecurityInterceptor 클래스를 이용해서 이 태그가 동작하게끔 하기 위해
	 * 우리가 커스터마이징한 FilterSecurityInterceptor 클래스를 DefaultWebInvocationPrivilegeEvaluator 클래스 bean을 만들때 사용하고
	 * 이를 configure(WebSecurity web) 메소드에서 WebSecurity 클래스의 privilegeEvaluator 메소드를 이용해 등록함으로써 이 기능을 정상적으로 동작하게끔 설정했다
	 * @return
	 */
	@Bean
	public WebInvocationPrivilegeEvaluator webInvocationPrivilegeEvaluator(){
		return new DefaultWebInvocationPrivilegeEvaluator(filterSecurityInterceptor);
	}
}
