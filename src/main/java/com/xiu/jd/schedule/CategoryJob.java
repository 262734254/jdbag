package com.xiu.jd.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.domain.category.AttValue;
import com.jd.open.api.sdk.domain.category.Category;
import com.jd.open.api.sdk.request.category.CategoryAttributeSearchRequest;
import com.jd.open.api.sdk.request.category.CategoryAttributeValueSearchRequest;
import com.jd.open.api.sdk.request.category.CategorySearchRequest;
import com.jd.open.api.sdk.response.category.CategoryAttributeSearchResponse;
import com.jd.open.api.sdk.response.category.CategoryAttributeSearchResponse.Attribute;
import com.jd.open.api.sdk.response.category.CategoryAttributeValueSearchResponse;
import com.jd.open.api.sdk.response.category.CategorySearchResponse;
import com.xiu.jd.bean.ware.JDAttribute;
import com.xiu.jd.bean.ware.JDAttributeValue;
import com.xiu.jd.bean.ware.JDCategory;
import com.xiu.jd.dao.ware.JDAttributeDao;
import com.xiu.jd.dao.ware.JDAttributeValueDao;
import com.xiu.jd.dao.ware.impl.JDCategoryDaoBean;
import com.xiu.jd.service.CategoryService;
import com.xiu.jd.service.log.impl.JdLogServiceBean;

/**
 * 将京东类目、属性、属性值导入本地库
 */
public class CategoryJob extends BaseSchedule{
	
	
	private static int num=1;
	private  final Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private JDCategoryDaoBean jdCategoryDao;
	
	@Resource
	private CategoryService categoryService;
	
	@Resource(name="jdLogServiceBean")
	private   JdLogServiceBean jdLogServiceBean;
	
	/**京东商品属性**/
	@Autowired
	private JDAttributeDao<JDAttribute> jDAttributeDaoBean;
	/**京东商品属性值**/
	@Autowired
	private JDAttributeValueDao<JDAttributeValue> jDAttributeValueDaoBean;
	
