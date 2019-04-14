package com.xiu.jd.service.jms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.xiu.channel.inventory.api.InventoryService;
import com.xiu.channel.inventory.api.dto.QueryInventoryRequest;
import com.xiu.channel.inventory.api.dto.QueryInventoryResponse;
import com.xiu.jd.bean.ware.ChannelSkuInventoryQtyChangeJms;
import com.xiu.jd.bean.ware.JDSku;
import com.xiu.jd.bean.ware.JdChangedGoodsStock;
import com.xiu.jd.bean.ware.SkuInventoryQtyChangeJms;
import com.xiu.jd.constants.GlobalConstants;
import com.xiu.jd.service.ware.JdChangeGoodsStockService;
import com.xiu.jd.service.ware.JdSkuService;

/**
 * 商品中心商品库存变化MQ
 * @author liweibiao
 *
 */
@Service(value="goodCenterStockChangeListener")
public class GoodCenterStockChangeListener implements MessageListener {
	private static final Logger logger = Logger.getLogger(GoodCenterStockChangeListener.class);
	
	@Resource(name = "jdSkuServiceBean")
	private JdSkuService<JDSku> jdSkuServiceBean;
	@Resource(name = "jdChangeGoodsStockService")
	private JdChangeGoodsStockService jdChangeGoodsStockService;
	
	/** 渠道中心 **/
	@Resource(name = "inventoryService")
	private InventoryService inventoryService;
	
	@Override
	public void onMessage(Message message) {
		logger.info("------------stock change mq--------------->");
		if(message==null || "".equals(message.toString())){
			return ;
		}
		if (!(message instanceof TextMessage)) {
			logger.info("商品中心,库存变化MQ:-->\n"+message);
			return;
		}
		
		try {
			String xml = ((TextMessage) message).getText().toString();
			 XStream xstream = new XStream(new DomDriver()); 
			 xmlToObject(xstream,xml);
		}catch (Exception e){
			logger.info("商品中心,库存变化MQ:=====>异常"+e);
			e.printStackTrace();
		}

	}
	
	public void xmlToObject(XStream xstream ,String xml){

		xstream.alias("ChannelSkuInventoryQtyChange", com.xiu.jd.bean.ware.ChannelSkuInventoryQtyChangeJms.class);//类别名
		xstream.alias("SkuInventoryQtyChange", com.xiu.jd.bean.ware.SkuInventoryQtyChangeJms.class);//类别名
		xstream.aliasField("sku", com.xiu.jd.bean.ware.SkuInventoryQtyChangeJms.class, "skuCode");
		//System.out.println( xstream.fromXML(xml));
		ChannelSkuInventoryQtyChangeJms channelSkuInventoryQtyChangeJms =(ChannelSkuInventoryQtyChangeJms) xstream.fromXML(xml);
		if(channelSkuInventoryQtyChangeJms!=null){
			
			logger.info("渠道ID="+channelSkuInventoryQtyChangeJms.getChannelCode());
			List<SkuInventoryQtyChangeJms> inventoryQtyChangeJms=channelSkuInventoryQtyChangeJms.getSkuInventoryQtyChanges();
			List<JdChangedGoodsStock> changedGoodsStockList = new ArrayList<JdChangedGoodsStock>();
			for(SkuInventoryQtyChangeJms  changeJms:inventoryQtyChangeJms ){
				if(changeJms!=null){
					logger.info(changeJms.toString());
					//取商品走秀skucode
					String skuCode = changeJms.getSkuCode();
					if(skuCode != null && !"".equals(skuCode.trim())){
						//数据表jd_product_sku_info中是否存在商品走秀skucode，true:本地不存在；false:本地存在
						boolean isExist = jdSkuServiceBean.xiuSnIsExistsNation(skuCode.trim());
						if(isExist){
							continue;
						}else{
							logger.info("本地数据库表中存在走秀sku为"+skuCode.trim()+"的商品");
						    Map<String, Object> skuParames=new HashMap<String, Object>();
						    skuParames.put("skuCode", skuCode.trim());
							List<JDSku> jdskuList = jdSkuServiceBean.querySkuInfo(skuParames);
							for (JDSku jdSku : jdskuList) {
								if(jdSku == null){
									continue;
								}
								JdChangedGoodsStock changeStock = new JdChangedGoodsStock();
								changeStock.setSkuCode(skuCode.trim());
								changeStock.setSkuId(jdSku.getJdSkuId());
								changeStock.setWareId(jdSku.getJdWareJd());
								//库存的变动量,而非当前sku总库存量
								String qty=changeJms.getQty();
								
								QueryInventoryRequest queryInventoryRequest = new QueryInventoryRequest();
								// 商品的sku码
								queryInventoryRequest.setSkuCode(skuCode.trim());
								queryInventoryRequest.setChannelCode(GlobalConstants.CHANNEL_ID);
								QueryInventoryResponse qir = null;
								 try {
									qir =inventoryService.inventoryQueryBySkuCodeAndChannelCode(queryInventoryRequest);
								} catch (Exception e) {
									logger.error("调用渠道中心中心查询sku库存异常,走秀商品sku:"+skuCode+",商品中心的现在库存:"+qty);
									e.printStackTrace();
									continue;
								}
								if(qir==null){
									logger.error("调用渠道中心中心查询sku库存,走秀商品sku:"+skuCode+",商品中心的现在库存:"+qty+",走秀sku在渠道不存在");
									continue;
								}
								Integer channelInvetory= qir.getQty();
								logger.info("走秀商品sku:"+skuCode+",商品中心的现在库存"+qty+",渠道中心库存:"+channelInvetory);
								if(channelInvetory==null){
									channelInvetory=0;
								}
								if(channelInvetory<=0){
									channelInvetory=0;
								}
								//不要相信商品走秀发过来的库存量,sku库存从渠道中去取
								changeStock.setXiuStock(channelInvetory+"");
								changeStock.setJdStock(jdSku.getStocknum());
								changeStock.setUpdateStatus(0);
								changeStock.setXiuCode(jdSku.getXiucode());
								changedGoodsStockList.add(changeStock);
								if(changedGoodsStockList.size()>=20){
									jdChangeGoodsStockService.insertBatch(changedGoodsStockList, changedGoodsStockList.size());
									 changedGoodsStockList = new ArrayList<JdChangedGoodsStock>();
								}
								
							}
						}
					}
				}
			}
			if(changedGoodsStockList.size()>0){
				jdChangeGoodsStockService.insertBatch(changedGoodsStockList, changedGoodsStockList.size());
			}
		}
	
	}

}
