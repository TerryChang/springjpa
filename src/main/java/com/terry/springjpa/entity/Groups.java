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
		result = prime * result + ((idx == null) ? 0 : idx.hashCode());
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
		Groups other = (Groups) obj;
		if (groupName == null) {
			if (other.getGroupName() != null)
				return false;
		} else if (!groupName.equals(other.getGroupName()))
			return false;
		if (idx == null) {
			if (other.getIdx() != null)
				return false;
		} else if (!idx.equals(other.getIdx()))
			return false;
		return true;
	}
	
	
	
}
