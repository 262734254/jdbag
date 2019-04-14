

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jd.open.api.sdk.domain.order.CouponDetail;
import com.jd.open.api.sdk.domain.order.ItemInfo;
import com.jd.open.api.sdk.domain.order.OrderDetailInfo;
import com.jd.open.api.sdk.domain.order.OrderInfo;
import com.jd.open.api.sdk.domain.order.UserInfo;
import com.jd.open.api.sdk.request.order.OrderGetRequest;
import com.jd.open.api.sdk.response.order.OrderGetResponse;
import com.xiu.commerce.hessian.model.Product;
import com.xiu.commerce.hessian.model.Sku;
import com.xiu.jd.bean.order.JDOrderItemInfo;
import com.xiu.jd.bean.user.JDConsigneenInfo;
import com.xiu.jd.bean.ware.JDAttributeValue;
import com.xiu.jd.bean.ware.JDOrderTrack;
import com.xiu.jd.bean.ware.XiuAddress;
import com.xiu.jd.constants.GlobalConstants;
import com.xiu.jd.schedule.JdCouponCalc;
import com.xiu.jd.service.OrderService;
import com.xiu.jd.service.ProductService;
import com.xiu.jd.service.order.JDOrderItemInfoService;
import com.xiu.jd.service.user.JDConsigneenInfoService;
import com.xiu.jd.service.ware.JDWareService;
import com.xiu.jd.utils.CommonUtil;
import com.xiu.jd.utils.ParseJDOauthProperties;
import com.xiu.jd.utils.ParseProperties;
import com.xiu.osc.client.common.OrderEnum.InventoryType;
import com.xiu.osc.client.common.OrderEnum.PayStatus;
import com.xiu.osc.client.fx.dto.FxDeliverAddr;
import com.xiu.osc.client.fx.dto.FxLog;
import com.xiu.osc.client.fx.dto.FxOrder;
import com.xiu.osc.client.fx.dto.FxOrderActive;
import com.xiu.osc.client.fx.dto.FxOrderDetail;
import com.xiu.osc.client.fx.dto.FxOrderPay;
import com.xiu.osc.client.fx.dto.FxOrderXp;
import com.xiu.osc.client.fx.dto.FxRequestOrderBody;
import com.xiu.osc.client.fx.dto.FxResponseOrderBody;
import com.xiu.osc.client.fx.dto.FxVersion;
import com.xiu.osc.client.fx.service.FxOrderService;

public class JdOrderPushToOSCTest extends BaseTestCase {
	
	@Resource(name="orderService")
	private OrderService orderService;
	
	@Resource(name="jDConsigneenInfoServiceBean")
	private JDConsigneenInfoService jDConsigneenInfoServiceBean;
	
	@Resource(name="jDOrderItemInfoServiceBean")
	private JDOrderItemInfoService jDOrderItemInfoServiceBean;
	
	@Resource(name = "fxOrderService")
	private FxOrderService fxOrderService;
	
	
	@Autowired
	private JDWareService jDWareServiceBean;
	
	/** 商品中心 **/
	@Autowired
	private ProductService productService;
	
	private static int next=1;
	
	@Test
	public void updateJDOrderIssyncplace(){
		JDOrderTrack jdOrderTrack=new JDOrderTrack();
		jdOrderTrack.setJdOrderId("819865828");
		jdOrderTrack.setProcesstime(new Date());
		jdOrderTrack.setIsSyncPlace("0");
		orderService.updateJDOrderIssyncplace(jdOrderTrack);
	}
	
