package com.xiu.jd.schedule.ware;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.request.ware.WareDelistingGetRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateListingRequest;
import com.jd.open.api.sdk.response.ware.WareDelistingGetResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateListingResponse;
import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.schedule.BaseSchedule;
import com.xiu.jd.service.ware.JDWareService;

/**
 * 定时批量上架商品
 * 
 * @author Administrator
 * 
 */
public class WareUpdateListingSchedule extends BaseSchedule {

	private static final Logger logger = Logger.getLogger(WareUpdateListingSchedule.class);

	@Autowired
	private JDWareService jDWareServiceBean;

	/**
	 * 获取下架商品信息
	 */
	public void getWareDelistInfo() {
		List<String> wareIdList = new ArrayList<String>();
		try {
			WareDelistingGetRequest request = new WareDelistingGetRequest();
			int page = 1;
			int size = 100;
			request.setPage(page + "");
			request.setPageSize(size + "");
			WareDelistingGetResponse response = client.execute(request);
			if (response != null && response.getCode().equals("0")) {
				logger.info("获取下架商品信息成功");
				int total = response.getTotal();
				int totalPage = getTotalPage(size, total);
				logger.info("总的记录数量为：" + total + "  总的页数为 =" + totalPage + ",  每页显示的记录数为" + size);
				for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
					if (currentPage != 1) {
						request = new WareDelistingGetRequest();
						request.setPage(currentPage + "");
						request.setPageSize(size + "");
						response = client.execute(request);
					}
					logger.info("第" + currentPage + "页");
					if (response != null) {
						List<Ware> wares = response.getWareInfos();
						for (Ware ware : wares) {
							if(ware.getWareStatus().equals("CUSTORMER_DOWN") && ware.getStockNum() != 0){
								wareIdList.add(ware.getWareId() + "");
							}
						}
					}
				}
			} else {
				logger.info("获取下架商品信息失败");
			}

			if (wareIdList != null && wareIdList.size() > 0) {
				updateWareList(wareIdList);
			}
		} catch (Exception e) {
			logger.error("获取下架商品信息时出现异常", e);
		}
	}

	/**
	 * 将下架的商品上架
	 * 
	 * @param wareIdList
	 */
	public void updateWareList(List<String> wareIdList) {
		List<JDProduct> jdProductList = new ArrayList<JDProduct>();
		for (String wareId : wareIdList) {
			WareUpdateListingRequest wareUpdateListingRequest = new WareUpdateListingRequest();
			wareUpdateListingRequest.setWareId(wareId);
			try {
				WareUpdateListingResponse respose = client.execute(wareUpdateListingRequest);
				if (respose != null && "0".equals(respose.getCode())) {
					JDProduct jdware = new JDProduct();
					jdware.setJdWareId(wareId);
					jdware.setOnLineStatus(1);
					jdProductList.add(jdware);
					logger.info("商品编码为：" + wareId + "的商品上架成功");
				} else {
					logger.info("商品编码为：" + wareId + "的商品上架失败");
				}
			} catch (JdException e) {
				logger.error("商品编码为：" + wareId + "的商品上架时出现异常", e);
				e.printStackTrace();
			}
		}

		if (jdProductList != null && jdProductList.size() > 0) {
			jDWareServiceBean.updateJdProductBatch(jdProductList, jdProductList.size());
		}
	}
}
