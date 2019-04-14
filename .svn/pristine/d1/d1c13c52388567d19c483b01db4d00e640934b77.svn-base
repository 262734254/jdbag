package com.xiu.jd.bean.user;

import java.io.Serializable;

/**
 * 京东收货人信息实体类
 * @author user
 *
 */
public class JDConsigneenInfo implements Serializable {
	private static final long serialVersionUID = 7415125939887028901L;
	/**主键ID**/
	private long id;
	/**收货人名称**/
	private String fullName;
	/**收货人手机号**/
	private String mobile;
	/**收货人固定电话**/
	private String telePhone;
	/**收货人地址详情**/
	private String fullAddress;
	
	public JDConsigneenInfo(){}
	
	
	
	public JDConsigneenInfo(String fullName, String mobile, String telePhone,
			String fullAddress) {
		super();
		this.fullName = fullName;
		this.mobile = mobile;
		this.telePhone = telePhone;
		this.fullAddress = fullAddress;
	}



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getTelePhone() {
		return telePhone;
	}

	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		JDConsigneenInfo other = (JDConsigneenInfo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JDConsigneenInfo [id=" + id + ", fullName=" + fullName
				+ ", mobile=" + mobile + ", telePhone=" + telePhone
				+ ", fullAddress=" + fullAddress + "]";
	}
	
	
	
	 

}
