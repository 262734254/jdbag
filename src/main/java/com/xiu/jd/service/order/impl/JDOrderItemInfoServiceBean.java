package com.xiu.jd.service.order.impl;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.xiu.jd.bean.order.JDOrderItemInfo;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.dao.BaseDaoImpl;
import com.xiu.jd.service.order.JDOrderItemInfoService;
import com.xiu.jd.sku.ExportTem;

/**
 *  ibatis namespace="JDOrderItemInfoServiceBean"
 * @author user
 *
 */
@Service("jDOrderItemInfoServiceBean")
public class JDOrderItemInfoServiceBean extends BaseDaoImpl<JDOrderItemInfo>
		implements JDOrderItemInfoService {
	
	@Resource(name = "exportTem")
	private ExportTem exportTem;

	@Override
	public int setProcessStatus(Map<String, Object> parames) {
		return this.getSqlMapClientTemplate().update("JDOrderItemInfoServiceBean.updateOrdersProcessStatu", parames);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JDOrderItemInfo> getOrderItemInfoByOrderId(String orderId) {
		return this.getSqlMapClientTemplate().queryForList("JDOrderItemInfoServiceBean.getOrderItemInfoByOrderId",orderId);
	}

	@Override
	public long getTotalCount() {
		 Object object=this.getSqlMapClientTemplate().queryForObject("JDOrderItemInfoServiceBean.getTotalCount");
		 if(object==null){
			 return 0;
		 }
		 return Long.parseLong(object.toString());
	}

	@Override
	public void createOrderWb(HSSFWorkbook wb,
			List<JDOrderItemInfo> jdOrderItemInfos) {
		exportTem.createOrderItemWb(wb,jdOrderItemInfos);
		
	}

	@Override
	public QueryResult<JDOrderItemInfo> getListResultPage(
			Map<String, Object> parames) {
		QueryResult<JDOrderItemInfo> qr=new QueryResult<JDOrderItemInfo>();
		List<JDOrderItemInfo> orderItemInfos=this.getSqlMapClientTemplate().queryForList("JDOrderItemInfoServiceBean.getListResultPage",parames);
		qr.setResultlist(orderItemInfos);
		long total=0;
		Object obj=this.getSqlMapClientTemplate().queryForObject("JDOrderItemInfoServiceBean.getListResultCount", parames);
		if(obj==null){
			total=0;
		}
		total=Long.parseLong(obj.toString());
		qr.setTotalrecord(total);
		return qr;
	}

	@Override
	public void createOrderWb(HSSFWorkbook wb, List<JDOrderItemInfo> lists,
			int count, int pageSize) {
		exportTem.createOrderItemWb(wb, lists,count,pageSize);
		
	}
	
	@Override
	public List<JDOrderItemInfo> getFailOrderItem() {
		// TODO Auto-generated method stub
		List<JDOrderItemInfo> orderItemInfos =this.getSqlMapClientTemplate().queryForList("JDOrderItemInfoServiceBean.getFailOrderItem");
		return orderItemInfos;
	}

	@Override
	public void updateEmailStatus(Map map) {
		this.getSqlMapClientTemplate().update("JDOrderItemInfoServiceBean.updateEmailStatus", map);
		
	}


	
}
