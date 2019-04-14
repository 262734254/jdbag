package com.xiu.jd.service.ware.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.domain.ware.WareTemplate;
import com.jd.open.api.sdk.request.ware.WareAddRequest;
import com.jd.open.api.sdk.request.ware.WareDeleteRequest;
import com.jd.open.api.sdk.request.ware.WareGetRequest;
import com.jd.open.api.sdk.request.ware.WareInfoByInfoRequest;
import com.jd.open.api.sdk.request.ware.WareSkuAddRequest;
import com.jd.open.api.sdk.request.ware.WareTemplateIdsAndNamesGetRequest;
import com.jd.open.api.sdk.request.ware.WareTemplateToWaresUpdateRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateDelistingRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateRequest;
import com.jd.open.api.sdk.request.ware.WareWriteMergeWareFeaturesRequest;
import com.jd.open.api.sdk.response.ware.WareAddResponse;
import com.jd.open.api.sdk.response.ware.WareDeleteResponse;
import com.jd.open.api.sdk.response.ware.WareGetResponse;
import com.jd.open.api.sdk.response.ware.WareInfoByInfoSearchResponse;
import com.jd.open.api.sdk.response.ware.WareSkuAddResponse;
import com.jd.open.api.sdk.response.ware.WareTemplateIdsAndNamesGetResponse;
import com.jd.open.api.sdk.response.ware.WareTemplateToWaresUpdateResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateDelistingResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateResponse;
import com.jd.open.api.sdk.response.ware.WareWriteMergeWareFeaturesResponse;
import com.xiu.channel.inventory.api.InventoryService;
import com.xiu.channel.inventory.api.dto.QueryInventoryRequest;
import com.xiu.channel.inventory.api.dto.QueryInventoryResponse;
import com.xiu.commerce.hessian.model.Product;
import com.xiu.commerce.hessian.model.Sku;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDAttribute;
import com.xiu.jd.bean.ware.JDAttributeValue;
import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.bean.ware.JDSku;
import com.xiu.jd.bean.ware.JDWareAndSkuInfo;
import com.xiu.jd.bean.ware.JDWareSkuBrand;
import com.xiu.jd.bean.ware.JdSkuIdSyn;
import com.xiu.jd.bean.ware.OnLineBlackProductBean;
import com.xiu.jd.bean.ware.ProductAttributeInfoModel;
import com.xiu.jd.bean.ware.ProductEntry;
import com.xiu.jd.bean.ware.SettlementProductInfo;
import com.xiu.jd.bean.ware.XOPBaseCategory;
import com.xiu.jd.bean.ware.XiuJdBrand;
import com.xiu.jd.constants.GlobalConstants;
import com.xiu.jd.dao.ware.JDAttributeDao;
import com.xiu.jd.dao.ware.JDAttributeValueDao;
import com.xiu.jd.dao.ware.JDWareAndSkuInfoDao;
import com.xiu.jd.dao.ware.JDWareDao;
import com.xiu.jd.dao.ware.JdSkuDao;
import com.xiu.jd.dao.ware.XOPBaseCategoryDao;
import com.xiu.jd.dao.ware.impl.JDCategoryDaoBean;
import com.xiu.jd.service.ProductService;
import com.xiu.jd.service.ware.JDWareService;
import com.xiu.jd.service.ware.JdSkuIdSynService;
import com.xiu.jd.sku.ExportTem;
import com.xiu.jd.utils.BaseUtils;
import com.xiu.jd.utils.CommonUtil;
import com.xiu.jd.utils.ExportExcelUtil;
import com.xiu.jd.utils.HttUtils;
import com.xiu.jd.utils.ParseProperties;
import com.xiu.jd.utils.WebUtils;
import com.xiu.settlement.common.ProdSettlementHessianService;

@Service("jDWareServiceBean")
public class JDWareServiceBean extends BaseUtils implements JDWareService {

	
	protected static final Logger logger = Logger.getLogger(JDWareServiceBean.class);
	protected static final String LENGHT = "290";
	protected static final String WIDE = "230";
	protected static final String WEIGHT = "0.5";
	protected static final String HIGH = "50";
	//
	/** 商品DAO **/
	@Autowired
	private JDWareDao<JDProduct> jDWareDaoBean;

	/** 商品 SKU DAO **/
	@Autowired
	private JdSkuDao<JDSku> jdSkuDaoBean;

	@Resource(name = "exportTem")
	private ExportTem exportTem;
	

	/** 商品信息 **/
	@Autowired
	private JDWareAndSkuInfoDao<JDWareAndSkuInfo> jDWareAndSkuInfoDaoBean;
	/** 商品分类信息 **/
	@Autowired
	private XOPBaseCategoryDao<XOPBaseCategory> xopBaseCategoryDaoBean;
	/** 商品中心 **/
	@Autowired
	private ProductService productService;
	/** 京东商品属性 **/
	@Autowired
	private JDAttributeDao<JDAttribute> jDAttributeDaoBean;
	/** 京东商品属性值 **/
	@Autowired
	private JDAttributeValueDao<JDAttributeValue> jDAttributeValueDaoBean;
	
	
	//同步京东商品skuid到本地数据jd_product_sku_info中的jdskuid字段
	@Resource(name = "jdSkuIdSynServiceBean")
	private JdSkuIdSynService jdSkuIdSynServiceBean;
	
	@Resource
	private JDCategoryDaoBean jdCategoryDao;

	/**
	 * 调用搜索slors
	 * 
	 * @Autowired private CommonsHttpSolrServer commonsHttpSolrServer;
	 ***/

	/** 渠道中心 **/
	@Autowired
	private InventoryService inventoryService;
	
	/**结算系统接口**/
	@Resource(name="prodSettlementHessianService")
	private ProdSettlementHessianService prodSettlementHessianService ;

