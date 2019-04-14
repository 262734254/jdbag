package com.xiu.jd.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.jd.open.api.sdk.domain.order.ItemInfo;
import com.jd.open.api.sdk.domain.order.OrderResult;
import com.jd.open.api.sdk.domain.order.OrderSearchInfo;
import com.jd.open.api.sdk.domain.order.UserInfo;
import com.jd.open.api.sdk.request.order.OrderSearchRequest;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;
import com.opensymphony.xwork2.Preparable;
import com.xiu.jd.bean.order.JDOrderItemInfo;
import com.xiu.jd.bean.user.JDConsigneenInfo;
import com.xiu.jd.bean.ware.JDOrderTrack;
import com.xiu.jd.dao.ware.impl.JDOrderTrackDaoBean;
import com.xiu.jd.schedule.BaseSchedule;
import com.xiu.jd.service.order.JDOrderItemInfoService;
import com.xiu.jd.service.order.impl.JDOrderItemInfoServiceBean;
import com.xiu.jd.service.user.JDConsigneenInfoService;
import com.xiu.jd.service.user.impl.JDConsigneenInfoServiceBean;

public class InitAction extends BaseSchedule implements  Preparable, ServletRequestAware, ServletResponseAware{
	private static final Logger logger = Logger.getLogger(InitAction.class);
	
	protected HttpServletRequest request;// 请求环境变量

	protected HttpServletResponse response;// 响应环境变量
	
	@Resource(name="jDOrderItemInfoServiceBean")
	private JDOrderItemInfoService jDOrderItemInfoServiceBean;
	
	@Resource(name="jDConsigneenInfoServiceBean")
	private JDConsigneenInfoService jDConsigneenInfoServiceBean;
	
	
	@Resource
	private JDOrderTrackDaoBean jdOrderTrackDao;
	public String init(){

		
	
		long count=jDOrderItemInfoServiceBean.getTotalCount();
		if(count==0){
			
				try {
					int page = 1;
					OrderSearchRequest osRequest = new OrderSearchRequest();
					osRequest.setStartDate(null);
					osRequest.setEndDate(null);
					osRequest.setOrderState("WAIT_SELLER_STOCK_OUT");
					osRequest.setPage(page + "");
					osRequest.setPageSize(pageSize + "");
					osRequest.setOptionalFields(null);
					OrderSearchResponse osResponse = client.execute(osRequest);
					logger.info("================初始化 ================");
				
					if (osResponse != null && "0".equals(osResponse.getCode())) {
						OrderResult orderResult = osResponse.getOrderInfoResult();
						if (orderResult != null) {
							// 取得等待出库总订单总的记录
							int orderTotal = orderResult.getOrderTotal();
							// 计算总页数
							int totalPage = getTotalPage(pageSize, orderTotal);
							logger.info("初始化等待出库订单总的记录数量为：" + orderTotal + "  总的页数为 =" + totalPage + ",  每页显示的记录数为" + pageSize);
							for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
								// currentPage = 1不会进去
								if (currentPage != 1) {
									osRequest = new OrderSearchRequest();
									osRequest.setStartDate(null);
									osRequest.setEndDate(null);
									osRequest.setOrderState("WAIT_SELLER_STOCK_OUT");
									osRequest.setPage(currentPage + "");
									osRequest.setPageSize(pageSize + "");
									osRequest.setOptionalFields(null);
									osResponse = client.execute(osRequest);
									if(osResponse!=null){
										orderResult = osResponse.getOrderInfoResult();
									}
									
									
								}
								logger.info("第" + currentPage + "页");
								if (orderResult != null) {
									logger.info("初始化获取等待出库订单开始");
									List<OrderSearchInfo> orderSearchInfos = orderResult.getOrderInfoList();
									if(orderSearchInfos!=null){
										for(OrderSearchInfo order:orderSearchInfos){
											if(order!=null){
												UserInfo userInfo=order.getConsigneeInfo();
												if(userInfo!=null){
													String mobile=userInfo.getMobile();
													String fullAddress=userInfo.getFullAddress();
													String fullName=userInfo.getFullname();
													
													
													boolean isExists=jDConsigneenInfoServiceBean.isExistsUserMobileNation(mobile);
													String telePhone=userInfo.getTelephone();
													/*System.out.println("收货人地址   :" +fullAddress);
													System.out.println("用户名   :" +userInfo.getFullname());*/
													JDConsigneenInfo entity=new JDConsigneenInfo(fullName, mobile, telePhone, fullAddress);
													
													//true存在，false不存在
								                    if(!isExists){
								                    	//保存购买者信息
								                    	jDConsigneenInfoServiceBean.insert(entity);
													}else{
														//更新收货人地址
														jDConsigneenInfoServiceBean.update(entity);
													}
								                    String orderId=order.getOrderId();
								                    logger.info("京东订单id="+orderId);
								                    JDOrderTrack jdOrderTrack = jdOrderTrackDao.getJDOrderTrack(orderId);
								                    //判断本地订单是否存在
								                    if(jdOrderTrack!=null){
								                    	//存在
								                    	Map<String, Object> parames=new HashMap<String, Object>();
								                    	parames.put("mobile", mobile);
								                    	parames.put("orderId", orderId);
								                    	int num=jdOrderTrackDao.updateUserMobileByOrderId(parames);
								                    	logger.info("根据京东订单号，更新购买者手机号,影响的记录数为"+num);
								                    }

												}else{
													logger.error("收货人信息为空");
												}
												
												List<ItemInfo>  itemInfos=order.getItemInfoList();
												List<JDOrderItemInfo> items=new ArrayList<JDOrderItemInfo>();
												if(itemInfos!=null && itemInfos.size()>0){
													logger.info("==========订单项 start==================");
													for(ItemInfo item:itemInfos){
														JDOrderItemInfo it=new JDOrderItemInfo(order.getOrderId(), item.getSkuId(), item.getOuterSkuId(), 
																item.getSkuName(), item.getJdPrice(), item.getWareId(), item.getItemTotal());
														items.add(it);
													}
													logger.info("==========订单项 end==================");
													//批量保存订单项
													jDOrderItemInfoServiceBean.insertBatch(items, items.size());
												
												}
												
											}
										}
									}
								}
								
								
							}
							
							
						}
					}
				} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
			request.setAttribute("message", "系统初始化完成");
			request.setAttribute("redirectUrl", "/doLogin.action");
			return "message";
		
	
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;

	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
