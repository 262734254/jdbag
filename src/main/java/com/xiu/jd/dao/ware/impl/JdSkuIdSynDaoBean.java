package com.xiu.jd.dao.ware.impl;

import org.springframework.stereotype.Repository;

import com.xiu.jd.bean.ware.JdSkuIdSyn;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.dao.ware.JdSkuIdSynDao;

@Repository("jdSkuIdSynDaoBean")
public class JdSkuIdSynDaoBean extends BaseDaoImpl<JdSkuIdSyn> implements
		JdSkuIdSynDao<JdSkuIdSyn> {

	@Override
	public int deleteById(String wareId) {
		return this.getSqlMapClientTemplate().delete("JdSkuIdSynDaoBean.deleteById",wareId);
	}
	

}
