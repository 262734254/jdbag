package com.xiu.jd.service;

import java.util.List;
import java.util.Map;

import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDOrderTrack;
import com.xiu.jd.bean.ware.XiuAddress;
import com.xiu.jd.bean.ware.XiuProductInfo;

public interface OrderService {

	
	public XiuProductInfo queryWareInfo(String sku)throws Exception;
	
	public Integer getStockNum(String wareId)throws Exception;
	
	public String queryPostcode(XiuAddress xiuAddress)throws Exception;
	
	/**
	 * 根据条件获取 所有JDOrderTrack
	 * @param jdOrderTrack
	 * @return
	 * @throws Exception
	 */
	public List<JDOrderTrack> queryJDOrderTrack(JDOrderTrack jdOrderTrack)throws Exception;
	/**
	 * 根据jdOrderId获取 JDOrderTrack
	 * @param jdOrderId
	 * @return
	 * @throws Exception
	 */
	public JDOrderTrack getJDOrderTrack(String jdOrderId)throws Exception;
	
	/**
	 * 添加京东订单与本地同步跟踪表信息
	 * @param jdOrderTrack
	 * @throws Exception
	 */
	public void insertJDOrderTrack(JDOrderTrack jdOrderTrack)throws Exception;
	
	/**
	 * 批量添加京东订单与本地同步跟踪表信息
	 * @param list
	 * @throws Exception
	 */
	public void insertJDOrderTracks(List<JDOrderTrack> list) throws Exception ;
	
	/**
	 * 更新京东订单与本地同步跟踪表信息
	 * @param jdOrderTrack
	 * @throws Exception
	 */
	public void updateJDOrderTrack(JDOrderTrack jdOrderTrack)throws Exception;
	/**
	 * 更新已同步标识
	 * @param list
	 * @throws Exception
	 */
	public void updateJDOrderTrackSync(List<JDOrderTrack> list)throws Exception;
	
	public String getSupplierCode(String sku)throws Exception;
	public void updateJDOrderIssyncplace(JDOrderTrack jdOrderTrack) ;
   /**
    * 根据京东订单号，更新购买者手机号
    * @param parames
    * @return
    */
	public int updateUserMobileByOrderId(Map<String, Object> parames) ;

   public List<JDOrderTrack> insertBatch(List<JDOrderTrack> jdOrderTrackList, int batchSize);

   public QueryResult<JDOrderTrack> getPageResule(Map<String, Object> parames);

   public XiuAddress queryXiuAddress(XiuAddress address);


}
