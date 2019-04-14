package com.xiu.jd.service.ware.impl;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.OnLineBlackProductBean;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.service.ware.OnLineBlackProductService;

@Service("onLineBlackProductServiceBean")
public class OnLineBlackProductServiceBean extends
		BaseDaoImpl<OnLineBlackProductBean> implements
		OnLineBlackProductService {
	
	

	@Override
	public Object insertBlackProductBeans(final List<OnLineBlackProductBean> blackProductBeans) {

		  return  getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
					int div = 0;
					int batchSize=blackProductBeans.size();
					if(batchSize<=0){
						div=20;
					}else{
						div=batchSize;
					}
					int count = 0;
					executor.startBatch();
					for (OnLineBlackProductBean  blackProductBean:blackProductBeans) {
						executor.insert(getClassSimpleNameSpace()+".insertBlackProductBean", blackProductBean);
						count++;
						if (count == div) {
							executor.executeBatch();
							executor.startBatch();
							count = 0;
						}
					}
					executor.executeBatch();
					return count;
				}
			});
			
		
		
	}

	
	@Override
	public boolean isOnLineBlack(String xiuCode) {
		Object object=this.getSqlMapClientTemplate().queryForObject(this.getClassSimpleNameSpace()+".isOnLineBlack", xiuCode);
		if(object==null){
			return false;//不是黑名单中的商品
		}
		return Integer.parseInt(object.toString())>=1?true:false;
		
	}

	@Override
	public int updateOnLineBlackProductBean(Map<String, Object> parames) {
		return this.getSqlMapClientTemplate().update(this.getClassSimpleNameSpace()+".updateOnLineBlackProductBean", parames);
	}


	@Override
	public QueryResult<OnLineBlackProductBean> getQueryResult(
			Map<String, Object> parames) {
		QueryResult<OnLineBlackProductBean> qr=new QueryResult<OnLineBlackProductBean>();
		long totalRecord=0;
		Object total=this.getSqlMapClientTemplate().queryForObject(this.getClassSimpleNameSpace()+".getQueryResultCount", parames);
		if(total!=null){
			totalRecord=Long.parseLong(total.toString());
			qr.setTotalrecord(totalRecord);
		}
		if(totalRecord>0){
			qr.setResultlist(this.getSqlMapClientTemplate().queryForList(this.getClassSimpleNameSpace()+".getQueryResultList", parames));	
		}
		
		return qr;
	}


	@Override
	public int  updateAllConfrimStatus(Map<String, Object> confrimparames) {
		 return this.getSqlMapClientTemplate().update(this.getClassSimpleNameSpace()+".updateAllConfrimStatus", confrimparames);
		
	}


	@Override
	public boolean xiuCodeIsExits(String xiuCode) {
		Object object=this.getSqlMapClientTemplate().queryForObject(this.getClassSimpleNameSpace()+".xiuCodeIsExits", xiuCode);
		if(object==null){
			return false;//不是黑名单中的商品
		}
		return Integer.parseInt(object.toString())>=1?true:false;
	}
	
	
}
