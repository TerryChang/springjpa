package com.terry.springjpa.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;

import com.terry.springjpa.entity.embed.InsertUpdateDT;

@Entity
@Table(name="MEMBER")
@SequenceGenerator(name="MemberSequenceGenerator", sequenceName="MEMBER_SEQUENCE", initialValue=1, allocationSize=1)
public class Member implements UpdateEntity<Member>{

	@Id
	@Column(name="IDX")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MemberSequenceGenerator")
	private Long idx;
	
	@Column(name="LOGINID")
	@NotBlank
	@Size(min=8, max=16)
	private String loginId;
	
	@Column(name="PASSWORD")
	@NotBlank
	@Size(min=8, max=20)
	@Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$")	// 길이체크 뺀거(길이는 @Size에서 해주고 있기 때문에 뺄수도 있다)
	// @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$", groups={InsertValidated.class, UpdateValidated.class}, message="{CheckPattern.password}")		// 길이체크 뺀거(길이는 @Size에서 해주고 있기 때문에 뺄수도 있다)
	// @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{10,15}$", message="{CheckPattern.password}")		// 길이체크 포함한거
	private String password;
	
	@Column(name="NAME")
	@NotBlank
	@Size(min=2, max=10)
	private String name;
	
	@Column(name="EMAIL")
	@NotBlank
	@Size(min=10, max=30)
	@Email
	private String email;
	
	@Embedded
	private InsertUpdateDT insertUpdateDT;

	public Member(){
		
	}
	
	public Member(String loginId, String password, String name){
		this.loginId = loginId;
		this.password = password;
		this.name = name;
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

	public InsertUpdateDT getInsertUpdateDT() {
		return insertUpdateDT;
	}

	public void setInsertUpdateDT(InsertUpdateDT insertUpdateDT) {
		this.insertUpdateDT = insertUpdateDT;
	}
	
	@PrePersist
	public void onCreate(){
		insertUpdateDT = new InsertUpdateDT();
		insertUpdateDT.setInsertDateTime(LocalDateTime.now());
	}
	
	@PreUpdate
	public void onUpdate(){
		insertUpdateDT.setUpdateDateTime(LocalDateTime.now());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idx == null) ? 0 : idx.hashCode());
		result = prime * result + ((loginId == null) ? 0 : loginId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Member))
			return false;
		Member other = (Member) obj;
		if (idx == null) {
			if (other.getIdx() != null)
				return false;
		} else if (!idx.equals(other.getIdx()))
			return false;
		if (loginId == null) {
			if (other.getLoginId() != null)
				return false;
		} else if (!loginId.equals(other.getLoginId()))
			return false;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		if (password == null) {
			if (other.getPassword() != null)
				return false;
		} else if (!password.equals(other.getPassword()))
			return false;
		return true;
	}

	@Override
	public void entityUpdate(Member member) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		loginId = member.getLoginId();
		password = member.getPassword();
		name = member.getName();
		email = member.getEmail();
	}
}
