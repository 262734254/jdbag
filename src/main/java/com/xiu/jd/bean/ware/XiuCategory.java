package com.xiu.jd.bean.ware;

import java.io.Serializable;

public class XiuCategory implements Serializable{
	private static final long serialVersionUID = 1884618980464031631L;
	private String code;
	private String name;
	private String parentCode;
	private String paramCode;
	public XiuCategory() {
		super();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	
	
}
