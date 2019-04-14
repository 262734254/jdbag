package com.xiu.jd.dao.ware.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xiu.jd.bean.ware.JDOrderTrack;
import com.xiu.jd.bean.ware.XiuAddress;
import com.xiu.jd.bean.ware.XiuProductInfo;
import com.xiu.jd.dao.BaseDaoImpl;

@Repository("jdOrderTrackDaoBean")
public class JDOrderTrackDaoBean extends BaseDaoImpl<JDOrderTrack> {
	
	public XiuProductInfo queryWareInfo(String sku)throws Exception{
		return (XiuProductInfo) getSqlMapClientTemplate().queryForObject("JDOrderTrackDaoBean.queryWareInfo",sku);
	}
	
	public Integer getStockNum(String wareId)throws Exception{
		return (Integer) super.getSqlMapClientTemplate().queryForObject("JDOrderTrackDaoBean.getStockNum",wareId);
	}
	
	@SuppressWarnings("unchecked")
	public String queryPostcode(XiuAddress xiuAddress)throws Exception{
		List<String> list = super.getSqlMapClientTemplate().queryForList("JDOrderTrackDaoBean.queryPostcode",xiuAddress);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据条件获取 所有JDOrderTrack
	 * @param jdOrderTrack
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JDOrderTrack> queryJDOrderTrack(JDOrderTrack jdOrderTrack)throws Exception{
		return super.getSqlMapClientTemplate().queryForList("JDOrderTrackDaoBean.queryJDOrderTrack",jdOrderTrack);
	}
	
	/**
	 * 根据jdOrderId获取 JDOrderTrack
	 * @param jdOrderId
	 * @return
	 * @throws Exception
	 */
	public JDOrderTrack getJDOrderTrack(String jdOrderId)throws Exception{
		return (JDOrderTrack) super.getSqlMapClientTemplate().queryForObject("JDOrderTrackDaoBean.getJDOrderTrack",jdOrderId);
	}
	
	/**
	 * 添加京东订单与本地同步跟踪表信息
	 * @param jdOrderTrack
	 * @throws Exception
	 */
	public void insertJDOrderTrack(JDOrderTrack jdOrderTrack)throws Exception{
		super.getSqlMapClientTemplate().insert("JDOrderTrackDaoBean.insertJDOrderTrack",jdOrderTrack);
	}
	
	/**
	 * 批量添加京东订单与本地同步跟踪表信息
	 * @param list
	 * @throws Exception
	 */
	public void insertJDOrderTracks(List<JDOrderTrack> list) throws Exception {
		if (list == null || list.isEmpty()) {
			return;
		}
		SqlMapClientTemplate template = super.getSqlMapClientTemplate();
		for (JDOrderTrack bean : list) {
			template.insert("JDOrderTrackDaoBean.insertJDOrderTrack", bean);
		}
		template = null;
	}
	
	/**
	 * 更新京东订单与本地同步跟踪表信息
	 * @param jdOrderTrack
	 * @throws Exception
	 */
	public void updateJDOrderTrack(JDOrderTrack jdOrderTrack)throws Exception{
		super.getSqlMapClientTemplate().update("JDOrderTrackDaoBean.updateJDOrderTrack",jdOrderTrack);
	}
	
	/**
	 * 更新已同步标识
	 * @param list
	 * @throws Exception
	 */
	public void updateJDOrderTrackSync(List<JDOrderTrack> list)throws Exception{
		for (JDOrderTrack jd : list) {
			super.getSqlMapClientTemplate().update("JDOrderTrackDaoBean.updateJDOrderTrackSync",jd);
		}
	}
	
	public String getSupplierCode(String sku)throws Exception{
		return (String) getSqlMapClientTemplate().queryForObject("JDOrderTrackDaoBean.getSupplierCode",sku);
	}

	public void updateJDOrderIssyncplace(JDOrderTrack jdOrderTrack) {
	  getSqlMapClientTemplate().update("JDOrderTrackDaoBean.updateJDOrderIssyncplace",jdOrderTrack);
		
	}
   /**
    * 根据京东订单号，更新购买者手机号
    * @param parames
    * @return
    */
	public int updateUserMobileByOrderId(Map<String, Object> parames) {
	    return  getSqlMapClientTemplate().update("JDOrderTrackDaoBean.updateUserMobileByOrderId",parames);
	}
	/**
	    * 查询走秀地址信息
	    * @param parames
	    * @return
	    */
	public XiuAddress queryXiuAddress(XiuAddress address) {
		XiuAddress xiuaddress=(XiuAddress)  super.getSqlMapClientTemplate().queryForObject("JDOrderTrackDaoBean.queryXiuAddress",address);
		return xiuaddress;
	}
}
