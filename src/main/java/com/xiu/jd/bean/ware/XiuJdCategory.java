package com.xiu.jd.bean.ware;

import java.io.Serializable;

public class XiuJdCategory implements Serializable {
	private static final long serialVersionUID = -5748887979325960747L;
	private Integer id;
	private String jdCid;
	private String jdName;
	private String xiuCid;
	private String xiuName;
	private String jdFullName;
	public XiuJdCategory() {
		super();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJdCid() {
		return jdCid;
	}
	public void setJdCid(String jdCid) {
		this.jdCid = jdCid;
	}
	public String getJdName() {
		return jdName;
	}
	public void setJdName(String jdName) {
		this.jdName = jdName;
	}
	public String getXiuCid() {
		return xiuCid;
	}
	public void setXiuCid(String xiuCid) {
		this.xiuCid = xiuCid;
	}
	public String getXiuName() {
		return xiuName;
	}
	public void setXiuName(String xiuName) {
		this.xiuName = xiuName;
	}

	public String getJdFullName() {
		return jdFullName;
	}

	public void setJdFullName(String jdFullName) {
		this.jdFullName = jdFullName;
	}

	
}
