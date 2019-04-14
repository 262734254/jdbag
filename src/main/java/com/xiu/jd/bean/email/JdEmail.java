package com.xiu.jd.bean.email;

import java.io.Serializable;

/**
 * 日志实体类
 * @author user
 *
 */
public class JdEmail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String emailAddress;//邮件地震
	
	private String type;//类型
	
	private String timeSet;
	
	

	public String getTimeSet() {
		return timeSet;
	}

	public void setTimeSet(String timeSet) {
		this.timeSet = timeSet;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

  
	
	
}