	public String insertProductAndSKU(HttpServletRequest request, String solrXiuSns, String productXiuCodes, int rownum, String userName,
			long batchNum) throws Exception {
		// TODO
		List<Product> products = productService.batchLoadProducts(productXiuCodes);

		List<JDProduct> jdProducts = new LinkedList<JDProduct>();
		long startTime = System.currentTimeMillis();
		StringBuilder messages = new StringBuilder();
		if (products != null && products.size() > 0) {
			for (Product p : products) {
				if(p==null){
					continue;
				}
				String jdCid = "";
				String catAndBrand = p.getBCatCode().trim().substring(0,4)+"("+p.getBrandCode().trim()+")";
				logger.info("catAndBrand=="+catAndBrand+",xiuCode="+p.getXiuSN()+",走秀四级分类ID"+p.getBCatCode()+",品牌"+p.getBrandCode());
				String isCatAndBrand =null;
				
				try {
					isCatAndBrand=jDWareDaoBean.findJDCategoryId(catAndBrand);
				} catch (Exception e1) {
					logger.error("根据走秀类目与品牌绑定,查询京东类目异常=="+"xiuCode="+p.getXiuSN()+",走秀四级分类ID"+p.getBCatCode()+",品牌"+p.getBrandCode());
					messages.append("走秀码" + p.getXiuSN() + "走秀类目为:"+p.getBCatCode()+"走秀类目与品牌绑定异常,返回多个结果</br>");
					logger.error("据走秀类目与品牌绑定,查询京东类目异常"+e1);
					e1.printStackTrace();
				}
				
				logger.info("isCatAndBrand=="+isCatAndBrand+",xiuCode="+p.getXiuSN());
				if(isCatAndBrand == null || "".equals(isCatAndBrand)){
					logger.info("isCatAndBrand is null,"+"xiuCode="+p.getXiuSN()+",走秀四级分类ID"+p.getBCatCode()+",品牌"+p.getBrandCode());
					String xiuCat = p.getBCatCode().trim().substring(0,4);
					String isXiuCat =null;
					try {
						isXiuCat=jDWareDaoBean.findJDCategoryId(xiuCat);
					} catch (Exception e) {
					    logger.error("根据走秀类目,查询京东类目异常"+"xiuCode="+p.getXiuSN()+",走秀四级分类ID"+p.getBCatCode()+",品牌"+p.getBrandCode());
					    messages.append("走秀码" + p.getXiuSN() + "走秀类目为:"+p.getBCatCode()+"走秀类目与京东类目异常,返回多个结果</br>");
					    logger.error("秀类目与京东类目异常,返回多个结果"+e);
						e.printStackTrace();
					}
					if(isXiuCat != null && !"".equals(isXiuCat)){
						jdCid = isXiuCat;
					}
				}else{
					jdCid = isCatAndBrand;
				}
				if (jdCid == null || "".equals(jdCid)) {
					logger.info("走秀的四级分类为：" + p.getBCatCode() + "==" + p.getBCatName() + ",与京东类目没有映射");
					messages.append("走秀类目(" + p.getBCatName() + "),与京东类目没有映射,走秀码" + p.getXiuSN() + "</br>");
					continue;
				}
				
				String xiucode = p.getXiuSN();
				boolean isExists = jDWareDaoBean.xiuCodeIsExistsNation(xiucode.trim());
				logger.info(isExists ? "导入走秀码为 " + xiucode + " 在本地不存在" : "走秀码为 " + xiucode + " 在本地存在");
				String imageURL = p.getMasterImgUrl();
				// 得到的是100013490001
				String mainSkuSn = imageURL.substring(imageURL.lastIndexOf('/') + 1, imageURL.length());
				// 图片前缀
				String uploadImagePrefix = "";
				if (imageURL.contains("upload")) {
					uploadImagePrefix = ParseProperties.getPropertiesValue("IMG_PREFIX").trim() + "/"+ imageURL.substring(imageURL.indexOf("upload"), imageURL.length());

				}
				uploadImagePrefix = uploadImagePrefix.trim();
				logger.info("图片前缀路径为" + uploadImagePrefix);
				// //true:本地不存在,false本地存在
				JDProduct entity = null;
				if (isExists) {
					entity = new JDProduct();
					Double productXiuPrice=p.getPrdOfferPrice(); //单位为元
					logger.info("走秀码为:"+xiucode+"的走秀价为"+productXiuPrice+"元");
					//对接走秀活动价到京东
					if("true".equals(ParseProperties.IS_ACTIVITY_PRICE)){
						//走秀商品活动价
						Double prdActivityPrice= p.getPrdActivityPrice();
						if(prdActivityPrice!=null && prdActivityPrice>0){
							logger.info("走秀码为:"+xiucode+"的活动价为"+prdActivityPrice+"元");
							productXiuPrice=prdActivityPrice;
						}
					}
					if(productXiuPrice==null || "null".equals(productXiuPrice) || productXiuPrice<=0){
						logger.error("走秀码为:"+xiucode+"的走秀价为"+productXiuPrice+" 失败");
						messages.append("走秀码为:"+xiucode+"的走秀价为"+productXiuPrice+" 失败");
						continue;
					}
					
					SettlementProductInfo settlementProductInfo= getProductSettlementInfo(prodSettlementHessianService, xiucode.trim(), WebUtils.doubleToInt(productXiuPrice)*100);
					if(settlementProductInfo==null){
						messages.append("走秀码:"+xiucode+"调用结算系统失败</br>");
						continue;
					}
					int tempJdPrice=WebUtils.longParseInt(settlementProductInfo.getFinnalXiuPrice());//分转换为元
					if(tempJdPrice<=0){
						messages.append("走秀码:"+xiucode+"解析价格"+settlementProductInfo.getFinnalXiuPrice()+"失败</br>");
						continue;
					}
					int jdPrice=WebUtils.computePrice(new BigDecimal(tempJdPrice)); //单位为元
					//[走秀码为:74149189,商品中心走秀价7660.0元,结算系统价格76600000元,最终推送给京东的价格76600000元
					logger.info("走秀码为:"+xiucode+",商品中心走秀价"+productXiuPrice+"元,结算系统价格"+tempJdPrice+"元,最终推送给京东的价格"+jdPrice+"元");
					
					// 商品官网标准价
					entity.setJdprice(jdPrice + "");
					// 商品市场价格
					Double xiuMaketPrice=p.getPrdListPrice();
					if(xiuMaketPrice==null || jdPrice>xiuMaketPrice){
						entity.setMarketprice(WebUtils.buildMarketPrice(jdPrice) + "");
					}else{
						entity.setMarketprice(xiuMaketPrice + "");
					}
					
					entity.setCostprice(productXiuPrice + "");

					entity.setLenght(LENGHT);
					entity.setWide(WIDE);
					entity.setHigh(HIGH);
					entity.setWeight(WEIGHT);
					entity.setXiuBrandCode(p.getBrandCode());
					entity.setOnLineStatus(0);
					// http://pic-handler.xiu.com/upload/goods20110924/10001349/100013490001

					logger.info("商品主图片路径  " + imageURL + "\n 主sku为 " + mainSkuSn + "/g1_200_200.jpg");

					entity.setMainimagepath(uploadImagePrefix.trim() + "/" + ParseProperties.IMAGE_SIZE);
					entity.setGlobalFlag(p.getGlobalFlag());
					String title=p.getPrdName();
					title=title.replaceAll("(美国直发)", "").replaceAll("（美国直发）", "").replaceAll("（）", "")
					.replaceAll("【eBay代购】", "").replaceAll("【eBay商品】", "");         
					 if (title.contains("(")) {                                                                                    
						 title = title.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\）", "").replaceAll("美国直发", "");
						}                                                                                                            
		            if (title.contains("（")) {                                                                            
		            	title = title.replaceAll("\\（", "").replaceAll("\\）", "").replaceAll("\\)", "").replaceAll("美国直发", "");
					}
		            if(title.contains("(")){
		            	title = title.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("全球速递", "");
		            }
		            if(title.contains("（")){
		            	title = title.replaceAll("\\（", "").replaceAll("\\）", "").replaceAll("全球速递", "");
		            }
		            if(title.contains("全球速递")){
		            	title = title.replaceAll("全球速递", "");
		            }

		            //entity.setTitle(p.getBrandName() +" "+  title);
					ProductEntry productEntry = new ProductEntry();
					XiuJdBrand jb=new XiuJdBrand();
					jb.setXiuBrandCode(p.getBrandCode());
					String xiuBrandName=null;
				    if(p.getBrandCode()!=null&&!"".equals(p.getBrandCode())){
				    	xiuBrandName=jdCategoryDao.getXopBrand(jb).getXiuBrandName();
				    }
					entity.setTitle(xiuBrandName+title);
					// 模板
					List<String> allPartNumbers = new ArrayList<String>();
					allPartNumbers.add(xiucode);
					productEntry.setAttributeList(this.findProductAttributeList(allPartNumbers));
					productEntry.setProductId(p.getInnerID() + "");
					productEntry.setEditKeyword(p.getEditWords());
					productEntry.setSizeContrast(p.getSizeUrl());
					logger.info("尺码图片路径" + p.getSizeUrl());
					
					logger.info("getGlobalFlag--->" + p.getGlobalFlag()+",supplierCode===>"+p.getSupplierCode());
					productEntry.setDescription(p.getDescription());
					// productEntry.setProductTypeName(p.setprd)
					Template template = Velocity.getTemplate("ebay_template.html");
					VelocityContext context = new VelocityContext();
					context.put("product", productEntry);
					StringWriter writer = new StringWriter();
					template.merge(context, writer);
					String content = writer.toString();
					entity.setNotes(content);

					entity.setXiucode(xiucode);
					// 商品所属的品牌
					entity.setBrandcode(p.getBrandCode());
					// entity.setXiu_brand_code(p.getBrandName());
					// 基本分类编码是四级
					entity.setCategoryid(p.getBCatCode());
					entity.setSupplierCode(p.getSupplierCode());
					entity.setNum(batchNum);
					entity.setStatus("0");
					entity.setCid(jdCid);
					if (!"".equals(userName)) {
						entity.setOperatename(userName);
					}

				}

				// 商品总库存量
				int totalStockNum = 0;
				// 获取商品的库存
				Sku[] skus = p.getSkus();
				List<JDSku> jdSkus = new LinkedList<JDSku>();
				if(skus!=null){
					for (Sku sku : skus) {
						if(sku==null){
							logger.info("走秀商品sku为null,走秀码"+xiucode);
							continue;
						}
						String skusn = sku.getSkuSN();
						if (skusn != null) {
							boolean flag = jdSkuDaoBean.xiuSnIsExistsNation(skusn.trim());
							logger.info(flag ? " 导入SKU码为  " + skusn + " 在本地不存在" : "SKU码为  " + skusn + " 在本地存在");
							JDSku jdSku = new JDSku();
							QueryInventoryRequest queryInventoryRequest = new QueryInventoryRequest();
							queryInventoryRequest.setSkuCode(skusn.trim());
							queryInventoryRequest.setChannelCode(GlobalConstants.CHANNEL_ID);
							QueryInventoryResponse qir = inventoryService.inventoryQueryBySkuCodeAndChannelCode(queryInventoryRequest);
	
							if (qir != null && qir.getCode().intValue() == 1) {
								logger.info("----> Code  " + qir.getCode().intValue());
								// 商品sku对应的库存
								int skuStockNum = qir.getQty();
								jdSku.setStocknum(skuStockNum + "");
								totalStockNum = totalStockNum + skuStockNum;
								logger.info("走秀码为" + xiucode + "  SKU为  " + skusn + " 对应的库存为 " + skuStockNum+",现在商品总库存为:"+totalStockNum+",走秀颜色="+sku.getColorValue()+",走秀尺码="+sku.getSizeValue());
							} else {
								// 查库存出错
								logger.error("查询库存出错：" + qir.toString());
								continue;
							}
							// //true:本地不存在,false本地存在
							if (!flag) {
								continue;
							}
							if (skusn.equals(mainSkuSn)) {
								jdSku.setIsmain(1);
							}
							// 设置sku路径
							String skuImagePath = uploadImagePrefix.replaceAll(mainSkuSn, skusn);
							jdSku.setSkuImagePath(skuImagePath);
							Map<String, String> colorMap = new HashMap<String, String>();
							colorMap.put("xiuColor", sku.getColorValue());
							colorMap.put("jdCid", jdCid);
							
							//京东的颜色
							String jdSkuColor =null;
							try {
								//根据走秀的颜色，得到京东的颜色
								jdSkuColor=jdSkuDaoBean.findSkuColor(colorMap);
							} catch (Exception e) {
								 messages.append("走秀码" + xiucode +",sku:"+skusn+",走秀sku颜色"+sku.getColorValue()+ "</br>");
								 logger.error("根据走秀颜色，获取京东颜色,返回多个结果"+e);
								 e.printStackTrace();
							}
							String colorAttr = "";
							String sizeAttr = "";
							// 红色，黄色
							if (jdSkuColor != null && !"".equals(jdSkuColor)) {
								Map<String, Object> colorAttrs = new HashMap<String, Object>();
								colorAttrs.put("jdCid", jdCid);
								colorAttrs.put("attrValue", jdSkuColor);
								//1000000027:1502349308(颜色:红色)         查询京东颜色与颜色值
								try {
									//根据京东的颜色，获取京东的颜色属性对应的值(例如:1000000027:1502349308)
									colorAttr = jdSkuDaoBean.findResultString(colorAttrs);
								} catch (Exception e) {
									 messages.append("走秀码" + xiucode +",sku:"+skusn+",sku颜色"+sku.getColorValue()+ "京东颜色"+jdSkuColor+"</br>");
									 logger.error("根据京东的颜色，获取京东的颜色属性对应的值,返回多个结果"+e);
									 e.printStackTrace();
								}
								jdSku.setColorname(jdSkuColor);
							}
	
							Map<String, String> sizeMap = new HashMap<String, String>();
							sizeMap.put("xiuSize", sku.getSizeValue());
							sizeMap.put("jdCid", jdCid);
							
							//京东sku尺码(XL)
							String jdSkuSize =null;
							try {
								jdSkuSize=jdSkuDaoBean.findSkuSize(sizeMap);
							} catch (Exception e) {
								 messages.append("走秀码" + xiucode +",sku:"+skusn+",走秀sku尺码"+sku.getSizeValue()+ "走秀sku颜色"+sku.getColorValue()+"</br>");
								 logger.error("根据走秀尺码，获取京东尺码,返回多个结果"+e);
								 e.printStackTrace();
							}
							logger.info("商品sku为" + skusn + ",对应京东的颜色为:" + jdSkuColor + ",对应京东的尺寸为:" + jdSkuSize);
							// //M ML S
							// jdSku.setSizevalue(sku.getSizeValue());
							if (jdSkuSize != null && !"".equals(jdSkuSize)) {
								Map<String, Object> sizeAttrs = new HashMap<String, Object>();
								sizeAttrs.put("jdCid", jdCid);
								sizeAttrs.put("attrValue", jdSkuSize);
								//1000000041:1502850426
								try {
									sizeAttr = jdSkuDaoBean.findResultString(sizeAttrs);
								} catch (Exception e) {
									 messages.append("走秀码" + xiucode +",sku:"+skusn+",走秀sku尺码"+sku.getSizeValue()+ "京东尺码"+jdSkuSize+"</br>");
									 logger.error("根据京东的尺码，获取京东的尺码属性对应的值,返回多个结果"+e);
									 e.printStackTrace();
								}
								jdSku.setSizevalue(jdSkuSize);
							}
							// List<String > skuAttribute=new ArrayList<String>();
							StringBuilder skuAttribute = new StringBuilder();
							if (!"".equals(colorAttr)) {
								skuAttribute.append(colorAttr);
								skuAttribute.append("^");
							}
	
							if (!"".equals(sizeAttr)) {
								skuAttribute.append(sizeAttr);
							}
							String tempSkuAttribute = skuAttribute.toString();
							if (tempSkuAttribute.endsWith("^")) {
								skuAttribute.deleteCharAt(skuAttribute.length() - 1);
							}
	
	//						jdSku.setAttributes(skuAttribute.toString());
							jdSku.setSkusn(skusn);
							jdSku.setNum(batchNum);
							logger.info("走秀商品ID =" + p.getInnerID() + "大小   " + skus.length);
							jdSku.setXiucode(xiucode);
							jdSkus.add(jdSku);
	
						}
					}
	
			  }
				// //true:本地不存在,false本地存在
				if (isExists && entity != null) {
					entity.setStocknum(totalStockNum + "");
					jdProducts.add(entity);
				}
				if (jdSkus!=null && jdSkus.size() > 0) {
					jdSkuDaoBean.insertBatch(jdSkus, jdSkus.size());
				}

			}
			if (jdProducts.size() > 0) {
				jDWareDaoBean.insertBatch(jdProducts, jdProducts.size());
			}
			long endTime = System.currentTimeMillis();
			logger.info((endTime - startTime) + " 毫秒");
			if (!"".equals(messages.toString())) {
				return messages.toString();
			} else {
				return "走秀码导入成功";
			}
		}
		return "商品中心没有找到对应的商品";

	}

	@Override
	public JDProduct getProductById(String xiuCode) {

		return jDWareDaoBean.getEntityById(xiuCode);
	}

	@Override
	public boolean xiuCodeIsExistsNation(String xiuCode) {
		// TODO Auto-generated method stub
		return jDWareDaoBean.xiuCodeIsExistsNation(xiuCode);
	}

	@Override
	public QueryResult<JDWareAndSkuInfo> getPageResule(Map<String, Object> parames) {
		QueryResult<JDWareAndSkuInfo> qr = jDWareAndSkuInfoDaoBean.getPageResule(parames);
		return qr;
	}

	@Override
	public JDProduct queryWareInfo(Map<String, Object> parames) {
		return jDWareDaoBean.queryResult(parames);
	}

	@Override
	public JDProduct queryWareInfoAndLockWareObject(Map<String, Object> parames, String userName, String flag) {
		return jDWareDaoBean.queryWareInfoAndLockWareObject(parames, userName, flag);
	}
	@Override
	public JDProduct queryXiuWareInfoAndLockWareObject(Map<String, Object> parames, String userName, String flag) {
		return jDWareDaoBean.queryXiuWareInfoAndLockWareObject(parames, userName, flag);
	}
	
	@Override
	public JDProduct queryEbayWareInfoAndLockWareObject(Map<String, Object> parames, String userName, String flag) {
		return jDWareDaoBean.queryEbayWareInfoAndLockWareObject(parames, userName, flag);
	}

	@Override
	public List<JDSku> querySkuInfo(Map<String, Object> parames) {
		return jdSkuDaoBean.getListResult(parames);
	}
	
	@Override
	public List<JDSku> queryXiuSkuInfo(Map<String, Object> parames) {
		return jdSkuDaoBean.getXiuListResult(parames);
	}
	
	@Override
	public List<JDSku> queryEbaySkuInfo(Map<String, Object> parames) {
		return jdSkuDaoBean.getEbayListResult(parames);
	}

	@Override
	public XOPBaseCategory queryXopCategory(Map<String, Object> parames) {
		return xopBaseCategoryDaoBean.queryResult(parames);
	}
	@Override
	public XOPBaseCategory queryXiuXopCategory(Map<String, Object> parames) {
		return xopBaseCategoryDaoBean.queryXiuResult(parames);
	}
	@Override
	public XOPBaseCategory queryEbayXopCategory(Map<String, Object> parames) {
		return xopBaseCategoryDaoBean.queryEbayResult(parames);
	}

	@Override
	public int updateJDProduct(JDProduct jdProduct) {
		return jDWareDaoBean.update(jdProduct);
	}

	@Override
	public Map<Map<String, String>, Map<String, List<JDAttributeValue>>> queryAttrbuteInfo(Map<String, Object> parames) {
		Map<Map<String, String>, Map<String, List<JDAttributeValue>>> mapAttr = new HashMap<Map<String, String>, Map<String, List<JDAttributeValue>>>();
		//商品属性
		List<JDAttribute> jdAttributeList = jDAttributeDaoBean.getListResult(parames);
		List<String> repeatList = new ArrayList<String>();
		for (JDAttribute jdAttribute : jdAttributeList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("aid", jdAttribute.getAid());
			//走秀分类截取4未了
			map.put("xiuCategoryId", parames.get("xiuCategoryId"));
			map.put("xiucode", parames.get("xiucode"));
			/**
			 * 是否必须,属性名<false,价格>
			 */
			Map<String, String> map2 = new HashMap<String, String>();
			Map<String, List<JDAttributeValue>> map3 = new HashMap<String, List<JDAttributeValue>>();
			if(repeatList.size()==0){
				repeatList.add(jdAttribute.getReq()+jdAttribute.getName());
				map2.put(jdAttribute.getReq(), jdAttribute.getName());
			}else{
				if(repeatList.contains(jdAttribute.getReq()+jdAttribute.getName())){
					map2.put(jdAttribute.getReq(), jdAttribute.getName()+" ");
				}else{
					repeatList.add(jdAttribute.getReq()+jdAttribute.getName());
					map2.put(jdAttribute.getReq(), jdAttribute.getName());
				}
			}
			//商品属性值
			List<JDAttributeValue> jdAttValues = jDAttributeValueDaoBean.getListResult(map);
			map3.put(jdAttribute.getInputType() + "", jdAttValues);
			mapAttr.put(map2, map3);
		}
		return mapAttr;
	}

	@Override
	public String findResultString(Map<String, Object> parames) {
		return jdSkuDaoBean.findResultString(parames);
	}

