package com.xiu.jd.schedule.ware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.jd.open.api.sdk.domain.ware.Sku;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.request.ware.WareInfoByInfoRequest;
import com.jd.open.api.sdk.request.ware.WareListRequest;
import com.jd.open.api.sdk.response.ware.WareInfoByInfoSearchResponse;
import com.jd.open.api.sdk.response.ware.WareListResponse;
import com.xiu.commerce.hessian.model.Product;
import com.xiu.jd.bean.ware.JDProduct;
import com.xiu.jd.bean.ware.JDSku;
import com.xiu.jd.dao.ware.JDWareDao;
import com.xiu.jd.schedule.BaseSchedule;
import com.xiu.jd.service.ProductService;
import com.xiu.jd.service.ware.JDWareService;
import com.xiu.jd.service.ware.JdSkuService;
import com.xiu.jd.utils.DateHelper;
import com.xiu.jd.utils.ParseProperties;

/**
 * 定时的从京东上获取商品以及商品的sku信息入到本地的数据库 jd_product_info jd_product_sku_info
 * 
 * @author user
 * 
 */
public class GetJdProductSchedule extends BaseSchedule {
	protected static final String LENGHT = "290";
	protected static final String WIDE = "230";
	protected static final String WEIGHT = "0.5";
	protected static final String HIGH = "50";
	private static final Logger logger = Logger.getLogger(GetJdProductSchedule.class);

	@Resource(name = "jDWareServiceBean")
	private JDWareService jDWareServiceBean;

	/** 商品DAO **/
	@Autowired
	private JDWareDao<JDProduct> jDWareDaoBean;

	@Resource(name = "jdSkuServiceBean")
	private JdSkuService<JDSku> jdSkuServiceBean;

