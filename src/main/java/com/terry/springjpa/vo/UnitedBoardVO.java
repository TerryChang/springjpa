package com.terry.springjpa.vo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class UnitedBoardVO {

	private Long idx;
	
	private Long boardTypeIdx;
	private String boardTypeName;
	private String boardTypeUrl;
	
	private Long memberIdx;
	private String memberLoginId;

	@NotBlank
	@Size(min=3, max=255)
	private String title;
	
	@NotBlank
	@Size(min=10)
	private String contents;
	
	private int viewCnt;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertDateTime;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDateTime;
	
	public Long getIdx() {
		return idx;
	}
	
	public void setIdx(Long idx) {
		this.idx = idx;
	}
	
	public Long getBoardTypeIdx() {
		return boardTypeIdx;
	}
	
	public void setBoardTypeIdx(Long boardTypeIdx) {
		this.boardTypeIdx = boardTypeIdx;
	}
	
	public String getBoardTypeName() {
		return boardTypeName;
	}
	
	public void setBoardTypeName(String boardTypeName) {
		this.boardTypeName = boardTypeName;
	}
	
	public String getBoardTypeUrl() {
		return boardTypeUrl;
	}
	
	public void setBoardTypeUrl(String boardTypeUrl) {
		this.boardTypeUrl = boardTypeUrl;
	}
	
	public Long getMemberIdx() {
		return memberIdx;
	}
	
	public void setMemberIdx(Long memberIdx) {
		this.memberIdx = memberIdx;
	}
	
	public String getMemberLoginId() {
		return memberLoginId;
	}
	
	public void setMemberLoginId(String memberLoginId) {
		this.memberLoginId = memberLoginId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public int getViewCnt() {
		return viewCnt;
	}
	
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
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
		result = prime * result + ((boardTypeIdx == null) ? 0 : boardTypeIdx.hashCode());
		result = prime * result + ((boardTypeName == null) ? 0 : boardTypeName.hashCode());
		result = prime * result + ((boardTypeUrl == null) ? 0 : boardTypeUrl.hashCode());
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
		result = prime * result + ((idx == null) ? 0 : idx.hashCode());
		result = prime * result + ((memberIdx == null) ? 0 : memberIdx.hashCode());
		result = prime * result + ((memberLoginId == null) ? 0 : memberLoginId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + viewCnt;
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
		UnitedBoardVO other = (UnitedBoardVO) obj;
		if (boardTypeIdx == null) {
			if (other.boardTypeIdx != null)
				return false;
		} else if (!boardTypeIdx.equals(other.boardTypeIdx))
			return false;
		if (boardTypeName == null) {
			if (other.boardTypeName != null)
				return false;
		} else if (!boardTypeName.equals(other.boardTypeName))
			return false;
		if (boardTypeUrl == null) {
			if (other.boardTypeUrl != null)
				return false;
		} else if (!boardTypeUrl.equals(other.boardTypeUrl))
			return false;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (idx == null) {
			if (other.idx != null)
				return false;
		} else if (!idx.equals(other.idx))
			return false;
		if (memberIdx == null) {
			if (other.memberIdx != null)
				return false;
		} else if (!memberIdx.equals(other.memberIdx))
			return false;
		if (memberLoginId == null) {
			if (other.memberLoginId != null)
				return false;
		} else if (!memberLoginId.equals(other.memberLoginId))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (viewCnt != other.viewCnt)
			return false;
		return true;
	}
}
