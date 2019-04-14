package com.xiu.jd.schedule;

import java.io.Serializable;
import java.util.List;

import com.jd.open.api.sdk.domain.order.CouponDetail;

/**
 * 京东优惠实体
 * @author root
 *
 */
public class JdCouponBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6447358917438765279L;
	
	
	//userTotalPayPrice+orderPayment+balanceUsed(京东的优惠+京东订单应付金额+京东余额支付的金额)
	private long userTotalPayPrice=0; 
	private List<CouponDetail> couponDetails;//走秀在京东上走秀自己的优惠列表
	public long getUserTotalPayPrice() {
		return userTotalPayPrice;
	}
	public void setUserTotalPayPrice(long userTotalPayPrice) {
		this.userTotalPayPrice = userTotalPayPrice;
	}
	public List<CouponDetail> getCouponDetails() {
		return couponDetails;
	}
	public void setCouponDetails(List<CouponDetail> couponDetails) {
		this.couponDetails = couponDetails;
	}
	
	
}
