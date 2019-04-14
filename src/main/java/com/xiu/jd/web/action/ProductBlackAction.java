package com.xiu.jd.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.solr.common.util.Hash;
import org.apache.struts2.ServletActionContext;

import com.xiu.jd.bean.page.PageView;
import com.xiu.jd.bean.page.QueryResult;
import com.xiu.jd.bean.ware.OnLineBlackProductBean;
import com.xiu.jd.service.ware.JDWareService;
import com.xiu.jd.service.ware.OnLineBlackProductService;
import com.xiu.jd.utils.CommonUtil;
import com.xiu.jd.utils.ExportExcelUtil;
import com.xiu.jd.utils.FileUtils;
import com.xiu.jd.utils.ImportExcelUtil;
import com.xiu.jd.web.form.BlackProductForm;
import com.xiu.usermanager.provider.bean.LocalOperatorsDO;

/**
 * 商品上下架黑名单(只针对商品上下架状态的黑名单,不包含商品库存与商品价格)
 * @author liweibiao
 *
 */
public class ProductBlackAction extends BaseAction {
	private static final long serialVersionUID = 3876378870584095945L;
	private static final Logger LOGGER=Logger.getLogger(ProductBlackAction.class);
	private static Map<String, String > title=new LinkedHashMap<String, String>();
	 
    private File uploadFile;//得到上传的文件
    
	  //application/vnd.ms-excel
    private String uploadFileContentType;//得到文件的类型
	  
	  
    private String uploadFileFileName;//得到文件的名称
    
  
    
    private PageView<OnLineBlackProductBean> pageView;
    
    private BlackProductForm productForm;
    
    /**记录ID**/
     private List<String> ids;
     
     private String id;
    
    @Resource(name="onLineBlackProductServiceBean")
    private OnLineBlackProductService onLineBlackProductServiceBean;
    
    @Resource(name="jDWareServiceBean")
    private JDWareService jDWareServiceBean;
    
	static{
		title.put("xiuCode", "走秀码");
	}
	/**
	 * 进入黑名单导入界面
	 * @return
	 */
	public String exportExcelBlackUI(){
		LOGGER.info("进入黑名单导入界面");
		return "success";
	}
	
