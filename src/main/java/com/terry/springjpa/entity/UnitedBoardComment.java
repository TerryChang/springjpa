package com.terry.springjpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name="UNITEDBOARD_COMMENT")
@SequenceGenerator(name="UnitedBoardCommentSequenceGenerator", sequenceName="UNITEDBOARD_COMMENT_SEQUENCE", initialValue=1, allocationSize=1)
public class UnitedBoardComment {

	@Id
	@Column(name="IDX")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="UnitedBoardCommentSequenceGenerator")
	private Long idx;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="UNITEDBOARD_IDX", referencedColumnName="IDX", nullable=false)
	private UnitedBoard unitedBoard;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MEMBER_IDX", referencedColumnName="IDX", nullable=false)
	private Member member;
	
	@Column(name="COMMENT")
	@Lob
	private String comment;
	
	// 댓글은 수정일시는 의미가 없을것 같아서 등록일시만 넣게끔 했다
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")    // Joda time 에서 제공하는 DateTime 클래스를 사용할 경우
	@Column(name="INSERTDT", insertable = true, updatable = false)
    private LocalDateTime insertDateTime;
	
	public UnitedBoardComment(){
		
	}

	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public UnitedBoard getUnitedBoard() {
		return unitedBoard;
	}

	public void setUnitedBoard(UnitedBoard unitedBoard) {
		this.unitedBoard = unitedBoard;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(LocalDateTime insertDateTime) {
		this.insertDateTime = insertDateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((idx == null) ? 0 : idx.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result + ((unitedBoard == null) ? 0 : unitedBoard.hashCode());
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
		UnitedBoardComment other = (UnitedBoardComment) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (idx == null) {
			if (other.idx != null)
				return false;
		} else if (!idx.equals(other.idx))
			return false;
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		if (unitedBoard == null) {
			if (other.unitedBoard != null)
				return false;
		} else if (!unitedBoard.equals(other.unitedBoard))
			return false;
		return true;
	}
}
