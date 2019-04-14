package com.xiu.jd.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.category.AttValue;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.request.category.CategoryAttributeSearchRequest;
import com.jd.open.api.sdk.request.category.CategoryAttributeValueSearchRequest;
import com.jd.open.api.sdk.request.ware.WareInfoByInfoRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateRequest;
import com.jd.open.api.sdk.response.category.CategoryAttributeSearchResponse;
import com.jd.open.api.sdk.response.category.CategoryAttributeSearchResponse.Attribute;
import com.jd.open.api.sdk.response.category.CategoryAttributeValueSearchResponse;
import com.jd.open.api.sdk.response.ware.WareInfoByInfoSearchResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateResponse;
import com.xiu.jd.bean.ware.JDAttribute;
import com.xiu.jd.bean.ware.JDAttributeValue;
import com.xiu.jd.dao.ware.JDAttributeDao;
import com.xiu.jd.dao.ware.JDAttributeValueDao;
import com.xiu.jd.utils.BaseUtils;

@Component("wareInfoManger")
public class WareInfoManger extends BaseUtils {
	private static final Logger logger = Logger.getLogger(WareInfoManger.class);
	
	/**京东商品属性**/
	@Autowired
	private JDAttributeDao<JDAttribute> jDAttributeDaoBean;
	/**京东商品属性值**/
	@Autowired
	private JDAttributeValueDao<JDAttributeValue> jDAttributeValueDaoBean;

