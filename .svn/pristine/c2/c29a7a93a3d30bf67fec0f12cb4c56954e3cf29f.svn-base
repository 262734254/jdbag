package com.xiu.jd.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiu.jd.bean.page.PageView;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDAttribute;
import com.xiu.jd.bean.ware.JDAttributeValue;
import com.xiu.jd.bean.ware.JDCategory;
import com.xiu.jd.bean.ware.XiuCategory;
import com.xiu.jd.bean.ware.XiuJdAttValue;
import com.xiu.jd.bean.ware.XiuJdBrand;
import com.xiu.jd.bean.ware.XiuJdCategory;
import com.xiu.jd.dao.ware.JDAttributeDao;
import com.xiu.jd.dao.ware.JDAttributeValueDao;
import com.xiu.jd.schedule.CategoryJob;
import com.xiu.jd.service.CategoryService;
import com.xiu.jd.service.log.impl.JdLogServiceBean;
import com.xiu.jd.utils.CommonUtil;
import com.xiu.jd.utils.ExportExcelUtil;
import com.xiu.jd.vo.DictVo;

@SuppressWarnings({ "unchecked","serial","unused","rawtypes"})
public class CategoryAction extends BaseAction{
	private  final Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private CategoryService categoryService;
	
	private String cid;
	
	private Long aid;
	
	private String path;
	
	private Integer fid;
	
	private XiuJdCategory xiuJdCategory;
	
	private XiuJdAttValue xiuJdAttValue;
	
	private XiuJdBrand xiuJdBrand;
	
	private PageView pageView;
	/**是否执行删除操作**/
	private String flag;
	
	@Resource
	private CategoryJob categoryJob;
	
	@Resource(name="jdLogServiceBean")
	private   JdLogServiceBean jdLogServiceBean;
	
	/**京东商品属性**/
	@Autowired
	private JDAttributeDao<JDAttribute> jDAttributeDaoBean;
	/**京东商品属性值**/
	@Autowired
	private JDAttributeValueDao<JDAttributeValue> jDAttributeValueDaoBean;
	
	//批量删除映射的属性id
	private List<Integer> ids;
	
	
	
	/**
	 * 京东分类ID(一级，二级，三级)
	 * 0为一级分类
	 */
	private Integer categoryId=0;
	//京东一级分类
	private List<JDCategory> jdCategories;
	
	private List<JDCategory> jdCategories2=new ArrayList<JDCategory>();
	
	private List<JDCategory> jdCategories3=new ArrayList<JDCategory>();
	
