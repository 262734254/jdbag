package com.xiu.jd.service.ware;

import java.util.List;
import java.util.Map;

import com.xiu.jd.bean.ware.JdSellerCategory;

public interface JdSellerCategoryService {

	public void insert(JdSellerCategory jdSellerCategory) throws Exception ;
	
	public void insertBatch(List<JdSellerCategory> jdSellerCategories, int batchSize) throws Exception ;
	/**查询京东店内分类**/
	public Map<JdSellerCategory,List<JdSellerCategory>> querySellerCategory();
	/**查询京东店内分类（根据fid）**/
	public List<JdSellerCategory> querySellerCategoryFid(Integer shopCategoryId);
	/**批量更新（覆盖）京东店内分类**/
	public void updateShopCategory(String jdWareIds, String jdShopCategory);
	/**删除本地京东店内分类表的数据**/
	public void deleteAllSellerCategory();
	/**批量更新（新增）京东店内分类**/
	public void addShopCategory(String jdwardIds, String jdShopCategory);

}
