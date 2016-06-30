package com.terry.springjpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.terry.springjpa.entity.idclass.MemberGroupsId;

@Entity
@Table(name="MEMBER_GROUPS")
@IdClass(MemberGroupsId.class)
public class MemberGroups {

	@Id
	@ManyToOne
	@JoinColumn(name="MEMBER_IDX")
	private Member member;
	
	@Id
	@ManyToOne
	@JoinColumn(name="GROUPS_IDX")
	private Groups groups;
	
	public MemberGroups(){
		
	}
	
	public MemberGroups(Member member, Groups groups){
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
		if (getClass() != obj.getClass())
			return false;
		MemberGroups other = (MemberGroups) obj;
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
