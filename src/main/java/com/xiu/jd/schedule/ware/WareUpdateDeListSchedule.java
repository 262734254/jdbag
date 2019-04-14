package com.xiu.jd.schedule.ware;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.request.ware.WareListingGetRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateDelistingRequest;
import com.jd.open.api.sdk.response.ware.WareListingGetResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateDelistingResponse;
import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.schedule.BaseSchedule;
import com.xiu.jd.service.ware.JDWareService;

public class WareUpdateDeListSchedule extends BaseSchedule {
	private static final Logger logger = Logger.getLogger(WareUpdateDeListSchedule.class);

	@Autowired
	private JDWareService jDWareServiceBean;

	/**
	 * 获取上架商品信息
	 */
	public void getWareListInfo() {
		List<String> wareIdList = new ArrayList<String>();
		try {
			WareListingGetRequest request = new WareListingGetRequest();
			int page = 1;
			int pageSize = 100;
			request.setPage(page + "");
			request.setPageSize(pageSize + "");
			WareListingGetResponse response = client.execute(request);
			if (response != null && response.getCode().equals("0")) {
				logger.info("获取上架商品信息成功");
				int total = response.getTotal();
				int totalPage = getTotalPage(pageSize, total);
				for (int currentPage = 1; currentPage < totalPage; currentPage++) {
					if (currentPage != 1) {
						request = new WareListingGetRequest();
						request.setPage(currentPage + "");
						request.setPageSize(pageSize + "");
						response = client.execute(request);
					}
					logger.info("第" + currentPage + "页");
					if (response != null) {
						List<Ware> wares = response.getWareInfos();
						for (Ware ware : wares) {
							wareIdList.add(ware.getWareId() + "");
						}
					}
				}
			} else {
				logger.info("获取上架商品信息失败");
			}

			if (wareIdList != null && wareIdList.size() > 1) {
				updateWareDeList(wareIdList);
			}
		} catch (Exception e) {
			logger.error("获取上架商品信息异常", e);
			e.printStackTrace();
		}

	}

	/**
	 * 上架的商品下架
	 * 
	 * @param wareIdList
	 */
	private void updateWareDeList(List<String> wareIdList) {
		List<JDProduct> jdProductList = new ArrayList<JDProduct>();
		for (String wareId : wareIdList) {
			WareUpdateDelistingRequest wareUpdateDelistingRequest = new WareUpdateDelistingRequest();
			wareUpdateDelistingRequest.setWareId(wareId);
			WareUpdateDelistingResponse res = null;
			try {
				res = client.execute(wareUpdateDelistingRequest);
				if (res != null && res.getCode().equals("0")) {
					JDProduct jdware = new JDProduct();
					jdware.setJdWareId(wareId);
					jdware.setOnLineStatus(2);
					jdProductList.add(jdware);
					logger.info("商品编码为：" + wareId + "的商品下架成功");
				} else {
					logger.info("商品编码为：" + wareId + "的商品下架失败");
				}
			} catch (JdException e) {
				logger.error("商品编码为：" + wareId + "的商品下架时出现异常", e);
				e.printStackTrace();
			}
		}
		if (jdProductList != null && jdProductList.size() > 0) {
			jDWareServiceBean.updateJdProductBatch(jdProductList, jdProductList.size());
		}
	}
}