	/** 获取商品信息 **/
	public void updateWareInfo(String prJdTitle) throws Exception {
		logger.info("===========================更新商品信息开始==========================");
		WareInfoByInfoRequest wareInfoByInfoRequest = new WareInfoByInfoRequest();
		int page = 1;
		wareInfoByInfoRequest.setPage(page + "");
		int pageSize = 30;
		wareInfoByInfoRequest.setPageSize(pageSize + "");
		WareInfoByInfoSearchResponse response = client.execute(wareInfoByInfoRequest);
		if (response != null) {
			String code = response.getCode();
			logger.info("0".equals(code) ? "商品信息入库,调用京东API成功" : "商品信息入库,调用京东API失败,失败原因" + response.getZhDesc());
			int total = response.getTotal();
			int totalPage = 0;
			if (total > 0) {
				totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
			}
			logger.info("总的记录数量为：" + total + "  总的页数为 =" + totalPage + ",  每页显示的记录数为" + pageSize);
			for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
				if (currentPage != 1) {
					wareInfoByInfoRequest = new WareInfoByInfoRequest();
					wareInfoByInfoRequest.setPage(currentPage + "");
					// 每页显示的记录数量
					wareInfoByInfoRequest.setPageSize(pageSize + "");
					response = client.execute(wareInfoByInfoRequest);
				}
				logger.info("第" + currentPage + "页");
				if (response != null) {
					List<Ware> wares = response.getWareInfos();
					if (wares != null && wares.size() > 0) {
						handleWareInfo(wares, prJdTitle);
					}
				}
			}
		}
		logger.info("===========================更新商品信息结束==========================");
	}

	/** 处理商品信息 **/
	private void handleWareInfo(List<Ware> wares, String prJdTitle) throws Exception {
		for (Ware ware : wares) {
			String oldTitle = ware.getTitle();
			logger.info("商品ID为"+ware.getWareId()+",商品名称为:"+oldTitle);
			if (oldTitle.contains("【ebay代购】")) {
				oldTitle = oldTitle.replaceAll("【ebay代购】", "");
			} 
			if (oldTitle.contains("【eBay商品】")) {
				oldTitle = oldTitle.replaceAll("【eBay商品】", "");
			} 
			if (oldTitle.contains("null")) {
					oldTitle = oldTitle.replaceAll("null", "");
			}
            if (oldTitle.contains("(")) {
				oldTitle = oldTitle.replaceAll("(", "").replaceAll(")", "").replaceAll("）", "").replace("美国直发", "");
			} 
            if (oldTitle.contains("（")) {
				oldTitle = oldTitle.replaceAll("（", "").replaceAll("）", "").replaceAll(")", "").replaceAll("美国直发", "");
				
			}
        
        	   WareUpdateRequest wareUpdateRequest = new WareUpdateRequest();
   			wareUpdateRequest.setWareId(ware.getWareId() + "");
   			wareUpdateRequest.setTitle(prJdTitle + oldTitle);
   			WareUpdateResponse wareUpdateResponse = client.execute(wareUpdateRequest);
   			if (wareUpdateResponse != null && "0".equals(wareUpdateResponse.getCode())) {
   				logger.info("商品ID为" + ware.getWareId() + "的标题修改成功");
   			} else {
   				logger.info("商品ID为" + ware.getWareId() + "的标题修改失败," + wareUpdateResponse.getZhDesc());
   			}
           
			
		}
	}
	
	/**
	 * 拉取京东类目下本地不存在的京东属性
	 * @param categoryId
	 * @return
	 */
	public String getJdAttribute(String categoryId){
		String message = "";
		CategoryAttributeSearchRequest request = new CategoryAttributeSearchRequest();
		request.setCid(categoryId);
		CategoryAttributeSearchResponse response= null;
		try {
			response = client.execute(request);
			if(response != null && response.getCode().equals("0")){
				logger.info("京东分类ID为" + categoryId + "的属性名拉取成功");
				message = "京东分类ID为" + categoryId + "的属性名拉取成功</br>";
				List<Attribute> attrNameList = response.getAttributes();
				List<JDAttribute> jdAttributeList = new ArrayList<JDAttribute>();
				List<JDAttribute> jdAttributeList2 = new ArrayList<JDAttribute>();
				for (Attribute attribute : attrNameList) {
					Map<String,Object> maps = new HashMap<String,Object>();
					maps.put("jdCid", categoryId);
					maps.put("jdAttrAid", attribute.getAid()+"");
					boolean jdAttr = jDAttributeDaoBean.isExistAttr(maps);
					if(!jdAttr){
						JDAttribute attr = new JDAttribute();
						BeanUtils.copyProperties(attribute, attr);
						//jdAttributeValue.setCid(cid);
						jdAttributeList.add(attr);
					}else{
						JDAttribute attr2 = new JDAttribute();
						BeanUtils.copyProperties(attribute, attr2);
						jdAttributeList2.add(attr2);
					}
				}
				if(jdAttributeList.size()>0){
					jDAttributeDaoBean.insertBatch(jdAttributeList, jdAttributeList.size());
					message = message + getJdAttributeValue(jdAttributeList,categoryId);
				}else{
					message = message + getJdAttributeValue(jdAttributeList2,categoryId);
				}
			}else{
				logger.info("京东分类ID为" + categoryId + "的属性名拉取失败");
				message = "京东分类ID为" + categoryId + "的属性名拉取失败</br>";
			}
		} catch (JdException e) {
			logger.error("京东分类ID为" + categoryId + "的属性名拉取出现异常"+e);
			message = "京东分类ID为" + categoryId + "的属性名拉取出现异常</br>";
			e.printStackTrace();
		}
		return message;
	}
	/**
	 * 拉取本地不存在的京东属性值
	 * @param jdAttributeList
	 */
	private String getJdAttributeValue(List<JDAttribute> jdAttributeList,String cid) {
		String message = "";
		for (JDAttribute jdAttribute : jdAttributeList) {
			logger.info("京东属性名aid为,假错误\n"+jdAttribute);
			CategoryAttributeValueSearchRequest request = new CategoryAttributeValueSearchRequest();
			request.setAvs(jdAttribute.getAid()+"");
			CategoryAttributeValueSearchResponse response= null;
			try {
				response = client.execute(request);
				if(response != null && response.getCode().equals("0")){
					List<AttValue> attValueList= response.getAttValues();
					//List<JDAttributeValue> attValues = new ArrayList<JDAttributeValue>();
					for (AttValue attValue : attValueList) {
						Map<String,Object> maps = new HashMap<String,Object>();
						maps.put("jdAttrAid", attValue.getAid()+"");
						maps.put("jdAttrVid", attValue.getVid()+"");
						//select count(*) from jd_attributevalue t where t.aid=#jdAttrAid# and t.vid=#jdAttrVid#
						//false为不存在
						boolean jdAttrValue = jDAttributeValueDaoBean.isExistAttrValue(maps);
						if(!jdAttrValue){
							JDAttributeValue jdAttributeValue = new JDAttributeValue();
							BeanUtils.copyProperties(attValue, jdAttributeValue);
							jdAttributeValue.setCid(Integer.parseInt(cid));
							//attValues.add(jdAttributeValue);
							try{
								jDAttributeValueDaoBean.insert(jdAttributeValue);
							}catch(Exception e){
								e.printStackTrace();
								//logger.error("京东属性名aid为" + jdAttribute.getAid() + "的属性值拉取异常");
								logger.error("京东属性名aid为" + jdAttributeValue.getAid() +","+jdAttributeValue.getVid()+ "的属性值拉取异常");
								message = message + "京东属性名aid为" + jdAttributeValue.getAid() +",=="+jdAttributeValue.getVid()+ "异常</br>";
							}
							
						}
					}
				}
			} catch (Exception e) {
				logger.error("京东属性名aid为" + jdAttribute.getAid() + "的属性值拉取异常",e);
				message = message + "京东属性名aid为" + jdAttribute.getAid() + "的属性值拉取异常</br>";
				e.printStackTrace();
			}
			
		}
		return message;
	}

}