	@Test
	public void pushOrder(){
		
		List<JDOrderTrack> jdOrderTrackList = new ArrayList<JDOrderTrack>();
		// 根据京东订单号查询本地数据库是否已经存在该订单了
		JDOrderTrack jdOrderTrack = null;
		int batchSize = 0;
		String orderId=null;
		OrderInfo  order= null;
		try {
			OrderGetRequest request = new OrderGetRequest();
	
			request.setOrderId("10346615821");
			request.setOptionalFields("order_id,pay_type,order_total_price,order_payment,order_seller_price,freight_price,seller_discount,order_state,consignee_info,item_info_list,coupon_detail_list,pin,balance_used,order_remark,order_start_time");
			OrderGetResponse response=client.execute(request);
			OrderDetailInfo orderDetailInfo=response.getOrderDetailInfo();
			if(orderDetailInfo==null){
				return ;
			}
			 order= orderDetailInfo.getOrderInfo();
			if(order==null){
				return ;
			}
			 orderId=order.getOrderId();
			 if(orderId==null || "".equals(orderId)){
				 return ;
			 }
			logger.info("京东的订单号为:" + orderId);
			jdOrderTrack = orderService.getJDOrderTrack(orderId);
		} catch (Exception e) {
			logger.error("根据京东订单号查询本地数据库是否已经存在该订单了异常" + orderId);
			e.printStackTrace();
		}
		// ----
		if (jdOrderTrack == null) {
			//本地数据库不存在
			jdOrderTrack = new JDOrderTrack();
			jdOrderTrack.setJdOrderId(orderId);
			jdOrderTrack.setPlaceTime(order.getOrderStartTime());
			jdOrderTrack.setPlaceResult("0");
			jdOrderTrack.setIsSyncPlace("0");
			batchSize++;
			jdOrderTrackList.add(jdOrderTrack);
			logger.info("jdOrderTrack对象为: " + jdOrderTrack.toString());
			saveUserInfoAndOrderInfo(order);
		}else{
			UserInfo userInfo=order.getConsigneeInfo();
			if(userInfo!=null){
				Map<String, Object> parames=new HashMap<String, Object>();
				parames.put("mobile", userInfo.getMobile());
            	parames.put("orderId", orderId);
            	int num=orderService.updateUserMobileByOrderId(parames);
            	logger.info("osc根据京东订单号，更新购买者手机号,影响的记录数为"+num);
			}
        	
		}
	
	if (jdOrderTrackList.size() > 0) {
		try {
			// 将本地数据库不存在京东的订单将订单保存到数据库中
			orderService.insertBatch(jdOrderTrackList, batchSize);
		} catch (Exception e) {
			logger.error("将本地数据库不存在京东的订单将订单保存到数据库中异常");
			e.printStackTrace();
		}
	}

	JDOrderTrack tgBean = new JDOrderTrack();
	// 0：未推送给osc
	tgBean.setPlaceResult("0");
	//
	List<JDOrderTrack> tgList = null;
	try {
		// ----//京东订单推送给走秀osc订单结果；0：未推送,1:推送成功,2:推送失败
		tgList = orderService.queryJDOrderTrack(tgBean);
	} catch (Exception e1) {
		logger.error("查询未推送给走秀osc系统的京东订单异常");
		e1.printStackTrace();
	}
	// 没有找到要推送到走秀订单系统的京东订单
	if (tgList != null && tgList.size() >0) {
		for (JDOrderTrack tg : tgList) {
			// 将jd订单信息转为本地订单信息
			FxRequestOrderBody reqBody =null;
			try {
				 reqBody = toLocalOrder(order);
			} catch (Exception e) {
				logger.error("将jd订单信息转为本地订单信息异常");
				tg.setFailDescri("将jd订单信息转为本地订单信息异常");
				e.printStackTrace();
			}
			
			if (reqBody != null) {
				// 请求下单
				FxResponseOrderBody respBody = null;
				try {

					respBody = fxOrderService.orderCapture(reqBody);
					logger.info("[向Osc请求下单]参数：" + reqBody);
				} catch (Exception e) {
					tg.setFailDescri("调用Osc请求下单异常");
					logger.error("调用osc异常");
					e.printStackTrace();
					continue;
				}

				if (respBody != null && respBody.isSuccess()) {
					tg.setLocalOrderId(respBody.getModels().get("orderId").toString());
					logger.info("下单成功");
					logger.info(respBody.getModels().get("orderId"));// 订单Id
					String orderCode = respBody.getModels().get("orderCode").toString();
					logger.info(orderCode);// 订单号
					logger.info(respBody.getModels().get("tradeId"));// 外部交易号(京东订单号)
					tg.setOrderCode(orderCode);
					// 0：未推送,1:推送成功,2:推送失败,3库存扣减失败
					tg.setPlaceResult("1");
					tg.setFailDescri("请求OSC下单成功");

				} else {
					// 结果码
					String code = respBody.getResultCode();
					logger.info("请求osc下单返回的结果码为" + code);
					// 2303 扩展信息外部交易号校验错误
					if (code != null) {
						// / FxReturnCode.cc
						String errorDesc=respBody.getError().toString();
						if ("3002".equals(code)) {
							tg.setPlaceResult("3"); // 库存扣减失败
							tg.setFailDescri("库存扣减失败3002");
							logger.error("---库存扣减失败--3002-,"+errorDesc);
						} else if ("2304".equals(code)) {
							tg.setFailDescri("外部交易已经存在--2304-,"+errorDesc);
							logger.error("---外部交易已经存在--2304-");
							tg.setPlaceResult("1"); // 外部交易已经存在
						} else if ("2303".equals(code)) {
							tg.setFailDescri("扩展信息外部交易号校验错误,订单重复推送--2303-,"+errorDesc);
							logger.error("--扩展信息外部交易号校验错误,订单重复推送--2303-");
							tg.setPlaceResult("2");
						} else {
							
							logger.error("其他失败原因");
							if(respBody!=null){
								tg.setFailDescri("其他失败原因,OSC返回的错误码为:"+code+" "+respBody.getError());
								logger.error("====="+respBody.getError()+"====");
							}
							tg.setPlaceResult("2");
						}
					} else {
						// 返回错误信息
						tg.setFailDescri("osc返回的错误码为null,");
						logger.error("请求下单失败原因\n" + respBody.getError());
						tg.setPlaceResult("2");
					}
				}
			} else {
				tg.setFailDescri("osc返回的FxResponseOrderBody对象为null");
				logger.error("将jd订单信息转为走秀订单出错,需要重新推送的订单号为"+tg.getJdOrderId());
				//0表未推送
				tg.setPlaceResult("0");
			}
			
			
			// 更新订单
			try {// orderCode
				logger.info("要更新的对象为:" + tg);
				orderService.updateJDOrderTrack(tg);

			} catch (Exception e) {
				logger.error("更新订单推送给异常");
				e.printStackTrace();
			}
		}
		
		
	
	}

}

/**
 * 保存用户信息和购物项信息
 * @param order
 */
private void saveUserInfoAndOrderInfo(OrderInfo   order) {
	logger.info("osc保存用户信息和购物项信息start");
	if(order!=null){
		logger.info("京东订单ID  " +order.getOrderId());
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
			
		}else{
			logger.error("osc收货人信息为空");
		}
		
		List<ItemInfo>  itemInfos=order.getItemInfoList();
		List<JDOrderItemInfo> items=new ArrayList<JDOrderItemInfo>();
		if(itemInfos!=null && itemInfos.size()>0){
			logger.info("==========jd 订单项 start==================");
			for(ItemInfo item:itemInfos){
				String orderId=order.getOrderId();
				JDOrderItemInfo it=new JDOrderItemInfo(orderId, item.getSkuId(), item.getOuterSkuId(), 
						item.getSkuName(), item.getJdPrice(), item.getWareId(), item.getItemTotal());
				items.add(it);
			}
			logger.info("==========jd 订单项 end==================");
			//批量保存订单项
			jDOrderItemInfoServiceBean.insertBatch(items, items.size());
		
		}
		
	}
 }


