package com.xiu.jd.bean.ware;

import java.io.Serializable;
import java.sql.Date;

/**
 * 商品价格变化MQ
 * @author liweibiao
 *
 */
public class JdChangedGoodsPrice implements Serializable{

	private static final long serialVersionUID = 5899888101914589415L;
	/**表主键**/
	private long recordid;
	/**商品走秀码**/
	private String xiucode;
	/**商品走秀价格**/
	private String xiuprice;
    /**商品活动价格**/
	private String xiuactivityprice;

	/**商品京东价格**/
	private String jdprice;
	/**数据创建时间**/
	private Date createdate;
	/**京东商品ID**/
	private String wareid;
	
	/**0:未同步到京东,1:同步到京东成功,2:失败,3未同步到京东,但本地商品表已经同步了**/
	private int updatestatus;
	public String getXiucode() {
		return xiucode;
	}
	public void setXiucode(String xiucode) {
		this.xiucode = xiucode;
	}
	public String getXiuprice() {
		return xiuprice;
	}
	public void setXiuprice(String xiuprice) {
		this.xiuprice = xiuprice;
	}
	
	public String getJdprice() {
		return jdprice;
	}
	public void setJdprice(String jdprice) {
		this.jdprice = jdprice;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public int getUpdatestatus() {
		return updatestatus;
	}
	public void setUpdatestatus(int updatestatus) {
		this.updatestatus = updatestatus;
	}
	public String getWareid() {
		return wareid;
	}
	public void setWareid(String wareid) {
		this.wareid = wareid;
	}
	public long getRecordid() {
		return recordid;
	}
	public void setRecordid(long recordid) {
		this.recordid = recordid;
	}

	public String getXiuactivityprice() {
		return xiuactivityprice;
	}
	public void setXiuactivityprice(String xiuactivityprice) {
		this.xiuactivityprice = xiuactivityprice;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "JdChangedGoodsPrice [recordid=" + recordid + ", xiucode="
				+ xiucode + ", xiuprice=" + xiuprice + ", xiuactivityprice="
				+ xiuactivityprice + ", jdprice=" + jdprice + ", createdate="
				+ createdate + ", wareid=" + wareid + ", updatestatus="
				+ updatestatus + "]";
	}
	
	
	
	
}
