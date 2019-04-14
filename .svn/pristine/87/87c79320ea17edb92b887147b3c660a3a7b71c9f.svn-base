package com.xiu.jd.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jd.open.api.sdk.domain.order.CouponDetail;
import com.xiu.jd.constants.GlobalConstants;
import com.xiu.jd.utils.ParseJDOauthProperties;
import com.xiu.osc.client.fx.dto.FxOrderActive;

public class JdCouponCalc {
	private static final Logger logger = Logger.getLogger(JdCouponCalc.class);

	/**
	 *   一个商品的单品优惠
	 * @param jdCouList
	 *            京东优惠列表
	 * @param skuId
	 *            京东skuId(当前商品的sku)
	 * @param itemTotalAmount
	 *            订单总金额是优惠前的金额,不包含运费
	 * @param discountFee1
	 *            订单总优惠(商家总的优惠)sellerDiscount
	 * @param itemAmount
	 *           京东的商品原价
	 * @param itemQty
	 *            单个商品用户购买的数量 
	 *@param itemCodes
	 *            走秀码
	 * 
	 * @param activeList
	 *           活动信息列表
	 *  */         
	public static Map<String, Object> calcItemAmountByItemOne(List<CouponDetail> jdCouList, String skuId, long itemTotalAmount, long discountFee1, long itemAmount,
			long itemQty, String itemCodes, List<FxOrderActive> activeList) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 1.JD没有提供活动信息
		if (jdCouList == null || jdCouList.size() == 0 || itemTotalAmount == 0) {
			return null;
		}

