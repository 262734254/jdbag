package com.xiu.jd.service.ware.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDOrderTrack;
import com.xiu.jd.dao.ware.impl.JDOrderTrackDaoBean;
import com.xiu.jd.service.ware.JdOrderTrackService;
import com.xiu.jd.sku.ExportTem;
import com.xiu.jd.utils.BaseUtils;

@Service("jdOrderTrackServiceBean")
public class JdOrderTrackServiceBean extends BaseUtils implements JdOrderTrackService {

	@Resource
	private JDOrderTrackDaoBean jdOrderTrackDaoBean;
	
	@Resource(name = "exportTem")
	private ExportTem exportTem;
	
	@Override
	public QueryResult<JDOrderTrack> getPageResule(Map<String, Object> parames) {
		QueryResult<JDOrderTrack> qr=jdOrderTrackDaoBean.getPageResule(parames);
		return qr;
	}

	@Override
	public List<JDOrderTrack> getListResult(Map<String, Object> parames) {
		List<JDOrderTrack> lists = jdOrderTrackDaoBean.getListResult(parames);
		return lists;
	}

	@Override
	public void createOrderWb(HSSFWorkbook wb, List<JDOrderTrack> lists) {
		exportTem.createOrderWb(wb,lists);
	}

}
