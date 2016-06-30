package com.terry.springjpa.entity.idclass;

import java.io.Serializable;

import com.terry.springjpa.entity.Authority;

public class AuthorityHierarchyId implements Serializable {
	
	private static final long serialVersionUID = -2690084632230206775L;

	private Authority parentAuthority;
	private Authority childAuthority;
	
	public AuthorityHierarchyId(){
		
	}
	
	public AuthorityHierarchyId(Authority parentAuthority, Authority childAuthority){
		this.parentAuthority = parentAuthority;
		this.childAuthority = childAuthority;
	}
	
	public Authority getParentAuthority() {
		return parentAuthority;
	}
	public void setParentAuthority(Authority parentAuthority) {
		this.parentAuthority = parentAuthority;
	}
	public Authority getChildAuthority() {
		return childAuthority;
	}
	public void setChildAuthority(Authority childAuthority) {
		this.childAuthority = childAuthority;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childAuthority == null) ? 0 : childAuthority.hashCode());
		result = prime * result + ((parentAuthority == null) ? 0 : parentAuthority.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AuthorityHierarchyId))
			return false;
		AuthorityHierarchyId other = (AuthorityHierarchyId) obj;
		if (childAuthority == null) {
			if (other.getChildAuthority() != null)
				return false;
		} else if (!childAuthority.equals(other.getChildAuthority()))
			return false;
		if (parentAuthority == null) {
			if (other.getParentAuthority() != null)
				return false;
		} else if (!parentAuthority.equals(other.getParentAuthority()))
			return false;
		return true;
	}
}
