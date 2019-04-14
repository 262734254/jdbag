package com.xiu.jd.schedule.ware;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ware.Sku;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.request.ware.WareGetRequest;
import com.jd.open.api.sdk.request.ware.WareInfoByInfoRequest;
import com.jd.open.api.sdk.request.ware.WareSkuPriceUpdateRequest;
import com.jd.open.api.sdk.request.ware.WareSkuStockUpdateRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateDelistingRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateListingRequest;
import com.jd.open.api.sdk.response.ware.WareGetResponse;
import com.jd.open.api.sdk.response.ware.WareInfoByInfoSearchResponse;
import com.jd.open.api.sdk.response.ware.WareListResponse;
import com.jd.open.api.sdk.response.ware.WareSkuPriceUpdateResponse;
import com.jd.open.api.sdk.response.ware.WareSkuStockUpdateResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateDelistingResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateListingResponse;
import com.xiu.channel.inventory.api.InventoryService;
import com.xiu.channel.inventory.api.dto.QueryInventoryRequest;
import com.xiu.channel.inventory.api.dto.QueryInventoryResponse;
import com.xiu.commerce.hessian.model.Product;
import com.xiu.jd.bean.log.JdLog;
import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.bean.ware.JDSku;
import com.xiu.jd.bean.ware.SettlementProductInfo;
import com.xiu.jd.constants.GlobalConstants;
import com.xiu.jd.schedule.BaseSchedule;
import com.xiu.jd.service.ProductService;
import com.xiu.jd.service.log.JdLogService;
import com.xiu.jd.service.ware.JDWareService;
import com.xiu.jd.service.ware.JdSkuService;
import com.xiu.jd.service.ware.OnLineBlackProductService;
import com.xiu.jd.utils.ParseProperties;
import com.xiu.jd.utils.WebUtils;
import com.xiu.settlement.common.ProdSettlementHessianService;

/***
 * 定时的更新京东上的商品价格和库存
 * 
 * @author user
 * 
 */
public class ScanJDWareInfoSchedule extends BaseSchedule {
	
	private static final Logger logger = Logger.getLogger(ScanJDWareInfoSchedule.class);
	//第一次启动时候，要同步一次京东商品上下架状态到本地数据库,以后的同步都是由其它定时器完成
	//private static int synProductOnLineStatusToNative=1;

	/** 渠道中心 **/
	@Resource(name = "inventoryService")
	private InventoryService inventoryService;

	/** 商品中心**/
	@Resource(name = "productService")
	private ProductService productService;


	@Resource(name = "jdLogServiceBean")
	private JdLogService jdLogServiceBean;

	@Resource(name = "jDWareServiceBean")
	private JDWareService jDWareServiceBean;

	@Resource(name = "jdSkuServiceBean")
	private JdSkuService<JDSku> jdSkuServiceBean;
	
	@Resource(name="onLineBlackProductServiceBean")
	private OnLineBlackProductService onLineBlackProductServiceBean;

	/**结算系统接口**/
	@Resource(name="prodSettlementHessianService")
	private ProdSettlementHessianService prodSettlementHessianService ;
	
