package com.xiu.jd.service.user;

import java.util.List;
import java.util.Map;

import com.xiu.jd.bean.email.JdEmail;
import com.xiu.jd.bean.jdtoken.JdToken;
import com.xiu.jd.bean.user.JDConsigneenInfo;
import com.xiu.jd.dao.BaseDao;

public interface JDConsigneenInfoService extends BaseDao<JDConsigneenInfo> {
    /**
     * 本地数据库是否存在过手机号了
     * @param mobile
     * @return
     */
	public boolean isExistsUserMobileNation(String mobile);
	
	public JdEmail getEmail(int type);
	
	public void updateOderEmailAddress(Map map);
	
	public void updateTokenEmailAddress(Map map);
	
	public List<JdToken> getTokenInfo();
}
