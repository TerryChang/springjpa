package com.terry.springjpa.entity;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SECURED_RESOURCES")
@SequenceGenerator(name="SecuredResourcesSequenceGenerator", sequenceName="SECURED_RESOURCES_SEQUENCE", initialValue=1, allocationSize=1)
@Access(AccessType.FIELD)
public class SecuredResources {

	private Long idx;
	
	@Column(name="RESOURCE_NAME", nullable=false)
	private String resourceName;
	
	@Column(name="RESOURCE_PATTERN", nullable = false)
	private String resourcePattern;
	
	@Column(name="RESOURCE_MATCH_TYPE", nullable=false)
	private String resourceMatchType;
	
	@Column(name="SORT_ORDER", nullable=false)
	private int sortOrder;
	
	@OneToMany(mappedBy="securedResources")
	private List<SecuredResourcesAuthority> securedResourcesAuthorityList;
	
	public SecuredResources(){
		
	}
	
	public SecuredResources(Long idx, String resourceName, String resourcePattern, String resourceMatchType, int sortOrder){
		this.idx = idx;
		this.resourceName = resourceName;
		this.resourcePattern = resourcePattern;
		this.resourceMatchType = resourceMatchType;
		this.sortOrder = sortOrder;
	}

	@Id
	@Column(name="IDX")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SecuredResourcesSequenceGenerator")
	@Access(AccessType.PROPERTY)
	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourcePattern() {
		return resourcePattern;
	}

	public void setResourcePattern(String resourcePattern) {
		this.resourcePattern = resourcePattern;
	}

	public String getResourceMatchType() {
		return resourceMatchType;
	}

	public void setResourceMatchType(String resourceMatchType) {
		this.resourceMatchType = resourceMatchType;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<SecuredResourcesAuthority> getSecuredResourcesAuthorityList() {
		return securedResourcesAuthorityList;
	}

	public void setSecuredResourcesAuthorityList(List<SecuredResourcesAuthority> securedResourcesAuthorityList) {
		this.securedResourcesAuthorityList = securedResourcesAuthorityList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idx == null) ? 0 : idx.hashCode());
		result = prime * result + ((resourceMatchType == null) ? 0 : resourceMatchType.hashCode());
		result = prime * result + ((resourceName == null) ? 0 : resourceName.hashCode());
		result = prime * result + ((resourcePattern == null) ? 0 : resourcePattern.hashCode());
		result = prime * result
				+ ((securedResourcesAuthorityList == null) ? 0 : securedResourcesAuthorityList.hashCode());
		result = prime * result + sortOrder;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SecuredResources))
			return false;
		SecuredResources other = (SecuredResources) obj;
		if (idx == null) {
			if (other.getIdx() != null)
				return false;
		} else if (!idx.equals(other.getIdx()))
			return false;
		if (resourceMatchType == null) {
			if (other.getResourceMatchType() != null)
				return false;
		} else if (!resourceMatchType.equals(other.getResourceMatchType()))
			return false;
		if (resourceName == null) {
			if (other.getResourceName() != null)
				return false;
		} else if (!resourceName.equals(other.getResourceName()))
			return false;
		if (resourcePattern == null) {
			if (other.getResourcePattern() != null)
				return false;
		} else if (!resourcePattern.equals(other.getResourcePattern()))
			return false;
		if (securedResourcesAuthorityList == null) {
			if (other.getSecuredResourcesAuthorityList() != null)
				return false;
		} else if (!securedResourcesAuthorityList.equals(other.getSecuredResourcesAuthorityList()))
			return false;
		if (sortOrder != other.getSortOrder())
			return false;
		return true;
	}

	
	
}
