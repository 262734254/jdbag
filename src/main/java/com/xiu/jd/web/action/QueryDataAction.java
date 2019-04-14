package com.xiu.jd.web.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.xiu.jd.bean.page.QueryData;
import com.xiu.jd.service.ware.QueryDataService;

/**
 * 查询数据库中的数据,直接输入sql
 * @author liweibiao
 *
 */
@Scope("prototype")
public class QueryDataAction extends BaseAction {
	
	private static final long serialVersionUID = -1200982401690213432L;
	private  final Logger logger = Logger.getLogger(QueryDataAction.class);
    private String sql;
    
    private Integer count=null;
    
    private String isQueryPage;
  
  @Resource(name = "queryDataServiceBean")
  private QueryDataService queryDataServiceBean;
  
  public String getDataUI(){
	  
	  return SUCCESS;
  }
  
  private QueryData queryData;
  public String getData(){
	  logger.info("SQL为:\n"+sql);
	  String flag="select";
	 String redirectUrl = request.getContextPath() + "/jdWare/getData.action";
	  if(sql!=null && !"".equals(sql.trim())){
		  try {
			  String querySql= sql.replaceAll("\\;", "");
			 
			    //默认是select
				if(!querySql.toLowerCase().trim().startsWith("select")){
					flag="other";
				}
			  queryData=queryDataServiceBean.queryResultData(querySql,flag);
			  if(queryData!=null){
				  if("other".equals(flag)){
					  count= queryData.getCount();
					  if(count!=null){
						  if(count>0){
								 request.setAttribute("message", "操作成功,影响的记录数为:"+count+" 条");
								 request.setAttribute("redirectUrl", redirectUrl);
								 return "message";
							 }else{
									 request.setAttribute("message", "操作失败,影响的记录数为:"+count+" 条");
									 request.setAttribute("redirectUrl", redirectUrl);
									 return "message";
							 }
					  }
					
				  }
			  }
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			if("other".equals(flag)){
				request.setAttribute("message", "操作SQL异常,请检查您输入的SQL语法");
			}else{
				request.setAttribute("message", "查询SQL异常,请检查您输入的SQL语法");
			}
			
			request.setAttribute("redirectUrl", redirectUrl);
			 return "message";
		}
	  }
	  return SUCCESS;
	  
  }

public void setSql(String sql) {
	this.sql = sql;
}

public QueryData getQueryData() {
	return queryData;
}

public String getSql() {
	return sql;
}

public Integer getCount() {
	return count;
}

public void setCount(Integer count) {
	this.count = count;
}

public String getIsQueryPage() {
	System.out.println("isQueryPage=" +isQueryPage);
	return isQueryPage;
}

public void setIsQueryPage(String isQueryPage) {
	this.isQueryPage = isQueryPage;
}



  
 
}
