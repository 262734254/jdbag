package com.xiu.jd.dao.ware.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiu.jd.bean.ware.JDAttributeValue;
import com.xiu.jd.bean.ware.XiuJdAttValue;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.dao.ware.JDAttributeValueDao;

@Repository("jDAttributeValueDaoBean")
public class JDAttributeValueDaoBean extends BaseDaoImpl<JDAttributeValue> implements JDAttributeValueDao<JDAttributeValue> {
	
	@Override
	public JDAttributeValue findJdAttributeValue(Map<String, String> params) {
		JDAttributeValue t = (JDAttributeValue) this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".findJdAttributeValue", params);
		return t;
	}
	@Override
	public JDAttributeValue findXiuJdAttributeValue(Map<String, String> params) {
		JDAttributeValue t = (JDAttributeValue) this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".findXiuJdAttributeValue", params);
		return t;
	}
	@Override
	public JDAttributeValue findEbayJdAttributeValue(Map<String, String> params) {
		JDAttributeValue t = (JDAttributeValue) this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".findEbayJdAttributeValue", params);
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryAttrValueList(Map<String, String> params) {
		List<String> lists = this.getSqlMapClientTemplate().queryForList(getClassSimpleNameSpace()+".queryAttrValueList", params);
		return lists;
	}
	
	@Override
	public int updateAttributeValueByXiuChildCode(XiuJdAttValue jdAttValue) {
		return this.getSqlMapClientTemplate().update(getClassSimpleNameSpace()+".updateAttributeValueByXiuChildCode", jdAttValue);
		
	}

	@Override
	public String queryAidandVid(Map<String, String> params) {
		Object obj = this.getSqlMapClientTemplate().queryForObject(getClassSimpleNameSpace()+".queryAidandVid", params);
		if(obj == null){
			return "";
		}
		return (String) obj;
	}

	@Override
	public boolean isExistAttrValue(Map<String, Object> paramters) {
		Object object = this.getSqlMapClientTemplate().queryForObject("JDAttributeValueDaoBean.isExistAttrValue", paramters);
		if(object == null){
			return false;
		}
		int count = Integer.parseInt(object.toString());
		return count>0?true:false;
	}



}
