package com.terry.springjpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.terry.springjpa.entity.idclass.GroupsAuthorityId;

@Entity
@Table(name="GROUPS_AUTHORITY")
@IdClass(GroupsAuthorityId.class)
public class GroupsAuthority {

	@Id
	@ManyToOne
	@JoinColumn(name="GROUPS_IDX")
	private Groups groups;
	
	@Id
	@ManyToOne
	@JoinColumn(name="AUTHORITY_IDX")
	private Authority authority;
	
	public GroupsAuthority(){
		
	}
	
	public GroupsAuthority(Groups groups, Authority authority){
		this.groups = groups;
		this.authority = authority;
	}

	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
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
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
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
		GroupsAuthority other = (GroupsAuthority) obj;
		if (authority == null) {
			if (other.getAuthority() != null)
				return false;
		} else if (!authority.equals(other.getAuthority()))
			return false;
		if (groups == null) {
			if (other.getGroups() != null)
				return false;
		} else if (!groups.equals(other.getGroups()))
			return false;
		return true;
	}
	
	
}
