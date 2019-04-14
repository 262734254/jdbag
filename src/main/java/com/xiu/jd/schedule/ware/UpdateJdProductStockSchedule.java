package com.xiu.jd.schedule.ware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.ware.WareSkuStockUpdateRequest;
import com.jd.open.api.sdk.response.ware.WareSkuStockUpdateResponse;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.bean.ware.JDSku;
import com.xiu.jd.bean.ware.JdChangedGoodsStock;
import com.xiu.jd.schedule.BaseSchedule;
import com.xiu.jd.service.ware.JDWareService;
import com.xiu.jd.service.ware.JdChangeGoodsStockService;
import com.xiu.jd.service.ware.JdSkuService;

/***
 * 定时更新京东商品SKU库存
 * 
 * @author liweibiao
 * 
 */
public class UpdateJdProductStockSchedule extends BaseSchedule {

	private static final Logger logger = Logger.getLogger(UpdateJdProductStockSchedule.class);

	@Resource(name = "jdChangeGoodsStockService")
	private JdChangeGoodsStockService jdChangeGoodsStockService;

	@Resource(name = "jdSkuServiceBean")
	private JdSkuService<JDSku> jdSkuServiceBean;

	@Resource(name = "jDWareServiceBean")
	private JDWareService jDWareServiceBean;