/**
 * 将jd订单信息转为走秀订单
 * 
 * @param orderInfo
 *            jd订单信息
 * @param client
 * @return
 */
private FxRequestOrderBody toLocalOrder(OrderInfo orderInfo) throws Exception {
	if (orderInfo == null) {
		return null;
	}
	UserInfo userInfo = orderInfo.getConsigneeInfo();
	if(userInfo==null){
		logger.error("订单中购买者信息为null,订单号为" +orderInfo.getOrderId()+"订单的状态为:"+orderInfo.getOrderState());
		 userInfo =new UserInfo();


		 userInfo.setProvince("北京");
		 
		 userInfo.setCity("丰台区");
		 userInfo.setCounty("三环到四环之间");
		 userInfo.setFullAddress("北京丰台区三环到四环之间北京市丰台区72号院");
		 userInfo.setMobile("15801065643");
		 userInfo.setTelephone("15801065643");
		 userInfo.setFullname("宋方成");
		//return null;
	}
	List<ItemInfo> itemInfoList = orderInfo.getItemInfoList();// 获取商品信息

	FxRequestOrderBody reqBody = new FxRequestOrderBody();
	long buyerId = ParseProperties.getPropertiesLongValue("remote.url.osc.buyerId");
	String buyerName = ParseProperties.getPropertiesValue("remote.url.osc.buyerName");
	// ============================订单主体头信息============================
	FxOrder order = new FxOrder();
	order.setBuyerEmail(null);
	order.setBuyerId(buyerId);
	order.setBuyerMessage(orderInfo.getOrderRemark());
	order.setBuyerNick(buyerName);
	order.setCompensationFee(0);
	order.setCouponPayShip(0);
	order.setCreator(buyerName);
	order.setCustId(buyerId);
     //已确认收到金额(用户通过支付宝支付的金额，不包含京东的余额支付，礼品卡，优惠劵)
	//TODO XXX 需要修改。礼品卡+余额支付算+平台优惠劵算+客人支付
	//用户在京东上总的支付金额(礼品卡+余额支付算+平台优惠劵算+客人支付)
	
	long jdOrderTotalPrice=toLongMoney("京东订单总金额ItemAmount", orderInfo.getOrderTotalPrice());//京东订单总金额
	long jdFreightPrice=toLongMoney("运费FreightPrice",orderInfo.getFreightPrice());//运费
	long sellerDiscount=toLongMoney("商家总的优惠DiscountFee1", orderInfo.getSellerDiscount());//商家总的优惠
	long orderPayment=toLongMoney("用户应付金额ConfirmPaidFee", orderInfo.getOrderPayment());//用户应付金额
	long balanceUsed=toLongMoney("余额支付DiscountFee1", orderInfo.getBalanceUsed()); //余额支付
	//用户在京东上总的支付金额(包含:礼品卡+余额支付算+平台优惠劵算+客人支付)
	//long userTotalPayPrice=orderPayment+balanceUsed;
	long userTotalPayPrice=getUserTotalPayPrice(orderInfo.getCouponDetailList(),orderPayment,balanceUsed);
	logger.info("userToablPayPrice="+userTotalPayPrice);
	order.setConfirmPaidFee(userTotalPayPrice);//已确认收到金额
	
	
	// 折扣金额1 (商家总的优惠)
	order.setDiscountFee1(sellerDiscount);
	order.setDiscountFee2(0l);
	order.setDiscountFee3(0l);
	order.setItemAmount(jdOrderTotalPrice);// 商品总金额                     todo订单总金额-商品的运费
	order.setPayAmount(jdOrderTotalPrice+jdFreightPrice);
/*	order.setPayAmount(toLongMoney("PayAmount",String.valueOf(Double.valueOf(orderInfo.getOrderTotalPrice()) + Double.valueOf(orderInfo.getFreightPrice()))));*/
	order.setPostFee(toIntMoney("PostFee", orderInfo.getFreightPrice()));
	order.setEbsProcessStatus(0);// 是否需要出库（0需要，1不需要）
	order.setFeatures(null);
	order.setIp(null);
	//还应支付总金额(还要支付的金额)
	order.setNotAmount(0l);// 还应支付总金额 不抓COD单情况下为0
	order.setOrderFrom(1);// 订单来源 1PC 2:MOBILE 3:WAP
	order.setOrderStatus(2);// 订单状态 1-未审核,4-撤销
							// 2-已审核,,3-待审核,2-已审核,5-审核不通过,6-交易完结,7-订单完结
	order.setParentCode(null);// ?
	order.setPayStatus(1);// 订单支付状态 0：未到账 1：已到账
	order.setPayType(ParseProperties.PAY_TYPE);//支付类型
	if("jd_xiu".equalsIgnoreCase(ParseJDOauthProperties.ORDER_PLATFORM_TYPE)){
		order.setPayType(ParseProperties.PAY_TYPE);
	}else{
		order.setPayType("JDPAY");
	}
	order.setPayTypeCat("PAY_ONLINE");//支付方式大类
	//订单总商品总数量
	int quantity = 0;
	for (ItemInfo itenInfo : itemInfoList) {
		quantity += Integer.parseInt(itenInfo.getItemTotal());
	}
	order.setQuantity(quantity);
	order.setStoreId(ParseProperties.STORE_ID);
	order.setStoreName(ParseProperties.STORE_NAME);

	// ============================订单配送信息  ============================
	FxDeliverAddr fxDeliveAddr = new FxDeliverAddr();
	fxDeliveAddr.setDeliveryComment(orderInfo.getOrderRemark()); // 订单中的备注信息
	fxDeliveAddr.setDeliveryTimePreference("deliverTime_01");// 发货说明mandy确定写死
	fxDeliveAddr.setAddress(userInfo.getFullAddress());// 获取订单中购买者的信息
	fxDeliveAddr.setFullName(userInfo.getFullname());
	fxDeliveAddr.setMobile(userInfo.getMobile());
	fxDeliveAddr.setPhone(userInfo.getTelephone());
	String province = userInfo.getProvince();
	String city = userInfo.getCity();
	String county = userInfo.getCounty();
	String postCode =null;
	 //直辖区
	if(province.startsWith("上海") || province.startsWith("北京")||province.startsWith("重庆")||province.startsWith("天津")){
		// 根据收货人信息中的配送地址信息查询邮编
		XiuAddress address = new XiuAddress(province, city);
		postCode = orderService.queryPostcode(address);
		fxDeliveAddr.setProvName(province+"市");
		fxDeliveAddr.setCityName(province+"市区");
		fxDeliveAddr.setAreaName(county);
		if (postCode == null || "".equals(postCode.trim())) {
			address = new XiuAddress(province, city.substring(0, city.length() - 1));
			postCode = orderService.queryPostcode(address);
		}
	 }else{
		XiuAddress address = new XiuAddress(province, city,county);
		postCode = orderService.queryPostcode(address);
		if (postCode == null || "".equals(postCode.trim())) {
			address = new XiuAddress();
			address.setProvince(province);
			address.setCity(city.substring(0, city.length() - 1));
			postCode = orderService.queryPostcode(address);
		}
		//自治区的
		if(province.startsWith("西藏") || province.startsWith("内蒙")){
			province=province+"自治区";
		}
		else if(province.startsWith("新疆")){
			province=province+"维吾尔自治区";
		}
		else if(province.startsWith("广西")){
			province=province+"壮族自治区 ";
		}
		else if(province.startsWith("宁夏")){
			province=province+"回族自治区 ";
		}else{
			province=province+"省";
		}
		
		fxDeliveAddr.setProvName(province);
		fxDeliveAddr.setCityName(city);
		fxDeliveAddr.setAreaName(county);
	}
	if (postCode == null || "".equals(postCode.trim())) {
		postCode = "518000";
		logger.info("Jd订单号为" +orderInfo.getOrderId()+"沙箱环境没有获取到，使用模拟数据的邮编" + postCode);
	}
	fxDeliveAddr.setPostCode(postCode);
	logger.info("Jd订单号为" +orderInfo.getOrderId()+"最后得到的邮编为:" + postCode);
	fxDeliveAddr.setSplitOrder(1);// 是否拆单 1拆单 0表示不拆单

	// ============================订单操作日志信息============================
	FxLog log = new FxLog();
	log.setOperatorId(0l);
	log.setOperatorIp(null);
	log.setOperatorName(buyerName);
	

	// ============================分销扩展信息============================
	FxOrderXp orderXp = new FxOrderXp();
	orderXp.setCreator(buyerName);
	orderXp.setCreatorId(buyerId);
	orderXp.setGmtCreate(CommonUtil.getDate(orderInfo.getOrderStartTime(), "yyyy-MM-dd HH:mm:ss"));
	orderXp.setIsPulled("N");
	orderXp.setOrderType("JD");
	orderXp.setTradeId(orderInfo.getOrderId());// 外部交易也就是京东的订单号

	//============================活动设置============================
	List<FxOrderActive> activeList = new ArrayList<FxOrderActive>();// 计算活动设置对象是什么 

	//============================订单商品行数据设置============================
	List<FxOrderDetail> detailList = new ArrayList<FxOrderDetail>();// 订单商品信息
	int itemCount=0;
	List<String> proudctCoupon =new ArrayList<String>();//不为空,就是商品级别的优惠
	for (ItemInfo itemInfo : itemInfoList) {
		itemCount++;
		String wareId = itemInfo.getWareId();
		/*String outerSkuId = itemInfo.getOuterSkuId();*/
		String outerSkuId = "101629430001";
		if (outerSkuId != null && !"".equals(outerSkuId.trim())) {
			//Product product = productService.loadProduct(outerSkuId.trim().substring(0, 8));
			Product product =new Product();
			product= productService.loadProduct("10162943");
		/*	// TODO
			// xiuProductInfo = new XiuProductInfo();
			// product = new Product();
			// product.setInnerID(100l);
			// product.setBrandCode("C3331");
			// product.setXiuSN("C111111");
			// itemInfo.setOuterSkuId("C1111110001");
			product.setBCatCode("100805");
			product.setBrandCode("US1364x");
			product.setBCatName("耐克");
			product.setGlobalFlag("1");
			//product.setSkus(new String[]{"a"});
			//product.setSkus(new String[]{"a","b","c","d","e"});
			if(next==1){
			product.setXiuSN("10162943");
			}else{
				product.setXiuSN("10162943");
			}
			product.setPrdName("JOE'S JEANS 男牛仔长裤 尺码【33x34】（美国直发） ");
			product.setInnerID(16624L);
			product.setSupplierCode("1528");
			product.setMasterImgUrl("http://images.xiu.com/goods20120304/"+outerSkuId.trim().substring(0, 8)+"/"+outerSkuId+"200_200.jpg");
			product.setPrdOfferPrice(746.0);*/
			/*if (product == null) {
				logger.info("京东商品id为：" + wareId + ",商品走秀码为 " + outerSkuId.substring(0, 8)
						+ "在本地x_data_prd_info表中找不到有相关记录匹配,可能是环境问题");
				continue;
			}*/
			logger.info(product.toString());
			FxOrderDetail detail = new FxOrderDetail();
			// 品牌编码
			detail.setBrandCode(product.getBrandCode());

			// 基本分类ID(第四级分类编码)
			detail.setCatCode(product.getBCatCode());// 走秀4级类目ID
			// 基础分类名称
			detail.setCatName(product.getBCatName());
			// 发送方式：默认0:普通商品 1:直发 2:全球速递 3:香港速递 4供应商直发 5全球供应商直发(目前为草莓供应商直发)
			int deliverType=0;
			String globalFlag=product.getGlobalFlag();
			if(globalFlag!=null && !"".equals(globalFlag.trim())){
				try{
				deliverType= Integer.valueOf(globalFlag);
				}catch(Exception e){
					deliverType=0;
					logger.error("解析全球速递字段globalFlag异常,值为:"+globalFlag);
					e.printStackTrace();
				}
			}
			detail.setDeliverType(deliverType);

			// ==================================================商品优惠金额================================================================================
			    detail.setInventoryType(InventoryType.INV_CHANNEL.getKey());//3:渠道库存
			detail.setIsSupportCod("0");// 是否支持cod 1支持COD，非1不支持.默认不支持COD

			detail.setItemCodes(product.getXiuSN());// 取走秀码
			detail.setItemId(product.getInnerID());// 取走秀码对应的商品ID
			detail.setItemName(product.getPrdName());
			// 商品类型1 秒杀流程 2 （活动）名品特卖 3 预售流程 4 （活动）奢华会 5 普通购物流程 6 分期付款流程 7
			// 代下订单';.
			detail.setItemType(5);//
			// 赠品 0表示不是赠品、1表示赠品
			detail.setLargess(0);//
			detail.setLevelLuxury(0);// 是否是奢侈品 1是 0否(默认否)
			// 渠道价.如官网的定价、名品的定价、奢华惠的定价等...
			detail.setOriginalPrice(toLongMoney("长整形 OriginalPrice", itemInfo.getJdPrice()));// 取京东的商品原价
			detail.setQuantity(Integer.parseInt(itemInfo.getItemTotal()));

			

			Double productXiuPrice=product.getPrdOfferPrice(); //单位为元   走秀价
			detail.setBasePrice(toLongMoney("Double走秀价 单位为元 ",productXiuPrice+""));
			// 计算商品优惠金额和分摊价 todo
			
			Map<String, Object> calcMap = this.calcItemAmount(orderInfo.getCouponDetailList(),itemInfo.getSkuId(), order.getItemAmount(),
					order.getDiscountFee1(), detail.getOriginalPrice(), detail.getQuantity(), product.getXiuSN(), activeList,proudctCoupon);
			long sharePrice = detail.getOriginalPrice();
			long favorableAmount = 0;
			if (calcMap != null && calcMap.size() > 0) {
				//订单中商品分摊价
				sharePrice = (Long) calcMap.get(GlobalConstants.OSC_ORDER_PRICE_ITEM_SHARE);
				//商品优惠金额
				favorableAmount = (Long) calcMap.get(GlobalConstants.OSC_ORDER_PRICE_ITEM_FAV1);
			}

			detail.setFavorableAmount(favorableAmount);// 商品优惠金额
			detail.setSharePrice(sharePrice);// 分摊价格
			detail.setFavorableAmount2(0l);
			detail.setFavorableAmount3(0l);
			// 商品类型 0、非C， 1C'
			detail.setProductType(1);//
			//
			String imageURL = product.getMasterImgUrl();
			String mainImagePath = "";
			if (product != null && imageURL != null && imageURL.contains("upload")) {
				// detail.setPicturePath(wareGetResponse.getWare().getLogo());
				String imageName = ParseProperties.IMAGE_SIZE.replaceAll("220", "80");
				mainImagePath = imageURL.substring(imageURL.indexOf("upload"), imageURL.length()) + "/" + imageName;
			}
			logger.info("商品主图路径" + mainImagePath);
			detail.setPicturePath(mainImagePath);
			Sku[] skus = product.getSkus();
			
			Sku sku = null;
			if (skus != null) {
				for (Sku s : skus) {
					String skuSn = s.getSkuSN();
					skuSn="101629430001";
					if (outerSkuId.equals(skuSn)) {
						sku = s;
						break;
					}
				}
			}
			sku.setXiuSN("10162943");
			
			if(sku==null){
				logger.error("京东中商品sku在商品中心不存在,京东的sku码为"+outerSkuId);
			}
			StringBuffer sb = new StringBuffer();
			if (sku != null) {
				sb.append("颜色:" + sku.getColorValue() + ";尺寸:" + sku.getSizeValue() + ";");
			}
			// TODO
			if(next==1){
				if(sku==null){
					sb.append("颜色:红色"   + ";尺寸:F"  + ";");
				}
				detail.setSkuCode("000000010001");
				next++;
			}else{
				sb.append("颜色:绿色"   + ";尺寸:XL"  + ";");
				detail.setSkuCode("000000020001");
			}
			
			sb.append("品牌:" + product.getBrandName() + ";品类:" + product.getBCatName() + "");
			detail.setPropertyAlias(sb.toString());

			// =============================================分摊价格 TODO========================================================================
			//detail.setSkuCode(itemInfo.getOuterSkuId());
			// 平台商编码（ebay和菲拉格慕的需要提供）jdOrderTrackDao.getSupplierCode(outerSkuId.trim()
			String supplierCode=product.getSupplierCode();
			if(supplierCode==null){
				supplierCode="";
			}
			detail.setSupplierCode(supplierCode);
			detailList.add(detail);
		}

	}
	// ============================订单支付信息============================
	List<FxOrderPay> payList = new ArrayList<FxOrderPay>();
	FxOrderPay pay = new FxOrderPay();
	pay.setFlowCode(orderXp.getTradeId());
	pay.setNotAmount(toLongMoney("长整数 NotAmount 订单总金额 ", orderInfo.getOrderTotalPrice()));  //订单总的支付金额
	//TODO XXX 以下两个设置和订单支付金额一致即可
	//参考->order.setConfirmPaidFee(toLongMoney("ConfirmPaidFee", orderInfo.getOrderPayment()));
	pay.setPagePayAmount( (int)userTotalPayPrice);// 用户应付金额(页面展示的支付金额)
	//pay.setPagePayAmount(toIntMoney("整数  OrderPayment  用户应付金额 ", orderInfo.getOrderPayment()));
	pay.setPayAmount(userTotalPayPrice); // 用户应付金额
	//pay.setPayAmount(toLongMoney("长整数 PayAmount 用户应付金额", orderInfo.getOrderPayment()));
	//走秀对接京东
	if("jd_xiu".equalsIgnoreCase(ParseJDOauthProperties.ORDER_PLATFORM_TYPE)){
		pay.setPayType(ParseProperties.PAY_TYPE);
		pay.setPayTitle("京东支付-xiu");
	}else{
		//ebay对接京东
		pay.setPayType("JDPAY");
		pay.setPayTitle("京东支付");
	}
	pay.setPayTypeCat("PAY_ONLINE");
	pay.setStatus(PayStatus.PAY.getKey());
	payList.add(pay);

	// ============================版本信息============================
	reqBody.setFxVersion(FxVersion.VERSION_1);

	//组装请求数据 
	reqBody.setAppKey(ParseProperties.getPropertiesValue("remote.url.osc.AppKey"));
	reqBody.setAppPwd(ParseProperties.getPropertiesValue("remote.url.osc.AppPwd"));
	reqBody.setCustomizedInfo(null);
	reqBody.setFxDeliverAddr(fxDeliveAddr);
	reqBody.setFxLog(log);
	reqBody.setFxOrder(order);
	reqBody.setFxOrderActiveList(activeList);
	reqBody.setFxOrderDetailList(detailList);
	reqBody.setFxOrderPayList(payList);
	reqBody.setFxOrderXp(orderXp);
	return reqBody;

  }


