package com.xiu.jd.dao.ware.impl;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.bean.ware.JDWareSkuBrand;
import com.xiu.jd.bean.ware.ProductAttributeInfoModel;
import com.xiu.jd.bean.ware.XiuJdLogistics;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.dao.ware.JDWareDao;

@Repository("jDWareDaoBean")
public class JDWareDaoBean extends BaseDaoImpl<JDProduct> implements JDWareDao<JDProduct> {
	
	
	public JDProduct getJdProductByWareId(String wareId)throws Exception{
		return (JDProduct) getSqlMapClientTemplate().queryForObject("JDWareDaoBean.getJdProductByWareId",wareId);
	}
	
	public String getTradeNo()throws Exception{
		Connection conn = getSqlMapClientTemplate().getDataSource().getConnection();
		CallableStatement cs = conn.prepareCall("{call proc_tradeno(?)}");
		cs.registerOutParameter(1,java.sql.Types.VARCHAR);
		cs.execute();
		String tradeNo = cs.getString(1);
		conn.close();
		return tradeNo;
	}
	
	@SuppressWarnings("unchecked")
	public XiuJdLogistics getXiuJdLogistics(String deliverCode)throws Exception{
		//queryForObject 改为queryForList
		List<XiuJdLogistics> jdLogistics= getSqlMapClientTemplate().queryForList("JDWareDaoBean.getXiuJdLogistics",deliverCode);
		if(jdLogistics!=null && jdLogistics.size()>0){
			return jdLogistics.get(0);
		}
		 return null;
	}
	
	
	private static final Logger logger=Logger.getLogger(JDWareDaoBean.class);
	/***
     * 导入Excle后跳转到商品列表时(或再商品列表查询时),
     * 当点击修改按钮时对该商品实行加锁
     * 防止多个用户同时对其进行操作
     * @param parames 
     * lockuser
     * xiucode 
     * @return
     */
	public int addLock(Map<String, Object> parames){
		return this.getSqlMapClientTemplate().update("JDWareDaoBean.addLock", parames);
		
	}
	/**
	 *  修改商品时完成时对商品进行解锁
	 * @param xiucode
	 * @return
	 */
	public int unlock(String xiucode){
		return this.getSqlMapClientTemplate().update("JDWareDaoBean.unlock", xiucode);
	}
	
	@Override
	public boolean xiuCodeIsExistsNation(String xiuCode) {
		Object object=this.getSqlMapClientTemplate().queryForObject("JDWareDaoBean.xiuCodeIsExistsNation",xiuCode);
		if(object==null){
			//在本地不存在
			return true;
		}
		int count=Integer.valueOf(object.toString());
		return count>0?false:true;

	}

	@Override
	public boolean wareIdIsExistsNation(String wareId) {
		Object object=this.getSqlMapClientTemplate().queryForObject("JDWareDaoBean.wareIdIsExistsNation",wareId);
		if(object==null){
			//在本地不存在
			return true;
		}
		int count=Integer.valueOf(object.toString());
		//false本地存在
		return count>0?false:true;
		
	}
   
	@Override
	public int deleteWareInfoByXiuCodes(List<String> xiucodes) {
		return this.getSqlMapClientTemplate().delete("JDWareDaoBean.deleteWareInfoByXiuCodes", xiucodes);
	}
	@Override
	public int updateJdProductPrice(Map<String, Object> parames) {
	       return this.getSqlMapClientTemplate().update ("JDWareDaoBean.updateJdProductPrice",parames);
	}
	
	@Override
	public int updateProductOnline(Map<String, Object> productParmes) {
		  return this.getSqlMapClientTemplate().update ("JDWareDaoBean.updateProductOnline",productParmes);
	}
	
