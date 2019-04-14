package com.xiu.jd.dao.ware.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiu.jd.bean.ware.JdOrderMode;
import com.xiu.jd.bean.ware.JdPromo;
import com.xiu.jd.bean.ware.JdPromoSku;
import com.xiu.jd.bean.ware.JdPromoSkuRec;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.dao.ware.JdPromoDao;



@Repository("JdPromoDaoBean")
public class JdPromoDaoBean extends BaseDaoImpl<JdPromo>implements JdPromoDao<JdPromo> {

	
	@Override
	public void insertPromo(JdPromo jdPromo) {
		// TODO Auto-generated method stub
		Object obj = getSqlMapClientTemplate().insert("JdPromoDaoBean.insertJdPromo",jdPromo);
		
	}

	@Override
	public void insertPromoSku(JdPromoSku jdPromoSku) {
		Object obj = getSqlMapClientTemplate().insert("JdPromoDaoBean.insertJdPromoSku",jdPromoSku);
		
	}

	@Override
	public List<JdPromo> getPromoList(Map<String, Object> params) {
		List<JdPromo> jdPromoList=new ArrayList<JdPromo>();
		Object obj = getSqlMapClientTemplate().queryForObject("JdPromoDaoBean.getPromoList",params);
		if(obj!=null){
			jdPromoList=(List<JdPromo>) obj;
		}
		return jdPromoList;
	}

	@Override
	public void insertOrderMode(JdOrderMode orderMode) {
		
		Object obj = getSqlMapClientTemplate().insert("JdPromoDaoBean.insertJdOrderMode",orderMode);
		
		
	}

	@Override
	public List<JdPromoSku> getJdPromoSkuList(Map<String, Object> parames) {
		// TODO Auto-generated method stub
		List<JdPromoSku> jdPromoSkuList=new ArrayList<JdPromoSku>();
		Object obj = getSqlMapClientTemplate().queryForObject("JdPromoDaoBean.getPromoSku",parames);
		if(obj!=null){
			jdPromoSkuList=(List<JdPromoSku>) obj;
		}
		return jdPromoSkuList;
	}

	

	@Override
	public int getJdPromoSkuRecCount(Map<String, Object> parames) {
		int count=0;
		Object obj = getSqlMapClientTemplate().queryForObject("JdPromoDaoBean.getJdPromoSkuRecCount",parames);
		if(obj!=null){
			count=(Integer) obj;
		}
		return count;
	}

	@Override
	public List<JdPromoSkuRec> getJdPromoSkuRecList(Map<String, Object> parames) {
		List<JdPromoSkuRec> jdPromoSkuRecList= new ArrayList<JdPromoSkuRec>();
		Object obj = getSqlMapClientTemplate().queryForList("JdPromoDaoBean.getJdPromoSkuRec",parames);
		if(obj!=null){
			jdPromoSkuRecList=(List<JdPromoSkuRec>) obj;
		}
		return jdPromoSkuRecList;
	}

	@Override
	public JdOrderMode getJdOrderMode(Map<String, Object> parames) {
		JdOrderMode jdOrderMode=new JdOrderMode();
		Object obj = getSqlMapClientTemplate().queryForObject("JdPromoDaoBean.getJdOrderMode",parames);
		if(obj!=null){
			jdOrderMode=(JdOrderMode) obj;
		}
		return jdOrderMode;
	}

	@Override
	public void insertJdPromoSkuRec(JdPromoSkuRec jdPromoSkuRec) {
		Object obj = getSqlMapClientTemplate().insert("JdPromoDaoBean.insertJdPromoSkuRec",jdPromoSkuRec);
		
	}
	
	
	

}
