package com.xiu.jd.dao.ware;

import java.util.List;
import java.util.Map;

import com.xiu.jd.bean.ware.JDWareSkuBrand;
import com.xiu.jd.dao.BaseDao;

public interface JDWareAndSkuInfoDao<T> extends BaseDao<T> {

	public List<JDWareSkuBrand> queryWareSkuBrand(Map<String, Object> maps);
	
}
