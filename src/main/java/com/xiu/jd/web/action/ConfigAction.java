package com.xiu.jd.web.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.sellercat.ShopCategory;
import com.jd.open.api.sdk.domain.ware.Sku;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.request.sellercat.SellerCatsGetRequest;
import com.jd.open.api.sdk.request.ware.SkuCustomGetRequest;
import com.jd.open.api.sdk.request.ware.SkuReadSearchSkuListRequest;
import com.jd.open.api.sdk.request.ware.WareInfoByInfoRequest;
import com.jd.open.api.sdk.request.ware.WareListRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateListingRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateRequest;
import com.jd.open.api.sdk.request.ware.WareWriteDeleteRequest;
import com.jd.open.api.sdk.response.sellercat.SellerCatsGetResponse;
import com.jd.open.api.sdk.response.ware.SkuCustomGetResponse;
import com.jd.open.api.sdk.response.ware.SkuReadSearchSkuListResponse;
import com.jd.open.api.sdk.response.ware.WareInfoByInfoSearchResponse;
import com.jd.open.api.sdk.response.ware.WareListResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateListingResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateResponse;
import com.jd.open.api.sdk.response.ware.WareWriteDeleteResponse;
import com.xiu.jd.bean.email.JdEmail;
import com.xiu.jd.bean.page.PageView;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDAttributeValue;
import com.xiu.jd.bean.ware.JDCategory;
import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.bean.ware.JDSku;
import com.xiu.jd.bean.ware.JDWareAndSkuInfo;
import com.xiu.jd.bean.ware.JDWareSkuBrand;
import com.xiu.jd.bean.ware.JdSellerCategory;
import com.xiu.jd.bean.ware.XOPBaseCategory;
import com.xiu.jd.bean.ware.XiuJdAttValue;
import com.xiu.jd.bean.ware.XiuJdBrand;
import com.xiu.jd.bean.ware.XiuJdCategory;
import com.xiu.jd.dao.ware.JDAttributeValueDao;
import com.xiu.jd.schedule.ware.GetJdProductSchedule;
import com.xiu.jd.schedule.ware.ScanJDWareInfoSchedule;
import com.xiu.jd.schedule.ware.UpdateJdProdcutPriceSchedule;
import com.xiu.jd.schedule.ware.UpdateJdProductOnSaleSchedule;
import com.xiu.jd.schedule.ware.UpdateJdProductStockSchedule;
import com.xiu.jd.service.CategoryService;
import com.xiu.jd.service.user.JDConsigneenInfoService;
import com.xiu.jd.service.ware.JDWareService;
import com.xiu.jd.service.ware.JdSellerCategoryService;
import com.xiu.jd.service.ware.JdSkuService;
import com.xiu.jd.utils.BaseUtils;
import com.xiu.jd.utils.CommonUtil;
import com.xiu.jd.utils.ExportExcelUtil;
import com.xiu.jd.utils.FileUtils;
import com.xiu.jd.utils.GlobalConstants;
import com.xiu.jd.utils.ImportExcelUtil;
import com.xiu.jd.utils.WebUtils;
import com.xiu.jd.web.form.JdProductInfoForm;
import com.xiu.usermanager.provider.bean.LocalOperatorsDO;

/**
 * 商品信息
 * 
 * @author kevin liu
 * 
 */
@SuppressWarnings("serial")
@Scope("prototype")
public class ConfigAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ConfigAction.class);

	@Autowired
	private JDWareService jDWareServiceBean;
	@Resource(name = "jdSkuServiceBean")
	private JdSkuService<JDSku> jdSkuServiceBean;

	@Resource(name = "wareInfoManger")
	private WareInfoManger wareInfoManger;
	@Resource
	private CategoryService categoryService;
	@Resource
	private JDAttributeValueDao<JDAttributeValue> jDAttributeValueDaoBean;
	
	@Resource(name="jDConsigneenInfoServiceBean")
	private JDConsigneenInfoService jDConsigneenInfoServiceBean;
	
	private String emailAddress;
	
	private String timeSet;
	
	private String tokenEmailAddress;
	
	
	
    public String getTokenEmailAddress() {
		return tokenEmailAddress;
	}
	public void setTokenEmailAddress(String tokenEmailAddress) {
		this.tokenEmailAddress = tokenEmailAddress;
	}
	public String getTimeSet() {
		return timeSet;
	}
	public void setTimeSet(String timeSet) {
		this.timeSet = timeSet;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String updateOrderEmailUI(){
		JdEmail jdEmail= jDConsigneenInfoServiceBean.getEmail(1);
		emailAddress=jdEmail.getEmailAddress();
    	return "success";
	}
	public String updateOrderEmail(){
		if(emailAddress!=null||!"".equals(emailAddress)){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("emailAddress", emailAddress.trim());
			try {
				jDConsigneenInfoServiceBean.updateOderEmailAddress(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "success";
		
	}
	
    public String updateTokenEmailUI(){
    	JdEmail jdEmail= jDConsigneenInfoServiceBean.getEmail(2);
    	tokenEmailAddress=jdEmail.getEmailAddress().trim();
		timeSet=jdEmail.getTimeSet();
    	return "success";
		
	}
	public String updateTokenEmail(){
		if(tokenEmailAddress!=null||!"".equals(tokenEmailAddress)){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("emailAddress", tokenEmailAddress.trim());
			map.put("timeSet", timeSet);
			try {
				jDConsigneenInfoServiceBean.updateTokenEmailAddress(map);
				this.updateOrderEmailUI();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		return "success";
	}
	
	public static void main(String[] args) {
		String str = "我是【001】真心求救的【002】，你能帮帮我吗";
        Pattern pattern = Pattern.compile("\\【(.*?)\\】");
        Matcher matcher = pattern.matcher(str);       
        while(matcher.find()){
        	 str=str.replace(matcher.group(), "");
             System.out.println(str);
        }
        
		
	}

	
}
