package com.terry.springjpa.vo;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;
import org.springframework.data.repository.CrudRepository;

import com.terry.springjpa.common.annotation.ExistCheck;
import com.terry.springjpa.entity.Member;

public class MemberVO implements VOConvert<Member, Long> {
	
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

	private LocalDateTime insertDateTime;
	private LocalDateTime updateDateTime;
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
	@Override
	public Member convertToEntity(CrudRepository<Member, Long> repository) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		Member member = null;
		if(this.getIdx() == null){
			member = new Member();
		}else{
			member = repository.findOne(this.getIdx());
		}
		
		member.setLoginId(this.loginId);
		member.setPassword(this.password);
		member.setName(this.name);
		member.setEmail(this.email);
		
		return member;
	}
	
	
}