		// 2.JD针对订单级别的活动，或是理解成 没有多个活动，也不存在针对于单品的活动
		if (jdCouList.size() == 1) {
			CouponDetail couponDetail = jdCouList.get(0);
			if(couponDetail==null){
				return null;
			}
			if (!StringUtils.isEmpty(couponDetail.getSkuId())) {
				//如果不是 该商品享受的优惠
				if(!couponDetail.getSkuId().equals(skuId) || StringUtils.isEmpty(couponDetail.getCouponType())) {
					return null;
				}
				
				// TODO XXX 是我们的活动
				if(couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_100) 
						|| couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_30)
						|| couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_35)
						){
					// 如果为null，我们认为他是针对于订单级别做的活动
					long sharePrice = 0;
					long favorableAmount = 0;

				   /*	// 京东订单总优惠多少
					long jdTotalCoupon = toLongMoney("京东总优惠", couponDetail.getCouponPrice());
					// 总优惠比重
					double rate = (double) jdTotalCoupon / itemTotalAmount;
					favorableAmount = (long) (rate * itemAmount / itemQty);// 单品优惠，
					sharePrice = itemAmount - favorableAmount;// 分摊价格
			
                   */					
					if(StringUtils.isEmpty(couponDetail.getCouponPrice()) ) {
						return null;
					}
					
					// 京东订单总优惠多少
					long jdTotalCoupon = toLongMoney("商品sku优惠金额有成已数量的",couponDetail.getCouponPrice());
					favorableAmount = jdTotalCoupon/itemQty;// 单品优惠，
					sharePrice = itemAmount - favorableAmount;// 分摊价格
					map.put(GlobalConstants.OSC_ORDER_PRICE_ITEM_FAV1, favorableAmount);
					map.put(GlobalConstants.OSC_ORDER_PRICE_ITEM_SHARE, sharePrice);

					/** 组装活动数据 */
					if (activeList != null && activeList.size() == 0) {
						FxOrderActive active = new FxOrderActive();
						active.setActiveId(0l);
						active.setActiveLevel(1);
						active.setActiveKind(9);
						//走秀店
						if("jd_xiu".equalsIgnoreCase(ParseJDOauthProperties.ORDER_PLATFORM_TYPE)){
							active.setActivityType(5);
						}else{
							//Ebay店
							active.setActivityType(2);
						}
						
						active.setCardNumber(null);
						active.setActiveStatus(0);
						active.setActiveStartTime(null);
						active.setActiveEndTime(null);
						active.setActiveRemark(couponDetail.getCouponType());
						active.setActiveTitle(couponDetail.getCouponType());
						active.setFavorableAmount(jdTotalCoupon);
						active.setGiftItemCode("");// SKu级别
						active.setItemCode(itemCodes);
						active.setSalesActivityType(1); // 这里是针对订单级别的设置
						activeList.add(active);
					} else {
						if (activeList != null && activeList.size() > 0) {
							FxOrderActive active = activeList.get(0);
							active.setItemCode(active.getItemCode() + ";" + itemCodes);
						}
					}
					return map;
				}
				
			}
		}
		// 3.JD针对订单做的多个活动
		// 4.JD的组合活动处理方式
		return null;
	}
	
	/**
	 *   多个单品的优惠
	 * @param jdCouList
	 *            京东优惠列表
	 * @param skuId
	 *            京东skuId(当前商品的sku)
	 * @param itemTotalAmount
	 *            订单总金额是优惠前的金额,不包含运费
	 * @param discountFee1
	 *            订单总优惠(商家总的优惠)sellerDiscount
	 * @param itemAmount
	 *           京东的商品原价
	 * @param itemQty
	 *            单个商品用户购买的数量 
	 *@param itemCodes
	 *            走秀码
	 * 
	 * @param activeList
	 *           活动信息列表
	 *  */ 
	public static Map<String, Object> calcItemAmountByItemBatch2(List<CouponDetail> jdCouList, String skuId, long itemTotalAmount, long discountFee1, long itemAmount,
			long itemQty, String itemCodes, List<FxOrderActive> activeList,List<String> proudctCoupon) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 1.JD没有提供活动信息
		if (jdCouList == null || jdCouList.size() == 0 || itemTotalAmount == 0) {
			return null;
		}

		// 2.JD针对订单级别的活动，或是理解成 没有多个活动，也不存在针对于单品的活动
		if (jdCouList.size() > 1) {
			// liweibiao add 针对订单做的活动
		
			long favorableAmountOrder = 0;
			long sharePriceOrder = 0;
			//boolean isProductAtive=false;
			for (CouponDetail couponDetail : jdCouList) {
				if (couponDetail == null) {
					continue;
				}
				Map<String, Object> orderCoupon=null;
				if (StringUtils.isEmpty(couponDetail.getSkuId())) {
					orderCoupon =calcOrderCoupon(jdCouList, skuId, itemTotalAmount, discountFee1, itemAmount, itemQty, itemCodes, activeList);
					if(orderCoupon!=null && orderCoupon.size()>0){
						//多级订单活动
						favorableAmountOrder=favorableAmountOrder+(Long)orderCoupon.get(GlobalConstants.OSC_ORDER_PRICE_ITEM_FAV1);
						//isProductAtive=true;
					}
				}
				
			}
			
			for (CouponDetail couponDetail : jdCouList) {
				if (couponDetail == null) {
					continue;
				}
/*				if (StringUtils.isEmpty(couponDetail.getSkuId())) {
					//liweibiao 20140626
					orderCoupon =calcOrderCoupon(jdCouList, skuId, itemTotalAmount, discountFee1, itemAmount, itemQty, itemCodes, activeList);
					// continue;
				}*/

				if (!couponDetail.getSkuId().equals(skuId)
						|| StringUtils.isEmpty(couponDetail.getCouponType())
						
				) {
					// 如果不是 该商品享受的优惠
					continue;
				}

				// TODO 是我们的活动
				if (couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_100)
						|| couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_30)
						|| couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_35)
				      ) {
				
					// 如果为null，我们认为他是针对于订单级别做的活动
					long sharePrice = 0;
					long favorableAmount = 0;

	
					if(StringUtils.isEmpty(couponDetail.getCouponPrice()) ) {
						continue;
					}
					
					// 京东订单总优惠多少
					long jdTotalCoupon = toLongMoney("商品sku优惠金额有成已数量的",couponDetail.getCouponPrice());
				
					favorableAmount = jdTotalCoupon/itemQty+favorableAmountOrder;// 单品优惠，
					sharePrice = itemAmount - favorableAmount+sharePriceOrder;// 分摊价格
					
					if(map!=null && map.size() > 0) {
						//订单中商品分摊价
						sharePrice += (Long) map.get(GlobalConstants.OSC_ORDER_PRICE_ITEM_SHARE);
						//商品优惠金额
						favorableAmount += (Long) map.get(GlobalConstants.OSC_ORDER_PRICE_ITEM_FAV1);
					}
					
					map.put(GlobalConstants.OSC_ORDER_PRICE_ITEM_FAV1,favorableAmount);
					map.put(GlobalConstants.OSC_ORDER_PRICE_ITEM_SHARE,sharePrice);

					/** 组装活动数据 */
					if (activeList != null && proudctCoupon.size()==0) {
						proudctCoupon.add("proudctCoupon");
						FxOrderActive active = new FxOrderActive();
						active.setActiveId(0l);
						active.setActiveLevel(1);
						active.setActiveKind(9);
						if ("jd_xiu".equalsIgnoreCase(ParseJDOauthProperties.ORDER_PLATFORM_TYPE)) {
							active.setActivityType(5);// 走秀店
						} else {
							active.setActivityType(2);// Ebay店
						}
						active.setCardNumber(null);
						active.setActiveStatus(0);
						active.setActiveStartTime(null);
						active.setActiveEndTime(null);
						active.setActiveRemark(couponDetail.getCouponType());
						active.setActiveTitle(couponDetail.getCouponType());
						active.setFavorableAmount(jdTotalCoupon);
						active.setGiftItemCode("");// SKu级别
						active.setItemCode(itemCodes);  //111
						active.setSalesActivityType(1); // 这里是针对订单级别的设置
						activeList.add(active);
					} else {
					if (activeList != null) {
							for(FxOrderActive active : activeList) {
								if(active==null){
									continue;
								}
								logger.info("skuId"+skuId+"List<FxOrderActive> activeList商品编码为:"+active.getItemCode());
								logger.info("skuId"+skuId+"传进来的商品编码:"+itemCodes);
								if(!StringUtils.isEmpty(active.getItemCode()) && !active.getItemCode().contains(itemCodes)) {
									active.setItemCode(active.getItemCode() + ";"+  itemCodes);
									break;
								}
							}
						}
					}
					continue;
				}
			}
		}
		// 3.JD针对订单做的多个活动
		// 4.JD的组合活动处理方式
		return map;
	}
	
	
	public static Map<String, Object> calcItemAmountByItemBatch(List<CouponDetail> jdCouList, String skuId, long itemTotalAmount, long discountFee1, long itemAmount,
			long itemQty, String itemCodes, List<FxOrderActive> activeList,List<String> proudctCoupon)
	  {
		Map<String, Object> map = new HashMap<String, Object>();

	    if ((jdCouList == null) || (jdCouList.size() == 0) || (itemTotalAmount == 0L)) {
	      return null;
	    }

	    if (jdCouList.size() > 1)
	    {
	      for (CouponDetail couponDetail : jdCouList) {
	        if (couponDetail == null)
	        {
	          continue;
	        }
	        if (StringUtils.isEmpty(couponDetail.getSkuId()))
	        {
	          continue;
	        }
	        if ((!couponDetail.getSkuId().equals(skuId)) || 
	          (StringUtils.isEmpty(couponDetail.getCouponType())))
	        {
	          continue;
	        }
	       if ((!couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_100)) && 
	                    (!couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_30)) && 
	                    (!couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_35)))
	       {
	          continue;
	       }
	        long sharePrice = 0L;
	        long favorableAmount = 0L;

	        if (StringUtils.isEmpty(couponDetail.getCouponPrice()))
	        {
	          continue;
	        }

	        long jdTotalCoupon = toLongMoney("商品sku优惠金额有成已数量的", couponDetail.getCouponPrice());
	        favorableAmount = jdTotalCoupon / itemQty;
	        sharePrice = itemAmount - favorableAmount;

	        if ((map != null) && (map.size() > 0))
	        {
	          sharePrice += ((Long)map.get("sharePrice")).longValue();

	          favorableAmount += ((Long)map.get("favorableAmount")).longValue();
	        }

	        map.put("favorableAmount", Long.valueOf(favorableAmount));
	        map.put("sharePrice", Long.valueOf(sharePrice));

	        if ((activeList != null) && (activeList.size() == 0)) {
	          proudctCoupon.add("proudctCoupon");
	          FxOrderActive active = new FxOrderActive();
	          active.setActiveId(0L);
	          active.setActiveLevel(1);
	          active.setActiveKind(9);
	          if ("jd_xiu".equalsIgnoreCase(ParseJDOauthProperties.ORDER_PLATFORM_TYPE))
	            active.setActivityType(5);
	          else {
	            active.setActivityType(2);
	          }
	          active.setCardNumber(null);
	          active.setActiveStatus(0);
	          active.setActiveStartTime(null);
	          active.setActiveEndTime(null);
	          active.setActiveRemark(couponDetail.getCouponType());
	          active.setActiveTitle(couponDetail.getCouponType());
	          active.setFavorableAmount(jdTotalCoupon);
	          active.setGiftItemCode("");
	          active.setItemCode(itemCodes);

	          active.setSalesActivityType(1);
	          activeList.add(active);
	        }
	        else if ((activeList != null) && (activeList.size() > 0)) {
	          for (FxOrderActive active : activeList) {
	            if (active == null) {
	              continue;
	            }
	            logger.info("skuId" + skuId + "List<FxOrderActive> activeList商品编码为:" + active.getItemCode());
	            logger.info("skuId" + skuId + "传进来的商品编码:" + itemCodes);
	            if ((!StringUtils.isEmpty(active.getItemCode())) && (!active.getItemCode().contains(itemCodes))) {
	              active.setItemCode(active.getItemCode() + ";" + itemCodes);
	              break;
	            }

	          }

	        }

	      }

	    }

	    return map;
	  }
	
	/**
	 *   订单级别的优惠
	 * @param jdCouList
	 *            京东优惠列表
	 * @param skuId
	 *            京东skuId(当前商品的sku)
	 * @param itemTotalAmount
	 *            订单总金额是优惠前的金额,不包含运费
	 * @param discountFee1
	 *            订单总优惠(商家总的优惠)sellerDiscount
	 * @param itemAmount
	 *           京东的商品原价
	 * @param itemQty
	 *            单个商品用户购买的数量 
	 *@param itemCodes
	 *            走秀码
	 * 
	 * @param activeList
	 *           活动信息列表
	 *  */ 
	public static  Map<String, Object> calcItemAmount(List<CouponDetail> jdCouList,
			String skuId, long itemTotalAmount, long discountFee1,
			long itemAmount, long itemQty, String itemCodes,
			List<FxOrderActive> activeList) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 1.JD没有提供活动信息
		if (jdCouList == null || jdCouList.size() == 0 || itemTotalAmount == 0) {
			return null;
		}

		// 2.JD针对订单级别的活动，或是理解成 没有多个活动，也不存在针对于单品的活动
		if (jdCouList.size() == 1) {
			CouponDetail couponDetail = jdCouList.get(0);
			if (couponDetail == null) {
				return null;
			}
			
			//过滤到京东的礼品卡和京东券优惠
			if (couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_41)
				||couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_52) 
			    	//20140218新添加的
				||couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_34)
				||couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_39)	
			 ){
				
			      return null;		
		     }
			
			// 订单号不存在，不认为有活动
			if (StringUtils.isEmpty(couponDetail.getOrderId())) {
				return null;
			}

			if (StringUtils.isEmpty(couponDetail.getSkuId())) {
				// 如果为null，我们认为他是针对于订单级别做的活动
				long sharePrice = 0;
				long favorableAmount = 0;

				// 京东订单总优惠多少
				long jdTotalCoupon = toLongMoney("京东总优惠",
						couponDetail.getCouponPrice());
				// 总优惠比重
				double rate = (double) jdTotalCoupon / itemTotalAmount;
				favorableAmount = (long) (rate * itemAmount);// 单品优惠
				sharePrice = itemAmount - favorableAmount;// 分摊价格
				map.put(GlobalConstants.OSC_ORDER_PRICE_ITEM_FAV1,
						favorableAmount);
				map.put(GlobalConstants.OSC_ORDER_PRICE_ITEM_SHARE, sharePrice);

				/** 组装活动数据 */
				if (activeList != null && activeList.size() == 0) {
					FxOrderActive active = new FxOrderActive();
					active.setActiveId(0l);
					active.setActiveLevel(1);
					active.setActiveKind(9);
					// 走秀店
					if ("jd_xiu"
							.equalsIgnoreCase(ParseJDOauthProperties.ORDER_PLATFORM_TYPE)) {
						active.setActivityType(5);
					} else {
						// Ebay店
						active.setActivityType(2);
					}

					active.setCardNumber(null);
					active.setActiveStatus(0);
					active.setActiveStartTime(null);
					active.setActiveEndTime(null);
					active.setActiveRemark(couponDetail.getCouponType());
					active.setActiveTitle(couponDetail.getCouponType());
					active.setFavorableAmount(jdTotalCoupon);
					active.setGiftItemCode("");// SKu级别
					active.setItemCode(itemCodes);
					active.setSalesActivityType(1); // 这里是针对订单级别的设置
					activeList.add(active);
				} else {
					if (activeList != null && activeList.size() > 0) {
						FxOrderActive active = activeList.get(0);
						active.setItemCode(active.getItemCode() + ";"
								+ itemCodes);
					}
				}
				return map;
			} else {
				// 这里处理针对 商品级别做的活动，而且订单只有一个商品的->先这们理解
			}
		}
		// 3.JD针对订单做的多个活动
		// 4.JD的组合活动处理方式
		return null;
	}

	/**
	 * 转为走秀没小数点long
	 * 
	 * @param fieldName
	 *            属性名称
	 * @param str
	 * @return
	 */
	private static long toLongMoney(String fieldName, String price) {
		long money = 0L;
		if (price == null || "".equals(price.trim())) {
			return money;
		}
		// price = price.replaceAll("\\.", "");
		Double dbMoney = Double.valueOf(price);
		try {
			money = (long) (dbMoney.doubleValue() * 100);
		} catch (NumberFormatException e) {
			return money;
		}
		logger.info("长整形,属性名称为" + fieldName + "   输入的价格为 " + price
				+ "  转换为long后为   " + money);
		return money;
	}
	
	/**
	 * 订单级别的优惠
	 * @param jdCouList
	 * @param skuId
	 * @param itemTotalAmount
	 * @param discountFee1
	 * @param itemAmount
	 * @param itemQty
	 * @param itemCodes
	 * @param activeList
	 * @return
	 */
	public static Map<String, Object> calcOrderCoupon(List<CouponDetail> jdCouList,
			String skuId, long itemTotalAmount, long discountFee1,
			long itemAmount, long itemQty, String itemCodes,
			List<FxOrderActive> activeList){
		Map<String, Object> map = new HashMap<String, Object>();
		for(CouponDetail couponDetail: jdCouList){
			//过滤到京东的礼品卡和京东券优惠
			if (couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_41)
				||couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_52) 
			    	//20140218新添加的
				||couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_34)
				||couponDetail.getCouponType().startsWith(GlobalConstants.JD_COUPON_TYPE_PREFIX_39)	
			 ){
				
			      return null;		
		     }
			
			// 订单号不存在，不认为有活动
			if (StringUtils.isEmpty(couponDetail.getOrderId())) {
				return null;
			}

			if (StringUtils.isEmpty(couponDetail.getSkuId())) {
				// 如果为null，我们认为他是针对于订单级别做的活动
				long sharePrice = 0;
				long favorableAmount = 0;

				// 京东订单总优惠多少
				long jdTotalCoupon = toLongMoney("京东总优惠",
						couponDetail.getCouponPrice());
				// 总优惠比重
				double rate = (double) jdTotalCoupon / itemTotalAmount;
				favorableAmount = (long) (rate * itemAmount);// 单品优惠，
				sharePrice = itemAmount - favorableAmount;// 分摊价格
				map.put(GlobalConstants.OSC_ORDER_PRICE_ITEM_FAV1,
						favorableAmount);
				map.put(GlobalConstants.OSC_ORDER_PRICE_ITEM_SHARE, sharePrice);
				
				/** 组装活动数据 */
				if (activeList != null) {
					FxOrderActive active = new FxOrderActive();
					active.setActiveId(0l);
					active.setActiveLevel(2); //订单的获取级别为2
					active.setActiveKind(9);
					// 走秀店
					if ("jd_xiu"
							.equalsIgnoreCase(ParseJDOauthProperties.ORDER_PLATFORM_TYPE)) {
						active.setActivityType(5);
					} else {
						// Ebay店
						active.setActivityType(2);
					}

					active.setCardNumber(null);
					active.setActiveStatus(0);
					active.setActiveStartTime(null);
					active.setActiveEndTime(null);
					active.setActiveRemark(couponDetail.getCouponType());
					active.setActiveTitle(couponDetail.getCouponType());
					active.setFavorableAmount(jdTotalCoupon);
					active.setGiftItemCode("");// SKu级别
					active.setItemCode(itemCodes);
					active.setSalesActivityType(1); // 这里是针对订单级别的设置
					activeList.add(active);
				} 
				return map;
			}
		}
		return null;
	}
}
