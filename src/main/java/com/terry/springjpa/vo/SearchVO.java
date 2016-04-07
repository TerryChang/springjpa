package com.terry.springjpa.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SearchVO {

	private String searchWrd;			// 검색어
	private String searchWrd1;			// 검색어1
	private String searchWrd2;			// 검색어2
	private String searchWrd3;			// 검색어3
	private String searchWrd4;			// 검색어4
	private String searchWrd5;			// 검색어5
	
	private String searchCnd;			// 검색종류
	private String searchCnd1;			// 검색종류1
	private String searchCnd2;			// 검색종류2
	private String searchCnd3;			// 검색종류3
	private String searchCnd4;			// 검색종류4
	private String searchCnd5;			// 검색종류5
	
	private int pageNo = 1;			// 페이지 번호
	private int pageSize = 10;			// 페이지 크기
	private int startnum = 1;			// 페이징시 조회해야 할 시작 index 값
	private int endnum = 11;			// 페이징시 조회해야 할 종료 index 값
	
	
	public String getSearchWrd() {
		return searchWrd;
	}

	public void setSearchWrd(String searchWrd) {
		this.searchWrd = searchWrd;
	}

	public String getSearchWrd1() {
		return searchWrd1;
	}
	
	public void setSearchWrd1(String searchWrd1) {
		this.searchWrd1 = searchWrd1;
	}
	
	public String getSearchWrd2() {
		return searchWrd2;
	}
	
	public void setSearchWrd2(String searchWrd2) {
		this.searchWrd2 = searchWrd2;
	}
	
	public String getSearchWrd3() {
		return searchWrd3;
	}
	
	public void setSearchWrd3(String searchWrd3) {
		this.searchWrd3 = searchWrd3;
	}
	
	public String getSearchWrd4() {
		return searchWrd4;
	}
	
	public void setSearchWrd4(String searchWrd4) {
		this.searchWrd4 = searchWrd4;
	}
	
	public String getSearchWrd5() {
		return searchWrd5;
	}
	
	public void setSearchWrd5(String searchWrd5) {
		this.searchWrd5 = searchWrd5;
	}
	
	public String getSearchCnd1() {
		return searchCnd1;
	}
	
	public String getSearchCnd() {
		return searchCnd;
	}

	public void setSearchCnd(String searchCnd) {
		this.searchCnd = searchCnd;
	}

	public void setSearchCnd1(String searchCnd1) {
		this.searchCnd1 = searchCnd1;
	}
	
	public String getSearchCnd2() {
		return searchCnd2;
	}
	
	public void setSearchCnd2(String searchCnd2) {
		this.searchCnd2 = searchCnd2;
	}
	
	public String getSearchCnd3() {
		return searchCnd3;
	}
	
	public void setSearchCnd3(String searchCnd3) {
		this.searchCnd3 = searchCnd3;
	}
	
	public String getSearchCnd4() {
		return searchCnd4;
	}
	
	public void setSearchCnd4(String searchCnd4) {
		this.searchCnd4 = searchCnd4;
	}
	
	public String getSearchCnd5() {
		return searchCnd5;
	}
	
	public void setSearchCnd5(String searchCnd5) {
		this.searchCnd5 = searchCnd5;
	}

	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		
		// startnum과 endnum 계산
		startnum = (pageNo - 1) * pageSize + 1;
		endnum = pageNo * pageSize + 1;
		
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		
		// startnum과 endnum 계산
		startnum = (pageNo - 1) * pageSize + 1;
		endnum = pageNo * pageSize + 1;
	}

	public int getStartnum() {
		return startnum;
	}

	public void setStartnum(int startnum) {
		this.startnum = startnum;
	}

	public int getEndnum() {
		return endnum;
	}

	public void setEndnum(int endnum) {
		this.endnum = endnum;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null) return false;
		if(!(obj instanceof SearchVO)) return false;
		
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	
}
