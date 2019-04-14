package com.xiu.jd.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDOrderTrack;
import com.xiu.jd.bean.ware.XiuAddress;
import com.xiu.jd.bean.ware.XiuProductInfo;
import com.xiu.jd.dao.ware.impl.JDOrderTrackDaoBean;
import com.xiu.jd.service.OrderService;

@Service("orderService") 
public class OrderServiceImpl implements OrderService{
	
	@Resource(name="jdOrderTrackDaoBean")
	private JDOrderTrackDaoBean jdOrderTrackDao;

	@Override
	public XiuProductInfo queryWareInfo(String sku) throws Exception {
		return jdOrderTrackDao.queryWareInfo(sku);
	}

	@Override
	public Integer getStockNum(String wareId) throws Exception {
		return jdOrderTrackDao.getStockNum(wareId);
	}

	@Override
	public String queryPostcode(XiuAddress xiuAddress) throws Exception {
		return jdOrderTrackDao.queryPostcode(xiuAddress);
	}

	@Override
	public List<JDOrderTrack> queryJDOrderTrack(JDOrderTrack jdOrderTrack)
			throws Exception {
		return jdOrderTrackDao.queryJDOrderTrack(jdOrderTrack);
	}

	@Override
	public JDOrderTrack getJDOrderTrack(String jdOrderId) throws Exception {
		return jdOrderTrackDao.getJDOrderTrack(jdOrderId);
	}

	@Override
	public void insertJDOrderTrack(JDOrderTrack jdOrderTrack) throws Exception {
		 jdOrderTrackDao.insertJDOrderTrack(jdOrderTrack);
		
	}

	@Override
	public void insertJDOrderTracks(List<JDOrderTrack> list) throws Exception {
		 jdOrderTrackDao.insertJDOrderTracks(list);
	}

	@Override
	public void updateJDOrderTrack(JDOrderTrack jdOrderTrack) throws Exception {
		 jdOrderTrackDao.updateJDOrderTrack(jdOrderTrack);
		
	}

	@Override
	public void updateJDOrderTrackSync(List<JDOrderTrack> list)
			throws Exception {
		 jdOrderTrackDao.updateJDOrderTrackSync(list);
		
	}

	@Override
	public String getSupplierCode(String sku) throws Exception {
		return jdOrderTrackDao.getSupplierCode(sku);
	}

	@Override
	public void updateJDOrderIssyncplace(JDOrderTrack jdOrderTrack) {
		 jdOrderTrackDao.updateJDOrderIssyncplace(jdOrderTrack);
		
	}

	@Override
	public int updateUserMobileByOrderId(Map<String, Object> parames) {
		return jdOrderTrackDao.updateUserMobileByOrderId(parames);
	}

	@Override
	public List<JDOrderTrack> insertBatch(List<JDOrderTrack> jdOrderTrackList, int batchSize) {
		return jdOrderTrackDao.insertBatch(jdOrderTrackList, batchSize);
		
	}

	@Override
	public QueryResult<JDOrderTrack> getPageResule(Map<String, Object> parames) {
	    return jdOrderTrackDao.getPageResule(parames);
	}

	@Override
	public XiuAddress queryXiuAddress(XiuAddress address) {
		return jdOrderTrackDao.queryXiuAddress(address);
	}
	
}
