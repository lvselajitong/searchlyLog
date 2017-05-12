package com.yihong.searchlyLog.model;

import io.searchbox.core.search.sort.Sort;

public class EsQuery {
	private int from;
	private int size;
	private Sort sort;
	private String queryString;
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	

}
