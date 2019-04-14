package com.xiu.jd.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件属性值类
 * 
 * @author jason.su
 * 
 */
public class ParseProperties {
	private static Properties properties = null;

	/**
	 * 私有初始化构造器，以保证单例
	 */
	private ParseProperties() {

	}

	/**
	 * 静态初始化块
	 */
	static {
		InputStream in = null;
		try {
			//config.properties
			in = ParseProperties.class.getClassLoader().getResourceAsStream(GlobalConstants.PROPERTIES_FILE_NAME);
			properties = new Properties();
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * 根据key取得属性配置值
	 * 
	 * @param key
	 * @return
	 */
	public static String getPropertiesValue(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 
	 * 根据key取得属性配置值，并转化为int类型
	 * 
	 * @param key
	 * @return
	 */
	public static int getPropertiesIntValue(String key) {
		return Integer.parseInt(properties.getProperty(key)+"".trim());
	}
	
	public static void main(String[] args) {
		System.out.println(ParseProperties.getPropertiesLongValue("remote.url.osc.buyerId"));
		//System.out.println(properties.getProperty("remote.url.osc.buyerId").toString().trim());
	}
	
	/**
	 * 
	 * 根据key取得属性配置值，并转化为int类型
	 * 
	 * @param key
	 * @return
	 */
	public static long getPropertiesLongValue(String key) {
		try {
			String buyerId=properties.getProperty(key);
			System.out.println("buyerId=="+buyerId +" max" +Long.MAX_VALUE);
			if(buyerId!=null){
				return Long.valueOf(buyerId);
			}
		} catch (Exception e) {
			System.out.println("getPropertiesLongValue ,key "+key+"  异常"+e );
		}
		
		return 1012922384l;
	}


	
	//走秀价比重
	public static String  RATIO=getPropertiesValue("ratio");
	
	public static int LASTNUMBER =getPropertiesIntValue("lastNumber");
	
	public static String IMAGE_SIZE=getPropertiesValue("IMAGE_SIZE");
	
	public static String IMAGE_PREFIX=getPropertiesValue("IMAGE_PREFIX");
	

	public static int STORE_ID=getPropertiesIntValue("storeId");
	public static String STORE_NAME=getPropertiesValue("storeName");
	
	public static String PAY_TYPE=getPropertiesValue("payType");
	//true对接为活动价,false:对接为走秀价 
	public  static String IS_ACTIVITY_PRICE=getPropertiesValue("is_activity_price");
	
	public  static int MAX_PACKAGE_FAIL_COUNT=getPropertiesIntValue("max_package_fail_count");
	
	
	
	
}
