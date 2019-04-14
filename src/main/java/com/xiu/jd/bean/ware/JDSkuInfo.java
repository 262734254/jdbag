package com.xiu.jd.bean.ware;

import java.io.Serializable;

public class JDSkuInfo implements Serializable {

	private static final long serialVersionUID = -3491019014891383048L;
	/**京东分类ID**/
	private String cid;
	/**京东商品ID**/
	private String jdWareId;
	/**走秀码**/
	private String xiuCode;
	/**商品sku码**/
	private String skuSn;
	/**图片上传状态**/
	private String status;
	/**sku创建时间**/
	private String createDate;
	
	private String mainImagePath;
	/**京东商品sku编码**/
	private String jdSkuId;
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getJdWareId() {
		return jdWareId;
	}
	public void setJdWareId(String jdWareId) {
		this.jdWareId = jdWareId;
	}
	public String getXiuCode() {
		return xiuCode;
	}
	public void setXiuCode(String xiuCode) {
		this.xiuCode = xiuCode;
	}
	public String getSkuSn() {
		return skuSn;
	}
	public void setSkuSn(String skuSn) {
		this.skuSn = skuSn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getMainImagePath() {
		return mainImagePath;
	}
	public void setMainImagePath(String mainImagePath) {
		this.mainImagePath = mainImagePath;
	}
	public String getJdSkuId() {
		return jdSkuId;
	}
	public void setJdSkuId(String jdSkuId) {
		this.jdSkuId = jdSkuId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
