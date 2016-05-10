package com.terry.springjpa.vo;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

import com.terry.springjpa.common.annotation.ExistCheck;

public class MemberVO implements UserDetails{
	
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	
	private Long idx;
	
	@NotBlank
	@Size(min=8, max=16)
	@ExistCheck(tableName="member", columnName="loginId")
	private String loginId;
	
	@NotBlank
	@Size(min=8, max=20)
	@Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$")	// 길이체크 뺀거(길이는 @Size에서 해주고 있기 때문에 뺄수도 있다)
	// @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$", groups={InsertValidated.class, UpdateValidated.class}, message="{CheckPattern.password}")		// 길이체크 뺀거(길이는 @Size에서 해주고 있기 때문에 뺄수도 있다)
	// @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{10,15}$", message="{CheckPattern.password}")		// 길이체크 포함한거
	private String password;
	
	@NotBlank
	@Size(min=2, max=10)
	private String name;
	
	@NotBlank
	@Size(min=10, max=30)
	@Email
	private String email;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime insertDateTime;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDateTime;
	
	// Spring Security 관련 멤버 변수들 정의 시작
	Collection<? extends GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();		// 계정이 갖고 있는 권한 목록
	boolean accountNonExpired = true;						// 계정 만료 여부(true일 경우 만료되지 않았음을 의미)
	boolean accountNonLocked = true;						// 계정 잠김 여부(true일 경우 계정이 잠기지 않았음을 의미)
	boolean credentialsNonExpired = true;					// 계정의 패스워드 만료 여부(true일 경우 계정의 패스워드가 아직 만료되지 않았음을 의미)
	boolean enabled = true;									// 계정 사용 가능 여부(true일 경우 계정이 사용 가능)
	// Spring Security 관련 멤버 변수들 정의 끝
	
	public MemberVO(){
		
	}

	/**
	 * 회원정보의 실제 유효한 멤버변수들만 설정하는 생성자
	 * @param idx
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @param insertDateTime
	 * @param updateDateTime
	 */
	public MemberVO(Long idx, String loginId, String password, String name, String email, LocalDateTime insertDateTime, LocalDateTime updateDateTime){
		this.idx = idx;
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.insertDateTime = insertDateTime;
		this.updateDateTime = updateDateTime;
	}
	
	/**
	 * Spring Security 관련으로 사용되는 멤버변수들만 설정하는 생성자
	 * @param accountNonExpired
	 * @param accountNonLocked
	 * @param credentialsNonExpired
	 * @param enabled
	 * @param authorities
	 */
	public MemberVO(boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Collection<? extends GrantedAuthority> authorities){
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.authorities = authorities;
	}
	
	/**
	 * 회원정보의 실제 유효한 멤버변수들과 Spring Security 관련으로 사용되는 멤버변수들 모두 설정하는 생성자
	 * @param idx
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @param insertDateTime
	 * @param updateDateTime
	 * @param accountNonExpired
	 * @param accountNonLocked
	 * @param credentialsNonExpired
	 * @param enabled
	 * @param authorities
	 */
	public MemberVO(Long idx, String loginId, String password, String name, String email, LocalDateTime insertDateTime, LocalDateTime updateDateTime, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Collection<? extends GrantedAuthority> authorities){
		this(idx, loginId, password, name, email, insertDateTime, updateDateTime);
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.authorities = authorities;
	}
	
	public Long getIdx() {
		return idx;
	}
	public void setIdx(Long idx) {
		this.idx = idx;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getInsertDateTime() {
		return insertDateTime;
	}
	public void setInsertDateTime(LocalDateTime insertDateTime) {
		this.insertDateTime = insertDateTime;
	}
	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return loginId;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return accountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return accountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return credentialsNonExpired;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}
}
