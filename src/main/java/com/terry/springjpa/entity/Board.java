package com.terry.springjpa.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Board {

	@Column(name="TITLE")
	private String title;
	
	private String contents;
	
	@Column(name="VIEWCNT")
	private int viewCnt;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @MappedSuperclass 어노테이션을 이용해서 이 클래스를 상속받는 클래스에 매핑정보만 제공할 경우
	 * CLOB 타입의 컬럼을 매핑할땐 JPA가 엔티티 데이터에 접근하는 방식을 설정하는 @Access 어노테이션을 프로퍼티 접근만 사용할 수 있는것 같다
	 * 필드 접근이 되지를 않아서 이 부분을 프로퍼티 접근으로 설정하는 것으로 바꾸었다.
	 * @return
	 */
	@Column(name="CONTENTS")
	@Lob
	@Access(AccessType.PROPERTY)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
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
		if (!(obj instanceof Board))
			return false;
		Board other = (Board) obj;
		if (contents == null) {
			if (other.getContents() != null)
				return false;
		} else if (!contents.equals(other.getContents()))
			return false;
		if (title == null) {
			if (other.getTitle() != null)
				return false;
		} else if (!title.equals(other.getTitle()))
			return false;
		if (viewCnt != other.getViewCnt())
			return false;
		return true;
	}
}