	public void updateJdProductStock() {
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("rown", "1"); // 分组后取时间最大的第一条数据
		parames.put("firstNum", "1");
		
		int pageSize = 300;
		parames.put("lastNum", pageSize);
		parames.put("updateStatus", "0");
		QueryResult<JdChangedGoodsStock> queryResult = jdChangeGoodsStockService.getPageResule(parames);
		if (queryResult != null) {
			long total = queryResult.getTotalrecord();
			logger.info("MQ 库存 总记录数为:" + total);
			int totalPage = getTotalPage(pageSize, (int) total);
			logger.info("MQ 库存 总页数为:" + totalPage);
			for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
				if (currentPage != 1) {
					parames = new HashMap<String, Object>();
					parames.put("rown", "1"); // 分组后取时间最大的第一条数据
					parames.put("firstNum", pageSize * (currentPage - 1) + 1);

					parames.put("lastNum", pageSize * currentPage + 1);
					parames.put("updateStatus", "0");
					queryResult = jdChangeGoodsStockService.getPageResule(parames);
				}
				logger.info("MQ 库存 第==" + currentPage + "==页");
				// 取得需要同步到京东的商品
				List<JdChangedGoodsStock> changedGoodsStockList = queryResult.getResultlist();
				if (changedGoodsStockList != null && changedGoodsStockList.size() > 0) {
					for (JdChangedGoodsStock jdChangedGoodsStock : changedGoodsStockList) {
						if(jdChangedGoodsStock==null ||jdChangedGoodsStock.getSkuCode()==null){
							continue;
						}
						sysWareStockToJd(jdChangedGoodsStock);
					}
				}
			}
		}
	}

	/**
	 * 同步走秀的库存到京东
	 * 
	 * @param jdChangedGoodsStock
	 */
	private void sysWareStockToJd(JdChangedGoodsStock jdChangedGoodsStock) {
		String jdSkuId = jdChangedGoodsStock.getSkuId();
		String jdWareId = jdChangedGoodsStock.getWareId();
		if (jdWareId == null || "".equals(jdWareId.trim())) {//商品还没有被推送
			logger.info("MQ 库存 京东商品ID为空，说明该商品还没有被推送到京东");
			jdChangedGoodsStock.setUpdateStatus(3);
			jdChangeGoodsStockService.update(jdChangedGoodsStock);
			sysLocalStock(jdChangedGoodsStock);
		} else {//商品已经推送
			WareSkuStockUpdateRequest request = new WareSkuStockUpdateRequest();
			if (jdSkuId != null) {
				request.setSkuId(jdSkuId);
			} else {
				if(jdChangedGoodsStock.getSkuCode()!=null && !"".equals(jdChangedGoodsStock.getSkuCode())){
					request.setOuterId(jdChangedGoodsStock.getSkuCode());
				}
				
			}
			String xiuStock=jdChangedGoodsStock.getXiuStock();
			if(xiuStock==null  ){
				logger.info("京东商品ID"+jdWareId+"走秀库存为====>"+xiuStock);
				return ;
			}
			int IntXiuStock=0;
			try {
				IntXiuStock = Integer.parseInt(xiuStock);
			} catch (NumberFormatException e1) {
				IntXiuStock=0;
				e1.printStackTrace();
			}
			if(IntXiuStock<=0){
				IntXiuStock=0;
			}
			request.setQuantity(IntXiuStock+"");
			WareSkuStockUpdateResponse res = null;
			try {
				res = client.execute(request);
				if(res != null ){
					logger.info("MQ 库存 京东商品ID="+jdWareId+",京东skuId="+jdSkuId+",走秀skuStock="+xiuStock+",推送到京东的库存为:"+IntXiuStock+",京东sku外部ID"+jdChangedGoodsStock.getSkuCode());
					if ( "0".equals(res.getCode())) {//更新京东商品库存成功
						logger.info("MQ 库存 京东商品ID为:" + jdChangedGoodsStock.getWareId() + "更新京东商品库存成功");
						jdChangedGoodsStock.setUpdateStatus(1);
						jdChangeGoodsStockService.update(jdChangedGoodsStock);
						
						sysLocalStock(jdChangedGoodsStock);
					} else {//更新京东商品库存失败 (:1007868425更新京东商品库存失败SKU已经删除)(1007963262更新京东商品库存失败外部id不存在)
						logger.info("MQ 库存 京东商品ID为:" + jdWareId + "更新京东商品库存失败"+res.getZhDesc()+",错误码为"+res.getCode());
						//[MQ 库存 京东商品ID为:1005433652更新京东商品库存失败SKU已经删除,错误码为11200025]
						if("11200025".equals(res.getCode())){
							jdChangedGoodsStock.setUpdateStatus(2);
							jdChangeGoodsStockService.update(jdChangedGoodsStock);
						}
						//[MQ 库存 京东商品ID为:1016806914更新京东商品库存失败外部id不存在,错误码为11200032]
						if("11200032".equals(res.getCode())){
							jdChangedGoodsStock.setUpdateStatus(5);
							jdChangeGoodsStockService.update(jdChangedGoodsStock);
						}
					}
				}
				
			} catch (JdException e) {
				logger.error("MQ 库存 京东商品ID为:" + jdWareId + "更新京东商品库存异常"+e);
			}
		}
	}
	/**
	 * 同步本地库存
	 * @param jdChangedGoodsStock
	 * @throws Exception
	 */
	public void sysLocalStock(JdChangedGoodsStock jdChangedGoodsStock) {
		
	    String wareId=jdChangedGoodsStock.getWareId();
		JDSku jdSku = new JDSku();
		String xiuStock=jdChangedGoodsStock.getXiuStock();
		if(xiuStock==null){
			logger.error("走秀库存为"+xiuStock);
			return ;
		}
		Integer intXiuStock=Integer.parseInt(xiuStock);
		if(intXiuStock<=0){
			intXiuStock=0;
		}
		jdSku.setStocknum(intXiuStock+"");
		jdSku.setSkusn(jdChangedGoodsStock.getSkuCode());
		//更新本地sku库存
		jdSkuServiceBean.update(jdSku);
		try {
			if(wareId!=null && !"null".equals(wareId.trim())&& !wareId.isEmpty()){
				JDProduct jdproduct = jDWareServiceBean.getJdProductByWareId(wareId.trim());
				if (jdproduct != null) {
					Map<String, Object> maps = new HashMap<String, Object>();
					//走秀sku库存-京东sku库存(changeStock可以是负数)
					String productSkuTotalStock=jDWareServiceBean.getProductTotalStockNumByWareId(wareId);
					int xiuSkuTotalStock=0;
					logger.info("京东商品ID="+wareId+",本地sku总库存为:"+productSkuTotalStock);
					try {
						xiuSkuTotalStock=Integer.parseInt(productSkuTotalStock);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("京东商品ID="+wareId+"查询本地库存异常"+e);
						xiuSkuTotalStock=0;
					}
					
					logger.info("京东商品ID="+wareId+" xiu库存 "+xiuStock+" (-)"+ " 京东库存"+jdChangedGoodsStock.getJdStock()+",本地商品总库存为:"+xiuSkuTotalStock);
					if(xiuSkuTotalStock<=0){
						xiuSkuTotalStock=0;
					}
					maps.put("stockNum", xiuSkuTotalStock);
					maps.put("JdwareId", wareId);
					jDWareServiceBean.updateProductOnline(maps);
				}
			}else{
				//商品未推送到京东的处理逻辑
				String xiuCode=jdChangedGoodsStock.getXiuCode();
				//走秀码不为空 
				if(xiuCode!=null && !xiuCode.isEmpty()){
				    //根据走秀码查询商品sku总库存
					//走秀sku库存-京东sku库存(changeStock可以是负数)
					int xiuSkuTotalStock=0;
					String productSkuTotalStock=jDWareServiceBean.getProductTotalStockNum(xiuCode);
					logger.info("走秀码="+xiuCode+",本地sku总库存为:"+productSkuTotalStock);
					try {
						xiuSkuTotalStock=Integer.parseInt(productSkuTotalStock);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("走秀码="+xiuCode+",本地sku总库存为:"+productSkuTotalStock+"查询本地库存异常"+e);
						xiuSkuTotalStock=0;
					}
					logger.info("走秀码="+xiuCode+" xiu库存 "+xiuStock+" (-)"+ " 京东库存"+jdChangedGoodsStock.getJdStock()+",本地商品总库存为:"+xiuSkuTotalStock);
					JDProduct jdProduct=new JDProduct();
					jdProduct.setStocknum(xiuSkuTotalStock+"");
					jdProduct.setXiucode(xiuCode);
					jDWareServiceBean.updateJDProduct(jdProduct);
				}
				
				
				
				
			}
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