	@Override
	public String addWare(JDProduct product, List<JDSku> skuInfoList) {
		String result = "";
		String content="";
		try {
			content=jDWareDaoBean.getJdAdContent();
			logger.info("京东广告词"+content);
		} catch (Exception e) {
			logger.info("京东广告词失败"+content);
			e.printStackTrace();
		}
		//更新sku对应的商品京东ID
		List<JDSku> jdSkus=null;
		if (skuInfoList != null && skuInfoList.size() > 0) {
			String cid=product.getCid();
			WareAddRequest wareAddRequest = new WareAddRequest();
			wareAddRequest.setCid(cid);
			wareAddRequest.setTitle(product.getTitle());
			wareAddRequest.setHigh(product.getHigh());
			wareAddRequest.setWide(product.getWide());
			wareAddRequest.setWeight(product.getWeight());
			wareAddRequest.setLength(product.getLenght());
			wareAddRequest.setMarketPrice(product.getMarketprice());
			wareAddRequest.setJdPrice(product.getJdprice());
			wareAddRequest.setNotes(product.getNotes());
			wareAddRequest.setStockNum(product.getStocknum());
			wareAddRequest.setAttributes(product.getAttributes());
			wareAddRequest.setShopCategory(product.getShopCategory());
			wareAddRequest.setCanVAT("true");
			wareAddRequest.setAdContent(content);
			wareAddRequest.setItemNum(product.getItemNum());
			wareAddRequest.setOptionType("offsale");
			wareAddRequest.setService("POP商家售后服务");
			// product.setStatus("0");
			// jDWareDaoBean.update(product);
			StringBuilder skusn = new StringBuilder();
			StringBuilder skuOutId = new StringBuilder();
			StringBuilder skuprice = new StringBuilder();
			StringBuilder skuStocks = new StringBuilder();
			StringBuilder skuProperties = new StringBuilder();
			jdSkus=new ArrayList<JDSku>();
			for (int i = 0; i < skuInfoList.size(); i++) {
				JDSku jdSku=new JDSku();
				if (i == skuInfoList.size() - 1) {
//					if (skuInfoList.get(i).getAttributes() != null && !"".equals(skuInfoList.get(i).getAttributes().trim())) {
//						skuProperties.append(skuInfoList.get(i).getAttributes().trim());
//						skusn.append(skuInfoList.get(i).getSkusn().trim());
//						skuOutId.append(skuInfoList.get(i).getSkusn().trim());
//						skuprice.append(product.getJdprice().trim());
//						skuStocks.append(skuInfoList.get(i).getStocknum().trim());
//					} else {
						Map<String, Object> mapColor = new HashMap<String, Object>();
						mapColor.put("jdCid", cid);
						String colorName=skuInfoList.get(i).getColorname().trim();
						mapColor.put("attrValue", colorName);
					

						String colorAttr = jdSkuDaoBean.findResultString(mapColor);
						logger.info(product.getXiucode()+", mapColor1 " + mapColor.toString() +" ,colorAttr1  " + colorAttr);

						Map<String, Object> mapSize = new HashMap<String, Object>();
						mapSize.put("jdCid", product.getCid());
						String sizeName=skuInfoList.get(i).getSizevalue().trim();
						mapSize.put("attrValue", sizeName);
					
						String sizeAttr = jdSkuDaoBean.findResultString(mapSize);
						logger.info(product.getXiucode()+", mapSize1  " + mapSize.toString() +", sizeAttr1 " + sizeAttr);
						StringBuilder skuAttribute = new StringBuilder();
						if (!"".equals(colorAttr)) {
							skuAttribute.append(colorAttr);
							skuAttribute.append("^");
						}

						if (!"".equals(sizeAttr)) {
							skuAttribute.append(sizeAttr);
						}
						String tempSkuAttribute = skuAttribute.toString();
						if (tempSkuAttribute.endsWith("^")) {
							skuAttribute.deleteCharAt(skuAttribute.length() - 1);
						}
						
						if (!"".equals(skuAttribute.toString())) {
							skuProperties.append(skuAttribute.toString());
							skusn.append(skuInfoList.get(i).getSkusn().trim());
							skuOutId.append(skuInfoList.get(i).getSkusn().trim());
							skuprice.append(product.getJdprice().trim());
							skuStocks.append(skuInfoList.get(i).getStocknum().trim());
						}
						jdSku.setAttributes(skuAttribute.toString());
//					}
				} else {
//					if (skuInfoList.get(i).getAttributes() != null && !"".equals(skuInfoList.get(i).getAttributes().trim())) {
//						skuProperties.append(skuInfoList.get(i).getAttributes().trim()).append("|");
//						skusn.append(skuInfoList.get(i).getSkusn()).append("|");
//						skuOutId.append(skuInfoList.get(i).getSkusn()).append("|");
//						skuprice.append(product.getJdprice()).append("|");
//						skuStocks.append(skuInfoList.get(i).getStocknum()).append("|");
//					} else {
						Map<String, Object> mapColor = new HashMap<String, Object>();
						mapColor.put("jdCid", product.getCid());
						String colorName=skuInfoList.get(i).getColorname().trim();
						mapColor.put("attrValue", colorName);
						String colorAttr = jdSkuDaoBean.findResultString(mapColor);
						logger.info(product.getXiucode()+", mapColor2 " + mapColor.toString()+", colorAttr " + colorAttr);

						Map<String, Object> mapSize = new HashMap<String, Object>();
						mapSize.put("jdCid", product.getCid());
						String sizeName=skuInfoList.get(i).getSizevalue().trim();
						mapSize.put("attrValue", sizeName);
						String sizeAttr = jdSkuDaoBean.findResultString(mapSize);
						logger.info(product.getXiucode()+", mapSize2 " + mapSize.toString() +", sizeAttr2 " + sizeAttr);
						
						StringBuilder skuAttribute = new StringBuilder();
						if (!"".equals(colorAttr)) {
							skuAttribute.append(colorAttr);
							skuAttribute.append("^");
						}

						if (!"".equals(sizeAttr)) {
							skuAttribute.append(sizeAttr);
						}
						String tempSkuAttribute = skuAttribute.toString();
						if (tempSkuAttribute.endsWith("^")) {
							skuAttribute.deleteCharAt(skuAttribute.length() - 1);
						}
						
						if (!"".equals(skuAttribute.toString())) {
							skuProperties.append(skuAttribute.toString()).append("|");
							skusn.append(skuInfoList.get(i).getSkusn().trim()).append("|");
							skuOutId.append(skuInfoList.get(i).getSkusn().trim()).append("|");
							skuprice.append(product.getJdprice().trim()).append("|");
							skuStocks.append(skuInfoList.get(i).getStocknum().trim()).append("|");
						}
						jdSku.setAttributes(skuAttribute.toString());
//					}
				}
				
				jdSku.setSkusn(skuInfoList.get(i).getSkusn().trim());
				
				logger.info(product.getXiucode()+", sku属性 attributes " +jdSku.getAttributes());
				jdSkus.add(jdSku);
			}
			if (!"".equals(skusn.toString())) {
				wareAddRequest.setOuterId(skuOutId.toString());
				wareAddRequest.setSkuPrices(skuprice.toString());
				wareAddRequest.setSkuProperties(skuProperties.toString());
				wareAddRequest.setSkuStocks(skuStocks.toString());
				logger.info(product.getXiucode()+",销售数据为,OuterId " +wareAddRequest.getOuterId()+",SkuPrices "+wareAddRequest.getSkuPrices()+",SkuProperties"+wareAddRequest.getSkuProperties()+",SkuStocks"+wareAddRequest.getSkuStocks());
			} else {
				result = "商品sku属性为空不能推送商品";
				return result;
			}
			try {
				
				wareAddRequest.setWareImage(HttUtils.getResponseData(product.getMainimagepath()));
				WareAddResponse response = client.execute(wareAddRequest);
				//outPutCommInfo(response);
                String skuUpdateMessage="";
				if (response.getCode().equals("0")) {
					String jdWareId=response.getWareId() + "";
					product.setStatus("1");
					product.setJdWareId(jdWareId);
					product.setOnLineStatus(0);
					product.setShopCategory(product.getShopCategory());
					jDWareDaoBean.update(product);
					
					this.addSevenReturn(product, client);
					
					try{
					if(jdSkus!=null && jdSkus.size()>0){
						//更新商品sku对应的京东id
						for(JDSku s:jdSkus){
							s.setJdWareJd(jdWareId);
						}
						jdSkuDaoBean.updateBatch(jdSkus ,jdSkus.size());
						skuUpdateMessage="更新商品sku表的京东ID成功";
						
						
						/*UpdateProductSKUIdThread updateProductSKUIdThread= new UpdateProductSKUIdThread(client,jdSkuDaoBean,jdWareId);
						Thread thread=new Thread(updateProductSKUIdThread);
						thread.start();*/
						JdSkuIdSyn jdSkuIdSyn=new JdSkuIdSyn();
						jdSkuIdSyn.setJdWareId(jdWareId);
						JdSkuIdSyn syn=jdSkuIdSynServiceBean.insertJdSkuIdSynService(jdSkuIdSyn);
						if(syn!=null){
							logger.info("保存成功");
						}
						
					}
					}catch(Exception e){
						logger.error("更新商品sku表的京东ID异常");
						skuUpdateMessage="更新商品sku表的京东ID异常";
					}
				
					result = "走秀码" + product.getXiucode() + ":推送成功," + "商品编码为：" + response.getWareId() + "\n 主图片路径"+product.getMainimagepath()
					+"\n商品sku表更新情况"+skuUpdateMessage;
					
					addTemplate(Long.toString(response.getWareId()),client);
				} else {
					result = "走秀码" + product.getXiucode() + ":推送失败:" + response.getZhDesc() + ",商品的属性为:" + product.getAttributes() +

					" sku属性为:" + skuProperties.toString() + ",主图片路径"+product.getMainimagepath()+"\n京东错误码为"+response.getCode()
					+"\n商品sku表更新情况"+skuUpdateMessage;;
				}
			} catch (Exception e) {
				result = "走秀码" + product.getXiucode() + "推送失败" + e.getStackTrace() + "\n异常信息:\n"+e;
				e.printStackTrace();
			} /*finally {
				logger.info("解锁被锁住的商品");
				jDWareDaoBean.unlock(product.getXiucode());
			}*/
		}

		return result;
	}
     
