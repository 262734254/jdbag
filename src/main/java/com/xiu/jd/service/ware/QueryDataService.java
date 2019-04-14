package com.xiu.jd.service.ware;

import com.xiu.jd.bean.page.QueryData;

/**
 * 用于查询数据
 * @author user
 *
 */
public interface QueryDataService {
	/**
	 * 
	 * @param sql 前台输入的sql
	 * @return
	 * @throws Exception
	 */
	public QueryData queryResultData(String sql,String flag)throws Exception;
}
