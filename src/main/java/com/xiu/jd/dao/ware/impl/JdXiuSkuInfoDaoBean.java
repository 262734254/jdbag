package com.xiu.jd.dao.ware.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDSkuInfo;
import com.xiu.jd.bean.ware.JDXiuColorAndSize;
import com.xiu.jd.dao.BaseDaoImpl;

@Repository("jdXiuSkuInfoDaoBean")
public class JdXiuSkuInfoDaoBean  extends BaseDaoImpl<JDXiuColorAndSize>{

	public boolean isExistSkuColor(JDXiuColorAndSize jxc){
		Object obj = this.getSqlMapClientTemplate().queryForObject("JdXiuSkuInfoDaoBean.findSkuColor", jxc);
		if(obj == null){
			return false;
		}
		int count=Integer.valueOf(obj.toString());
		return count>=1?true:false;
	}
	
	public boolean isExistSkuSize(JDXiuColorAndSize jxs){
		Object obj = this.getSqlMapClientTemplate().queryForObject("JdXiuSkuInfoDaoBean.findSkuSize", jxs);
		if(obj == null){
			return false;
		}
		int count=Integer.valueOf(obj.toString());
		return count>=1?true:false;
	}

	public void insertBatchJdSku(final List<JDXiuColorAndSize> jdXiuList, final int batchSize,final String param) {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public List<JDXiuColorAndSize> doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				int div = 0;
				if(batchSize<=0){
					div=20;
				}else{
					div=batchSize;
				}
				int count = 0;
				executor.startBatch();
				for (JDXiuColorAndSize  entity:jdXiuList) {
					executor.insert(getClassSimpleNameSpace()+"."+param, entity);
					count++;
					if (count == div) {
						executor.executeBatch();
						executor.startBatch();
						count = 0;
					}
				}
				executor.executeBatch();
				return jdXiuList;
			}
		});
	}

	
	@SuppressWarnings("unchecked")
	public QueryResult<JDSkuInfo> querySkuInfoList(Map<String,Object> paramers){
		 QueryResult<JDSkuInfo> qr=new QueryResult<JDSkuInfo>();
		 List<JDSkuInfo> lists= this.getSqlMapClientTemplate().queryForList("JdXiuSkuInfoDaoBean.querySkuInfoList", paramers);
		 qr.setResultlist(lists);
		 qr.setTotalrecord(this.getSkuInfoCount(paramers));
		 return qr;
	}
	
	public long getSkuInfoCount(Map<String, Object> parames) {
		 Object object=this.getSqlMapClientTemplate().queryForObject("JdXiuSkuInfoDaoBean.getSkuInfoCount",parames);
		 if(object==null){
			 return 0;
		 }
		 return Long.parseLong(object.toString());
	}

	public void deleteSku(String skuId) {
		this.getSqlMapClientTemplate().delete("JdXiuSkuInfoDaoBean.deleteSkubySkuId",skuId);
	}

	public String queryWareId(String jdSkuId) {
		Object object = this.getSqlMapClientTemplate().queryForObject("JdXiuSkuInfoDaoBean.queryWareId",jdSkuId);
		if(object == null){
			return "";
		}
		return object.toString();
	}

	public void deleteSkuByWareId(String jdWareId){
		this.getSqlMapClientTemplate().delete("JdXiuSkuInfoDaoBean.deleteSkuByWareId", jdWareId);
	}
	
	public void deleteWareByWareId(String jdWareId){
		this.getSqlMapClientTemplate().delete("JdXiuSkuInfoDaoBean.deleteWareByWareId", jdWareId);
	}
}
