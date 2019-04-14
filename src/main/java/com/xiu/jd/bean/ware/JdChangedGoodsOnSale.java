package com.xiu.jd.bean.ware;

import java.io.Serializable;

/**
 * 商品上下架实体类
 * @author liweibiao
 *   table JD_CHANGED_GOODS_ONLINE
	 *    RECORDID     NUMBER(15) not null,
		  XIUCODE      VARCHAR2(10),
		  UPDATESTATUS NUMBER(2) default 0,
		  ONSALE       NUMBER(2),
		  FAILDESC     VARCHAR2(60),
		  CREATETIME   DATE
 *
 */
public class JdChangedGoodsOnSale implements Serializable {
	

	/**
	 * recordid
	 * jdWareId
	 * xiuCode
	 * updateStatus
	 * onSale
	 */
	private static final long serialVersionUID = 1L;
	/**记录ID**/
	private long recordid;
	//京东商品ID
	private String jdWareId;
	//商品走秀码
	private String xiuCode;
	//默认为0为处理,1:成功,2:失败,3:格式化商品上下架状态异常,4:黑名单商品,5:京东商品不存在
	private Integer updateStatus;
	//0下架，1上架
	private Integer onSale;
	
	private String failDesc;

	public long getRecordid() {
		return recordid;
	}

	public void setRecordid(long recordid) {
		this.recordid = recordid;
	}

	public String getXiuCode() {
		return xiuCode;
	}

	public void setXiuCode(String xiuCode) {
		this.xiuCode = xiuCode;
	}

	public Integer getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(Integer updateStatus) {
		this.updateStatus = updateStatus;
	}

	public Integer getOnSale() {
		return onSale;
	}

	public void setOnSale(Integer onSale) {
		this.onSale = onSale;
	}

	public String getFailDesc() {
		return failDesc;
	}

	public void setFailDesc(String failDesc) {
		this.failDesc = failDesc;
	}

	public String getJdWareId() {
		return jdWareId;
	}

	public void setJdWareId(String jdWareId) {
		this.jdWareId = jdWareId;
	}

	@Override
	public String toString() {
		return "JdChangedGoodsOnSale [recordid=" + recordid + ", jdWareId="
				+ jdWareId + ", xiuCode=" + xiuCode + ", updateStatus="
				+ updateStatus + ", onSale=" + onSale + ", failDesc="
				+ failDesc + "]";
	}
	
	
	
	
}
