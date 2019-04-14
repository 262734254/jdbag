package com.xiu.jd.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.channel.inventory.api.InventoryService;
import com.xiu.channel.inventory.api.dto.QueryInventoryRequest;
import com.xiu.channel.inventory.api.dto.QueryInventoryResponse;
import com.xiu.commerce.hessian.model.Product;
import com.xiu.commerce.hessian.model.ProductCommonParas;
import com.xiu.commerce.hessian.model.ProductSearchParas;
import com.xiu.commerce.hessian.server.GoodsCenterService;
import com.xiu.jd.constants.GlobalConstants;
import com.xiu.jd.service.ProductService;


/**
 * @see com.xiu.m.service.ProductService
 * 
 * @author zany@buzheng.org
 *
 */
@Service("productService") 
public class ProductServiceImpl implements ProductService {

	private static Log logger = LogFactory.getLog(ProductServiceImpl.class);

	@Autowired
	private GoodsCenterService goodsCenterService;
	
	@Autowired
	private InventoryService inventoryService;

	@SuppressWarnings("unchecked")
	@Override
	public Product loadProduct(String goodsSn) {
		ProductSearchParas productSearchParas = new ProductSearchParas();
		productSearchParas.setXiuSnList(goodsSn);

		ProductCommonParas productCommonParas = new ProductCommonParas();
		productCommonParas.setStoreID(Integer
				.parseInt(GlobalConstants.STORE_ID));

		if (logger.isDebugEnabled()) {
			logger.debug("invoke remote interface [goods] : goodsCenterService.searchProduct - 查询商品详情, goodsn: "
					+ goodsSn);
		}

		Map<String, Object> result = goodsCenterService.searchProduct(
				productCommonParas, productSearchParas);

		List<Product> products = (List<Product>) result.get("Products");
		if (products == null || products.isEmpty()) {
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(products.get(0));
		}

		return products.get(0);
	}

	@Override
	public int queryInventoryBySku(String sku) {
		try {
			QueryInventoryRequest queryInventoryRequest = new QueryInventoryRequest();
			queryInventoryRequest.setSkuCode(sku);
			queryInventoryRequest.setChannelCode(GlobalConstants.CHANNEL_ID);

			if (logger.isDebugEnabled()) {
				logger.debug("invoke remote interface [channel] : inventoryService.inventoryQueryBySkuCodeAndChannelCod - 根据sku查询库存");
				logger.debug("input args >>>> skuCode: "
						+ queryInventoryRequest.getSkuCode());
			}

			QueryInventoryResponse rsp = inventoryService
					.inventoryQueryBySkuCodeAndChannelCode(queryInventoryRequest);

			if (rsp.getCode().intValue() == 1) {
				logger.info(rsp.getQty());
				return rsp.getQty();
			} else {
				logger.error("查询库存出错：" + rsp.toString());
				return -9999;
			}
		} catch (Exception e) {
			logger.error("查询库存出错", e);
			return -9999;
		}
	}
	/****/
	@SuppressWarnings("unchecked")
	public List<Product> batchLoadProducts(String goodsSnBag) {
		List<Product> products=new ArrayList<Product>();
		if (goodsSnBag!=null&&!"".equals(goodsSnBag)) {
			String[] xiuCodes = goodsSnBag.split(",");
			int count =xiuCodes.length;
			String xiucode="";
			if (count<50){
				xiucode=goodsSnBag;
				ProductSearchParas productSearchParas = new ProductSearchParas();
				ProductCommonParas productCommonParas = new ProductCommonParas();
				productSearchParas.setXiuSnList(xiucode);
				productSearchParas.setPageStep(50);
				List<Product> productlist=null;
				try {
					Map<String, Object> result = goodsCenterService
							.searchProduct(productCommonParas,
									productSearchParas);
					productlist = (List<Product>) result
							.get("Products");
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("调用商品中心接口失败");
				}				
			
				if (productlist!= null &&!productlist.isEmpty()) {
					 products.addAll(productlist);
				}
			}else{
				StringBuffer sb = new StringBuffer(); 
				for (int i=0;i<count;i++) {
					sb.append(xiuCodes[i]+",");
					if(i%50==0||i==count-1){
						xiucode=sb.toString();
						ProductSearchParas productSearchParas = new ProductSearchParas();
						productSearchParas.setXiuSnList(xiucode);
						productSearchParas.setPageStep(50);

						ProductCommonParas productCommonParas = new ProductCommonParas();
						//productCommonParas.setStoreID(Integer.parseInt(GlobalConstants.STORE_ID));

						if (logger.isDebugEnabled()) {
							logger.debug("invoke remote interface [goods] : goodsCenterService.getProductLight - 查询商品详情, goodsn: \n"
									+ goodsSnBag);
						}
						List<Product> productlist=null;
						try {
							Map<String, Object> result = goodsCenterService
									.searchProduct(productCommonParas,
											productSearchParas);
							productlist = (List<Product>) result
									.get("Products");
						} catch (Exception e) {
							e.printStackTrace();
							logger.info("调用商品中心接口失败");
						}
						
					
						if (productlist!= null &&!productlist.isEmpty()) {
							 products.addAll(productlist);
						}

						if (logger.isDebugEnabled()) {
							logger.debug(productlist);
						}
						sb=new StringBuffer(); 

					}
					
				}
			}
		}
		
		return products;
	}
	


}
