package com.xiu.jd.bean.ware;

import java.io.Serializable;

/**
 * 同步京东商品skuid到本地数据jd_product_sku_info中的jdskuid字段
 * @author user
 *
 */
public class JdSkuIdSyn implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5713025265509760867L;
	/**
     * 京东商品ID
     */
	private String jdWareId;

	public String getJdWareId() {
		return jdWareId;
	}

	public void setJdWareId(String jdWareId) {
		this.jdWareId = jdWareId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((jdWareId == null) ? 0 : jdWareId.hashCode());
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
		JdSkuIdSyn other = (JdSkuIdSyn) obj;
		if (jdWareId == null) {
			if (other.jdWareId != null)
				return false;
		} else if (!jdWareId.equals(other.jdWareId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JdSkuIdSyn [jdWareId=" + jdWareId + "]";
	}
	
	
	
}
