/**
 * 
 */
package com.xiu.jd.web.action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xiu.usermanager.provider.bean.LocalOperatorsDO;

/**
 * 用户登陆拦截器
 * @author user
 *
 */
public class AdminInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	private  final Logger logger = Logger.getLogger(AdminInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		LocalOperatorsDO localOperatorsDO = (LocalOperatorsDO)ServletActionContext.getRequest().getSession().getAttribute("localOperator");
		if (localOperatorsDO == null) {
			logger.error("无效的用户名或密码");
			return "login";
		}
       logger.info("登陆成功");
		return invocation.invoke();
	}

}
