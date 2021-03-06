package com.xiu.jd.dao.ware;

import java.util.List;
import java.util.Map;

import com.xiu.jd.bean.ware.JDAttributeValue;
import com.xiu.jd.bean.ware.XiuJdAttValue;
import com.xiu.jd.dao.BaseDao;

public interface JDAttributeValueDao<T> extends BaseDao<T> {
	
	public T findJdAttributeValue(Map<String,String> params);
	public T findXiuJdAttributeValue(Map<String,String> params);
	public T findEbayJdAttributeValue(Map<String,String> params);
	
	public List<String> queryAttrValueList(Map<String,String> params);
	
	/**
	 * 根据走秀三级分类Id更新jd_xiu_attvalue表
	 * @param jdAttValue
	 */
	public int updateAttributeValueByXiuChildCode(XiuJdAttValue jdAttValue);
	/**
	 * 查找京东销售属性，格式为（aid:vid）
	 * @param params
	 * @return
	 */
	public String queryAidandVid(Map<String,String> params);
	/**
	 * 本地是否存在该属性值
	 * @param paramters
	 * @return
	 */
	public boolean isExistAttrValue(Map<String,Object> paramters);
}
