package com.xiu.jd.bean.ware;

import java.io.Serializable;

public class XiuProduct implements Serializable{
   /**
	 * 
	 */
	private static final long serialVersionUID = 4053847353044592085L;
	//走秀商品ID
	private String xiuProductId;
     //商品走秀码
	private String xiuCode;
	//商品所属的品牌
	private String brandCode;
	 //基本分类编码是四级
	private String bcatCode;
	
	public XiuProduct(){}
	
	
	public XiuProduct(String xiuCode, String brandCode, String bcatCode) {
		super();
		this.xiuCode = xiuCode;
		this.brandCode = brandCode;
		this.bcatCode = bcatCode;
	}

	

	public String getXiuProductId() {
		return xiuProductId;
	}


	public void setXiuProductId(String xiuProductId) {
		this.xiuProductId = xiuProductId;
	}


	public String getXiuCode() {
		return xiuCode;
	}
	public void setXiuCode(String xiuCode) {
		this.xiuCode = xiuCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getBcatCode() {
		return bcatCode;
	}
	public void setBcatCode(String bcatCode) {
		this.bcatCode = bcatCode;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((xiuProductId == null) ? 0 : xiuProductId.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XiuProduct other = (XiuProduct) obj;
		if (xiuProductId == null) {
			if (other.xiuProductId != null)
				return false;
		} else if (!xiuProductId.equals(other.xiuProductId))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "XiuProduct [xiuCode=" + xiuCode + ", brandCode=" + brandCode
				+ ", bcatCode=" + bcatCode + ", getXiuCode()=" + getXiuCode()
				+ ", getBrandCode()=" + getBrandCode() + ", getBcatCode()="
				+ getBcatCode() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	
}
