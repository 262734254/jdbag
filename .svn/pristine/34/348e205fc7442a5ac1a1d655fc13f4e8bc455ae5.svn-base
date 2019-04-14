package com.xiu.jd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * 目的：实现Dao类注解方式自动注入
 * 
 * @author zany@buzheng.org
 *
 */
public class BaseIbatisDao<T> extends SqlMapClientDaoSupport {
	
    @Autowired
    public void setSqlMapClientBase(SqlMapClient sqlMapClient) {  
        super.setSqlMapClient(sqlMapClient);
    }
    
   
    
}  