private long getUserTotalPayPrice(List<CouponDetail> couponDetailList,
		long orderPayment, long balanceUsed) {
	long userTotalPayPrice=0;
	if(couponDetailList!=null && couponDetailList.size()>0){
		for(CouponDetail couponDetail: couponDetailList){
			if(couponDetail!=null){
				//商家店内优惠
				/*if(couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_100)||couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_30)){
					userTotalPayPrice=userTotalPayPrice+toLongMoney("", couponDetail.getCouponPrice());
				}*/
				//京东优惠
				if(couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_41)||
						couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_52)||
						couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_39)||
						couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_34)
						){
					userTotalPayPrice=userTotalPayPrice+toLongMoney("",  couponDetail.getCouponPrice());
				}
				 
			}
		}
	}
	return userTotalPayPrice+orderPayment+balanceUsed;
}

/**
 * 转为走秀没小数点long
 * 
 * @param fieldName
 *            属性名称
 * @param str
 * @return
 */
private long toLongMoney(String fieldName, String price) {
	long money = 0L;
	if (price == null || "".equals(price.trim())) {
		return money;
	}
	Double dbMoney = Double.valueOf(price);
	try {
		money=Long.parseLong(new DecimalFormat("0").format(dbMoney * 100));
		
	} catch (NumberFormatException e) {
		return money;
	}
	logger.info("长整形,属性名称为" + fieldName + "   输入的价格为 " + price + "  转换为long后为   " + money);
	return money;
}

