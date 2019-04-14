package com.xiu.jd.service.ware;

import java.util.List;
import java.util.Map;

import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDSkuInfo;
import com.xiu.jd.bean.ware.JDXiuColorAndSize;

public interface JdXiuSkuInfoService {
	
	public boolean isExistJdXiuSku(JDXiuColorAndSize jdc);

	public void insertBatch(List<JDXiuColorAndSize> jdXiuColorList, int i);

	public QueryResult<JDSkuInfo> querySkuInfoList(Map<String, Object> paramers);
	
	public long getSkuInfoCount(Map<String, Object> parames);
	/**批量删除京东商品sku**/
	public String deleteSku(Map<String, List<String>> parames);
	/**根据京东商品商品skuId查询商品ID**/
	public String queryWareId(String jdSkuId);
}
