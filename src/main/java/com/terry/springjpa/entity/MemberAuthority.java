package com.terry.springjpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.terry.springjpa.entity.idclass.MemberAuthorityId;

@Entity
@Table(name="MEMBER_AUTHORITY")
@IdClass(MemberAuthorityId.class)
public class MemberAuthority {

	@Id
	@ManyToOne
	@JoinColumn(name="MEMBER_IDX")
	private Member member;
	
	@Id
	@ManyToOne
	@JoinColumn(name="AUTHORITY_IDX")
	private Authority authority;
	
	public MemberAuthority(){
		
	}
	
	public MemberAuthority(Member member, Authority authority){
		this.member = member;
		this.authority = authority;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberAuthority other = (MemberAuthority) obj;
		if (authority == null) {
			if (other.getAuthority() != null)
				return false;
		} else if (!authority.equals(other.getAuthority()))
			return false;
		if (member == null) {
			if (other.getMember() != null)
				return false;
		} else if (!member.equals(other.getMember()))
			return false;
		return true;
	}
}
