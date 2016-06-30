package com.terry.springjpa.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.terry.springjpa.entity.idclass.SecuredResourcesAuthorityId;

@Entity
@Table(name="SECURED_RESOURCES_AUTHORITY")
@IdClass(SecuredResourcesAuthorityId.class)
public class SecuredResourcesAuthority {
	
	@Id
	@ManyToOne
	@JoinColumn(name="RESOURCES_IDX", nullable=false)
	private SecuredResources securedResources;
	
	@Id
	@ManyToOne
	@JoinColumn(name="AUTHORITY_IDX", nullable=false)
	private Authority authority;

	public SecuredResourcesAuthority(){
		
	}
	
	public SecuredResourcesAuthority(SecuredResources securedResources, Authority authority){
		this.securedResources = securedResources;
		this.authority = authority;
	}

	public SecuredResources getSecuredResources() {
		return securedResources;
	}

	public void setSecuredResources(SecuredResources securedResources) {
		this.securedResources = securedResources;
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
		result = prime * result + ((securedResources == null) ? 0 : securedResources.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SecuredResourcesAuthority))
			return false;
		SecuredResourcesAuthority other = (SecuredResourcesAuthority) obj;
		if (authority == null) {
			if (other.getAuthority() != null)
				return false;
		} else if (!authority.equals(other.getAuthority()))
			return false;
		if (securedResources == null) {
			if (other.getSecuredResources() != null)
				return false;
		} else if (!securedResources.equals(other.getSecuredResources()))
			return false;
		return true;
	}
	
	
}
