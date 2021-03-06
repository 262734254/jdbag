package com.xiu.jd.service.user.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xiu.jd.bean.email.JdEmail;
import com.xiu.jd.bean.jdtoken.JdToken;
import com.xiu.jd.bean.user.JDConsigneenInfo;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.service.user.JDConsigneenInfoService;

/**
 * ibatis namespace="JDConsigneenInfoServiceBean"
 * @author user
 *
 */
@Service("jDConsigneenInfoServiceBean")
public class JDConsigneenInfoServiceBean extends BaseDaoImpl<JDConsigneenInfo> implements
JDConsigneenInfoService {
	  private static final Logger logger=Logger.getLogger(JDConsigneenInfoServiceBean.class);
	@Override
	public boolean isExistsUserMobileNation(String mobile) {
		Object obj = this.getSqlMapClientTemplate().queryForObject("JDConsigneenInfoServiceBean.isExistsUserMobileNation", mobile);
		boolean flag =false;
		//true存在，false不存在
		if(obj!=null){
			//本地数据库已经存在
			
			int count=Integer.parseInt(obj.toString());
			
			flag =count>0?true:false;
			//"数量为 "+count +",本地数据库已经存在手机号为"+mobile
			logger.info(count>0?"本地数据库已经存在手机号为"+mobile:"本地数据库不存在手机号为"+mobile);
			return flag;
		}
		return flag;
	}
	
	@Override
	public JdEmail getEmail(int type) {
		JdEmail obj = (JdEmail)this.getSqlMapClientTemplate().queryForObject("JDConsigneenInfoServiceBean.getEmail",type);
		return obj;
	}
	@Override
	public void updateOderEmailAddress(Map map) {
		this.getSqlMapClientTemplate().update("JDConsigneenInfoServiceBean.updateOderEmailAddress",map);
		
	}
	@Override
	public void updateTokenEmailAddress(Map map) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().update("JDConsigneenInfoServiceBean.updateTokenEmailAddress",map);
	}
	@Override
	public List<JdToken> getTokenInfo() {
		// TODO Auto-generated method stub
		List<JdToken> tokenList=(List<JdToken>)this.getSqlMapClientTemplate().queryForList("JDConsigneenInfoServiceBean.getJdTokenInfo");
		return tokenList;
	}

	
}
