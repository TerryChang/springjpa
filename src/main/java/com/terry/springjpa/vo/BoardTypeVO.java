package com.terry.springjpa.vo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.format.annotation.DateTimeFormat;

import com.terry.springjpa.entity.BoardType;

public class BoardTypeVO implements VOConvert<BoardType, Long>{
	private Long idx;
	
	@NotBlank
	@Size(min=3, max=10)
	private String boardTypeName;
	
	@NotBlank
	@Size(min=10, max=100)
	private String url;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime insertDateTime;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDateTime;
	
	public BoardTypeVO(){
		
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
	public BoardType convertToEntity(CrudRepository<BoardType, Long> repository) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		BoardType boardType = null;
		if(this.getIdx() == null){
			boardType = new BoardType();
		}else{
			boardType = repository.findOne(this.getIdx());
		}
		
		boardType.setBoardTypeName(this.boardTypeName);
		boardType.setUrl(this.url);
		
		return boardType;
	}

	
	
	

}
