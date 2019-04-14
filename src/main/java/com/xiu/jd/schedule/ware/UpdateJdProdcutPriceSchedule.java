package com.xiu.jd.schedule.ware;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ware.Sku;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.request.ware.WareSkuPriceUpdateRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateRequest;
import com.jd.open.api.sdk.response.ware.WareListResponse;
import com.jd.open.api.sdk.response.ware.WareSkuPriceUpdateResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateResponse;
import com.xiu.commerce.hessian.model.Product;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.bean.ware.JdChangedGoodsPrice;
import com.xiu.jd.bean.ware.SettlementProductInfo;
import com.xiu.jd.schedule.BaseSchedule;
import com.xiu.jd.service.ProductService;
import com.xiu.jd.service.ware.JDWareService;
import com.xiu.jd.service.ware.JdChangeGoodsPriceService;
import com.xiu.jd.utils.ParseProperties;
import com.xiu.jd.utils.WebUtils;
import com.xiu.settlement.common.ProdSettlementHessianService;

/***
 * 定时更新京东商品价格,扫描jd_changed_goods_price表
 * 
 * @author liweibiao
 * 
 */
public class UpdateJdProdcutPriceSchedule extends BaseSchedule {

	private static final Logger logger = Logger.getLogger(UpdateJdProdcutPriceSchedule.class);

	@Resource(name = "jdChangeGoodsPriceService")
	private JdChangeGoodsPriceService jdChangeGoodsPriceService;

