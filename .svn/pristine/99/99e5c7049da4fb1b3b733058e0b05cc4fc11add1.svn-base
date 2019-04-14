package com.xiu.jd.web.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.xiu.common.command.result.Result;
import com.xiu.common.command.result.ResultSupport;
import com.xiu.jd.utils.ComparatorPageDO;
import com.xiu.usermanager.provider.UserManagerService;
import com.xiu.usermanager.provider.bean.LocalOperatorsDO;
import com.xiu.usermanager.provider.bean.PageDO;
import com.xiu.usermanager.provider.bean.SystemDO;

/**
 * 用户登录控制处理类
 * 
 * @author tao.huang
 *
 */

public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = -2366824280614573860L;

	private  final Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	@Qualifier(value = "defaultUserManagerHessianServices")
	private UserManagerService user;
	
	private String userName; 
	private String userPwd; 
	private String validateCode; 
	private Result result;
	
	@SuppressWarnings("unused")
	private List<PageDO> labelList = new ArrayList<PageDO>();
	

	public String checkLogin() throws Exception {
		Result result = new ResultSupport();
		result.setSuccess(true);

		if ((StringUtils.isNotBlank(userName))
				&& StringUtils.isNotBlank(userPwd)) {
			return SUCCESS;
		} else {
			return INPUT;
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String systemAdmin() throws Exception {
		labelList = (List<PageDO>) ActionContext.getContext().getSession()
				.get("labelList");
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes" })
	public String doLogin() throws Exception {
		String returnStr = INPUT;
		try {
			result = new ResultSupport();
			result.setSuccess(true);

			// 将必要的个人信息放入session中
			Map resultset = (Map) ActionContext.getContext().getSession();

			if (StringUtils.isBlank(userName)) {
				result.setSuccess(false);
				result.setResultCode("登录名信息错误");
			}
			if (StringUtils.isBlank(userPwd)) {
				result.setSuccess(false);
				result.setResultCode("密码信息错误");
			}

			if (result.isSuccess()) {
				// ops
				SystemDO sysDO = user.getSystemByName("jd_xiu_bag");
				if(sysDO==null){
					log.error("cas页面配置错误，页面管理中的系统名称应该为jd_xiu_bag");
					/*if("liweibiao".equals(userName)){
						LocalOperatorsDO opt=new LocalOperatorsDO();
						SystemDO sys=new SystemDO(55, "liweibiao", "http://localhost");
						
						ServletActionContext.getRequest().getSession().setAttribute("localOperator", opt);
						return SUCCESS;
					}*/
				}
				
				result = user.authenticate(userName, userPwd);
				if (sysDO!=null && result.isSuccess()) {
					LocalOperatorsDO opt = (LocalOperatorsDO) result
							.getDefaultModel();
					// 将用户登入信息放入seesion中
					this.addUserInfoSession(resultset, opt);
					// 获取系统内可访问页面列表
					List<PageDO> pages = user.getPagesBySystem(sysDO.getId(),
							opt.getId());

					ServletActionContext.getRequest().getSession().setAttribute("localOperator", opt);
					ActionContext.getContext().getSession().put("labelList", pages);

					returnStr = SUCCESS;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return returnStr;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addUserInfoSession(Map resultset,
			LocalOperatorsDO localOperatorsDO) {
		resultset.put("USER_NAME", localOperatorsDO.getLoginName());
		resultset.put("USER_ID", localOperatorsDO.getId());
		resultset.put("USER_DEPT_ID", localOperatorsDO.getDeptId());
		resultset.put("USER_JOB_ID", localOperatorsDO.getJobId());
		resultset.put("USER_STATUS", localOperatorsDO.getStatus());
		resultset.put("USER_GRADE", localOperatorsDO.getGrade());
		resultset.put("SYSTEM_USER_ID", "1200");
	}

	public String loginOff() throws Exception {
		result = new ResultSupport();
		result.setSuccess(true);

		return SUCCESS;
	}

	/**
	 * 返回导航列表
	 * 
	 * @param permission
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageDO> initOperatorLabel(Map<String, String> permission)
			throws Exception {
		if (permission == null) {
			return null;
		}
		List<PageDO> pageList = new ArrayList<PageDO>();
		Map<String, String> labelMap = new HashMap<String, String>();
		// 去掉重复的一级导航
		for (Iterator<String> pageKeys = permission.keySet().iterator(); pageKeys
				.hasNext();) {
			String pageKey = pageKeys.next();
			String value = permission.get(pageKey);
			if (value == null) {
				continue;
			}
			String labelArray[] = value.split(";");
			if (labelArray == null) {
				continue;
			}
			for (String str : labelArray) {
				labelMap.put(str, str);
			}
		}
		for (Iterator<String> keys = labelMap.keySet().iterator(); keys
				.hasNext();) {
			String key = keys.next();
			String value = labelMap.get(key);
			String[] tempArray = value.split(":");
			PageDO page = new PageDO();
			page.setSystemPermissionId(Integer.valueOf(tempArray[0]));
			// 去掉不是本系统的页面
			if (1200 != Integer.valueOf(tempArray[0])) {
				continue;
			}
			page.setPageId(Integer.valueOf(tempArray[1]));
			page.setPageUrl(tempArray[2]);
			page.setPageName(tempArray[3]);
			page.setParentId(Integer.valueOf(tempArray[4]));
			pageList.add(page);
		}
		ComparatorPageDO comparator = new ComparatorPageDO();
		Collections.sort(pageList, comparator);
		return pageList;
	}
	
	
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
}