/**
 * 转为走秀没小数点int
 * 
 * @param fieldName
 *            属性名称
 * @param price
 * @return 已经转换为分了
 */
private int toIntMoney(String fieldName, String price) {

	int money = 0;
	if (price == null || "".equals(price.trim())) {
		return money;
	}
	try {
		Double dbMoney = Double.valueOf(price);
		money = (int) (dbMoney * 100);
	} catch (NumberFormatException e) {
		return money;
	}
	logger.info("整形,属性名称为" + fieldName + "   输入的价格为 " + price + "  转换为int后为   " + money);
	return money;
 }

/**
 *   
 * @param jdCouList
 *            京东优惠列表
 * @param skuId
 *            京东skuId
 * @param itemTotalAmount
 *            　商品总金额(即订单总金额  )OrderTotalPrice
 * @param discountFee1
 *            订单总优惠(商家总的优惠)sellerDiscount
 * @param itemAmount
 *            单品金额 (京东的商品原价)JdPrice
 * @param itemQty
 *            单个商品用户购买的数量 itemTotal
 *
 *@param itemCodes
 *            走秀码
 * 
 * @param activeList
 *           活动信息列表
 * this.calcItemAmount(orderInfo.getCouponDetailList(), itemInfo.getSkuId(), order.getItemAmount(),
					order.getDiscountFee1(), detail.getOriginalPrice(), detail.getQuantity(), product.getXiuSN(), activeList)
 */

