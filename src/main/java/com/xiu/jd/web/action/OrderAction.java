package com.xiu.jd.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.domain.order.OrderDetailInfo;
import com.jd.open.api.sdk.domain.order.OrderInfo;
import com.jd.open.api.sdk.domain.order.OrderResult;
import com.jd.open.api.sdk.domain.order.PrintResult;
import com.jd.open.api.sdk.request.order.OrderGetRequest;
import com.jd.open.api.sdk.request.order.OrderPrintRequest;
import com.jd.open.api.sdk.request.order.OrderSearchRequest;
import com.jd.open.api.sdk.request.order.OrderSopOutstorageRequest;
import com.jd.open.api.sdk.response.order.OrderGetResponse;
import com.jd.open.api.sdk.response.order.OrderPrintResponse;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;
import com.jd.open.api.sdk.response.order.OrderSopOutstorageResponse;
import com.xiu.jd.bean.order.JDOrderItemInfo;
import com.xiu.jd.bean.page.PageView;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDWareSkuBrand;
import com.xiu.jd.service.order.JDOrderItemInfoService;
import com.xiu.jd.service.ware.JdOrderTrackService;
import com.xiu.jd.utils.CommonUtil;
import com.xiu.jd.utils.ExportExcelUtil;
import com.xiu.jd.utils.ImportExcelUtil;
import com.xiu.jd.utils.ParseJDOauthProperties;
import com.xiu.jd.web.form.JdOrderTrackForm;

