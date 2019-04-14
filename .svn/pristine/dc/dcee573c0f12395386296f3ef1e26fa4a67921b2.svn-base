package com.xiu.jd.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;

import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.promotion.SellerPromotionAddRequest;
import com.jd.open.api.sdk.request.promotion.SellerPromotionCommitRequest;
import com.jd.open.api.sdk.request.promotion.SellerPromotionOrdermodeAddRequest;
import com.jd.open.api.sdk.request.promotion.SellerPromotionSkuAddRequest;
import com.jd.open.api.sdk.response.promotion.SellerPromotionAddResponse;
import com.jd.open.api.sdk.response.promotion.SellerPromotionCommitResponse;
import com.jd.open.api.sdk.response.promotion.SellerPromotionOrdermodeAddResponse;
import com.jd.open.api.sdk.response.promotion.SellerPromotionSkuAddResponse;
import com.xiu.jd.bean.page.PageView;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.JDWareAndSkuInfo;
import com.xiu.jd.bean.ware.JdOrderMode;
import com.xiu.jd.bean.ware.JdPromo;
import com.xiu.jd.bean.ware.JdPromoSku;
import com.xiu.jd.service.ware.JdPromoService;
import com.xiu.jd.utils.BaseUtils;
import com.xiu.jd.utils.FileUtils;
import com.xiu.jd.utils.ImportExcelUtil;
import com.xiu.jd.web.form.AddPromoForm;
import com.xiu.jd.web.form.PromoForm;

@SuppressWarnings("serial")
@Scope("prototype")
public class JdPromoAction extends BaseAction {
private static final Logger logger=Logger.getLogger(JdPromoAction.class);
    
	 
    private File uploadFile;//得到上传的文件    
	 
    private String uploadFileContentType;//得到文件的类型  
    
    private String uploadFileFileName;//得到文件的名称
    
	@Resource(name = "jdPromoServiceBean")
	private JdPromoService jdPromoServiceBean;
	
	
	private String xiuCodes;
	private String wareIds;
	private String jdSkus;
	private String xiuSkus;
	
	private PromoForm promoForm=new PromoForm() ;
	
	private PageView<JdPromo> pageView;
	
