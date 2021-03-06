package com.xiu.jd.dao.ware.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.xiu.jd.bean.ware.JDSku;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.dao.ware.JdSkuDao;

@Repository("jdSkuDaoBean")
public class JdSkuDaoBean extends BaseDaoImpl<JDSku> implements JdSkuDao<JDSku> {

	@Override
	public boolean xiuSnIsExistsNation(String outerId) {
		Object object = this.getSqlMapClientTemplate().queryForObject("JdSkuDaoBean.xiuSnIsExistsNation", outerId);
		if (object == null) {
			// 在本地不存在
			return true;
		}
		int count = Integer.valueOf(object.toString());
		// false本地存在
		return count > 0 ? false : true;
	}

	@Override
	public int updateJdProductSkuStock(Map<String, Object> parames) {
		return this.getSqlMapClientTemplate().update("JdSkuDaoBean.updateJdProductSkuStock", parames);
	}

	@Override
	public int updateBatch(final List<JDSku> entities, final int batchSize) {
		 
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
						for (JDSku jdSku : entities) {
							int num=executor.update(getClassSimpleNameSpace() + ".updateBatch", jdSku);
							 System.out.println("num " +num);
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
	public int updateXiuBatch(final List<JDSku> entities, final int batchSize) {
		 
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
						for (JDSku jdSku : entities) {
							int num=executor.update(getClassSimpleNameSpace() + ".updateXiuBatch", jdSku);
							 System.out.println("num " +num);
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
	public int updateEbayBatch(final List<JDSku> entities, final int batchSize) {
		 
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
						for (JDSku jdSku : entities) {
							int num=executor.update(getClassSimpleNameSpace() + ".updateEbayBatch", jdSku);
							 System.out.println("num " +num);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<JDSku> isUpLoadToJd() {
		List<JDSku> jDskus = this.getSqlMapClientTemplate().queryForList("JdSkuDaoBean.isUpLoadToJd");
		return jDskus;
	}

	@Override
	public int updateStatus(JDSku sku) {
		return this.getSqlMapClientTemplate().update("JdSkuDaoBean.updateStatus", sku);
	}

	@Override
	public void deleteWareSKUByXiuCodes(List<String> xiucodes) {
		this.getSqlMapClientTemplate().delete("JdSkuDaoBean.deleteWareSKUByXiuCodes", xiucodes);

	}
	@Override
	public int deleteWareSKUByXiuCodesAndWardId(Map<String, Object> parames) {
		return this.getSqlMapClientTemplate().delete("JdSkuDaoBean.deleteWareSKUByXiuCodesAndWardId", parames);

	}

	@Override
	public String findPicDate(JDSku t) {
		Object obj = this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace() + ".findPicDate", t);
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	@Override
	public String findSkuColor(Map<String, String> maps) {
		return (String) this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace() + ".findSkuColor", maps);
	}

	@Override
	public String findSkuSize(Map<String, String> maps) {
		return (String) this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace() + ".findSkuSize", maps);
	}

	@Override
	public String findLocalSkuAttr(String outerId) {
		Object obj = this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace() + ".findLocalSkuAttr", outerId);
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<JDSku> findSku(Map<String, Object> parames) {
		List<JDSku> lists = this.getSqlMapClientTemplate().queryForList(getClassSimpleNameSpace() + ".findSku", parames);
		return lists;
	}

	@Override
	public String findSaleAttrName(String saleValue) {
		Object obj = this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace() +".findSaleAttrName", saleValue);
		if(obj == null){
			return "";
		}
		return obj.toString();
	}

	@Override
	public int deleteNationProductSkuByWareId(String wareId) {
		return this.getSqlMapClientTemplate().delete(getClassSimpleNameSpace() + ".deleteNationProductSkuByWareId", wareId);
	}

	@Override
	public List<JDSku> querySkuInfo(Map<String, Object> skuParames) {
		@SuppressWarnings("unchecked")
		List<JDSku> jdskuList = this.getSqlMapClientTemplate().queryForList(getClassSimpleNameSpace()+".querySkuInfo", skuParames);
		return jdskuList;
	}

	

	@Override
	public int findSkuCount(Map<String, Object> skuParames) {
		 Object count = this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace() + ".findSkuCount", skuParames);
		 if(count==null){
			 return 0;
		 }
		 return Integer.valueOf(count.toString());
	}
	
	@Override
	public List<JDSku> querySkuInfoByWareId(Map<String, Object> parames) {
		List<JDSku> jdskuList = this.getSqlMapClientTemplate().queryForList(getClassSimpleNameSpace()+".querySkuInfoByWareId", parames);
		return jdskuList;
	}
	
}
