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
@Table(name="AUTHORITY")
@SequenceGenerator(name="AuthoritySequenceGenerator", sequenceName="AUTHORITY_SEQUENCE", initialValue=1, allocationSize=1)
@Access(AccessType.FIELD)
public class Authority {

	private Long idx;
	
	@Column(name="AUTHORITY_NAME")
	private String authorityName;
	
	@Column(name="AUTHORITY_DESC")
	private String authorityDesc;
	
	@OneToMany(mappedBy="authority")
	private List<MemberAuthority> authorityMembers;
	
	@OneToMany(mappedBy="authority")
	private List<GroupsAuthority> authorityGroups;
	
	@OneToMany(mappedBy="authority")
	private List<SecuredResourcesAuthority> securedResourcesAuthorityList;
	
	public Authority(){
		
	}
	
	public Authority(Long idx, String authorityName, String authorityDesc){
		this.idx = idx;
		this.authorityName = authorityName;
		this.authorityDesc = authorityDesc;
	}
	
	@Id
	@Column(name="IDX")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AuthoritySequenceGenerator")
	@Access(AccessType.PROPERTY)
	public Long getIdx() {
		return idx;
	}
	
	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getAuthorityDesc() {
		return authorityDesc;
	}

	public void setAuthorityDesc(String authorityDesc) {
		this.authorityDesc = authorityDesc;
	}

	public List<MemberAuthority> getAuthorityMembers() {
		return authorityMembers;
	}

	public void setAuthorityMembers(List<MemberAuthority> authorityMembers) {
		this.authorityMembers = authorityMembers;
	}

	public List<GroupsAuthority> getAuthorityGroups() {
		return authorityGroups;
	}

	public void setAuthorityGroups(List<GroupsAuthority> authorityGroups) {
		this.authorityGroups = authorityGroups;
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
		result = prime * result + ((authorityDesc == null) ? 0 : authorityDesc.hashCode());
		result = prime * result + ((authorityGroups == null) ? 0 : authorityGroups.hashCode());
		result = prime * result + ((authorityMembers == null) ? 0 : authorityMembers.hashCode());
		result = prime * result + ((authorityName == null) ? 0 : authorityName.hashCode());
		result = prime * result + ((idx == null) ? 0 : idx.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Authority))
			return false;
		Authority other = (Authority) obj;
		if (authorityDesc == null) {
			if (other.getAuthorityDesc() != null)
				return false;
		} else if (!authorityDesc.equals(other.getAuthorityDesc()))
			return false;
		if (authorityGroups == null) {
			if (other.getAuthorityGroups() != null)
				return false;
		} else if (!authorityGroups.equals(other.getAuthorityGroups()))
			return false;
		if (authorityMembers == null) {
			if (other.getAuthorityMembers() != null)
				return false;
		} else if (!authorityMembers.equals(other.getAuthorityMembers()))
			return false;
		if (authorityName == null) {
			if (other.getAuthorityName() != null)
				return false;
		} else if (!authorityName.equals(other.getAuthorityName()))
			return false;
		if (idx == null) {
			if (other.getIdx() != null)
				return false;
		} else if (!idx.equals(other.getIdx()))
			return false;
		return true;
	}

	
	
}
