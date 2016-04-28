package com.terry.springjpa.entity.embed;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Embeddable
public class InsertUpdateDT {

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")    // Joda time 에서 제공하는 DateTime 클래스를 사용할 경우
	@Column(name="INSERTDT", insertable = true, updatable = false)
    private LocalDateTime insertDateTime;
	
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")    // Joda time 에서 제공하는 DateTime 클래스를 사용할 경우
	@Column(name="UPDATEDT", insertable = false, updatable = true)
	private LocalDateTime updateDateTime;
	
	public InsertUpdateDT(){
		
	}
	
	public InsertUpdateDT(LocalDateTime insertDateTime, LocalDateTime updateDateTime){
		this.insertDateTime = insertDateTime;
		this.updateDateTime = updateDateTime;
	}

	public LocalDateTime getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(LocalDateTime insertDateTime) {
		this.insertDateTime = insertDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((insertDateTime == null) ? 0 : insertDateTime.hashCode());
		result = prime * result + ((updateDateTime == null) ? 0 : updateDateTime.hashCode());
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
		InsertUpdateDT other = (InsertUpdateDT) obj;
		if (insertDateTime == null) {
			if (other.insertDateTime != null)
				return false;
		} else if (!insertDateTime.equals(other.insertDateTime))
			return false;
		if (updateDateTime == null) {
			if (other.updateDateTime != null)
				return false;
		} else if (!updateDateTime.equals(other.updateDateTime))
			return false;
		return true;
	}
	
	
}
