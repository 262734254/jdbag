package com.xiu.jd.service.log;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.xiu.jd.bean.log.JdLog;
import com.xiu.jd.bean.page.QueryResult;

public interface JdLogService {
	 public void insert(JdLog entity);
	 /**当前日期下日志表中是否存在记录sku表只存在**/
	 public boolean isExist(Map<String, Object> parames);
	 /**分页查询日志**/
	 public  QueryResult<JdLog> getPageResule(Map<String, Object> parames);
	 
	 /**查询日志**/
	 public List<JdLog> getListResult(Map<String, Object> parames);
	 
	 /**导出Excel报表**/
	 public void createWb(List<JdLog> logList,HSSFWorkbook wb);
	 
	 public void deleteAll();
	 
	 /**查询商品sku**/
	 public List<JdLog> findAllLogSku();
	 
	 public void update(JdLog entity);
	 
	 public double queryPrice(String skusn);
	 
	 
}