	/**
	 * 将京东类目信息导入本地库
	 */
	public void sycnCategoryInfo(){
		try {
			/*JdClient client = new DefaultJdClient(
					ParseJDOauthProperties.JD_SERVER_URL,
					ParseJDOauthProperties.ACCESS_TOKEN,
					ParseJDOauthProperties.JD_APP_KEY,
					ParseJDOauthProperties.JD_APP_SECRET);*/
			log.info("=========start============");
			//删除本地京东分类
			categoryService.deleteAll();
			//删除本地京东属性
			jDAttributeDaoBean.deleteAll();
			//删除本地京东属性值
			jDAttributeValueDaoBean.deleteAll();
			if(num==1){
				//删除本地日志
				jdLogServiceBean.deleteAll();
			}
			num++;
			CategorySearchRequest csRequest = new CategorySearchRequest();
			//获取类目
			CategorySearchResponse csResponse=client.execute(csRequest);
		
			
			if(csResponse == null) {
				log.info("=======CategorySearchResponse 为 null ==return============");
				return;
			}
			log.info("=======CategorySearchResponse");
			outPutCommInfo(csResponse);
			List<Category> list = csResponse.getCategory();
			log.info("=======CategorySearchResponse");
			if(list==null || list.isEmpty()){
				log.info("=======list 为null");
				return ;
			}
			inputCategory(list);
			inputAttr(list, client);
			inputAttrValue(list, client);
			log.info("=========end============");
		} catch (Exception e) {
			log.error("将京东类目信息导入本地异常"+e);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 导入类目
	 * @param list
	 */
	private void inputCategory(final List<Category> list) throws Exception{
		Thread td = new Thread(new Runnable() {
			@Override
			public void run() {
				List<JDCategory> jdCategoryList = new ArrayList<JDCategory>();
				for (Category category : list) {
					JDCategory jdCategory = new JDCategory();
					BeanUtils.copyProperties(category, jdCategory);
					jdCategoryList.add(jdCategory);
				}
				try {
					jdCategoryDao.insertJDCategorys(jdCategoryList);
				} catch (Exception e) {
					log.error("将京东类目信息导入本地异常"+e);
					e.printStackTrace();
				}
			}
		});
		td.start();
	}
	
	/**
	 * 导入类目属性
	 * @param list
	 * @param client
	 * @throws Exception
	 */
	private void inputAttr(final List<Category> list,final JdClient client) throws Exception{
		for (Category category : list) {
			CategoryAttributeSearchRequest casRequest = new CategoryAttributeSearchRequest();
			casRequest.setCid(category.getId()+"");
			//获取类目属性
			CategoryAttributeSearchResponse casResponse=client.execute(casRequest);
			if(casResponse == null) continue;
			List<Attribute> attributeList = casResponse.getAttributes();
			List<JDAttribute> jdAttributeList = new ArrayList<JDAttribute>();
			int index = 0; 
			for (Attribute attribute : attributeList) {
				JDAttribute jdAttribute = new JDAttribute();
				BeanUtils.copyProperties(attribute, jdAttribute);
				jdAttributeList.add(jdAttribute);
			
				
				index++;
				if(index >= 1000){
					index = 0;
					final List<JDAttribute> jdAttributeList2 = jdAttributeList;
					jdAttributeList = new ArrayList<JDAttribute>();
					Thread td1 = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								jdCategoryDao.insertJDAttributes(jdAttributeList2);
							} catch (Exception e) {
								log.error("导入类目属性异常111",e);
								e.printStackTrace();
							}
						}
					});
					td1.start();
				}
			}
			try {
			jdCategoryDao.insertJDAttributes(jdAttributeList);
			} catch (Exception e) {
				log.error("导入类目属性异常222",e);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 导入类目属性值
	 * @param list
	 * @param client
	 * @throws Exception
	 */
	private void inputAttrValue(final List<Category> list,final JdClient client) throws Exception{
		for (Category category : list) {
			try{
			CategoryAttributeSearchRequest casRequest = new CategoryAttributeSearchRequest();
			int cid=category.getId();
			casRequest.setCid(cid+"");
			//获取类目属性
			CategoryAttributeSearchResponse casResponse=null;
			try {
				casResponse = client.execute(casRequest);
			} catch (Exception e2) {
				log.error("京东分类"+category.getId()+",异常");
				e2.printStackTrace();
				casResponse=null;
			}
			if(casResponse == null)
				continue;
			List<Attribute> attributeList = casResponse.getAttributes();
			if(attributeList!=null && attributeList.size()>0){
				for (Attribute attribute : attributeList) {
					if(attribute==null){
						continue;
					}
					CategoryAttributeValueSearchRequest cavRequest = new CategoryAttributeValueSearchRequest();
					cavRequest.setAvs(attribute.getAid()+"");
					//获取类目属性值
					CategoryAttributeValueSearchResponse cavResponse=null;
					try {
						cavResponse = client.execute(cavRequest);
					} catch (Exception e1) {
						log.error("京东分类"+category.getId()+",属性aid="+attribute.getAid()+"异常");
						e1.printStackTrace();
						cavResponse=null;
					}
					if(cavResponse == null) continue;
					List<AttValue> attrValueList = cavResponse.getAttValues();
					
					List<JDAttributeValue> jdAttributeValueList = new ArrayList<JDAttributeValue>();
					int index = 0;
					if(attrValueList==null || attrValueList.size()==0){
						continue;
					}
					for(AttValue attValue : attrValueList){
						Map<String,Object> maps = new HashMap<String,Object>();
						maps.put("jdAttrAid", attValue.getAid()+"");
						maps.put("jdAttrVid", attValue.getVid()+"");
						maps.put("cid", cid);
						//select count(*) from jd_attributevalue t where t.aid=#jdAttrAid# and t.vid=#jdAttrVid#
						//false为不存在
						boolean jdAttrValue = jDAttributeValueDaoBean.isExistAttrValue(maps);
						if(!jdAttrValue){
							JDAttributeValue jdAttributeValue = new JDAttributeValue();
							BeanUtils.copyProperties(attValue, jdAttributeValue);
							jdAttributeValue.setCid(cid);
							jdAttributeValueList.add(jdAttributeValue);
							
							index++;
							if(index >= 1000){
								index = 0;
								final List<JDAttributeValue> jdAttributeValueList2 = jdAttributeValueList;
								jdAttributeValueList = new ArrayList<JDAttributeValue>();
								Thread td1 = new Thread(new Runnable() {
									@Override
									public void run() {
										try {
											jdCategoryDao.insertJDAttributeValues(jdAttributeValueList2);
										} catch (Exception e) {
											log.error("导入类目属性值异常===>1",e);
											e.printStackTrace();
										}
									}
								});
								td1.start();
							}
						}
					
					}
					jdCategoryDao.insertJDAttributeValues(jdAttributeValueList);
				}
				
			}
	
			}catch(Exception e){
				log.error("导入类目属性值异常===>2",e);
				e.printStackTrace();
			}
			
			
		}
	}
}