	/**
	 * 黑名单模板下载动作
	 * @return
	 */
	public String blackTemplateDown()throws Exception{
	   LOGGER.info("黑名单模板下载动作");
		try{
		String fileName = "off_online_template";
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
	}
	/**
	 * 解析上传的商品黑名单Excle
	 * @return
	 */
   public String blackExcleUpload(){

		LOGGER.info("解析上传的商品黑名单Excle");
		String redirectUrl=request.getContextPath()+"/jdWare/exportExcelUI.action";
		Workbook wb=null;
		FileInputStream fis=null;
		if(uploadFile!=null){
			//取得文件后缀名
			String ext=FileUtils.getFileExt(uploadFileFileName);
			if(!"xls".equals(ext) && !"xlsx".equals(ext)){
				request.setAttribute("message", "上传文件必须为Excle文件");
				request.setAttribute("redirectUrl", redirectUrl);
				return "message";
			}
			LOGGER.info("上传的文件类型:"+uploadFileContentType+",   application/vnd.ms-excel  --  aplication/msexcel");
			try {
				fis = new FileInputStream(uploadFile);
				if("xls".equals(ext)){
					wb = new HSSFWorkbook(fis);
				}else{
					wb = new XSSFWorkbook(fis);
				}
				//取得第一个工作表
				Sheet sheet=wb.getSheetAt(0);
				int firseRowNum=0;
				//第一行为标题
				Row hssfRow=sheet.getRow(firseRowNum);
				int lastCellNum=hssfRow.getLastCellNum();
				//是否包含走秀码列
				int containsXiuCode=0;
				for(int cellnum=0 ;cellnum<lastCellNum;cellnum++ ){
					Cell cell= hssfRow.getCell(cellnum);
					if(cell!=null){
						String title=cell.getStringCellValue();
						if(title!=null && !title.isEmpty()){
							if(title.trim().equalsIgnoreCase("走秀码")){
								containsXiuCode++;
							}
						}
					}
				}
				if(containsXiuCode==1){
					firseRowNum++;
					int lastRowNum=sheet.getLastRowNum();
					String userName="";
					LocalOperatorsDO localOperatorsDO=getLoginUser();
					if(localOperatorsDO!=null){
						userName=localOperatorsDO.getLoginName();
					}
					if(lastRowNum>=1){
							List<OnLineBlackProductBean> blackProductBeans=new ArrayList<OnLineBlackProductBean>();
							//保存商品的文件描述信息
							for(int rowIndex=firseRowNum;rowIndex<=lastRowNum;rowIndex++){
								Row row=sheet.getRow(rowIndex);
								if(null==row){
									continue;
								}
								Cell cell=row.getCell(0);
								if(cell!=null){
									String xiuCode =getCellValue(cell,"xiuCode");
									if(xiuCode==null || xiuCode.isEmpty()){
										continue;
									}
									if(xiuCode.length()<=15){
										//parames.put("xiuCode", xiuCode.trim());
										//parames.put("isDelete", 1); //没被删除的
										boolean flag=onLineBlackProductServiceBean.xiuCodeIsExits(xiuCode.trim());
										if(!flag){//本地不存在，才保存
											OnLineBlackProductBean blackProductBean=new OnLineBlackProductBean();
											blackProductBean.setXiuCode(xiuCode.trim());
											blackProductBean.setImportUserName(userName);
											blackProductBean.setConfirmUserName(userName);
											blackProductBeans.add(blackProductBean);
										}else{
											//本地存在
											Map<String, Object> parames=new HashMap<String, Object>();
											parames.put("xiuCode", xiuCode.trim());
											parames.put("confirmStatus", 1);//已确认
											parames.put("confirmDate", "confirmDate");//确认时间
											parames.put("createDate", "createDate");//创建时间;
											parames.put("importUserName",userName);
											parames.put("isButtJoint", 1);//1,未对接
											parames.put("isDelete", 1); //没被删除的
											onLineBlackProductServiceBean.updateOnLineBlackProductBean(parames);
										}
									
									}else{
										LOGGER.error("用户"+userName+",导入黑名单走秀码"+xiuCode+",长度不符合走秀码的规则");
									}
									if(blackProductBeans!=null && blackProductBeans.size()>=30){
										//批量保存走秀码(是不需要同步商品上下架到京东的走秀商品走秀码)
										onLineBlackProductServiceBean.insertBlackProductBeans(blackProductBeans);
										blackProductBeans=new ArrayList<OnLineBlackProductBean>();
									}
								}
							}
							if(blackProductBeans!=null){
								//批量保存走秀码(是不需要同步商品上下架到京东的走秀商品走秀码)
								onLineBlackProductServiceBean.insertBlackProductBeans(blackProductBeans);
							}
							request.setAttribute("message", "导入上下架黑名单商品成功");
					}else{
						request.setAttribute("message", "没有需要解析的商品走秀码");
					}
				}else{
					request.setAttribute("message", "Excle模板必须包含走秀码列,文件内容格式不正确");
					redirectUrl = request.getContextPath()+"/jdWare/exportExcelBlackUI.action";
					request.setAttribute("redirectUrl",redirectUrl );
					return "message";
				}
			} catch (Exception e) {
				request.setAttribute("message", "导入上下架黑名单商品异常:"+e.getMessage());
				redirectUrl = request.getContextPath()+"/jdWare/exportExcelBlackUI.action";
				request.setAttribute("redirectUrl",redirectUrl );
				LOGGER.error(e);
				e.printStackTrace();
				return "message";
			}
		}else{
			request.setAttribute("message", "请上传Excle文件");
		}
		redirectUrl = request.getContextPath()+"/jdWare/exportExcelBlackUI.action";
		request.setAttribute("redirectUrl",redirectUrl );
		return "message";
   }
   
   /**
    * 上下架不对接黑名单商品列表界面
    * @return
    */
   public String blackProductListUI(){
	   return SUCCESS;
	   
   }
   
   /**
    * 上下架不对接黑名单商品列表动作
    * @return
    */
   public String blackProductList(){
	   String redirectUrl= request.getContextPath()+"/jdWare/blackProductListUI.action"; 
	   try{
		   if(productForm==null){
			   productForm=new BlackProductForm();
		   }
		   Map<String, Object> parames=productForm.getBlackProductForm();
		 //走秀码
			String xiuCodes[]=null;
			String xiuCode=productForm.getXiuCode();
			if(xiuCode!=null && !"".equals(xiuCode.trim()) && !xiuCode.isEmpty()){
				xiuCodes=xiuCode.split("\r\n");
			}
			if(xiuCodes!=null && xiuCodes.length>0){
				int xiuCodeLen=xiuCodes.length;
				if(xiuCodeLen>200){
				    request.setAttribute("redirectUrl", redirectUrl);
				    request.setAttribute("message", "输入走秀商品编码个数不能超过"+200);
					return "message";
				}
				parames.put("xiuCodes",xiuCodes);
			}
			
		   pageView= new PageView<OnLineBlackProductBean>(this.getPageSize(), this.getCurrentPage());
		   parames.put("firstNum", pageView.getFirstResult());
		   parames.put("lastNum", pageView.getMaxresult());
		   
		   parames.put("isDelete", 1);//没被删除
		   QueryResult<OnLineBlackProductBean> qr= onLineBlackProductServiceBean.getPageResule(parames);
		   pageView.setQueryResult(qr);
		   
	   }catch (Exception e) {
		    LOGGER.error("上下架不对接商品列表异常"+e);
		    e.printStackTrace();
		    request.setAttribute("message", "上下架不对接商品列表异常:"+e.getMessage());
			request.setAttribute("redirectUrl",redirectUrl );
			return "message";
	   }
	   return SUCCESS;
   }
   
   /**
    * 设置不对接商品上下架(单个或批量)
    * @return
    */
   public String setOnLine(){
	   String redirectUrl= request.getContextPath()+"/jdWare/blackProductListUI.action";
	   int count=0;
	   try{
		   Map<String,Object> parames =new HashMap<String,Object>();
		   parames.put("confirmStatus", 1);//确认不对接商品上下架到京东，1：表示已确认
		   parames.put("confirmDate", "confirmDate");//确实时的时间
		   parames.put("isButtJoint", 1);//上下架对接状态,默认为1,未对接,2:已对接(走秀商品上下架是否与京东对接)
		   
		   LocalOperatorsDO localOperatorsDO=getLoginUser();
		   String userName="";
			if(localOperatorsDO!=null){
				userName=localOperatorsDO.getLoginName();
			}
			parames.put("confirmUserName", userName);//被确认的用户
	     if(id!=null && !"".equals(id.trim())){
	    	//单个
	    	List<String> recordIds=new ArrayList<String>();
	    	recordIds.add(id);
	    	parames.put("ids", recordIds);
	        count=onLineBlackProductServiceBean.updateOnLineBlackProductBean(parames);
	    	LOGGER.info("设置不对接商品上下架,影响的记录数:"+count+",用户"+userName+",设置不对接商品上下架,商品ID="+id);  
	    }else if(ids!=null && ids.size()>0){
	    	//批量
	    	parames.put("ids", this.ids);
	    	count=onLineBlackProductServiceBean.updateOnLineBlackProductBean(parames);
	    	LOGGER.info("设置不对接商品上下架,影响的记录数:"+count+",用户"+userName+",设置不对接商品上下架,商品IDs="+this.ids);  
	    }
		   
	   }catch (Exception e) {
		    LOGGER.error("设置不对接商品上下架异常"+e);
		    e.printStackTrace();
		    request.setAttribute("message", "设置不对接商品上下架异常:"+e.getMessage());
			request.setAttribute("redirectUrl",redirectUrl );
			return "message";
	   }
	   return SUCCESS;
   }
  
   /**
    * 设置商品为删除状态(单个或批量)
    * @return
    */
   public String deleteProduct(){
	   String redirectUrl= request.getContextPath()+"/jdWare/blackProductListUI.action";
	   int count=0;
	   try{
		   Map<String,Object> parames =new HashMap<String,Object>();
		   parames.put("isDelete", 0);//被删除的标记
		  
		   LocalOperatorsDO localOperatorsDO=getLoginUser();
		   String userName="";
			if(localOperatorsDO!=null){
				userName=localOperatorsDO.getLoginName();
			}
			parames.put("userName", userName);//被删除的用户是
		   if(id!=null && !"".equals(id.trim())){
		    	//单个
				List<String> recordIds=new ArrayList<String>();
		    	recordIds.add(id);
		    	parames.put("ids", recordIds);
		        count=onLineBlackProductServiceBean.updateOnLineBlackProductBean(parames);
		        LOGGER.info("设置删除商品,影响的记录数:"+count+",用户"+userName+",设置不对接商品上下架,商品ID="+id);   
		    }else if(ids!=null && ids.size()>0){
		    	//批量
		    	parames.put("ids", this.ids);
		    	count=onLineBlackProductServiceBean.updateOnLineBlackProductBean(parames);
		    	 LOGGER.info("设置删除商品,影响的记录数:"+count+",用户"+userName+",设置不对接商品上下架,商品IDs="+ids); 
		    }
	 
	   }catch (Exception e) {
		    LOGGER.error("删除商品异常"+e);
		    e.printStackTrace();
		    request.setAttribute("message", "删除商品:"+e.getMessage());
			request.setAttribute("redirectUrl",redirectUrl );
			return "message";
	   }
	   return SUCCESS;
   }
   
   
   /**
    * //导出黑名单商品
    * 走秀码 	商品名称 	京东上架状态 	上下架对接状态 	确认状态 	上次确认时间 	创建时间 	操作人
    * @return
    */
   public String exportBlackProduct(){
		long startTime=System.currentTimeMillis();
		  String redirectUrl= request.getContextPath()+"/jdWare/blackProductListUI.action";
		try {
			 if(productForm==null){
				   productForm=new BlackProductForm();
			   }
			   Map<String, Object> parames=productForm.getBlackProductForm();
			 //走秀码
				String xiuCodes[]=null;
				String xiuCode=productForm.getXiuCode();
				if(xiuCode!=null && !"".equals(xiuCode.trim()) && !xiuCode.isEmpty()){
					xiuCodes=xiuCode.split("\r\n");
				}
				if(xiuCodes!=null && xiuCodes.length>0){
					int xiuCodeLen=xiuCodes.length;
					if(xiuCodeLen>200){
					    request.setAttribute("redirectUrl", redirectUrl);
					    request.setAttribute("message", "输入走秀商品编码个数不能超过"+200);
						return "message";
					}
					parames.put("xiuCodes",xiuCodes);
				}
				parames.put("firstNum", "1");
				int pageSize =2000;
				parames.put("lastNum", pageSize);
				parames.put("isDelete", 1);//没被删除
				
				String templatePath = ServletActionContext.getServletContext().getRealPath("") + "/template";
				HSSFWorkbook wb = ImportExcelUtil.getWorkbook(templatePath + "/blackProduct_template.xls");
				int  maxSheetCount=50000;//每个Excle工作表最大写入记录数
				
				if(wb!=null){
					QueryResult<OnLineBlackProductBean> qr= onLineBlackProductServiceBean.getPageResule(parames);
					if(qr!=null){
						long total=qr.getTotalrecord();
						int totoalSheetCount=getTotalPage(maxSheetCount,(int) total);//需要总的Excle工作表才能装完查询的数据
						
						int totalPage = getTotalPage(pageSize, (int) total);
						LOGGER.info("总页数为:" + totalPage);
						int currentPage = 1;
						for (int sheetCount=0;sheetCount<=totoalSheetCount-1;sheetCount++){
							int rowIndex=0;
							if(rowIndex>=maxSheetCount){
								LOGGER.info("EXCLE 索引为:"+rowIndex+",Excle中对多有"+maxSheetCount+"行数据");
								rowIndex=0;
							}
							HSSFSheet sheet = wb.getSheetAt(sheetCount);
							if(sheet==null){
								sheet=wb.createSheet("商品黑名单信息"+sheetCount);
							}
							if(sheet==null){
								break;
							}
							for (; currentPage <= totalPage; currentPage++) {
								if (currentPage != 1) {
									//parames = new HashMap<String, Object>();
									parames.put("firstNum", pageSize * (currentPage - 1) + 1); 
									parames.put("lastNum", pageSize * currentPage );
									//LOGGER.info("firstNum:" +  (pageSize * (currentPage - 1) + 1));
									//LOGGER.info("lastNum:" +  (pageSize * currentPage));
									qr= onLineBlackProductServiceBean.getPageResule(parames);
								}
								if(qr!=null){
									List<OnLineBlackProductBean> productBeans=	qr.getResultlist();
									if(productBeans!=null && productBeans.size()>0){
										rowIndex=	jDWareServiceBean.writeDataToExcel(sheet,rowIndex,productBeans);
										if(rowIndex>=maxSheetCount){
											currentPage=currentPage+1;
											break;
										}
										LOGGER.info("rowIndex===========>"+rowIndex);
									}
								}
								
							}
							
						}
						
					}
					String fileName = "blackProduct_template_" + CommonUtil.getNowTime() + ".xls";
					ExportExcelUtil.downloadExcel(wb, fileName);
				}
			
		}catch(Exception e){
			LOGGER.error("导出商品黑名单异常");
			e.printStackTrace();
		    request.setAttribute("redirectUrl", redirectUrl);
		    request.setAttribute("message", "导出商品黑名单异常"+e);
			return "message";
		}
		
		long endTime=System.currentTimeMillis();
		LOGGER.info("经历的时间为:"+ ((endTime-startTime)/1000)+"秒" );
	   return null;
   }
   
	private static List<String> getTitleValue(){
		List<String> value=new ArrayList<String>();
		for(Map.Entry<String, String> t:title.entrySet()){
			value.add(t.getValue());
		}
		return value;
	}
	   /**
	    * 获取Excel单元格中的数据
	    * @param cell  单元格对象
	    * @param tag   单元格标志
	    * @return
	    */
		private static String getCellValue(Cell cell,String tag){
			String value = null;
			switch (cell.getCellType()) {		 
		        case HSSFCell.CELL_TYPE_NUMERIC: // 数字           
		        	if(tag=="xiuCode"){
		        		value=""+((int)cell.getNumericCellValue());
		        	}
		            break;  
		        case HSSFCell.CELL_TYPE_STRING: // 字符串             
		            value=cell.getStringCellValue();
		            break;  
		        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean  	         
		            break;  
		        case HSSFCell.CELL_TYPE_FORMULA: // 公式  	         
		            break;  
		        case HSSFCell.CELL_TYPE_BLANK: // 空值  
		        	value=null;
		            break;  
		        case HSSFCell.CELL_TYPE_ERROR: // 故障  
		        	value=null;
		            break;  
		        default:  
		        	value=null;
		            break;  
		        }  		
			return value;
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

	public PageView<OnLineBlackProductBean> getPageView() {
		return pageView;
	}

	public void setPageView(PageView<OnLineBlackProductBean> pageView) {
		this.pageView = pageView;
	}

	public BlackProductForm getProductForm() {
		return productForm;
	}

	public void setProductForm(BlackProductForm productForm) {
		this.productForm = productForm;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
	
	
}
