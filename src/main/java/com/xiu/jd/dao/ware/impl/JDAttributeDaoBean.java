package com.xiu.jd.dao.ware.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiu.jd.bean.ware.JDAttribute;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.dao.ware.JDAttributeDao;

@Repository("jDAttributeDaoBean")
public class JDAttributeDaoBean extends BaseDaoImpl<JDAttribute> implements JDAttributeDao<JDAttribute> {

	@Override
	public boolean isExistAttr(Map<String,Object> paramters) {
		Object obj = getSqlMapClientTemplate().queryForObject("JDAttributeDaoBean.isExistAttr",paramters);
		if(obj == null){
			return false;
		}
		int count=Integer.valueOf(obj.toString());
		return count>=1?true:false;
	}

}
