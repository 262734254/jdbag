package com.xiu.jd.service.ware;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDOrderTrack;

public interface JdOrderTrackService {
	/**分页查询订单**/
	 public  QueryResult<JDOrderTrack> getPageResule(Map<String, Object> parames);
	 
	 /**查询订单集合**/
	 public List<JDOrderTrack> getListResult(Map<String, Object> parames);
	 
	 /**导出订单为excel**/
	 public void createOrderWb(HSSFWorkbook wb,List<JDOrderTrack> lists);
}