	@Override
	public void updateProductOnlineBatch( final List<JDProduct> jdProducts,  final int batchSize) {

		try {
			if (jdProducts != null) {
			this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
						int div = 0;
						if(batchSize<=0){
							div=20;
						}else{
							div=batchSize;
						}
						int count = 0;
						executor.startBatch();
						for (JDProduct jdProduct : jdProducts) {
							executor.update(getClassSimpleNameSpace() + ".updateProductOnlineBatch", jdProduct);
							count++;
							if (count == div) {
								executor.executeBatch();
								executor.startBatch();
								count = 0;
							}
						}
						executor.executeBatch();
						return jdProducts.size();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductAttributeInfoModel> findProductAttributeList(
			List<String> partNumbers) {
		return (List<ProductAttributeInfoModel>)this.getSqlMapClientTemplate().queryForList("JDWareDaoBean.select_product_attribute_list", partNumbers);
	}
	public JDProduct queryWareInfoAndLockWareObject(Map<String, Object> parames,String userName,String flag){
		if(flag!=null && !flag.isEmpty()){
			Object obj=parames.get("xiucode");
			if(obj!=null){
				Map<String, Object> p=new HashMap<String, Object>();
				p.put("xiucode", obj.toString());
				p.put("lockuser", userName);
				int result=addLock(p);
				logger.info("1:商品加锁成功,0失败"+result);
			}
		}
		return this.queryResult(parames);
	}
	
	public JDProduct queryXiuWareInfoAndLockWareObject(Map<String, Object> parames,String userName,String flag){
		if(flag!=null && !flag.isEmpty()){
			Object obj=parames.get("xiucode");
			if(obj!=null){
				Map<String, Object> p=new HashMap<String, Object>();
				p.put("xiucode", obj.toString());
				p.put("lockuser", userName);
				int result=addLock(p);
				logger.info("1:商品加锁成功,0失败"+result);
			}
		}
		return this.queryXiuResult(parames);
	}
	
	public JDProduct queryEbayWareInfoAndLockWareObject(Map<String, Object> parames,String userName,String flag){
		if(flag!=null && !flag.isEmpty()){
			Object obj=parames.get("xiucode");
			if(obj!=null){
				Map<String, Object> p=new HashMap<String, Object>();
				p.put("xiucode", obj.toString());
				p.put("lockuser", userName);
				int result=addLock(p);
				logger.info("1:商品加锁成功,0失败"+result);
			}
		}
		return this.queryEbayResult(parames);
	}

	@Override
	public String findJDCategoryId(String xiuCategoryId) {
		return (String) this.getSqlMapClientTemplate().queryForObject("JDWareDaoBean.findJDCategoryId",xiuCategoryId);
	}

	@Override
	public boolean jdBrandIsExist(String xiucode) {
		Object object=this.getSqlMapClientTemplate().queryForObject("JDWareDaoBean.jdBrandIsExist",xiucode);
		if(object==null){
			return false;
		}
		int count=Integer.valueOf(object.toString());
		return count>=1?true:false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<JDProduct> queryWardIdsByXiuCodes(Map<String, Object> parames) {
		return(List<JDProduct>) this.getSqlMapClientTemplate().queryForList("JDWareDaoBean.queryWardIdsByXiuCodes",parames);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<JDProduct> queryXiuCodesByWardIds(Map<String, Object> parames) {
		return(List<JDProduct>) this.getSqlMapClientTemplate().queryForList("JDWareDaoBean.queryXiuCodesByWardIds",parames);
	}
	@Override
	public void deleteAllProduct() {
		 this.getSqlMapClientTemplate().delete("JDWareDaoBean.deleteAllProduct");
		
	}

	@Override
	public void updateProduct(JDProduct product) {
		 this.getSqlMapClientTemplate().update("JDWareDaoBean.updateProduct",product);
	}


	@Override
	public String getJdProductMainPic(String jdWareId) {
		Object obj = this.getSqlMapClientTemplate().queryForObject("JDWareDaoBean.getJdProductMainPic",jdWareId);
		return (String) obj;
	}

	@Override
	public int updateJdProductBatch(final List<JDProduct> entities, final int batchSize) {
		try {
			if (entities != null) {
			return	(Integer)this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
						int div = 0;
						if(batchSize<=0){
							div=20;
						}else{
							div=batchSize;
						}
						int count = 0;
						executor.startBatch();
						for (JDProduct jdProduct : entities) {
							executor.update(getClassSimpleNameSpace() + ".updateJdProductBatch", jdProduct);
							count++;
							if (count == div) {
								executor.executeBatch();
								executor.startBatch();
								count = 0;
							}
						}
						executor.executeBatch();
						return entities.size();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteNationProductByWareId(String wareId) {
		return this.getSqlMapClientTemplate().delete("JDWareDaoBean.deleteNationProductByWareId",wareId);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<JDWareSkuBrand> queryWareSkuBrandPage(
			Map<String, Object> parames) {
		return (List<JDWareSkuBrand>)this.getSqlMapClientTemplate().queryForList("JDWareAndSkuInfoDaoBean.queryWareSkuBrandPage",parames);
	}

	@Override
	public long queryWareSkuBrandCount(Map<String, Object> parames) {
		Object obj=this.getSqlMapClientTemplate().queryForObject("JDWareAndSkuInfoDaoBean.queryWareSkuBrandCount",parames);
		if(obj==null){
			return 0;
		}
		return Long.parseLong(obj.toString());
	}

	@Override
	public JDProduct getProductByXiuCode(String xiuCode) {
		return (JDProduct)this.getSqlMapClientTemplate().queryForObject("JDWareDaoBean.getProductByXiuCode",xiuCode);
		
	}
	
	@Override
	public JDProduct getXiuProductByXiuCode(String xiuCode) {
		return (JDProduct)this.getSqlMapClientTemplate().queryForObject("JDWareDaoBean.getXiuProductByXiuCode",xiuCode);
		
	}
	
	
	@Override
	public JDProduct getEbayProductByXiuCode(String xiuCode) {
		return (JDProduct)this.getSqlMapClientTemplate().queryForObject("JDWareDaoBean.getEbayProductByXiuCode",xiuCode);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JDProduct> queryWareInfoAndLockWareObjects(
			Map<String, Object> parames, String userName, String flag) {
		if(flag!=null && !flag.isEmpty()){
			Object obj=parames.get("xiucode");
			if(obj!=null){
				Map<String, Object> p=new HashMap<String, Object>();
				p.put("xiucode", obj.toString());
				p.put("lockuser", userName);
				int result=addLock(p);
				logger.info("1:商品加锁成功,0失败"+result);
			}
			
		}
		
		return (List<JDProduct>) this.getSqlMapClientTemplate().queryForList("JDWareDaoBean.queryWareInfoAndLockWareObjects",parames);
	}

	@Override
	public String getProductTotalStockNum(String xiuCode) {
	 return (String)this.getSqlMapClientTemplate().queryForObject("JDWareDaoBean.getProductTotalStockNum",xiuCode);
	}

	@Override
	public String getProductTotalStockNumByWareId(String wareId) {
		 return (String)this.getSqlMapClientTemplate().queryForObject("JDWareDaoBean.getProductTotalStockNumByWareId",wareId);
	}
	@Override
	public void updateJdAdContent(String jdAdContent) {
		getSqlMapClientTemplate().update("JDWareDaoBean.updateAdContent",jdAdContent);
		
	}

	@Override
	public String getJdAdContent() {
		Object object=getSqlMapClientTemplate().queryForObject("JDWareDaoBean.getAdcontent",null);
		return (String) object;
	}
	

	
	

}
