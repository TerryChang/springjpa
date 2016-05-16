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
@Table(name="GROUPS")
@SequenceGenerator(name="GroupsSequenceGenerator", sequenceName="GROUPS_SEQUENCE", initialValue=1, allocationSize=1)
@Access(AccessType.FIELD)
public class Groups {
	
	private Long idx;
	
	@Column(name="GROUP_NAME")
	private String groupName;
	
	@OneToMany(mappedBy="groups")
	private List<MemberGroups> memberGroups;
	
	@OneToMany(mappedBy="groups")
	private List<GroupsAuthority> groupsAuthority;
	
	public Groups(){
		
	}
	
	public Groups(Long idx, String groupName){
		this.idx = idx;
		this.groupName = groupName;
	}

	@Id
	@Column(name="IDX")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GroupsSequenceGenerator")
	@Access(AccessType.PROPERTY)
	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<MemberGroups> getMemberGroups() {
		return memberGroups;
	}

	public void setMemberGroups(List<MemberGroups> memberGroups) {
		this.memberGroups = memberGroups;
	}

	public List<GroupsAuthority> getGroupsAuthority() {
		return groupsAuthority;
	}

	public void setGroupsAuthority(List<GroupsAuthority> groupsAuthority) {
		this.groupsAuthority = groupsAuthority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + ((groupsAuthority == null) ? 0 : groupsAuthority.hashCode());
		result = prime * result + ((idx == null) ? 0 : idx.hashCode());
		result = prime * result + ((memberGroups == null) ? 0 : memberGroups.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Groups))
			return false;
		Groups other = (Groups) obj;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (groupsAuthority == null) {
			if (other.groupsAuthority != null)
				return false;
		} else if (!groupsAuthority.equals(other.groupsAuthority))
			return false;
		if (idx == null) {
			if (other.idx != null)
				return false;
		} else if (!idx.equals(other.idx))
			return false;
		if (memberGroups == null) {
			if (other.memberGroups != null)
				return false;
		} else if (!memberGroups.equals(other.memberGroups))
			return false;
		return true;
	}
}
