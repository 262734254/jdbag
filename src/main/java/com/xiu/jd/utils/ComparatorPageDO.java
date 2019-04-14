package com.xiu.jd.utils;

import java.util.Comparator;

import com.xiu.usermanager.provider.bean.PageDO;

public class ComparatorPageDO implements Comparator, java.io.Serializable {

	private static final long serialVersionUID = -3950138211373470486L;

	public int compare(Object arg0, Object arg1) {
		PageDO PageDO0 = (PageDO) arg0;
		PageDO PageDO1 = (PageDO) arg1;	
		return PageDO0.getPageId().compareTo(PageDO1.getPageId());
	}
}