package com.xiu.jd.service.jms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.xiu.jd.bean.ware.ActivityPriceJms;
import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.bean.ware.JdChangedGoodsPrice;
import com.xiu.jd.service.ware.JDWareService;
import com.xiu.jd.service.ware.JdChangeGoodsPriceService;

/**
 * 商品中心价格变化MQ
 * @author liweibiao
 *
 */
@Service(value="goodCeneterPriceChangeListener")
public class GoodCeneterPriceChangeListener implements MessageListener {
	private static final Logger logger = Logger.getLogger(GoodCeneterPriceChangeListener.class);
	
	
	@Resource(name = "jDWareServiceBean")
	private JDWareService jDWareServiceBean;
	
	@Resource(name = "jdChangeGoodsPriceService")
	private JdChangeGoodsPriceService jdChangeGoodsPriceService;
	
	
	@Override
	public void onMessage(Message message) {
		logger.info("------------price change mq--------------->");
		if(message==null || "".equals(message.toString())){
			return ;
		}
		if (!(message instanceof TextMessage)) {
			logger.info("商品中心,MQ价格:-->\n"+message);
			return;
		}
		
		try {
			String xml = ((TextMessage) message).getText().toString();
			 XStream xstream = new XStream(new DomDriver()); 
			 xmlToObject(xstream, xml);
		} catch (JMSException e) {
			logger.error("商品中心,MQ价格,异常-==->>\n"+e);
			e.printStackTrace();
		}

	}
	
	/***
	 * 将xml数据格式的字符串转换为java对象
	 * @param xstream
	 */
	@SuppressWarnings("unchecked")
	public void xmlToObject(XStream xstream ,String xml){
		xstream.alias("activityPrice", com.xiu.jd.bean.ware.ActivityPriceJms.class);//类别名
		List<ActivityPriceJms> activityPrices =(List<ActivityPriceJms>) xstream.fromXML(xml);
		if(activityPrices!=null && activityPrices.size()>0){
			for(ActivityPriceJms  activityPrice:activityPrices ){
				if(activityPrice!=null){
					//取得商品走秀码
					String goodSn=activityPrice.getGoodSn();
					if(goodSn!=null && !"".equals(goodSn.trim())){
						//判断该走秀码是否JD_PRODUCT_INFO表中存在,只要存在才处理
						//true:本地不存在,false本地存在
						boolean isExistsProduct=jDWareServiceBean.xiuCodeIsExistsNation(goodSn.trim());
						if(!isExistsProduct){
							logger.info("MQ价格,本地表JD_PRODUCT_INFO存在走秀码为"+goodSn.trim()+"的商品");
							JdChangedGoodsPrice entity=new JdChangedGoodsPrice();
							
							String xiuPrice=activityPrice.getXiuPrice();
							logger.info("MQ价格,走秀码为"+goodSn.trim()+"走秀价"+xiuPrice+"分");
							String 	xiuactivityprice=activityPrice.getActivityPrice();
							logger.info("MQ价格,走秀码为"+goodSn.trim()+"活动价"+xiuactivityprice+"分");
							
							if(xiuPrice!=null && !"".equals(xiuPrice.trim())){
								xiuPrice=xiuPrice.trim();
							}
							  Map<String, Object> productParames=new HashMap<String, Object>();
							  List<String> xiuCodes=new ArrayList<String>();
							  xiuCodes.add(goodSn.trim());
							  productParames.put("xiuCodes", xiuCodes);
								//根据商品走秀码，取得商品京东ID,与该商品对应的走秀码(目前系统还是存在重复的走秀码)
							try{
								  List<JDProduct> jdProducts=jDWareServiceBean.queryWardIdsByXiuCodes(productParames);
								  for(JDProduct product:jdProducts){
										if(product==null){
											logger.error("MQ价格,没有找到商品"+goodSn.trim());
											continue;
										}
										String xiucode=product.getXiucode();
										//京东商品ID
										String wareId=product.getJd_ware_id();
										//logger.info("需要同步价格的走秀码为:"+xiucode+", 对应的京东商品ID为:"+wareId);
										entity.setXiucode(xiucode);
										entity.setWareid(wareId);
										entity.setXiuprice(xiuPrice);
										if(xiuPrice==null || xiuPrice.isEmpty()){
											//商品中心给的走秀价为null
											entity.setUpdatestatus(8);
										}else{
											entity.setUpdatestatus(0);
										}
										
										entity.setXiuactivityprice(xiuactivityprice);
										entity.setJdprice(product.getJdprice());
										logger.info("MQ价格,"+entity.toString());
										try{
											jdChangeGoodsPriceService.insert(entity);
										} catch(Exception e){
											logger.error("MQ价格,保存商品异常==>"+e);
										}

								  }  
								
								  }catch(Exception e){
									e.printStackTrace();
									logger.error("MQ价格,保存商品异常"+e);
								}
						}
					}
				}
			}
		}
	}

}