	private AddPromoForm addPromoForm; 

    
    public String importPromo(){
    	JdClient client=BaseUtils.getJdClient();
    	String redirectUrl = request.getContextPath() + "/jdGoods/importPromoUI.action";
		if(uploadFile!= null){
			// 取得文件后缀名
			String ext = FileUtils.getFileExt(uploadFileFileName);
			if (!"xls".equals(ext) && !"xlsx".equals(ext)) {
				request.setAttribute("message", "上传文件必须为Excle文件");
				request.setAttribute("redirectUrl", redirectUrl);
				return "message";
			}
	   
			try {
				Object wb = ImportExcelUtil.getWorkbook(uploadFile, uploadFileFileName);
				Object sheet = null;
				String sku="";
				if(wb instanceof HSSFWorkbook){
					sheet = ((HSSFWorkbook) wb).getSheetAt(0);
				}
				if(wb instanceof XSSFWorkbook){
					sheet = ((XSSFWorkbook) wb).getSheetAt(0);
				}
				Object row = null;
				int rowCount = 0;
				if(sheet instanceof HSSFSheet){
					row = ((HSSFSheet) sheet).getRow(0);
					rowCount = ((HSSFSheet) sheet).getLastRowNum();
				}
				if(sheet instanceof XSSFSheet){
					row = ((XSSFSheet) sheet).getRow(0);
					rowCount = ((XSSFSheet) sheet).getLastRowNum();
				}
				Object cellSkuInfo = ImportExcelUtil.getCell(row, 3);
				if(cellSkuInfo != null){
					sku = ImportExcelUtil.getCellValueStr(cellSkuInfo);
				}
				List<JdPromo> jdPromoList = null;
				List<JdPromoSku> JdPromoSku = null;
				for(int i=1;i<=rowCount;i++){
					JdPromo jdPromo = new JdPromo();
					JdPromoSku jdPromoSku =new JdPromoSku();
					String name=getSkuMapInfo(sheet,row,i,0);
					boolean bool=false;
					if(name==null || "".equals(name.trim())){
						continue;
					}
					jdPromo.setName(name);
					String beginTime =getSkuMapInfo(sheet,row,i,1);
					if(beginTime==null||"".equals(beginTime.trim())){
						continue;
					}
					jdPromo.setBeginTime(beginTime);
					String endTime=getSkuMapInfo(sheet,row,i,2);
					if(endTime==null || "".equals(endTime.trim())){
						continue;
					}
					jdPromo.setEndTime(endTime);
					jdPromo.setType(1);
					
					
					
					String xiuCode=getSkuMapInfo(sheet,row,i,3);
					String xiuSku=getSkuMapInfo(sheet,row,i,4);
					String wareId=getSkuMapInfo(sheet,row,i,5);
					String jdWareSkuId=getSkuMapInfo(sheet,row,i,6);
					String jdPrice=getSkuMapInfo(sheet,row,i,7);
					String promoPrice=getSkuMapInfo(sheet,row,i,8);					
					jdPromoSku.setXiuCode(xiuCode);
					jdPromoSku.setSkuSn(xiuSku);
					jdPromoSku.setWareId(wareId);
					jdPromoSku.setJdPrice(jdPrice);
					jdPromoSku.setPromoPrice(promoPrice);
					jdPromoSku.setJdSkuId(jdWareSkuId);
					
					
					SellerPromotionAddRequest addPromoequest=new SellerPromotionAddRequest();
					addPromoequest.setName(jdPromo.getName());
					addPromoequest.setType( 1 );
					addPromoequest.setBeginTime( jdPromo.getBeginTime() );
					addPromoequest.setEndTime( jdPromo.getEndTime() );					
					SellerPromotionAddResponse response=null;
					try {
						logger.info("==创建单品促销开始==");
						response=client.execute(addPromoequest);						
						Long promoId =response.getPromoId();
						jdPromo.setPromoId(Long.toString(promoId));
						jdPromoSku.setPromoId(Long.toString(promoId));
						
						SellerPromotionSkuAddRequest skuAddRequest=new SellerPromotionSkuAddRequest();
						skuAddRequest.setPromoId( promoId );
						skuAddRequest.setSkuIds( jdPromoSku.getJdSkuId() );
						skuAddRequest.setJdPrices( jdPromoSku.getJdPrice() );
						skuAddRequest.setPromoPrices( jdPromoSku.getPromoPrice() );
						SellerPromotionSkuAddResponse skuResponse=null;
						if (promoId!=null) {
							try {
								logger.info("==创建单品促销添加sku开始=="+jdPromo+" "+jdPromoSku);
								skuResponse = client.execute(skuAddRequest);
								List<Long> ids = skuResponse.getIds();
								SellerPromotionCommitRequest requestCommit = new SellerPromotionCommitRequest();
								requestCommit.setPromoId(promoId);
								SellerPromotionCommitResponse responseCommit = null;
								try {
									if (ids!=null) {
										logger.info("==提交单品促销开始=="
												+ jdPromo.toString() + " "
												+ jdPromoSku.toString());
										responseCommit = client
												.execute(requestCommit);
										bool = responseCommit.getSuccess();
										if (bool) {
											jdPromoSku.setStatus(1);//添加促销成功
										} else {
											jdPromoSku.setStatus(2);//添加促销失败
											jdPromoSku.setFailDesc("单品促销提交失败:"+responseCommit.getZhDesc());
										}
									}else{
										jdPromoSku.setStatus(2);
										jdPromoSku.setFailDesc("单品添加sku失败："+skuResponse.getZhDesc());
									}

								} catch (Exception e) {
									jdPromoSku.setFailDesc("单品促销提交失败:"+responseCommit.getZhDesc());
									jdPromoSku.setStatus(2);
									logger.error("单品促销提交失败：" + jdPromo.getPromoId() + " "
											+ jdPromoSku.getJdSkuId() + "返回错误代码"
											+ responseCommit.getCode()
											+ " 错误描述："
											+ responseCommit.getZhDesc());
									e.printStackTrace();
								}
							} catch (Exception e) {
								jdPromoSku.setStatus(2);
								jdPromoSku.setFailDesc("单品添加sku失败："+skuResponse.getZhDesc());
								logger.error("单品添加sku促销失败：" + jdPromo.getPromoId() + " "
										+ jdPromoSku.getJdSkuId() + "返回错误代码"
										+ skuResponse.getCode() + " 错误描述："
										+ skuResponse.getZhDesc());
								e.printStackTrace();
								
							}
						}
						
						jdPromoServiceBean.createPromo(jdPromo, jdPromoSku);
						
					} catch (Exception e) {
						jdPromoSku.setFailDesc("创建单品促销失败:"+response.getZhDesc());
						jdPromoSku.setStatus(2);
						logger.error("创建单品促销失败："+jdPromo+" "+jdPromoSku+"返回错误代码"+response.getCode()+" 错误描述："+response.getZhDesc()
								);
						e.printStackTrace();
					} 
				}	
				request.setAttribute("message", "文件已经上传");
			} catch (Exception e) {
				request.setAttribute("message", "文件上传异常");
				e.printStackTrace();
			}
		}
		request.setAttribute("redirectUrl", redirectUrl);
		return "message";
    }
    
