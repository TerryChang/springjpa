package com.terry.springjpa.entity.idclass;

import java.io.Serializable;

import com.terry.springjpa.entity.Authority;
import com.terry.springjpa.entity.Groups;

public class GroupsAuthorityId implements Serializable {

	private static final long serialVersionUID = -4510130434066793710L;
	
	private Groups groups;
	private Authority authority;
	
	public GroupsAuthorityId(){
		
	}
	
	public GroupsAuthorityId(Groups groups, Authority authority){
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
		if (!(obj instanceof GroupsAuthorityId))
			return false;
		GroupsAuthorityId other = (GroupsAuthorityId) obj;
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