private Map<String, Object> calcItemAmount(List<CouponDetail> jdCouList, String skuId, long itemTotalAmount, long discountFee1, long itemAmount,
		long itemQty, String itemCodes, List<FxOrderActive> activeList,List<String> proudctCoupon) {
	// 1.JD没有提供活动信息
	if (jdCouList == null || jdCouList.size() == 0 || itemTotalAmount == 0) {
		return null;
	}
	
	if(jdCouList.size() == 1) {
		CouponDetail couponDetail = jdCouList.get(0);
		if (couponDetail == null) {
			return null;
		}
		// 订单号不存在，不认为有活动
		if (StringUtils.isEmpty(couponDetail.getOrderId())) {
			return null;
		}
		if(StringUtils.isEmpty(couponDetail.getSkuId())){
			//订单级别
			return JdCouponCalc.calcItemAmount(jdCouList, skuId, itemTotalAmount, discountFee1, itemAmount, itemQty, itemCodes, activeList);
		} else  {
			//单个商品优惠
			return JdCouponCalc.calcItemAmountByItemOne(jdCouList, skuId, itemTotalAmount, discountFee1, itemAmount, itemQty, itemCodes, activeList);
		}
		
	} else {
		//多个商品优惠
		return JdCouponCalc.calcItemAmountByItemBatch(jdCouList, skuId, itemTotalAmount, discountFee1, itemAmount, itemQty, itemCodes, activeList,proudctCoupon);
	}
 }

  @Test
  public void isXiuOrEbay(){
	   String name=ParseJDOauthProperties.ORDER_PLATFORM_TYPE.trim();
	   System.out.println("name="+name);
		if("jd_ebay".equalsIgnoreCase(name)){
			System.out.println("Ebay店");
		}else{
			System.out.println("Xiu店");
		}
  }
  
  @Test
  public void aa(){
	  
	  Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("xiucode","10223919");
		parames.put("xiuCategoryId", "1348");
	  
	  Map<Map<String, String>, Map<String, List<JDAttributeValue>>> attrbuteInfoMap = jDWareServiceBean.queryAttrbuteInfo(parames);
	  if(attrbuteInfoMap.isEmpty()){
			parames.put("xiuCategoryId", "1348");
			attrbuteInfoMap = jDWareServiceBean.queryAttrbuteInfo(parames);
		}
  }

  @Test
  public void testXiuAddress() {
//  	/贵州省%' and t.city_name like '%毕节市%' and t.area_name like '%金沙县%' and rownum=1

  	  String a = "澄迈";
  	  System.out.println(a.length());
  	  if(a.length()>2)
  	  {
  	  String suba = a.substring(0, 2);
  	  System.out.println(suba);
  	  }
  	
  	  String  str = "123";
  	  String newArea = str.replaceAll("1","");
  	  System.out.println(newArea);
  	
  	  
  	String province = "贵州";
  	String city = "毕节市";
  	String county ="金沙县";
  	XiuAddress xiuAddress=null;
  	XiuAddress address = new XiuAddress(province, city,county);//第1次调用SQL	
  	xiuAddress=orderService.queryXiuAddress(address);
  	logger.info("第一次调用地址映射SQL查询的结果为："+xiuAddress);
  	 if(xiuAddress==null){//第2次调用SQL
  		 address= new XiuAddress();
  		 address.setProvince(province);
  		 address.setCity(city);
  		 xiuAddress=orderService.queryXiuAddress(address);
  		 logger.info("第二次调用地址映射SQL查询的结果为："+xiuAddress);
  	 }
  	 if(xiuAddress==null){//第3次调用SQL
  		 address= new XiuAddress();
  		 address.setProvince(province);
  		 address.setArea(county);
  		 xiuAddress=orderService.queryXiuAddress(address);
  		 logger.info("第三次调用地址映射SQL查询的结果为："+xiuAddress);
  	 }
  	 if(xiuAddress==null){//第4次调用SQL
  		 address= new XiuAddress();
  		 address.setProvince(province);
  		
  		  if(county.length()>2)
  		  {
  			  address.setArea(county.substring(0,2));
  		  }
  		  else
  		  {
  			  address.setArea(county);
  		  }
  		 xiuAddress=orderService.queryXiuAddress(address);
  		 logger.info("第四次调用地址映射SQL查询的结果为："+xiuAddress);
  	 }
  	 
  	 System.out.println(xiuAddress.getProvince());
  	 System.out.println(xiuAddress.getCity());
     System.out.println(xiuAddress.getArea()); 
     System.out.println(xiuAddress.getPostcode());
  	 System.out.println(xiuAddress);
  	
  }
  
  @Test
  public void testProduct(){
	  productService.loadProduct("71033106");
  }
}
