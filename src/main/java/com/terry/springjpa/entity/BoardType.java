package com.terry.springjpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="BOARDTYPE")
@SequenceGenerator(name="BoardTypeSequenceGenerator", sequenceName="BOARDTYPE_SEQUENCE", initialValue=1, allocationSize=1)
public class BoardType {

	@Id
	@Column(name="IDX")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BoardTypeSequenceGenerator")
	private Long idx;
	
	@Column(name="NAME")
	private String boardTypeName;
	
	public BoardType(){
		
	}

	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public String getBoardTypeName() {
		return boardTypeName;
	}

	public void setBoardTypeName(String boardTypeName) {
		this.boardTypeName = boardTypeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardTypeName == null) ? 0 : boardTypeName.hashCode());
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
		BoardType other = (BoardType) obj;
		if (boardTypeName == null) {
			if (other.boardTypeName != null)
				return false;
		} else if (!boardTypeName.equals(other.boardTypeName))
			return false;
		if (idx == null) {
			if (other.idx != null)
				return false;
		} else if (!idx.equals(other.idx))
			return false;
		return true;
	}

	
}
