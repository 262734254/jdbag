package com.xiu.jd.dao.ware.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiu.jd.bean.ware.JDWareAndSkuInfo;
import com.xiu.jd.bean.ware.JDWareSkuBrand;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.dao.ware.JDWareAndSkuInfoDao;

@Repository("jDWareAndSkuInfoDaoBean")
public class JDWareAndSkuInfoDaoBean extends BaseDaoImpl<JDWareAndSkuInfo> implements JDWareAndSkuInfoDao<JDWareAndSkuInfo> {

	@SuppressWarnings("unchecked")
	@Override
	public List<JDWareSkuBrand> queryWareSkuBrand(Map<String, Object> maps) {
		List<JDWareSkuBrand> lists = this.getSqlMapClientTemplate().queryForList("JDWareAndSkuInfoDaoBean.queryWareSkuBrand", maps);
		return lists;
	}

}