	/**
	 * 商品中心
	 * 
	 * 
	 * **/
	@Resource(name = "productService")
	private ProductService productService;

public void getJdProdcut() {
  try {
		logger.info("=============  getJdProdcut  =====================");
		WareInfoByInfoRequest wareInfoByInfoRequest = new WareInfoByInfoRequest();
		int page = 1;
		wareInfoByInfoRequest.setPage(page + "");
		// 每页显示的记录数量
		//int size = 10;
		wareInfoByInfoRequest.setPageSize(pageSize + "");
		WareInfoByInfoSearchResponse response=null;
		try {
			response = client.execute(wareInfoByInfoRequest);
		} catch (Exception e1) {
			logger.error("从京东获取商品信息与商品sku信息异常"+e1);
			e1.printStackTrace();
		}
	   if (response != null) {
				String code = response.getCode();
				logger.info("0".equals(code) ? "商品信息入库,调用京东API成功" : "商品信息入库,调用京东API失败,失败原因" + response.getZhDesc());
				int total = response.getTotal();
				int totalPage = getTotalPage(pageSize, total);
				logger.info("总的记录数量为：" + total + "  总的页数为 =" + totalPage + ",  每页显示的记录数为" + pageSize);
				//long id=1;
				for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
					if(currentPage==totalPage){
						logger.info("--------------------------"+"总页数为:"+totalPage+"完成");
					}
					if (currentPage != 1) {
						wareInfoByInfoRequest = new WareInfoByInfoRequest();
						wareInfoByInfoRequest.setPage(currentPage + "");
						// 每页显示的记录数量
						wareInfoByInfoRequest.setPageSize(pageSize + "");
						try {
						response = client.execute(wareInfoByInfoRequest);
						} catch (Exception e) {
							logger.error("从京东获取商品信息与商品sku信息异常,当前页"+currentPage+"\n异常"+e);
							e.printStackTrace();
						}
						// logger.info("第"+(currentPage)+"页" );
					}
					logger.info("Current第" + currentPage + "页");
					   if (response != null && "0".equals(response.getCode())) {
							List<Ware> wares = response.getWareInfos();
							// 用于保存京东存在的商品,本地数据库不在在的京东商品id
							int total1 = wares.size();
							int pageSize1 = 10;
							// 总页数
							int totalPage1 = getTotalPage(pageSize1, total);
							int index = 0;
							for (int currentPage1 = 1; currentPage1 <= totalPage1; currentPage1++) {
								StringBuffer wardIds = new StringBuffer();
								try{
									for (; index < currentPage1 * pageSize1; index++) {
										if (index >= total1) {
											break;
										}
										Ware ware = wares.get(index);
										if (ware != null) {
											wardIds.append(ware.getWareId()).append(',');
										}

									}
								}catch(Exception e){
									logger.error("获取商品异常,index="+index+"total1="+total1+"\n"+e);
									e.printStackTrace();
								}
								
								if(wardIds.length() > 0) {
									wardIds.deleteCharAt(wardIds.length() - 1);
									WareListRequest wareListRequest = new WareListRequest();
									String wIds = wardIds.toString();
									logger.info("京东商品IDs = " + wIds);
									// 京东值支持10个
									wareListRequest.setWareIds(wIds);
									WareListResponse wareListResponse =null;
									try {
									 wareListResponse = client.execute(wareListRequest);
									} catch (Exception e) {
										logger.error("从京东批量获取商品信息与商品sku信息异常,京东商品ID"+wIds+",当前页("+currentPage+")\n异常"+e);
										e.printStackTrace();
									}
									if(wareListResponse==null){
										continue;
									}
									List<Ware> wareLists = wareListResponse.getWareList();
										if (wareLists != null) {
											//int index = 0;
											for (Ware ware : wareLists) {
												if (ware != null) {
													long wareId = ware.getWareId();
													String wareStatus=ware.getWareStatus();
													String goodsSn = "";
													List<Sku> skus = ware.getSkus();
													if (skus != null && skus.size() > 0) {
														Sku sku = skus.get(0);
														if (sku != null) {
																String skuCode = sku.getOuterId();
																if (skuCode != null && !"".equals(skuCode.trim())) {
																	if (skuCode.length() >= 8) {
																		goodsSn = skuCode.substring(0, 8);
																		
																	}
																	Product product = productService.loadProduct(goodsSn);
																/*	product.setBrandCode("111111");
																	product.setBCatCode("cc11112");
																	product.setInnerID(id);
																	product.setXiuSN(goodsSn);*/
																	if (product == null) {
																		logger.error("走秀码为 "+ goodsSn+ "在商品中心不存在");
																		continue;
																		// 商品所属的品牌
																		
																	} else {
																		// 走秀码在商品中心存在
																		// true:本地不存在,false本地存在
																		// wareIdIsExistsNation
																		boolean isExists = jDWareServiceBean.wareIdIsExistsNation(wareId+ "".trim());
																		//jDWareServiceBean.xiuCodeIsExistsNation(xiuCode)
																		logger.info(isExists ? "商品ID为"+ wareId+ "在本地不存在": "商品ID为"+ wareId+ "在本地存在");
																		if (isExists) {
																			// xiuCodes.add(goodsSn);
																			// 商品所属的品牌
																			// 基本分类编码是四级
																			if (goodsSn != null && !"".equals(goodsSn.trim())) {
																				JDProduct jdProduct = new JDProduct();
																				jdProduct.setXiucode(goodsSn);
																				jdProduct.setBrandcode(product.getBrandCode());
																				jdProduct.setCategoryid(product.getBCatCode());
																				jdProduct.setXiu_item_id(product.getInnerID()+ "");
							
																				// 将京东的商品入到本地库中
																				jdProduct.setTitle(ware.getTitle());
																				// cubage 长:宽:高
																				String cubage = ware.getCubage();
																				if (cubage != null) {
																					String cubages[] = cubage.split("\\:");
																					if(cubages==null||cubages.length==0){
																						jdProduct.setLenght(GetJdProductSchedule.LENGHT);
																					}
																					
																					try{
																						jdProduct.setWide(cubages[1]);
																						jdProduct.setHigh(cubages[2]);
																					}catch(Exception e){
																					   jdProduct.setWide(GetJdProductSchedule.WIDE);
																						jdProduct.setHigh(GetJdProductSchedule.HIGH);
																						logger.error("京东商品ID"+wareId+"走秀码为"+goodsSn+"异常,"+cubages.toString());
																						e.printStackTrace();
																					}
																					
																				}
																				Double productXiuPrice=product.getPrdOfferPrice();//单位为元																				
																				if("true".equals(ParseProperties.IS_ACTIVITY_PRICE)){
																					//走秀商品活动价
																					Double prdActivityPrice= product.getPrdActivityPrice();
																					if(prdActivityPrice!=null && prdActivityPrice>0){																						
																						productXiuPrice=prdActivityPrice;
																					}
																				}
																				jdProduct.setCostprice(Double.toString(productXiuPrice));
																				jdProduct.setWeight(ware.getWeight());
																				jdProduct.setMarketprice(ware.getMarketPrice());
																				jdProduct.setJdprice(ware.getJdPrice());
																				jdProduct.setAttributes(ware.getAttributes());
																				jdProduct.setStocknum(ware.getStockNum()+ "");
																				jdProduct.setCreateDate(DateHelper.stringToDate(ware.getCreated()));
																				jdProduct.setLastupdate(DateHelper.stringToDate(ware.getModified()));
																				jdProduct.setJd_ware_id(ware.getWareId()+ "");
																				jdProduct.setNotes(ware.getDesc());
																				jdProduct.setCid(ware.getCategoryId()+ "");
																				jdProduct.setMainimagepath(ware.getLogo());
																				jdProduct.setOperatename("system");
																				jdProduct.setStatus("1");
																				jdProduct.setShopCategory(ware.getShopCategorys());
																				/**
																				 * NEVER_UP:从未上架,

																					CUSTORMER_DOWN:自主下架
																						
																					SYSTEM_DOWN:系统下架
																						
																					ON_SALE:在售
																						
																					AUDIT_AWAIT: 待审核
																						
																					AUDIT_FAIL: 审核不通过
																				 */
																				//商品状态
																				
																				if("ON_SALE".equalsIgnoreCase(wareStatus)){
																					//在售
																					jdProduct.setOnLineStatus(1);
																				}else if(
																						"CUSTORMER_DOWN".equalsIgnoreCase(wareStatus)||"SYSTEM_DOWN".equalsIgnoreCase(wareStatus)){
																					jdProduct.setOnLineStatus(2);
																				}else{
																					jdProduct.setOnLineStatus(0);
																				}
																				logger.info("商品ID为"+ ware.getWareId()+ "商品走秀码"+ goodsSn+",京东状态为:"+wareStatus);
																				try{
																				   jDWareDaoBean.insert(jdProduct);
																				   
																				}catch(Exception e){
																					logger.error("商品ID为"+ ware.getWareId()+ "商品走秀码"+ goodsSn+",保存异常"+e);
																					e.printStackTrace();
																				}
							
																			}
																		}
							                                              
																		if (skus != null && skus.size() > 0) {
																			int batchSizes = skus.size();
																			List<JDSku> jdSkus = new ArrayList<JDSku>();
																			List<JDSku> jdSkusUpdate = new ArrayList<JDSku>();
																			//商品sku的个数
																			int count = 0;
																			//商品sku总库存
																			int skutotalStockNum=0;
																			for (Sku s : skus) {
																				if (s != null) {
																					count++;
																					skutotalStockNum=skutotalStockNum+(int)sku.getStockNum();
																					// 京东的outerId对应走秀商品的SKU码
																					String outerId = s.getOuterId();
																					logger.info("京东outerId"+outerId);
																					if (outerId == null|| "".equals(outerId.trim())) {
																						continue;
																					}
																					if(outerId.trim().length()>12){
																						outerId=outerId.trim().substring(0, 12);
																					}
																					outerId=outerId.trim();
																					if(outerId.trim().length()!=12){
																						logger.info("商品sku的长度不为12," +outerId);
																						continue;
																					}
																					//outerId=outerId.trim().replaceAll("\\&", "").replaceAll("\\#", "");
																					// //true:本地不存在,false本地存在
																					boolean isExist = jdSkuServiceBean.xiuSnIsExistsNation(outerId);
																					logger.info(isExist ? "京东商品ID"+ s.getWareId()+"SKU码为  "+ outerId+ "在本地不存在": "京东商品ID"+ s.getWareId()+"SKU码为  "+ outerId+ "在本地存在");
																					if (!isExist) {
																						logger.info("京东商品ID"+ s.getWareId()+ "  商品sku"+ outerId+ "  走秀码"+ goodsSn+",京东skuID="+s.getSkuId());
																						String skuAttr = jdSkuServiceBean.findLocalSkuAttr(outerId);
																						JDSku jdSkuUpdate = new JDSku();
																						jdSkuUpdate.setSkusn(outerId);
																						jdSkuUpdate.setJdWareJd(wareId+"");
																						jdSkuUpdate.setJdSkuId(s.getSkuId()+"");
																						if (skuAttr == null|| skuAttr.isEmpty()) {
																							jdSkuUpdate.setAttributes(s.getAttributes());
																							jdSkuUpdate.setColorname(s.getColorValue());
																							jdSkuUpdate.setSizevalue(s.getSizeValue());
																							//sku图片是否已经上传到京东：0未上传;1已经上传;2图片上传失败
																							jdSkuUpdate.setStatus("0");
																						}
																						jdSkusUpdate.add(jdSkuUpdate);
							
																					} else {
																						// 本地不存在(将不存在的sku保存在本地数据库中jd_product_sku_info表中)\
																						JDSku jdSku = new JDSku();
																						jdSku.setXiucode(goodsSn);
							
																						// 红色，黄色
																						jdSku.setColorname(s.getColorValue());
							
																						// //M
																						// ML S
																						jdSku.setSizevalue(s.getSizeValue());
																						jdSku.setSkusn(outerId);
																						jdSku.setNum(0);
																						jdSku.setStocknum(s.getStockNum()+ "");
																						// 商品sku销售属性
																						jdSku.setAttributes(s.getAttributes());
																						jdSku.setJdWareJd(wareId+"");
																						jdSku.setJdSkuId(s.getSkuId()+"");
																						jdSkus.add(jdSku);
																						
																						logger.info("京东商品ID"+ s.getWareId()+"商品sku码 "+ outerId);
																						
							
																					}
																					if (batchSizes == count) {
																						int onlineStatus=0;
																						//本地存在更新销售状态
																						if("ON_SALE".equalsIgnoreCase(wareStatus)){
																							//在售
																							onlineStatus=1;
																						}else{
																							onlineStatus=2;
																						}
																						Map<String, Object> productParmes=new HashMap<String, Object>();
																						productParmes.put("onlineStatus", onlineStatus);
																						productParmes.put("stocknum", skutotalStockNum);
																						productParmes.put("JdwareId", wareId);
																						
																						
																						int counts=jDWareDaoBean.updateProductOnline(productParmes);
																						logger.info("京东商品ID:"+wareId+"状态为:" +onlineStatus +" ,更新商品上下架状态,影响的记录数量为"+counts);
																						if (jdSkusUpdate.size() > 0) {
																							try{
																								jdSkuServiceBean.updateBatch(jdSkusUpdate,batchSizes);
																								}catch(Exception e){
																									logger.error("商品ID为"+ ware.getWareId()+ "商品走秀码"+ goodsSn+",更新sku异常"+e);
																									e.printStackTrace();
																								}
																							
																						}
																					}
							
																				}
																			}
																			  try{
																				  jdSkuServiceBean.insertBatch(jdSkus,batchSizes);
																				}catch(Exception e){
																					logger.error("商品ID为"+ ware.getWareId()+ "商品走秀码"+ goodsSn+",保存sku异常"+e);
																					e.printStackTrace();
																				}
																		
																		}
																	}
																}
															}
														}
													}
							                    // id++;
												}
											}
									}
							}
							
						}
				}
			}else{
				logger.info("京东商品入库response对象为"+response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("从京东商品获取商品信息异常"+e);
		}
	}


  //删除本地数据库不存在的商品和商品sku(与京东上的商品进行比较),这种情况是走秀用户通过京东后台删除商品所导致的,(会出现我们这边数据库中的记录比京东上的多了)

}
