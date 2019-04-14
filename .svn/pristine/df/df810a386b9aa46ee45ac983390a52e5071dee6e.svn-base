package com.xiu.jd.dao.ware;

import java.util.List;
import java.util.Map;

import com.xiu.jd.bean.ware.JdOrderMode;
import com.xiu.jd.bean.ware.JdPromo;
import com.xiu.jd.bean.ware.JdPromoSku;
import com.xiu.jd.bean.ware.JdPromoSkuRec;
import com.xiu.jd.dao.BaseDao;

public interface JdPromoDao<T> extends BaseDao<T> {
	
	public void insertPromo(JdPromo jdPromo);
	
	public void insertPromoSku(JdPromoSku jdPromoSku);
	
	
	
	public void insertOrderMode(JdOrderMode orderMode);
	
	public List<JdPromoSku> getJdPromoSkuList(Map<String,Object> parames);
	
	public List<JdPromo> getPromoList(Map<String,Object> params);
	
	
	public JdOrderMode getJdOrderMode(Map<String,Object> parames);
	
	public int getJdPromoSkuRecCount(Map<String,Object> parames);
	
	public List<JdPromoSkuRec> getJdPromoSkuRecList(Map<String,Object> parames);
	public void insertJdPromoSkuRec(JdPromoSkuRec jdPromoSkuRec);
	
	
	

}