	/**
	 * 扫描京东商品信息 (做库存同步) 商品库存是跟着SKU走的 而商品的价格是跟着商品走的
	 */
	public void getJdWareInfo() {
		try {
			WareInfoByInfoRequest wareInfoByInfoRequest = new WareInfoByInfoRequest();
			// 默认当前页为第一页
			int page = 1;
			wareInfoByInfoRequest.setPage(page + "");
			// 每页显示的记录数量
			wareInfoByInfoRequest.setPageSize(pageSize + "");
			WareInfoByInfoSearchResponse response = client.execute(wareInfoByInfoRequest);
			if (response != null && "0".equals(response.getCode())) {
				int total = response.getTotal();
				int totalPage = getTotalPage(pageSize, total);
				logger.info("总的记录数量为:" + total + "总的页数为 =" + totalPage + ",  每页显示的记录数为" + pageSize);
				for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
					if (currentPage == totalPage) {
						logger.info("=======================" + "总页数为:" + totalPage + "完成");
					}
					if (currentPage != 1) {
						wareInfoByInfoRequest = new WareInfoByInfoRequest();
						wareInfoByInfoRequest.setPage(currentPage + "");
						// 每页显示的记录数量
						wareInfoByInfoRequest.setPageSize(pageSize + "");
						response = client.execute(wareInfoByInfoRequest);
					}
					logger.info("第current=" + currentPage + "=页");
					if (response != null) {
						String code = response.getCode();
						logger.info("0".equals(code) ? "调用京东API成功" : "调用京东API失败,失败原因" + response.getZhDesc());
						if ("0".equals(code)) {
							List<Ware> wares = response.getWareInfos();
							if (wares != null && wares.size() > 0) {
								updateProudctPriceAndStock(wares);
							}
						}
					}
				}
			} else {
				logger.info("response对象为" + response);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 处理库存
	 * 
	 * @param sku
	 * @param qir
	 * @param jdLog
	 * @param skusn
	 * @throws JdException
	 */
	public void handleStock(Sku sku, QueryInventoryResponse qir, JdLog jdLog, String outerId, String desc) throws JdException {
		if (outerId.length() > 12) {
			outerId = outerId.substring(0, 12);
		}
		// 渠道中心商品sku对应的库存
		int skuStockNum= 0;
		if(qir!=null){
			skuStockNum= qir.getQty();
			if(skuStockNum<=0){
				skuStockNum=0;
			}
		}
		long jdStockNum = sku.getStockNum();
		// skuStockNum!=0 && skuStockNum != jdStockNum
		logger.info("京东商品ID为:" + sku.getWareId() + "走秀sku为:" + outerId + ",走秀上的库存为:" + skuStockNum + ",京东上的库存为:" + jdStockNum);
		logger.info(skuStockNum == jdStockNum ? "京东商品ID为:" + sku.getWareId() + "商品SKU为," + outerId + "库存相等" : "京东商品ID为:" + sku.getWareId()
				+ "商品SKU为," + outerId + "库存不相等");
		if (skuStockNum != jdStockNum) {
			if (skuStockNum < 0) {
				skuStockNum = 0;
			}
			WareSkuStockUpdateRequest request = new WareSkuStockUpdateRequest();
			// 京东skuId
			request.setSkuId(sku.getSkuId() + "");
			request.setOuterId(outerId);
			// SKU对应的库存量
			request.setQuantity(skuStockNum + "");

			// 更新商品SKU所对应的库存
			WareSkuStockUpdateResponse responseStock = client.execute(request);
			int count = 0;
			if (responseStock != null) {
				desc = HandleMsg.SYN_STOCK.getDesc(skuStockNum + "", jdStockNum + "");
				if (responseStock != null && "0".equals(responseStock.getCode())) {
					logger.info("京东商品ID为:" + sku.getWareId() + "库存同步到京东成功,商品SKU码:" + responseStock.getOuterId());
					syncInformation(jdLog, desc, 2, 1);
					Map<String, Object> parames = new HashMap<String, Object>();
					parames.put("stocknum", skuStockNum);
					parames.put("skusn", outerId);
					count = jdSkuServiceBean.updateJdProductSkuStock(parames);
					logger.info(HandleMsg.SYN_STOCK.getSyn_success());
				} else {
					syncInformation(jdLog, desc, 2, 2);
					logger.info(HandleMsg.SYN_PRICE.getSyn_fail());
				}
			}
			logger.info(count == 1 ? "京东商品ID为:" + sku.getWareId() + "更新(本地数据库)库存成功," : "京东商品ID为:" + sku.getWareId() + "更新(本地数据库)库存失败");
			if (responseStock != null) {
				logger.info("京东商品ID为:" + sku.getWareId() + ",京东Code=" + responseStock.getCode());
			}
		}

	}

	/**
	 * 处理价格
	 * 
	 * @param jdLog
	 *            日记对象
	 * @param w
	 *            京东商品实体
	 * @param price
	 *            官网标准价
	 * @param outerId
	 *            skuID jd外部Id也就是走秀的skuID
	 * @param skuId
	 *            京东自己的skuID
	 * @param desc
	 *            日志描述
	 *            
	 * @param xiuCode
	      走秀码
	 * @throws JdException
	 */
	public void handlePrice(JdLog jdLog, Ware w, double price, Double xiuMarketPrice, String outerId, String skuId, String desc,int size,int totalSkuSize,String xiuCode,String skuPrice) throws JdException {

		// 京东上商品的价格 //300.00
		String jdPrice = w.getJdPrice();
		/**
		 * 如走秀价为100元 则京东价为 109元。 100/0.95=105.26 -->　109 元
		 */
		// 商品原始走秀价格 413 413.0 官网标准价
		BigDecimal prdOfferPriceBig = new BigDecimal(price);
		// 京东的市场价格
		Double jdMarketPrice = Double.valueOf(w.getMarketPrice()).doubleValue();
		BigDecimal jdPriceBig = new BigDecimal(jdPrice);
		BigDecimal jdSkuPriceBig = new BigDecimal(skuPrice);

		/**
		 * ================================== 京东价= 1） 走秀价/0.95 ;2） 取整后，个位数以9结尾。
		 * e.g. 如走秀价为100元， 则京东价为 109元。 100/0.95=105.26 -->　109 元
		 */
		
		SettlementProductInfo settlementProductInfo= getProductSettlementInfo(prodSettlementHessianService, xiuCode.trim(),WebUtils.doubleToInt(price)*100);
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
		logger.info("MQ 价格,走秀码为:"+xiuCode+",商品中心走秀价"+price+"元,结算系统价格"+tempJdPrice+"元,最终推送给京东的价格"+finalPrice+"元");
		
		BigDecimal jdMarketPriceBig = new BigDecimal(jdMarketPrice);
		WareSkuPriceUpdateResponse responsePrice = null;
		// 走秀的市场价
		BigDecimal xiuMarketPriceBig = new BigDecimal(xiuMarketPrice);
		// 走秀价高于市场价
		//走秀渠道中心价格为:303.0, 京东市场价为:657.0,走秀价高于京东市场价,更新京东市场价为240.0失败,商品京东价不能大于市场价 
		if (prdOfferPriceBig.compareTo(jdMarketPriceBig) == 1 || jdMarketPriceBig.doubleValue() != xiuMarketPriceBig.doubleValue()) {
			desc = "走秀渠道中心价格为:" + price ;
			if(prdOfferPriceBig.compareTo(jdMarketPriceBig) == 1){
				//走秀价高于京东市场价
				desc = desc+ ", 京东市场价为:" + jdMarketPriceBig.doubleValue() + ",走秀价高于京东市场价";
			}
			if (xiuMarketPrice != null && xiuMarketPrice.doubleValue() > 0) {
				WareSkuPriceUpdateResponse priceUpdateResponse = null;
				// 走秀的市场价与京东的市场价不相等
				logger.info("京东商品ID:" + w.getWareId() + "走秀的市场价为:" + xiuMarketPriceBig.doubleValue() + "京东的市场价为:" + jdMarketPriceBig.doubleValue());
				//京东市场价和走秀市场价不相等或者走秀价高于市场价
				if (jdMarketPriceBig.doubleValue() != xiuMarketPriceBig.doubleValue() ||prdOfferPriceBig.compareTo(jdMarketPriceBig) == 1) {
					// 将京东的市场价更新为走秀的市场价
					WareSkuPriceUpdateRequest requestSKUPrice = new WareSkuPriceUpdateRequest();
					requestSKUPrice.setSkuId(skuId);
					requestSKUPrice.setOuterId(outerId);
					if (finalPrice != 0) {
						requestSKUPrice.setPrice(finalPrice + "");
						if(size>1){
							requestSKUPrice.setJdPrice(finalPrice + "");
						}
					} else {
						requestSKUPrice.setPrice(prdOfferPriceBig + "");
						requestSKUPrice.setJdPrice(prdOfferPriceBig + "");
					}
					// 解决市场价带小数更新失败
					int jdMarketPriceFianl = (int) xiuMarketPriceBig.doubleValue();
					if(size==totalSkuSize){
						requestSKUPrice.setMarketPrice(jdMarketPriceFianl + "");
					}
					try {
						priceUpdateResponse = client.execute(requestSKUPrice);
					} catch (Exception ee) {
						logger.error("京东商品ID:" + w.getWareId() + "更新京东价格异常");
						if (priceUpdateResponse != null) {
							desc = desc + "," + priceUpdateResponse.getZhDesc();
						}
					}
					if (priceUpdateResponse != null && "0".equals(priceUpdateResponse.getCode())) {
						if(prdOfferPriceBig.compareTo(jdMarketPriceBig) == 1){
							if (finalPrice != 0) {
								desc = desc + ",更新京东价为" + finalPrice+"成功";
							}else{
								desc = desc + ",更新京东价为" + price+"走秀官网标准成功";
							}
							
						}else{
							desc = desc + ",更新京东市场价为" + jdMarketPriceFianl+"成功";
						}
						
					} else {
						if (priceUpdateResponse != null) {
							//走秀渠道中心价格为:609.0,更新京东市场价为477.0失败,商品京东价不能大于市场价
							desc = desc + ",更新京东市场价为" + xiuMarketPrice + ",失败," + priceUpdateResponse.getZhDesc();
						}
					}
				}
					if (prdOfferPriceBig.compareTo(jdMarketPriceBig) == 1) {
						if(size==totalSkuSize){
							// 走秀价高于市场价，不能更新价格
							syncInformation(jdLog, desc, 3, 5);
						}
					} else {
						if (jdMarketPriceBig.doubleValue() != xiuMarketPriceBig.doubleValue()) {
							// 市场价更新成功
							if (priceUpdateResponse != null ) {
								if( "0".equals(priceUpdateResponse.getCode())){
									if(size==totalSkuSize){
										syncInformation(jdLog, desc, 3, 3);
									}
								}else{
									if(size==totalSkuSize){
										syncInformation(jdLog, desc, 3, 4);
									}
								}
							}
						}
					}
			}
		} else if (jdSkuPriceBig.doubleValue() != finalPrice) {
			logger.info(jdSkuPriceBig.doubleValue() + "--->finalPrice =" + finalPrice + ",京东价与最终价是否相等   " + (jdSkuPriceBig.doubleValue() == finalPrice));
			logger.info("京东价为:" + jdSkuPriceBig.doubleValue() + "");
			// 商品sku对应的价格
			WareSkuPriceUpdateRequest requestPrice = new WareSkuPriceUpdateRequest();
			requestPrice.setSkuId(skuId);
			requestPrice.setOuterId(outerId);
			if (finalPrice != 0) {
				requestPrice.setPrice(finalPrice + "");
				requestPrice.setJdPrice(finalPrice + "");

			} else {
				requestPrice.setPrice(prdOfferPriceBig + "");
				requestPrice.setJdPrice(prdOfferPriceBig + "");
			}
			// 439.00 439.0 true
			// 解决市场价带小数更新失败
			int jdMarketPriceFianl = (int) xiuMarketPriceBig.doubleValue();
			if(size==totalSkuSize){
			  requestPrice.setMarketPrice(jdMarketPriceFianl + "");
				Map<String, Object> parames = new HashMap<String, Object>();
				parames.put("xiucode", outerId.substring(0, 8));
				if (finalPrice != 0) {
					parames.put("jdprice", finalPrice);
				} else {
					parames.put("jdprice", jdPriceBig.doubleValue());
				}

				// 更新京东商品价格(本地数据库)
				int count = jDWareServiceBean.updateJdProductPrice(parames);
				logger.info(count == 1 ? "更新京东商品价格(本地数据库)成功" : "更新京东商品价格(本地数据库)失败");
			}
			responsePrice = client.execute(requestPrice);
		} else if (jdSkuPriceBig.doubleValue() == finalPrice) {
			// 价格已经同步过了
			logger.info("京东Skuid为:" + skuId + ",走秀码为," + outerId + ",京东价与最终价走秀价相等,不需要同步,商品在京东最终售价为  " + finalPrice);
		}

		if (responsePrice != null) {
			logger.info("=========================京东返回的数据=========================\n");
			if (finalPrice != 0) {
				// 走秀渠道中心价格为:609.0, 京东价格为:649.00,将京东的价格更新为:609.0
				// //走秀渠道中心价格为:###, 京东价格为:***,将京东的价格更新为:###"
				desc = HandleMsg.SYN_PRICE.getDesc(Double.toString(finalPrice), jdPrice);
			} else {
				desc = HandleMsg.SYN_PRICE.getDesc(Double.toString(price), jdPrice);
			}

			if ("0".equals(responsePrice.getCode())) {
				if(size==totalSkuSize){
					syncInformation(jdLog, desc, 3, 3);
					logger.info("京东skuId为:" + skuId + ",走秀码为：" + outerId + HandleMsg.SYN_PRICE.getSyn_success());
				}
			} else {
				if(responsePrice!=null){
					desc=desc+","+responsePrice.getZhDesc()+",商品总库存为:"+w.getStockNum();
				}
				if(size==totalSkuSize){
					syncInformation(jdLog, desc, 3, 4);
					logger.info("京东skuId为:" + skuId + ",走秀码为:" + outerId + HandleMsg.SYN_PRICE.getSyn_fail());
				}
				
			}
		}
	}

	/**
	 * 同步数据
	 * 
	 * @param jdLog
	 * @param desc
	 * @param logType
	 * @param status
	 */
	public void syncInformation(JdLog jdLog, String desc, int logType, int status) {
		jdLog.setDescribe(desc);
		jdLog.setLogtype(logType);
		jdLog.setStatus(status);
		String xiuCode = jdLog.getXiucode();
		if (xiuCode != null) {
			if (xiuCode.length() >= 20) {
				jdLog.setXiucode(xiuCode.substring(0, 20));
			} else {
				jdLog.setXiucode(xiuCode);
			}
		}
		jdLogServiceBean.insert(jdLog);
	}

	/**
	 * 更新京东商品库存和价格
	 * 
	 * @param wares
	 * @throws JdException
	 */
	private void updateProudctPriceAndStock(List<Ware> wares) throws JdException {
       
		int total = wares.size();
		int pageSize = 10;
		// 总页数
		int totalPage = getTotalPage(pageSize, total);
		int index = 0;
		for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
			StringBuffer wardIds = new StringBuffer();
			for (; index < currentPage * pageSize; index++) {
				if (index >= total) {
					break;
				}
				Ware ware = wares.get(index);
				if (ware != null) {
					wardIds.append(ware.getWareId()).append(',');
				}
			}
			try{
			if (wardIds.length() > 0) {
				wardIds.deleteCharAt(wardIds.length() - 1);
				// 京东API最大支持10个
				WareListResponse response = getWareListResponse(wardIds.toString());
				if (response != null && "0".equals(response.getCode())) {
					List<Ware> wareList = response.getWareList();
					List<JDProduct> jdProducts=new ArrayList<JDProduct>();;
					for (Ware w : wareList) {
						if (w != null) {
							List<Sku> skus = w.getSkus();
							try{
							  jdSKUInfo(skus, w,jdProducts);
							}catch(Exception e){ 
								logger.error("异常信息1"+e);
								e.printStackTrace();
							}
						}
					}
					//批量更新商品库存和价格
					if(jdProducts!=null && jdProducts.size()>0){
						logger.info("批量更新商品上下架与价格");
						try {
							jDWareServiceBean.updateProductOnlineBatch(jdProducts,jdProducts.size());
						} catch (Exception e) {
							logger.error("批量更新商品库存和价格异常,"+e);
							e.printStackTrace();
						}
					}
				}
			}
			}catch(Exception e){ 
				logger.error("异常信息2"+e);
				e.printStackTrace();
			}

		}
	}

	private void jdSKUInfo(List<Sku> skus, Ware w,List<JDProduct> jdProducts) throws JdException {
		// 取得商品低下的所有sku
		if (skus != null && skus.size() > 0) {
			long wareId = w.getWareId();
			int size = 1;
			// 京东商品sku总的库存
			int jdSkuTotalStockNum = 0;
			// 走秀渠道中心商品总库存
			int xiuSkuTotoalStockNum = 0;
			int totalSkuSize=0;
			if (skus != null && skus.size() > 0) {
				totalSkuSize=skus.size();
				Product product = null;
				for (Sku sku : skus) {
					if (sku == null) {
						continue;
					}
					// 京东上的outerId就是商品的sku码,唯一
					String outerId = sku.getOuterId();
					if(outerId == null || "".equals(outerId.trim())) {
						logger.error("京东商品ID:" + wareId + ",商品SKU码为=>" + outerId);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("xiusn", outerId);
						map.put("currentDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
						boolean isExist=false;// = jdLogServiceBean.isExist(map);
						if (!isExist) {// 日志表里不存在当前日期下的商品SKU
							String desc = "京东商家skuid为空";
							JdLog log = new JdLog();
							log.setWareid(wareId + "");
							log.setXiucode("空");
							log.setXiusn(outerId);
							syncInformation(log, desc, 1, 0);
						}
						continue;
					}
						long skuStock=sku.getStockNum();
						if(skuStock<0){
							skuStock=0;
						}
						jdSkuTotalStockNum = jdSkuTotalStockNum + (int) skuStock;
						String skuId = sku.getSkuId() + "";
						logger.info("京东商品ID" + wareId + ",京东 skuId" + skuId + ",商品SKU码" + outerId + " =======开始=========");
						QueryInventoryRequest queryInventoryRequest = new QueryInventoryRequest();
						// 商品的sku码
						queryInventoryRequest.setSkuCode(outerId);
						queryInventoryRequest.setChannelCode(GlobalConstants.CHANNEL_ID);
						QueryInventoryResponse qir = null;
						try{
						  qir =inventoryService.inventoryQueryBySkuCodeAndChannelCode(queryInventoryRequest);
						}catch(Exception e){
							logger.error("京东商品ID" + wareId + ",京东 skuId" + skuId + ",商品SKU码" + outerId + ",查询库存异常"+e);
							e.printStackTrace();
						}
						// 商品的走秀码(商品的sku码除去后4为剩下的为商品的走秀码)
						String xiucode =outerId;
						try{
						  xiucode = outerId.trim().substring(0,  outerId.trim().length() - 4);
						}catch(Exception e){
							logger.error("京东商品ID为" + wareId + ",走秀码为 " + xiucode + ",商品SKU码" + outerId + ",异常==="+e);
							xiucode=outerId;
							e.printStackTrace();
						}
						JdLog jdLog = new JdLog();
						jdLog.setWareid(w.getWareId() + "");
						jdLog.setXiucode(xiucode);
						jdLog.setXiusn(outerId);
						String desc = "";
						if (qir != null && qir.getCode().intValue() == 1) {
							logger.info("库存更新,京东商品ID" + wareId + ",走秀码" + xiucode + ",走秀SKU" + outerId);
							this.handleStock(sku, qir, jdLog, outerId.trim(), desc);
							Integer xiuSingleSkuStock=qir.getQty();
							if(xiuSingleSkuStock==null|| xiuSingleSkuStock<=0){
								xiuSingleSkuStock=0;
							}
							xiuSkuTotoalStockNum = xiuSkuTotoalStockNum + xiuSingleSkuStock;
						} else {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("xiusn", outerId);
							map.put("currentDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
							boolean isExist = jdLogServiceBean.isExist(map);
							if (!isExist) {// 日志表里不存在当前日期下的商品SKU
								desc = "商品SKU在渠道中心不存在";
								JdLog log = new JdLog();
								log.setWareid(wareId + "");
								log.setXiucode(xiucode);
								log.setXiusn(outerId);
								syncInformation(log, desc, 1, 0);
							}
							if(qir!=null){
								logger.error("走秀码" + xiucode + ",商品sku码" + outerId + ",不存在 ,查询库存出错:" + qir.toString());
							}
						}
						//根据走秀码获取走秀的商品
						try {
							product = productService.loadProduct(xiucode.trim());
						} catch (Exception e1) {
							logger.error("走秀码" + xiucode + ",调用商品中心接口方法loadProduct异常"+e1);
							e1.printStackTrace();
						}
						if (product != null) {
							Double xiuPrice = product.getPrdOfferPrice();//商品中心目前商品价格,单位为元
							logger.info("走秀码" + xiucode + ",走秀价"+xiuPrice+"元");
							//对接走秀活动价到京东
							if("true".equals(ParseProperties.IS_ACTIVITY_PRICE)){
								Double prdActivityPrice=product.getPrdActivityPrice();//走秀商品活动价(单位为元)
								if(prdActivityPrice!=null && prdActivityPrice>0){
									logger.info("走秀码" + xiucode + ",活动价"+prdActivityPrice+"元");
									xiuPrice=prdActivityPrice;
								}
							}
							if (xiuPrice == null) {
								logger.error("走秀价不能为null," + xiuPrice);
								continue;
							}
							Double xiuMarketPrice = product.getPrdListPrice();
							logger.info("走秀码" + xiucode + ",官网标准价" + xiuPrice + "元,市场价" + xiuMarketPrice + "元,京东商品ID" + wareId
									+ ",走秀sku码" + outerId);
							if(xiuMarketPrice==null ||  xiuMarketPrice<=0){
								logger.error("京东商品ID" + wareId +",市场价:"+ xiuMarketPrice+"元");
								int finalPrice = WebUtils.computePrice(new BigDecimal(xiuPrice));
								xiuMarketPrice=Double.parseDouble(WebUtils.buildMarketPrice(finalPrice)+"");
								logger.info("京东商品ID" + wareId +"市场价<=0,使用最后售价递归生成市场价:"+ xiuMarketPrice+",走秀价格"+xiuPrice+",京东最后售价"+finalPrice);
							}else{
								int finalPrice = WebUtils.computePrice(new BigDecimal(xiuPrice));
								//处理最终售价大于市场价的
								if(finalPrice>xiuMarketPrice){
									xiuMarketPrice=(double) WebUtils.buildMarketPrice(finalPrice);
									logger.info("京东商品ID: " + wareId +"市场价:"+ xiuMarketPrice+",走秀价格为"+xiuPrice+",京东最后售价为"+finalPrice+",最终售价大于市场价");
								}
								
							}
							
							logger.info("京东商品ID-->:" + wareId +",市场价:"+ xiuMarketPrice+"元");
							
							try{
								String jdSkuPrice = sku.getJdPrice();
							    this.handlePrice(jdLog, w, xiuPrice, xiuMarketPrice, outerId.trim(), skuId, desc,size,totalSkuSize,xiucode,jdSkuPrice);
							}catch(Exception e){
								logger.error("修改价格异常"+e);
								e.printStackTrace();
							}
							// 到最后一个sku时更新商品销售状态
							if (size == totalSkuSize) {
								logger.info("京东商品ID" + wareId + ",到最后一个sku时更新商品销售状态与总库存");
								boolean productIsExtists = jDWareServiceBean.wareIdIsExistsNation(wareId + "".trim());
								// 本地存在
								if (!productIsExtists) {
										//只同步一次
									 // if(synProductOnLineStatusToNative==1){
										  JDProduct jdProduct=new JDProduct();
										//走秀商品上下价状态 (0下架，1上架)
										  int onlineStatus =0;
											String jdWareStatus=w.getWareStatus();
											if(jdWareStatus!=null){
												if(jdWareStatus.startsWith("ON_SALE")){//包含上架,和上架审核中
													onlineStatus = 1;
												}else if ("CUSTORMER_DOWN".equalsIgnoreCase(jdWareStatus)
														||  jdWareStatus.startsWith("SYSTEM_DOWN")) {
													onlineStatus = 2;
												}
												//商品销售状态：0:未上架,1:在售,2:下架
												jdProduct.setOnLineStatus(onlineStatus);
											}
											//用走秀的总库存，更新本地数据 jdProduct.setStocknum(jdSkuTotalStockNum+"");这里不能用京东sku总库存，有可能为0
											if(xiuSkuTotoalStockNum<=0){
												xiuSkuTotoalStockNum=0;
											}
											 jdProduct.setStocknum(xiuSkuTotoalStockNum+"");
											//进货价等于走秀价
											jdProduct.setCostprice(product.getPrdOfferPrice()+"");
											jdProduct.setJdWareId(wareId+"");
											logger.info("京东商品ID"+wareId+",供应商编码"+product.getSupplierCode()+",京东sku总库存"+jdSkuTotalStockNum);
											if(jdProducts!=null){
												logger.info("京东商品ID"+wareId+",商品添加到集合,更新商品销售状态与价格,状态为 "+onlineStatus);
												jdProducts.add(jdProduct);
											}
									 // }
								}
							}
						}else {
							logger.info("在商品中心不到走秀码为" + xiucode + ",商品的");
						}
						logger.info("==========京东 skuId为" + skuId + ",走秀码为:" + outerId + "=======结束=========");
					size++;
				}
				// 更新商品总库存(目前更新不了商品总库存)，做下架处理
				String wareStatus = w.getWareStatus();
				// 渠道中心商品总库存
				if (xiuSkuTotoalStockNum == 0) {
					logger.info("京东商品ID" + wareId +",商品sku渠道总库存为0,将商品下架,京东上的总库存" + jdSkuTotalStockNum + ",wareStatus:" + wareStatus);
				}
				long jdWareTotalStock=w.getStockNum();
				logger.info("京东商品ID" + wareId +",京东上sku总库存"+jdSkuTotalStockNum+",京东商品总库存:"+jdWareTotalStock+",京东商品状态:"+wareStatus);
				//走秀总的库存,或者，京东商品总库存不为0
				String xiuCode=product.getXiuSN();
				if(product!=null && xiuCode!=null){
					//--------------商品黑名单拦截,不对接商品上下架状态-----------------------
					 boolean isBlack=onLineBlackProductServiceBean.isOnLineBlack(xiuCode.trim());
					 if(!isBlack){ //不是黑名单中的商品,需要对接上下架状态到京东
							//0下架，1上架
							String onSale=product.getOnSale();
							logger.info("京东商品ID" + wareId +",走秀目前商品上下架状态:"+onSale+", 0下架，1上架");
							if(onSale!=null && wareStatus!=null){
								//商品在走秀中是上架的
								if("1".equals(onSale)){
									//包含不是上架,与不是上架审核中,和不是从未上架的
									if(!wareStatus.startsWith("ON_SALE") && !wareStatus.startsWith("NEVER_UP") ){
										//将走秀在京东商品的商品上架
										updateJdWareOnSale(wareId+"");  
									}
								}else{
									//商品在京东上是在售的,也包含在售审核中的商品
									if(wareStatus.startsWith("ON_SALE")){
										//将走秀在京东商品的商品下架
										updateJdWareOnOffSale(wareStatus,wareId+"");
									}
								}
							}
					 }else{
						logger.info("========京东商品ID" + wareId +",走秀码:"+xiuCode+",是黑名单中的商品 ");
					 }
			
					 
				}
			}
		}
	}
	 /**
     * 更新京东商品的状态为在售状态
     */
    private void updateJdWareOnSale(String wareId){
    	WareUpdateListingRequest updateListingRequest = new WareUpdateListingRequest();
		updateListingRequest.setWareId(wareId+"");
		WareUpdateListingResponse res = null;
		String code="-1";
		try {
			res = client.execute(updateListingRequest);
			 logger.info("京东商品ID为:" + wareId + ",系统上架"); 
			if(res!=null){
				 code=res.getCode();   //商家类目没有设置扣点,京东错误码为:11100010|| "11100010".equals(code)
				 if("0".equals(code)){ 
					   logger.info("京东商品ID" + wareId + ",系统上架成功,京东错误码"+code); 
						Map<String, Object> maps = new HashMap<String, Object>();
						maps.put("JdwareId", wareId);
						//1:在售
					    maps.put("onlineStatus", 1);
						int num=jDWareServiceBean.updateProductOnline(maps);
						logger.info("京东商品ID" + wareId + ",系统上架,更新本地数据库上下架影响的记录数为:"+num); 
				 }else{
					 logger.error("京东商品ID" + wareId + ",系统上架失败,京东错误码为:"+code+",错误信息为:"+res.getZhDesc()); 
				 }
			}
		}catch(Exception e){
			logger.error("京东商品ID" + wareId + ",系统上架异常"+e);
			e.printStackTrace();
		}
    }
   
    /**
     * 更新京东商品的状态为下架状态
     * @param wareId
     */
    private void  updateJdWareOnOffSale(String wareStatus,String wareId){
    	WareUpdateDelistingRequest wareUpdateDelistingRequest = new WareUpdateDelistingRequest();
		wareUpdateDelistingRequest.setWareId(wareId+"");
		WareUpdateDelistingResponse res =null;
		String code="-1";
		try {
			 res = client.execute(wareUpdateDelistingRequest);
			 logger.info("京东商品ID" + wareId + "系统下架"); 
			if(res!=null){
				 code=res.getCode();
				 if("0".equals(code)){
					 logger.info("京东商品ID" + wareId + ",系统下架成功,京东错误码:"+code ); 
					 //不为自主下架或自主下架审核中
					 if(!wareStatus.startsWith("NEVER_UP")){
						 Map<String, Object> maps = new HashMap<String, Object>();
							maps.put("JdwareId", wareId);
							//1:下架
						    maps.put("onlineStatus", 2);
							int num=jDWareServiceBean.updateProductOnline(maps);
							logger.info("京东商品ID" + wareId + ",系统下架,更新本地数据库上下架影响的记录数为:"+num); 
					 }
				 }else{
					 logger.error("京东商品ID" + wareId + ",系统下架失败,京东错误码为:"+code+",错误信息为:"+res.getZhDesc()); 
				 }
			}
		}catch(Exception e){
			logger.error("京东商品ID" + wareId + ",系统下架异常"+e);
			e.printStackTrace();
		}
    	
    }

	/***
	 *  商品下架
	 * @param wareId
	 * @param wareStatus
	 * @param title
	 * @param skutotalStockNum
	 * @param skuStockNum
	 */
	private void updateWareOnOffSale(String wareId, String wareStatus,String title,int skutotalStockNum,int skuStockNum) {
		if (wareStatus == null || "".equals(wareStatus.trim())) {
			return;
		}
		logger.info("下架的京东商品ID=" + wareId + ",状态为:" + wareStatus);
		try {
			if ("ON_SALE".equalsIgnoreCase(wareStatus)) {
				WareUpdateDelistingRequest wareUpdateDelistingRequest = new WareUpdateDelistingRequest();
				wareUpdateDelistingRequest.setWareId(wareId);
				WareUpdateDelistingResponse res = client.execute(wareUpdateDelistingRequest);
				if ("0".equals(res.getCode())) {
					logger.info("京东商品ID为:" + wareId + ",系统下架成功");
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("stockNum", skuStockNum);
					maps.put("JdwareId", wareId);
				    maps.put("onlineStatus", 2);
					jDWareServiceBean.updateProductOnline(maps);
				} else {
					if (res != null) {
						logger.info("京东商品ID为:" + wareId + ",系统下架失败,京东失败信息:" + res.getZhDesc());
					}
				}
			} 
			
		} catch (JdException e) {
			logger.error("京东商品ID为:" + wareId + ",商品sku渠道总库存为0,将商品下架,系统下架异常");
			e.printStackTrace();
		}
	}


	/**
	 * 商品上架
	 * @param wareId
	 */
	private void updateWareOnSale(String wareId) {
		WareGetRequest  wareGetRequest=new WareGetRequest();
		wareGetRequest.setWareId(wareId);
		wareGetRequest.setFields("ware_id,ware_status");
		WareGetResponse response=null;
		try {
			response = client.execute(wareGetRequest);
		} catch (JdException e1) {
			logger.error("查询商品状态异常:"+e1);
			e1.printStackTrace();
			 response=null;
		}
		if(response!=null){
			Ware ware=response.getWare();
			if(ware!=null){
				String wareStatus=ware.getWareStatus();
				if (!"ON_SALE".equalsIgnoreCase(wareStatus)) {
					//将商品上架
					WareUpdateListingRequest updateListingRequest = new WareUpdateListingRequest();

					updateListingRequest.setWareId(wareId);
					WareUpdateListingResponse res = null;
					try {
						res = client.execute(updateListingRequest);
						if ("0".equals(res.getCode())) {
							logger.info("京东商品ID为:" + wareId + ",系统上架成功");
							
						} else {

							if (res != null) {
								logger.info("京东商品ID为:" + wareId + ",系统上架失败,京东失败信息:" + res.getZhDesc()+",商品状态:"+wareStatus+",京东错误码为:"+res.getCode());
							}
						}
					} catch (JdException e) {
						logger.error("京东商品ID为:" + wareId + ",商品sku渠道总库存为0,将商品上架,系统上架异常");

						e.printStackTrace();
					}
				}
			}
		
		}
	}

}
