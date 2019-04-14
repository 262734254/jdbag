package com.xiu.jd.dao.ware;

import java.util.List;
import java.util.Map;

import com.xiu.jd.bean.ware.JdSellerCategory;
import com.xiu.jd.dao.BaseDao;

public interface JdSellerCategoryDao<T> extends BaseDao<T> {
	/**查询京东店内分类**/
	public List<JdSellerCategory> querySellerCategory(Map<String, Object> maps);
	/**查询京东店内分类（根据fid）**/
	public List<JdSellerCategory> querySellerCategoryFid(Integer shopCategoryId);
	/**删除本地京东店内分类表中的数据**/
	public void deleteAllSellerCategory();

}
