package com.yihong.searchlyLog.model;

import java.util.List;

public class EsResult<T> {
	private int totalNum;
	private List<T> resultList;
	
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	
}
