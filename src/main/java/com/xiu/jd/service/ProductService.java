package com.xiu.jd.service;

import java.util.List;

import com.xiu.commerce.hessian.model.Product;

/**
 * 产品相关业务逻辑
 * 将远程调用商品中心、渠道中心的逻辑封装在此
 * 
 * @author liweibiao
 *
 */
public interface ProductService {
	/**
	 * 查询商品详情
	 * @param goodsSn 商品编号
	 * @return 商品中心的Product对象
	 */
	public Product loadProduct(String goodsSn);
	
	/**
	 * 根据sku查询库存
	 * @param sku
	 * @return 库存信息，出错则返回-9999
	 */
	public int queryInventoryBySku(String sku);
	
	
	public List<Product> batchLoadProducts(String goodsSnBag);
}
