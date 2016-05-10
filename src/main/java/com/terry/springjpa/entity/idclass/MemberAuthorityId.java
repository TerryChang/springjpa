package com.terry.springjpa.entity.idclass;

import java.io.Serializable;

import com.terry.springjpa.entity.Authority;
import com.terry.springjpa.entity.Member;

public class MemberAuthorityId implements Serializable {
	
	private static final long serialVersionUID = 7329339948425089772L;
	
	private Member member;
	private Authority authority;
	
	public MemberAuthorityId(){
		
	}
	
	public MemberAuthorityId(Member member, Authority authority){
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
		MemberAuthorityId other = (MemberAuthorityId) obj;
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
