package com.terry.springjpa.entity.idclass;

import java.io.Serializable;

import com.terry.springjpa.entity.Groups;
import com.terry.springjpa.entity.Member;

public class MemberGroupsId implements Serializable {

	private static final long serialVersionUID = 2640369442441665212L;
	
	private Member member;
	private Groups groups;
	
	public MemberGroupsId(){
		
	}
	
	public MemberGroupsId(Member member, Groups groups){
		this.member = member;
		this.groups = groups;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MemberGroupsId))
			return false;
		MemberGroupsId other = (MemberGroupsId) obj;
		if (groups == null) {
			if (other.getGroups() != null)
				return false;
		} else if (!groups.equals(other.getGroups()))
			return false;
		if (member == null) {
			if (other.getMember() != null)
				return false;
		} else if (!member.equals(other.getMember()))
			return false;
		return true;
	}
}
