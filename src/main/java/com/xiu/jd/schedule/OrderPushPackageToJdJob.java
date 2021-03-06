package com.xiu.jd.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.order.OrderSopOutstorageRequest;
import com.jd.open.api.sdk.request.order.OverseasOrderSopDeliveryRequest;
import com.jd.open.api.sdk.request.order.OverseasOrderSopOutstorageRequest;
import com.jd.open.api.sdk.response.order.OrderSopOutstorageResponse;
import com.jd.open.api.sdk.response.order.OverseasOrderSopDeliveryResponse;
import com.jd.open.api.sdk.response.order.OverseasOrderSopOutstorageResponse;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDOrderTrack;
import com.xiu.jd.bean.ware.XiuJdLogistics;
import com.xiu.jd.dao.ware.impl.JDWareDaoBean;
import com.xiu.jd.service.OrderService;
import com.xiu.jd.utils.ParseJDOauthProperties;
import com.xiu.jd.utils.ParseProperties;
import com.xiu.osc.client.fx.dto.FxPackage;
import com.xiu.osc.client.fx.dto.FxQuery;
import com.xiu.osc.client.fx.dto.FxResponseOrderBody;
import com.xiu.osc.client.fx.service.FxOrderService;


/**
 * 该定时任务完成以下功能
 * 1.查询本地数据库JD_ORDER_TRACK,将PLACERESULT为 1:推送成功的
 * 订单包裹推送给京东
 * @author liweibiao
 *
 */
public class OrderPushPackageToJdJob extends BaseSchedule {
	private static final Logger logger = Logger.getLogger(OrderPushPackageToJdJob.class);
	
	@Resource(name="orderService")
	private OrderService orderService;
	
	@Resource(name="fxOrderService")
	private FxOrderService fxOrderService;
	
	
	@Resource
	private JDWareDaoBean jDWareDaoBean;
	
	
	public void pushPackageToJd(){
		Map<String, Object> parames=new HashMap<String, Object>();
		parames.put("firstNum", 1);
		int page=pageSize*2;
		parames.put("lastNum", page);
		parames.put("issyncplace", "0");
		parames.put("placeResult", "1");
		QueryResult<JDOrderTrack>  queryResult=orderService.getPageResule(parames);
		if(queryResult!=null){
			int total=(int)queryResult.getTotalrecord();
			List<JDOrderTrack> jdOrderTracks=queryResult.getResultlist();
			if(jdOrderTracks!=null){
				//得到总页数
				int toalPage=getTotalPage(page,total);
				for (int currentPage = 0; currentPage < toalPage; currentPage++) {
					// currentPage = 1不会进去
					if (currentPage != 0) {
						parames=new HashMap<String, Object>();
						parames.put("firstNum",page*currentPage+1);
						parames.put("lastNum",  page*(currentPage+1));
						parames.put("issyncplace", "0");
						parames.put("placeResult", "1");
						queryResult=orderService.getPageResule(parames);
						if(queryResult!=null){
							jdOrderTracks=queryResult.getResultlist();
						}else{
							jdOrderTracks=null;
						}
						
					}
					//总页数
					if(jdOrderTracks!=null && jdOrderTracks.size()>0){
						//Ebay店是海外购
						if("jd_ebay".equalsIgnoreCase(ParseJDOauthProperties.ORDER_PLATFORM_TYPE.trim())){
							pushXiuObjectPackageToJd(jdOrderTracks);
						}else{
							//Xiu店是国内购
							pushXiuObjectPackageToJdFromXiu(jdOrderTracks);
						}
					}
					}
				}
			}
		}
	
	
	
