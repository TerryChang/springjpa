package com.terry.springjpa.entity;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.joda.time.LocalDateTime;

import com.terry.springjpa.entity.embed.InsertUpdateDT;

@Entity
@Table(name="MEMBER")
@SequenceGenerator(name="MemberSequenceGenerator", sequenceName="MEMBER_SEQUENCE", initialValue=1, allocationSize=1)
@Access(AccessType.FIELD)
public class Member {

	private Long idx;
	
	@Column(name="LOGINID")
	private String loginId;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="EMAIL")
	private String email;
	
	@Embedded
	private InsertUpdateDT insertUpdateDT;
	
	@OneToMany(mappedBy="member")
	private List<MemberAuthority> memberAuthorities;
	
	@OneToMany(mappedBy="member")
	private List<MemberGroups> memberGroups;

	public Member(){
		
	}
	
	public Member(String loginId, String password, String name, String email){
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.email = email;
	}
	
	@Id
	@Column(name="IDX")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MemberSequenceGenerator")
	@Access(AccessType.PROPERTY)
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
	
	public List<MemberAuthority> getMemberAuthorities() {
		return memberAuthorities;
	}

	public void setMemberAuthorities(List<MemberAuthority> memberAuthorities) {
		this.memberAuthorities = memberAuthorities;
	}

	public List<MemberGroups> getMemberGroups() {
		return memberGroups;
	}

	public void setMemberGroups(List<MemberGroups> memberGroups) {
		this.memberGroups = memberGroups;
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		if (email == null) {
			if (other.getEmail() != null)
				return false;
		} else if (!email.equals(other.getEmail()))
			return false;
		return true;
	}
}
