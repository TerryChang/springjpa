package com.terry.springjpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SECURED_RESOURCES")
@SequenceGenerator(name="SecuredResourcesSequenceGenerator", sequenceName="SECURED_RESOURCES_SEQUENCE", initialValue=1, allocationSize=1)
@Access(AccessType.FIELD)
public class SecuredResources {

	private Long idx;
	
	@Column(name="RESOURCE_NAME")
	private String resourceName;
	
	@Column(name="RESOURCE_PATTERN")
	private String resourcePattern;
	
	@Column(name="RESOURCE_TYPE")
	private String resourceType;
	
	@Column(name="SORT_ORDER")
	private int sortOrder;
	
	public SecuredResources(){
		
	}
	
	public SecuredResources(Long idx, String resourceName, String resourcePattern, String resourceType, int sortOrder){
		this.idx = idx;
		this.resourceName = resourceName;
		this.resourcePattern = resourcePattern;
		this.resourceType = resourceType;
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

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idx == null) ? 0 : idx.hashCode());
		result = prime * result + ((resourceName == null) ? 0 : resourceName.hashCode());
		result = prime * result + ((resourcePattern == null) ? 0 : resourcePattern.hashCode());
		result = prime * result + ((resourceType == null) ? 0 : resourceType.hashCode());
		result = prime * result + sortOrder;
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
		SecuredResources other = (SecuredResources) obj;
		if (idx == null) {
			if (other.getIdx() != null)
				return false;
		} else if (!idx.equals(other.getIdx()))
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
		if (resourceType == null) {
			if (other.getResourceType() != null)
				return false;
		} else if (!resourceType.equals(other.getResourceType()))
			return false;
		if (sortOrder != other.getSortOrder())
			return false;
		return true;
	}
	
	
}