    /**
	 * 获取excel单元格的信息
	 * @param sheet
	 * @param row
	 * @param rowIndex
	 * @param index
	 * @return
	 */
	private String getSkuMapInfo(Object sheet,Object row,int rowIndex, int index) {
		String skuInfo = "";
		if (sheet instanceof HSSFSheet) {
			row = ((HSSFSheet) sheet).getRow(rowIndex);
		}
		if (sheet instanceof XSSFSheet) {
			row = ((XSSFSheet) sheet).getRow(rowIndex);
		}
		Object cellSku = ImportExcelUtil.getCell(row, index);
		if(cellSku != null){
			skuInfo = ImportExcelUtil.getCellValue3(cellSku);
		}
		return skuInfo;
	}
    

	public String getPromoList(){
		try {
			Map<String, Object> parames = new HashMap<String,Object>();
			List<String> codes = new ArrayList<String>();
			if (this.xiuCodes != null && !"".equals(this.xiuCodes.trim())) {

				String codeString[] = this.xiuCodes.split("\r\n");
				int len = codeString.length;
				if (codeString != null && len > 0) {
					if (len > 200) {
						String redirectUrl = request.getContextPath() + "/jdGoods/getPromoList.action";
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
			
			if (this.jdSkus != null && !"".equals(this.jdSkus.trim())) {

				String codeString[] = this.jdSkus.split("\r\n");
				int len = codeString.length;
				if (codeString != null && len > 0) {
					if (len > 200) {
						String redirectUrl = request.getContextPath() + "/jdGoods/getPromoList.action";
						request.setAttribute("message", "京东sku超出200个");
						request.setAttribute("redirectUrl", redirectUrl);
						return "message";
					}
					for (String c : codeString) {
						codes.add(c.trim());
					}
					parames.put("jdSkus", codes);
				}
			}
			
			if (this.wareIds != null && !"".equals(this.wareIds.trim())) {

				String codeString[] = this.wareIds.split("\r\n");
				int len = codeString.length;
				if (codeString != null && len > 0) {
					if (len > 200) {
						String redirectUrl = request.getContextPath() + "/jdGoods/getPromoList.action";
						request.setAttribute("message", "京东商品编码超出200个");
						request.setAttribute("redirectUrl", redirectUrl);
						return "message";
					}
					for (String c : codeString) {
						codes.add(c.trim());
					}
					parames.put("wareIds", codes);
				}
			}
			
			if (this.xiuSkus != null && !"".equals(this.xiuSkus.trim())) {

				String codeString[] = this.xiuSkus.split("\r\n");
				int len = codeString.length;
				if (codeString != null && len > 0) {
					if (len > 200) {
						String redirectUrl = request.getContextPath() + "/jdGoods/getPromoList.action";
						request.setAttribute("message", "走秀sku数量超出200个");
						request.setAttribute("redirectUrl", redirectUrl);
						return "message";
					}
					for (String c : codeString) {
						codes.add(c.trim());
					}
					parames.put("xiuSkus", codes);
				}
			}
			
			this.setPageSize(20);
			pageView = new PageView<JdPromo>(this.getPageSize(), this.getCurrentPage());
			parames.put("start", pageView.getFirstResult());
			parames.put("end", pageView.getMaxresult());
			parames.put("type", 10);//默认查询总价促销
			parames.put("name", promoForm.getName());
			parames.put("beginTime", promoForm.getBeginTime());
			parames.put("endTime", promoForm.getEndTime());
			QueryResult<JdPromo> qr = jdPromoServiceBean.getPageResule(parames);
			pageView.setQueryResult(qr);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
		
	}
	
	public String addPromoUI(){
		return SUCCESS;
		
	}
	
	public String addPromo(){
		String responseString=null;
		JdClient client=BaseUtils.getJdClient();
		JdPromo jdPromo=new JdPromo();
		JdOrderMode jdOrderMode=new JdOrderMode();
		
		jdPromo.setBeginTime(addPromoForm.getStartTime().trim()); 
		jdPromo.setEndTime(addPromoForm.getEndTime().trim());
		jdPromo.setType(10);//10表示总价促销
		jdPromo.setName(addPromoForm.getName().trim());
		jdPromo.setBound(addPromoForm.getBound().trim());
		jdPromo.setMember(addPromoForm.getFavorMode().trim());
		jdPromo.setFavorMode(addPromoForm.getFavorMode().trim());
		
		
		
		
		SellerPromotionAddRequest promoRequest=new SellerPromotionAddRequest();
		promoRequest.setName( jdPromo.getName() );
		promoRequest.setType( jdPromo.getType() ); 
		promoRequest.setBeginTime( jdPromo.getBeginTime() ); 
		promoRequest.setEndTime( jdPromo.getEndTime() ); 
		promoRequest.setBound(Integer.valueOf(jdPromo.getBound())); 
		promoRequest.setFavorMode( Integer.valueOf(jdPromo.getFavorMode()) );
		Long promoId=null;
		Boolean bool= false;
		try {
			SellerPromotionAddResponse response=client.execute(promoRequest);
			if(response.getCode().equals("0")){
				promoId=response.getPromoId();
				jdPromo.setPromoId(String.valueOf(promoId));
			}
			logger.info(response.getCode()+": " +response.getZhDesc()+response.getPromoId());
		} catch (JdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("添加促销失败");
		} 
		if(promoId!=null){
			jdOrderMode.setFavorMode(addPromoForm.getFavorMode().trim());
			jdOrderMode.setLink(addPromoForm.getLink().trim());
			jdOrderMode.setQuota(addPromoForm.getQuota().trim());
			jdOrderMode.setRate(addPromoForm.getRate().trim());
			jdOrderMode.setPromoId(String.valueOf(promoId));
			jdOrderMode.setJdDesc(addPromoForm.getJdDesc().trim());
			
			SellerPromotionOrdermodeAddRequest orderModeRequest=new SellerPromotionOrdermodeAddRequest();

			orderModeRequest.setPromoId(promoId); 
			orderModeRequest.setFavorMode(Integer.valueOf(jdOrderMode.getFavorMode()) ); 
			orderModeRequest.setQuota( jdOrderMode.getQuota() ); 
			orderModeRequest.setRate( jdOrderMode.getRate() ); 
			try {
				SellerPromotionOrdermodeAddResponse orderModeResponse=client.execute(orderModeRequest);
				if(orderModeResponse.getCode().equals("0")){
					bool=true;
				}
				logger.info("添加订单规则：" +orderModeResponse.getCode()+orderModeResponse.getZhDesc());
			} catch (JdException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("促销添加订单规则失败 促销ID:"+promoId);
			}
			
		}
		
		if(bool)
		  {
			try {
				jdPromoServiceBean.addCentsOffPromo(jdPromo);
				jdPromoServiceBean.addOrderMode(jdOrderMode);
				String redirectUrl = request.getContextPath() + "/jdGoods/promoInfoList.action";
				request.setAttribute("message", "添加成功");
				request.setAttribute("redirectUrl", redirectUrl);
				responseString= "message";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("添加促销入库失败");
			}
		  }
		
		else{
			
			String redirectUrl = request.getContextPath() + "/jdGoods/promoInfoList.action";
			request.setAttribute("message", "添加促销失败");
			request.setAttribute("redirectUrl", redirectUrl);
			responseString= "message";
		}
		
		return responseString;
		
		
	}
	
	
	public String importPromoSkuUI(){
		
		return SUCCESS;
		
	}
	
	
	
	  public String importPromoSku(){
	    	JdClient client=BaseUtils.getJdClient();
	    	String redirectUrl = request.getContextPath() + "/jdGoods/importPromoSkuUI.action";
			if(uploadFile!= null){
				// 取得文件后缀名
				String ext = FileUtils.getFileExt(uploadFileFileName);
				if (!"xls".equals(ext) && !"xlsx".equals(ext)) {
					request.setAttribute("message", "上传文件必须为Excle文件");
					request.setAttribute("redirectUrl", redirectUrl);
					return "message";
				}
		   
				try {
					Object wb = ImportExcelUtil.getWorkbook(uploadFile, uploadFileFileName);
					Object sheet = null;
					String sku="";
					if(wb instanceof HSSFWorkbook){
						sheet = ((HSSFWorkbook) wb).getSheetAt(0);
					}
					if(wb instanceof XSSFWorkbook){
						sheet = ((XSSFWorkbook) wb).getSheetAt(0);
					}
					Object row = null;
					int rowCount = 0;
					if(sheet instanceof HSSFSheet){
						row = ((HSSFSheet) sheet).getRow(0);
						rowCount = ((HSSFSheet) sheet).getLastRowNum();
					}
					if(sheet instanceof XSSFSheet){
						row = ((XSSFSheet) sheet).getRow(0);
						rowCount = ((XSSFSheet) sheet).getLastRowNum();
					}
					Object cellSkuInfo = ImportExcelUtil.getCell(row, 3);
					if(cellSkuInfo != null){
						sku = ImportExcelUtil.getCellValueStr(cellSkuInfo);
					}
					
					List<JdPromoSku> JdPromoSku = null;
					for(int i=1;i<=rowCount;i++){
						JdPromo jdPromo = new JdPromo();
						JdPromoSku jdPromoSku =new JdPromoSku();
						String name=getSkuMapInfo(sheet,row,i,0);
						boolean bool=false;
						if(name==null || "".equals(name.trim())){
							continue;
						}
						jdPromo.setName(name);
						String beginTime =getSkuMapInfo(sheet,row,i,1);
						if(beginTime==null||"".equals(beginTime.trim())){
							continue;
						}
						jdPromo.setBeginTime(beginTime);
						String endTime=getSkuMapInfo(sheet,row,i,2);
						if(endTime==null || "".equals(endTime.trim())){
							continue;
						}
						jdPromo.setEndTime(endTime);
						jdPromo.setType(1);
						
						
						
						String xiuCode=getSkuMapInfo(sheet,row,i,3);
						String xiuSku=getSkuMapInfo(sheet,row,i,4);
						String wareId=getSkuMapInfo(sheet,row,i,5);
						String jdWareSkuId=getSkuMapInfo(sheet,row,i,6);
						String jdPrice=getSkuMapInfo(sheet,row,i,7);
						String promoPrice=getSkuMapInfo(sheet,row,i,8);					
						jdPromoSku.setXiuCode(xiuCode);
						jdPromoSku.setSkuSn(xiuSku);
						jdPromoSku.setWareId(wareId);
						jdPromoSku.setJdPrice(jdPrice);
						jdPromoSku.setPromoPrice(promoPrice);
						jdPromoSku.setJdSkuId(jdWareSkuId);
						
						
						SellerPromotionAddRequest addPromoequest=new SellerPromotionAddRequest();
						addPromoequest.setName(jdPromo.getName());
						addPromoequest.setType( 1 );
						addPromoequest.setBeginTime( jdPromo.getBeginTime() );
						addPromoequest.setEndTime( jdPromo.getEndTime() );					
						SellerPromotionAddResponse response=null;
						try {
							logger.info("==创建单品促销开始==");
							response=client.execute(addPromoequest);						
							Long promoId =response.getPromoId();
							jdPromo.setPromoId(Long.toString(promoId));
							jdPromoSku.setPromoId(Long.toString(promoId));
							
							SellerPromotionSkuAddRequest skuAddRequest=new SellerPromotionSkuAddRequest();
							skuAddRequest.setPromoId( promoId );
							skuAddRequest.setSkuIds( jdPromoSku.getJdSkuId() );
							skuAddRequest.setJdPrices( jdPromoSku.getJdPrice() );
							skuAddRequest.setPromoPrices( jdPromoSku.getPromoPrice() );
							SellerPromotionSkuAddResponse skuResponse=null;
							if (promoId!=null) {
								try {
									logger.info("==创建单品促销添加sku开始=="+jdPromo+" "+jdPromoSku);
									skuResponse = client.execute(skuAddRequest);
									List<Long> ids = skuResponse.getIds();
									SellerPromotionCommitRequest requestCommit = new SellerPromotionCommitRequest();
									requestCommit.setPromoId(promoId);
									SellerPromotionCommitResponse responseCommit = null;
									try {
										if (ids!=null) {
											logger.info("==提交单品促销开始=="
													+ jdPromo.toString() + " "
													+ jdPromoSku.toString());
											responseCommit = client
													.execute(requestCommit);
											bool = responseCommit.getSuccess();
											if (bool) {
												jdPromoSku.setStatus(1);//添加促销成功
											} else {
												jdPromoSku.setStatus(2);//添加促销失败
												jdPromoSku.setFailDesc("单品促销提交失败:"+responseCommit.getZhDesc());
											}
										}else{
											jdPromoSku.setStatus(2);
											jdPromoSku.setFailDesc("单品添加sku失败："+skuResponse.getZhDesc());
										}

									} catch (Exception e) {
										jdPromoSku.setFailDesc("单品促销提交失败:"+responseCommit.getZhDesc());
										jdPromoSku.setStatus(2);
										logger.error("单品促销提交失败：" + jdPromo.getPromoId() + " "
												+ jdPromoSku.getJdSkuId() + "返回错误代码"
												+ responseCommit.getCode()
												+ " 错误描述："
												+ responseCommit.getZhDesc());
										e.printStackTrace();
									}
								} catch (Exception e) {
									jdPromoSku.setStatus(2);
									jdPromoSku.setFailDesc("单品添加sku失败："+skuResponse.getZhDesc());
									logger.error("单品添加sku促销失败：" + jdPromo.getPromoId() + " "
											+ jdPromoSku.getJdSkuId() + "返回错误代码"
											+ skuResponse.getCode() + " 错误描述："
											+ skuResponse.getZhDesc());
									e.printStackTrace();
									
								}
							}
							
							jdPromoServiceBean.createPromo(jdPromo, jdPromoSku);
							
						} catch (Exception e) {
							jdPromoSku.setFailDesc("创建单品促销失败:"+response.getZhDesc());
							jdPromoSku.setStatus(2);
							logger.error("创建单品促销失败："+jdPromo+" "+jdPromoSku+"返回错误代码"+response.getCode()+" 错误描述："+response.getZhDesc()
									);
							e.printStackTrace();
						} 
					}	
					request.setAttribute("message", "文件已经上传");
				} catch (Exception e) {
					request.setAttribute("message", "文件上传异常");
					e.printStackTrace();
				}
			}
			request.setAttribute("redirectUrl", redirectUrl);
			return "message";
	    }
	/**
	 * 商品库存模板下载动作
	 * @return
	 *//*
	public String xiuCodeTemplateDown()throws Exception{
	   logger.info("单品促销模板下载动作");
		try{
		String fileName = "xiuCode_template";
		response.setContentType("aplication/msexcel"); // 设置文件类型
		response.setHeader("Content-disposition", "attachment; filename="+fileName+".xls");

		HSSFWorkbook workbook =new HSSFWorkbook();// 创建工作薄
	
		//// 创建工作表
		HSSFSheet sheet=workbook.createSheet("sheet");
		//获取工作表中的第一行
		HSSFRow row=sheet.createRow(0);
        //设置第一行的表头
		List<String> title=getTitleValue();
		for(int i=0;i<title.size();i++){
			
			HSSFCell  cell=row.createCell(i);
			cell.setCellValue(title.get(i));
		}
		workbook.write(response.getOutputStream());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return null;
	}*/
    public String importPromoUI(){
		logger.info("显示 导入单品促销商品页面");
		return "success";
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

	public String getXiuCodes() {
		return xiuCodes;
	}

	public void setXiuCodes(String xiuCodes) {
		this.xiuCodes = xiuCodes;
	}

	public String getWareIds() {
		return wareIds;
	}

	public void setWareIds(String wareIds) {
		this.wareIds = wareIds;
	}

	public String getJdSkus() {
		return jdSkus;
	}

	public void setJdSkus(String jdSkus) {
		this.jdSkus = jdSkus;
	}

	public String getXiuSkus() {
		return xiuSkus;
	}

	public void setXiuSkus(String xiuSkus) {
		this.xiuSkus = xiuSkus;
	}

	public PromoForm getPromoForm() {
		return promoForm;
	}

	public void setPromoForm(PromoForm promoForm) {
		this.promoForm = promoForm;
	}

	public PageView<JdPromo> getPageView() {
		return pageView;
	}

	public void setPageView(PageView<JdPromo> pageView) {
		this.pageView = pageView;
	}

	public AddPromoForm getAddPromoForm() {
		return addPromoForm;
	}

	public void setAddPromoForm(AddPromoForm addPromoForm) {
		this.addPromoForm = addPromoForm;
	}
	
    
    
    
    

}
