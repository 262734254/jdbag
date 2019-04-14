package com.xiu.jd.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.xiu.common.command.result.Result;
import com.xiu.jd.bean.ware.SettlementProductInfo;
import com.xiu.sales.common.balance.dataobject.PriceSettlementDO;
import com.xiu.settlement.common.ProdSettlementHessianService;
import com.xiu.settlement.common.model.ProdSimpleSettleDO;

public class BaseUtils {
	private static String SERVER_URL=ParseJDOauthProperties.JD_SERVER_URL.replaceAll("[?]*", "");//"http://gw.api.sandbox.360buy.com/routerjson";
	private static final Logger LOGGER = Logger.getLogger(BaseUtils.class);

	
	  public static JdClient client=null;
		static{
			String ACCESS_TOKEN=ParseJDOauthProperties.ACCESS_TOKEN;
			String APP_KEY=ParseJDOauthProperties.JD_APP_KEY;
			String APP_SECRET=ParseJDOauthProperties.JD_APP_SECRET;
			
			client = new DefaultJdClient(SERVER_URL,ACCESS_TOKEN,APP_KEY,APP_SECRET);
		
		}
		
		 public static JdClient clientXiu=null;
			static{
				String ACCESS_TOKEN=ParseJDOauthProperties.ACCESS_TOKEN_XIU;
				String APP_KEY=ParseJDOauthProperties.JD_APP_KEY_XIU;
				String APP_SECRET=ParseJDOauthProperties.JD_APP_SECRET_XIU;
				
				clientXiu = new DefaultJdClient(SERVER_URL,ACCESS_TOKEN,APP_KEY,APP_SECRET);
			
			}
			
			public static JdClient clientEbay=null;
				static{
					String ACCESS_TOKEN=ParseJDOauthProperties.ACCESS_TOKEN_EBAY;
					String APP_KEY=ParseJDOauthProperties.JD_APP_KEY_EBAY;
					String APP_SECRET=ParseJDOauthProperties.JD_APP_SECRET_EBAY;
					
					clientEbay = new DefaultJdClient(SERVER_URL,ACCESS_TOKEN,APP_KEY,APP_SECRET);
				
				}
				
				
				 public static List<String> jdSensitiveWord=null;
					static{
						
					String url="/jd_sensitive.txt";
						//System.out.println(System.getProperty("/jd_sensitive.txt"));
						//   BufferedReader reader = new BufferedReader(new InputStreamReader(is));    
					InputStream inStream=ParseJDOauthProperties.class.getResourceAsStream(url);
				    try {
				            
			         
			            	 List<String> list=new ArrayList<String>();
			                 InputStreamReader read = new InputStreamReader(inStream);//考虑到编码格式
			                 BufferedReader bufferedReader = new BufferedReader(read);
			                 String lineTxt = null;
			                 while((lineTxt = bufferedReader.readLine()) != null){
			                	list.add(lineTxt);
			                 }
			                 read.close();
			                 jdSensitiveWord=list;
				  
				     } catch (Exception e) {
				    	 LOGGER.info("读取文件内容出错找不到指定的文件"+url);
				         e.printStackTrace();
				     }
					
				}
		
		
		protected void outPutCommInfo(AbstractResponse response){
			if(response!=null){
				LOGGER.info("URL: "+response.getUrl());
				LOGGER.info("Code: "+response.getCode());
				LOGGER.info(response.getCode().equals("0")?"操作成功":"操作失败");
				LOGGER.info("ZhDesc: "+response.getZhDesc());
			}else{
				LOGGER.info("response对象为 空操作失败");
			}
		}

		protected String getDateString(){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(new Date());
		}
		
		public static JdClient getJdClient(){
			return client;
		}
		
