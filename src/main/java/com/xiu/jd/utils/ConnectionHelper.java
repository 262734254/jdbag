package com.xiu.jd.utils;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * 用于操作数据库的连接类
 * 
 * @author jason.su
 * 
 */
public class ConnectionHelper {
	private static final Log logger = LogFactory.getLog(ConnectionHelper.class);

	private static SqlMapClient sqlMapClient = null;

	/**
	 * 当类加载时去解析数据源连接信息
	 */
	static {
		try {
			Reader reader = Resources.getResourceAsReader("sqlmap-config.xml");
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
		} catch (IOException e) {
			logger.error("连接数据库失败.", e);
		}
	}

	// 把构造器私有化
	private ConnectionHelper() {

	}

	/**
	 * 取得连接的方法
	 * 
	 * @return sqlMapClient
	 */
	public static SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}
}
