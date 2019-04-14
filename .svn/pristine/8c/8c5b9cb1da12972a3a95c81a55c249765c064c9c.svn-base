package com.xiu.jd.dao.log.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiu.jd.bean.log.JdLog;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.dao.log.JdLogDao;
@Repository("jdLogDaoBean")
public class JdLogDaoBean extends BaseDaoImpl<JdLog> implements JdLogDao<JdLog> {
	
	/**
	 * 判断当前日期日志表中是否存在商品SKU
	 * @param parames
	 * @return flase:不存在 true:存在
	 */
	public boolean isExist(Map<String, Object> parames){
		Object object = this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".isExist",parames);
		if(object == null || object.equals(0)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 查询日志中商品sku
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<JdLog> findAllLogSku(){
		List<JdLog> lists = this.getSqlMapClientTemplate().queryForList(getClassSimpleNameSpace()+".findAllLogSku");
		return lists;
	}
	
	public double queryPrice(String skusn){
		Object price = this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".queryPrice", skusn);
		return (Double) price;
	}
}
