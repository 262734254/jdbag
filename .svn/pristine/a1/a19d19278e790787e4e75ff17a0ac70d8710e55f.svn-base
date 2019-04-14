package com.xiu.jd.bean.page;

import java.util.List;


public class PageView<T> {
	/** 分页数据 **/
	private List<T> records;
	/** 页码开始索引和结束索引 **/
	private PageIndex pageindex;
	/** 总页数 **/
	private long totalpage = 1;
	/** 每页显示记录数 **/
	private int maxresult = 24;
	/** 当前页 **/
	private int currentpage =1;
	/** 总记录数 **/
	private long totalrecord=0;
	/** 页码数量 **/
	private int pagecode = 1;
	/** 要获取记录的开始索引 **/
	public int getFirstResult() {
		
	 return (this.currentpage-1)*this.maxresult+1;

	}
	public int getPagecode() {
		return pagecode;
	}

	public void setPagecode(int pagecode) {
		this.pagecode = pagecode;
	}
   /**
    * 
    * @param maxresult   每页显示的记录数量
    * @param currentpage 当前页
    */
	public PageView(int maxresult, int currentpage) {
		this.maxresult = maxresult;
		if(currentpage<=0){
			currentpage=1;
		}
		this.currentpage = currentpage;
	}
	
	public void setQueryResult(QueryResult<T> qr){
		setTotalrecord(qr.getTotalrecord());
		setRecords(qr.getResultlist());
	}
	
	public long getTotalrecord() {
		return totalrecord;
	}
	public void setTotalrecord(long totalrecord ) {
		this.totalrecord = totalrecord ;
		setTotalpage(this.totalrecord%this.maxresult==0? this.totalrecord/this.maxresult : this.totalrecord/this.maxresult+1);
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	public PageIndex getPageindex() {
		return pageindex;
	}
	public long getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(long totalpage) {
		this.totalpage = totalpage;
		this.pageindex = PageIndex.getPageIndex(pagecode, currentpage, totalpage);
		if(this.currentpage>totalpage){
			this.currentpage=this.currentpage;
		}
	}
	public int getMaxresult() {
		//return maxresult;
		return this.currentpage*this.maxresult;
	}
	public int getCurrentpage() {
		return currentpage;
	}
}