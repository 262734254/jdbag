package com.xiu.jd.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xiu.jd.bean.page.QueryResult;



public interface BaseDao <T> {
	/**
	 * 根据主键进行删除
	 * @param entityId 实体ID
	 * @return 影响的记录数量
	 */
	public int delete(Serializable entityId);
	/**
	 * 保存实体
	 * @param entity 实体
	 * @return
	 */
	public T insert(T entity);
	
	public T insertXiu(T entity);
	
	public T insertEbay(T entity);
	
	/**
	 * 批量保存
	 * @param entities 实体集合
	 *  @param batchSize 批大小(达到多少数量执行一次批处理) 默认为20条
	 */
	public List<T> insertBatch(final List<T> entities,final int batchSize);
	/**
	 * 批量保存
	 * @param entities 实体集合
	 *  @param batchSize 批大小(达到多少数量执行一次批处理) 默认为20条
	 */
	public List<T> insertEbayBatch(final List<T> entities,final int batchSize);
	/**
	 * 批量保存
	 * @param entities 实体集合
	 *  @param batchSize 批大小(达到多少数量执行一次批处理) 默认为20条
	 */
	public List<T> insertXiuBatch(final List<T> entities,final int batchSize);
	
	/**
	 * 分页查询实体
	 * @param parames 条件
	 * @return 对结果集的封装
	 */
	public QueryResult<T>  getPageResule(Map<String, Object> parames);
	
	/**
	 * 根据条件查询满足条件的记录数量
	 * @param parames 条件
	 * @return满足条件的记录数量
	 */
	public long getCount(Map<String, Object> parames);
	

	
	//更新；
	public int update(T entity);
	//更新；
	public int updateXiu(T entity);
		//更新；
	public int updateEbay(T entity);
	//根据主键ID查询一条数据
	public T getEntityById(Serializable entityId);
	
	public void deleteAll();
	
 
	public List<T> getListResult(Map<String, Object> parames);
	public List<T> getEbayListResult(Map<String, Object> parames);
	public List<T> getXiuListResult(Map<String, Object> parames);
	
	public T queryResult(Map<String, Object> parames);
	public T queryXiuResult(Map<String, Object> parames);
	public T queryEbayResult(Map<String, Object> parames);
	
	public String findResultString(Map<String, Object> parames);
	public String findEbayResultString(Map<String, Object> parames);
	public String findXiuResultString(Map<String, Object> parames);
	 
}
