package com.terry.springjpa.entity;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Board {

	@Column(name="TITLE")
	private String title;
	
	@Column(name="CONTENTS")
	@Lob
	private String contents;
	
	@Column(name="VIEWCNT")
	private int viewCnt;

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
}
