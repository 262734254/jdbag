package com.xiu.jd.schedule;




import org.apache.log4j.Logger;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.ware.WareListRequest;
import com.jd.open.api.sdk.response.AbstractResponse;
import com.jd.open.api.sdk.response.ware.WareListResponse;
import com.xiu.jd.utils.BaseUtils;
import com.xiu.jd.utils.ParseJDOauthProperties;

public class BaseSchedule  extends BaseUtils{
	
	private static final Logger logger = Logger.getLogger(BaseSchedule.class);
	private static String SERVER_URL=ParseJDOauthProperties.JD_SERVER_URL.replaceAll("[?]*", "");//"http://gw.api.sandbox.360buy.com/routerjson";

	protected static JdClient client=null;
	
	//protected static JdClient client2=null;
	
	//protected static JdClient client3=null;
	/**每页显示的记录数量**/
	protected static int pageSize=2;
	
	/***定时从京东获取商品入到本地库的分页大小**/
	//protected static int size=pageSize*2;
	
	static{
		String maxSize=ParseJDOauthProperties.getValue("pageSize");
		logger.info("maxSize " +maxSize);
		if(maxSize!=null && !"".equals(maxSize.trim())){
			try{
				pageSize=Integer.parseInt(maxSize);
				//size=pageSize*2;
				logger.info("页面显示"+pageSize +" 记录");
			}catch(Exception e){
				logger.error("解析 pageSize异常" +e);
			}
		}
		String ACCESS_TOKEN=ParseJDOauthProperties.ACCESS_TOKEN;
		String APP_KEY=ParseJDOauthProperties.JD_APP_KEY;
		String APP_SECRET=ParseJDOauthProperties.JD_APP_SECRET;
		client = new DefaultJdClient(SERVER_URL,ACCESS_TOKEN,APP_KEY,APP_SECRET);
		
		/*	String ACCESS_TOKEN1=ParseJDOauthProperties.ACCESS_TOKEN1;
		String APP_KEY1=ParseJDOauthProperties.JD_APP_KEY1;
		String APP_SECRET1=ParseJDOauthProperties.JD_APP_SECRET1;
		
		client2 = new DefaultJdClient(SERVER_URL,ACCESS_TOKEN1,APP_KEY1,APP_SECRET1);

		String ACCESS_TOKEN2=ParseJDOauthProperties.ACCESS_TOKEN2;
		String APP_KEY2=ParseJDOauthProperties.JD_APP_KEY2;
		String APP_SECRET2=ParseJDOauthProperties.JD_APP_SECRET2;
		client3 = new DefaultJdClient(SERVER_URL,ACCESS_TOKEN2,APP_KEY2,APP_SECRET2);*/
	}
	protected void outPutCommInfo(AbstractResponse response){
		logger.info("URL: "+response.getUrl());
		logger.info("Code: "+response.getCode());
		logger.info(response.getCode().equals("0")?"操作成功":"操作失败");
		logger.info("ZhDesc: "+response.getZhDesc());
	}
	


	
	/**
	 * 根据商品ID查询商品信息
	 * @param wareIds 京东商品ID
	 * @return
	 */
	
	protected WareListResponse getWareListResponse(String wareIds){
		if(wareIds.length()>0){
			WareListRequest wareListRequest= new WareListRequest();
			wareListRequest.setWareIds(wareIds);
			wareListRequest.setFields("ware_id,skus,ware_status,attributes,cost_price,market_price,jd_price,stock_num,status,outer_id,shop_categorys,title");
			try {
				WareListResponse response = client.execute(wareListRequest);
				return response;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return null;
	}
	


}
