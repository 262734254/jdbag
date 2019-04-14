package com.xiu.jd.service.log.impl;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.jd.bean.log.JdLog;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.dao.log.impl.JdLogDaoBean;
import com.xiu.jd.service.log.JdLogService;
import com.xiu.jd.utils.ExportExcelUtil;
@Service("jdLogServiceBean")
public class JdLogServiceBean implements JdLogService {
    @Autowired
	private JdLogDaoBean jdLogDaoBean;
  
   public void insert(JdLog entity){
	   jdLogDaoBean.insert(entity);
   }

   @Override
   public boolean isExist(Map<String, Object> parames) {
	   return jdLogDaoBean.isExist(parames);
   }
    
   public  QueryResult<JdLog> getPageResule(Map<String, Object> parames){
	    QueryResult<JdLog> qr=jdLogDaoBean.getPageResule(parames);
	    
	    return qr;
   }
   
   @Override
	public List<JdLog> getListResult(Map<String, Object> parames) {
	   return jdLogDaoBean.getListResult(parames);
	}

   	/**
   	 * 导出日志excel报表
   	 */
	@Override
	public void createWb(List<JdLog> logList, HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		HSSFDataFormat format= wb.createDataFormat();
		style.setDataFormat(format.getFormat("0"));
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		int i = 0;
		int rowIndex = 1;
		for(JdLog jdlog:logList){
			row = sheet.createRow(i+rowIndex);
			//日志类型
			if(jdlog.getLogtype()==1){
				ExportExcelUtil.createCell(row, 0, "sku码不存在");
			}else if(jdlog.getLogtype()==2){
				ExportExcelUtil.createCell(row, 0, "库存");
			}else if(jdlog.getLogtype()==3){
				ExportExcelUtil.createCell(row, 0, "价格");
			}
			//日志描述
			if(jdlog.getDescribe()==null || "".equals(jdlog.getDescribe())){
				ExportExcelUtil.createCell(row, 1, "");
			}else{
				ExportExcelUtil.createCell(row, 1, jdlog.getDescribe());
			}
			//商品id
			if(jdlog.getWareid()==null || "".equals(jdlog.getWareid())){
				ExportExcelUtil.createCell(row, 2, "",style);
			}else{
				ExportExcelUtil.createCell(row, 2, jdlog.getWareid(),style);
			}
			//京东商品sku
			if(jdlog.getXiusn()==null || "".equals(jdlog.getXiusn())){
				ExportExcelUtil.createCell(row, 3, "",style);
			}else{
				ExportExcelUtil.createCell(row, 3, jdlog.getXiusn(),style);
			}
			//创建时间
			if(jdlog.getCreatedate()==null || "".equals(jdlog.getCreatedate())){
				ExportExcelUtil.createCell(row, 4, "");
			}else{
				ExportExcelUtil.createCell(row, 4, jdlog.getCreatedate());
			}
			//更新时间
//			if(jdlog.getUpdatedate()==null || "".equals(jdlog.getUpdatedate())){
//				ExportExcelUtil.createCell(row, 4, "");
//			}else{
//				ExportExcelUtil.createCell(row, 4, jdlog.getUpdatedate());
//			}
			//状态
			if(jdlog.getStatus()==0){
				ExportExcelUtil.createCell(row, 5, "商品sku码不存在");
			}else if(jdlog.getStatus() == 1){
				ExportExcelUtil.createCell(row, 5, "库存同步成功");
			}else if(jdlog.getStatus() == 2){
				ExportExcelUtil.createCell(row, 5, "库存同步失败");
			}else if(jdlog.getStatus() == 3){
				ExportExcelUtil.createCell(row, 5, "价格同步到京东");
			}else if(jdlog.getStatus() == 4){
				ExportExcelUtil.createCell(row, 5, "价格同步失败");
			}else if(jdlog.getStatus() == 5){
				ExportExcelUtil.createCell(row, 5, "不能同步价格");
			}
			
			i++;
		}
	}

	@Override
	public List<JdLog> findAllLogSku() {
		return jdLogDaoBean.findAllLogSku();
	}

	@Override
	public void update(JdLog entity) {
		jdLogDaoBean.update(entity);
	}

	@Override
	public double queryPrice(String skusn) {
		// TODO Auto-generated method stub
		return jdLogDaoBean.queryPrice(skusn);
	}

	public void deleteAll() {
		jdLogDaoBean.deleteAll();
		
	}

}
