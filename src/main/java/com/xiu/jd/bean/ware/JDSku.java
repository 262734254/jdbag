package com.xiu.jd.bean.ware;

import java.io.Serializable;

/**
 * 京东sku实体
 * @author user
 *
 */
public class JDSku implements Serializable {

	
	private static final long serialVersionUID = -4782347165631795629L;
	
	
	/**sku ID**/
	private String skusn;
	
	/**sku商品的走秀码***/
	private String xiucode;
	
	/**库存量**/
	private String stocknum;
	
	private String colorname;
	
	private String sizevalue;
	
	private String attributes;
	//是否为主图:默认为0非主图,1为主图 
	 private int ismain;
	 //sku图片路径,由于京东要求sku图片必须是800*800的,而走秀最大的图片是600*600
	 private String skuImagePath;
	 
	 private String wareId;
	 /**sku图片是否已经上传到京东：**/
	 private String status;
	
	/**导入Excel的批次号**/
	private long num;
	
	
	/**sku对应的商品**/
	
	private String jdWareJd;
	/**京东商品sku编码**/
	private String jdSkuId;
	
	public JDSku(){}
	
	

	public JDSku(String stocknum, String colorname, String sizevalue,
			String attributes) {
		super();
		this.stocknum = stocknum;
		this.colorname = colorname;
		this.sizevalue = sizevalue;
		this.attributes = attributes;
	}



	public String getSkusn() {
		return skusn;
	}

	public void setSkusn(String skusn) {
		this.skusn = skusn;
	}

	public String getXiucode() {
		return xiucode;
	}

	public void setXiucode(String xiucode) {
		this.xiucode = xiucode;
	}

	public String getStocknum() {
		return stocknum;
	}

	public void setStocknum(String stocknum) {
		this.stocknum = stocknum;
	}

	public String getColorname() {
		return colorname;
	}

	public void setColorname(String colorname) {
		this.colorname = colorname;
	}

	public String getSizevalue() {
		return sizevalue;
	}

	public void setSizevalue(String sizevalue) {
		this.sizevalue = sizevalue;
	}
	
	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	
	

	public int getIsmain() {
		return ismain;
	}

	public void setIsmain(int ismain) {
		this.ismain = ismain;
	}

	public String getSkuImagePath() {
		return skuImagePath;
	}

	public void setSkuImagePath(String skuImagePath) {
		this.skuImagePath = skuImagePath;
	}
	

	public String getWareId() {
		return wareId;
	}

	public void setWareId(String wareId) {
		this.wareId = wareId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((skusn == null) ? 0 : skusn.hashCode());
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
		JDSku other = (JDSku) obj;
		if (skusn == null) {
			if (other.skusn != null)
				return false;
		} else if (!skusn.equals(other.skusn))
			return false;
		return true;
	}

	
	public String getJdWareJd() {
		return jdWareJd;
	}



	public void setJdWareJd(String jdWareJd) {
		this.jdWareJd = jdWareJd;
	}



	public String getJdSkuId() {
		return jdSkuId;
	}



	public void setJdSkuId(String jdSkuId) {
		this.jdSkuId = jdSkuId;
	}



	@Override
	public String toString() {
		return "JDSku [skusn=" + skusn + ", xiucode=" + xiucode + ", stocknum="
				+ stocknum + ", colorname=" + colorname + ", sizevalue="
				+ sizevalue + ", attributes=" + attributes + ", ismain="
				+ ismain + ", skuImagePath=" + skuImagePath + ", wareId="
				+ wareId + ", status=" + status + ", num=" + num
				+ ", jdWareJd=" + jdWareJd + ",jdSkuId="+jdSkuId+"]";
	}
	
	
	

   
}
