package com.xiu.jd.service.ware.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.ware.WareGetRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateRequest;
import com.jd.open.api.sdk.response.ware.WareGetResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateResponse;
import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.bean.ware.JdSellerCategory;
import com.xiu.jd.dao.ware.JDWareDao;
import com.xiu.jd.dao.ware.JdSellerCategoryDao;
import com.xiu.jd.service.ware.JdSellerCategoryService;
import com.xiu.jd.utils.BaseUtils;

@Service("jdSellerCategoryServiceBean")
public class JdSellerCategoryServiceBean extends BaseUtils implements JdSellerCategoryService {
	private Logger log = LoggerFactory.getLogger("jdShopCategory");

	@Resource(name = "jdSellerCategoryDaoBean")
	private JdSellerCategoryDao<JdSellerCategory> jdSellerCategoryDaoBean;
	@Autowired
	private JDWareDao<JDProduct> jdWareDaoBean;

	@Override
	public void insert(JdSellerCategory jdSellerCategory) throws Exception {
		jdSellerCategoryDaoBean.insert(jdSellerCategory);
	}

	@Override
	public void insertBatch(List<JdSellerCategory> jdSellerCategories, int batchSize) throws Exception {
		jdSellerCategoryDaoBean.insertBatch(jdSellerCategories, batchSize);

	}

	@Override
	public Map<JdSellerCategory, List<JdSellerCategory>> querySellerCategory() {
		Map<JdSellerCategory, List<JdSellerCategory>> catMaps = new HashMap<JdSellerCategory, List<JdSellerCategory>>();
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("isParent", "1");
		List<JdSellerCategory> lists = jdSellerCategoryDaoBean.querySellerCategory(maps);
		for (JdSellerCategory jdSellerCategory : lists) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("parentId", jdSellerCategory.getCid());
			List<JdSellerCategory> catList = jdSellerCategoryDaoBean.querySellerCategory(params);
			catMaps.put(jdSellerCategory, catList);
		}
		return catMaps;
	}

	@Override
	public List<JdSellerCategory> querySellerCategoryFid(Integer shopCategoryId) {
		return jdSellerCategoryDaoBean.querySellerCategoryFid(shopCategoryId);
	}

	@Override
	public void updateShopCategory(String jdWareIds, String jdShopCategory) {
		String[] jdWareId = jdWareIds.split(",");
		for (int i = 0; i < jdWareId.length; i++) {
			updateWareShopCategory(jdWareId[i].trim(), jdShopCategory);
		}
	}

	/**
	 * 更新（覆盖）商品的店内分类
	 * 
	 * @param jdWareId
	 * @param jdShopCategory
	 */
	private void updateWareShopCategory(String jdWareId, String jdShopCategory) {
		WareUpdateRequest wareUpdateRequest = new WareUpdateRequest();
		wareUpdateRequest.setWareId(jdWareId);
		wareUpdateRequest.setShopCategory(jdShopCategory);
		WareUpdateResponse wareUpdateResponse = null;
		try {
			wareUpdateResponse = client.execute(wareUpdateRequest);
			if (wareUpdateResponse != null && "0".equals(wareUpdateResponse.getCode())) {
				log.info("京东商品ID为:" + jdWareId + ",更新该商品的店内分类为" + jdShopCategory + "成功");
				JDProduct jdProduct = new JDProduct();
				jdProduct.setShopCategory(jdShopCategory);
				jdProduct.setJdWareId(jdWareId);
				jdWareDaoBean.updateProduct(jdProduct);
			} else {
				log.info("京东商品ID为:" + jdWareId + ",更新该商品的店内分类为" + jdShopCategory + "失败," + wareUpdateResponse.getCode() + ":"
						+ wareUpdateResponse.getZhDesc());
			}
		} catch (JdException e) {
			log.error("京东商品ID为:" + jdWareId + ",更新该商品的店内分类为" + jdShopCategory + "异常",e.getErrMsg());
		}
	}

	@Override
	public void deleteAllSellerCategory() {
		jdSellerCategoryDaoBean.deleteAllSellerCategory();
	}
	
	@Override
	public void addShopCategory(String jdwardIds, String jdShopCategory) {
		String[] jdWareId = jdwardIds.split(",");
		for (int i = 0; i < jdWareId.length; i++) {
			getWareShopCategory(jdWareId[i].trim(), jdShopCategory);
		}
	}

	/**
	 * 根据商品ID获取商品京东店内分类
	 * @param jdwardId
	 * @param jdShopCategory
	 */
	private void getWareShopCategory(String jdwardId, String jdShopCategory) {
		WareGetRequest  wareGetRequest= new WareGetRequest();
		wareGetRequest.setWareId(jdwardId);
		wareGetRequest.setFields("shop_categorys");
		WareGetResponse response = null;
		try {
			response = client.execute(wareGetRequest);
			if(response.getCode().equals("0")){
				log.info("京东商品ID为："+jdwardId+"的京东商品内部分类为："+response.getWare().getShopCategorys());
				addWareShopCategory(jdwardId,response.getWare().getShopCategorys(), jdShopCategory);
			}else{
				log.info("京东商品ID为："+jdwardId+"拉取京东内部分类失败");
			}
		} catch (JdException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 更新（新增）商品的店内分类
	 * @param jdwardId：京东商品ID
	 * @param shopCategorys：京东原有内部分类
	 * @param jdShopCategory：新增京东内部分类
	 */
	private void addWareShopCategory(String jdWareId, String shopCategorys, String jdShopCategory) {
		WareUpdateRequest wareUpdateRequest = new WareUpdateRequest();
		wareUpdateRequest.setWareId(jdWareId);
		StringBuilder scBuilder = new StringBuilder();
		if(shopCategorys == null || shopCategorys.equals("")){
			wareUpdateRequest.setShopCategory(jdShopCategory);
		}else{
			String[] shopCategoryArray = shopCategorys.split(";");
			for (String sc : shopCategoryArray) {
				if(!jdShopCategory.contains(sc)){
					scBuilder.append(sc).append(";");
				}
			}
			wareUpdateRequest.setShopCategory(scBuilder.append(jdShopCategory).toString());
		}
		WareUpdateResponse wareUpdateResponse = null;
		try {
			wareUpdateResponse = client.execute(wareUpdateRequest);
			if (wareUpdateResponse != null && "0".equals(wareUpdateResponse.getCode())) {
				log.info("京东商品ID为:" + jdWareId + ",更新该商品的店内分类为" + jdShopCategory + "成功");
				JDProduct jdProduct = new JDProduct();
				jdProduct.setShopCategory(scBuilder.toString());
				jdProduct.setJdWareId(jdWareId);
				jdWareDaoBean.updateProduct(jdProduct);
			} else {
				log.info("京东商品ID为:" + jdWareId + ",更新该商品的店内分类为" + jdShopCategory + "失败," + wareUpdateResponse.getCode() + ":"
						+ wareUpdateResponse.getZhDesc());
			}
		} catch (JdException e) {
			log.error("京东商品ID为:" + jdWareId + ",更新该商品的店内分类为" + jdShopCategory + "异常",e.getErrMsg());
		}
	}
}
