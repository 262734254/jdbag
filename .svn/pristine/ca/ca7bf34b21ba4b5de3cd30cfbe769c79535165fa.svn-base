package com.xiu.jd.dao.ware.impl;

import org.springframework.stereotype.Repository;

import com.xiu.jd.bean.ware.JDBatchNum;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.dao.ware.JDBatchNumDao;

@Repository("jDBatchNumDaoBean")
public class JDBatchNumDaoBean extends BaseDaoImpl<JDBatchNum> implements
		JDBatchNumDao<JDBatchNum> {

	@Override
	public long getBatchNum() {
		Object object=this.getSqlMapClientTemplate().queryForObject(this.getClassSimpleNameSpace()+".getBatchNum");
		if(object!=null){
			return Long.valueOf(object.toString());
		}
		return 0;
	}
	
}
