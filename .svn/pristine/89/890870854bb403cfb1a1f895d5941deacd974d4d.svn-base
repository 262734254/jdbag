package com.xiu.jd.dao;



import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.xiu.jd.bean.page.QueryResult;


public  abstract class BaseDaoImpl <T> extends SqlMapClientDaoSupport implements BaseDao <T>{
  private static final Logger logger=Logger.getLogger(BaseDaoImpl.class);
  @Autowired
  public void setSqlMapClientBase(SqlMapClient sqlMapClient) {  
      super.setSqlMapClient(sqlMapClient);
  }
  
	@Override
	public int delete(Serializable entityId) {
		String name=getClassSimpleNameSpace();
	    return  this.getSqlMapClientTemplate().delete(name+".delete");
		
	}

	@Override
	public T insert(T entity) {
		String name=getClassSimpleNameSpace();
		Object object=this.getSqlMapClientTemplate().insert(name+".insert", entity);
		//logger.info("name  ---------------->>  "+name);
		if(object!=null){
			logger.info("insert method  return  "+object.toString());
		}
		
		return entity;
	}

	@Override
	public T insertXiu(T entity) {
		String name=getClassSimpleNameSpace();
		Object object=this.getSqlMapClientTemplate().insert(name+".insertXiu", entity);
		//logger.info("name  ---------------->>  "+name);
		if(object!=null){
			logger.info("insert method  return  "+object.toString());
		}
		
		return entity;
	}

	@Override
	public T insertEbay(T entity) {
		String name=getClassSimpleNameSpace();
		Object object=this.getSqlMapClientTemplate().insert(name+".insertEbay", entity);
		//logger.info("name  ---------------->>  "+name);
		if(object!=null){
			logger.info("insert method  return  "+object.toString());
		}
		
		return entity;
	}

	@Override
	public int update(T entity) {
		return this.getSqlMapClientTemplate().update(getClassSimpleNameSpace()+".update", entity);
		
	}
	
	@Override
	public int updateXiu(T entity) {
		return this.getSqlMapClientTemplate().update(getClassSimpleNameSpace()+".updateXiu", entity);
		
	}
	
	@Override
	public int updateEbay(T entity) {
		return this.getSqlMapClientTemplate().update(getClassSimpleNameSpace()+".updateEbay", entity);
		
	}

	@Override
	public T getEntityById(Serializable entityId) {
		return (T)this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".getEntityById",entityId);
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> insertBatch( final List<T> entities ,final int batchSize) {
	   return (List<T>)  getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public List<T> doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				int div = 0;
				if(batchSize<=0){
					div=20;
				}else{
					div=batchSize;
				}
				int count = 0;
				executor.startBatch();
				//logger.info("执行批量insert开始");
				for (T  entity:entities) {
					executor.insert(getClassSimpleNameSpace()+".insertBatch", entity);
					count++;
					if (count == div) {
						executor.executeBatch();
						executor.startBatch();
					//	logger.info("===执行批量insert结束===");
						count = 0;
					}
				}
				executor.executeBatch();
				//logger.info("执行批量insert结束");
				return entities;
			}
		});
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> insertXiuBatch( final List<T> entities ,final int batchSize) {
	   return (List<T>)  getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public List<T> doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				int div = 0;
				if(batchSize<=0){
					div=20;
				}else{
					div=batchSize;
				}
				int count = 0;
				executor.startBatch();
				//logger.info("执行批量insert开始");
				for (T  entity:entities) {
					executor.insert(getClassSimpleNameSpace()+".insertXiuBatch", entity);
					count++;
					if (count == div) {
						executor.executeBatch();
						executor.startBatch();
					//	logger.info("===执行批量insert结束===");
						count = 0;
					}
				}
				executor.executeBatch();
				//logger.info("执行批量insert结束");
				return entities;
			}
		});
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> insertEbayBatch( final List<T> entities ,final int batchSize) {
	   return (List<T>)  getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public List<T> doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				int div = 0;
				if(batchSize<=0){
					div=20;
				}else{
					div=batchSize;
				}
				int count = 0;
				executor.startBatch();
				//logger.info("执行批量insert开始");
				for (T  entity:entities) {
					executor.insert(getClassSimpleNameSpace()+".insertEbayBatch", entity);
					count++;
					if (count == div) {
						executor.executeBatch();
						executor.startBatch();
					//	logger.info("===执行批量insert结束===");
						count = 0;
					}
				}
				executor.executeBatch();
				//logger.info("执行批量insert结束");
				return entities;
			}
		});
		
	}
	
	protected String getClassSimpleNameSpace() {
		
		String name=this.getClass().getSimpleName();
		return name;
	}


	@Override
	public void deleteAll() {
		this.getSqlMapClientTemplate().delete(getClassSimpleNameSpace()+".deleteAll");
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<T> getPageResule(Map<String, Object> parames) {
		 QueryResult<T> qr=new QueryResult<T>();
		 List<T> lists= this.getSqlMapClientTemplate().queryForList(getClassSimpleNameSpace()+".getPageResule", parames);
		 qr.setResultlist(lists);
		 qr.setTotalrecord(this.getCount(parames));
		 return qr;
	}

	@Override
	public long getCount(Map<String, Object> parames) {
		 Object object=this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".getCount",parames);
		 if(object==null){
			 return 0;
		 }
		 return Long.parseLong(object.toString());
	}

	@SuppressWarnings("unchecked")
	public List<T> getListResult(Map<String, Object> parames){
		List<T> lists = this.getSqlMapClientTemplate().queryForList(getClassSimpleNameSpace()+".getListResult", parames);
		return lists;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getXiuListResult(Map<String, Object> parames){
		List<T> lists = this.getSqlMapClientTemplate().queryForList(getClassSimpleNameSpace()+".getXiuListResult", parames);
		return lists;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getEbayListResult(Map<String, Object> parames){
		List<T> lists = this.getSqlMapClientTemplate().queryForList(getClassSimpleNameSpace()+".getEbayListResult", parames);
		return lists;
	}

	@SuppressWarnings("unchecked")
	public T queryResult(Map<String, Object> parames){
		T t = (T) this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".queryResult", parames);
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public T queryXiuResult(Map<String, Object> parames){
		T t = (T) this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".queryXiuResult", parames);
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public T queryEbayResult(Map<String, Object> parames){
		T t = (T) this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".queryEbayResult", parames);
		return t;
	}
	
	public String findResultString(Map<String, Object> parames){
		Object obj = this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".findResultString", parames);
		if(obj == null){
			return "";
		}
		return obj.toString();
	}
	
	public String findXiuResultString(Map<String, Object> parames){
		Object obj = this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".findXiuResultString", parames);
		if(obj == null){
			return "";
		}
		return obj.toString();
	}
	
	
	public String findEbayResultString(Map<String, Object> parames){
		Object obj = this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".findEbayResultString", parames);
		if(obj == null){
			return "";
		}
		return obj.toString();
	}

	
}
