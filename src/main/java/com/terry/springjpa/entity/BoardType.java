package com.terry.springjpa.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import org.joda.time.LocalDateTime;

import com.terry.springjpa.entity.embed.InsertUpdateDT;
import com.terry.springjpa.vo.BoardTypeVO;

@Entity
@Table(name = "BOARDTYPE")
@SequenceGenerator(name = "BoardTypeSequenceGenerator", sequenceName = "BOARDTYPE_SEQUENCE", initialValue = 1, allocationSize = 1)
public class BoardType implements EntityConvert<BoardTypeVO>{

	@Id
	@Column(name = "IDX")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BoardTypeSequenceGenerator")
	private Long idx;

	@Column(name = "NAME", nullable = false)
	private String boardTypeName;

	@Column(name = "URL", nullable = false)
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

	@Override
	public BoardTypeVO convertToVO() {
		// TODO Auto-generated method stub
		BoardTypeVO boardTypeVO = new BoardTypeVO();
		boardTypeVO.setIdx(this.idx);
		boardTypeVO.setBoardTypeName(this.boardTypeName);
		boardTypeVO.setUrl(this.url);
		
		if(this.insertUpdateDT != null){
			boardTypeVO.setInsertDateTime(this.insertUpdateDT.getInsertDateTime());
			boardTypeVO.setUpdateDateTime(this.insertUpdateDT.getUpdateDateTime());
		}
		
		return boardTypeVO;
	}
	
	public static List<BoardTypeVO> convertEntityListToVOList(List<BoardType> entityList){
		List<BoardTypeVO> result = new ArrayList<BoardTypeVO>();
		Iterator<BoardType> iterator = entityList.iterator();
		while(iterator.hasNext()){
			BoardType boardType = iterator.next();
			BoardTypeVO boardTypeVO = boardType.convertToVO();
			result.add(boardTypeVO);
			iterator.remove();
		}
		entityList = null;
		return result;
	}

}
