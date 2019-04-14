package com.xiu.jd.dao.ware;

import java.util.List;
import java.util.Map;

import com.xiu.jd.bean.ware.JDSku;
import com.xiu.jd.dao.BaseDao;

public interface JdSkuDao<T> extends BaseDao<T> {
    /**
     * 判断商品SKU码在本地数据库中是否已经存在
     * @param outerId
     * @return
     */
	boolean xiuSnIsExistsNation(String outerId);

	
	  /**
     * 更新商品sku库存 (到本地数据库 jd_product_sku_info表)
     * @param parames
     * @return
     */
	public int updateJdProductSkuStock(Map<String, Object> parames);
	
	/**
	 * 批量更新
	 */
	public int updateBatch(final List<T> entities,final int batchSize);
	/**
	 * 批量更新
	 */
	public int updateEbayBatch(final List<T> entities,final int batchSize);
	/**
	 * 批量更新
	 */
	public int updateXiuBatch(final List<T> entities,final int batchSize);
	
	public List<T> isUpLoadToJd();
	
	public int updateStatus(T t);
	
	public String findPicDate(T t);
	
	public String findSkuColor(Map<String,String> maps);
	
	/**商品商品SKU**/
	public void deleteWareSKUByXiuCodes(List<String> xiucodes);
	
	public  int deleteWareSKUByXiuCodesAndWardId(Map<String, Object> parames);
	
	public String findSkuSize(Map<String,String> maps);
	/****
	 * 查找商品的sku为outerId的属性
	 * @param outerId
	 * @return
	 */
	public String findLocalSkuAttr(String outerId);

	
	public List<JDSku> findSku(Map<String, Object> parames);

	/**查找销售属性名**/
	public String findSaleAttrName(String saleValue);


	public int deleteNationProductSkuByWareId(String wareId);

	/**查找商品sku**/
	List<JDSku> querySkuInfo(Map<String, Object> skuParames);
	
	public int findSkuCount(Map<String, Object> skuParames);
	
	public List<JDSku> querySkuInfoByWareId(Map<String, Object> parames);




	
	
}