		/**
		 * 计算总页数
		 * 
		 * @param pageSize
		 * @param total
		 * @return
		 */
		public static int getTotalPage(int pageSize, int total) {
			int totalPage = 0;
			if (total > 0) {
				// 总页数
				totalPage = total % pageSize == 0 ? total / pageSize : total
						/ pageSize + 1;
			}

			return totalPage;
		}
		
		
		/**
		 * 
		 * 调用结算系统,获取商品结算信息
		 * 根据走秀码获取,最大只返回一条记录
		 * 
		 * @param prodSettlementHessianService 结算系统业务类
		 * @param goodsSn  走秀码
		 * @param xiuPrice 走秀价(单位为分)
		 */
		protected SettlementProductInfo getProductSettlementInfo(ProdSettlementHessianService prodSettlementHessianService,String goodsSn,long xiuPrice){
			if(xiuPrice<=0){
				return null;
			}
			StringBuilder message=new StringBuilder();
			message.append("走秀码为"+goodsSn+"调用结算系统,输入参数价格为"+xiuPrice);
           List<ProdSimpleSettleDO> prodSimpleSettleDOs=new ArrayList<ProdSimpleSettleDO>();
			ProdSimpleSettleDO prodSimpleSettleDO=new ProdSimpleSettleDO();
			prodSimpleSettleDO.setGoodsSn(goodsSn);
			PriceSettlementDO priceSettlementDO=new PriceSettlementDO();
			priceSettlementDO.setBasePrice(xiuPrice);//商品未加入关税与运费的价格,(走秀价)
			prodSimpleSettleDO.setPrice(priceSettlementDO);
			prodSimpleSettleDOs.add(prodSimpleSettleDO);
			Result result=null;
			try {
				result=prodSettlementHessianService.getProdSimpleSettleDOBacth(prodSimpleSettleDOs);
				if(result!=null){
					boolean isSuccess=result.isSuccess();
					message.append("调用结算系统成功与否:"+isSuccess+",走秀码为"+goodsSn+",目前的走秀价为"+xiuPrice);
					if(isSuccess){
						List<ProdSimpleSettleDO> simpleSettleDOs=(List<ProdSimpleSettleDO>)	result.getDefaultModel();
						if(simpleSettleDOs!=null && simpleSettleDOs.size()>0){
							ProdSimpleSettleDO simpleSettleDO=simpleSettleDOs.get(0);
							if(simpleSettleDO!=null){
								SettlementProductInfo settlementProductInfo=new SettlementProductInfo();
								settlementProductInfo.setGoodsSn(goodsSn);
								settlementProductInfo.setXiuPrice(xiuPrice);
								settlementProductInfo.setHsCode(simpleSettleDO.getHsCode());//海关编码
								settlementProductInfo.setCustomsCode(simpleSettleDO.getCustomsCode());//税率编码
								settlementProductInfo.setCustoms(simpleSettleDO.isCustoms());//是否是行邮
								PriceSettlementDO priceDo= simpleSettleDO.getPrice();
								LOGGER.info("走秀码为"+goodsSn+"调用结算系统,商品信息:"+simpleSettleDO+",价格信息:"+priceDo);
								if(priceDo!=null){
									/**
									 * 加入关税与国际物流费的价格
									 * 公式 ： price = basePrice + realCustomsTax + transportCost
									 */
									long price=priceDo.getPrice();
									settlementProductInfo.setFinnalXiuPrice(price);
									settlementProductInfo.setDealPrice(priceDo.getBasePrice());//商品未加入关税与运费的价格
									settlementProductInfo.setRealCustomsTax(priceDo.getRealCustomsTax());//关税  单位：分
									settlementProductInfo.setTransportCost(priceDo.getTransportCost());//国际物流费 单位：分
									// 国际物流费 单位：分
									message.append(",调用结算系统后的价格为"+price);
								}
								return settlementProductInfo;
							}
						}
					}else{
						//获取错误信息
						message.append(",调用结算系统错误信息为:"+result.getErrorMessages());
					}
				}
			} catch (Exception e) {
				LOGGER.error("走秀码为"+goodsSn+"调用结算系统Hessian接口异常"+e);
				e.printStackTrace();
				return null;
			}finally{
				if(message!=null){
					LOGGER.info("========结算系统==========="+message.toString());
				}
			}
	
			return null;
		}
		
		
}