	/**
	 * ebay店的
	 * 推送走秀订单包裹号到京东
	 */
	private void pushXiuObjectPackageToJd(List<JDOrderTrack> jdOrderTracks) {
		logger.info("=========start======推送走秀订单包裹号到京东=====start==========");
		int index = 0;
		int total = jdOrderTracks.size();
		int maxSize = 20;
		int totalPages = getTotalPage(maxSize, total);
		for (int currentpage = 1; currentpage <= totalPages; currentpage++) {
			//List<String> tradeIdList = new ArrayList<String>();
			for (; index < currentpage * maxSize; index++) {
				if (index >= total) {
					break;
				}
				JDOrderTrack jDOrderTrack = jdOrderTracks.get(index);
				List<String> tradeIdList = new ArrayList<String>();
				if (jDOrderTrack != null) {
					tradeIdList.add(jDOrderTrack.getJdOrderId());
				}
				
				if (tradeIdList.size() > 0) {
					FxQuery fxQuery = new FxQuery();
					fxQuery.setStoreId(15);
					fxQuery.setOrderType("JD");
					fxQuery.setAppKey(ParseProperties.getPropertiesValue("remote.url.osc.AppKey"));
					fxQuery.setAppPwd(ParseProperties.getPropertiesValue("remote.url.osc.AppPwd"));
					fxQuery.setTradeIdList(tradeIdList);
					//根据外部ID查询包裹
					FxResponseOrderBody respBody = fxOrderService.queryPackageByJd(fxQuery);
					
					//TODO
				/*	FxResponseOrderBody respBody  = new FxResponseOrderBody();
					List<FxPackage> pkgList1 = new ArrayList<FxPackage>();
					FxPackage p = new FxPackage();
					p.setTradeId("591995686");
					p.setDeliverCode("SF");
					p.setGmtStockOutFinish(new Date());
					p.setOrderId(4041729l);
					p.setOrderStatus(7);
					p.setDeliverNo("1111000000");
					pkgList1.add(p);
					respBody.setSuccess(true);
					respBody.setDefaultModel("packageList", pkgList1);
					*/
					if (respBody != null && respBody.isSuccess()) {
						List<FxPackage> pkgList = (List<FxPackage>) respBody.getModels().get("packageList");
						if (pkgList != null && pkgList.size() > 0) {
							
							// 请求JD Sop出库集合 OrderSopOutstorageRequest   
							List<OverseasOrderSopDeliveryRequest> jdSopReqList = new ArrayList<OverseasOrderSopDeliveryRequest>();
	
							Date tmpStockOutFinish = null;
							for (FxPackage pkg : pkgList) {
								logger.info("从OSC取得的包裹信息" + pkg);
								// 订单完结，下单成功
								if (pkg.getOrderStatus() == 6 || pkg.getOrderStatus() == 7) {
									// 京东 的order ID
									String tradeId = pkg.getTradeId();
									
									logger.info("京东订单ID为:" + tradeId+",包裹状态为:"+pkg.getOrderStatus());
									if (jdSopReqList.size() > 0) {
										OverseasOrderSopDeliveryRequest exsitJdSopReq = jdSopReqList.get(jdSopReqList.size() - 1);
										//取最后一个出库时间的运单号
										if(pkg.getGmtStockOutFinish() != null && pkg.getGmtStockOutFinish().after(tmpStockOutFinish)){
											exsitJdSopReq.setLogisticsId(getJdLogisticsId(pkg.getDeliverCode()));// SF
											exsitJdSopReq.setWaybill(pkg.getDeliverNo());
											tmpStockOutFinish = pkg.getGmtStockOutFinish();//取最新时间
										}
										continue;
									}
									//出库时间为Null 跳出去
									if(pkg.getGmtStockOutFinish() == null) {
										continue;
									}
									tmpStockOutFinish = pkg.getGmtStockOutFinish();
									//海外购订单发货
									OverseasOrderSopDeliveryRequest jdSopReq = new OverseasOrderSopDeliveryRequest();
									jdSopReq.setOrderId(pkg.getTradeId());
									try {
										// 流水号,任意字符串
										 jdSopReq.setTradeNo(jDWareDaoBean.getTradeNo());
									} catch (Exception e) {
										logger.error("京东订单ID为:"+tradeId+ "生成京东的流水号异常" + e.getMessage());
//										e.printStackTrace();
									}
									jdSopReq.setWaybill(pkg.getDeliverNo()); // //
																				// 运单号(当厂家直送时运单号可为空，多个运单号以,分割)
									String deliverCode = pkg.getDeliverCode();
									logger.info("deliverCode=物流配送编码" + deliverCode);
									jdSopReq.setLogisticsId(getJdLogisticsId(pkg.getDeliverCode()));// SF
									jdSopReqList.add(jdSopReq);
								}
							}
							for (OverseasOrderSopDeliveryRequest jdSopReq : jdSopReqList) {
								//出库
								OverseasOrderSopOutstorageResponse response =null;
								// TODO 请求京东APi
	                            //发货
								OverseasOrderSopDeliveryResponse ossResponse = null;
								
								//sop包裹出库
								OverseasOrderSopOutstorageRequest request=new OverseasOrderSopOutstorageRequest();
								request.setOrderId(jdSopReq.getOrderId());
								request.setTradeNo(jdSopReq.getTradeNo());
								logger.info("京东订单ID为:"+jdSopReq.getOrderId()+ "推送给京东的流水号为:"+jdSopReq.getTradeNo());
								try {
									//sop包裹出库
									response=client.execute(request);
									logger.info("京东订单ID为:"+jdSopReq.getOrderId()+ "sop包裹出库");
								} catch (JdException e) {
									logger.error("京东订单ID为:"+jdSopReq.getOrderId()+ "调用京东API,SOP出库接口异常"+e);
									e.printStackTrace();
	
								}
								if (response != null && "0".equals(response.getCode())){
									logger.info("京东订单ID为:"+jdSopReq.getOrderId()+ "sop包裹出库成功");
									try {
										logger.info("京东订单ID为:"+jdSopReq.getOrderId()+ "sop包裹发货");
										//sop包裹发货
										ossResponse = client.execute(jdSopReq);
									} catch (JdException e) {
										logger.error("京东订单ID为:"+jdSopReq.getOrderId()+ "sop包裹发货异常");
										e.printStackTrace();
									} 
							     }else{
							    	 if(response != null){
							    		 if ( "19".equals(response.getCode().trim())){
							    			 return;
							    			 
							    		 }
							    		 
							    	 	 // 京东返回的错误码为:10400001错误描述593244305订单已出库
								    	 if ( "10400001".equals(response.getCode().trim())){
								    		 logger.info("京东订单ID为:"+jdSopReq.getOrderId()+ "sop包裹出库成功,之前已经出库过了，系统通知发货");
												try {
													//sop包裹发货
													ossResponse = client.execute(jdSopReq);
													logger.info("京东订单ID为:"+jdSopReq.getOrderId()+ "sop包裹发货");
												} catch (JdException e) {
													logger.error("sop包裹发货异常");
													e.printStackTrace();
												} 
								    	 } else {
								    		 logger.error("京东订单ID为:"+jdSopReq.getOrderId()+"sop包裹出库失败");
								    		 if (response != null) {
								    			 logger.error("京东订单ID为:"+jdSopReq.getOrderId()+"sop包裹出库,京东返回的错误码为:" + response.getCode() + "错误描述" + response.getZhDesc());
								    		 }
								    	 }
							    		 
							    	 }
							   
							     }
								JDOrderTrack jdOrderTrack = new JDOrderTrack();
								jdOrderTrack.setJdOrderId(jdSopReq.getOrderId());
								if (ossResponse != null && "0".equals(ossResponse.getCode())) {
									logger.error("京东订单ID为:"+jdSopReq.getOrderId()+"调用京东API,SOP发货接口成功,错误描述:"+ ossResponse.getZhDesc());
									jdOrderTrack.setIsSyncPlace("1");
									jdOrderTrack.setPackageFailDesci("包裹出库成功");
	
								} else {
									
									String failDesc=null;
									if (ossResponse != null) {
										if ( "19".equals(response.getCode().trim())){
							    			 return;
							    			 
							    		 }
										
										//10400008错误描述要出库的订单不存在或者不属于当前商家(订单被锁定了)
										 if("10400008".equals(response.getCode())){
											 //订单被锁定了,暂时不能推送包裹
											 jdOrderTrack.setIsSyncPlace("3");
											 jdOrderTrack.setPackageFailDesci("订单被锁定了");
										 }else if("66".equals(response.getCode())){
								    		 jdOrderTrack.setIsSyncPlace("0");
								    		 jdOrderTrack.setPackageFailDesci("");
								    	 }else if("10200005".equals(response.getCode())){
											 jdOrderTrack.setIsSyncPlace("0");
											 jdOrderTrack.setPackageFailDesci("物流编号不正确sop包裹发货,京东返回的错误码为:" + ossResponse.getCode() + "错误描述" + failDesc);
										 }else{
											    jdOrderTrack.setIsSyncPlace("2");
											    failDesc=ossResponse.getZhDesc();
												logger.error("京东订单ID为:"+jdSopReq.getOrderId()+"sop包裹发货,京东返回的错误码为:" + ossResponse.getCode() + "错误描述" + failDesc);
												jdOrderTrack.setPackageFailDesci("sop包裹发货,京东返回的错误码为:" + ossResponse.getCode() + "错误描述" + failDesc);
										 }
									}
								}
								if(response!=null){
									if ( "19".equals(response.getCode().trim())){
						    			 return;
						    			 
						    		 }
									if ("10400010".equals(response.getCode()) || "0".equals(response.getCode())){
										//logger.error("=========调用京东API,SOP系统之前已经发过货了============");
										 jdOrderTrack.setIsSyncPlace("1");
										 jdOrderTrack.setPackageFailDesci("包裹出库成功");
									 }else if("66".equals(response.getCode())){
							    		 jdOrderTrack.setIsSyncPlace("0");
							    		 jdOrderTrack.setPackageFailDesci("");
							    	 }else if("10400008".equals(response.getCode())){
										//10400008错误描述要出库的订单不存在或者不属于当前商家(订单被锁定了)
										 //订单被锁定了,暂时不能推送包裹
										 jdOrderTrack.setIsSyncPlace("3");
										 jdOrderTrack.setPackageFailDesci("订单被锁定了");
									 }else{
										 jdOrderTrack.setPackageFailDesci("sop包裹出库,京东返回的错误码为:" + response.getCode() + "错误描述" + response.getZhDesc());
										 jdOrderTrack.setIsSyncPlace("2");
									 }
									logger.info("要更新的对象为:"+jdOrderTrack);
								}
								
								//包裹推送成功的订单处理时间为此时
								if("1".equals(jdOrderTrack.getIsSyncPlace())){
									jdOrderTrack.setProcesstime(new Date());
									jdOrderTrack.setIsprocess(1);
								}
								orderService.updateJDOrderIssyncplace(jdOrderTrack);
							}
						}
					} else {
						logger.info("osc包裹错误码为:"+respBody.getResultCode()+", 出错信息为:"+respBody.getError());
					}
				}
			}
			logger.info("=========end======推送走秀订单包裹号到京东=====end==========");
		}

	}
	
	
	
	
	/**
	 * xiu店的
	 * 推送走秀订单包裹号到京东
	 */
	@SuppressWarnings("unchecked")
	private void pushXiuObjectPackageToJdFromXiu(List<JDOrderTrack> jdOrderTracks) {
		logger.info("=========start======推送走秀订单包裹号到京东=====start xiu店的==========");
		int index = 0;
		int total = jdOrderTracks.size();
		int maxSize = 20;
		int totalPages = getTotalPage(maxSize, total);
		for (int currentpage = 1; currentpage <= totalPages; currentpage++) {
			//List<String> tradeIdList = new ArrayList<String>();
			for (; index < currentpage * maxSize; index++) {
				if (index >= total) {
					break;
				}
				JDOrderTrack jDOrderTrack = jdOrderTracks.get(index);
				List<String> tradeIdList = new ArrayList<String>();
				if (jDOrderTrack != null) {
					tradeIdList.add(jDOrderTrack.getJdOrderId());
				}
				if (tradeIdList.size() > 0) {
					FxQuery fxQuery = new FxQuery();
					fxQuery.setStoreId(15);
					fxQuery.setOrderType("JD");
					fxQuery.setAppKey(ParseProperties.getPropertiesValue("remote.url.osc.AppKey"));
					fxQuery.setAppPwd(ParseProperties.getPropertiesValue("remote.url.osc.AppPwd"));
					fxQuery.setTradeIdList(tradeIdList);
					//根据外部ID查询包裹
					FxResponseOrderBody respBody = fxOrderService.queryPackageByJd(fxQuery);
					if (respBody != null && respBody.isSuccess()) {
						List<FxPackage> pkgList = (List<FxPackage>) respBody.getModels().get("packageList");
						if (pkgList != null && pkgList.size() > 0) {
							// 请求JD Sop出库集合 OrderSopOutstorageRequest   
							List<OrderSopOutstorageRequest> jdSopReqList = new ArrayList<OrderSopOutstorageRequest>();
							Date tmpStockOutFinish = null;
							for (FxPackage pkg : pkgList) {
								logger.info("xiu店的,从OSC取得的包裹信息" + pkg);
								// 订单完结，下单成功
								if (pkg.getOrderStatus() == 6 || pkg.getOrderStatus() == 7) {
									// 京东 的order ID
									String tradeId = pkg.getTradeId();
									
									logger.info("xiu店的,京东订单ID为:" + tradeId+",包裹状态为:"+pkg.getOrderStatus());
									if (jdSopReqList.size() > 0) {
										OrderSopOutstorageRequest exsitJdSopReq = jdSopReqList.get(jdSopReqList.size() - 1);
										//取最后一个出库时间的运单号
										if(pkg.getGmtStockOutFinish() != null && pkg.getGmtStockOutFinish().after(tmpStockOutFinish)){
											exsitJdSopReq.setLogisticsId(getJdLogisticsId(pkg.getDeliverCode()));// SF
											exsitJdSopReq.setWaybill(pkg.getDeliverNo());
											tmpStockOutFinish = pkg.getGmtStockOutFinish();//取最新时间
										}
										continue;
									}
									//出库时间为Null 跳出去
									if(pkg.getGmtStockOutFinish() == null) {
										continue;
									}
									tmpStockOutFinish = pkg.getGmtStockOutFinish();
									OrderSopOutstorageRequest jdSopReq = new OrderSopOutstorageRequest();
									jdSopReq.setOrderId(pkg.getTradeId());
									try {
										// 流水号,任意字符串
										 jdSopReq.setTradeNo(jDWareDaoBean.getTradeNo());
									} catch (Exception e) {
										logger.error("xiu店的,京东订单ID为:"+tradeId+ "生成京东的流水号异常" + e.getMessage());
									}
									jdSopReq.setWaybill(pkg.getDeliverNo()); // //
									String deliverCode = pkg.getDeliverCode();
									logger.info("xiu店的,deliverCode=物流配送编码" + deliverCode);
									jdSopReq.setLogisticsId(getJdLogisticsId(pkg.getDeliverCode()));// SF
									jdSopReqList.add(jdSopReq);
								}
							}
							for (OrderSopOutstorageRequest jdSopReq : jdSopReqList) {
								
								//op包裹出库，向应体
								OrderSopOutstorageResponse  ossResponse = null;
								
								//sop包裹出库请求体
								OrderSopOutstorageRequest   request=new OrderSopOutstorageRequest();
								request.setOrderId(jdSopReq.getOrderId());
								request.setTradeNo(jdSopReq.getTradeNo());
								request.setLogisticsId(jdSopReq.getLogisticsId());
                                ///运单号
								String wayBill= jdSopReq.getWaybill();
								request.setWaybill(wayBill);
								logger.info("xiu店的,京东订单ID为:"+jdSopReq.getOrderId()+ ",推送给京东的流水号为:"+jdSopReq.getTradeNo()+"物流ID="+jdSopReq.getLogisticsId()+"运单号:"+wayBill );
								try {
									//订单SOP出库
									ossResponse=client.execute(request);
								} catch (JdException e) {
									logger.error("xiu店的,京东订单ID为:"+jdSopReq.getOrderId()+ "调用京东API,SOP出库接口异常");
									e.printStackTrace();
	
								}
								JDOrderTrack jdOrderTrack = new JDOrderTrack();
								jdOrderTrack.setJdOrderId(jdSopReq.getOrderId());
								if(ossResponse != null){
									if ("0".equals(ossResponse.getCode())){
										logger.info("xiu店的,京东订单ID为:"+jdSopReq.getOrderId()+ "sop包裹出库成功");
										jdOrderTrack.setIsSyncPlace("1");
										jdOrderTrack.setPackageFailDesci("包裹出库成功");
								     }else{
								    	 String errorCode=ossResponse.getCode();
								    	 String failDesc=ossResponse.getZhDesc();
								    	  //错误信息要出库的订单被锁定或删除或不存在错误码:10400008
								    	 logger.info("xiu店的,京东订单ID为:"+jdSopReq.getOrderId()+ "sop包裹出库失败,XIU店的错误信息:"+failDesc+"错误码:"+errorCode);
								    	 //TODO
								    	 if("10400008".equals(errorCode)){
								    		 jdOrderTrack.setIsSyncPlace("3");
								    		 jdOrderTrack.setPackageFailDesci("订单被锁定了");
								    		 logger.info("xiu店的,京东订单ID为:"+jdSopReq.getOrderId()+ "订单被锁定或删除");
								    	  }else if("66".equals(errorCode)){
								    		  //sop包裹出库失败,XIU店的错误信息平台连接后端服务不可用错误码:66]
								    		  jdOrderTrack.setIsSyncPlace("0");
									    	  jdOrderTrack.setPackageFailDesci("");
								    	  }else if("10400001".equals(errorCode)){
								    		  // 京东返回的错误码为:10400001错误描述593244305订单已出库
								    		  jdOrderTrack.setIsSyncPlace("1");
									    	  jdOrderTrack.setPackageFailDesci("订单包裹已出库成功");
								    	  }else{
									    		 jdOrderTrack.setIsSyncPlace("2");
									    		 jdOrderTrack.setPackageFailDesci("sop包裹发货,京东返回的错误码为:" + errorCode + "错误描述:" + failDesc);
									     }
								     }
								}else{
									 jdOrderTrack.setIsSyncPlace("2");
									 jdOrderTrack.setPackageFailDesci("sop包裹出库失败");
								}
								
								logger.info("xiu店的,要更新的对象为:"+jdOrderTrack);
								//包裹推送成功的订单处理时间为此时
								if("1".equals(jdOrderTrack.getIsSyncPlace())){
									jdOrderTrack.setProcesstime(new Date());
									jdOrderTrack.setIsprocess(1);
								}
								orderService.updateJDOrderIssyncplace(jdOrderTrack);
							}
						}
					} else {
						logger.info("xiu店的,osc包裹错误码为:"+respBody.getResultCode()+", 出错信息为:"+respBody.getError());
					}
				}
			}
			
			logger.info("=========end======推送走秀订单包裹号到京东=====end==========xiu店的");
		}

	}
	
