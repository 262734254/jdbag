package com.xiu.jd.bean.log;

import java.io.Serializable;

/**
 * 日志实体类
 * @author user
 *
 */
public class JdLog implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3991527717167560896L;
	/****/
	private long logid;
	 /****/
	private String describe;
	 /**日志类型 1:(库存)sku在渠道中心不存在,2：库存,3:价格**/
	private int logtype;
	 /****/
	private String createdate;
	
	private String updatedate;
	 /**商品走秀码**/
	private String xiucode;
	 /**商品sku码**/
	private String xiusn;
	 /****/
	private String wareid;
	/**日志是否已经更新0：未更新；1：已更新**/
	private int status;
	public long getLogid() {
		return logid;
	}
	public void setLogid(long logid) {
		this.logid = logid;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public int getLogtype() {
		return logtype;
	}
	public void setLogtype(int logtype) {
		this.logtype = logtype;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getXiucode() {
		return xiucode;
	}
	public void setXiucode(String xiucode) {
		this.xiucode = xiucode;
	}
	public String getXiusn() {
		return xiusn;
	}
	public void setXiusn(String xiusn) {
		this.xiusn = xiusn;
	}
	public String getWareid() {
		return wareid;
	}
	public void setWareid(String wareid) {
		this.wareid = wareid;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "JdLog [logid=" + logid + ", describe=" + describe
				+ ", logtype=" + logtype + ", updatedate=" + updatedate
				+ ",createdate=" + createdate
				+ ", xiucode=" + xiucode + ", xiusn=" + xiusn + ", wareid="
				+ wareid + ", status=" + status + "]";
	}
	
	
	
}
