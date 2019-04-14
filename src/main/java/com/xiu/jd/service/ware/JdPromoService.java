package com.xiu.jd.service.ware;

import java.util.List;
import java.util.Map;

import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JdOrderMode;
import com.xiu.jd.bean.ware.JdPromo;
import com.xiu.jd.bean.ware.JdPromoSku;
import com.xiu.jd.bean.ware.JdPromoSkuRec;

public interface JdPromoService {
	public boolean createPromo(JdPromo jdPromo,JdPromoSku jdPromoSku);
	
	 /**分页查询促销**/
	public  QueryResult<JdPromo> getPageResule(Map<String, Object> parames);
	
	/**
	 * 添加满减促销
	 * @param jdPromo
	 * @return
	 */
	public boolean addCentsOffPromo(JdPromo jdPromo);
	
	/**
	 * 满减促销添加订单规则
	 * @param jdOrderMode
	 * @return
	 */
	public boolean addOrderMode(JdOrderMode jdOrderMode);
	
	/**
	 * 创建完促销以及规则之后添加sku
	 * @param jdPromoSku
	 * @return
	 */
	public boolean addPromoSku(JdPromoSku jdPromoSku);
	
	
    public JdOrderMode getJdOrderMode(Map<String,Object> parames);
	
	public int getJdPromoSkuRecCount(Map<String,Object> parames);
	
	public List<JdPromoSkuRec> getJdPromoSkuRecList(Map<String,Object> parames);
	public void insertJdPromoSkuRec(JdPromoSkuRec jdPromoSkuRec);
	
	public  QueryResult<JdPromoSkuRec> getPageJdSkuRecResule(Map<String, Object> parames);
	
	


}