	/**
	 * 根据osc提供的物流编号,查询京东的物流id
	 * @param oscLogisticsCode
	 * @return
	 */
	private String getJdLogisticsId(String oscLogisticsCode){
		String jdLogisticID=null;
		XiuJdLogistics xiuJdLogistics=null;
		try {
			xiuJdLogistics = jDWareDaoBean.getXiuJdLogistics(oscLogisticsCode);
		} catch (Exception e) {
			logger.error("根据osc返回的物流编号"+oscLogisticsCode+"查询京东物流ID为:"+jdLogisticID+"异常");
			e.printStackTrace();
		}
		if(xiuJdLogistics!=null){
			jdLogisticID= xiuJdLogistics.getJdDeliverId();
		}
		logger.info("oscLogisticsCode编码为 "+oscLogisticsCode+" 京东ID =" +jdLogisticID);
		return jdLogisticID;
	}
	
	public void pushFailPackageToJd(){
		Map<String, Object> parames=new HashMap<String, Object>();
		parames.put("firstNum", 1);
		int page=pageSize*2;
		parames.put("lastNum", page);
		parames.put("issyncplace", "0");
		parames.put("placeResult", "2");
		QueryResult<JDOrderTrack>  queryResult=orderService.getPageResule(parames);
		if(queryResult!=null){
			int total=(int)queryResult.getTotalrecord();
			List<JDOrderTrack> jdOrderTracks=queryResult.getResultlist();
			if(jdOrderTracks!=null){
				//得到总页数
				int toalPage=getTotalPage(page,total);
				for (int currentPage = 0; currentPage < toalPage; currentPage++) {
					// currentPage = 1不会进去
					if (currentPage != 0) {
						parames=new HashMap<String, Object>();
						parames.put("firstNum",page*currentPage+1);
						parames.put("lastNum",  page*(currentPage+1));
						parames.put("issyncplace", "0");
						parames.put("placeResult", "2");
						queryResult=orderService.getPageResule(parames);
						if(queryResult!=null){
							jdOrderTracks=queryResult.getResultlist();
						}else{
							jdOrderTracks=null;
						}
					}
					//总页数
					if(jdOrderTracks!=null && jdOrderTracks.size()>0){
						//Ebay店是海外购
						if("jd_ebay".equalsIgnoreCase(ParseJDOauthProperties.ORDER_PLATFORM_TYPE.trim())){
							pushXiuObjectPackageToJd(jdOrderTracks);
						}else{
							//Xiu店是国内购
							pushXiuObjectPackageToJdFromXiu(jdOrderTracks);
						}
					}
					}
				}
			}
		
	}
}
