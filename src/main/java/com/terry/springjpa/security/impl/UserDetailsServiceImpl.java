package com.terry.springjpa.security.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.terry.springjpa.common.converter.MemberToMemberVOConverter;
import com.terry.springjpa.entity.Authority;
import com.terry.springjpa.entity.Member;
import com.terry.springjpa.repository.LoginAuthorities;
import com.terry.springjpa.vo.MemberVO;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private boolean enableAuthorities = false;						// 권한이용하는지의 여부
	private boolean enableGroups = false;							// 권한 관리시 그룹핑 이용하는지의 여부
	private String rolePrefix = "";

	private LoginAuthorities loginAuthorities;
	
	private MemberToMemberVOConverter memberToMemberVOConverter;
	
	protected final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	
	public UserDetailsServiceImpl(){
		
	}
	
	public UserDetailsServiceImpl(boolean enableAuthorities, boolean enableGroups, String rolePrefix){
		this.enableAuthorities = enableAuthorities;
		this.enableGroups = enableGroups;
		this.rolePrefix = rolePrefix;
	}
	
	/**
	 * 로그인 id를 이용해 사용자 정보를 조회한다
	 */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Member> users = loginAuthorities.loadUsersByLoginId(username);

        if (users.size() == 0) {
            logger.debug("Query returned no results for user '" + username + "'");
            
            throw new UsernameNotFoundException(
                    messages.getMessage("JdbcDaoImpl.notFound", new Object[]{username}, "Username {0} not found"));
        }

        MemberVO user = memberToMemberVOConverter.convert(users.get(0)); // contains no GrantedAuthority[]

        Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

        if (enableAuthorities) {
            // dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
        	List<Authority> listAuthorities = loginAuthorities.loadUserAuthorities(username);
        	for(Authority authority : listAuthorities){
        		String roleName = rolePrefix + authority.getAuthorityName();
        		dbAuthsSet.add(new SimpleGrantedAuthority(roleName));
        	}
        }

        if (enableGroups) {
        	List<Authority> listGroupAuthorities = loginAuthorities.loadGroupAuthorities(username);
        	for(Authority authority : listGroupAuthorities){
        		String roleName = rolePrefix + authority.getAuthorityName();
        		dbAuthsSet.add(new SimpleGrantedAuthority(roleName));
        	}
        }

        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);


        if (dbAuths.size() == 0) {
            logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");

            throw new UsernameNotFoundException(
                    messages.getMessage("JdbcDaoImpl.noAuthority",
                            new Object[] {username}, "User {0} has no GrantedAuthority"));
        }

        user.setAuthorities(dbAuths);
        
        /*
        return 하는 객체의 타입을 신경써야 한다. 
        이 타입이 나중에 Spring MVC의 Controller에서 @AuthenticationPrincipal 어노테이션을 이용해서 로그인 한 사용자 정보를 가져올때
        여기서 return 하는 타입으로 사용자 정보를 가져오기 때문에 
        Controller에서 @Authentication을 이용해서 로그인 한 사용자 정보를 여기서 return 한 타입의 객체로 받아야 한다
        주석처리한 createUserDetails 메소드를 이용해서 return 할 경우 Controller에서 @AuthenticationPrincipal 어노테이션을 이용할때 User 클래스로 받아야 한다
        */
        return user;
        // return createUserDetails(username, user, dbAuths);
    }

    /*
    private UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
            List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();

        return new User(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
                true, true, true, combinedAuthorities);
    }
    */

	public boolean isEnableAuthorities() {
		return enableAuthorities;
	}

	public void setEnableAuthorities(boolean enableAuthorities) {
		this.enableAuthorities = enableAuthorities;
	}

	public boolean isEnableGroups() {
		return enableGroups;
	}

	public void setEnableGroups(boolean enableGroups) {
		this.enableGroups = enableGroups;
	}

	public String getRolePrefix() {
		return rolePrefix;
	}

	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	public LoginAuthorities getLoginAuthorities() {
		return loginAuthorities;
	}

	public void setLoginAuthorities(LoginAuthorities loginAuthorities) {
		this.loginAuthorities = loginAuthorities;
	}

	public MemberToMemberVOConverter getMemberToMemberVOConverter() {
		return memberToMemberVOConverter;
	}

	public void setMemberToMemberVOConverter(MemberToMemberVOConverter memberToMemberVOConverter) {
		this.memberToMemberVOConverter = memberToMemberVOConverter;
	}
}
