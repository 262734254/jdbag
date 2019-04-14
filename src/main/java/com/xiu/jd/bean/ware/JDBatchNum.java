package com.xiu.jd.bean.ware;

import java.io.Serializable;

public class JDBatchNum implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4175611491898485577L;
	private long num;

	public JDBatchNum(){}
	
	public JDBatchNum(long num) {
		super();
		this.num = num;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (num ^ (num >>> 32));
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
		JDBatchNum other = (JDBatchNum) obj;
		if (num != other.num)
			return false;
		return true;
	}
	
	
}
