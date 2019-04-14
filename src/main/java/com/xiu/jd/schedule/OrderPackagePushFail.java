package com.xiu.jd.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.order.OrderDetailInfo;
import com.jd.open.api.sdk.domain.order.OrderInfo;
import com.jd.open.api.sdk.request.order.OrderGetRequest;
import com.jd.open.api.sdk.response.order.OrderGetResponse;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDOrderTrack;
import com.xiu.jd.service.OrderService;
import com.xiu.jd.utils.ParseProperties;

/**
 * 推送失败(订单被锁定)的订单包裹
 * @author liweibiao
 *
 */
public class OrderPackagePushFail extends BaseSchedule {
	private static final Logger LOGGER = Logger.getLogger(OrderPackagePushFail.class);
	
	@Resource(name="orderService")
	private OrderService orderService;

	public void getLockedJdOrder(){
		LOGGER.info("===推送失败(订单被锁定)的订单包裹(定时器)==");
		Map<String, Object> parames=new HashMap<String, Object>();
		parames.put("firstNum", 1);
		int page=pageSize*2;
		parames.put("lastNum", page);
		parames.put("issyncplace", "3");//包裹推送到京东失败(3:表示订单被锁住而导致的失败)
		parames.put("placeResult", "1");//订单推送到TM成功
		QueryResult<JDOrderTrack>  queryResult=orderService.getPageResule(parames);
		if(queryResult!=null){
			int total=(int)queryResult.getTotalrecord();
			if(total<=0){
				return;
			}
			List<JDOrderTrack> jdOrderTracks=queryResult.getResultlist();
			if(jdOrderTracks!=null){
				//得到总页数
				int toalPage=getTotalPage(page,total);
				for (int currentPage = 0; currentPage < toalPage; currentPage++) {
					if (currentPage != 0) {
						parames=new HashMap<String, Object>();
						parames.put("firstNum",page*currentPage+1);
						parames.put("lastNum",  page*(currentPage+1));
						parames.put("issyncplace", "3"); //包裹推送到京东失败(3:表示订单被锁住而导致的失败)
						parames.put("placeResult", "1");//订单推送到TM成功
						queryResult=orderService.getPageResule(parames);
						if(queryResult!=null){
							jdOrderTracks=queryResult.getResultlist();
						}else{
							jdOrderTracks=null;
						}
					}
					if(jdOrderTracks!=null && jdOrderTracks.size()>0){
						updateOrderStatus(jdOrderTracks);
					}
					}
				}
			}
	  }
	/**
	 * 更新本地订单包裹状态
	 * @param jdOrderTracks
	 */
	private void updateOrderStatus(List<JDOrderTrack> jdOrderTracks){
		int index = 0;
		int total = jdOrderTracks.size();
		int maxSize = 20;
		int totalPages = getTotalPage(maxSize, total);
		for (int currentpage = 1; currentpage <= totalPages; currentpage++) {
			for (; index < currentpage * maxSize; index++) {
				if (index >= total) {
					break;
				}
				JDOrderTrack jDOrderTrack = jdOrderTracks.get(index);
				if(jDOrderTrack!=null){
					Integer count=jDOrderTrack.getPackageFailCount();
					if(count==null){
						count=0;
					}
					String jdOrderId=jDOrderTrack.getJdOrderId();
					//调用京东接口获取订单，判断订单的状态是否被锁住,如果还是被锁住,订单包裹失败次数累计
					if(jdOrderId!=null){
						String orderStatus=getJdOrderById(jdOrderId);
						LOGGER.info("京东订单号为 :"+jdOrderId+"推送失败(订单被锁定)的订单包裹,订单的状态为:"+orderStatus);
						if(orderStatus!=null){
							JDOrderTrack jdOrder= new JDOrderTrack();
							jdOrder.setJdOrderId(jdOrderId);
							if("LOCKED".equalsIgnoreCase(orderStatus)){
								if(count<ParseProperties.MAX_PACKAGE_FAIL_COUNT){
									count=count+1;
									jdOrder.setPackageFailCount(count);
								}else{
									LOGGER.info("京东订单号为 :"+jdOrderId+"推送失败(订单被锁定)的订单包裹,达到最大失败次数");
									//达到最大失败次数
									jdOrder.setIsSyncPlace("2");
									jdOrder.setPackageFailDesci("推送订单包裹达到最大失败次数");
								}
							}else{
								//订单被取消
								if("TRADE_CANCELED".equalsIgnoreCase(orderStatus)){
									//达到最大失败次数
									jdOrder.setIsSyncPlace("2");
									jdOrder.setPackageFailDesci("推送订单包裹失败订单已被取消了");
								}else{
									//从新把订单的推送状态置为未推送
									jdOrder.setIsSyncPlace("0");
									LOGGER.info("京东订单号为 :"+jdOrderId+"从新把订单的推送状态置为未推");
								}
							}
							orderService.updateJDOrderIssyncplace(jdOrder);
						}
					}
				}
			}
		}
	}
	/**
	 * 根据京东订单ID获取订单状态
	 * @param jdOrderId京东订单ID
	 * @return 订单状态
	 */
	private String getJdOrderById(String jdOrderId){
		OrderGetRequest request = new OrderGetRequest();
		request.setOrderId(jdOrderId);
		request.setOptionalFields("order_id,order_state");
		OrderGetResponse response=null;
		try {
			response = client.execute(request);
		} catch (JdException e) {
			LOGGER.error("京东订单号为 :"+jdOrderId+"推送失败(订单被锁定)的订单包裹异常"+e);
			e.printStackTrace();
			return null;
		}
		if(response!=null){
			OrderDetailInfo orderDetailInfo=response.getOrderDetailInfo();
			if(orderDetailInfo!=null){
				OrderInfo  order= orderDetailInfo.getOrderInfo();
				if(order!=null){
					return order.getOrderState();
				}
			}
		}
		return null;
		
	}
	
}