	@Override
	public String importTemWare(String jdCid, String skuCode, Map<String, List<String>> attrMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("xiucode", skuCode);
		String jdAttributes = "";
		JDProduct product = jDWareDaoBean.queryResult(map);

		for (Entry<String, List<String>> entity : attrMap.entrySet()) {
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("cid", jdCid);
			map1.put("attrName", entity.getKey());

			for (String it : entity.getValue()) {
				map1.put("attrValue", it);
				JDAttributeValue attrValue = jDAttributeValueDaoBean.findJdAttributeValue(map1);
				if (attrValue != null) {
					jdAttributes = attrValue.getAid() + ":" + attrValue.getVid() + "|";
				}
			}
		}

		jdAttributes = jdAttributes.substring(0, jdAttributes.length() - 1);
		String result = "";
		WareAddRequest wareAddRequest = new WareAddRequest();
		wareAddRequest.setCid(jdCid);
		wareAddRequest.setTitle(product.getTitle() + getDateString());
		wareAddRequest.setHigh(product.getHigh() + "1");
		wareAddRequest.setWide(product.getWide() + "1");
		wareAddRequest.setWeight(product.getWeight() + "1");
		wareAddRequest.setLength(product.getLenght() + "1");
		wareAddRequest.setMarketPrice(product.getMarketprice());
		wareAddRequest.setJdPrice(product.getJdprice());
		wareAddRequest.setNotes(product.getNotes() + getDateString());
		wareAddRequest.setWareImage(HttUtils.getResponseData(product.getMainimagepath()));
		wareAddRequest.setStockNum(product.getStocknum());
		wareAddRequest.setAttributes(product.getAttributes());
		wareAddRequest.setOptionType("offsale");
		product.setStatus("0");
		jDWareDaoBean.update(product);

		try {
			WareAddResponse response = client.execute(wareAddRequest);
			outPutCommInfo(response);
			if (response.getCode().equals("0")) {
				product.setStatus("1");
				jDWareDaoBean.update(product);
				result = skuCode + ":操作成功\n";
			} else {
				result = skuCode + ":操作失败:" + response.getZhDesc() + "\n";
			}
		} catch (Exception e) {
			result = skuCode + ":操作失败\n";
			e.printStackTrace();
		} finally {
			logger.info("解锁被锁住的商品");
			//jDWareDaoBean.unlock(product.getXiucode());
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String addJdProduct(HttpServletRequest request, Map<JDProduct, Map<String, List<String>>> jdMap) {
		Iterator it = jdMap.entrySet().iterator();
		List<JDProduct> products = new ArrayList<JDProduct>();
		while (it.hasNext()) {
			Map.Entry<JDProduct, Map<String, List<String>>> entity = (Map.Entry<JDProduct, Map<String, List<String>>>) it.next();
			Map<String, List<String>> attrMap = entity.getValue();
			JDProduct jdp = entity.getKey();
			StringBuilder jdAttributes = new StringBuilder();
			for (Entry<String, List<String>> attrEntity : attrMap.entrySet()) {
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("cid", entity.getKey().getCid());
				map1.put("attrName", attrEntity.getKey());

				for (String value : attrEntity.getValue()) {
					map1.put("attrValue", value);
					JDAttributeValue attrValue = jDAttributeValueDaoBean.findJdAttributeValue(map1);
					if (attrValue != null) {
						jdAttributes.append(attrValue.getAid()).append(":").append(attrValue.getVid()).append("|");
					}
				}
			}
			String attributes = jdAttributes.toString();
			jdp.setAttributes(attributes.substring(0, attributes.length() - 1));
			products.add(jdp);
		}
		StringBuilder goodXiucodes = new StringBuilder();
		String jdCid = "";

		for (int i = 0; i < products.size(); i++) {
			if (i == products.size() - 1) {
				goodXiucodes.append(products.get(i).getXiucode());
				jdCid = products.get(i).getCid();
			} else {
				goodXiucodes.append(products.get(i).getXiucode()).append(",");
			}
		}
		Map<String, String> colorMap = new HashMap<String, String>();
		colorMap.put("attrName", "颜色");
		colorMap.put("cid", jdCid);
		List<String> colorList = jDAttributeValueDaoBean.queryAttrValueList(colorMap);
		Map<String, String> sizeMap = new HashMap<String, String>();
		sizeMap.put("attrName", "尺寸");
		sizeMap.put("cid", jdCid);
		List<String> sizeList = jDAttributeValueDaoBean.queryAttrValueList(sizeMap);
		try {
			getJdProductInfo(request, jdCid, goodXiucodes.toString(), products, colorList, sizeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ProductAttributeInfoModel> findProductAttributeList(List<String> partNumbers) {
		List<ProductAttributeInfoModel> list = jDWareDaoBean.findProductAttributeList(partNumbers);
		if (list != null && list.size() > 0) {
			/**
			 * <productId-attrId,ProductAttributeInfoModel>
			 */
			Map<String, ProductAttributeInfoModel> map = new HashMap<String, ProductAttributeInfoModel>();

			// 遍历商品属性,将相同属性的属性值合并
			for (int i = 0; i < list.size(); i++) {
				ProductAttributeInfoModel attribute = (ProductAttributeInfoModel) list.get(i);
				ProductAttributeInfoModel model = map.get(attribute.getProductId() + "-" + attribute.getAttrId());
				if (model != null) {
					model.setValDesc(model.getValDesc() + "," + attribute.getValDesc());
				} else {
					map.put(attribute.getProductId() + "-" + attribute.getAttrId(), attribute);
				}
			}

			Object[] objects = map.values().toArray();

			List<ProductAttributeInfoModel> attributes = new ArrayList<ProductAttributeInfoModel>();
			for (int j = 0; j < objects.length; j++) {
				attributes.add((ProductAttributeInfoModel) objects[j]);
			}

			return attributes;
		} else {
			return new ArrayList<ProductAttributeInfoModel>();
		}

	}

	@Override
	public int updateJdProductPrice(Map<String, Object> parames) {
		return jDWareDaoBean.updateJdProductPrice(parames);
	}

	public void getJdProductInfo(HttpServletRequest request, String jdCid, String xiucodes, List<JDProduct> lists, List<String> colorList,
			List<String> sizeList) throws Exception {
		List<Product> products = productService.batchLoadProducts(xiucodes);
		List<JDProduct> jdProducts = new LinkedList<JDProduct>();
		List<JDSku> jdSkus = new LinkedList<JDSku>();
		for (Product p : products) {
			String xiucode = p.getXiuSN();
			for (JDProduct jdProduct : lists) {
				if (jdProduct.getXiucode().equals(xiucode)) {
					// 商品官网标准价
					jdProduct.setJdprice(p.getPrdOfferPrice() + "");
					// 商品市场价格
					jdProduct.setMarketprice(p.getPrdListPrice() + "");
					// 商品进货价格(目前市场价和进货价设置为相同)
					jdProduct.setCostprice(p.getPrdListPrice() + "");
					String imageURL = p.getMasterImgUrl();
					// 得到的是100013490001
					String mainSkuSn = imageURL.substring(imageURL.lastIndexOf('/') + 1, imageURL.length());
					// 图片前缀
					String uploadImagePrefix = "";
					if (imageURL.contains("upload")) {

						/*
						 * uploadImagePath =
						 * ParseProperties.getPropertiesValue("IMG_PREFIX") +
						 * "/" + // upload/goods20110924/10001349/100013490001"
						 * imageURL.substring(imageURL.indexOf("upload"),
						 * imageURL.length()) + "/" +
						 * ParseProperties.IMAGE_SIZE;
						 */
						if (imageURL.contains("upload")) {
							uploadImagePrefix = ParseProperties.getPropertiesValue("IMG_PREFIX") + "/"
									+ imageURL.substring(imageURL.indexOf("upload"), imageURL.length());

						}
					}
					jdProduct.setMainimagepath(uploadImagePrefix + "/" + ParseProperties.IMAGE_SIZE);
					jdProduct.setTitle(p.getPrdName());
					ProductEntry productEntry = new ProductEntry();
					// 模板
					List<String> allPartNumbers = new ArrayList<String>();
					allPartNumbers.add(xiucode);
					productEntry.setAttributeList(this.findProductAttributeList(allPartNumbers));
					productEntry.setProductId(p.getInnerID() + "");
					productEntry.setEditKeyword(p.getEditWords());
					productEntry.setSizeContrast(p.getSizeUrl());
					logger.info("尺码图片路径" + p.getSizeUrl());

					productEntry.setDescription(p.getDescription());
					Template template = Velocity.getTemplate("ebay_template.html");
					VelocityContext context = new VelocityContext();
					context.put("product", productEntry);
					StringWriter writer = new StringWriter();
					template.merge(context, writer);
					String content = writer.toString();
					jdProduct.setNotes(content);
					jdProduct.setBrandcode(p.getBrandCode());
					// 基本分类编码是四级
					jdProduct.setCategoryid(p.getBCatCode());
					// 商品总库存量
					int totalStockNum = 0;
					// 获取商品的库存
					Sku[] skus = p.getSkus();
					// List<JDSku> jdSkus = new LinkedList<JDSku>();

					for (Sku sku : skus) {
						JDSku jdSku = new JDSku();
						jdSku.setXiucode(jdProduct.getXiucode());
						String skusn = sku.getSkuSN();
						jdSku.setSkusn(skusn);
						logger.info("走秀商品ID =" + p.getInnerID() + "大小   " + skus.length);
						/*
						 * String pathdir = "/images/" + xiucode + "/" + skusn;
						 * // 得到图片保存目录的真实路径 String realpathdir =
						 * request.getSession
						 * ().getServletContext().getRealPath(pathdir); File
						 * savedir = new File(realpathdir); if
						 * (!savedir.exists()) { savedir.mkdirs(); }//
						 * 如果目录不存在就创建 logger.info("保存800*800的图片真实路径为:" +
						 * savedir.getAbsolutePath());
						 */

						QueryInventoryRequest queryInventoryRequest = new QueryInventoryRequest();
						queryInventoryRequest.setSkuCode(skusn.trim());
						queryInventoryRequest.setChannelCode(GlobalConstants.CHANNEL_ID);
						QueryInventoryResponse qir = inventoryService.inventoryQueryBySkuCodeAndChannelCode(queryInventoryRequest);

						if (qir != null && qir.getCode().intValue() == 1) {
							logger.info("----> Code  " + qir.getCode().intValue());
							// 商品sku对应的库存
							int skuStockNum = qir.getQty();
							jdSku.setStocknum(skuStockNum + "");
							totalStockNum = totalStockNum + skuStockNum;
						} else {
							// 查库存出错
							logger.error("查询库存出错：" + qir.toString());
							continue;

						}

						String skuImagePath = uploadImagePrefix.replaceAll(mainSkuSn, skusn);
						String skuNetWorkPath = skuImagePath.replaceFirst(ParseProperties.getPropertiesValue("IMG_PREFIX"),
								ParseProperties.getPropertiesValue("IMAGE_PREFIX"));
						Thread thread = new Thread(new ImageCompressThread(skuNetWorkPath + "/g1_800_800.jpg"));
						thread.start();
						// logger.info(realpathdir+"\\g1_800_800.jpg");
						// jdSku.setSkuImagePath(realpathdir+"\\g1_800_800.jpg");
						jdSku.setSkuImagePath(skuNetWorkPath + "/g1_800_800.jpg");
						jdSku.setColorname(sku.getColorValue());
						jdSku.setSizevalue(sku.getSizeValue());
						if (qir.getQty() != 0) {// sku库存不为0时插入到List集合中
							jdSkus.add(jdSku);
						}

					}
					jdProduct.setStocknum(totalStockNum + "");
					if (totalStockNum != 0) {
						jdProducts.add(jdProduct);
					}
				}
			}
		}

		jDWareDaoBean.insertBatch(jdProducts, 12);
		HSSFWorkbook wb = new HSSFWorkbook();
		exportTem.createSkuWb(wb, jdCid, jdSkus, colorList, sizeList);
		String fileName = "Template_SKU_" + CommonUtil.getNowTime() + ".xls";
		try {
			ExportExcelUtil.downloadExcel(wb, fileName);
			for (JDSku s : jdSkus) {
				s.setColorname(null);
				s.setSizevalue(null);
			}
			jdSkuDaoBean.insertBatch(jdSkus, 10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据SKU模板推送商品
	 */
	@SuppressWarnings("null")
	@Override
	public String pushWare(String jdCid, List<JDSku> skuList) {
		String result = "";
		List<String> xiuCodeList = new ArrayList<String>();
		for (JDSku jdSku : skuList) {
			if (xiuCodeList == null) {
				xiuCodeList.add(jdSku.getXiucode());
			} else {
				if (!xiuCodeList.contains(jdSku.getXiucode())) {
					xiuCodeList.add(jdSku.getXiucode());
				}
			}
		}

		for (String xiucode : xiuCodeList) {
			Map<String, Object> parames = new HashMap<String, Object>();
			parames.put("xiucode", xiucode);
			JDProduct jdProduct = jDWareDaoBean.queryResult(parames);
			List<JDSku> jdSkuList = new ArrayList<JDSku>();
			for (JDSku jdSku : skuList) {
				if (xiucode.equals(jdSku.getXiucode())) {
					StringBuilder attrSku = new StringBuilder();
					Map<String, String> mapColor = new HashMap<String, String>();
					mapColor.put("cid", jdCid);
					mapColor.put("attrName", jdSku.getColorname());
					String colorAvid = jDAttributeValueDaoBean.queryAidandVid(mapColor);
					Map<String, String> mapSize = new HashMap<String, String>();
					mapSize.put("cid", jdCid);
					mapSize.put("attrName", jdSku.getSizevalue());
					String sizeAvid = jDAttributeValueDaoBean.queryAidandVid(mapSize);
					if (!"".equals(colorAvid) && !"".equals(sizeAvid)) {
						attrSku.append(colorAvid).append("^").append(sizeAvid);
						jdSku.setAttributes(attrSku.toString());
					}
					jdSkuList.add(jdSku);
				}
			}
			jdSkuDaoBean.updateBatch(jdSkuList, 10);
			result = result + isExist(jdProduct, jdSkuList) + "\n";
		}

		return result;
	}

	/**
	 * 判断该商品是否已经在京东存在
	 * 
	 * @param jdProduct
	 * @param jdSkuList
	 * @return
	 */
	@SuppressWarnings("unused")
	public String isExist(JDProduct jdProduct, List<JDSku> jdSkuList) {
		String result = "";
		int page = 1;
		int pageSize = 20;

		WareInfoByInfoRequest wareInfoByInfoRequest = new WareInfoByInfoRequest();
		wareInfoByInfoRequest.setCid(jdProduct.getCid());
		wareInfoByInfoRequest.setPage(page + "");
		wareInfoByInfoRequest.setPageSize(pageSize + "");
		WareInfoByInfoSearchResponse res = null;
		try {
			res = client.execute(wareInfoByInfoRequest);
			if (res != null && res.getCode().equals("0")) {
				int total = res.getTotal();
				int totalPage = 0;
				if (total > 0) {
					totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
				}

				for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
					wareInfoByInfoRequest = new WareInfoByInfoRequest();
					wareInfoByInfoRequest.setPage(currentPage + "");
					// 每页显示的记录数量
					wareInfoByInfoRequest.setPageSize(pageSize + "");
					res = client.execute(wareInfoByInfoRequest);
					if (res != null && res.getCode().equals("0")) {
						List<Ware> lists = res.getWareInfos();
						List<String> wareIds = new ArrayList<String>();
						Map<String, String> maps = new HashMap<String, String>();
						for (Ware ware : lists) {
							wareIds.add(ware.getWareId() + "");
							maps.put(ware.getWareId() + "", ware.getItemNum());
						}
						/*
						 * if (wareIds.contains(jdProduct.getJd_ware_id())) {
						 * result = updateWare(jdProduct, jdSkuList); return
						 * result; }
						 */
						List<JDSku> jdskus = new ArrayList<JDSku>();
						for (Map.Entry<String, String> entry : maps.entrySet()) {
							if (entry.getKey() != null && entry.getKey().equals(jdProduct.getJd_ware_id())) {
								for (JDSku sk : jdSkuList) {
									if (!entry.getValue().contains(sk.getSkusn())) {
										jdskus.add(sk);
									}
								}
							}
						}
						if (jdskus != null) {
							result = updateWare(jdProduct, jdSkuList);
						} else {
							result = "该商品在京东已经存在";
						}
					}
				}
			}
			result = addWare(jdProduct, jdSkuList);

		} catch (Exception e) {
			result = res.getCode() + ":" + res.getZhDesc();
		}
		return result;
	}

	/**
	 * 同步走秀在京东的商品
	 * 
	 * @param jdProduct
	 * @param jdSkuList
	 * @return
	 */
	public String updateWare(JDProduct jdProduct, List<JDSku> jdSkuList) {
		String result = "";
		for (JDSku jdSku : jdSkuList) {
			WareSkuAddRequest wareSkuAddRequest = new WareSkuAddRequest();
			wareSkuAddRequest.setAttributes(jdSku.getAttributes());
			wareSkuAddRequest.setJdPrice(jdProduct.getJdprice());
			wareSkuAddRequest.setStockNum(jdSku.getStocknum());
			wareSkuAddRequest.setWareId(jdSku.getSkusn());
			WareSkuAddResponse res = null;
			try {
				res = client.execute(wareSkuAddRequest);
				if (res != null && res.getCode().equals("0")) {
					result = "SKU" + jdSku.getSkusn() + "推送成功";
				} else {
					result = "SKU" + jdSku.getSkusn() + "推送失败";
				}
			} catch (JdException e) {
				result = "SKU" + jdSku.getSkusn() + "推送失败";
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	public String deleteXiuProductAndJdProduct(String xiuCode, String wareId, int synStatus) {
		
		String message="";
		//删除本地数据库中的表
		if(xiuCode!=null && !"".equals(xiuCode.trim())){
		 List<String> xiucodes=new 	ArrayList<String>();
		 xiucodes.add(xiuCode);
			int count=jDWareDaoBean.deleteWareInfoByXiuCodes(xiucodes);
			if(count>0){
				message="删除商品表jd_product_info成功,";
				logger.info("删除商品表jd_product_info成功"+xiuCode);
				if(wareId!=null && !"".equals(wareId.trim())){
					Map<String, Object> parames=new HashMap<String, Object>();
					parames.put("xiuCodes", xiucodes);
					parames.put("jdWareJd", wareId);
					jdSkuDaoBean.deleteWareSKUByXiuCodesAndWardId(parames);
				}else{
					jdSkuDaoBean.deleteWareSKUByXiuCodes(xiucodes);
				}
				
				//message=message+"删除商品表jd_product_sku_info成功";
			}
			
		}
		
		////要删除京东上的商品(先将商品下架，才能删除商品)
		if(synStatus==1){
			if(wareId!=null && !"".equals(wareId.trim())){
				try {
				WareUpdateDelistingRequest  wareUpdateDelistingRequest=new WareUpdateDelistingRequest();

				wareUpdateDelistingRequest.setWareId(wareId);
                //商品下架
				WareUpdateDelistingResponse res = client.execute(wareUpdateDelistingRequest);
				 if("0".equals(res.getCode())){
					logger.info("商品ID为"+wareId+"的商品,下架成功");
					
				 }else{
					 //商品已经是处于下架的状态
					 if("11201012".equals(res.getCode())){
						 logger.info("商品ID为"+wareId+"商品已经是处于下架的状态,不需要再次下架");
					 }
					 //logger.info("商品ID为"+wareId+"的商品");
				 }
				    WareDeleteRequest  wareDeleteRequest= new WareDeleteRequest();
					wareDeleteRequest.setWareId(wareId);
					WareDeleteResponse response = client.execute(wareDeleteRequest);
					if(response!=null && "0".equals(response.getCode())){
						logger.info("删除京东上的商品成功,商品ID为:"+wareId);
						return message+"  删除京东上的商品成功";
					}
				
				}catch(Exception e){
					logger.error("删除京东上的商品异常,商品ID为:"+wareId);
					e.printStackTrace();
					return "";
				}
			}else{
				logger.info("走秀码为:"+xiuCode+", 商品ID不存在");
			}
		}
		return message;
	}
	
	private void addTemplate(String wareId,JdClient client){
		WareTemplateIdsAndNamesGetRequest getResquest=new WareTemplateIdsAndNamesGetRequest();	
		try {
			WareTemplateIdsAndNamesGetResponse getResponse=client.execute(getResquest);
			List<WareTemplate> list =getResponse.getWareTemplateList();
			list.get(0).getContents();	
			WareTemplateToWaresUpdateRequest updateRequest=new WareTemplateToWaresUpdateRequest();
			updateRequest.setId(Long.toString(list.get(0).getId()));//默认选第一个版式进行关联
			updateRequest.setWareIds(wareId);
			WareTemplateToWaresUpdateResponse updateResponse =client.execute(updateRequest);
			if(Integer.valueOf(updateResponse.getCode())==0){
				logger.info("关联版式成功：关联版式ID:"+Long.toString(list.get(0).getId())+",京东商品id:"+wareId+",关联版式名称："+list.get(0).getName());
			}
			else{
				logger.info("关联版式失败：关联版式ID"+Long.toString(list.get(0).getId())+",京东商品id:"+wareId+"关联版式名称："+list.get(0).getName());
			}
		} catch (JdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("关联版式失败: 京东商品id"+wareId);
		} 
	}

	@Override
	public int deleteWareInfoAndXiuSnByXiuCodes(List<String> xiucodes) {
		jdSkuDaoBean.deleteWareSKUByXiuCodes(xiucodes);
		return jDWareDaoBean.deleteWareInfoByXiuCodes(xiucodes);

	}

	@Override
	public boolean wareIdIsExistsNation(String wareId) {
		
		return jDWareDaoBean.wareIdIsExistsNation(wareId);
	}

	@Override
	public List<JDWareSkuBrand> queryWareSkuBrand(Map<String, Object> maps) {
		return jDWareAndSkuInfoDaoBean.queryWareSkuBrand(maps);
	}

	@Override
	public void createWareSkuBrand(HSSFWorkbook wb, List<JDWareSkuBrand> lists) {
		exportTem.createWareSkuBrand(wb, lists);
	}
	
	@Override
	public int writeDataToExcel(HSSFSheet sheet,int rowIndex,
			List<OnLineBlackProductBean> productBeans) {
		return exportTem.writeDataToExcel(sheet,rowIndex,productBeans);
		
	}

	@Override
	public boolean jdBrandIsExist(String xiucode) {
		return jDWareDaoBean.jdBrandIsExist(xiucode);
	}

	@Override
	public void deleteAllProduct() {
		jDWareDaoBean.deleteAllProduct();

	}
	
	@Override
	public void updateProduct(JDProduct jdProduct) {
		jDWareDaoBean.updateProduct(jdProduct);
		
	}
	@Override
	public List<JDProduct> queryWardIdsByXiuCodes(Map<String, Object> parames) {
		 return jDWareDaoBean.queryWardIdsByXiuCodes(parames);
	}

	@Override
	public List<JDProduct> queryXiuCodesByWardIds(Map<String, Object> parames) {
		return jDWareDaoBean.queryXiuCodesByWardIds(parames);
	}
	@Override
	public String updateWareInfo(JDProduct product, List<JDSku> skuList) {
		logger.info("=============更新商品信息"+product.getJdWareId()+"开始==================");
		String message = "";
		WareUpdateRequest wareUpdateRequest = new WareUpdateRequest();
		wareUpdateRequest.setWareId(product.getJdWareId());
		wareUpdateRequest.setTitle(product.getTitle());
		wareUpdateRequest.setNotes(product.getNotes());
		wareUpdateRequest.setJdPrice(product.getJdprice());
		wareUpdateRequest.setMarketPrice(product.getMarketprice());
		wareUpdateRequest.setShopCategory(product.getShopCategory());
		wareUpdateRequest.setItemNum(product.getItemNum());
		wareUpdateRequest.setAdContent(product.getAdContent());
		wareUpdateRequest.setService("POP商家售后服务");
		logger.info("product.getAttributes"+product.getAttributes());
		try {
			WareUpdateResponse wareUpdateResponse= client.execute(wareUpdateRequest);
			if(wareUpdateResponse != null && "0".equals(wareUpdateResponse.getCode())){
				jDWareDaoBean.updateProduct(product);
				message = "商品ID为"+product.getJdWareId()+"已经更新成功";
				logger.info("商品ID为"+product.getJdWareId()+"商品名称为"+product.getTitle()+"已经更新成功");
			}else{
				logger.info("商品ID为"+product.getJdWareId()+"商品名称为"+product.getTitle()+"已经更新失败");
				message = "商品ID为"+product.getJdWareId()+"已经更新失败";
			}
		} catch (JdException e) {
			message = "商品ID为"+product.getJdWareId()+"已经更新异常";
			logger.info("异常:"+e);
			e.printStackTrace();
		} finally {
			logger.info("解锁被锁住的商品");
			//jDWareDaoBean.unlock(product.getXiucode());
		}
		logger.info("=============更新商品信息"+product.getJdWareId()+"结束==================");
		return message;
	}
	@Override
	public int updateProductOnline(Map<String, Object> productParmes) {
		
		return jDWareDaoBean.updateProductOnline(productParmes);
	}

	@Override
	public String getJdProductMainPic(String jdWareId) {
		return jDWareDaoBean.getJdProductMainPic(jdWareId);
	}

	@Override
	public void updateJdProductBatch(List<JDProduct> jdProductList, int size) {
		jDWareDaoBean.updateJdProductBatch(jdProductList,size);
	}

	@Override
	public int deleteNationProductByWareId(String wareId) {
	   return jDWareDaoBean.deleteNationProductByWareId(wareId);
	}

	@Override
	public void updateProductOnlineBatch( final List<JDProduct> jdProducts,  final int batchSize) {
		 jDWareDaoBean.updateProductOnlineBatch(jdProducts,batchSize);
	}

	@Override
	public JDProduct getJdProductByWareId(String wareId) throws Exception{
		return jDWareDaoBean.getJdProductByWareId(wareId);
	}

	@Override
	public QueryResult<JDWareSkuBrand> queryWareSkuBrandPage(Map<String, Object> parames) {
		QueryResult<JDWareSkuBrand> qr=new QueryResult<JDWareSkuBrand>();
		List<JDWareSkuBrand> resultlist =jDWareDaoBean.queryWareSkuBrandPage(parames);
		qr.setResultlist(resultlist);
		long totalrecord=jDWareDaoBean.queryWareSkuBrandCount(parames);
		qr.setTotalrecord(totalrecord);
		return qr;
	}

	@Override
	public void createWareSkuBrandPage(HSSFWorkbook wb,
			List<JDWareSkuBrand> lists, int sheetIndex, int total) {
		exportTem.createWareSkuBrandPage(wb, lists, sheetIndex, total);
		
	}

	@Override
	public JDProduct getProductByXiuCode(String xiuCode) {
		return this.jDWareDaoBean.getProductByXiuCode(xiuCode);
	}
	
	@Override
	public JDProduct getXiuProductByXiuCode(String xiuCode) {
		return this.jDWareDaoBean.getXiuProductByXiuCode(xiuCode);
	}
	
	@Override
	public JDProduct getEbayProductByXiuCode(String xiuCode) {
		return this.jDWareDaoBean.getEbayProductByXiuCode(xiuCode);
	}

	@Override
	public JDProduct queryWareInfoAndLockWareObjects(
			Map<String, Object> parames, String userName, String flag) {
	
		List<JDProduct> jdProducts=jDWareDaoBean.queryWareInfoAndLockWareObjects(parames,userName,flag);
		if(jdProducts!=null && jdProducts.size()>0){
			return jdProducts.get(0);
		}
		return null;
	}

	@Override
	public String getProductTotalStockNum(String xiuCode) {
		return jDWareDaoBean.getProductTotalStockNum(xiuCode);
	}

	@Override
	public String getProductTotalStockNumByWareId(String wareId) {
		return jDWareDaoBean.getProductTotalStockNumByWareId(wareId);
	}
	
	@Override
	public String queryAttrbutes(Map<String, Object> parames,JDProduct product,String shopType) {
		
		String attrbutesString="";
		try {
			StringBuilder sb = new StringBuilder();
			//商品属性
			List<JDAttribute> jdAttributeList = new ArrayList<JDAttribute>();
			if (GlobalConstants.SHOP_TYEP_EBAY.equals(shopType)) {
				jdAttributeList = jDAttributeDaoBean.getEbayListResult(parames);
			} else if (GlobalConstants.SHOP_TYEP_XIU.equals(shopType)) {
				jdAttributeList = jDAttributeDaoBean.getXiuListResult(parames);
			} else {
				jdAttributeList = jDAttributeDaoBean.getListResult(parames);
			}
			for (JDAttribute jdAttribute : jdAttributeList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("aid", jdAttribute.getAid());
				map.put("xiuCategoryId", parames.get("xiuCategoryId"));
				map.put("xiucode", parames.get("xiucode"));

				List<JDAttributeValue> jdAttValues = null;
			
				if (GlobalConstants.SHOP_TYEP_EBAY.equals(shopType)) {
					jdAttValues = jDAttributeValueDaoBean
							.getEbayListResult(map);
				} else if (GlobalConstants.SHOP_TYEP_XIU.equals(shopType)) {
					jdAttValues = jDAttributeValueDaoBean.getXiuListResult(map);
				} else {
					jdAttValues = jDAttributeValueDaoBean.getListResult(map);
				}

				Boolean attributeBool = false;
				Boolean attributeValueBool = false;
				attributeBool = Boolean.valueOf(jdAttribute.getReq());
				String attributeString = "";
				String attributeName = jdAttribute.getName();
                String[] priorAttr="线上销售,青年,休闲,2016,时尚,通勤,其它,其他".split(",");
				if (!attributeName.equals("价格") && !attributeName.equals("品牌")) {
					for (int i = 0; i < jdAttValues.size(); i++) {
						Long vid = jdAttValues.get(i).getVid();
						String identify = jdAttValues.get(i).getIdentify();
						if (vid.equals(identify)) {
							attributeString = jdAttValues.get(i).getAid() + ":"
									+ vid + "|";
							attributeValueBool = true;
							break;
						}
					}
					if (!attributeValueBool && attributeBool) {//有其他、其他优先选
						Boolean defaltBool = false;
						for (JDAttributeValue jdAttributeValue : jdAttValues) {
							String name = jdAttributeValue.getName();
  						    Long vid = jdAttributeValue.getVid();
							for (String string : priorAttr) {
								if(name.indexOf(string)>-1){
									attributeString = jdAttValues.get(0).getAid()
										+ ":" + vid + "|";
								    defaltBool = true;
									break;									
								}
							}
							if(defaltBool){
								break;
							}
						}
						if (!defaltBool) {
							Long vid = jdAttValues.get(0).getVid();
							attributeString = jdAttValues.get(0).getAid() + ":"
									+ vid + "|";
							defaltBool = true;
						}
					}

				}
				if (attributeName.equals("价格")) {//根据价格区间匹配
					try {
						JDAttributeValue jdAttributePriceValue = this.getPrice(
								jdAttValues, (Double.valueOf(product
										.getJdprice())).intValue());
						attributeString = jdAttributePriceValue.getAid() + ":"
								+ jdAttributePriceValue.getVid() + "|";
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("店铺类型：" + shopType + "走秀码:"
								+ product.getXiucode() + "获取价格失败");
					}
				}

				sb.append(attributeString);

			}
			String brandString = "";
			if (!sb.toString().equals("")) {

				if (product.getJdBrandVid() == null
						|| product.getJdBrandVid().equals("")) {
					Map<String, String> attrMap = new HashMap<String, String>();
					attrMap.put("cid", parames.get("xiuCategoryId").toString());
					attrMap.put("name", "品牌");
					attrMap.put("attrValue", "其他");
					JDAttributeValue jdAttributeValue = jDAttributeValueDaoBean
							.findJdAttributeValue(attrMap);
					if (jdAttributeValue.getVid() != null
							&& !jdAttributeValue.equals("")) {
						brandString = jdAttributeValue.getAid() + ":"
								+ jdAttributeValue.getVid() + "|";
					} else {
						attrMap.put("attrValue", "其它");
						JDAttributeValue jdAttributeValue2 = jDAttributeValueDaoBean
								.findJdAttributeValue(attrMap);
						if (jdAttributeValue2.getVid() != null
								&& !jdAttributeValue2.equals("")) {
							brandString = jdAttributeValue2.getAid() + ":"
									+ jdAttributeValue2.getVid() + "|";
						}
					}
				} else {
					brandString = product.getJdBrandAid() + ":"
							+ product.getJdBrandVid() + "|";
				}
			}
			sb.append(brandString);
			attrbutesString = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attrbutesString;
		
	}


	/**
	 *  3000-4999
		5000以上
		1-999
		1000-1999
		2000-2999
	 * @param jdAttValues
	 * @param jdPrice
	 * @return
	 */
	private JDAttributeValue  getPrice(List<JDAttributeValue> jdAttValues,int jdPrice){
		JDAttributeValue jdAttribute =new JDAttributeValue();
		for (JDAttributeValue jdAttributeValue : jdAttValues) {
			String reg = "[\u4e00-\u9fa5]";//去除中文
		    Pattern pat = Pattern.compile(reg);  
			String attributeName=jdAttributeValue.getName();
			if(attributeName!=null&&!jdAttributeValue.equals("")){
				Matcher mat=pat.matcher(attributeName); 
				String repAttributeName = mat.replaceAll("");
				if(repAttributeName.indexOf("-")>0){
					String[] numValue =repAttributeName.split("-");
					int minValue =Integer.valueOf(numValue[0].trim());
					int maxValue = Integer.valueOf(numValue[1].trim());
					//alert("minValue="+minValue+",maxValue="+maxValue+ ",jdPrice="+jdPrice);
					if(minValue<jdPrice && jdPrice<maxValue){
						jdAttribute=jdAttributeValue;
						break;
					}
				}else if(Integer.valueOf(repAttributeName) <=jdPrice){
					jdAttribute=jdAttributeValue;
					break;
				}else if (Integer.valueOf(repAttributeName) >=jdPrice){
					jdAttribute=jdAttributeValue;
					break;
				}
			}
		}
		
		return jdAttribute;
	}	
	
	@Override
	public String addWareInBatch(JDProduct product, List<JDSku> skuInfoList,
			String isStatus, String attrbute, String shopType) {
		String content="";
		try {
			content=jDWareDaoBean.getJdAdContent();
			logger.info("京东广告词"+content);
		} catch (Exception e) {
			logger.info("京东广告词失败"+content);
			e.printStackTrace();
		}
		String result = "";
		String note=product.getNotes();
		List<String> sensitiveList=jdSensitiveWord;;
		
		String title=product.getTitle();
		
		if (sensitiveList.size()>0) {
			for (String string : sensitiveList) {
				if (string != null && !"".equals(string)) {
					note = note.replace(string, "");
					title = title.replace(string, "");
				}
			}
		}
		
		Pattern pattern = Pattern.compile("\\【(.*?)\\】");
        Matcher matcher = pattern.matcher(title);       
        while(matcher.find()){
        	title=title.replace(matcher.group(), "");             
        }
        product.setTitle(title);
		product.setNotes(note);
		//更新sku对应的商品京东ID
		List<JDSku> jdSkus=null;
		if (skuInfoList != null && skuInfoList.size() > 0) {
			WareAddRequest wareAddRequest = new WareAddRequest();
			wareAddRequest.setCid(product.getCid());
			wareAddRequest.setTitle(title);
			wareAddRequest.setHigh(product.getHigh());
			wareAddRequest.setWide(product.getWide());
			wareAddRequest.setWeight(product.getWeight());
			wareAddRequest.setLength(product.getLenght());
			wareAddRequest.setMarketPrice(product.getMarketprice());
			wareAddRequest.setJdPrice(product.getJdprice());
			wareAddRequest.setNotes(note);
			wareAddRequest.setWareImage(HttUtils.getResponseData(product.getMainimagepath()));
			wareAddRequest.setStockNum(product.getStocknum());
			wareAddRequest.setAttributes(attrbute);
			wareAddRequest.setShopCategory(product.getShopCategory());
			wareAddRequest.setCanVAT("true");
			wareAddRequest.setOptionType("offsale");
			wareAddRequest.setAdContent(product.getAdContent());
			String itemNum=product.getItemNum();
			if (itemNum==null||itemNum.equals("")) {
				itemNum="N";
			}
			wareAddRequest.setItemNum(itemNum);
			wareAddRequest.setService("POP商家售后服务");
			wareAddRequest.setAdContent(content);
			// product.setStatus("0");
			// jDWareDaoBean.update(product);
			StringBuilder skusn = new StringBuilder();
			StringBuilder skuOutId = new StringBuilder();
			StringBuilder skuprice = new StringBuilder();
			StringBuilder skuStocks = new StringBuilder();
			StringBuilder skuProperties = new StringBuilder();
			jdSkus=new ArrayList<JDSku>();
			for (int i = 0; i < skuInfoList.size(); i++) {
				JDSku jdSku=new JDSku();
				JDSku ebarjdSku=new JDSku();
				if (i == skuInfoList.size() - 1) {
//					if (skuInfoList.get(i).getAttributes() != null && !"".equals(skuInfoList.get(i).getAttributes().trim())) {
//						skuProperties.append(skuInfoList.get(i).getAttributes().trim());
//						skusn.append(skuInfoList.get(i).getSkusn().trim());
//						skuOutId.append(skuInfoList.get(i).getSkusn().trim());
//						skuprice.append(product.getJdprice().trim());
//						skuStocks.append(skuInfoList.get(i).getStocknum().trim());
//					} else {
						Map<String, Object> mapColor = new HashMap<String, Object>();
						mapColor.put("jdCid", product.getCid());
						mapColor.put("attrValue", skuInfoList.get(i).getColorname().trim());
						logger.info("mapColor1 " + mapColor.toString());

						String colorAttr = jdSkuDaoBean.findResultString(mapColor);
						//String ebayjdSkus =jd
						logger.info("colorAttr1  " + colorAttr);

						Map<String, Object> mapSize = new HashMap<String, Object>();
						mapSize.put("jdCid", product.getCid());
						String size1=skuInfoList.get(i).getSizevalue();
						if(size1==null||size1.equals("")){
							size1="F";
						}
						mapSize.put("attrValue", size1);
						logger.info("mapSize1  " + mapSize.toString());
						String sizeAttr = jdSkuDaoBean.findResultString(mapSize);
						
						logger.info("sizeAttr1 " + sizeAttr);
						
						StringBuilder skuAttribute = new StringBuilder();
						if (!"".equals(colorAttr)) {
							skuAttribute.append(colorAttr);
							skuAttribute.append("^");
						}

						if (!"".equals(sizeAttr)) {
							skuAttribute.append(sizeAttr);
						}
						String tempSkuAttribute = skuAttribute.toString();
						if (tempSkuAttribute.endsWith("^")) {
							skuAttribute.deleteCharAt(skuAttribute.length() - 1);
						}
						
						if (!"".equals(skuAttribute.toString())) {
							skuProperties.append(skuAttribute.toString());
							skusn.append(skuInfoList.get(i).getSkusn().trim());
							skuOutId.append(skuInfoList.get(i).getSkusn().trim());
							skuprice.append(product.getJdprice().trim());
							skuStocks.append(skuInfoList.get(i).getStocknum().trim());
						}
						jdSku.setAttributes(skuAttribute.toString());
//					}
				} else {
//					if (skuInfoList.get(i).getAttributes() != null && !"".equals(skuInfoList.get(i).getAttributes().trim())) {
//						skuProperties.append(skuInfoList.get(i).getAttributes().trim()).append("|");
//						skusn.append(skuInfoList.get(i).getSkusn()).append("|");
//						skuOutId.append(skuInfoList.get(i).getSkusn()).append("|");
//						skuprice.append(product.getJdprice()).append("|");
//						skuStocks.append(skuInfoList.get(i).getStocknum()).append("|");
//					} else {
						Map<String, Object> mapColor = new HashMap<String, Object>();
						mapColor.put("jdCid", product.getCid());
						mapColor.put("attrValue", skuInfoList.get(i).getColorname().trim());
						logger.info("mapColor2 " + mapColor.toString());
						String colorAttr = jdSkuDaoBean.findResultString(mapColor);
						logger.info("colorAttr " + colorAttr);

						Map<String, Object> mapSize = new HashMap<String, Object>();
						mapSize.put("jdCid", product.getCid());
						String  size=skuInfoList.get(i).getSizevalue();
						if(size==null||size.equals("")){
							size="F";
						}
						mapSize.put("attrValue", size.trim());
						logger.info("mapSize2 " + mapSize.toString());
						String sizeAttr = jdSkuDaoBean.findResultString(mapSize);
						logger.info("sizeAttr2 " + sizeAttr);
						
						StringBuilder skuAttribute = new StringBuilder();
						if (!"".equals(colorAttr)) {
							skuAttribute.append(colorAttr);
							skuAttribute.append("^");
						}

						if (!"".equals(sizeAttr)) {
							skuAttribute.append(sizeAttr);
						}
						String tempSkuAttribute = skuAttribute.toString();
						if (tempSkuAttribute.endsWith("^")) {
							skuAttribute.deleteCharAt(skuAttribute.length() - 1);
						}
						
						if (!"".equals(skuAttribute.toString())) {
							skuProperties.append(skuAttribute.toString()).append("|");
							skusn.append(skuInfoList.get(i).getSkusn().trim()).append("|");
							skuOutId.append(skuInfoList.get(i).getSkusn().trim()).append("|");
							skuprice.append(product.getJdprice().trim()).append("|");
							skuStocks.append(skuInfoList.get(i).getStocknum().trim()).append("|");
						}
						jdSku.setAttributes(skuAttribute.toString());
//					}
				}
				
				jdSku.setSkusn(skuInfoList.get(i).getSkusn().trim());
				jdSkus.add(jdSku);
			}
			if (!"".equals(skusn.toString())) {
				wareAddRequest.setOuterId(skuOutId.toString());
				wareAddRequest.setSkuPrices(skuprice.toString());
				wareAddRequest.setSkuProperties(skuProperties.toString());
				wareAddRequest.setSkuStocks(skuStocks.toString());
			} else {
				result = "商品sku属性为空不能推送商品";
				return result;
			}
			
			try {
				WareAddResponse response = client.execute(wareAddRequest);
                String skuUpdateMessage="";
				if (response.getCode().equals("0")) {
					String jdWareId=response.getWareId() + "";
					product.setStatus("1");
					product.setJdWareId(jdWareId);
					product.setOnLineStatus(0);
					product.setShopCategory(product.getShopCategory());
					jDWareDaoBean.update(product);
					
					this.addSevenReturn(product, client);
					try{
					if(jdSkus!=null && jdSkus.size()>0){
						//更新商品sku对应的京东id
						for(JDSku s:jdSkus){
							s.setJdWareJd(jdWareId);
						}
						jdSkuDaoBean.updateBatch(jdSkus ,jdSkus.size());
						
						skuUpdateMessage="更新商品sku表的京东ID成功";
						
						
						/*UpdateProductSKUIdThread updateProductSKUIdThread= new UpdateProductSKUIdThread(client,jdSkuDaoBean,jdWareId);
						Thread thread=new Thread(updateProductSKUIdThread);
						thread.start();*/
						JdSkuIdSyn jdSkuIdSyn=new JdSkuIdSyn();
						jdSkuIdSyn.setJdWareId(jdWareId);
						JdSkuIdSyn syn=jdSkuIdSynServiceBean.insertJdSkuIdSynService(jdSkuIdSyn);
						if(syn!=null){
							logger.info("保存成功");
						}
						
					}
					}catch(Exception e){
						logger.error("更新商品sku表的京东ID异常");
						skuUpdateMessage="更新商品sku表的京东ID异常";
					}
				
					result += "走秀码" + product.getXiucode() + ":推送成功," + "商品编码为：" + response.getWareId() + "\n 主图片路径"+product.getMainimagepath()
					+"\n商品sku表更新情况"+skuUpdateMessage;
					addTemplate(Long.toString(response.getWareId()),client);
				} else {
					result += "走秀码" + product.getXiucode() + ":推送失败:" + response.getZhDesc() + ",商品的属性为:" + product.getAttributes() +

					" sku属性为:" + skuProperties.toString() + ",主图片路径"+product.getMainimagepath()+"\n京东错误码为"+response.getCode()
					+"\n商品sku表更新情况"+skuUpdateMessage;
					JDProduct productFail=new JDProduct();
					productFail.setStatus("2");
					productFail.setXiucode(product.getXiucode());
					jDWareDaoBean.update(productFail);
				}
			} catch (Exception e) {
				result += "走秀码" + product.getXiucode() + "推送失败" + e.getStackTrace() + "\n异常信息:\n"+e;
				e.printStackTrace();
			} finally {
				logger.info("解锁被锁住的商品");
				jDWareDaoBean.unlock(product.getXiucode());
			}
		}

		return result;
	}
	
	@Override
	public String addXiuWareInBatch(JDProduct product,
			List<JDSku> skuInfoList, String isStatus, String attrbute,
			String shopType) {
		String result = "";
		String note=product.getNotes();
		String title=product.getTitle();
		List<String> sensitiveList=jdSensitiveWord;
		if (jdSensitiveWord.size()>0) {
			for (String string : sensitiveList) {
				if (string != null && !"".equals(string)) {
					note = note.replace(string, "");
					title = title.replace(string, "");
				}
			}
		}
		Pattern pattern = Pattern.compile("\\【(.*?)\\】");
        Matcher matcher = pattern.matcher(title);       
        while(matcher.find()){
        	title=title.replace(matcher.group(), "");             
        }
        String content="";
		try {
			content=jDWareDaoBean.getJdAdContent();
			logger.info("京东广告词"+content);
		} catch (Exception e) {
			logger.info("京东广告词失败"+content);
			e.printStackTrace();
		}
		//更新sku对应的商品京东ID
		List<JDSku> jdSkus=null;
		if (skuInfoList != null && skuInfoList.size() > 0) {
			WareAddRequest wareAddRequest = new WareAddRequest();
			wareAddRequest.setCid(product.getCid());
			wareAddRequest.setTitle(title);
			wareAddRequest.setHigh(product.getHigh());
			wareAddRequest.setWide(product.getWide());
			wareAddRequest.setWeight(product.getWeight());
			wareAddRequest.setLength(product.getLenght());
			wareAddRequest.setMarketPrice(product.getMarketprice());
			wareAddRequest.setJdPrice(product.getJdprice());
			wareAddRequest.setNotes(note);
			wareAddRequest.setWareImage(HttUtils.getResponseData(product.getMainimagepath()));
			wareAddRequest.setStockNum(product.getStocknum());
			wareAddRequest.setAttributes(attrbute);
			wareAddRequest.setShopCategory(product.getShopCategory());
			wareAddRequest.setCanVAT("true");
			wareAddRequest.setOptionType("offsale");
			wareAddRequest.setAdContent(product.getAdContent());
			String itemNum=product.getItemNum();
			if (itemNum==null||itemNum.equals("")) {
				itemNum="N";
			}
			wareAddRequest.setItemNum(itemNum);
			wareAddRequest.setService("POP商家售后服务");
			wareAddRequest.setAdContent(content);
			// product.setStatus("0");
			// jDWareDaoBean.update(product);
			StringBuilder skusn = new StringBuilder();
			StringBuilder skuOutId = new StringBuilder();
			StringBuilder skuprice = new StringBuilder();
			StringBuilder skuStocks = new StringBuilder();
			StringBuilder skuProperties = new StringBuilder();
			jdSkus=new ArrayList<JDSku>();
			for (int i = 0; i < skuInfoList.size(); i++) {
				JDSku jdSku=new JDSku();
				JDSku ebarjdSku=new JDSku();
				if (i == skuInfoList.size() - 1) {
//					if (skuInfoList.get(i).getAttributes() != null && !"".equals(skuInfoList.get(i).getAttributes().trim())) {
//						skuProperties.append(skuInfoList.get(i).getAttributes().trim());
//						skusn.append(skuInfoList.get(i).getSkusn().trim());
//						skuOutId.append(skuInfoList.get(i).getSkusn().trim());
//						skuprice.append(product.getJdprice().trim());
//						skuStocks.append(skuInfoList.get(i).getStocknum().trim());
//					} else {
						Map<String, Object> mapColor = new HashMap<String, Object>();
						mapColor.put("jdCid", product.getCid());
						mapColor.put("attrValue", skuInfoList.get(i).getColorname().trim());
						logger.info("mapColor1 " + mapColor.toString());

						String colorAttr = jdSkuDaoBean.findXiuResultString(mapColor);
						//String ebaycolorAttr = jdSkuDaoBean.findResultString(mapColor);
						//String ebayjdSkus =jd
						logger.info("colorAttr1  " + colorAttr);

						Map<String, Object> mapSize = new HashMap<String, Object>();
						mapSize.put("jdCid", product.getCid());
						String size1=skuInfoList.get(i).getSizevalue();
						if(size1==null||size1.equals("")){
							size1="F";
						}
						mapSize.put("attrValue", size1);
						logger.info("mapSize1  " + mapSize.toString());
						String sizeAttr = jdSkuDaoBean.findXiuResultString(mapSize);
						
						logger.info("sizeAttr1 " + sizeAttr);
						
						StringBuilder skuAttribute = new StringBuilder();
						if (!"".equals(colorAttr)) {
							skuAttribute.append(colorAttr);
							skuAttribute.append("^");
						}

						if (!"".equals(sizeAttr)) {
							skuAttribute.append(sizeAttr);
						}
						String tempSkuAttribute = skuAttribute.toString();
						if (tempSkuAttribute.endsWith("^")) {
							skuAttribute.deleteCharAt(skuAttribute.length() - 1);
						}
						
						if (!"".equals(skuAttribute.toString())) {
							skuProperties.append(skuAttribute.toString());
							skusn.append(skuInfoList.get(i).getSkusn().trim());
							skuOutId.append(skuInfoList.get(i).getSkusn().trim());
							skuprice.append(product.getJdprice().trim());
							skuStocks.append(skuInfoList.get(i).getStocknum().trim());
						}
						jdSku.setAttributes(skuAttribute.toString());
				} else {
						Map<String, Object> mapColor = new HashMap<String, Object>();
						mapColor.put("jdCid", product.getCid());
						mapColor.put("attrValue", skuInfoList.get(i).getColorname().trim());
						logger.info("mapColor2 " + mapColor.toString());
						String colorAttr = jdSkuDaoBean.findXiuResultString(mapColor);
						logger.info("colorAttr " + colorAttr);

						Map<String, Object> mapSize = new HashMap<String, Object>();
						mapSize.put("jdCid", product.getCid());
						String  size=skuInfoList.get(i).getSizevalue();
						if(size==null||size.equals("")){
							size="F";
						}
						mapSize.put("attrValue", size.trim());
						logger.info("mapSize2 " + mapSize.toString());
						String sizeAttr = jdSkuDaoBean.findXiuResultString(mapSize);
						logger.info("sizeAttr2 " + sizeAttr);
						
						StringBuilder skuAttribute = new StringBuilder();
						if (!"".equals(colorAttr)) {
							skuAttribute.append(colorAttr);
							skuAttribute.append("^");
						}

						if (!"".equals(sizeAttr)) {
							skuAttribute.append(sizeAttr);
						}
						String tempSkuAttribute = skuAttribute.toString();
						if (tempSkuAttribute.endsWith("^")) {
							skuAttribute.deleteCharAt(skuAttribute.length() - 1);
						}
						
						if (!"".equals(skuAttribute.toString())) {
							skuProperties.append(skuAttribute.toString()).append("|");
							skusn.append(skuInfoList.get(i).getSkusn().trim()).append("|");
							skuOutId.append(skuInfoList.get(i).getSkusn().trim()).append("|");
							skuprice.append(product.getJdprice().trim()).append("|");
							skuStocks.append(skuInfoList.get(i).getStocknum().trim()).append("|");
						}
						jdSku.setAttributes(skuAttribute.toString());
//					}
				}
				
				jdSku.setSkusn(skuInfoList.get(i).getSkusn().trim());
				jdSkus.add(jdSku);
			}
			if (!"".equals(skusn.toString())) {
				wareAddRequest.setOuterId(skuOutId.toString());
				wareAddRequest.setSkuPrices(skuprice.toString());
				wareAddRequest.setSkuProperties(skuProperties.toString());
				wareAddRequest.setSkuStocks(skuStocks.toString());
			} else {
				result = "商品sku属性为空不能推送商品";
				return result;
			}
			
			try {
				WareAddResponse response = clientXiu.execute(wareAddRequest);
				//outPutCommInfo(response);
                String skuUpdateMessage="";
				if (response.getCode().equals("0")) {
					String jdWareId=response.getWareId() + "";
					product.setStatus("1");
					product.setJdWareId(jdWareId);
					product.setOnLineStatus(0);
					product.setShopCategory(product.getShopCategory());
					//jDWareDaoBean.update(product);
					
					jDWareDaoBean.updateXiu(product);
					this.addSevenReturn(product, client);
					
					
					try{
					if(jdSkus!=null && jdSkus.size()>0){
						//更新商品sku对应的京东id
						for(JDSku s:jdSkus){
							s.setJdWareJd(jdWareId);
						}
						jdSkuDaoBean.updateXiuBatch(jdSkus ,jdSkus.size());
					
						skuUpdateMessage="更新商品sku表的京东ID成功";
						
						
						/*UpdateProductSKUIdThread updateProductSKUIdThread= new UpdateProductSKUIdThread(client,jdSkuDaoBean,jdWareId);
						Thread thread=new Thread(updateProductSKUIdThread);
						thread.start();*/
						JdSkuIdSyn jdSkuIdSyn=new JdSkuIdSyn();
						jdSkuIdSyn.setJdWareId(jdWareId);
						
						
						JdSkuIdSyn syn=jdSkuIdSynServiceBean.insertXiuJdSkuIdSynService(jdSkuIdSyn);
						
						
						if(syn!=null){
							logger.info("保存成功");
						}
						
					}
					}catch(Exception e){
						logger.error("更新商品sku表的京东ID异常");
						 e.printStackTrace();
						skuUpdateMessage="更新商品sku表的京东ID异常";
					}
				
					result += "走秀码" + product.getXiucode() + ":推送成功," + "商品编码为：" + response.getWareId() + "\n 主图片路径"+product.getMainimagepath()
					+"\n商品sku表更新情况"+skuUpdateMessage;
					addTemplate(Long.toString(response.getWareId()),clientXiu);
				} else {
					result += "走秀码" + product.getXiucode() + ":推送失败:" + response.getZhDesc() + ",商品的属性为:" + product.getAttributes() +

					" sku属性为:" + skuProperties.toString() + ",主图片路径"+product.getMainimagepath()+"\n京东错误码为"+response.getCode()
					+"\n商品sku表更新情况"+skuUpdateMessage;
					JDProduct productFail =new JDProduct();
					productFail.setXiucode(product.getXiucode());
					productFail.setStatus("2");	
					jDWareDaoBean.updateXiu(productFail);
				}
			} catch (Exception e) {
				result += "走秀码" + product.getXiucode() + "推送失败" + e.getStackTrace() + "\n异常信息:\n"+e;
				e.printStackTrace();
			} finally {
				logger.info("解锁被锁住的商品");
				//jDWareDaoBean.unlockXiu(product.getXiucode());
			}
		}

		return result;
	}

	
	private void addSevenReturn(JDProduct jdProduct,JdClient jdClient){
		String globalFlag=jdProduct.getGlobalFlag();
		String jdWareId=jdProduct.getJdWareId();
		String featureValue="";
		String xiuCode=jdProduct.getXiucode();
		try {
			logger.info("添加商品七天无理由退换货 wareId:"+jdWareId+" xiuCode:"+xiuCode+"globalFlag:"+globalFlag);
			if("2".equals(globalFlag)||"3".equals(globalFlag)||"5".equals(globalFlag)){
				featureValue="0";
			}
			if("0".equals(featureValue)){
			    WareWriteMergeWareFeaturesRequest request=new WareWriteMergeWareFeaturesRequest();		
				request.setWareId(Long.valueOf(jdWareId));
				request.setFeatureKey( "is7ToReturn" );
				request.setFeatureValue(featureValue );
				request.setTimestamp(getDateString());
				try {
					WareWriteMergeWareFeaturesResponse response=jdClient.execute(request);
					if(response==null||!"0".equals(response.getCode())){
						logger.error("添加商品七天无理由退换货失败 wareId:"+jdWareId+" xiuCode:"+xiuCode+" 错误码："+response.getZhDesc());
					}
				
				} catch (Exception e) {
					logger.error("添加商品七天无理由退换货失败 wareId:"+jdWareId+" xiuCode:"+xiuCode);
					e.printStackTrace();
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加商品七天无理由退换货失败 wareId:"+jdWareId+" xiuCode:"+xiuCode+e);
		}
		
	}

	@Override
	public String addEbayWareInBatch(JDProduct product, List<JDSku> skuInfoList,
			String isStatus, String attrbute, String shopType) {
		String result = "";
		String note=product.getNotes();
		String title=product.getTitle();
		List<String> sensitiveList=jdSensitiveWord;
		if (jdSensitiveWord.size()>0) {
			for (String string : sensitiveList) {
				if (string != null && !"".equals(string)) {
					note = note.replace(string, "");
					title = title.replace(string, "");
				}
			}
		}
		Pattern pattern = Pattern.compile("\\【(.*?)\\】");
        Matcher matcher = pattern.matcher(title);       
        while(matcher.find()){
        	title=title.replace(matcher.group(), "");             
        }
        String content="";
		try {
			content=jDWareDaoBean.getJdAdContent();
			logger.info("京东广告词"+content);
		} catch (Exception e) {
			logger.info("京东广告词失败"+content);
			e.printStackTrace();
		}
		//更新sku对应的商品京东ID
		List<JDSku> jdSkus=null;
		if (skuInfoList != null && skuInfoList.size() > 0) {
			WareAddRequest wareAddRequest = new WareAddRequest();
			wareAddRequest.setCid(product.getCid());
			wareAddRequest.setTitle(title);
			wareAddRequest.setHigh(product.getHigh());
			wareAddRequest.setWide(product.getWide());
			wareAddRequest.setWeight(product.getWeight());
			wareAddRequest.setLength(product.getLenght());
			wareAddRequest.setMarketPrice(product.getMarketprice());
			wareAddRequest.setJdPrice(product.getJdprice());
			wareAddRequest.setNotes(note);
			wareAddRequest.setWareImage(HttUtils.getResponseData(product.getMainimagepath()));
			wareAddRequest.setStockNum(product.getStocknum());
			wareAddRequest.setAttributes(attrbute);
			wareAddRequest.setShopCategory(product.getShopCategory());
			wareAddRequest.setCanVAT("true");
			wareAddRequest.setOptionType("offsale");
			wareAddRequest.setAdContent(product.getAdContent());
			String itemNum=product.getItemNum();
			if (itemNum==null||itemNum.equals("")) {
				itemNum="N";
			}
			wareAddRequest.setItemNum(itemNum);
			wareAddRequest.setService("POP商家售后服务");
			wareAddRequest.setAdContent(content);
			// product.setStatus("0");
			// jDWareDaoBean.update(product);
			StringBuilder skusn = new StringBuilder();
			StringBuilder skuOutId = new StringBuilder();
			StringBuilder skuprice = new StringBuilder();
			StringBuilder skuStocks = new StringBuilder();
			StringBuilder skuProperties = new StringBuilder();
			jdSkus=new ArrayList<JDSku>();
			for (int i = 0; i < skuInfoList.size(); i++) {
				JDSku jdSku=new JDSku();
				JDSku ebarjdSku=new JDSku();
				if (i == skuInfoList.size() - 1) {
//					if (skuInfoList.get(i).getAttributes() != null && !"".equals(skuInfoList.get(i).getAttributes().trim())) {
//						skuProperties.append(skuInfoList.get(i).getAttributes().trim());
//						skusn.append(skuInfoList.get(i).getSkusn().trim());
//						skuOutId.append(skuInfoList.get(i).getSkusn().trim());
//						skuprice.append(product.getJdprice().trim());
//						skuStocks.append(skuInfoList.get(i).getStocknum().trim());
//					} else {
						Map<String, Object> mapColor = new HashMap<String, Object>();
						mapColor.put("jdCid", product.getCid());
						mapColor.put("attrValue", skuInfoList.get(i).getColorname().trim());
						logger.info("mapColor1 " + mapColor.toString());

						String colorAttr = jdSkuDaoBean.findEbayResultString(mapColor);
						//String ebaycolorAttr = jdSkuDaoBean.findResultString(mapColor);
						//String ebayjdSkus =jd
						logger.info("colorAttr1  " + colorAttr);

						Map<String, Object> mapSize = new HashMap<String, Object>();
						mapSize.put("jdCid", product.getCid());
						String size1=skuInfoList.get(i).getSizevalue();
						if(size1==null||size1.equals("")){
							size1="F";
						}
						mapSize.put("attrValue", size1);
						logger.info("mapSize1  " + mapSize.toString());
						String sizeAttr = jdSkuDaoBean.findEbayResultString(mapSize);
						
						logger.info("sizeAttr1 " + sizeAttr);
						
						StringBuilder skuAttribute = new StringBuilder();
						if (!"".equals(colorAttr)) {
							skuAttribute.append(colorAttr);
							skuAttribute.append("^");
						}

						if (!"".equals(sizeAttr)) {
							skuAttribute.append(sizeAttr);
						}
						String tempSkuAttribute = skuAttribute.toString();
						if (tempSkuAttribute.endsWith("^")) {
							skuAttribute.deleteCharAt(skuAttribute.length() - 1);
						}
						
						if (!"".equals(skuAttribute.toString())) {
							skuProperties.append(skuAttribute.toString());
							skusn.append(skuInfoList.get(i).getSkusn().trim());
							skuOutId.append(skuInfoList.get(i).getSkusn().trim());
							skuprice.append(product.getJdprice().trim());
							skuStocks.append(skuInfoList.get(i).getStocknum().trim());
						}
						jdSku.setAttributes(skuAttribute.toString());
				} else {
						Map<String, Object> mapColor = new HashMap<String, Object>();
						mapColor.put("jdCid", product.getCid());
						mapColor.put("attrValue", skuInfoList.get(i).getColorname().trim());
						logger.info("mapColor2 " + mapColor.toString());
						String colorAttr = jdSkuDaoBean.findEbayResultString(mapColor);
						logger.info("colorAttr " + colorAttr);

						Map<String, Object> mapSize = new HashMap<String, Object>();
						mapSize.put("jdCid", product.getCid());
						String  size=skuInfoList.get(i).getSizevalue();
						if(size==null||size.equals("")){
							size="F";
						}
						mapSize.put("attrValue", size.trim());
						logger.info("mapSize2 " + mapSize.toString());
						String sizeAttr = jdSkuDaoBean.findEbayResultString(mapSize);
						logger.info("sizeAttr2 " + sizeAttr);
						
						StringBuilder skuAttribute = new StringBuilder();
						if (!"".equals(colorAttr)) {
							skuAttribute.append(colorAttr);
							skuAttribute.append("^");
						}

						if (!"".equals(sizeAttr)) {
							skuAttribute.append(sizeAttr);
						}
						String tempSkuAttribute = skuAttribute.toString();
						if (tempSkuAttribute.endsWith("^")) {
							skuAttribute.deleteCharAt(skuAttribute.length() - 1);
						}
						
						if (!"".equals(skuAttribute.toString())) {
							skuProperties.append(skuAttribute.toString()).append("|");
							skusn.append(skuInfoList.get(i).getSkusn().trim()).append("|");
							skuOutId.append(skuInfoList.get(i).getSkusn().trim()).append("|");
							skuprice.append(product.getJdprice().trim()).append("|");
							skuStocks.append(skuInfoList.get(i).getStocknum().trim()).append("|");
						}
						jdSku.setAttributes(skuAttribute.toString());
//					}
				}
				
				jdSku.setSkusn(skuInfoList.get(i).getSkusn().trim());
				jdSkus.add(jdSku);
			}
			if (!"".equals(skusn.toString())) {
				wareAddRequest.setOuterId(skuOutId.toString());
				wareAddRequest.setSkuPrices(skuprice.toString());
				wareAddRequest.setSkuProperties(skuProperties.toString());
				wareAddRequest.setSkuStocks(skuStocks.toString());
			} else {
				result = "商品sku属性为空不能推送商品";
				return result;
			}
			
			try {
				WareAddResponse response = clientEbay.execute(wareAddRequest);
				//outPutCommInfo(response);
                String skuUpdateMessage="";
				if (response.getCode().equals("0")) {
					String jdWareId=response.getWareId() + "";
					product.setStatus("1");
					product.setJdWareId(jdWareId);
					product.setOnLineStatus(0);
					product.setShopCategory(product.getShopCategory());
					//jDWareDaoBean.update(product);
					
					jDWareDaoBean.updateEbay(product);
					
					try{
					if(jdSkus!=null && jdSkus.size()>0){
						//更新商品sku对应的京东id
						for(JDSku s:jdSkus){
							s.setJdWareJd(jdWareId);
						}
						jdSkuDaoBean.updateEbayBatch(jdSkus ,jdSkus.size());
						
						skuUpdateMessage="更新商品sku表的京东ID成功";
						
						
						/*UpdateProductSKUIdThread updateProductSKUIdThread= new UpdateProductSKUIdThread(client,jdSkuDaoBean,jdWareId);
						Thread thread=new Thread(updateProductSKUIdThread);
						thread.start();*/
						JdSkuIdSyn jdSkuIdSyn=new JdSkuIdSyn();
						jdSkuIdSyn.setJdWareId(jdWareId);
						
						
						JdSkuIdSyn syn=jdSkuIdSynServiceBean.insertEbayJdSkuIdSynService(jdSkuIdSyn);
						if(syn!=null){
							logger.info("保存成功");
						}
						
					}
					}catch(Exception e){
						logger.error("更新商品sku表的京东ID异常");
						skuUpdateMessage="更新商品sku表的京东ID异常";
					}
				
					result += "走秀码" + product.getXiucode() + ":推送成功," + "商品编码为：" + response.getWareId() + "\n 主图片路径"+product.getMainimagepath()
					+"\n商品sku表更新情况"+skuUpdateMessage;
					addTemplate(Long.toString(response.getWareId()),clientEbay);
				} else {
					result += "走秀码" + product.getXiucode() + ":推送失败:" + response.getZhDesc() + ",商品的属性为:" + product.getAttributes() +

					" sku属性为:" + skuProperties.toString() + ",主图片路径"+product.getMainimagepath()+"\n京东错误码为"+response.getCode()
					+"\n商品sku表更新情况"+skuUpdateMessage;
					
					JDProduct productFail =new JDProduct();
					productFail.setXiucode(product.getXiucode());
					productFail.setStatus("2");	
					jDWareDaoBean.updateXiu(productFail);
					
				}
			} catch (Exception e) {
				result += "走秀码" + product.getXiucode() + "推送失败" + e.getStackTrace() + "\n异常信息:\n"+e;
				e.printStackTrace();
			} finally {
				logger.info("解锁被锁住的商品");
				//jDWareDaoBean.unlockEbay(product.getXiucode());
			}
		}

		return result;
	}


	@Override
	public JDAttributeValue getJdBrand(String xiuCode, String catCode,String shoptype) {
		JDAttributeValue attrValue=null;
		try {
			Map<String, String> attrMap = new HashMap<String, String>();
			attrMap.put("cid", catCode);
			attrMap.put("attrName", "品牌");
			attrMap.put("attrValue", "其它");
			JDAttributeValue jdAttributeValue=null;
			if (shoptype.equals(GlobalConstants.SHOP_TYEP_EBAY)) {
				jdAttributeValue = jDAttributeValueDaoBean.findEbayJdAttributeValue(attrMap);
						
			}
			else if(shoptype.equals(GlobalConstants.SHOP_TYEP_XIU)){
				jdAttributeValue = jDAttributeValueDaoBean.findXiuJdAttributeValue(attrMap);
			}else{
				jdAttributeValue = jDAttributeValueDaoBean
						.findJdAttributeValue(attrMap);
			}
			if (jdAttributeValue!= null
					&& !jdAttributeValue.equals("")) {
				attrValue = jdAttributeValue;
			} else {
				attrMap.put("attrValue", "其他");
				JDAttributeValue jdAttributeValue2 =null;
				
				if (shoptype.equals(GlobalConstants.SHOP_TYEP_EBAY)) {
					jdAttributeValue2 = jDAttributeValueDaoBean
							.findEbayJdAttributeValue(attrMap);
				}
				else if(shoptype.equals(GlobalConstants.SHOP_TYEP_XIU)){
					jdAttributeValue2 = jDAttributeValueDaoBean
							.findXiuJdAttributeValue(attrMap);
				}else{
					jdAttributeValue2 = jDAttributeValueDaoBean
							.findJdAttributeValue(attrMap);
				}
				if (jdAttributeValue2!= null
						&& !jdAttributeValue2.equals("")) {
					attrValue = jdAttributeValue2;
				}
			}
			logger.info("店铺类别:"+shoptype+" 京东分类"+catCode+" 走秀码:"+xiuCode+"品牌映射失败"+attrValue.getAid()+"|"+attrValue.getVid());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("店铺类别:"+shoptype+" 走秀码:"+xiuCode+"品牌映射失败");
		}
		return attrValue;
	}
	
	
	@Override
	public String getJdAdcontent() {
		// TODO Auto-generated method stub
		return jDWareDaoBean.getJdAdContent();
	}


	@Override
	public void updateJdAdcontent(String jdAdContent) {
		jDWareDaoBean.updateJdAdContent(jdAdContent);
		
	}




}

/**
 * 从网络上获取图片并压缩为800*800保存到本地目录
 * 
 * @author user
 * 
 */
class ImageCompressThread implements Runnable {
	private String skuNetWorkPath;

	public ImageCompressThread(String skuNetWorkPath) {
		this.skuNetWorkPath = skuNetWorkPath;

	}

	@Override
	public void run() {
		try {
			JDWareServiceBean.logger.info("图片网络路径为" + skuNetWorkPath);
			String returnData = HttUtils.postRequest(skuNetWorkPath);
			if (returnData != null) {
				JDWareServiceBean.logger.info("生成sku800*800的图片成功");
			}

		} catch (Exception e) {
			JDWareServiceBean.logger.info("生成sku800*800的图片异常");
			e.printStackTrace();
		}

	}
}

/***
 * 更新jd_product_sku_info表京东商品sku字段(jdskuid)
 * @author user
 *
 */
class UpdateProductSKUIdThread implements Runnable{
	protected static final Logger logger = Logger.getLogger(UpdateProductSKUIdThread.class);
	private JdClient client;
	private JdSkuDao<JDSku> jdSkuDaoBean; 
	private String jdWareId;
	

	public UpdateProductSKUIdThread(JdClient client,
			JdSkuDao<JDSku> jdSkuDaoBean, String jdWareId) {
		this.client=client;
		this.jdSkuDaoBean=jdSkuDaoBean;
		this.jdWareId=jdWareId;
	}

	@Override
	public void run() {
		logger.info("京东商品ID为"+this.jdWareId+" >>client "+client+" -->"+jdSkuDaoBean);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if(this.jdWareId!=null && !"".equals(this.jdWareId.trim())){
			WareGetRequest wareGetRequest=new WareGetRequest();
			
			wareGetRequest.setWareId(this.jdWareId);
			WareGetResponse response=null;
			try {
				response = client.execute(wareGetRequest);
			} catch (JdException e) {
				logger.error("调用京东API异常,新jd_product_sku_info表京东商品sku字段");
				e.printStackTrace();
			}
			if(response!=null && "0".equals(response.getCode())){
				Ware ware=response.getWare();
				if(ware!=null){
					List<com.jd.open.api.sdk.domain.ware.Sku> skus=ware.getSkus();
					logger.info("集合大小"+skus);
					if(skus!=null && skus.size()>0){
						List<JDSku> jdSkus=new ArrayList<JDSku>();
						for(com.jd.open.api.sdk.domain.ware.Sku sku:skus){
							String xiuSkuCode=sku.getOuterId();
							if(xiuSkuCode!=null && !"".equals(xiuSkuCode.trim())){
								JDSku jdSku=new JDSku();
								jdSku.setWareId(sku.getWareId()+"");
								jdSku.setSkusn(sku.getOuterId());
								jdSku.setJdSkuId(sku.getSkuId()+"");
								jdSkus.add(jdSku);
							}
						}
						if(jdSkus.size()>0){
							try{
							jdSkuDaoBean.updateBatch(jdSkus, jdSkus.size());
							logger.info("更新商品skuId成功");
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					
					}else{
						logger.info("京东商品ID为:"+this.jdWareId+"没有sku信息");
					}
				}
			}
			
		}
		
	}

	
	
	
	
}