package com.xiu.jd.bean.page;

import java.util.List;

/**
 * 用于查询数据
 * @author user
 *
 */
public class QueryData {

	private List<String> columnTitles;
	
	private List<List<Object>> data;
	
	//影响的记录数
	private int count=0;

	public List<String> getColumnTitles() {
		return columnTitles;
	}

	public void setColumnTitles(List<String> columnTitles) {
		this.columnTitles = columnTitles;
	}

	public List<List<Object>> getData() {
		return data;
	}

	public void setData(List<List<Object>> data) {
		this.data = data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
  
	
	
}