	private Integer firstCategoryId ;
	

	
    private Integer sendCategoryId ;
    private Integer threeCategoryId;
	
	
	public String sycnCategoryInfo(){
		try {
			if(flag!=null && "".trim().isEmpty() && flag.equals("true")){
				//删除数据库中已有的记录 
				
				//删除本地京东分类
				categoryService.deleteAll();
				//删除本地京东属性
				jDAttributeDaoBean.deleteAll();
				//删除本地京东属性值
				jDAttributeValueDaoBean.deleteAll();
				
				//删除本地日志
				jdLogServiceBean.deleteAll();
			
			}
			categoryJob.sycnCategoryInfo();
			super.returnAjaxStr("succ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 跳转
	 * @return
	 */
	public String forward(){
		return super.toView("/WEB-INF/category/"+path+".jsp");
	}
	
	public String toEditXiuJdCategory(){
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			xiuJdCategory = categoryService.getXiuJdCategory(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.toView("/WEB-INF/category/edit_xiu_jd_c.jsp");
	}
	
	public String chooseCategory(){
		String type = request.getParameter("type");
		try {
			if("jd".equalsIgnoreCase(type)){
				int curfid = 0;
				List<JDCategory> list = categoryService.queryJDCategoryByFid(curfid);
				request.setAttribute("jdClzList", list);
			}else if("xiu".equalsIgnoreCase(type)){
				XiuCategory xiu = new XiuCategory();
				xiu.setCode("super");
				List<XiuCategory> xiuClzList = categoryService.queryXiuCategory(xiu);
				request.setAttribute("xiuClzList", xiuClzList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.toView("/WEB-INF/category/"+type+"_point.jsp");
	} 
	
	public String editXiuJdCategory(){
		try {
			categoryService.updateXiuJdCategory(xiuJdCategory);
			request.setAttribute("oper_rs", "succ");
		} catch (Exception e) {
			request.setAttribute("oper_rs", "lost");
		}
		return super.toView("/WEB-INF/category/edit_xiu_jd_c.jsp");
	}
	
	public String delXiuJdCategory(){
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			categoryService.deleteXiuJdCategory(id);
			//super.returnAjaxStr("succ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryXiuJdCategory();
	}
	
	/**
	 * 查询已映射的分类列表
	 * @return
	 */
	public String queryXiuJdCategory(){
		try {
		
			pageView=new PageView<XiuJdCategory>(this.getPageSize(), this.getCurrentPage());
			
			//查询京东一级分类
			if(this.jdCategories==null){
				this.jdCategories=categoryService.queryJDCategoryByFid(this.categoryId);
			}
			log.info(this.sendCategoryId);
			//第一个分类的id
			if(this.firstCategoryId!=null && this.firstCategoryId>0){
				this.jdCategories2=categoryService.queryJDCategoryByFid(this.firstCategoryId);
			}
			
			if(this.threeCategoryId!=null && this.threeCategoryId>0){
				this.jdCategories3=categoryService.queryJDCategoryByFid(this.sendCategoryId);
				 if(xiuJdCategory!=null){
					 xiuJdCategory.setJdCid(this.threeCategoryId+"");
				 }
			}
			QueryResult<XiuJdCategory> qr = categoryService.getXiuJdCategoryPageResule(xiuJdCategory, this.getCurrentPage(), this.getPageSize());
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.toView("/WEB-INF/category/category_map_list.jsp");
	}
	
	/**
	 * 设置京东-走秀类目映射
	 * @return
	 */
	public String refCategory(){
		String[] refdata = request.getParameterValues("refdata");
		log.info("前台接收的参数"+refdata);
		try {
			List<XiuJdCategory> list = new ArrayList<XiuJdCategory>();
			if(refdata != null && refdata.length > 0){
				for (int i = 0; i < refdata.length; i++) {
					String cur = refdata[i];
					String[] data = cur.split("#");
					if(data == null || data.length != 5){
						continue;
					}
					XiuJdCategory xiuJdCategory = new XiuJdCategory();
					xiuJdCategory.setJdCid(data[0].trim());
					xiuJdCategory.setJdName(data[1].trim());
					xiuJdCategory.setJdFullName(data[2].trim());
					if(data[4].trim().contains("(brand")){
						String brand[] = data[4].trim().split("brand");
						String brandCode = brand[1].split("==")[0];
						xiuJdCategory.setXiuCid(data[3].trim()+"("+brandCode.trim()+")");
					}else{
						xiuJdCategory.setXiuCid(data[3].trim());
					}
					boolean isExist = categoryService.isExistXiuJdCategory(xiuJdCategory);
					if(isExist){
						continue;
					}
					xiuJdCategory.setXiuName(data[4].trim());
					list.add(xiuJdCategory);
				}
			}
			categoryService.insertXiuJdCategory(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return queryJDToLocalRef();
	}
	
	/**
	 * 转至京东与本地类目映射设置页
	 * @return
	 */
	public String queryJDToLocalRef(){
		try {
			int curfid = 0;
			List<JDCategory> lists = categoryService.queryJDCategoryByFid(curfid);
			request.setAttribute("jdClzList", lists);
			XiuCategory xiu = new XiuCategory();
			xiu.setCode("super");
			List<XiuCategory> xiuClzList = categoryService.queryXiuCategory(xiu);
			request.setAttribute("xiuClzList", xiuClzList);
			
			pageView=new PageView<XiuJdBrand>(this.getPageSize(), this.getCurrentPage());
			QueryResult<XiuJdBrand> qr = 
					categoryService.getXiuBrandPageResule(xiuJdBrand, this.getCurrentPage(), this.getPageSize());
			pageView.setQueryResult(qr);
			
//			List<JDCategory> list = categoryService.queryJDCategory();
			List<JDCategory> list = categoryService.queryJDCategoryByFid(curfid);
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.toView("/WEB-INF/category/category_map_set.jsp");
	}
	
	/**
	 * 根据父ID查询京东类目
	 * @return
	 */
	public String searchCategorysByFid(){
		try {
			List<JDCategory> list = categoryService.queryJDCategoryByFid(fid);
			StringBuffer sb = new StringBuffer();
			for (JDCategory bean : list) {
				XiuJdCategory xiujd = new XiuJdCategory();
				xiujd.setJdCid(bean.getId()+"");
				List<XiuJdCategory> list2 = categoryService.queryXiuJdCategory(xiujd);//走秀类目与京东类目映射表
				/*if(list2 == null || list2.isEmpty()){*/
					sb.append("<li onclick='liclk(this)' title='"+bean.getName()+"' id='"+bean.getId()+"'>"+bean.getName()+"<span style='float:right;'>></span></li>");
				/*}else{
					sb.append("<li style='color:red;' title='"+bean.getName()+"' id='"+bean.getId()+"'>"+bean.getName()+"<span style='float:right;'>></span></li>");
				}*/
			}
			super.returnAjaxStr(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询本地类目
	 * @return
	 */
	public String searchXiuCategory(){
		try {
			String curCode = request.getParameter("curCode");
			String parentCode = request.getParameter("parentCode");
			String paramCode = request.getParameter("paramCode");
			XiuCategory xiu = new XiuCategory();
			xiu.setCode(curCode);
			xiu.setParentCode(parentCode);
			xiu.setParamCode(paramCode);
			List<XiuCategory> xiuClzList = categoryService.queryXiuCategory(xiu);
			StringBuffer sb = new StringBuffer();
			for (XiuCategory bean : xiuClzList) {
				if("class".equals(curCode)){
					XiuJdCategory xiujd = new XiuJdCategory();
					xiujd.setXiuCid(bean.getCode());
					List<XiuJdCategory> list2 = categoryService.queryXiuJdCategory(xiujd);
					if(list2 == null || list2.isEmpty()){
						sb.append("<li onclick='xiu_liclk(this)' title='"+bean.getName()+"' id='"+bean.getCode()+"'>"+bean.getName()+"<span style='float:right;'>></span></li>");
					}else{
						sb.append("<li style='color:red;' onclick='xiu_liclk(this)' title='"+bean.getName()+"' id='"+bean.getCode()+"'>"+bean.getName()+"<span style='float:right;'>></span></li>");
					}
				}else{
					sb.append("<li onclick='xiu_liclk(this)' title='"+bean.getName()+"' id='"+bean.getCode()+"'>"+bean.getName()+"<span style='float:right;'>></span></li>");
				}
			}
			super.returnAjaxStr(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String searchXiuCategory2(){
		try {
			String curCode = request.getParameter("curCode");
			String parentCode = request.getParameter("parentCode");
			String paramCode = request.getParameter("paramCode");
			XiuCategory xiu = new XiuCategory();
			xiu.setCode(curCode);
			xiu.setParentCode(parentCode);
			xiu.setParamCode(paramCode);
			List<XiuCategory> xiuClzList = categoryService.queryXiuCategory(xiu);
			StringBuffer sb = new StringBuffer();
			for (XiuCategory bean : xiuClzList) {
				if("class".equals(curCode)){
//					XiuJdCategory xiujd = new XiuJdCategory();
//					xiujd.setXiuCid(bean.getCode());
//					List<XiuJdCategory> list2 = categoryService.queryXiuJdCategory(xiujd);
//					if(list2 == null || list2.isEmpty()){
						sb.append("<li onclick='xiu_liclk(this)' title='"+bean.getName()+"' id='"+bean.getCode()+"'>"+bean.getName()+"<span style='float:right;'>></span></li>");
//					}else{
//						sb.append("<li style='color:red;' title='"+bean.getName()+"' id='"+bean.getCode()+"'>"+bean.getName()+"<span style='float:right;'>></span></li>");
//					}
				}else{
					sb.append("<li onclick='xiu_liclk(this)' title='"+bean.getName()+"' id='"+bean.getCode()+"'>"+bean.getName()+"<span style='float:right;'>></span></li>");
				}
			}
			super.returnAjaxStr(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 类目-属性-属性值页
	 * @return
	 */
	public String searchCategorys(){
		try {
//			List<JDCategory> list = categoryService.queryJDCategory();
//			request.setAttribute("list", list);
			int curfid = 0;
			List<JDCategory> lists = categoryService.queryJDCategoryByFid(curfid);
			request.setAttribute("list", lists);
			
			/*List<DictVo> clist = categoryService.queryXiuCategoryForValueRef();
			request.setAttribute("clist", clist);*/
			
			XiuCategory xiu = new XiuCategory();
			xiu.setCode("super");
			List<XiuCategory> xiuClzList = categoryService.queryXiuCategory(xiu);
			request.setAttribute("clist", xiuClzList);
			
			pageView=new PageView<XiuJdBrand>(this.getPageSize(), this.getCurrentPage());
			QueryResult<XiuJdBrand> qr = 
					categoryService.getXiuBrandPageResule(xiuJdBrand, this.getCurrentPage(), this.getPageSize());
			pageView.setQueryResult(qr);
			
//			List<JDCategory> list = categoryService.queryJDCategory();
			List<JDCategory> list = categoryService.queryJDCategoryByFid(curfid);
			request.setAttribute("list", list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.toView("/WEB-INF/category/c_a_v_map_set.jsp");
	}
	
	/**
	 * 根据类目ID查询类目属性信息
	 * @return
	 */
	public String searchCategoryAttr(){
		try {
			List<JDAttribute> list = categoryService.queryJDAttributeByCid(cid);
			StringBuffer sb = new StringBuffer();
			for (JDAttribute attr : list) {
				if(!"品牌".equals(attr.getName().trim())){
					if(!attr.getSaleProp()){
						if(attr.getReq().equals("true")){
							sb.append("<li onclick='liclk(this)' title='"+attr.getName()+"' id='"+attr.getAid()+"'>"+attr.getName()+"(必填)<span style='float:right;'>></span></li>");
						}else{
							sb.append("<li onclick='liclk(this)' title='"+attr.getName()+"' id='"+attr.getAid()+"'>"+attr.getName()+"<span style='float:right;'>></span></li>");
						}
					}
				}
			}
			super.returnAjaxStr(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 根据属性ID查询属性值信息
	 * @return
	 */
	public String searchCategoryAttrValue(){
		try {
			List<JDAttributeValue> list = categoryService.queryJDAttrValueByAid(aid);
			StringBuffer sb = new StringBuffer();
			for (JDAttributeValue attrValue : list) {
				sb.append("<li onclick='liclk(this)' title='"+attrValue.getName()+"' id='"+attrValue.getVid()+"'>"+attrValue.getName()+"<span style='float:right;'>></span></li>");
			}
			super.returnAjaxStr(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public String searchCategoryAttr2(){
		try {
			String categoryId = this.cid;
			String cid = this.cid.substring(0, 1);
			List<DictVo> list = categoryService.queryXiuAttrForValueRef(cid);
			StringBuffer sb = new StringBuffer();
			for (DictVo attr : list) {
				sb.append("<li onclick='xiu_liclk(this)' class='"+categoryId+"' title='"+attr.getName()+"' id='"+attr.getId()+"'>"+attr.getName()+"<span style='float:right;'>></span></li>");
			}
			super.returnAjaxStr(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public String searchCategoryAttrValue2(){
		try {
			List<DictVo> list = categoryService.queryXiuAttrValueForValueRef(aid+"");
			StringBuffer sb = new StringBuffer();
			for (DictVo attrValue : list) {
				Map<String,Object> paramers = new HashMap<String, Object>();
				paramers.put("cid", this.cid);
				paramers.put("xiuVid", attrValue.getId());
				List<XiuJdAttValue> lists = categoryService.queryXiuJdAttValue(paramers);
				if(lists == null || lists.isEmpty()){
					sb.append("<li onclick='xiu_liclk(this)' title='"+attrValue.getName()+"' id='"+attrValue.getId()+"'>"+attrValue.getName()+"<span style='float:right;'>></span></li>");
				}else{
					sb.append("<li style='color:red;' onclick='xiu_liclk(this)'  title='"+attrValue.getName()+"' id='"+attrValue.getId()+"'>"+attrValue.getName()+"<span style='float:right;'>></span></li>");
				}
			}
			super.returnAjaxStr(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * ================================
	 * 设置京东-走秀属性值映射
	 * @return
	 */
	public String refXiuJDValue(){
		String[] refdata = request.getParameterValues("refdata");
		try {
			List<XiuJdAttValue> list = new ArrayList<XiuJdAttValue>();
			if(refdata != null && refdata.length > 0){
				for (int i = 0; i < refdata.length; i++) {
					String cur = refdata[i];
					String[] data = cur.split("#");
					if(data == null || data.length != 8){
						continue;
					}
					XiuJdAttValue xiuJdAttValue = new XiuJdAttValue();
					xiuJdAttValue.setJdVid(data[0].trim());
					xiuJdAttValue.setJdName(data[1].trim());
					xiuJdAttValue.setJdCid(data[2].trim());
					xiuJdAttValue.setJdCategoryName(data[3].trim());
					
					xiuJdAttValue.setXiuVid(data[4].trim());
					xiuJdAttValue.setXiuName(data[5].trim());
					xiuJdAttValue.setXiuAttrId(data[6].trim());
					
					if(data[7].trim().contains("(brand")){
						String brand[] = data[7].trim().split("brand");
						String brandCode = brand[1].split("==")[0];
						xiuJdAttValue.setXiuChildcode(brand[0]+brandCode.trim()+")");
					}else{
						xiuJdAttValue.setXiuChildcode(data[7].trim());
					}
					boolean isExistValue = categoryService.isExistXiuJdValue(xiuJdAttValue);
					if(isExistValue){
						continue;
					}
					list.add(xiuJdAttValue);
				}
			}
			categoryService.insertXiuJdAttValue(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return searchCategorys();
	}
	//属性值映射查询
	public String queryXiuJdAttValue(){
		
		try {
			pageView=new PageView<XiuJdAttValue>(this.getPageSize(), this.getCurrentPage());
			
			//查询京东一级分类
			if(this.jdCategories==null){
				this.jdCategories=categoryService.queryJDCategoryByFid(this.categoryId);
			}
			log.info(this.sendCategoryId);
			//第一个分类的id
			if(this.firstCategoryId!=null && this.firstCategoryId>0){
				this.jdCategories2=categoryService.queryJDCategoryByFid(this.firstCategoryId);
			}
			
			if(this.threeCategoryId!=null && this.threeCategoryId>0){
				this.jdCategories3=categoryService.queryJDCategoryByFid(this.sendCategoryId);
				 if(xiuJdAttValue!=null){
					 xiuJdAttValue.setJdCid(this.threeCategoryId+"");
				 }
			}
			QueryResult<XiuJdAttValue> qr = categoryService.getXiuJdAttValuePageResule(xiuJdAttValue, this.getCurrentPage(), this.getPageSize());
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.toView("/WEB-INF/category/c_a_v_map_list.jsp");
	}
	
	
	public String batchDeleteAttribute(){
		log.info("批量删除映射的属性");
		String redirectUrl = request.getContextPath() + "/cate!queryXiuJdAttValue.action";
		try{
		Map<String, Object> parames=new HashMap<String, Object>();
		parames.put("ids", this.ids);
		int count=categoryService.batchDeleteAttri(parames);
		request.setAttribute("message", "批量删除映射的属性成功,影响的记录数为"+count);
	
		}catch(Exception e){
			request.setAttribute("message", "批量删除映射的属性异常");
			
		}
		request.setAttribute("redirectUrl", redirectUrl);
		//return queryXiuJdAttValue();
		return "message";
		
	}
	
	public String delXiuJdAttValue(){
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			categoryService.deleteXiuJdAttValue(id);
			//super.returnAjaxStr("succ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryXiuJdAttValue();
	}
	
	public String toRefxiuJdBrand(){
		try {
			pageView=new PageView<XiuJdBrand>(this.getPageSize(), this.getCurrentPage());
			QueryResult<XiuJdBrand> qr = 
					categoryService.getXiuBrandPageResule(xiuJdBrand, this.getCurrentPage(), this.getPageSize());
			pageView.setQueryResult(qr);
			
//			List<JDCategory> list = categoryService.queryJDCategory();
			int curfid = 0;
			List<JDCategory> list = categoryService.queryJDCategoryByFid(curfid);
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.toView("/WEB-INF/category/brand_map_set.jsp");
	}
	
	/**
	 * 品牌映射
	 * @return
	 */
	public String queryJdBrand(){
		try {
			List<JDAttribute> list = categoryService.queryJDAttributeByCid(cid);
			for (JDAttribute attr : list) {
				if("品牌".equals(attr.getName().trim())){
					this.aid = attr.getAid();
					break;
				}
			}
			
			List<JDAttributeValue> listValue = categoryService.queryJDAttrValueByAid(aid);
			StringBuffer sb = new StringBuffer();
			if(listValue!=null){
				
				for (JDAttributeValue attrValue : listValue) {
					sb.append("<li onclick='liclk(this)' title='"+attrValue.getName()+"' class='"+cid+"' id='"+attrValue.getVid()+"'>"+attrValue.getName()+"<span style='float:right;'>></span></li>");
				}
			}/*else{
				JDCategory jdCategory= categoryService.getJdCategoryById(Integer.parseInt(cid));
				if(jdCategory!=null){
					sb.append("<li onclick='liclk(this)' title='"+jdCategory.getName()+"' class='"+cid+"' id='"+jdCategory.getId()+"'>"+jdCategory.getName()+"<span style='float:right;'>></span></li>");
				}
			}*/
			
			super.returnAjaxStr(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String queryXiuBrand(){
		try {
			pageView=new PageView<XiuJdBrand>(this.getPageSize(), this.getCurrentPage());
			xiuJdBrand.setXiuBrandName(xiuJdBrand.getXiuBrandName().toLowerCase());
			QueryResult<XiuJdBrand> qr = 
					categoryService.getXiuBrandPageResule(xiuJdBrand, this.getCurrentPage(), this.getPageSize());
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.toView("/WEB-INF/category/xiu_brand.jsp");
	}
	
	/**
	 * 京东走秀品牌映射
	 * @return
	 */
	public String refXiuJdBrand(){
		String[] refdata = request.getParameterValues("refdata");
		try {
			List<XiuJdBrand> list = new ArrayList<XiuJdBrand>();
			if(refdata != null && refdata.length > 0){
				for (int i = 0; i < refdata.length; i++) {
					String cur = refdata[i];
					String[] data = cur.split("#");
					if(data == null || data.length != 6){
						continue;
					}
					XiuJdBrand xiuJdBrand = new XiuJdBrand();
					xiuJdBrand.setJdVid(data[0].trim());
					xiuJdBrand.setJdCid(data[1].trim());
					xiuJdBrand.setJdVname(data[2].trim());
					xiuJdBrand.setJdCategoryName(data[3].trim());
					
					xiuJdBrand.setXiuBrandCode(data[4].trim());
					xiuJdBrand.setXiuBrandName(data[5].trim());
					list.add(xiuJdBrand);
				}
			}
			String message=categoryService.queryXiuBrand2(list);
			String redirectUrl=request.getContextPath()+"/cate!toRefxiuJdBrand.action";
			if(!"".equals(message.trim())){
			
				request.setAttribute("message", message);
				
			
			}else{
				request.setAttribute("message", "品牌映射成功");
			}
			request.setAttribute("redirectUrl",redirectUrl );
			//----------------------------------
			categoryService.insertXiuJdBrand(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//toRefxiuJdBrand
		return "message";
	}
	
	public String deleteXiuJdBrand(){
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			categoryService.deleteXiuJdBrand(id);
			//super.returnAjaxStr("succ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryXiuJdBrand();
	}
	
	public String batchDeleteBrand(){
		log.info("批量删除映射的品牌");
		String redirectUrl = request.getContextPath() + "/cate!queryXiuJdBrand.action";
		try{
		Map<String, Object> parames=new HashMap<String, Object>();
		parames.put("ids", this.ids);
		int count=categoryService.batchDeleteBrand(parames);
		request.setAttribute("message", "批量删除映射的品牌成功,影响的记录数为"+count);
	
		}catch(Exception e){
			request.setAttribute("message", "批量删除映射的品牌异常");
			
		}
		request.setAttribute("redirectUrl", redirectUrl);
		//return queryXiuJdAttValue();
		return "message";
		
	}
	/**
	 * 品牌分页列表查询
	 * @return
	 */
	public String queryXiuJdBrand(){
	
		try {
			pageView=new PageView<XiuJdBrand>(this.getPageSize(), this.getCurrentPage());
			//查询京东一级分类
			if(this.jdCategories==null){
				this.jdCategories=categoryService.queryJDCategoryByFid(this.categoryId);
			}
			log.info(this.sendCategoryId);
			//第一个分类的id
			if(this.firstCategoryId!=null && this.firstCategoryId>0){
				this.jdCategories2=categoryService.queryJDCategoryByFid(this.firstCategoryId);
			}
			
			if(this.threeCategoryId!=null && this.threeCategoryId>0){
				this.jdCategories3=categoryService.queryJDCategoryByFid(this.sendCategoryId);
				 if(xiuJdBrand!=null){
					 xiuJdBrand.setJdCid(this.threeCategoryId+"");
				 }
			}
			QueryResult<XiuJdBrand> qr = 
					categoryService.getXiuJdBrandPageResule(xiuJdBrand, this.getCurrentPage(), this.getPageSize());
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.toView("/WEB-INF/category/brand_map_list.jsp");
	}
	
	/**
	 * 导出excel模板
	 * @return
	 */
	public String exportTemplate(){
		Map<String,List<JDAttributeValue>> map = new HashMap<String,List<JDAttributeValue>>();
		try {
			List<JDAttribute> jdAttrList = categoryService.queryJDAttributeByCid(cid);
			for (JDAttribute jdAttribute : jdAttrList) {
				List<JDAttributeValue> jdAttrVal = categoryService.queryJDAttrValueByAid(jdAttribute.getAid());
				map.put(jdAttribute.getName()+"=="+jdAttribute.getReq()+"=="+jdAttribute.getInputType(), jdAttrVal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HSSFWorkbook wb = new HSSFWorkbook();
		categoryService.createWb(wb,map,cid);
		String fileName = "Template_"+cid+"_"+CommonUtil.getNowTime()+ ".xls";
		try {
			ExportExcelUtil.downloadExcel(wb, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Long getAid() {
		return aid;
	}

	public void setAid(Long aid) {
		this.aid = aid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public XiuJdCategory getXiuJdCategory() {
		return xiuJdCategory;
	}

	public void setXiuJdCategory(XiuJdCategory xiuJdCategory) {
		this.xiuJdCategory = xiuJdCategory;
	}

	public XiuJdAttValue getXiuJdAttValue() {
		return xiuJdAttValue;
	}

	public void setXiuJdAttValue(XiuJdAttValue xiuJdAttValue) {
		this.xiuJdAttValue = xiuJdAttValue;
	}

	public PageView getPageView() {
		return pageView;
	}

	public void setPageView(PageView pageView) {
		this.pageView = pageView;
	}

	public XiuJdBrand getXiuJdBrand() {
		return xiuJdBrand;
	}

	public void setXiuJdBrand(XiuJdBrand xiuJdBrand) {
		this.xiuJdBrand = xiuJdBrand;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public List<JDCategory> getJdCategories() {
		return jdCategories;
	}

	public void setJdCategories(List<JDCategory> jdCategories) {
		this.jdCategories = jdCategories;
	}

	public List<JDCategory> getJdCategories2() {
		return jdCategories2;
	}

	public void setJdCategories2(List<JDCategory> jdCategories2) {
		this.jdCategories2 = jdCategories2;
	}

	public List<JDCategory> getJdCategories3() {
		return jdCategories3;
	}

	public void setJdCategories3(List<JDCategory> jdCategories3) {
		this.jdCategories3 = jdCategories3;
	}

	public Integer getSendCategoryId() {
		return sendCategoryId;
	}

	public void setSendCategoryId(Integer sendCategoryId) {
		this.sendCategoryId = sendCategoryId;
	}

	public Integer getThreeCategoryId() {
		return threeCategoryId;
	}

	public void setThreeCategoryId(Integer threeCategoryId) {
		this.threeCategoryId = threeCategoryId;
	}

	public Integer getFirstCategoryId() {
		return firstCategoryId;
	}

	public void setFirstCategoryId(Integer firstCategoryId) {
		this.firstCategoryId = firstCategoryId;
	}
	
}
