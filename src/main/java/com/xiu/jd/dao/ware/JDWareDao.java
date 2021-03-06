package com.xiu.jd.dao.ware;

import java.util.List;
import java.util.Map;

import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.bean.ware.JDWareSkuBrand;
import com.xiu.jd.bean.ware.ProductAttributeInfoModel;
import com.xiu.jd.dao.BaseDao;

public interface JDWareDao<T> extends BaseDao<T> {

	 /***
     * 导入Excle后跳转到商品列表时(或再商品列表查询时),
     * 当点击修改按钮时对该商品实行加锁
     * 防止多个用户同时对其进行操作
     * @param parames 
     * lockuser
     * xiucode 
     * @return
     */
	public int addLock(Map<String, Object> parames);
	
	
	/**
	 *  修改商品时完成时对商品进行解锁
	 * @param xiucode
	 * @return
	 */
	public int unlock(String xiucode);
	
	/**
	 * 走秀码是否在本地数据库在存在
	 * @param xiuCode
	 * @return  true:本地不存在,false本地存在
	 */
	public boolean xiuCodeIsExistsNation(String xiuCode);
	  /**根据走秀码根系商品价格(本地数据库jd_product_info表)**/
	public int updateJdProductPrice(Map<String, Object> parames);
	
	/**
	 * 京东商品是否在本地数据库在存在
	 * @param wareId 京东商品Id
	 * @return  true:本地不存在,false本地存在
	 */
	public boolean wareIdIsExistsNation(String wareId);
	 /***
	  * 根据走秀码删除商品已经商品底下的SKU信息
	  * @param xiucodes
	  * @return
	  */
	 public int deleteWareInfoByXiuCodes(List<String> xiucodes);
	 
	 
	 public int deleteNationProductByWareId(String wareId);
	
	/**
	 *  从商品中心查询商品的属性信息
	 * @param partNumbers 商品走秀码
	 * @return
	 */
	public List<ProductAttributeInfoModel> findProductAttributeList(List<String> partNumbers );
	
	/**根据走秀码查询对应的京东ID**/
	public List<JDProduct> queryWardIdsByXiuCodes(Map<String, Object> parames);
	
	 /**根据京东商品ID查询对应的走秀码**/
	public List<JDProduct> queryXiuCodesByWardIds(Map<String, Object> parames);
	
	/**
	 * 更新商品销售状态 1:在售,2:下架 
	 * @param productParmes
	 * @return
	 */
	public int updateProductOnline(Map<String, Object> productParmes);
	
	public void updateProductOnlineBatch( final List<JDProduct> jdProducts,  final int batchSize);
	
	public void deleteAllProduct();
	
	/**
	 * 
	 * @param parames   参数
	 * @param userName  被锁住的用户名
	 * @param flag     是否执行更新 flag=flag执行
	 * @return
	 */
	public JDProduct queryWareInfoAndLockWareObject(Map<String, Object> parames,String userName,String flag);
	/**
	 * 
	 * @param parames   参数
	 * @param userName  被锁住的用户名
	 * @param flag     是否执行更新 flag=flag执行
	 * @return
	 */
	public JDProduct queryXiuWareInfoAndLockWareObject(Map<String, Object> parames,String userName,String flag);
	
	/**
	 * 
	 * @param parames   参数
	 * @param userName  被锁住的用户名
	 * @param flag     是否执行更新 flag=flag执行
	 * @return
	 */
	public JDProduct queryEbayWareInfoAndLockWareObject(Map<String, Object> parames,String userName,String flag);
	
	public String findJDCategoryId(String xiuCategoryId);

	/**查询京东类目下是否存在品牌**/
	public boolean jdBrandIsExist(String xiucode);

	/**更新京东商品信息**/
	public void updateProduct(JDProduct product);


	/**根据商品ID查询商品主图**/
	public String getJdProductMainPic(String jdWareId);

	/**批量更新商品信息**/
	public int updateJdProductBatch(List<JDProduct> jdProductList, int size);
	
	public JDProduct getJdProductByWareId(String wareId) throws Exception;

	/**
	 * Excel分页导出商品信息
	 * @param parames
	 * @return
	 */

	public List<JDWareSkuBrand> queryWareSkuBrandPage(
			Map<String, Object> parames);


	/**
	 * Excel导出商品总记录数量
	 * @param parames
	 * @return
	 */
	public long queryWareSkuBrandCount(Map<String, Object> parames);


	 /**
	  * 获取商品通过走秀码
	  * @param xiuCode 走秀码
	  * @return
	  */
	public JDProduct getProductByXiuCode(String xiuCode);
	
	 /**
	  * 获取商品通过走秀码
	  * @param xiuCode 走秀码
	  * @return
	  */
	public JDProduct getXiuProductByXiuCode(String xiuCode);
	
	 /**
	  * 获取商品通过走秀码
	  * @param xiuCode 走秀码
	  * @return
	  */
	public JDProduct getEbayProductByXiuCode(String xiuCode);

   /**
    * 获取商品信息
    * @param parames
    * @param userName
    * @param flag
    * @return
    */
	public List<JDProduct> queryWareInfoAndLockWareObjects(
			Map<String, Object> parames, String userName, String flag);


	/**
	 * 根据走秀码，查询商品总库存
	 * @param  xiuCode
	 * @return
	 */
 public String getProductTotalStockNum(String xiuCode);

	/**
	 * 根据京东商品ID，查询商品总库存
	 * @param  xiuCode
	 * @return
	 */
	public String getProductTotalStockNumByWareId(String wareId);

	
	public void updateJdAdContent(String jdAdContent);
	public String getJdAdContent();
	


	


	



	


	
}
