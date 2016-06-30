package com.terry.springjpa.entity.idclass;

import java.io.Serializable;

import com.terry.springjpa.entity.Authority;
import com.terry.springjpa.entity.SecuredResources;

public class SecuredResourcesAuthorityId implements Serializable {

	private static final long serialVersionUID = -1953329543415671466L;
	
	private SecuredResources securedResources;
	private Authority authority;
	
	public SecuredResourcesAuthorityId(){
		
	}
	
	public SecuredResourcesAuthorityId(SecuredResources securedResources, Authority authority){
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
		if (getClass() != obj.getClass())
			return false;
		SecuredResourcesAuthorityId other = (SecuredResourcesAuthorityId) obj;
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
