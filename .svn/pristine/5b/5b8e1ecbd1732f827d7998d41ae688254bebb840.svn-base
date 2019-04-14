package com.xiu.jd.web.action.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.xiu.jd.bean.log.JdLog;
import com.xiu.jd.bean.page.PageView;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.service.log.JdLogService;
import com.xiu.jd.utils.CommonUtil;
import com.xiu.jd.utils.ExportExcelUtil;
import com.xiu.jd.utils.ImportExcelUtil;
import com.xiu.jd.web.action.BaseAction;
import com.xiu.jd.web.form.LogQueryForm;


/**
 * 日志分页列表显示
 * @author user
 *
 */
@Scope("prototype")
public class LogListAction  extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7119985945823043619L;

	private static final Logger logger=Logger.getLogger(LogListAction.class);

	@Autowired
	private JdLogService jdLogServiceBean;
	
	private PageView<JdLog> pageView;

	/**封装前台查询参数**/
	private LogQueryForm queryForm=new LogQueryForm();
	
	/** 
    * 异步请求结果. 
    */
	private Map<String, Object> ajaxResultMap;
	
	public LogListAction(){
		logger.info("prototype");
	}
	/**
	 * 日志分页列表显示
	 * @return
	 */
	public String logListRecord(){
		long startTime =System.currentTimeMillis();
		try{
		logger.info("Logger logListRecord start ");
		
        Map<String, Object> parames=queryForm.getLogQueryForm();
		pageView=new PageView<JdLog>(this.getPageSize(), this.getCurrentPage());
		parames.put("firstNum", pageView.getFirstResult());
		parames.put("lastNum", pageView.getMaxresult());
		QueryResult<JdLog> qr=jdLogServiceBean.getPageResule(parames);
		pageView.setQueryResult(qr);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("Logger logListRecord Exception "+e);
		}
		logger.info("Logger logListRecord end ");
		long endTime =System.currentTimeMillis();
		logger.info("Logger logListRecord  " +( (endTime-startTime)/1000)+"秒");
		return "success";
	}
	
	/**
	 * 导出日志excel报表
	 * @return
	 * @throws Exception
	 */
	public String exportLogExcel() throws Exception{
		Map<String, Object> parames=queryForm.getLogQueryForm();
		List<JdLog> logList = jdLogServiceBean.getListResult(parames);
		String whichPath = ServletActionContext.getServletContext().getRealPath("")
				+ "/template";
		HSSFWorkbook wb = ImportExcelUtil.getWorkbook(whichPath
				+ "/log_records.xls");
		jdLogServiceBean.createWb(logList,wb);
		String fileName = "log_records_"+CommonUtil.getNowTime()+ ".xls";
    	ExportExcelUtil.downloadExcel(wb, fileName);
    	return null;
	}
	
	/**
	 * 异步查询SKU
	 * @return
	 * @throws Exception
	 */
	public String findAllLogSku() throws Exception{
        ajaxResultMap = new HashMap<String, Object>();
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        List<JdLog> logSkuList = jdLogServiceBean.findAllLogSku();
        
        ajaxResultMap.put("logSkuList", logSkuList);
        return SUCCESS;
    }
	
	public PageView<JdLog> getPageView() {
		return pageView;
	}
	
	public void setQueryForm(LogQueryForm queryForm) {
		this.queryForm = queryForm;
	}
	public LogQueryForm getQueryForm() {
		return queryForm;
	}
	
	public Map<String, Object> getAjaxResultMap() {
        return ajaxResultMap;
    }
    
    public void setAjaxResultMap(Map<String, Object> ajaxResultMap) {
        this.ajaxResultMap = ajaxResultMap;
    }
	
}
