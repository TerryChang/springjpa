package com.terry.springjpa.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;

import com.terry.springjpa.entity.embed.InsertUpdateDT;

@Entity
@Table(name = "BOARDTYPE")
@SequenceGenerator(name = "BoardTypeSequenceGenerator", sequenceName = "BOARDTYPE_SEQUENCE", initialValue = 1, allocationSize = 1)
public class BoardType {

	@Id
	@Column(name = "IDX")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BoardTypeSequenceGenerator")
	private Long idx;

	@Column(name = "NAME", nullable = false)
	@NotBlank
	@Size(min=3, max=10)
	private String boardTypeName;

	@Column(name = "URL", nullable = false)
	@NotBlank
	@Size(min=10, max=100)
	private String url;

	@Embedded
	private InsertUpdateDT insertUpdateDT;

	public BoardType() {

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InsertUpdateDT getInsertUpdateDT() {
		return insertUpdateDT;
	}

	public void setInsertUpdateDT(InsertUpdateDT insertUpdateDT) {
		this.insertUpdateDT = insertUpdateDT;
	}

	@PrePersist
	public void onCreate() {
		insertUpdateDT = new InsertUpdateDT();
		insertUpdateDT.setInsertDateTime(LocalDateTime.now());
	}

	@PreUpdate
	public void onUpdate() {
		insertUpdateDT.setUpdateDateTime(LocalDateTime.now());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardTypeName == null) ? 0 : boardTypeName.hashCode());
		result = prime * result + ((idx == null) ? 0 : idx.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BoardType))
			return false;
		BoardType other = (BoardType) obj;
		if (boardTypeName == null) {
			if (other.getBoardTypeName() != null)
				return false;
		} else if (!boardTypeName.equals(other.getBoardTypeName()))
			return false;
		if (idx == null) {
			if (other.getIdx() != null)
				return false;
		} else if (!idx.equals(other.getIdx()))
			return false;
		if (url == null) {
			if (other.getUrl() != null)
				return false;
		} else if (!url.equals(other.getUrl()))
			return false;
		return true;
	}

	

	

}
