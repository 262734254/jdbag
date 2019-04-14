package com.xiu.jd.service.ware.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.jd.bean.ware.JDBatchNum;
import com.xiu.jd.dao.ware.JDBatchNumDao;
import com.xiu.jd.service.ware.JDBatchNumService;

@Service("jDBatchNumServiceBean")
public class JDBatchNumServiceBean implements JDBatchNumService {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private JDBatchNumDao jDBatchNumDaoBean;
	@SuppressWarnings("unchecked")
	@Override
	public long updateAndSelectBatchNum() {
		JDBatchNum entity=new JDBatchNum(1L);
		jDBatchNumDaoBean.update(entity);
		return jDBatchNumDaoBean.getBatchNum();
		
	}

}
