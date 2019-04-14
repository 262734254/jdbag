package com.xiu.jd.service.ware.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xiu.jd.bean.page.QueryData;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.service.ware.QueryDataService;

@Service("queryDataServiceBean")
public class QueryDataServiceBean extends BaseDaoImpl implements
		QueryDataService {
	private static final Logger logger = Logger.getLogger(QueryDataServiceBean.class);
	public QueryData queryResultData(String sql,String flag)throws Exception{
		QueryData queryData=new QueryData();
		Connection conn =null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try{
		 conn = this.getSqlMapClientTemplate().getDataSource().getConnection();
		 
		 statement=conn.prepareStatement(sql);
		
		if("select".equals(flag.trim())){
			 resultSet= statement.executeQuery();
			if(resultSet!=null){
				ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
				//所有列的个数
				int columnCount=resultSetMetaData.getColumnCount();
				//System.out.println("所有列的个数:" +columnCount);
				List<String> columnNames=new ArrayList<String>();
				for(int column=1;column<=columnCount;column++){
					String columnName=resultSetMetaData.getColumnName(column);
					columnNames.add(columnName);
				}
				
				queryData.setColumnTitles(columnNames);
				List<List<Object>> data=new ArrayList<List<Object>>();
				while(resultSet.next()){
					List<Object> rowData=new ArrayList<Object>();
					for(String columnName: columnNames){
						Object object=resultSet.getObject(columnName);
						rowData.add(object);
					}
					data.add(rowData);
				}
				queryData.setData(data);
				return queryData;
		    }
		
			
		}else{
			boolean updateFlage=statement.execute(sql);
			int count=0;
			//为update delete insert语句
			if(!updateFlage){
				count= statement.getUpdateCount();
			}
			queryData.setCount(count);
			logger.info("updateFlage=>>>"+updateFlage +", count ="+count);
		} 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(resultSet!=null){
				resultSet.close();
			}
			if(statement!=null){
				statement.close();
			}
			if(conn!=null){
				conn.close();
			}
				
		}
		return queryData;
	}
}
