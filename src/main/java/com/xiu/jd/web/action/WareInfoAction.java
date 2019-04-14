package com.xiu.jd.web.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jd.open.api.sdk.request.ware.WareWriteMergeWareFeaturesRequest;
import com.jd.open.api.sdk.response.sellercat.SellerCatsGetResponse;
import com.jd.open.api.sdk.response.ware.SkuCustomGetResponse;
import com.jd.open.api.sdk.response.ware.SkuReadSearchSkuListResponse;
import com.jd.open.api.sdk.response.ware.WareInfoByInfoSearchResponse;
import com.jd.open.api.sdk.response.ware.WareListResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateListingResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateResponse;
import com.jd.open.api.sdk.response.ware.WareWriteDeleteResponse;
import com.jd.open.api.sdk.response.ware.WareWriteMergeWareFeaturesResponse;
import com.xiu.commerce.hessian.model.Product;
import com.xiu.jd.bean.log.JdLog;
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
import com.xiu.jd.service.ProductService;
import com.xiu.jd.service.log.JdLogService;
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
public class WareInfoAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(WareInfoAction.class);

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
	
	/** 商品中心 **/
	@Autowired
	private ProductService productService;
	
	@Resource(name = "jdLogServiceBean")
	private JdLogService jdLogServiceBean;
	


	private PageView<JDWareAndSkuInfo> pageView;
	private JDProduct product = new JDProduct();
	private List<JDSku> skuInfoList;
	private Map<JdSellerCategory, List<JdSellerCategory>> jdSellerCategoryMap;
	private XOPBaseCategory xopCategory;
	private String jdBrand = "0";
	private String num;
	private String status;
	private String xiucode;
	private String xiuCategoryId;
	private JDSku skuinfo;
	private String prJdTitle;
	private JdProductInfoForm jdProductInfoForm = new JdProductInfoForm();
	protected HttpServletResponse response;
	/** 如果是来自直接点击商品信息中的修改按钮,这该值不为空,如果是通过导入Excel后跳转到修改页面则该值为空 ***/
	private String flag;

	private File uploadFile;// 得到上传的文件

	private String uploadFileContentType;// 得到文件的类型

	private String uploadFileFileName;// 得到文件的名称

	/** 多个商品走秀码 ***/
	private String xiuCodes;

	private String jdWareId;

	private Map<Map<String, String>, Map<String, List<JDAttributeValue>>> attrbuteInfoMap;

	/**
	 * 京东分类ID(一级，二级，三级) 0为一级分类
	 */
	private Integer categoryId = 0;
	// 京东一级分类
	private List<JDCategory> jdCategories;

	private List<JDCategory> jdCategories2 = new ArrayList<JDCategory>();

	private List<JDCategory> jdCategories3 = new ArrayList<JDCategory>();

	private Integer firstCategoryId;

	private Integer sendCategoryId;

    private Integer threeCategoryId;
    /***广告词**/
    private String  adContent;
    //是否同步给京东商品
    private int synStatus;
    /**选择 -1,  添加前缀 1 ,删除前缀 2 ，删除包含 3**/
    private int titleStatus;
    
    /***商品标题前缀**/
    private String titlePrefix;
    /**删除商品标题之前前缀**/
    private String deletePreFix;
    
    /**多个京东商品ID**/
    private String jdwardIds;
    
    private int pageSize=20;
    
    /**
     * 商品列表页面,根据走秀码删除商品
     * =new ArrayList<String>()
     */
    private  List<String> xiucodes ;
    //0默认,1：来自商品列表页面的删除按钮
    private int isDelete =0;
	@Resource(name="jdSellerCategoryServiceBean")
	private JdSellerCategoryService jdSellerCategoryServiceBean;
	/**
	 * 京东店内分类ID（一级，二级） 0为一级分类
	 */
	private Integer shopCategoryId = 0;

	private List<JdSellerCategory> firstShopCategorys;
	private List<JdSellerCategory> secondShopCategorys = new ArrayList<JdSellerCategory>();
	private Integer firstShopCategoryId;
	private Integer secondShopCategoryId;
	private String stockOrder="asc";
	
	//控制前台图片""表示箭头向上,down表示箭头向下
	private String jdstockNumOrder="";
	private String jdShopCategory;
	private String brandCode;
	
	
	private String shopType;
	
	private String isPushAll;
	
	private String manualType;
	
	

	public String getManualType() {
		return manualType;
	}

	public void setManualType(String manualType) {
		this.manualType = manualType;
	}

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public String getIsPushAll() {
		return isPushAll;
	}

	public void setIsPushAll(String isPushAll) {
		this.isPushAll = isPushAll;
	}

	/**
	 * 商品信息分页列表查询
	 * 
	 * @return
	 */
	public String findWareInfo() {
		try {
			Map<String, Object> parames = jdProductInfoForm.getJdProductInfoForm();
			List<String> codes = new ArrayList<String>();
			if (this.xiuCodes != null && !"".equals(this.xiuCodes.trim())) {

				String codeString[] = this.xiuCodes.split("\r\n");
				int len = codeString.length;
				if (codeString != null && len > 0) {
					if (len > 1000) {
						String redirectUrl = request.getContextPath() + "/jdGoods/wareInfoList.action";
						request.setAttribute("message", "走秀码数量超出1000个");
						request.setAttribute("redirectUrl", redirectUrl);
						return "message";
					}
					for (String c : codeString) {
						codes.add(c.trim());
					}
					parames.put("xiuCodes", codes);
				}
			}
			// 查询京东一级分类
			if (this.jdCategories == null) {
				this.jdCategories = categoryService.queryJDCategoryByFid(this.categoryId);
			}
			// 第一个分类的id
			if (this.firstCategoryId != null && this.firstCategoryId > 0) {
				this.jdCategories2 = categoryService.queryJDCategoryByFid(this.firstCategoryId);
			}

			if (this.threeCategoryId != null && this.threeCategoryId > 0) {
				this.jdCategories3 = categoryService.queryJDCategoryByFid(this.sendCategoryId);
				parames.put("cid", this.threeCategoryId);
			}
			// 查询京东店内一级分类
			if (this.firstShopCategorys == null) {
				this.firstShopCategorys = jdSellerCategoryServiceBean.querySellerCategoryFid(this.shopCategoryId);
			}
			if (this.firstShopCategoryId != null && this.firstShopCategoryId > 0) {
				this.secondShopCategorys = jdSellerCategoryServiceBean.querySellerCategoryFid(this.firstShopCategoryId);
				parames.put("shopCategory", this.firstShopCategoryId+"-"+this.secondShopCategoryId);
			}
			
			
			logger.info("firstCategoryId=" + firstCategoryId + "  sendCategoryId=" + sendCategoryId + " threeCategoryId=" + threeCategoryId);
			if(this.stockOrder != null && !"".equals(this.stockOrder.trim())){
				parames.put("stockOrder", this.stockOrder.trim());
			}
			this.setPageSize(1000);
			pageView = new PageView<JDWareAndSkuInfo>(this.getPageSize(), this.getCurrentPage());
			parames.put("firstNum", pageView.getFirstResult());
			parames.put("lastNum", pageView.getMaxresult());
			QueryResult<JDWareAndSkuInfo> qr = jDWareServiceBean.getPageResule(parames);
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 修改商品界面
	 * @return
	 */
	public String modifyWareInfo() {
		String redirectUrl = request.getContextPath() + "/jdGoods/wareInfoList.action";
		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("num", Integer.parseInt(num));
		parames.put("xiucode", xiucode.trim());
		String userName = "";
		LocalOperatorsDO localOperatorsDO = getLoginUser();
		if (localOperatorsDO != null) {
			userName = localOperatorsDO.getLoginName();
		}
		//商品信息
		product = jDWareServiceBean.queryWareInfoAndLockWareObject(parames, userName, flag);
		//是否需要品牌映射
		boolean isExists = jDWareServiceBean.jdBrandIsExist(xiucode.trim());
		//false不存在
		if (isExists) {
			//需要品牌的
			jdBrand = "1";
		}
		String message="";
		//不存在品牌的映射
		if(product==null && isExists){
			
			try{
			JDProduct product=jDWareServiceBean.getProductByXiuCode(xiucode.trim());
			StringBuffer sb=new StringBuffer("该商品的京东分类ID为:"+product.getCid());
			
			if(product.getMainimagepath()==null){
				sb.append(",京东分类没有映射,导致错误");
			}else{
				sb.append(",京东三级分类名称为:"+product.getMainimagepath());
			}
			 sb.append(",走秀第三级类目ID为:"+product.getCategoryid().substring(0,4));
			 
			 if(product.getXiu_brand_code()==null){
					sb.append(",京东品牌没有与走秀品牌做映射,导致错误");
				}else{
					sb.append(",走秀品牌编码为:"+product.getXiu_brand_code());
				}
			 
			 sb.append(" ,该商品走秀品牌名称为:"+product.getJdBrandName() );
			 message=sb.toString();
			}catch(Exception e){
				e.printStackTrace();
				message="查询商品异常";
			}
			request.setAttribute("message", message);
			request.setAttribute("redirectUrl", redirectUrl);
			return "message";
		}
		if(product==null){
		    logger.info("走秀码为:"+xiucode.trim()+"的商品,不需要品牌映射就能推送");
			product=jDWareServiceBean.queryWareInfoAndLockWareObjects(parames, userName, flag);
		}
		if(product==null){
			message="该商品不需要做品牌映射就能推送";
			request.setAttribute("message", message);
			request.setAttribute("redirectUrl", redirectUrl);
			return "message";
		}
		//String lockUser = product.getLockuser();
		//logger.info("当前登录的用户为" + "userName" + "  被锁住的用户为" + lockUser);
		// 该商品已经被其他用户锁住了
	/*	if (lockUser != null && !lockUser.equals(userName)) {
			request.setAttribute("message", "走秀码为" + xiucode + "的商品已经被用户名为" + lockUser + "锁住了");
			request.setAttribute("redirectUrl", redirectUrl);
			return "message";
		}*/
        //走秀分类信息
		xopCategory = jDWareServiceBean.queryXopCategory(parames);
		if (xopCategory != null) {
			logger.info(xiucode + "=======================" + xopCategory.getSuperName() + "====================");
		}
        //商品sku列表
		skuInfoList = jDWareServiceBean.querySkuInfo(parames);
		//查询京东店内分类
		jdSellerCategoryMap = jdSellerCategoryServiceBean.querySellerCategory();
		String xiuCode = xiuCategoryId.trim().substring(0,4)+"("+brandCode.trim()+")";
		parames.put("xiuCategoryId", xiuCode);
		attrbuteInfoMap = jDWareServiceBean.queryAttrbuteInfo(parames);
		if(attrbuteInfoMap.isEmpty()){
			parames.put("xiuCategoryId", xiuCategoryId.trim().substring(0,4));
			attrbuteInfoMap = jDWareServiceBean.queryAttrbuteInfo(parames);
		}
		
		return SUCCESS;
	}

	/**
	 * 推送商品
	 * @return
	 */
	public String warePush() {
		List<JDSku> skuList = null;
		String message = "";
		if (skuinfo != null) {
			String skusn[] = skuinfo.getSkusn().split(",");
			String stock[] = skuinfo.getStocknum().split(",");
			String color[] = skuinfo.getColorname().split(",");
			String size[] = skuinfo.getSizevalue().split(",");
			skuList = new ArrayList<JDSku>();
			for (int i = 0; i < skusn.length; i++) {
				JDSku jdSku = new JDSku();
				jdSku.setSkusn(skusn[i].trim());
				jdSku.setStocknum(stock[i].trim());
				jdSku.setColorname(color[i]);
				jdSku.setSizevalue(size[i]);
				skuList.add(jdSku);
			}
			String userName = "";
			LocalOperatorsDO localOperatorsDO = getLoginUser();
			if (localOperatorsDO != null) {
				userName = localOperatorsDO.getLoginName();
			}
			product.setLockuser(userName);
			if (product.getJdWareId() != null && !"".equals(product.getJdWareId())) {
				message = jDWareServiceBean.updateWareInfo(product, skuList);
			} else {
				boolean isExitInJd=this.isExitInJd(skuList, BaseUtils.client, "bag");
				if(isExitInJd){message = jDWareServiceBean.addWare(product, skuList);}
				else{
					message="京东包店已经存在该商品";
				};
			}
		} else {
			message = "该商品没有sku,不能上传该商品";
		}
		try {
			this.response.getWriter().write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String exportExcelUI() {
		logger.info("显示 导入商品模板推送页面");
		return "success";
	}

	/**
	 * 根据模板推送商品
	 * 
	 * @return
	 */
	public String wareTemPush() {
		String redirectUrl = request.getContextPath() + "/jdGoods/wareTemPush.action";
		if (uploadFile != null) {
			String jdCid = "";
			Map<JDProduct, Map<String, List<String>>> jdMap = new HashMap<JDProduct, Map<String, List<String>>>();
			// 取得文件后缀名
			String ext = FileUtils.getFileExt(uploadFileFileName);
			if (!"xls".equals(ext) && !"xlsx".equals(ext)) {
				request.setAttribute("message", "上传文件必须为Excle文件");
				request.setAttribute("redirectUrl", redirectUrl);
				return "message";
			}
			if (!"application/vnd.ms-excel".equalsIgnoreCase(uploadFileContentType) && !"aplication/msexcel".equalsIgnoreCase(uploadFileContentType)) {
				request.setAttribute("message", "文件类型不正确");
				request.setAttribute("redirectUrl", redirectUrl);
				return "message";
			}
			try {
				Object wb = ImportExcelUtil.getWorkbook(uploadFile, uploadFileFileName);
				Object sheet = null;
				if (wb instanceof HSSFWorkbook) {// office 2003
					sheet = ((HSSFWorkbook) wb).getSheetAt(0);
				}
				if (wb instanceof XSSFWorkbook) {// office 2007
					sheet = ((XSSFWorkbook) wb).getSheetAt(0);
				}

				int rowCount = 0;
				if (sheet instanceof HSSFSheet) {
					rowCount = ((HSSFSheet) sheet).getLastRowNum();
				}

				if (sheet instanceof XSSFSheet) {
					rowCount = ((XSSFSheet) sheet).getLastRowNum();
				}

				if (rowCount >= 10) {
					for (int i = 0; i < 11; i++) {
						Object row = null;
						List<String> multiList = null;
						if (i == 0) {
							if (sheet instanceof HSSFSheet) {
								row = ((HSSFSheet) sheet).getRow(i);
							}
							if (sheet instanceof XSSFSheet) {
								row = ((XSSFSheet) sheet).getRow(i);
							}
							Object cell = ImportExcelUtil.getCell(row, 1);
							if (!"".equals(ImportExcelUtil.getCellValueStrTrim(cell))) {// 京东分类ID不能为空
								jdCid = ImportExcelUtil.getCellValueStrTrim(cell);
							}
						} else if (i != 0) {
							if (sheet instanceof HSSFSheet) {
								row = ((HSSFSheet) sheet).getRow(i);
							}
							if (sheet instanceof XSSFSheet) {
								row = ((XSSFSheet) sheet).getRow(i);
							}

							Object cell = ImportExcelUtil.getCell(row, 1);
							if (cell != null) {
								JDProduct jdProduct = new JDProduct();
								Map<String, List<String>> attrMap = new HashMap<String, List<String>>();
								if (ImportExcelUtil.getCellValueDou(cell) != null && !"".equals(ImportExcelUtil.getCellValueDou(cell))) {
									jdProduct.setXiucode(ImportExcelUtil.getCellValueDou(cell));
								}
								if (ImportExcelUtil.getCellValueDou(ImportExcelUtil.getCell(row, 3)) != null
										&& !"".equals(ImportExcelUtil.getCellValueDou(ImportExcelUtil.getCell(row, 3)))) {
									jdProduct.setLenght(ImportExcelUtil.getCellValueDou(ImportExcelUtil.getCell(row, 3)));
								}
								if (ImportExcelUtil.getCellValueDou(ImportExcelUtil.getCell(row, 5)) != null
										&& !"".equals(ImportExcelUtil.getCellValueDou(ImportExcelUtil.getCell(row, 5)))) {
									jdProduct.setWide(ImportExcelUtil.getCellValueDou(ImportExcelUtil.getCell(row, 5)));
								}
								if (ImportExcelUtil.getCellValueDou(ImportExcelUtil.getCell(row, 7)) != null
										&& !"".equals(ImportExcelUtil.getCellValueDou(ImportExcelUtil.getCell(row, 7)))) {
									jdProduct.setHigh(ImportExcelUtil.getCellValueDou(ImportExcelUtil.getCell(row, 7)));
								}

								if (ImportExcelUtil.getCellValueDou(ImportExcelUtil.getCell(row, 9)) != null
										&& !"".equals(ImportExcelUtil.getCellValueDou(ImportExcelUtil.getCell(row, 9)))) {
									jdProduct.setWeight(ImportExcelUtil.getCellValueDou(ImportExcelUtil.getCell(row, 9)));
								}

								jdProduct.setCid(jdCid);

								int lastCellCount = 0;
								if (sheet instanceof HSSFSheet) {
									lastCellCount = ((HSSFRow) row).getLastCellNum();
								}
								if (sheet instanceof XSSFSheet) {
									lastCellCount = ((XSSFRow) row).getLastCellNum();
								}

								for (int j = 10; j < lastCellCount; j++) {
									Object cellAttr = null;
									Object cellValue = null;
									if (j % 2 == 0) {
										if (sheet instanceof HSSFSheet) {
											row = ((HSSFSheet) sheet).getRow(i);
										}
										if (sheet instanceof XSSFSheet) {
											row = ((XSSFSheet) sheet).getRow(i);
										}
										if (row != null) {
											if (row instanceof HSSFRow) {
												cellAttr = ((HSSFRow) row).getCell(j);
												cellValue = ((HSSFRow) row).getCell(j + 1);
											}
											if (row instanceof XSSFRow) {
												cellAttr = ((XSSFRow) row).getCell(j);
												cellValue = ((XSSFRow) row).getCell(j + 1);
											}
										}

										if (cellValue != null) {
											String value = ImportExcelUtil.getCellValueStrTrim(cellValue);
											String[] multiValue = null;
											if (value.contains(",")) {
												multiValue = value.split(",");
											} else if (value.contains("，")) {
												multiValue = value.split("，");
											} else {
												multiValue = value.split(",");
											}

											multiList = new ArrayList<String>();
											for (int k = 0; k < multiValue.length; k++) {
												multiList.add(multiValue[k]);
											}
											attrMap.put(ImportExcelUtil.getCellValueStrTrim(cellAttr), multiList);
										}
									}
								}
								jdMap.put(jdProduct, attrMap);
							} else {
								break;
							}
						}
					}
				}
				jDWareServiceBean.addJdProduct(this.request, jdMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("redirectUrl", redirectUrl);
		return null;
	}

	public String wareSkuTemPush() {
		String redirectUrl = request.getContextPath() + "/jdGoods/wareTemPush.action";
		String message = "";
		if (uploadFile != null) {
			String jdCid = "";
			// 取得文件后缀名
			String ext = FileUtils.getFileExt(uploadFileFileName);
			if (!"xls".equals(ext) && !"xlsx".equals(ext)) {
				request.setAttribute("message", "上传文件必须为Excle文件");
				request.setAttribute("redirectUrl", redirectUrl);
				return "message";
			}
			if (!"application/vnd.ms-excel".equalsIgnoreCase(uploadFileContentType) && !"aplication/msexcel".equalsIgnoreCase(uploadFileContentType)) {
				request.setAttribute("message", "文件类型不正确");
				request.setAttribute("redirectUrl", redirectUrl);
				return "message";
			}
			Object wb;
			try {
				wb = ImportExcelUtil.getWorkbook(uploadFile, uploadFileFileName);
				Object sheet = null;
				if (wb instanceof HSSFWorkbook) {// office 2003
					sheet = ((HSSFWorkbook) wb).getSheetAt(0);
				}
				if (wb instanceof XSSFWorkbook) {// office 2007
					sheet = ((XSSFWorkbook) wb).getSheetAt(0);
				}

				int rowCount = 0;
				if (sheet instanceof HSSFSheet) {
					rowCount = ((HSSFSheet) sheet).getLastRowNum();
				}

				if (sheet instanceof XSSFSheet) {
					rowCount = ((XSSFSheet) sheet).getLastRowNum();
				}
				Object row = null;
				if (sheet instanceof HSSFSheet) {
					row = ((HSSFSheet) sheet).getRow(0);
				}
				if (sheet instanceof XSSFSheet) {
					row = ((XSSFSheet) sheet).getRow(0);
				}
				Object cellJdCid = ImportExcelUtil.getCell(row, 1);
				if (cellJdCid != null) {// 京东类目ID
					jdCid = ImportExcelUtil.getCellValueStrTrim(cellJdCid);
				}

				List<JDSku> jdSkuList = new ArrayList<JDSku>();
				for (int i = 2; i <= rowCount; i++) {
					JDSku jdSku = new JDSku();
					if (sheet instanceof HSSFSheet) {
						row = ((HSSFSheet) sheet).getRow(i);
					}
					if (sheet instanceof XSSFSheet) {
						row = ((XSSFSheet) sheet).getRow(i);
					}

					Object cellSku = ImportExcelUtil.getCell(row, 0);
					if (cellSku != null) {
						jdSku.setSkusn(ImportExcelUtil.getCellValueStrTrim(cellSku));// SKU码
					}

					cellSku = ImportExcelUtil.getCell(row, 1);
					if (cellSku != null) {
						jdSku.setXiucode(ImportExcelUtil.getCellValueStrTrim(cellSku));// 商品走秀码
					}

					cellSku = ImportExcelUtil.getCell(row, 2);
					if (cellSku != null) {
						jdSku.setStocknum(ImportExcelUtil.getCellValueStrTrim(cellSku));// sku库存
					}

					cellSku = ImportExcelUtil.getCell(row, 3);
					if (cellSku != null && !"请选择".equals(ImportExcelUtil.getCellValueStrTrim(cellSku))) {
						jdSku.setColorname(ImportExcelUtil.getCellValueStrTrim(cellSku));// 颜色
					} else {
						message = "请选择模板中的颜色";
					}
					cellSku = ImportExcelUtil.getCell(row, 4);
					if (cellSku != null && !"请选择".equals(ImportExcelUtil.getCellValue(cellSku))) {
						jdSku.setSizevalue(ImportExcelUtil.getCellValue(cellSku));// 尺寸
					} else {
						message = "请选择模板中的尺寸";
					}
					if (!"".equals(message)) {
						this.response.getWriter().write(message);
						return null;
					}
					jdSkuList.add(jdSku);
				}
				message = jDWareServiceBean.pushWare(jdCid, jdSkuList);
				this.response.getWriter().write(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// request.setAttribute("redirectUrl",redirectUrl );
		return null;
	}

	/**
	 * 导出商品信息
	 * 
	 * @return
	 */
	public String exportWareInfo() {
		long startTime=System.currentTimeMillis();
		try {
		Map<String, Object> parames = jdProductInfoForm.getJdProductInfoForm();
		parames.put("cid", this.threeCategoryId);
		List<String> codes = new ArrayList<String>();
		if (this.xiuCodes != null && !"".equals(this.xiuCodes.trim())) {

			String codeString[] = this.xiuCodes.split("\r\n");
			int len = codeString.length;
			if (codeString != null && len > 0) {
				if (len > 200) {
					String redirectUrl = request.getContextPath() + "/jdGoods/wareInfoList.action";
					request.setAttribute("message", "走秀码数量超出200个");
					request.setAttribute("redirectUrl", redirectUrl);
					return "message";
				}
				for (String c : codeString) {
					codes.add(c.trim());
				}
				parames.put("xiuCodes", codes);
			}
		}
		
		
		parames.put("firstNum", "1");
		int pageSize = 59999;
		parames.put("lastNum", pageSize);
		String whichPath = ServletActionContext.getServletContext().getRealPath("") + "/template";
		HSSFWorkbook wb = ImportExcelUtil.getWorkbook(whichPath + "/wareInfo_records.xls");
		QueryResult<JDWareSkuBrand> qr=jDWareServiceBean.queryWareSkuBrandPage(parames);
		if(qr!=null){
			
			
			long total=qr.getTotalrecord();
			int totalPage = getTotalPage(pageSize, (int) total);
			logger.info("总页数为:" + totalPage);
			for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
				if (currentPage != 1) {
					parames.put("firstNum", pageSize * (currentPage - 1) + 1); 
					parames.put("lastNum", pageSize * currentPage);
					logger.info("firstNum:" +  (pageSize * (currentPage - 1) + 1));
					logger.info("lastNum:" +  (pageSize * currentPage));
					parames.put("cid", this.threeCategoryId);
					parames.put("xiuCodes", xiucodes);
					qr = jDWareServiceBean.queryWareSkuBrandPage(parames);
				}
				logger.info("当前页为:" + currentPage);
				if(qr!=null){
					List<JDWareSkuBrand> lists =qr.getResultlist();
					if(lists!=null && lists.size()>0){
						total=qr.getTotalrecord();
						jDWareServiceBean.createWareSkuBrandPage(wb, lists,currentPage-1,pageSize);
					
					}
				}
				
			}
			
		}
		String fileName = "wareInfo_records_" + CommonUtil.getNowTime() + ".xls";
		ExportExcelUtil.downloadExcel(wb, fileName);
		} catch (IOException e) {
			logger.error("导出商品异常"+e);
			e.printStackTrace();
		}
		long endTime=System.currentTimeMillis();
		logger.info("经历的时间为:"+ ((endTime-startTime)/1000)+"秒" );
		return null;
	}

	/** 更新商品信息(更新商品标题) **/
	public String updateWareInfo() {
		try {
			wareInfoManger.updateWareInfo(prJdTitle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除商品和商品SKU信息界面(不包含京东上的商品)
	 * 
	 * @return
	 */
	public String deleWareUI() {
		return SUCCESS;
	}

	/**
	 * 删除商品和商品SKU信息界面(可选删除京东上的商品)
	 * 
	 * @return
	 */
	public String deleProductUI() {

		return SUCCESS;
	}

	/**
	 * 更新广告词信息界面
	 * 
	 * @return
	 */
	public String updateAdContentUI() {

		return SUCCESS;
	}
	
	/***
	 * 删除商品和商品SKU信息动作
	 * 
	 * @return
	 */
	public String deleWare(){
		List<String> xiucodes=new ArrayList<String>();
	    if(xiuCodes!=null){
	    	
	    	if(xiuCodes.contains("delete")){
	    		jDWareServiceBean.deleteAllProduct();
	    		jdSkuServiceBean.deleteAll();
	    		request.setAttribute("message", "删除商品和商品SKU表成功");
	    		
	    	}else if(xiuCodes.contains("initAttr")){
	    		//attr表示 更新 jd_xiu_attvalue表
	    		int count =initJdXiuAttvalueTable("attr");
	    		request.setAttribute("message", "初始化jd_xiu_attvalue表成功,影响的记录数为"+count);
	    	}else if(xiuCodes.contains("initBrand")){
	    		//attr表示 更新 jd_xiu_attvalue表
	    		int num =initJdXiuAttvalueTable("brand");
	    		request.setAttribute("message", "初始化jd_xiu_brand表成功,影响的记录数为"+num);
	    	}else if(xiuCodes.contains("initSellerCatetory")){
	    		//attr表示 更新 jd_xiu_attvalue表
	    		//int num =initJdXiuAttvalueTable("brand");
	    		jdSellerCategoryServiceBean.deleteAllSellerCategory();
	    		String message=initSellerCatetory();
	    		request.setAttribute("message", message);
	    	}

	    	else{
	    		String codes[]=xiuCodes.split("\r\n");
	    		if(codes!=null&& codes.length>0){
		    		for(String c:codes){
			    		xiucodes.add(c.trim());
			    	}
		    		  int count=jDWareServiceBean.deleteWareInfoAndXiuSnByXiuCodes(xiucodes);
		    		  request.setAttribute("message", "影响的记录数为:"+count+"要删除的走秀码为:"+xiuCodes);
		    	}
	    	}
	    	
	    	
	    }
	  
	    String redirectUrl = request.getContextPath() + "/jdGoods/deleWareUI.action";
		request.setAttribute("redirectUrl", redirectUrl);
		return "message";
	}
	private String initSellerCatetory(){
		try {
				JdClient client=BaseUtils.getJdClient();
				SellerCatsGetRequest sellerCatsGetRequest = new SellerCatsGetRequest();
				SellerCatsGetResponse sellerCatsGetResponse = client.execute(sellerCatsGetRequest);
				if(sellerCatsGetResponse!=null){
					List<ShopCategory> categories=  sellerCatsGetResponse.getShopCatList();
					if(categories!=null && categories.size()>0){
						List<JdSellerCategory> jdSellerCategories=new ArrayList<JdSellerCategory>();
						int count=0;
						int batchSize=20;
						for(ShopCategory c:categories){
							count++;
							/*System.out.println("类目ID："+c.getCid()+",是否在首页展示商品："+c.getHomeShow()
									+",名称："+c.getName()+",是否展开子分类:"+c.getOpen()+",排序号:"+c.getOrderNo()
									+",类目是否为父类目:"+c.getParent()+",父类目编号:"+c.getParentId()+",店铺ID:"+c.getShopId());*/
							JdSellerCategory jdSellerCategory =new JdSellerCategory((int)c.getCid(), (int)c.getParentId(), c.getName(), c.getOrderNo(), c.getShopId()+"");
							//0:前台不展示，1:前台展示
							//
							jdSellerCategory.setIsHomeShow(c.getHomeShow()==true? 1:0);
							
							jdSellerCategory.setIsOpen(c.getOpen()==true? 1:0);
							
							jdSellerCategory.setIsParent(c.getParent()==true? 1:0);
							
							jdSellerCategories.add(jdSellerCategory);
							if(count==batchSize){
								jdSellerCategoryServiceBean.insertBatch(jdSellerCategories,batchSize);
								count=0;
								jdSellerCategories=new ArrayList<JdSellerCategory>();
							}
							
						}
						jdSellerCategoryServiceBean.insertBatch(jdSellerCategories,count);
						
						return "初始化jd_seller_category表成功";
						
					}
				}
				
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				return "初始化jd_seller_category异常";
			}
			return "初始化jd_seller_category表失败";
			
		}
	
	public String deleProduct(){
		logger.info("是否同步给京东商品 0:否 ,1是 ,前台参数为:"+this.synStatus);
		logger.info("是否要到商品列表页面 0:否 ,1是 ,前台参数为:"+this.isDelete);
		String redirectUrl ="";
		  if(isDelete==0){
			  redirectUrl = request.getContextPath() + "/jdGoods/deleProductUI.action?synStatus="+this.synStatus;
		  }else{
			  //到商品列表页面
			  redirectUrl = request.getContextPath() + "/jdGoods/wareInfoList.action";
		  }
		 
		List<String> codes=new ArrayList<String>();
		if(this.isDelete!=1 && this.xiuCodes!=null && !"".equals(this.xiuCodes.trim())){
			
			String codeString[]=this.xiuCodes.split("\r\n");
			int len=codeString.length;
    		if(codeString!=null&& len>0){
    			if(len>200){
    				
    				request.setAttribute("message", "走秀码数量超出200个");
    				request.setAttribute("redirectUrl", redirectUrl);
    				return "message";
    			}
    			for(String c:codeString){
    				codes.add(c.trim());
    			}
    		}
		}else if(this.xiucodes!=null && this.xiucodes.size()>0){
			codes.addAll(this.xiucodes);
			
		}
		List<String> xcodes=new ArrayList<String>();
		if(codes.size()>0){
			//根据走秀码查询对应的京东ID
			Map<String, Object> parames=new HashMap<String, Object>();
			parames.put("xiuCodes", codes);
			List<JDProduct> jdProducts=jDWareServiceBean.queryWardIdsByXiuCodes(parames);
			
			if(jdProducts!=null && jdProducts.size()>0){
				for(JDProduct product:jdProducts){
					String xiuCode=product.getXiucode();
					String wareId=product.getJd_ware_id();
					String message=jDWareServiceBean.deleteXiuProductAndJdProduct(xiuCode,wareId,this.synStatus);;
				/*	if(isDelete==0){
						 message=jDWareServiceBean.deleteXiuProductAndJdProduct(xiuCode,wareId,this.synStatus);
					}else{
						if(wareId==null || "".equals(wareId.trim())){
							
						}
					}*/
					
					if(message==null){
						logger.error("调用京东API异常");
					} else {
						if (message != "") {
							xcodes.add(xiuCode);
						}
					}

				}
			}
		}
		if(this.isDelete==1){
			return findWareInfo();
		}else{
		request.setAttribute("message", "删除成功,影响的记录数为" + xcodes.size());
		request.setAttribute("redirectUrl", redirectUrl);

		return "message";
		}
	}
	
	
	public String updateAdContent(){
	
		logger.info("批量更新广告词 ,前台参数为:"+this.adContent);
		String redirectUrl = request.getContextPath() + "/jdGoods/updateAdContentUI.action";
		List<String> codes=new ArrayList<String>();
			String codeString[]=this.xiuCodes.split("\r\n");
			int len=codeString.length;
    		if(codeString!=null&& len>0){
    			if(len>200){
    				
    				request.setAttribute("message", "走秀码数量超出200个");
    				request.setAttribute("redirectUrl", redirectUrl);
    				return "message";
    			}
    			for(String c:codeString){
    				codes.add(c.trim());
    			}
    		}
    		int sucessnum=0;
			int errornum=0;
		List<String> xcodes=new ArrayList<String>();
		if(codes.size()>0){
			
			//根据走秀码查询对应的京东ID
			Map<String, Object> parames=new HashMap<String, Object>();
			parames.put("xiuCodes", codes);
			List<JDProduct> jdProducts=jDWareServiceBean.queryWardIdsByXiuCodes(parames);
			JdClient client = BaseUtils.getJdClient();
			if(jdProducts!=null && jdProducts.size()>0){
				for(JDProduct product:jdProducts){
					String xiuCode=product.getXiucode();
					String wareId=product.getJd_ware_id();
					
					WareUpdateRequest wareUpdateRequest = new WareUpdateRequest();
					wareUpdateRequest.setWareId(wareId + "");
					
					JDProduct jdProduct = new JDProduct();
					jdProduct.setJdWareId(wareId + "");
					jdProduct.setAdContent(adContent);
					wareUpdateRequest.setAdContent(adContent);
					WareUpdateResponse wareUpdateResponse = null;
					try {
						// 更新本地数据库广告词
						jDWareServiceBean.updateProduct(jdProduct);
						// 更新京东上商品广告词
						wareUpdateResponse = client.execute(wareUpdateRequest);
					} catch (JdException e) {
						logger.error("商品ID为" + wareId + "调用京东API更新京东商品标题异常"+e);
						
					}
					if (wareUpdateResponse != null && "0".equals(wareUpdateResponse.getCode())) {
						sucessnum++;
						logger.info("商品ID为" + wareId + "的广告词修改成功");
					} else {
						errornum++;
						logger.info("商品ID为" + wareId + "的广告词修改失败," + wareUpdateResponse.getZhDesc());
					}					
				}
			}
		}
		logger.info("批量更新广告词结束共更新"+codes.size()+"条记录"+"更新成功"+sucessnum+"条记录"+"更新失败"+errornum+"条记录");		

		request.setAttribute("message", "更新成功,影响的记录数为" + codes.size()+"更新成功"+sucessnum+"条记录;"+"更新失败"+errornum+"条记录");
		request.setAttribute("redirectUrl", redirectUrl);

		return "message";
		
	}

	/*** 商品标题修改界面eBayÉÌÆ· ***/
	public String updateProductTitleUI() {
		/*
		 * logger.info("==="+this.titlePrefix); if(this.titlePrefix!=null){
		 * logger.info("===---"+ new
		 * String(Base64.decodeBase64(this.titlePrefix.getBytes())));
		 * 
		 * try { String aa= URLDecoder.decode(this.titlePrefix, "utf-8");
		 * System.out.println(aa); } catch (UnsupportedEncodingException e) { //
		 * 
		 * }
		 */
		return SUCCESS;
	}
	/***商品标题修改界面 动作
	 * ajax
	 * ***/
	public void updateProductTitle(){
		logger.info("商品标题前缀为:"+this.titlePrefix);
		//logger.info("商品标题前缀为:"+UrlEncoding.decode(this.titlePrefix));
		//String redirectUrl = new String(request.getContextPath() + "/jdGoods/updateProductTitleUI.action?titleStatus="+this.titleStatus+"&titlePrefix="+this.titlePrefix);
		//默认是成功的
		String data="1";
		PrintWriter out =null;
		try{
			out = response.getWriter();
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			JdClient client=BaseUtils.getJdClient();
		        logger.info("选择 -1,  添加前缀 1 ,删除前缀 2 ，删除包含 3. 前台输入参数为:"+this.titleStatus);
				if(this.titleStatus!=-1){
					
					// 如果有京东商品ID,根据京东ID进行修改
					if(this.jdwardIds!=null && !"".equals(this.jdwardIds.trim())){
						String wareIdString[]=this.jdwardIds.split("\r\n");
						int len=wareIdString.length;
			    		if(wareIdString!=null && len>0){
			    			if(len>200){
			    				/*request.setAttribute("message", "走秀码数量超出200个");
			    				request.setAttribute("redirectUrl", redirectUrl);
			    				return "message";*/
			    				data="0";
			    				if(out!=null){
			    					out.print(data);
			    					out.flush();
			    					out.close();
			    				}
			    				return;
			    			}
			    			List<String> wareIds=new ArrayList<String>();	
			    			for(String c:wareIdString){
			    				wareIds.add(c.trim());
			    			}
                          
			    			int total = wareIds.size();
			    			int pageSize = 10;
			    			// 总页数
			    			int totalPage = BaseUtils.getTotalPage(pageSize, total);
			    			int index = 0;
			    			for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
			    				StringBuffer wardIdsStringBuffer = new StringBuffer();
			    				for (; index < currentPage * pageSize; index++) {
			    					if (index >= total) {
			    						break;
			    					}
			    					String wareId = wareIds.get(index);
		    						if(wareId!=null && !"".equals(wareId.trim())){
		    							wardIdsStringBuffer.append(wareId.trim()).append(',');
		    						}
			    				}
			    				
			    				if (wardIdsStringBuffer.length() > 0) {
			    					wardIdsStringBuffer.deleteCharAt(wardIdsStringBuffer.length() - 1);
			    					WareListResponse response = getWareListResponse(wardIdsStringBuffer.toString(),BaseUtils.getJdClient());
			    					if (response != null && "0".equals(response.getCode())) {
			    						List<Ware> wareList = response.getWareList();
			    						updateJDAndXiuProductTitle2(this.titleStatus,this.titlePrefix.trim(), wareList);
			    					}
			    				}
			    			}
			    			
			    			//根据京东ID查询走秀码
			    			/*Map<String, Object> parames=new HashMap<String, Object>();
			    			parames.put("jdWardIds", wareIds);
			    			List<JDProduct> jdProducts=jDWareServiceBean.queryXiuCodesByWardIds(parames);
			    			updateJDAndXiuProductTitle(this.titleStatus,this.titlePrefix.trim(),jdProducts);*/
			    			
			    		
			    		}
				 
				} else {
					// 修改全部
					logger.info("===========================更新商品标题开始==========================");
					WareInfoByInfoRequest wareInfoByInfoRequest = new WareInfoByInfoRequest();
					int page = 1;
					wareInfoByInfoRequest.setPage(page + "");
					int pageSize = 100;
					wareInfoByInfoRequest.setPageSize(pageSize + "");
					WareInfoByInfoSearchResponse response = client.execute(wareInfoByInfoRequest);
					if (response != null) {
						String code = response.getCode();
						logger.info("0".equals(code) ? "商品信息入库,调用京东API成功" : "商品信息入库,调用京东API失败,失败原因" + response.getZhDesc());
						int total = response.getTotal();
						int totalPage = 0;
						if (total > 0) {
							totalPage = BaseUtils.getTotalPage(pageSize, total);
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
									/*
									 * ///根据京东ID查询走秀码 List<String>
									 * tempWareIds=new ArrayList<String>();
									 * for(Ware w:wares){
									 * tempWareIds.add(w.getWareId()+""); }
									 * if(tempWareIds.size()>0){ Map<String,
									 * Object> parames=new HashMap<String,
									 * Object>(); parames.put("jdWardIds",
									 * tempWareIds); List<JDProduct>
									 * jdProducts=jDWareServiceBean
									 * .queryXiuCodesByWardIds(parames);
									 * updateJDAndXiuProductTitle
									 * (this.titleStatus
									 * ,this.titlePrefix.trim(),jdProducts); }
									 */
									updateJDAndXiuProductTitle2(this.titleStatus, this.titlePrefix.trim(), wares);

								}
							}
						}
						logger.info("===========================更新商品标题结束==========================");	
					}//else end
					// data="{success:1}"; 更新商品标题成功");
				}
			}
		}catch(Exception e){
			// 更新商品标题异常
			data="-1";
			e.printStackTrace();
			logger.error("商品标题异常"+e);
		}
	
		if(out!=null){
			out.print(data);
			out.flush();
			out.close();
		}

		
	}

	/**
	 * 修改商品标题
	 * 
	 * @param titleStatus
	 *            选择 -1, 添加前缀 1 ,删除前缀 2 ，删除包含 3
	 * @param titlePrefix
	 *            商品标题前缀
	 * @param jdProducts
	 *            商品信息(商品ID,走秀码,商品原来标题,是本地数据库的标题)
	 */
	private void updateJDAndXiuProductTitle(int titleStatus, String titlePrefix, List<JDProduct> jdProducts) {
		if (jdProducts != null && jdProducts.size() > 0) {

			JdClient client = BaseUtils.getJdClient();
			int total = jdProducts.size();
			int pageSize = 10;
			// 总页数
			int totalPage = BaseUtils.getTotalPage(pageSize, total);
			int index = 0;
			for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
				StringBuffer wardIds = new StringBuffer();
				for (; index < currentPage * pageSize; index++) {
					if (index >= total) {
						break;
					}
					JDProduct jdProduct = jdProducts.get(index);
					if (jdProduct != null) {
						String wareId = jdProduct.getJdWareId();
						if (wareId != null && !"".equals(wareId.trim()))
							wardIds.append(wareId.trim()).append(',');
					}

				}
				if (wardIds.length() > 0) {
					wardIds.deleteCharAt(wardIds.length() - 1);
					WareListResponse response = getWareListResponse(wardIds.toString(), BaseUtils.getJdClient());
					if (response != null && "0".equals(response.getCode())) {
						List<Ware> wareList = response.getWareList();
						// 索引下标
						int subscript = 0;
						for (Ware ware : wareList) {
							if (ware != null) {
								// 添加前缀 1 ,删除前缀 2 ，删除包含 3

								WareUpdateRequest wareUpdateRequest = new WareUpdateRequest();
								wareUpdateRequest.setWareId(ware.getWareId() + "");

								JDProduct jdProduct = jdProducts.get(subscript);
								// String title=jdProduct.getTitle().trim();
								String jdTitle = ware.getTitle();
								if (titleStatus == 1) {
									jdTitle = titlePrefix + " " + jdTitle;
									jdProduct.setTitle(titlePrefix + " " + jdTitle);

								} else if (titleStatus == 2) {
									// 添加前缀 1 ,删除前缀 2 ，删除包含 3
									if (jdTitle.startsWith(titlePrefix)) {
										jdTitle = jdTitle.replace(titlePrefix, "");
										// jdTitle=jdTitle.replace(titlePrefix,
										// "");
									}
									jdProduct.setTitle(jdTitle);
								} else if (titleStatus == 3) {
									if (jdTitle.contains(titlePrefix)) {
										jdTitle = jdTitle.replaceAll("\\" + titlePrefix, "");
										// title=title.replaceAll("\\"+titlePrefix,
										// "");
									}
									jdProduct.setTitle(jdTitle);
								}

								wareUpdateRequest.setTitle(jdTitle);
								WareUpdateResponse wareUpdateResponse = null;
								try {
									// 更新本地数据库标题
									jDWareServiceBean.updateProduct(jdProduct);
									// 更新京东上商品标题
									wareUpdateResponse = client.execute(wareUpdateRequest);
								} catch (JdException e) {
									logger.error("调用京东API更新京东商品标题异常");
									e.printStackTrace();
								}
								if (wareUpdateResponse != null && "0".equals(wareUpdateResponse.getCode())) {
									logger.info("商品ID为" + ware.getWareId() + "的标题修改成功");
								} else {
									logger.info("商品ID为" + ware.getWareId() + "的标题修改失败," + wareUpdateResponse.getZhDesc());
								}

							}
							subscript++;
						}
					} else {
						logger.error("调用京东API异常");
					}
				}

			}
		}

	}

	/**
	 * 修改商品标题
	 * 
	 * @param titleStatus
	 *            选择 -1, 添加前缀 1 ,删除前缀 2 ，删除包含 3
	 * @param titlePrefix
	 *            商品标题前缀
	 * @param jdProducts
	 *            商品信息(商品ID,走秀码,商品原来标题,是本地数据库的标题)
	 */
	private void updateJDAndXiuProductTitle2(int titleStatus, String titlePrefix, List<Ware> wards) {
		if (wards != null && wards.size() > 0) {

			JdClient client = BaseUtils.getJdClient();
			int total = wards.size();
			int pageSize = 10;
			// 总页数
			int totalPage = BaseUtils.getTotalPage(pageSize, total);
			int index = 0;
			for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
				StringBuffer wardIds = new StringBuffer();
				for (; index < currentPage * pageSize; index++) {
					if (index >= total) {
						break;
					}
					Ware Ware = wards.get(index);
					if (Ware != null) {
						String wareId = Ware.getWareId() + "";
						if (wareId != null && !"".equals(wareId.trim()))
							wardIds.append(wareId.trim()).append(',');
					}

				}
				if (wardIds.length() > 0) {
					wardIds.deleteCharAt(wardIds.length() - 1);
					WareListResponse response = getWareListResponse(wardIds.toString(), BaseUtils.getJdClient());
					if (response != null && "0".equals(response.getCode())) {
						List<Ware> wareList = response.getWareList();

						for (Ware ware : wareList) {
							if (ware != null) {
								// 添加前缀 1 ,删除前缀 2 ，删除包含 3

								WareUpdateRequest wareUpdateRequest = new WareUpdateRequest();
								wareUpdateRequest.setWareId(ware.getWareId() + "");
								
								JDProduct jdProduct = new JDProduct();
								jdProduct.setJdWareId(ware.getWareId() + "");
								// String title=jdProduct.getTitle().trim();
								String jdTitle = ware.getTitle();
								if (titleStatus == 1) {
									logger.info("要添加的前缀为:"+titlePrefix+"需要删除之前的前缀为："+this.deletePreFix);
									if(this.deletePreFix != null && !"".equals(this.deletePreFix)){
										jdTitle = titlePrefix + " " + jdTitle.replace(this.deletePreFix, "").trim();
									}else{
										jdTitle = titlePrefix + " " + jdTitle.trim();
									}
									jdProduct.setTitle(jdTitle);

								} else if (titleStatus == 2) {
									// 添加前缀 1 ,删除前缀 2 ，删除包含 3
									if (jdTitle.startsWith(titlePrefix)) {
										jdTitle = jdTitle.replace(titlePrefix, "");
									}
									jdProduct.setTitle(jdTitle);
								} else if (titleStatus == 3) {
									if (jdTitle.contains(titlePrefix)) {
										jdTitle = jdTitle.replaceAll("\\" + titlePrefix, "");
									}
									jdProduct.setTitle(jdTitle);
								}

								wareUpdateRequest.setTitle(jdTitle);
								WareUpdateResponse wareUpdateResponse = null;
								try {
									// 更新本地数据库标题
									jDWareServiceBean.updateProduct(jdProduct);
									// 更新京东上商品标题
									wareUpdateResponse = client.execute(wareUpdateRequest);
								} catch (JdException e) {
									logger.error("调用京东API更新京东商品标题异常");
									e.printStackTrace();
								}
								if (wareUpdateResponse != null && "0".equals(wareUpdateResponse.getCode())) {
									logger.info("商品ID为" + ware.getWareId() + "的标题修改成功");
								} else {
									logger.info("商品ID为" + ware.getWareId() + "的标题修改失败," + wareUpdateResponse.getZhDesc());
								}

							}
						}
					} else {
						logger.error("调用京东API异常");
					}
				}

			}
		}

	}

	/**
	 * 根据商品ID查询商品信息
	 * 
	 * @param wareIds
	 *            京东商品ID
	 * @return
	 */

	private WareListResponse getWareListResponse(String wareIds, JdClient client) {
		if (wareIds.length() > 0) {
			WareListRequest wareListRequest = new WareListRequest();
			wareListRequest.setWareIds(wareIds);
			WareListResponse response = null;
			try {
				response = client.execute(wareListRequest);
				return response;
			} catch (Exception e) {

				e.printStackTrace();
				if (response != null) {
					logger.error(response.getZhDesc());
				}
			}
		}

		return null;
	}

	private int initJdXiuAttvalueTable(String name) {

		int count = 0;
		int num = 0;
		int currentPage = 1;
		int pageSize = 10;
		PageView<XiuJdCategory> pageView = new PageView<XiuJdCategory>(pageSize, currentPage);
		XiuJdCategory xiuJdCategory = new XiuJdCategory();
		QueryResult<XiuJdCategory> qr = null;
		try {
			qr = categoryService.getXiuJdCategoryPageResule(xiuJdCategory, currentPage, pageSize);
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (qr != null) {
			pageView.setQueryResult(qr);
		}
		// 总页数
		int totalPage = (int) pageView.getTotalpage();
		// int totalPage=getTotalPage(pageSize, total);

		for (; currentPage <= totalPage; currentPage++) {
			if (currentPage != 1) {
				new PageView<XiuJdCategory>(pageSize, currentPage);
				try {
					qr = categoryService.getXiuJdCategoryPageResule(xiuJdCategory, currentPage, pageSize);
				} catch (Exception e) {
					e.printStackTrace();
				}
				pageView.setQueryResult(qr);
			}

			List<XiuJdCategory> xiuJdCategories = pageView.getRecords();
			if (xiuJdCategories != null && xiuJdCategories.size() > 0) {
				for (XiuJdCategory c : xiuJdCategories) {
					if (c != null) {
						String jdCid = c.getJdCid();
						String jdCategoryName = c.getJdFullName();
						System.out.println("id= " + c.getId() + " -->" + jdCategoryName);
						// 走秀三级分类ID queryXiuJdAttValue
						String xiuChildcode = c.getXiuCid();
						// 更新jd_xiu_attvalu 表
						if ("attr".equals(name)) {
							XiuJdAttValue xiuJdAttValue = new XiuJdAttValue();
							xiuJdAttValue.setXiuChildcode(xiuChildcode);
							try {
								QueryResult<XiuJdAttValue> queryResult = categoryService.getXiuJdAttValuePageResule(xiuJdAttValue, 1, 10000);
								List<XiuJdAttValue> attValues = queryResult.getResultlist();
								if (attValues != null && attValues.size() > 0) {
									for (XiuJdAttValue a : attValues) {
										if (a != null) {
											// 根据id更新表 jd_xiu_attvalue
											// int id= a.getId();
											a.setJdCid(jdCid);
											a.setJdCategoryName(jdCategoryName);
											XiuJdAttValue jdAttValue = new XiuJdAttValue();
											jdAttValue.setJdCid(jdCid);
											jdAttValue.setJdCategoryName(jdCategoryName);
											jdAttValue.setXiuChildcode(xiuChildcode);
											count = jDAttributeValueDaoBean.updateAttributeValueByXiuChildCode(jdAttValue);
											count = count + count;
											logger.info("影响的记录数为: " + count);

										}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						// 更新 jd_xiu_brand表
						if ("brand".equals(name)) {
							// 更新jd_xiu_brand表通过京东第三级类目ID
							XiuJdBrand brand = new XiuJdBrand();
							brand.setJdCid(jdCid);
							brand.setJdCategoryName(jdCategoryName);
							num = categoryService.updateBrandByJdCid(brand);
							num = num + num;
							logger.info("影响的记录数为: " + num);

						}

					}
				}

			}
		}
		if (num != 0) {
			return num;
		}
		return count;

	}

	/**
	 * 异步ajax请求 查询京东分类=================================================
	 * 
	 * @return
	 */
	public void getJdCategory() {
		String joson = null;
		logger.info("京东分类ID为:" + this.categoryId);
		try {
			if (this.categoryId != null && this.categoryId > 0) {
				List<JDCategory> jdCategories = categoryService.queryJDCategoryByFid(this.categoryId);
				JSONArray jsonArray = JSONArray.fromObject(jdCategories);
				joson = jsonArray.toString();
			}
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(joson);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 异步查询京东店内分类
	 */
	public void getJdShopCategory() {
		String joson = null;
		logger.info("京东店内分类ID为:" + this.shopCategoryId);
		try {
			if (this.shopCategoryId != null && this.shopCategoryId > 0) {
				List<JdSellerCategory> secondShopCategorys = jdSellerCategoryServiceBean.querySellerCategoryFid(this.shopCategoryId);
				JSONArray jsonArray = JSONArray.fromObject(secondShopCategorys);
				joson = jsonArray.toString();
			}
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(joson);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除商品的sku图片并再次上传该sku图片
	 * 
	 * @return
	 */
	public String deleAndAddPic() {
		
		String message = "";
	    int pageSize=100;
	    Map<String,Object> params = new HashMap<String,Object>();
		params.put("jdWareId", jdWareId);
		int count =jdSkuServiceBean.findSkuCount(params);
		if(count>0){
			for(int i=1;i<=count;i=i+pageSize){
				Map<String,Object> parames = new HashMap<String,Object>();
				parames.put("start", i);
				parames.put("end", i+pageSize);
				parames.put("jdWareId", jdWareId);
				logger.info("=====删除京东无法访问图片：记录数"+"[start:"+i+"end:"+(i+pageSize)+"]");
				List<JDSku> jdSkus = jdSkuServiceBean.findSku(parames);
				if (jdSkus != null && jdSkus.size() > 0) {
					String mainPic = jDWareServiceBean.getJdProductMainPic(jdWareId);
					message = jdSkuServiceBean.pushSkuPic(jdSkus, mainPic);
				}
			}
		}
		
		try {
			this.response.getWriter().write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询京东店内分类
	 * @return
	 */
	public String shopCategoryList(){
		jdwardIds = this.jdwardIds;
		jdSellerCategoryMap = jdSellerCategoryServiceBean.querySellerCategory();
		return SUCCESS;
	}
	/**
	 * 批量更新(覆盖)京东店内分类
	 * @return
	 */
	public void updateShopCategory(){
		if(this.jdwardIds != null && !this.jdwardIds.isEmpty()){
			jdSellerCategoryServiceBean.updateShopCategory(jdwardIds,this.jdShopCategory);
		}
		try {
			this.response.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 批量更新(新增)京东店内分类
	 */
	public void addShopCategory(){
		if(this.jdwardIds != null && !this.jdwardIds.isEmpty()){
			jdSellerCategoryServiceBean.addShopCategory(jdwardIds,this.jdShopCategory);
		}
		try {
			this.response.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 拉取京东属性和属性值页面
	 * @return
	 */
	public String pullAttrAndValueUI(){
		try{
			// 查询京东一级分类
			if (this.jdCategories == null) {
				this.jdCategories = categoryService.queryJDCategoryByFid(this.categoryId);
			}
			// 第一个分类的id
			if (this.firstCategoryId != null && this.firstCategoryId > 0) {
				this.jdCategories2 = categoryService.queryJDCategoryByFid(this.firstCategoryId);
			}
			
			if (this.threeCategoryId != null && this.threeCategoryId > 0) {
				this.jdCategories3 = categoryService.queryJDCategoryByFid(this.sendCategoryId);
			}
		}catch(Exception e){
		}
		return SUCCESS;
	}
	
	public String pullAttrAndValue(){
		String redirectUrl=request.getContextPath()+"/jdGoods/pullAttrAndValueUI.action";
		String messages="";
		try{
		
		if(this.threeCategoryId != null && !"".equals(this.threeCategoryId)){
			messages = wareInfoManger.getJdAttribute(this.threeCategoryId+"");
		}else{
			request.setAttribute("message", "您没选择京东分类");
			request.setAttribute("redirectUrl", redirectUrl);
			return "message";
		}
	
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("message", messages);
		request.setAttribute("redirectUrl", redirectUrl);
		return "message";
	}
	
	public String pullProduct(){
		try {
			if(this.xiucodes != null && this.xiucodes.contains("initProduct")){
				GetJdProductSchedule gjps =(GetJdProductSchedule)WebApplicationContextUtils.getWebApplicationContext(this.request.getSession().getServletContext())
				.getBean("getJdProductSchedule");
				logger.info("==============查询分类start================>");
				gjps.getJdProdcut();
				super.returnAjaxStr("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 商品信息分页列表查询
	 * 
	 * @return
	 */
	public String findBatchWareInfo() {
		try {
			Map<String, Object> parames = jdProductInfoForm.getJdProductInfoForm();
			List<String> codes = new ArrayList<String>();
			if (this.xiuCodes != null && !"".equals(this.xiuCodes.trim())) {

				String codeString[] = this.xiuCodes.split("\r\n");
				int len = codeString.length;
				if (codeString != null && len > 0) {
					if (len > 1000) {
						String redirectUrl = request.getContextPath() + "/jdWare/wareInfoBatchList.action";
						request.setAttribute("message", "走秀码数量超出1000个");
						request.setAttribute("redirectUrl", redirectUrl);
						return "message";
					}
					for (String c : codeString) {
						codes.add(c.trim());
					}
					parames.put("xiuCodes", codes);
				}
			}
			// 查询京东一级分类
			if (this.jdCategories == null) {
				this.jdCategories = categoryService.queryJDCategoryByFid(this.categoryId);
			}
			// 第一个分类的id
			if (this.firstCategoryId != null && this.firstCategoryId > 0) {
				this.jdCategories2 = categoryService.queryJDCategoryByFid(this.firstCategoryId);
			}

			if (this.threeCategoryId != null && this.threeCategoryId > 0) {
				this.jdCategories3 = categoryService.queryJDCategoryByFid(this.sendCategoryId);
				parames.put("cid", this.threeCategoryId);
			}
			// 查询京东店内一级分类
			if (this.firstShopCategorys == null) {
				this.firstShopCategorys = jdSellerCategoryServiceBean.querySellerCategoryFid(this.shopCategoryId);
			}
			if (this.firstShopCategoryId != null && this.firstShopCategoryId > 0) {
				this.secondShopCategorys = jdSellerCategoryServiceBean.querySellerCategoryFid(this.firstShopCategoryId);
				parames.put("shopCategory", this.firstShopCategoryId+"-"+this.secondShopCategoryId);
			}
			
			
			logger.info("firstCategoryId=" + firstCategoryId + "  sendCategoryId=" + sendCategoryId + " threeCategoryId=" + threeCategoryId);
			if(this.stockOrder != null && !"".equals(this.stockOrder.trim())){
				parames.put("stockOrder", this.stockOrder.trim());
			}
			this.setPageSize(1000);
			pageView = new PageView<JDWareAndSkuInfo>(1000, this.getCurrentPage());
			parames.put("firstNum", pageView.getFirstResult());
			parames.put("lastNum", pageView.getMaxresult());
			QueryResult<JDWareAndSkuInfo> qr = jDWareServiceBean.getPageResule(parames);
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	public String pushWareInBatch(){
		String resultString ="";
		String[] shopTypes=null;
		List<String> pushXiuCodeList=new ArrayList<String>();
		if (shopType!=null){
			shopTypes=shopType.split(",");
		}
		
	    if(isPushAll.equals("1")){//推送选中商品
	    	if(this.xiucodes.size()>0){
	    		pushXiuCodeList.addAll(this.xiucodes);
	    	}else{
	    		resultString="没有选中商品";
	    	}    	
	    }else if(isPushAll.equals("0")){//推送全部商品，默认推送未推送商品
	    	Map<String,Object> parames = new HashMap<String,Object>();
	    	parames.put("status", "0");
	    	parames.put("firstNum", "1");
			parames.put("lastNum", "100");
			parames.put("stockOrder", "asc");
			
			QueryResult<JDWareAndSkuInfo> qr = jDWareServiceBean.getPageResule(parames);
		    long total=qr.getTotalrecord();
			if(total>100){
				parames.put("lastNum", parames.put("lastNum", total));
				qr = jDWareServiceBean.getPageResule(parames);				
			}
			List<JDWareAndSkuInfo> list=qr.getResultlist();
			if (list!=null&&list.size()>0) {
				for (JDWareAndSkuInfo jdWareAndSkuInfo : list) {
					String xiuCode=jdWareAndSkuInfo.getXiucode();
					pushXiuCodeList.add(xiuCode);
				}
			}	    	
	    }	
		if (pushXiuCodeList!=null&&pushXiuCodeList.size()>0) {
			for (String shoptypeString : shopTypes) {
				shoptypeString=shoptypeString.trim();
				for (String c : pushXiuCodeList) {
					Map<String,Object> map=this.getPushProductInfo(c,shoptypeString);
					if(map==null){
						resultString=resultString+"店铺："+shoptypeString+" 走秀码："+c+" 没有数据"+"<br>";
						continue;
					}					
					String message="";
					List<JDSku> skuInfoList=(List<JDSku>) map.get("skuInfoList");
					String attrbute =(String) map.get("attrbuteInfoMap");
					JDProduct product=(JDProduct) map.get("product");
					Boolean isExitInJd=false;
					if (product!=null&&(product.getJdWareId() == null || "".equals(product.getJdWareId()))) {
												
						if (GlobalConstants.SHOP_TYEP_EBAY.equals(shoptypeString)) {//ebay
							isExitInJd=this.isExitInJd(skuInfoList, BaseUtils.clientEbay, shoptypeString);
							if(!isExitInJd){
								message="在京东ebay已经存在该商品"+product.getXiucode();
							}else{
							message = jDWareServiceBean.addEbayWareInBatch(
									product, skuInfoList, c, attrbute,
									shoptypeString);
							}
						}else if(GlobalConstants.SHOP_TYEP_XIU.equals(shoptypeString)){//bag
							isExitInJd=this.isExitInJd(skuInfoList, BaseUtils.clientXiu, shoptypeString);
							if(!isExitInJd){
								message="在京东XIU已经存在该商品"+product.getXiucode();
							}else{
						    message = jDWareServiceBean.addXiuWareInBatch(
										product, skuInfoList, c, attrbute,
										shoptypeString);
							}
						}else{
							isExitInJd=this.isExitInJd(skuInfoList, BaseUtils.client, shoptypeString);
							if(!isExitInJd){
								message="在包店已经存在该商品"+product.getXiucode();
							}else{
							message = jDWareServiceBean.addWareInBatch(//xiu店
									product, skuInfoList, c, attrbute,
									shoptypeString);	
							}
						}
						resultString=resultString+"店铺："+shoptypeString+message+"<br>";
					}else if(!"".equals(product.getJdWareId())&&product.getJdWareId() != null){
						resultString=resultString+"店铺："+shoptypeString+" 走秀码："+c+" 已经推送"+"<br>";
					}
				}
			}	
		}
		
		
		
		
		try {
			this.response.getWriter().write(resultString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
		
	}
	private Boolean isExitInJd(List<JDSku> skuInfoList,JdClient jdClient,String shopType){
		Boolean result=true;
		try {
			if (skuInfoList.size() > 0) {
				for (JDSku jdSku : skuInfoList) {
					try {
						SkuReadSearchSkuListRequest request=new SkuReadSearchSkuListRequest();
						request.setOutId(jdSku.getSkusn());
						request.setPageNo( 1);
						
						SkuReadSearchSkuListResponse response=jdClient.execute(request); 
						if("0".equals(response.getCode())){
							List<com.jd.open.api.sdk.domain.ware.SkuReadService.Sku> list=response.getPage().getData();
							if(list==null){
								return true;
							}
							if(list!=null&&list.size()>0){
								int count=list.size();
								int delCount=0;
								for (com.jd.open.api.sdk.domain.ware.SkuReadService.Sku sku : list) {
									if(sku.getStatus()==1){
										return false;
									}else{
										delCount++;
										WareWriteDeleteRequest delRequest=new WareWriteDeleteRequest();
										delRequest.setWareId( sku.getWareId());
										WareWriteDeleteResponse delResponse=jdClient.execute(delRequest); 
										logger.info("上传商品 wareId" +sku.getWareId()+"返回码："+delResponse.getCode()+delResponse.getZhDesc());
									}
								}
								if(count==delCount){
									return true;
								}
							}
						}
						
					} catch (Exception e) {
						logger.error("通过走秀码查询京东编号失败  走秀sku：" + jdSku.getSkusn());
						e.printStackTrace();
					}
				}

			}
		} catch (Exception e) {
		  logger.error("通过走秀码查询京东编号失败 ");
		  e.printStackTrace();
		
		}
		return result;
		
		
	}
	

	/**
	 * 获取推送商品信息
	 * @param xiuCode 走秀码
	 * @param shopType 店铺类型，ebay、xiu、bag 店
	 * @return
	 */
	private  Map<String,Object> getPushProductInfo(String xiuCode,String shopType) {
		Map<String,Object> jdProductMap=new HashMap<String,Object>();
		XOPBaseCategory xopCategory=null;
		List<JDSku>  skuInfoList=null;
		String attrbuteInfoMap=null;
		Map<JdSellerCategory, List<JdSellerCategory>> jdSellerCategoryMap=null;
		
		Map<String, Object> parames = new HashMap<String, Object>();
		//parames.put("num", Integer.parseInt(num));
		parames.put("xiucode", xiuCode.trim());
		String userName = "";
		LocalOperatorsDO localOperatorsDO = getLoginUser();
		if (localOperatorsDO != null) {
			userName = localOperatorsDO.getLoginName();
		}
		boolean isExists =false;
		JDProduct product=null;


		//商品信息
		if(GlobalConstants.SHOP_TYEP_EBAY.equals(shopType)){
			product=jDWareServiceBean.queryEbayWareInfoAndLockWareObject(parames, userName, "");
			//isExists=jDWareServiceBean.jdEbayBrandIsExist(xiuCode.trim());
			xopCategory = jDWareServiceBean.queryEbayXopCategory(parames);
			skuInfoList=jDWareServiceBean.queryEbaySkuInfo(parames);
			
		}
		else if(GlobalConstants.SHOP_TYEP_XIU.equals(shopType)){
			product=jDWareServiceBean.queryXiuWareInfoAndLockWareObject(parames, userName, "");
			//isExists=jDWareServiceBean.jdBagBrandIsExist(xiuCode.trim());
			xopCategory = jDWareServiceBean.queryXiuXopCategory(parames);
			skuInfoList=jDWareServiceBean.queryXiuSkuInfo(parames);
			
		}else
		{
		    product = jDWareServiceBean.queryWareInfoAndLockWareObject(parames, userName, "");
		    isExists=jDWareServiceBean.jdBrandIsExist(xiuCode.trim());
		    xopCategory = jDWareServiceBean.queryXopCategory(parames);
		    skuInfoList = jDWareServiceBean.querySkuInfo(parames);
		  
		
		}
		
		if(skuInfoList.size()>0){
			for (JDSku jdSku : skuInfoList) {
				if(jdSku.getColorname()==null||"".equals(jdSku.getColorname())){
					jdSku.setColorname("图片色");
				}
				if(jdSku.getSizevalue()==null||"".equals(jdSku.getSizevalue())){
					jdSku.setSizevalue("F");
				}
			}
		}
		
		if(product==null){
			this.mapBrand(xiuCode,shopType);
			if(GlobalConstants.SHOP_TYEP_XIU.equals(shopType)){
				product=jDWareServiceBean.getXiuProductByXiuCode(xiuCode.trim());
				product = jDWareServiceBean.queryXiuWareInfoAndLockWareObject(parames, userName, shopType);
			}
			else if(GlobalConstants.SHOP_TYEP_EBAY.equals(shopType)){
				product=jDWareServiceBean.getEbayProductByXiuCode(xiuCode.trim());
				product = jDWareServiceBean.queryEbayWareInfoAndLockWareObject(parames, userName, shopType);
			}
			else{
				product=jDWareServiceBean.getProductByXiuCode(xiuCode.trim());
				product = jDWareServiceBean.queryWareInfoAndLockWareObject(parames, userName, shopType);
			};
			
			
		}		                            
		
		
	
		String message="";
		/*//不存在品牌的映射
		if(product==null && isExists){
			
			try{
			JDProduct product=jDWareServiceBean.getProductByXiuCode(xiuCode.trim());
			StringBuffer sb=new StringBuffer("该商品的京东分类ID为:"+product.getCid());
			
			if(product.getMainimagepath()==null){
				sb.append(",京东分类没有映射,导致错误");
			}else{
				sb.append(",京东三级分类名称为:"+product.getMainimagepath());
			}
			 sb.append(",走秀第三级类目ID为:"+product.getCategoryid().substring(0,4));
			 
			 if(product.getXiu_brand_code()==null){
					sb.append(",京东品牌没有与走秀品牌做映射,导致错误");
				}else{
					sb.append(",走秀品牌编码为:"+product.getXiu_brand_code());
				}
			 
			 sb.append(" ,该商品走秀品牌名称为:"+product.getJdBrandName() );
			 message=sb.toString();
			}catch(Exception e){
				e.printStackTrace();
				message="查询商品异常";
			}
			request.setAttribute("message", message);
			request.setAttribute("redirectUrl", redirectUrl);
			
		}*/
		/*if(product==null){
		    logger.info("走秀码为:"+xiuCode.trim()+"的商品,不需要品牌映射就能推送");
			product=jDWareServiceBean.queryWareInfoAndLockWareObjects(parames, userName, flag);
		}
		if(product==null){
			message="该商品不需要做品牌映射就能推送";
			request.setAttribute("message", message);
			request.setAttribute("redirectUrl", redirectUrl);
			return "message";
		}*/
		/*String lockUser = product.getLockuser();*/
		/*logger.info("当前登录的用户为" + "userName" + "  被锁住的用户为" + lockUser);*/
		// 该商品已经被其他用户锁住了
		/*if (lockUser != null && !lockUser.equals(userName)) {
			request.setAttribute("message", "走秀码为" + xiucode + "的商品已经被用户名为" + lockUser + "锁住了");
			request.setAttribute("redirectUrl", redirectUrl);
			return "message";
		}*/
        //走秀分类信息
		/*if(shopType.equals(GlobalConstants.SHOP_TYEP_EBAY)){
			xopCategory = jDWareServiceBean.queryEbayXopCategory(parames);
		}else if(shopType.equals(GlobalConstants.SHOP_TYEP_BAG)){
			xopCategory = jDWareServiceBean.queryBagXopCategory(parames);
		}else{
			xopCategory = jDWareServiceBean.queryXopCategory(parames);
		}*/
		
		if (xopCategory != null) {
			logger.info(xiucode + "=======================" + xopCategory.getSuperName() + "====================");
		}
        //商品sku列表
		
		
		
		//查询京东店内分类
	    jdSellerCategoryMap = jdSellerCategoryServiceBean.querySellerCategory();
	    if(product==null){
	    	return null;
	    }
		xiuCode = product.getCategoryid().trim().substring(0,4)+"("+product.getBrandcode().trim()+")";
		parames.put("xiuCategoryId", xiuCode);
		attrbuteInfoMap=jDWareServiceBean.queryAttrbutes(parames, product,shopType);
		if(attrbuteInfoMap.isEmpty()){
			parames.put("xiuCategoryId", product.getCategoryid().substring(0,4));
			attrbuteInfoMap = jDWareServiceBean.queryAttrbutes(parames, product, shopType);
		}
		
	
		jdProductMap.put("xopCategory", xopCategory);
		jdProductMap.put("skuInfoList", skuInfoList);
		jdProductMap.put("attrbuteInfoMap", attrbuteInfoMap);	
		jdProductMap.put("product", product);
		
		return jdProductMap;
		
	}
	
	
	private void mapBrand(String xiuCode,String shopType){
		
	    List<XiuJdBrand> list=new ArrayList<XiuJdBrand>();
	    XiuJdBrand XiuJdBrand = new XiuJdBrand();
		XiuJdBrand xiuJdBrand =new XiuJdBrand();
		String catCode="";

		try {
			
			JDAttributeValue jDAttributeValue=null;
			
			JDProduct product = null;
			
			if (shopType.equals(GlobalConstants.SHOP_TYEP_EBAY)) {
				product=jDWareServiceBean.getEbayProductByXiuCode(xiuCode);
			}else if (shopType.equals(GlobalConstants.SHOP_TYEP_XIU)){
				product=jDWareServiceBean.getXiuProductByXiuCode(xiuCode);
			}else{
				product=jDWareServiceBean.getProductByXiuCode(xiuCode);
			}
			logger.info("店铺类型："+shopType+"获取京东商品信息");
			catCode=product.getCid();
			
			
		    jDAttributeValue = jDWareServiceBean.getJdBrand(xiuCode, catCode, shopType);
		    if(jDAttributeValue==null){
		    	logger.info("店铺类型："+shopType+"走秀码：" +xiuCode+"获取京东品牌属性失败");
		    	return;
		    }
			
			logger.info("店铺类型："+shopType+"获取京东品牌属性");
			
			xiuJdBrand.setXiuBrandCode(product.getBrandcode());
			XiuJdBrand xopBrand=categoryService.getXiuBrand(xiuJdBrand);
			logger.info("店铺类型："+shopType+"获取走秀品牌信息");
			
			XiuJdBrand.setJdCid(catCode);
			XiuJdBrand.setJdVname(jDAttributeValue.getName());
			XiuJdBrand.setJdVid(String.valueOf(jDAttributeValue.getVid()));
			XiuJdBrand.setXiuBrandCode(xopBrand.getXiuBrandCode());
			XiuJdBrand.setXiuBrandName(xopBrand.getXiuBrandName());
			list.add(XiuJdBrand);
			if (shopType.equals(GlobalConstants.SHOP_TYEP_EBAY)) {
				categoryService.insertEbayJdBrand(list);
			}else if (shopType.equals(GlobalConstants.SHOP_TYEP_XIU)){
				categoryService.insertJdBrandXiu(list);
			}else{
				categoryService.insertXiuJdBrand(list);
			}
			logger.info("店铺类型："+shopType+"没有品牌映射的进行品牌映射");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("品牌映射失败：" +"店铺类型："+shopType+"走秀码："+xiuCode);
		}
		
		
	}
	
	
	
	
	public String updateAllAdContent(){
		String redirectUrl = request.getContextPath() + "/jdWare/updateAdContentUI.action";
		try {
		logger.info("更新整个店铺广告词 ,前台参数为:"+this.adContent);
		if(this.adContent==null||"".equals(this.adContent)){
			request.setAttribute("message", "广告词不能为空");
			request.setAttribute("redirectUrl", redirectUrl);
			return "message";
		}
		try {
			logger.info("更新本地数据库广告词"+this.adContent);
			jDWareServiceBean.updateJdAdcontent(this.adContent);	
			logger.info("更新广告词成功"+this.adContent);
		} catch (Exception e) {
			logger.info("更新广告词失败"+this.adContent);
		    e.printStackTrace();
		}
		
		Map<String, Object> parames = new HashMap<String,Object>();
		parames.put("firstNum", "1");
		int pageSize = 59999;
		parames.put("lastNum", pageSize);
		QueryResult<JDWareSkuBrand> qr=jDWareServiceBean.queryWareSkuBrandPage(parames);
		JdClient client = BaseUtils.getJdClient();
		if(qr!=null){
			long total=qr.getTotalrecord();
			int totalPage = getTotalPage(pageSize, (int) total);
			logger.info("总页数为:" + totalPage);
			for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
				if (currentPage != 1) {					
					parames.put("firstNum", pageSize * (currentPage - 1) + 1); 
					parames.put("lastNum", pageSize * currentPage);
					logger.info("firstNum:" +  (pageSize * (currentPage - 1) + 1));
					logger.info("lastNum:" +  (pageSize * currentPage));
					parames.put("cid", this.threeCategoryId);
					parames.put("xiuCodes", xiucodes);
					qr = jDWareServiceBean.queryWareSkuBrandPage(parames);
				}
				logger.info("更改广告词当前页为:" + currentPage);
				if(qr!=null){
					List<JDWareSkuBrand> lists =qr.getResultlist();
					if(lists!=null && lists.size()>0){
						total=qr.getTotalrecord();
						for (JDWareSkuBrand jdWareSkuBrand : lists) {
							String wareId=jdWareSkuBrand.getJdWareId();
							if(wareId==null||"".equals(wareId)){
								continue;
							}
							try {
								
								WareUpdateRequest wareUpdateRequest = new WareUpdateRequest();
								wareUpdateRequest.setWareId(wareId + "");
								
								JDProduct jdProduct = new JDProduct();
								jdProduct.setJdWareId(wareId + "");
								jdProduct.setAdContent(adContent);
								wareUpdateRequest.setAdContent(adContent);
								WareUpdateResponse wareUpdateResponse = null;
								try {
									wareUpdateResponse = client.execute(wareUpdateRequest);
								} catch (Exception e) {
									logger.error("商品ID为" + wareId + "调用京东API更新京东商品标题异常"+e);
									e.printStackTrace();
								}
								if (wareUpdateResponse != null && "0".equals(wareUpdateResponse.getCode())) {
									logger.info("商品ID为" + wareId + "的广告词修改成功");
								} else {
									logger.info("商品ID为" + wareId + "的广告词修改失败," + wareUpdateResponse.getZhDesc());
								}
							
							} catch (Exception e) {
								e.printStackTrace();
								logger.info("商品ID为" + wareId + "的广告词修改失败,");
							}
						}
					
					}
				}
				
			}
			
		}
		request.setAttribute("message", "更新完成");
		request.setAttribute("redirectUrl", redirectUrl);
		return "message";
		} catch (Exception e) {
			request.setAttribute("message", "更新失败"+e);
			request.setAttribute("redirectUrl", redirectUrl);
			return "message";
		}
	}
	
	public String manualFuction(){
		try {
			logger.info("获取类型："+manualType);
			if(this.manualType != null &&! "".equals(manualType)){
				
				if("price".equals(manualType)){
					logger.info("==============手工启动价格更新开始================>");
					UpdateJdProdcutPriceSchedule gjps =(UpdateJdProdcutPriceSchedule)WebApplicationContextUtils.getWebApplicationContext(this.request.getSession().getServletContext())
					.getBean("updateJdProdcutPriceSchedule");
					gjps.updateJdProdcutPrice();
					logger.info("==============手工启动价格更新结束================>");
				}else if ("stock".equals(manualType)){
					logger.info("==============手工启动库存更新开始================>");
					UpdateJdProductStockSchedule gjps =(UpdateJdProductStockSchedule)WebApplicationContextUtils.getWebApplicationContext(this.request.getSession().getServletContext())
							.getBean("updateJdProductStockSchedule");
							gjps.updateJdProductStock();
					logger.info("==============手工启动库存更新结束================>");
					
				}else if ("online".equals(manualType)){
					logger.info("==============手工启动上下架更新开始================>");
					UpdateJdProductOnSaleSchedule gjps =(UpdateJdProductOnSaleSchedule)WebApplicationContextUtils.getWebApplicationContext(this.request.getSession().getServletContext())
							.getBean("updateJdProductOnSaleSchedule");
							gjps.updateJdProductOnSale();
					logger.info("==============手工启动上下架更新结束================>");
					
				}else if ("all".equals(manualType)){
					logger.info("==============手工启动上下架状态价格更新开始================>");
					ScanJDWareInfoSchedule gjps =(ScanJDWareInfoSchedule)WebApplicationContextUtils.getWebApplicationContext(this.request.getSession().getServletContext())
					.getBean("scanJDWareInfoSchedule");
					gjps.getJdWareInfo();
					logger.info("==============手工启动上下架状态价格更新结束================>");
					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String updateProductStatus(){
		
		String resultString ="";
		List<String> jdWareIdList=new ArrayList<String>();
		Map<String, Object> parames=new HashMap<String, Object>();
		List<JDProduct> jdProducts=null;
		if(this.xiucodes.size()>0){
			parames.put("xiuCodes", this.xiucodes);
			try {
				jdProducts=jDWareServiceBean.queryWardIdsByXiuCodes(parames);
				List<JDProduct> jdProductList=new ArrayList<JDProduct>();
		    	if(jdProducts!=null){
		    		for (JDProduct jdProduct : jdProducts) {
		    			String wareId=jdProduct.getJd_ware_id();
						try {
							if(wareId==null||"0".equals(wareId)){
								continue;
							}
							WareUpdateListingRequest request=new WareUpdateListingRequest();
				    		request.setWareId( jdProduct.getJd_ware_id());
				    		request.setTradeNo(WebUtils.getFullYearString() );
				    		WareUpdateListingResponse response=BaseUtils.client.execute(request); 
				    		JDProduct newjdProduct=new JDProduct();
				    		if(response!=null&&"0".equals(response.getCode())){
				    			newjdProduct.setJdWareId(jdProduct.getJd_ware_id());
				    			newjdProduct.setOnLineStatus(1);//1 位上架状态
				    			jdProductList.add(newjdProduct);
				    		}else{
				    			logger.info("京东商品wareId:"+jdProduct.getJd_ware_id()+"上架失败");
				    		}
				    		
						} catch (Exception e) {
							logger.info("京东商品wareId:"+jdProduct.getJd_ware_id()+"上架异常");
							e.printStackTrace();
						}
					}
		    		try {
		    			if(jdProductList!=null){
			    			logger.info("批量更新商品状态总数："+jdProductList.size());
			    			jDWareServiceBean.updateProductOnlineBatch(jdProductList, 200);
			    			resultString="批量更新商品成功";
		    			}
					} catch (Exception e) {
						logger.error("批量更新商品状态失败");
						resultString="批量更新商品成功";
						e.printStackTrace();
					}
		    	}
			} catch (Exception e) {
				logger.error("通过走秀码查询京东ID失败");
				e.printStackTrace();
			}
			
    	}else{
    		resultString="没有选中商品";
    	}    	
		
		
    	
    	
    	try {
			this.response.getWriter().write(resultString);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
	   
	}
	
	
	  public String sevenToReturn(){
	       try {
	    	logger.info("==七天无理由退换对接开始==");
	    	Map<String, Object> parames = new HashMap<String,Object>();
			parames.put("firstNum", "1");
			int pageSize = 1000;
			parames.put("lastNum", pageSize);
			parames.put("stockOrder", "asc");
			QueryResult<JDWareAndSkuInfo> qr=	jDWareServiceBean.getPageResule(parames);
			JdClient client = BaseUtils.getJdClient();
			if(qr!=null){
				long total=qr.getTotalrecord();
				int totalPage = getTotalPage(pageSize, (int) total);
				logger.info("更新七天无理由退换货总页数为:" + totalPage);
				for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
					try {
					
						if (currentPage != 1) {					
							parames.put("firstNum", pageSize * (currentPage - 1) + 1); 
							parames.put("lastNum", pageSize * currentPage);
							logger.info("firstNum:" +  (pageSize * (currentPage - 1) + 1));
							logger.info("lastNum:" +  (pageSize * currentPage));
							qr = jDWareServiceBean.getPageResule(parames);
							
						}
						logger.info("更新七天无理由退换货当前页为:" + currentPage);
						if(qr!=null){
							List<JDWareAndSkuInfo> lists =qr.getResultlist();
							if(lists!=null && lists.size()>0){
								StringBuffer s=new StringBuffer();
								Map<String,Object> map=new HashMap<String,Object>();
								for (JDWareAndSkuInfo jdWareAndSkuInfo : lists) {
									String jdWareId=jdWareAndSkuInfo.getJdWareId();
									String xiuCode=jdWareAndSkuInfo.getXiucode();
									if(jdWareId!=null&&!"".equals(jdWareId)){
										s.append(xiuCode+",");
										map.put(xiuCode, jdWareId);
									}
									
								}
								String xiuCodes=s.toString();
								xiuCodes=xiuCodes.substring(0, xiuCodes.length()-1);
								List<Product> productList=(List<Product>) productService.batchLoadProducts(xiuCodes);
								if(productList!=null&&productList.size()>0){
									
									for (Product product : productList) {
										JdLog jdLog=new JdLog();
										String xiuCode=product.getXiuSN();
										String globalFlag=product.getGlobalFlag();
										try {
											
											if(map.containsKey(xiuCode)){
												String jdWareId=(String) map.get(xiuCode);
												logger.info("is7ToReturn:"+jdWareId+" xiuCode:"+xiuCode+" globalFlag："+globalFlag);
												jdLog.setWareid(jdWareId);
												jdLog.setXiucode(xiuCode);
												jdLog.setStatus(1);//成功
												jdLog.setLogtype(7);//七天无理由退换货日志类型
												jdLog.setDescribe("globalFlag:"+globalFlag);
												String featureValue="1";//默认支持七天退换货
												/*0 普通商品 1、直发商品、2全球速递 3、香港速递、4国内直发、5海外直发							 * 
												 */
												if("2".equals(globalFlag)||"3".equals(globalFlag)||"5".equals(globalFlag)){
													featureValue="0";
												}
												WareWriteMergeWareFeaturesRequest request=new WareWriteMergeWareFeaturesRequest();		
												request.setWareId(Long.valueOf(jdWareId));
												request.setFeatureKey( "is7ToReturn" );
												request.setFeatureValue(featureValue );
												request.setTimestamp(getDateString());
												try {
													WareWriteMergeWareFeaturesResponse response=client.execute(request);
													if(response==null||!"0".equals(response.getCode())){
														logger.error("七天无理由退换货失败 wareId:"+jdWareId+" xiuCode:"+xiuCode+" 错误码："+response.getZhDesc());
														jdLog.setStatus(2);
														jdLog.setDescribe("globalFlag:"+globalFlag+" "+response.getZhDesc());
													}
												
												} catch (Exception e) {
													logger.error("七天无理由退换货失败 wareId:"+jdWareId+" xiuCode:"+xiuCode);
													e.printStackTrace();
												} 
											}
										
										} catch (Exception e) {
											e.printStackTrace();
										}
										try {
											if(jdLog!=null){
												this.saveSevenReturnLog(jdLog);
											}
										} catch (Exception e) {
											logger.error("添加日志失败 xiuCode："+xiuCode);
											e.printStackTrace();
										}
											
									}
								}
								
							}
						}
					
					
					} catch (Exception e) {
						logger.error("更新七天无理由退换货当前页为:" + currentPage+"失败");
						e.printStackTrace();
					}
			}
					
					
			}
			
			logger.info("==七天无理由退换对接结束==");
			
	    	} catch (Exception e) {
	    		logger.error("七天无理由退换对接失败");
				e.printStackTrace();
			}
					
				
	    	return "SUCCESS";
	    }
	    
	    
	    public void saveSevenReturnLog(JdLog jdLog) {
			
			String xiuCode = jdLog.getXiucode();
			if (xiuCode != null) {
				if (xiuCode.length() >= 20) {
					jdLog.setXiucode(xiuCode.substring(0, 20));
				} else {
					jdLog.setXiucode(xiuCode);
				}
			}
			jdLogServiceBean.insert(jdLog);
		}
	    
	    
	    private String getDateString(){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(new Date());
		}
	    

	



	
	

	public PageView<JDWareAndSkuInfo> getPageView() {
		return pageView;
	}

	public void setPageView(PageView<JDWareAndSkuInfo> pageView) {
		this.pageView = pageView;
	}

	public JDProduct getProduct() {
		return product;
	}

	public void setProduct(JDProduct product) {
		this.product = product;
	}

	public List<JDSku> getSkuInfoList() {
		return skuInfoList;
	}

	public void setSkuInfoList(List<JDSku> skuInfoList) {
		this.skuInfoList = skuInfoList;
	}

	public Map<JdSellerCategory, List<JdSellerCategory>> getJdSellerCategoryMap() {
		return jdSellerCategoryMap;
	}

	public void setJdSellerCategoryMap(Map<JdSellerCategory, List<JdSellerCategory>> jdSellerCategoryMap) {
		this.jdSellerCategoryMap = jdSellerCategoryMap;
	}

	public XOPBaseCategory getXopCategory() {
		return xopCategory;
	}

	public void setXopCategory(XOPBaseCategory xopCategory) {
		this.xopCategory = xopCategory;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getJdBrand() {
		return jdBrand;
	}

	public void setJdBrand(String jdBrand) {
		this.jdBrand = jdBrand;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getXiucode() {
		return xiucode;
	}

	public void setXiucode(String xiucode) {
		this.xiucode = xiucode;
	}

	public String getXiuCategoryId() {
		return xiuCategoryId;
	}

	public void setXiuCategoryId(String xiuCategoryId) {
		this.xiuCategoryId = xiuCategoryId;
	}

	public JDSku getSkuinfo() {
		return skuinfo;
	}

	public Map<Map<String, String>, Map<String, List<JDAttributeValue>>> getAttrbuteInfoMap() {
		return attrbuteInfoMap;
	}

	public void setAttrbuteInfoMap(Map<Map<String, String>, Map<String, List<JDAttributeValue>>> attrbuteInfoMap) {
		this.attrbuteInfoMap = attrbuteInfoMap;
	}

	public void setSkuinfo(JDSku skuinfo) {
		this.skuinfo = skuinfo;
	}

	public JdProductInfoForm getJdProductInfoForm() {
		return jdProductInfoForm;
	}

	public void setJdProductInfoForm(JdProductInfoForm jdProductInfoForm) {
		this.jdProductInfoForm = jdProductInfoForm;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public void setServletResponse(HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		this.response = response;
	}

	public void setFlag(String flag) {
		// logger.info("flag  =" + flag);
		this.flag = flag;
	}

	public void setXiuCodes(String xiuCodes) {
		// logger.info("要商品的走秀码为:" +xiuCodes);
		this.xiuCodes = xiuCodes;
	}

	public String getXiuCodes() {
		return xiuCodes;
	}

	public String getPrJdTitle() {
		return prJdTitle;
	}

	public void setPrJdTitle(String prJdTitle) {
		this.prJdTitle = prJdTitle;
	}

	public String getJdWareId() {
		return jdWareId;
	}

	public void setJdWareId(String jdWareId) {
		this.jdWareId = jdWareId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public List<JDCategory> getJdCategories() {
		return jdCategories;
	}

	public List<JDCategory> getJdCategories2() {
		return jdCategories2;
	}

	public List<JDCategory> getJdCategories3() {
		return jdCategories3;
	}

	public Integer getFirstCategoryId() {
		return firstCategoryId;
	}

	public void setFirstCategoryId(Integer firstCategoryId) {
		this.firstCategoryId = firstCategoryId;
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

	public int getSynStatus() {
		return synStatus;
	}

	public void setSynStatus(int synStatus) {
		this.synStatus = synStatus;
	}

	public int getTitleStatus() {
		return titleStatus;
	}

	public void setTitleStatus(int titleStatus) {
		this.titleStatus = titleStatus;
	}

	public String getTitlePrefix() {
		return titlePrefix;
	}

	public void setTitlePrefix(String titlePrefix) {
		this.titlePrefix = titlePrefix;
	}

	public String getDeletePreFix() {
		return deletePreFix;
	}

	public void setDeletePreFix(String deletePreFix) {
		this.deletePreFix = deletePreFix;
	}

	public String getJdwardIds() {
		return jdwardIds;
	}

	public void setJdwardIds(String jdwardIds) {
		this.jdwardIds = jdwardIds;
	}
	public void setShopCategoryId(Integer shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public List<String> getXiucodes() {
		return xiucodes;
	}

	public void setXiucodes(List<String> xiucodes) {
		/*if(xiucodes!=null){
			System.out.println(xiucodes.size()+"------------");
		}*/
		this.xiucodes = xiucodes;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public List<JdSellerCategory> getFirstShopCategorys() {
		return firstShopCategorys;
	}

	public Integer getFirstShopCategoryId() {
		return firstShopCategoryId;
	}

	public void setFirstShopCategoryId(Integer firstShopCategoryId) {
		this.firstShopCategoryId = firstShopCategoryId;
	}

	public List<JdSellerCategory> getSecondShopCategorys() {
		return secondShopCategorys;
	}

	public void setSecondShopCategorys(List<JdSellerCategory> secondShopCategorys) {
		this.secondShopCategorys = secondShopCategorys;
	}

	public Integer getSecondShopCategoryId() {
		return secondShopCategoryId;
	}

	public void setSecondShopCategoryId(Integer secondShopCategoryId) {
		this.secondShopCategoryId = secondShopCategoryId;
	}

	public String getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(String stockOrder) {
		
		this.stockOrder = stockOrder;
	}

	public String getJdstockNumOrder() {
		return jdstockNumOrder;
	}

	public void setJdstockNumOrder(String jdstockNumOrder) {
		this.jdstockNumOrder = jdstockNumOrder;
	}

	public void setJdShopCategory(String jdShopCategory) {
		this.jdShopCategory = jdShopCategory;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getAdContent() {
		return adContent;
	}

	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}
	
	

	
}
