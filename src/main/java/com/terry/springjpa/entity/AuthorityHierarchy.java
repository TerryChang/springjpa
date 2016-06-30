package com.terry.springjpa.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.terry.springjpa.entity.idclass.AuthorityHierarchyId;

@Entity
@Table(name="AUTHORITY_HIERARCHY")
@IdClass(AuthorityHierarchyId.class)
public class AuthorityHierarchy {

	@Id
	@ManyToOne
	@JoinColumn(name="PARENT_AUTHORITY_IDX", nullable=false)
	private Authority parentAuthority;
	
	@Id
	@ManyToOne
	@JoinColumn(name="CHILD_AUTHORITY_IDX", nullable=false)
	private Authority childAuthority;
	
	public AuthorityHierarchy(){
		
	}
	
	public AuthorityHierarchy(Authority parentAuthority, Authority childAuthority){
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
		if (!(obj instanceof AuthorityHierarchy))
			return false;
		AuthorityHierarchy other = (AuthorityHierarchy) obj;
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
