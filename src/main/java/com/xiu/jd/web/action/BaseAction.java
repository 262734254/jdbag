package com.xiu.jd.web.action;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.xiu.usermanager.provider.bean.LocalOperatorsDO;
 
public class BaseAction extends ActionSupport implements  Preparable, ServletRequestAware, ServletResponseAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2185523461445867603L;
	
	private static final Logger logger=Logger.getLogger(BaseAction.class);

	protected HttpServletRequest request;// 请求环境变量

	protected HttpServletResponse response;// 响应环境变量
	protected String ip;
	public String getIp() {
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("X-Cluster-Client-Ip");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}  


	//当前页;默认为1;
	public int currentPage=1;
	
	//每页行数，默认24; 
	public int pageSize=10;
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		if(currentPage<=0){
			this.currentPage=1;
			return;
		}
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		if(pageSize<=0){
			pageSize=10;
		}
		this.pageSize = pageSize;
	}
 
	public void prepare() throws Exception {
		 
		
	}
 
	public void setServletRequest(HttpServletRequest request) {
		    this.request = request;
	}
	
	public void setServletResponse(HttpServletResponse response) {
	    this.response = response;
	}
	/**获取登陆用户信息(保存在session中)**/
	protected LocalOperatorsDO getLoginUser(){
		return (LocalOperatorsDO) ServletActionContext.getRequest().getSession().getAttribute("localOperator");
		
	}

	private String view;
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String toView(String path){
		view = path;
		return "view";
	}

	
	/**
	 * 输出执行异步操作结果字符
	 * @param str
	 */
	public void returnAjaxStr(String str) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(str);
		out.flush();
		out.close();
	}
	
	/**
	 * 计算总页数
	 * 
	 * @param pageSize
	 * @param total
	 * @return
	 */
	protected int getTotalPage(int pageSize, int total) {
		int totalPage = 0;
		if (total > 0) {
			// 总页数
			totalPage = total % pageSize == 0 ? total / pageSize : total
					/ pageSize + 1;
		}

		return totalPage;
	}
	
}
