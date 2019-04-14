package com.xiu.jd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiu.jd.service.GoodsNotificationService;
import com.xiu.jd.service.MessageListener;

public class GoodsModifiedMessageListener implements MessageListener {
	
	private static Logger logger = LoggerFactory.getLogger(GoodsModifiedMessageListener.class);

	@Autowired
	private GoodsNotificationService GoodsNotificationService;
	
	@Override
	public void handleMessage(TextMessage textMessage) {
		logger.debug("====== Textmessage: {}", textMessage);
		
//		String messageHeader = null;
		String messageBody = null;
		try {
//			messageHeader = textMessage.getStringProperty("mykey");
			messageBody = textMessage.getText();
		} catch (JMSException e) {
			logger.error("处理JMS消息出错", e);
		}
		
		this.handleGoodsChangeMessage(messageBody);
	}
	
	// 处理商品变动
	private void handleGoodsChangeMessage(String message) {
		List<ProductMqMessageModel> products = null;
		try {
			products = parseMessge(message);
		} catch (Exception e) {
			logger.error("解析消息出错", e);
			return;
		}
		logger.debug("上架商品:{}" + products);
		if (products != null && products.size() > 0) {
			for (ProductMqMessageModel product : products) {
				this.startGoodsNotification(product.getGoodSn());
			}
		}
	}
	
	// 处理消息，解析出上架的商品信息
	@SuppressWarnings("unchecked")
	private List<ProductMqMessageModel> parseMessge(String message) throws Exception {
		logger.info("开始解析消息：{}", message);
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(message);
		} catch (DocumentException e) {
			logger.error("解析jms xml消息出错", e);
		}
		
		Element root = doc.getRootElement();


		List<ProductMqMessageModel> onSaleGoods = new ArrayList<ProductMqMessageModel>();
		
		List<Element> ps = root.elements("productMqMessageModel");
			for (Element p : ps) {
			String onSale = p.elementText("onSale");
			
			// 上架信息
			if ("1".equals(onSale)) {
				ProductMqMessageModel product = new ProductMqMessageModel();
				product.setGoodSn(p.elementText("goodSn"));
				product.setName(p.elementText("name"));
				onSaleGoods.add(product);
			}
		}
		
		return onSaleGoods;
	}
		
	// 调用到货通知逻辑，将商品的变动通知给客户
	private void startGoodsNotification(String goodsSn) {
		if (goodsSn == null) {
			return;
		}
		
		this.GoodsNotificationService.notifyCustomers(goodsSn);
	}
	
	public static void main(String[] args) throws Exception {
		String xml = 
				"<list>" +
				"  <productMqMessageModel>" +
				"    <goodSn>60000025</goodSn>" +
				"    <name></name>" +
				"    <preName></preName>" +
				"    <postName></postName>" +
				"    <xiuPrice></xiuPrice>" +
				"    <marketPrice></marketPrice>" +
				"    <activityPrice></activityPrice>" +
				"    <activityType></activityType>" +
				"    <onSale>1</onSale>" +
				"    <offShow></offShow>" +
				"    <onSaleTime></onSaleTime>" +
				"    <keywords></keywords>" +
				"    <brandId></brandId>" +
				"    <catIds></catIds>" +
				"  </productMqMessageModel>" +
				"  <productMqMessageModel>" +
				"    <goodSn>60000026</goodSn>" +
				"    <name></name>" +
				"    <preName></preName>" +
				"    <postName></postName>" +
				"    <xiuPrice></xiuPrice>" +
				"    <marketPrice></marketPrice>" +
				"    <activityPrice></activityPrice>" +
				"    <activityType></activityType>" +
				"    <onSale>1</onSale>" +
				"    <offShow></offShow>" +
				"    <onSaleTime></onSaleTime>" +
				"    <keywords></keywords>" +
				"    <brandId></brandId>" +
				"    <catIds></catIds>" +
				"  </productMqMessageModel>" +
				"</list>";
		
		GoodsModifiedMessageListener j = new GoodsModifiedMessageListener();
		logger.info(j.parseMessge(xml).toString());
	}
	
	
	/**
	 * jms 消息
	 * @author zany@buzheng.org
	 *
	 */
	class ProductMqMessageModel {
		private String goodSn;
		private String name;
		private String preName;
		private String postName;
		private String xiuPrice;
		private String marketPrice;
		private String activityPrice;
		private String activityType;
		private String onSale;
		private String offShow;
		private String onSaleTime;
		private String keywords;
		private String brandId;
		private String catIds;
		public String getGoodSn() {
			return goodSn;
		}
		public void setGoodSn(String goodSn) {
			this.goodSn = goodSn;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPreName() {
			return preName;
		}
		public void setPreName(String preName) {
			this.preName = preName;
		}
		public String getPostName() {
			return postName;
		}
		public void setPostName(String postName) {
			this.postName = postName;
		}
		public String getXiuPrice() {
			return xiuPrice;
		}
		public void setXiuPrice(String xiuPrice) {
			this.xiuPrice = xiuPrice;
		}
		public String getMarketPrice() {
			return marketPrice;
		}
		public void setMarketPrice(String marketPrice) {
			this.marketPrice = marketPrice;
		}
		public String getActivityPrice() {
			return activityPrice;
		}
		public void setActivityPrice(String activityPrice) {
			this.activityPrice = activityPrice;
		}
		public String getActivityType() {
			return activityType;
		}
		public void setActivityType(String activityType) {
			this.activityType = activityType;
		}
		public String getOnSale() {
			return onSale;
		}
		public void setOnSale(String onSale) {
			this.onSale = onSale;
		}
		public String getOffShow() {
			return offShow;
		}
		public void setOffShow(String offShow) {
			this.offShow = offShow;
		}
		public String getOnSaleTime() {
			return onSaleTime;
		}
		public void setOnSaleTime(String onSaleTime) {
			this.onSaleTime = onSaleTime;
		}
		public String getKeywords() {
			return keywords;
		}
		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}
		public String getBrandId() {
			return brandId;
		}
		public void setBrandId(String brandId) {
			this.brandId = brandId;
		}
		public String getCatIds() {
			return catIds;
		}
		public void setCatIds(String catIds) {
			this.catIds = catIds;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ProductMqMessageModel [");
			if (goodSn != null)
				builder.append("goodSn=").append(goodSn).append(", ");
			if (name != null)
				builder.append("name=").append(name).append(", ");
			if (preName != null)
				builder.append("preName=").append(preName).append(", ");
			if (postName != null)
				builder.append("postName=").append(postName).append(", ");
			if (xiuPrice != null)
				builder.append("xiuPrice=").append(xiuPrice).append(", ");
			if (marketPrice != null)
				builder.append("marketPrice=").append(marketPrice).append(", ");
			if (activityPrice != null)
				builder.append("activityPrice=").append(activityPrice)
						.append(", ");
			if (activityType != null)
				builder.append("activityType=").append(activityType)
						.append(", ");
			if (onSale != null)
				builder.append("onSale=").append(onSale).append(", ");
			if (offShow != null)
				builder.append("offShow=").append(offShow).append(", ");
			if (onSaleTime != null)
				builder.append("onSaleTime=").append(onSaleTime).append(", ");
			if (keywords != null)
				builder.append("keywords=").append(keywords).append(", ");
			if (brandId != null)
				builder.append("brandId=").append(brandId).append(", ");
			if (catIds != null)
				builder.append("catIds=").append(catIds);
			builder.append("]");
			return builder.toString();
		}
		
		
		
	}
}
