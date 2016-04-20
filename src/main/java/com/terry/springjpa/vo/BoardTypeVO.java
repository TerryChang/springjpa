package com.terry.springjpa.vo;

import org.joda.time.LocalDateTime;
import org.springframework.data.repository.CrudRepository;

import com.terry.springjpa.entity.BoardType;

public class BoardTypeVO implements VOConvert<BoardType, Long>{
	private Long idx;
	private String boardTypeName;
	private String url;
	private LocalDateTime insertDateTime;
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
			boardType.setBoardTypeName(this.boardTypeName);
			boardType.setUrl(this.url);
		}
		return boardType;
	}

	
	
	

}
