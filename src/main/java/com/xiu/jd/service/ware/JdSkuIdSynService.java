package com.xiu.jd.service.ware;

import java.util.Map;

import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JdSkuIdSyn;

public interface JdSkuIdSynService {
	public JdSkuIdSyn insertJdSkuIdSynService(JdSkuIdSyn entity);
	public JdSkuIdSyn insertXiuJdSkuIdSynService(JdSkuIdSyn entity);
	public JdSkuIdSyn insertEbayJdSkuIdSynService(JdSkuIdSyn entity);
	

	public QueryResult<JdSkuIdSyn> getPageResule(Map<String, Object> parames);

	public int deleteById(String wareId);
}
