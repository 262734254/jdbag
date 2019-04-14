package com.xiu.jd.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ParseJDOauthProperties {

	 private static final Logger logger=Logger.getLogger(ParseJDOauthProperties.class);
	 private static final String  PROPERTIES_FILE_NAME="/jd_oauth_constant.properties";
	 private static Properties properties=null;

	 static{
		 InputStream inStream=ParseJDOauthProperties.class.getResourceAsStream(PROPERTIES_FILE_NAME);
		
		
		 properties=new Properties();
		 try {
			properties.load(inStream);
			logger.info("加载京东配置文件成功");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(inStream!=null){
				try {
					inStream.close();
				} catch (IOException e) {
					logger.error("加载属性文件 " +PROPERTIES_FILE_NAME+ " 异常");
					e.printStackTrace();
				}
			}
			
		}
	 }
	 
	 public static String JD_AUTHORIZE_URL=properties.getProperty("jd_authorize_url");
	 
	 /**AppKey**/
	 public static String JD_APP_KEY=properties.getProperty("jd_app_key");
	 
	 public static String JD_APP_SECRET=properties.getProperty("jd_app_secret");
	 
	 public static String ACCESS_TOKEN=properties.getProperty("access_token");
	 
	 
	 
	 
	 /**xiu AppKey**/
	 public static String JD_APP_KEY_XIU=properties.getProperty("jd_app_key_xiu");
	 
	 public static String JD_APP_SECRET_XIU=properties.getProperty("jd_app_secret_xiu");
	 
	 public static String ACCESS_TOKEN_XIU=properties.getProperty("access_token_xiu");
	 
	 
	 
	 /**ebay AppKey**/
	 public static String JD_APP_KEY_EBAY=properties.getProperty("jd_app_key_ebay");
	 
	 public static String JD_APP_SECRET_EBAY=properties.getProperty("jd_app_secret_ebay");
	 
	 public static String ACCESS_TOKEN_EBAY=properties.getProperty("access_token_ebay");
	 
	 
    /* public static String JD_APP_KEY1=properties.getProperty("jd_app_key1");
	 
	 public static String JD_APP_SECRET1=properties.getProperty("jd_app_secret1");
	 
	 public static String ACCESS_TOKEN1=properties.getProperty("access_token1");
	 
	 
	 public static String JD_APP_KEY2=properties.getProperty("jd_app_key2");
		 
	 public static String JD_APP_SECRET2=properties.getProperty("jd_app_secret2");
		 
	 public static String ACCESS_TOKEN2=properties.getProperty("access_token2");*/
	 
	 
	 
	 

	 public static String  JD_STATE=properties.getProperty("jd_state");
	 
	 public static String JD_TOKEN_URL=properties.getProperty("jd_token_url");
	 
	 public static String GRANT_TYPE=properties.getProperty("grant_type");
	 
	 public static String SCOPE=properties.getProperty("scope");
	 
	 public static String V=properties.getProperty("v");
	 
	 public static String  JD_SERVER_URL=properties.getProperty("jd_server_url");
	 

	 public static String  ORDER_PLATFORM_TYPE=properties.getProperty("order_platform_type");
	 
	 
	 
	 
	 
	 



	 
	 

	 
	 
	 
	 
	 
	 public static String getValue(String key){
		if(properties!=null){
			Object ObjectKey=properties.getProperty(key);
			if(ObjectKey!=null){
				return ObjectKey.toString();
			}
		}
		return null;
	 }

	 
	 public static void main(String[] args) {
		System.out.println();
	}

}
