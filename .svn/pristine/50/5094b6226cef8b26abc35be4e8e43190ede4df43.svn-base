package com.xiu.jd.dao.ware.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiu.jd.bean.ware.JdSellerCategory;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.dao.ware.JdSellerCategoryDao;

@Repository("jdSellerCategoryDaoBean")
public class JdSellerCategoryDaoBean extends BaseDaoImpl<JdSellerCategory>
		implements JdSellerCategoryDao<JdSellerCategory> {

	@SuppressWarnings("unchecked")
	@Override
	public List<JdSellerCategory> querySellerCategory(Map<String, Object> maps) {
		List<JdSellerCategory> lists = this.getSqlMapClientTemplate().queryForList("JdSellerCategoryDaoBean.querySellerCategory",maps);
		return lists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JdSellerCategory> querySellerCategoryFid(Integer shopCategoryId) {
		List<JdSellerCategory> lists = this.getSqlMapClientTemplate().queryForList("JdSellerCategoryDaoBean.querySellerCategoryFid",shopCategoryId);
		return lists;
	}

	@Override
	public void deleteAllSellerCategory() {
		this.getSqlMapClientTemplate().delete("JdSellerCategoryDaoBean.deleteAllSellerCategory");
	}
	
}
