package com.xiu.jd.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;



public class HttUtils {
	
	public static final String UTF8 = "utf-8";   

	private static  int TIME_OUT_CONNECTION = 30000;  // 30秒  设置连接超时时间(单位毫秒)
    private static  int TIME_OUT_SOTIMEOUT = 120000;  // 120秒 设置读数据超时时间(单位毫秒)
    private static final Logger logger=Logger.getLogger(HttUtils.class);
	
	public static String encode(String s, String enc) {
		String value = "";
		try {
			if (isNotEmpty(s) &&isNotEmpty(enc)) {
				value = java.net.URLEncoder.encode(s, enc);
			}
		} catch (UnsupportedEncodingException e) {
			return value;
		}
		return value;
	}
	
	public static boolean isNotEmpty(String outstr){ 
		if(outstr!=null&&outstr.trim().length()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * Initiates the request
	 * 
	 * @param reqUrl
	 * @return
	 */
	public static String postRequest(String reqUrl) {
		DataInputStream in = null;
		StringBuilder out = new StringBuilder();
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Accept-Charset","utf-8");

			connection.setRequestMethod("POST");
			in = new DataInputStream(connection.getInputStream());
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.append(new String(buffer, 0, count));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					in = null;
				}
			}
		}
		return out.toString();
	}
	public static byte[] getResponseData(String reqUrl){
		
		DataInputStream in = null;
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		URL url=null;
		try {
			url = new URL(reqUrl);
			//logger.info("URL对象为:"+url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			//logger.info("HttpURLConnection:"+connection);
			connection.setConnectTimeout(TIME_OUT_CONNECTION);
			
			connection.setReadTimeout(TIME_OUT_SOTIMEOUT);
			
			connection.setDoOutput(true);
			connection.connect();
			in = new DataInputStream(connection.getInputStream());
			//logger.info("DataInputStream:"+in);
			int code=connection.getResponseCode();
			logger.info("code:"+code);
			if(code!=200){
				logger.error("失败!!! 请在浏览器的地址栏上访问\n"+reqUrl+"路径,是不是没有请求成功 "+code);
			}
			byte[] buffer = new byte[4096];
			int len = 0;
			while((len=in.read(buffer, 0, buffer.length))!=-1){
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (Exception e) {
			logger.error("获取走秀主图异常,!请在浏览器的地址栏上访问\n"+reqUrl+" 路径,异常为:\n"+e);
			e.printStackTrace();
		}
		return null;
		
	}
}
