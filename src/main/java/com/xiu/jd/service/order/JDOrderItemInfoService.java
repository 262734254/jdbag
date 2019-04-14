package com.xiu.jd.service.order;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.xiu.jd.bean.order.JDOrderItemInfo;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.dao.BaseDao;

/**
 * 京东订单项业务接口
 * @author user
 *
 */
public interface JDOrderItemInfoService extends BaseDao<JDOrderItemInfo> {
	/**查询总的记录数**/
	public long getTotalCount();
	
	
   /***
    * 批量设置订单处理状态
    * @param orderIds 订单编号集合,处理状态
    * @return  影响的记录数量
    */
	public int setProcessStatus(Map<String, Object> parames);
    /**
     * 根据订单id查询订单中所订购的商品SKU信息
     * @param orderId
     * @return
     */
    public List<JDOrderItemInfo> getOrderItemInfoByOrderId(String orderId);


    /**导出订单项Excel**/
	public void createOrderWb(HSSFWorkbook wb,
			List<JDOrderItemInfo> jdOrderItemInfos);


	/**
	 * 分页查询
	 *  导出订单项Excel
	 * @param parames
	 * @return
	 */
	public QueryResult<JDOrderItemInfo> getListResultPage(
			Map<String, Object> parames);


	public void createOrderWb(HSSFWorkbook wb, List<JDOrderItemInfo> lists,
			int i, int pageSize);
	
	
	public List<JDOrderItemInfo> getFailOrderItem();
	
	public void updateEmailStatus(Map map);

	

}