public class OrderAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7776065830316155616L;
	private static final Logger logger = Logger.getLogger(OrderAction.class);
	private String orderId;
	private String startDate;
	private String endDate;
	/**
	 * 订单状态 多订单状态可以用逗号隔开 WAIT_SELLER_STOCK_OUT 等待出库 , WAIT_GOODS_RECEIVE_CONFIRM
	 * 等待确认收货, FINISHED_L 完成, TRADE_CANCELED 取消, LOCKED 已锁定（锁定的订单不返回订单详情）
	 */
	private String orderState;//
	/**订单编号集合,用于批量设置处理状态(默认为:0未处理,1:已处理)**/
	private List<String> orderIds;
	
    private String processStatu;

	private String page = "1";
	private String curPageSize = "10";

	private PageView<JDOrderItemInfo> pageView;
	
	private List<JDOrderItemInfo> itemInfos;

	private String path;
	/** 封装查询订单的前台参数 **/
	private JdOrderTrackForm jdOrderTrackForm = new JdOrderTrackForm();
	
	@Resource
	private JdOrderTrackService jdOrderTrackServiceBean;
	
	@Resource(name="jDOrderItemInfoServiceBean")
	private JDOrderItemInfoService jDOrderItemInfoServiceBean;
	
	private String flag;

	/**
	 * 跳转
	 * 
	 * @return
	 */
	public String forward() {
		return super.toView("/WEB-INF/order/" + path + ".jsp");
	}

	/***
	 * 订单管理
	 * @return
	 */
	public String searchOrder() {
		
		try{
			Map<String, Object> parames = jdOrderTrackForm.getJdProductInfoForm();
			pageView = new PageView<JDOrderItemInfo>(this.getPageSize(), this.getCurrentPage());
			parames.put("firstNum", pageView.getFirstResult());
			parames.put("lastNum", pageView.getMaxresult());
			QueryResult<JDOrderItemInfo> qr=jDOrderItemInfoServiceBean.getPageResule(parames);
			pageView.setQueryResult(qr);
			}catch(Exception e){
				logger.error("分页查询订单中订单项和订购人信息异常");
				e.printStackTrace();
			}
			return super.toView("/WEB-INF/order/xiuAndJdorderInfoList.jsp");
	}
	public String searchOrder2() {
		try {
			if (orderId != null && !"".equals(orderId.trim())) {
				return searchOrderByOrderId();
			}
			JdClient client = new DefaultJdClient(ParseJDOauthProperties.JD_SERVER_URL, ParseJDOauthProperties.ACCESS_TOKEN,
					ParseJDOauthProperties.JD_APP_KEY, ParseJDOauthProperties.JD_APP_SECRET);
			OrderSearchRequest osRequest = new OrderSearchRequest();
			osRequest.setStartDate(startDate);
			osRequest.setEndDate(endDate);
			osRequest.setOrderState(orderState);
			osRequest.setPage(page);
			osRequest.setPageSize(curPageSize);
			osRequest
					.setOptionalFields("order_id,order_state,item_info_list,pay_type,order_start_time,order_total_price,order_payment,seller_discount,order_state_remark");
			OrderSearchResponse osResponse = client.execute(osRequest);
			OrderResult orderResult = osResponse.getOrderInfoResult();
			request.setAttribute("totalCount", 0);
			request.setAttribute("totalPage", "0");
			if (orderResult != null) {
				int totalCount = orderResult.getOrderTotal();
				String totalPage = getTotalPage(totalCount, curPageSize) + "";
				request.setAttribute("totalCount", totalCount);
				request.setAttribute("totalPage", totalPage);
				request.setAttribute("orderResult", orderResult);
			}

			if ("WAIT_SELLER_STOCK_OUT".equals(orderState)) {
				request.setAttribute("_sn", "1");
			} else if ("WAIT_GOODS_RECEIVE_CONFIRM".equals(orderState)) {
				request.setAttribute("_sn", "4");
			} else if ("TRADE_CANCELED".equals(orderState)) {
				request.setAttribute("_sn", "6");
			} else if ("FINISHED_L".equals(orderState)) {
				request.setAttribute("_sn", "7");
			} else {
				request.setAttribute("_sn", "-1");
			}
		} catch (Exception e) {
			request.setAttribute("_sn", "-1");
			e.printStackTrace();
		}

		return super.toView("/WEB-INF/order/list.jsp");
	}

	/**
	 * 订单检索页录入订单号时查询单个订单
	 * 
	 * @return
	 */
	public String searchOrderByOrderId() {
		try {
			JdClient client = new DefaultJdClient(ParseJDOauthProperties.JD_SERVER_URL, ParseJDOauthProperties.ACCESS_TOKEN,
					ParseJDOauthProperties.JD_APP_KEY, ParseJDOauthProperties.JD_APP_SECRET);
			OrderGetRequest ogRequest = new OrderGetRequest();
			ogRequest.setOrderId(orderId);
			ogRequest
					.setOptionalFields("order_id,order_state,item_info_list,pay_type,order_start_time,order_total_price,order_payment,seller_discount,order_state_remark");
			OrderGetResponse ogResponse = client.execute(ogRequest);
			OrderDetailInfo orderDetail = ogResponse.getOrderDetailInfo();
			request.setAttribute("totalCount", 0);
			request.setAttribute("totalPage", "0");
			if (orderDetail == null || orderDetail.getOrderInfo() == null) {
				request.setAttribute("_sn", "-1");
				return super.toView("/WEB-INF/order/list.jsp");
			}
			OrderResult orderResult = new OrderResult();
			List list = new ArrayList();
			OrderInfo orderInfo = orderDetail.getOrderInfo();
			list.add(orderInfo);
			orderResult.setOrderInfoList(list);
			orderResult.setOrderTotal(1);
			this.page = "1";
			request.setAttribute("totalCount", 1);
			request.setAttribute("totalPage", "1");
			request.setAttribute("orderResult", orderResult);
			if ("WAIT_SELLER_STOCK_OUT".equals(orderInfo.getOrderState())) {
				request.setAttribute("_sn", "1");
			} else if ("WAIT_GOODS_RECEIVE_CONFIRM".equals(orderInfo.getOrderState())) {
				request.setAttribute("_sn", "4");
			} else if ("TRADE_CANCELED".equals(orderInfo.getOrderState())) {
				request.setAttribute("_sn", "6");
			} else if ("FINISHED_L".equals(orderInfo.getOrderState())) {
				request.setAttribute("_sn", "7");
			} else {
				request.setAttribute("_sn", "-1");
			}
		} catch (Exception e) {
			request.setAttribute("_sn", "-1");
			e.printStackTrace();
		}

		return super.toView("/WEB-INF/order/list.jsp");
	}

	/**
	 * 获取单个订单详情
	 * 
	 * @return
	 */
	public String toGetOrderInfo() {
		try {
			JdClient client = new DefaultJdClient(ParseJDOauthProperties.JD_SERVER_URL, ParseJDOauthProperties.ACCESS_TOKEN,
					ParseJDOauthProperties.JD_APP_KEY, ParseJDOauthProperties.JD_APP_SECRET);
			OrderGetRequest ogRequest = new OrderGetRequest();
			ogRequest.setOrderId(orderId);
			ogRequest.setOptionalFields(null);
			OrderGetResponse ogResponse = client.execute(ogRequest);
			OrderDetailInfo orderDetail = ogResponse.getOrderDetailInfo();
			if (orderDetail != null) {
				OrderInfo orderInfo = orderDetail.getOrderInfo();
				request.setAttribute("orderInfo", orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.toView("/WEB-INF/order/orderInfo.jsp");
	}

	/**
	 * 打印面单
	 * 
	 * @return
	 */
	public String print() {
		try {
			JdClient client = new DefaultJdClient(ParseJDOauthProperties.JD_SERVER_URL, ParseJDOauthProperties.ACCESS_TOKEN,
					ParseJDOauthProperties.JD_APP_KEY, ParseJDOauthProperties.JD_APP_SECRET);
			OrderPrintRequest opRequest = new OrderPrintRequest();
			opRequest.setOrderId(orderId);
			OrderPrintResponse opResponse = client.execute(opRequest);
			PrintResult printResult = opResponse.getPrintResult();
			request.setAttribute("htmlContent", printResult.getHtmlContent());
			System.out.println(printResult.getImageData());
			request.setAttribute("imageData", printResult.getImageData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.toView("/WEB-INF/order/print.jsp");
	}

	/**
	 * 修改订单备注页
	 * 
	 * @return
	 */
	public String modifyOrderRemark() {
		try {
			JdClient client = new DefaultJdClient(ParseJDOauthProperties.JD_SERVER_URL, ParseJDOauthProperties.ACCESS_TOKEN,
					ParseJDOauthProperties.JD_APP_KEY, ParseJDOauthProperties.JD_APP_SECRET);
			// OrderVenderRemarkUpdateRequest ovruRequest = new
			// OrderVenderRemarkUpdateRequest();
			// ovruRequest.setOrderId(orderId);
			// ovruRequest.setRemark(request.getParameter("remark"));
			// orderVenderRemarkUpdateResponse orderVenderRemarkUpdateResponse =
			// client.execute(ovruRequest);
			request.setAttribute("oper_rs", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.toView("/WEB-INF/order/orderRemark.jsp");
	}

	/**
	 * 订单出库
	 * 
	 * @return
	 */
	public String outstorage() {
		try {
			JdClient client = new DefaultJdClient(ParseJDOauthProperties.JD_SERVER_URL, ParseJDOauthProperties.ACCESS_TOKEN,
					ParseJDOauthProperties.JD_APP_KEY, ParseJDOauthProperties.JD_APP_SECRET);
			OrderSopOutstorageRequest osoRequest = new OrderSopOutstorageRequest();
			osoRequest.setOrderId(orderId);
			osoRequest.setLogisticsId(request.getParameter("logisticsId"));
			osoRequest.setTradeNo(request.getParameter("tradeNo"));
			osoRequest.setWaybill(request.getParameter("waybill"));
			OrderSopOutstorageResponse osoResponse = client.execute(osoRequest);
			System.out.println(osoResponse.getCode());
			System.out.println(osoResponse.getMsg());
			if (osoResponse != null && "0".equals(osoResponse.getCode())) {
				request.setAttribute("oper_rs", true);
			} else {
				request.setAttribute("oper_rs", "-1");
			}
		} catch (Exception e) {
			request.setAttribute("oper_rs", "-1");
			e.printStackTrace();
		}

		return super.toView("/WEB-INF/order/outstorage.jsp");
	}

	/** 分页查询订单中订单项和订购人信息 **/
	public String findOrderInfo() {
		try{
		Map<String, Object> parames = jdOrderTrackForm.getJdProductInfoForm();
		pageView = new PageView<JDOrderItemInfo>(this.getPageSize(), this.getCurrentPage());
		parames.put("firstNum", pageView.getFirstResult());
		parames.put("lastNum", pageView.getMaxresult());
		//QueryResult<JDOrderTrack> qr=jdOrderTrackServiceBean.getPageResule(parames);
		QueryResult<JDOrderItemInfo> qr=jDOrderItemInfoServiceBean.getPageResule(parames);
		pageView.setQueryResult(qr);
		}catch(Exception e){
			logger.error("分页查询订单中订单项和订购人信息异常");
			e.printStackTrace();
		}
		return "success";
	}
	
	
	/***
	 * 更新处理状态
	 * @return
	 */
	public String updateProcessState(){
		try{
		Map<String, Object> parames=new HashMap<String, Object>();
		parames.put("processStatu", processStatu);
		  //直接点击处理链接
		  if(orderIds==null){
			  orderIds=new ArrayList<String>();
		  }
		  if(orderId!=null && !"".equals(orderId.trim())){
			orderIds.add(orderId.trim());
		   }
		
		parames.put("orderIds", orderIds);
		int count =jDOrderItemInfoServiceBean.setProcessStatus(parames);
		logger.info("影响的记录数为:"+count);
		}catch(Exception e){
			logger.error("更新处理状态异常");
			e.printStackTrace();
		}
		if(this.flag!=null && "true".equals(this.flag)){
			return searchOrder();
		}
		return findOrderInfo();
	}
	
	/**
	 * 查看订单SKU详情信息(也就是订单项中的信息)
	 * @return
	 */
	public String getOrderSkuInfo(){
		try{
		 itemInfos =jDOrderItemInfoServiceBean.getOrderItemInfoByOrderId(orderId);
		}catch(Exception e){
			logger.error("查看订单SKU详情信息异常");
			e.printStackTrace();
		}
		return "success";
	}
	
	/**导出订单项**/
	public String exportOrderExcel(){
		long startTime=System.currentTimeMillis();
		try {
		
		Map<String, Object> parames = jdOrderTrackForm.getJdProductInfoForm();
		/*
		List<JDOrderItemInfo>  jdOrderItemInfos= jDOrderItemInfoServiceBean.getListResult(parames);
		
		jDOrderItemInfoServiceBean.createOrderWb(wb,jdOrderItemInfos);

		String fileName = "order_item_records_"+CommonUtil.getNowTime()+ ".xls";
	    ExportExcelUtil.downloadExcel(wb, fileName);*/
			parames.put("firstNum", "1");
			int pageSize = 59999;
			parames.put("lastNum", pageSize);
			String whichPath = ServletActionContext.getServletContext().getRealPath("")+ "/template";
			HSSFWorkbook wb = ImportExcelUtil.getWorkbook(whichPath+ "/order_item_records.xls");
			QueryResult<JDOrderItemInfo> qr=jDOrderItemInfoServiceBean.getListResultPage(parames);
			if(qr!=null){
				
				
				long total=qr.getTotalrecord();
				int totalPage = getTotalPage(pageSize, (int) total);
				logger.info("总页数为:" + totalPage);
				for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
					if (currentPage != 1) {
						//parames = new HashMap<String, Object>();
						parames.put("firstNum", pageSize * (currentPage - 1) + 1); 
						parames.put("lastNum", pageSize * currentPage );
						logger.info("firstNum:" +  (pageSize * (currentPage - 1) + 1));
						logger.info("lastNum:" +  (pageSize * currentPage ));
					
						qr = jDOrderItemInfoServiceBean.getListResultPage(parames);
					}
					logger.info("当前页为:" + currentPage);
					if(qr!=null){
						List<JDOrderItemInfo> lists =qr.getResultlist();
						if(lists!=null && lists.size()>0){
							total=qr.getTotalrecord();
							jDOrderItemInfoServiceBean.createOrderWb(wb, lists,currentPage-1,pageSize);
						
						}
					}
					
				}
				
			}
			
			String fileName = "order_item_records_"+CommonUtil.getNowTime()+ ".xls";
		    ExportExcelUtil.downloadExcel(wb, fileName);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出excel出现异常", e);
		}
		long endTime=System.currentTimeMillis();
		long lastTime=(endTime-startTime)/1000 ;
		logger.info("用时"+ lastTime+"秒");
		return null;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getCurPageSize() {
		return curPageSize;
	}

	public void setCurPageSize(String curPageSize) {
		this.curPageSize = curPageSize;
	}

	/**
	 * 获取页数
	 * 
	 * @param total
	 *            记录数
	 * @param p
	 *            每页条数
	 * @return
	 */
	public int getTotalPage(int total, String p) {
		int pageSize = 1;
		try {
			pageSize = Integer.parseInt(p);
		} catch (NumberFormatException e) {
		}
		int size = total / pageSize;
		if ((total % pageSize) != 0) {
			size = size + 1;
		}
		return size;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		if (orderId != null && !"".equals(orderId)) {
			orderId = orderId.trim();
		}
		this.orderId = orderId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public JdOrderTrackForm getJdOrderTrackForm() {
		return jdOrderTrackForm;
	}

	public void setJdOrderTrackForm(JdOrderTrackForm jdOrderTrackForm) {
		this.jdOrderTrackForm = jdOrderTrackForm;
	}

	public PageView<JDOrderItemInfo> getPageView() {
		return pageView;
	}

	public void setOrderIds(List<String> orderIds) {
		/*if(orderIds!=null && orderIds.size()>0){
			for(String id:orderIds){
				logger.info("订单ID为:"+id);
			}
		}*/
		this.orderIds = orderIds;
	}

	
	public void setProcessStatu(String processStatu) {
		this.processStatu = processStatu;
	}

	public List<JDOrderItemInfo> getItemInfos() {
		return itemInfos;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

   
}