	/**
	 * 商品中心
	 * 
	 * 
	 * **/
	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "jDWareServiceBean")
	private JDWareService jDWareServiceBean;

	/**结算系统接口**/
	@Resource(name="prodSettlementHessianService")
	private ProdSettlementHessianService prodSettlementHessianService ;

	public void updateJdProdcutPrice() {
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("rown", "1"); // 分组后取时间最大的第一条数据
		parames.put("firstNum", "1");
		int pageSize = 200;
		parames.put("lastNum", pageSize);
		parames.put("updatestatus", "0");
		QueryResult<JdChangedGoodsPrice> queryResult = jdChangeGoodsPriceService.getPageResule(parames);
		if (queryResult != null) {
			long total = queryResult.getTotalrecord();
			logger.info("MQ 价格 总记录数为:" + total);
			int totalPage = getTotalPage(pageSize, (int) total);
			logger.info("MQ 价格总页数为:" + totalPage);
			for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
				if (currentPage != 1) {
					parames = new HashMap<String, Object>();
					parames.put("rown", "1"); // 分组后取时间最大的第一条数据
					parames.put("firstNum", pageSize * (currentPage - 1) + 1); 
					parames.put("lastNum", pageSize * currentPage + 1);
					parames.put("updatestatus", "0");
					queryResult = jdChangeGoodsPriceService.getPageResule(parames);
				}
				logger.info("MQ 价格 第==" + currentPage + "==页");
				// 取得需要同步到京东的商品
				List<JdChangedGoodsPrice> changedGoodsPrices = queryResult.getResultlist();
				if (changedGoodsPrices != null && changedGoodsPrices.size() > 0) {
					// 京东
					int totalRecord = changedGoodsPrices.size();
					int maxSize = 10;
					// 总页数
					int totalPages = getTotalPage(maxSize, totalRecord);
					int index = 0;
					for (int current = 1; current <= totalPages; current++) {
						StringBuffer wardIds = new StringBuffer();
						for (; index < current * maxSize; index++) {
							if (index >= totalRecord) {
								break;
							}
							JdChangedGoodsPrice ware = changedGoodsPrices.get(index);
							if (ware != null) {
								String wareId = ware.getWareid();
								if (wareId != null && !"".equals(wareId.trim())) {
									wardIds.append(wareId.trim()).append(',');
								}else{
									//把本地价格更新好，等待推送到京东update jd_product_info t set t.jdprice=#jdprice# where t.xiucode=#xiucode#
									Map<String, Object> wareParames=new HashMap<String, Object>();
									String price=ware.getXiuprice();
									String xiuCode=ware.getXiucode();
									try{
										if(price==null || "".equals(price.trim())){
											logger.error("MQ 价格 xiucode"+xiuCode+"商品价格为:"+price);
											 continue;
										}
										Double xiuPrice=null;
										try {
											//为分
											xiuPrice=Double.valueOf(price.trim());
										} catch (Exception e1) {
											logger.error("MQ 价格 xiucode"+xiuCode+"商品价格为:"+price+"格式化异常"+e1);
											e1.printStackTrace();
											continue;
										}
										
										//对接走秀活动价到京东
										if("true".equals(ParseProperties.IS_ACTIVITY_PRICE)){
											String xiuactivityprice=ware.getXiuactivityprice();
											if(xiuactivityprice!=null && !xiuactivityprice.isEmpty()){
												//走秀商品活动价
												Double prdActivityPrice=0d;
												try {
													prdActivityPrice = Double.valueOf(xiuactivityprice.trim());
												} catch (Exception e) {
													logger.error("MQ 价格 xiucode"+xiuCode+"商品活动价格式化异常"+e);
													e.printStackTrace();
													continue;
												}
											    if(prdActivityPrice!=null && prdActivityPrice>0){
											    	logger.error("MQ 价格 xiucode"+xiuCode+"商品活动价为:"+prdActivityPrice);
											    	xiuPrice=prdActivityPrice;
											    }
											}
										}
										SettlementProductInfo settlementProductInfo= getProductSettlementInfo(prodSettlementHessianService, xiuCode.trim(),WebUtils.doubleToInt(xiuPrice));
										if(settlementProductInfo==null){
											logger.error("走秀码:"+xiuCode+"调用结算系统失败");
											continue;
										}
										int tempJdPrice=WebUtils.longParseInt(settlementProductInfo.getFinnalXiuPrice());
										if(tempJdPrice<=0){
											logger.error("走秀码:"+xiuCode+"解析价格"+settlementProductInfo.getFinnalXiuPrice()+"失败");
											continue;
										}
										int finalPrice = WebUtils.computePrice(new BigDecimal(tempJdPrice)); //单位为元
										logger.info("MQ 价格,走秀码为:"+xiuCode+",商品中心走秀价"+xiuPrice+"分,结算系统价格"+tempJdPrice+"元,最终推送给京东的价格"+finalPrice+"元");
										
										wareParames.put("jdprice", finalPrice);
									}catch(Exception e){
										wareParames.put("jdprice", price);
										Double xiuPrice=Double.valueOf(price);
										logger.error("MQ 价格 xiucode"+ware.getXiucode()+"商品价格为("+(xiuPrice/100)+")异常"+e);
										e.printStackTrace();
									}
								   try{
										wareParames.put("xiucode", xiuCode.trim());
										int num=jDWareServiceBean.updateJdProductPrice(wareParames);
										logger.info("MQ 价格 商品走秀码为:"+xiuCode+"更新本地商品价格,影响的记录数为: "+num);
									    JdChangedGoodsPrice entity = new JdChangedGoodsPrice();
										entity.setUpdatestatus(3);
										entity.setRecordid(ware.getRecordid());
										jdChangeGoodsPriceService.update(entity);
										logger.info("MQ 价格 商品走秀码为:"+xiuCode+"=====>");
								   }catch(Exception e){
									   logger.info("MQ 价格 商品走秀码为:"+xiuCode+"=====>更新本地数据库异常");
									   e.printStackTrace();
								   }
								}
							}
						}
						if (wardIds.length() > 0) {
							wardIds.deleteCharAt(wardIds.length() - 1);
							// 京东API最大支持10个
							WareListResponse response = getWareListResponse(wardIds.toString());
							if (response != null) {
								if ("0".equals(response.getCode())) {
									List<Ware> wareList = response.getWareList();
									if (wareList != null && wareList.size() > 0) {
										for (Ware ware : wareList) {
											if (ware == null) {
												continue;
											}
											String jdWareId = ware.getWareId() + "";
											for (JdChangedGoodsPrice changedGoodsPrice : changedGoodsPrices) {
												if (changedGoodsPrice == null) {
													continue;
												}
												try{
												// 需要更新的京东商品
													if (jdWareId.equals(changedGoodsPrice.getWareid())) {
															String xiuCode=changedGoodsPrice.getXiucode();
															String xiuPrice = changedGoodsPrice.getXiuprice();//分
															Double price=Double.valueOf(xiuPrice); //分
															xiuPrice= String.valueOf((price/100)); //元
															logger.info("MQ 价格 京东商品ID为:" + ware.getWareId() + "原始走秀价格是分的:"+changedGoodsPrice.getXiuprice()+",数据库走秀价转换为元的:"+xiuPrice);
															
															if(xiuPrice!=null && !"".equals(xiuPrice.trim())){
																if(xiuCode!=null && !"".equals(xiuCode.trim())){
																	synWarePriceToJd(changedGoodsPrice.getRecordid(), ware, xiuPrice+"",xiuCode);
																}
															}
														break;
													}
												}catch(Exception e){
													logger.error("MQ 价格 京东商品ID为:" + jdWareId + "京东商品价格设置异常"+e);
													e.printStackTrace();
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/***
	 * 同步商品价格到京东
	 * 
	 * @param ware
	 *            京东商品实体
	 * @param xiuPrice
	 *            要同步的价格,单位为元
	  * @param xiuCode
	 *            走秀码
	 */
	private void synWarePriceToJd(long recordid, Ware ware, String price,String xiuCode) {
		List<Sku> skus = ware.getSkus();
		if(skus==null || skus.size()<=0){
			return ;
		}
		Product product = null;
		String wareId = ware.getWareId() + "";
		try {
			product=productService.loadProduct(xiuCode.trim());
			//模拟市场价TODO
			//product.setPrdListPrice(446D);
		} catch (Exception e1) {
			logger.error("MQ 价格 京东商品ID为:" + wareId +"MQ 价格,调用商品中心接口异常:"+e1);
			e1.printStackTrace();
			product=null;
		}
		
		if(product==null){
			logger.error("MQ 价格 京东商品ID为"+wareId+",走秀码为"+xiuCode+",数据库中走秀价为"+price+",商品中心不存在");
			return ;
		}
		int skuTotalCount = 0;
		int count = 0;
	
		//商品中心现在的走秀价格为,单位为元
		Double xiuPrice = product.getPrdOfferPrice();
		if("true".equals(ParseProperties.IS_ACTIVITY_PRICE)){
			//商品中心现在的活动格为，单位为元
			Double xiuactivityprice=product.getPrdActivityPrice();
			if(xiuactivityprice!=null && xiuactivityprice>0){
				xiuPrice=xiuactivityprice;
				logger.info("MQ 价格 京东商品ID为:" + wareId + "商品中心活动价格是元的:"+xiuactivityprice+",走秀价元为:"+xiuPrice);
			}
		}
		

		SettlementProductInfo settlementProductInfo= getProductSettlementInfo(prodSettlementHessianService, xiuCode.trim(),WebUtils.doubleToInt(xiuPrice)*100);
		if(settlementProductInfo==null){
			logger.error("走秀码:"+xiuCode+"调用结算系统失败");
			return;
		}
		int tempJdPrice=WebUtils.longParseInt(settlementProductInfo.getFinnalXiuPrice());
		if(tempJdPrice<=0){
			logger.error("走秀码:"+xiuCode+"解析价格"+settlementProductInfo.getFinnalXiuPrice()+"失败");
			return;
		}
		int finalPrice = WebUtils.computePrice(new BigDecimal(tempJdPrice)); //单位为元
		logger.info("MQ 价格,走秀码为:"+xiuCode+",商品中心走秀价"+xiuPrice+"元,结算系统价格"+tempJdPrice+"元,最终推送给京东的价格"+finalPrice+"元");
		
		if (skus != null && skus.size() > 0) {
			skuTotalCount = skus.size();
		}
		
		//走秀市场价
		Double xiuMarketPrice=product.getPrdListPrice();
		logger.info("MQ 价格 京东商品ID为:" + wareId + "走秀价元为:"+xiuPrice+"最终价格为:"+finalPrice+"元,京东sku总数量为:"+skuTotalCount+",走秀市场价:"+xiuMarketPrice);
		if(xiuMarketPrice==null || xiuMarketPrice<=0){
			try{
				xiuMarketPrice=(double) WebUtils.buildMarketPrice(finalPrice);
				logger.info(" 京东商品ID: " + wareId +"市场价<=0,使用最后售价递归生成市场价:"+ xiuMarketPrice+",走秀价格为"+xiuPrice+",京东最后售价为"+finalPrice);
			}catch(Exception e){
				xiuMarketPrice=finalPrice+(int)finalPrice*0.4;
				logger.error(" 京东商品ID: " + wareId +"市场价:"+ xiuMarketPrice+",走秀价格为"+xiuPrice+",京东最后售价为"+finalPrice+",市场价转换异常"+e);
			}
		}	
		if(finalPrice>xiuMarketPrice){
			xiuMarketPrice=(double) WebUtils.buildMarketPrice(finalPrice);
			logger.info(" 京东商品ID: " + wareId +"市场价:"+ xiuMarketPrice+",走秀价格为"+xiuPrice+",京东最后售价为"+finalPrice+",最终售价大于市场价");
		}
		for (Sku s : skus) {
			if (s == null) {
				continue;
			}
			count++;
			// 到达最后一个sku时，更新商品价格(京东商品价格是跟着sku和商品走的)
			JdChangedGoodsPrice entity = null;
			if (count == skuTotalCount) {
				WareUpdateResponse wareUpdateResponse = null;
				// skuTotalCount!=1 京东商品有两个sku才处理更新京东商品上的价格
				// 更新商品价格
				try {
					entity = new JdChangedGoodsPrice();
					entity.setRecordid(recordid);
					entity.setJdprice(ware.getJdPrice());
					String code = "";
					// 商品只有一个sku
					if (skuTotalCount == 1) {
						entity.setUpdatestatus(1);
					} else {
						WareUpdateRequest wareUpdateRequest = new WareUpdateRequest();
						wareUpdateRequest.setWareId(wareId);
						wareUpdateRequest.setJdPrice(finalPrice + "");
						if(xiuMarketPrice>0){
							if(xiuMarketPrice>= finalPrice){
								int marketPrice=Integer.parseInt(new DecimalFormat("0").format(xiuMarketPrice));
								wareUpdateRequest.setMarketPrice(marketPrice+"");
							}else{
								int marketPrice=WebUtils.buildMarketPrice(finalPrice);
								wareUpdateRequest.setMarketPrice(marketPrice+"");
							}
						}
						
						wareUpdateResponse = client.execute(wareUpdateRequest);
						
					}
					if (wareUpdateResponse != null) {
						code = wareUpdateResponse.getCode();
						logger.info("京东商品ID: " + wareId +"京东市场价为"+ xiuMarketPrice+",走秀价格为"+price+",京东最后售价为"+finalPrice+",商品的,京东错误码:"+code+"错误信息"+wareUpdateResponse.getEnDesc());
						if ("0".equals(code)) {
							logger.info("MQ 价格 京东商品ID为:" + wareId + "更新京东商品价格成功,Code="+code+"商品原始价格为:"+xiuPrice+"商品最终价格为"+finalPrice);
							// 更新jd_changed_goods_price 需要同步价格商品表中的同步状态
							// UPDATESTATUS
							entity.setUpdatestatus(1);

						} else {
							
							if("11201093".equals(code) ) {
								logger.error("MQ 价格京东商品商品ID"+wareId+",商品参加促销,暂时不能修改价格,Code="+code+"商品原始价格为:"+xiuPrice+"商品最终价格为"+finalPrice+",京东失败信息:"+wareUpdateResponse.getZhDesc());
								//参加促销的商品
								entity.setUpdatestatus(6);
							}else if("11201082".equals(code)){
								 //11201082 发布商品时需要录入必填属性
								entity.setUpdatestatus(5);
							}else if("11201056".equals(code)){
								//京东价不能大于市场价
								entity.setUpdatestatus(7);
							}else if("11200025".equals(code)){
								//SKU已经删除错误
								entity.setUpdatestatus(4);
							}else if("11201083".equals(code)){
								// 11201083 单选属性不可录入多值,这种情况其实也是成功了
								//失败信息11201048:商品描述过长
								entity.setUpdatestatus(8);
							}
							else{
								//更新失败
								entity.setUpdatestatus(2);
							}
							// ,如果京东上一个商品下只有一个sku时京东返回的错误码为 :11201081,代表
							// 商品价格不能超出销售属性（颜色，尺码等）表中的价格范围  || "11201081".equals(code)
							logger.error("MQ 价格 京东商品ID为:" + wareId + "更新京东商品价格失败,失败信息" + wareUpdateResponse.getZhDesc()+",Code="+code+"商品原始价格为:"+xiuPrice+"商品最终价格为"+finalPrice);
                           
						}
						jdChangeGoodsPriceService.update(entity);
					}

				} catch (JdException e) {
					logger.error("MQ 价格 京东商品ID为:" + wareId + "更新京东商品价格异常");
					e.printStackTrace();
				}
			}
			// 更新京东sku价格
			String resultCode = updateSKUPrice(wareId, s.getSkuId()+"", finalPrice,xiuMarketPrice,count);
			if ("0".equals(resultCode)) {
				if (count == skuTotalCount) {
					if (entity != null) {
						int num = jdChangeGoodsPriceService.update(entity);
						logger.info("MQ 价格 京东商品ID为:" + wareId +"价格 更新商品京东同步到京东状态,影响的记录数为:" + num);
						JDProduct jdProduct = new JDProduct();
						jdProduct.setJdprice(finalPrice + "");
						jdProduct.setJdWareId(wareId);
						// 更新本地数据库表JD_PRODUCT_INFO 商品价格
						jDWareServiceBean.updateProduct(jdProduct);
					}

				}

			}else{
				if (count == skuTotalCount) {
					if("11201093".equals(resultCode) ) {
						logger.error("MQ 价格京东商品商品ID"+wareId+",商品参加促销,暂时不能修改价格,Code="+resultCode+"商品原始价格为:"+xiuPrice+"商品最终价格为"+finalPrice);
						//参加促销的商品
						entity.setUpdatestatus(6);
					}
					else if("11201056".equals(resultCode)){
						//京东价不能大于市场价
						entity.setUpdatestatus(7);
						logger.error("MQ 价格,京东ID:"+entity.getWareid()+"京东价为"+entity.getJdprice()+"走秀价为:"+entity.getXiuprice()+"京东价不能大于京东市场价");
					}//
					else if("11200025".equals(resultCode)){
						//SKU已经删除错误
						entity.setUpdatestatus(4);
						logger.error("MQ 价格,京东ID:"+entity.getWareid()+"京东价为"+entity.getJdprice()+"走秀价为:"+entity.getXiuprice()+"SKU已经删除错误");
					}else if("66".equals(resultCode) ){//平台连接后端服务不可用错误码为
						entity.setUpdatestatus(0);
					}
					
				    //异常
					else if("-1".equals(resultCode)){
						//
						entity.setUpdatestatus(9);
						logger.error("MQ 价格,京东ID:"+entity.getWareid()+"京东价为"+entity.getJdprice()+"走秀价为:"+entity.getXiuprice()+"SKU已经删除错误");
					}
					logger.info("MQ 价格,京东ID:"+entity.getWareid()+"最后一个商品sku更新情况\n"+entity+"京东错误吗为:"+resultCode);
					jdChangeGoodsPriceService.update(entity);
				}
			} 
		}
	}

	/**
	 * @param wareId
	 *            京东商品ID
	 * @param skuId
	 *            京东商品skuID
	 * @param xiuPrice  (走秀的最终价格)
	 *            要同步的价格
	 * @param xiumarketPrice
	 *            要同步的价格
	 */
	private String updateSKUPrice(String wareId, String skuId, int xiuPrice,double xiuMarketPrice,int skuCode) {
		WareSkuPriceUpdateRequest request = new WareSkuPriceUpdateRequest();
		request.setSkuId(skuId);
		request.setPrice(xiuPrice+"");
		if(skuCode>1){
			request.setJdPrice(xiuPrice+"");
			 if(xiuMarketPrice>0 && xiuMarketPrice>= xiuPrice){
				int marketPrice=(int)xiuMarketPrice;
				request.setMarketPrice(marketPrice+"");
				logger.info("京东商品ID:" + wareId +",京东sku="+skuId+"京东市场价为"+ marketPrice+",京东最后售价为"+xiuPrice);
			 }
		}
	
		try {
			WareSkuPriceUpdateResponse response = client.execute(request);
			String code=response.getCode();
			if (response != null) {
				if ("0".equals(code)) {
					logger.info("MQ 价格 京东商品ID为:" + wareId +",京东sku="+skuId+ ",更新京东商品SKU价格成功,价格为:"+xiuPrice);
				
				} else {
					logger.error("MQ 价格 京东商品ID为:" + wareId + ",京东sku="+skuId+",更新京东商品SKU价格失败," + response.getZhDesc()+"错误码为:"+code+",价格为:"+xiuPrice);
				}
				return code;
			}
		} catch (JdException e) {
			logger.error("MQ 价格 京东商品ID为:" + wareId + ",京东sku="+skuId+",更新京东商品SKU价格异常:"+e+",价格为:"+xiuPrice);
			e.printStackTrace();
		}

		return "-1";
	}

   
  
}
