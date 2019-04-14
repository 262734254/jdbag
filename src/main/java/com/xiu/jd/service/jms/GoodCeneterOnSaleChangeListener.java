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
import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.bean.ware.JdChangedGoodsOnSale;
import com.xiu.jd.bean.ware.OnSaleChangeJms;
import com.xiu.jd.service.ware.JDWareService;
import com.xiu.jd.service.ware.JdChangeGoodsOnSaleService;

/**
 * 商品中心上下架变化MQ
 * @author liweibiao
 *
 */
@Service(value="goodCeneterOnSaleChangeListener")
public class GoodCeneterOnSaleChangeListener implements MessageListener {
	private static final Logger logger = Logger.getLogger(GoodCeneterOnSaleChangeListener.class);
	
	
	@Resource(name = "jDWareServiceBean")
	private JDWareService jDWareServiceBean;
	
	@Resource(name="jdChangeGoodsOnSaleServiceBean")
	private JdChangeGoodsOnSaleService jdChangeGoodsOnSaleServiceBean;
	
	
	@Override
	public void onMessage(Message message) {
		logger.info("------------onsale change mq--------------->");
		if(message==null || "".equals(message.toString())){
			return ;
		}
		if (!(message instanceof TextMessage)) {
			logger.info("商品中心,MQ上下架状态:-->\n"+message);
			return;
		}
		
		try {
			String xml = ((TextMessage) message).getText().toString();
			 XStream xstream = new XStream(new DomDriver()); 
			 xmlToObject(xstream, xml);
		} catch (JMSException e) {
			logger.error("商品中心,MQ上下架状态-==->>\n"+e);
			e.printStackTrace();
		}

	}
	
	/***
	 * 将xml数据格式的字符串转换为java对象
	 * @param xstream
	 */
	@SuppressWarnings("unchecked")
	public void xmlToObject(XStream xstream ,String xml){

		xstream.alias("ProductTopicMqMessageModel", com.xiu.jd.bean.ware.OnSaleChangeJms.class);
		List<OnSaleChangeJms> onSaleChangeJms=(List<OnSaleChangeJms>)xstream.fromXML(xml);
		if(onSaleChangeJms!=null && onSaleChangeJms.size()>0){
			List<JdChangedGoodsOnSale> changedGoodsOnSales=new ArrayList<JdChangedGoodsOnSale>();
			for(OnSaleChangeJms changeJms: onSaleChangeJms){
				if(changeJms!=null){
					logger.info("商品中心上下架JMS:"+changeJms);
					String objType =changeJms.getObjType();
					if(objType!=null && !objType.isEmpty()){
						String xiuCode=changeJms.getGoodSn();
						if(objType.contains(",16,")){//,16,代表包含有上下架变化的数据 
							logger.info("MQ上下架状态,走秀码为"+xiuCode+",对象类型为:"+objType+",如果该字符串包含,16,需要处理");
							if(xiuCode!=null && !xiuCode.isEmpty()){
								//判断该走秀码是否JD_PRODUCT_INFO表中存在,只要存在才处理
								//true:本地不存在,false本地存在
								xiuCode=xiuCode.trim();
								boolean isExistsProduct=jDWareServiceBean.xiuCodeIsExistsNation(xiuCode);
								if(!isExistsProduct){
									logger.info("MQ上下架状态,走秀码为"+xiuCode+",走秀码在本地数据库存在,需要处理");
									Map<String, Object> productParames=new HashMap<String, Object>();
									List<String> xiuCodes=new ArrayList<String>();
									xiuCodes.add(xiuCode);
									productParames.put("xiuCodes", xiuCodes);
									 //一般只有一条，除非数据有错
									List<JDProduct> jdProducts=jDWareServiceBean.queryWardIdsByXiuCodes(productParames);
									  for(JDProduct product:jdProducts){
										  if(product==null){
												logger.error("MQ上下架状态,没有找到商品"+xiuCode);
												continue;
											}
										    String jdWareid=product.getJd_ware_id();
											//0下架，1上架
											String onSale=changeJms.getOnSale();
											String onSaleMessage="1".equals(onSale)?"上架的":"下架的";
											logger.info("MQ上下架状态,走秀码为"+xiuCode+",上下架状态为:"+onSale+", "+onSaleMessage);
											JdChangedGoodsOnSale changedGoodsOnSale=new JdChangedGoodsOnSale();
											changedGoodsOnSale.setXiuCode(xiuCode);
											changedGoodsOnSale.setFailDesc("");
											changedGoodsOnSale.setUpdateStatus(0);
											changedGoodsOnSale.setJdWareId(jdWareid);
											if(jdWareid==null || jdWareid.isEmpty()){
												//changedGoodsOnSale.setUpdateStatus(4);
												changedGoodsOnSale.setFailDesc("MQ上下架状态,"+xiuCode+"京东商品ID在本地数据库不存在,该商品还未推送到京东");
											}
											int onSaleInt=0;
											if(onSale!=null && !onSale.isEmpty()){
												try {
													onSaleInt=Integer.parseInt(onSale);
												} catch (NumberFormatException e) {
													logger.error("MQ上下架状态,"+xiuCode+"格式化商品上下架状态异常"+e);
													onSaleInt=0;
													changedGoodsOnSale.setFailDesc("MQ上下架状态,"+xiuCode+"格式化商品上下架状态异常");
													changedGoodsOnSale.setUpdateStatus(3);
													e.printStackTrace();
												}
											}
											if(onSaleInt!=1){
												//商品中心只会给2个状态:0下架，1上架,非1就认为是下架的
												onSaleInt=0;
											}
											changedGoodsOnSale.setOnSale(onSaleInt);
											changedGoodsOnSales.add(changedGoodsOnSale);
											//jdChangeGoodsOnSaleServiceBean.insert(changedGoodsOnSale);
									  }
								}
							}
		
						}
					}
				}
				//for
				if(changedGoodsOnSales!=null && changedGoodsOnSales.size()>20){
					try {
					//批量插入
					  jdChangeGoodsOnSaleServiceBean.insertBatch(changedGoodsOnSales, changedGoodsOnSales.size());
					  changedGoodsOnSales=new ArrayList<JdChangedGoodsOnSale>();
					} catch (Exception e) {
						logger.error("MQ上下架状态,--->保存商品上下架异常"+e);
						e.printStackTrace();
					}
				}
			}
			if(changedGoodsOnSales!=null && changedGoodsOnSales.size()>0){
				try {
				//批量插入
				jdChangeGoodsOnSaleServiceBean.insertBatch(changedGoodsOnSales, changedGoodsOnSales.size());
				changedGoodsOnSales=null;
				} catch (Exception e) {
					logger.error("MQ上下架状态,---====>保存商品上下架异常"+e);
					e.printStackTrace();
				}
			}
		}
	}
}
