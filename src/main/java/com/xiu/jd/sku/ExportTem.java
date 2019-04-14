package com.xiu.jd.sku;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.stereotype.Component;



import com.xiu.jd.bean.order.JDOrderItemInfo;
import com.xiu.jd.bean.ware.JDAttributeValue;
import com.xiu.jd.bean.ware.JDOrderTrack;
import com.xiu.jd.bean.ware.JDSku;
import com.xiu.jd.bean.ware.JDWareSkuBrand;
import com.xiu.jd.bean.ware.OnLineBlackProductBean;
import com.xiu.jd.utils.ExportExcelUtil;

/**
 * 导出excel模板类
 * @author Administrator
 *
 */
@Component("exportTem")
public class ExportTem {
	public void createJDAttrWb(HSSFWorkbook wb,Map<String,List<JDAttributeValue>> map,String jdCid){
		HSSFSheet sheet = wb.createSheet("JDAttribute");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cellJdCid = row.createCell(0);
		cellJdCid.setCellValue("京东分类ID");
		cellJdCid = row.createCell(1);
		cellJdCid.setCellStyle(getTextStyle(wb));
		cellJdCid.setCellValue(jdCid);
		cellJdCid.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellJdCid = row.createCell(4);
		cellJdCid.setCellValue("*京东类目下的属性与属性值对应报表，其中红色的属性为必填项");
		HSSFRow rowWare = sheet.createRow(1);
		Iterator it=map.entrySet().iterator();
		int i=0;
		int y=0;
		int multi = 0;
		while(it.hasNext()){
			Map.Entry<String,List<JDAttributeValue>> entity = (Map.Entry<String,List<JDAttributeValue>>)it.next();
			String[] attrs = entity.getKey().split("==");
			if(attrs[2].equals("1")){
				for(int m = 1;m<11;m++){
					if(sheet.getRow(m)!=null){
						rowWare = sheet.getRow(m);
					}else{
						rowWare = sheet.createRow(m);
					}
					//创建商品走秀码单元格
					HSSFCell cellWare = rowWare.createCell(0);
					cellWare.setCellStyle(getFontStyle(wb));
					cellWare.setCellValue("商品走秀码");
					//创建商品长度单元格
					cellWare = rowWare.createCell(2);
					cellWare.setCellStyle(getFontStyle(wb));
					cellWare.setCellValue("商品长度");
					
					//创建商品宽度单元格
					cellWare = rowWare.createCell(4);
					cellWare.setCellStyle(getFontStyle(wb));
					cellWare.setCellValue("商品宽度");
					
					//创建商品高度单元格
					cellWare = rowWare.createCell(6);
					cellWare.setCellStyle(getFontStyle(wb));
					cellWare.setCellValue("商品高度");
					
					//创建商品重量单元格
					cellWare = rowWare.createCell(8);
					cellWare.setCellStyle(getFontStyle(wb));
					cellWare.setCellValue("商品重量");
					HSSFCell cell = rowWare.createCell(10+i);
					if(attrs[1].equals("true")){//该属性为必填属性
						cell.setCellStyle(getFontStyle(wb));
						cell.setCellValue(attrs[0]);
					}else{//该属性为非必填属性
						cell.setCellValue(attrs[0]);
					}
					
					List<String> lists = new ArrayList<String>();
					for (JDAttributeValue att : entity.getValue()) {
						lists.add(att.getName());
					}
					cell = rowWare.createCell(11+i);
					cell.setCellValue("请选择");
					HSSFSheet hidden = wb.createSheet("hidden"+y);
					for(int j=0;j<entity.getValue().size();j++){
						 String name = lists.get(j); 
						 HSSFRow rowc = hidden.createRow(j+m);
						 HSSFCell cellc = rowc.createCell(i+11);
						 cellc.setCellValue(name);
					}
					
					HSSFName name = wb.createName();
					name.setNameName("hidden"+y);
					name.setRefersToFormula("hidden"+y+"!A1:A1"+lists.size());
					DVConstraint constraint = DVConstraint.createFormulaListConstraint("hidden"+y);
					//wb.setSheetHidden(i, true);
					CellRangeAddressList regions = new CellRangeAddressList(m,m,i+11,i+11);
					HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint); 
					sheet.addValidationData(data_validation); 
					y++;
				}
				i += 2;
			}else if(attrs[2].equals("2")){
				if(sheet.getRow(13)!= null){
					rowWare = sheet.getRow(13);
				}else{
					rowWare = sheet.createRow(13);
				}
				
				HSSFCell cell = rowWare.createCell(multi);
				if(attrs[1].equals("true")){//该属性为必填属性
					cell.setCellStyle(getFontStyle(wb));
					cell.setCellValue(attrs[0]);
				}else{//该属性为非必填属性
					cell.setCellValue(attrs[0]);
				}
				
				for (int m = 0; m < entity.getValue().size(); m++)
				{
					if(sheet.getRow(14+m) == null){
						rowWare = sheet.createRow(14+m);
					}else{
						rowWare = sheet.getRow(14+m);
					}
					cell = rowWare.createCell(multi+1);
					cell.setCellValue(entity.getValue().get(m).getName());
				}
				multi += 2;
			}
		}
		
		int tt = 0;
		Iterator its=map.entrySet().iterator();
		while(its.hasNext()){
			Map.Entry<String,List<JDAttributeValue>> entity1 = (Map.Entry<String,List<JDAttributeValue>>)its.next();
			String[] attrs1 = entity1.getKey().split("==");
			if(attrs1[2].equals("2")){
				for(int n=1;n<11;n++){
					if(sheet.getRow(n)!= null){
						rowWare = sheet.getRow(n);
					}else{
						rowWare = sheet.createRow(n);
					}
					
					HSSFCell cell = rowWare.createCell(tt+i+10);
					if(attrs1[1].equals("true")){//该属性为必填属性
						cell.setCellStyle(getFontStyle(wb));
						cell.setCellValue(attrs1[0]);
					}else{//该属性为非必填属性
						cell.setCellValue(attrs1[0]);
					}
				}
				tt+=2;
			}
		}
	}
	
	public void createSkuWb(HSSFWorkbook wb,String jdCid,List<JDSku> skus,List<String> colorList,List<String> sizeList){
		HSSFSheet sheet = wb.createSheet("skuinfo");
		HSSFRow row = sheet.createRow(0);
		sheet.protectSheet("加密");
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("京东类目ID");
//		HSSFCellStyle style = wb.createCellStyle();
//		style.setLocked(false);
		cell = row.createCell(1);
		cell.setCellStyle(getTextStyle(wb));
		cell.setCellValue(jdCid);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("SKU码");
		cell = row.createCell(1);
		cell.setCellValue("商品走秀码");
		cell = row.createCell(2);
		cell.setCellValue("SKU库存");
		cell = row.createCell(3);
		cell.setCellValue("颜色");
		cell = row.createCell(4);
		cell.setCellValue("尺寸");
		cell = row.createCell(5);
		cell.setCellValue("走秀颜色");
		cell = row.createCell(6);
		cell.setCellValue("走秀尺寸");
		for (int i = 0; i < skus.size(); i++) {
			row = sheet.createRow(i+2);
			cell = row.createCell(0);
			cell.setCellStyle(getTextStyle(wb));
			cell.setCellValue(skus.get(i).getSkusn());
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell = row.createCell(1);
			cell.setCellStyle(getTextStyle(wb));
			cell.setCellValue(skus.get(i).getXiucode());
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell = row.createCell(2);
			cell.setCellStyle(getTextStyle(wb));
			cell.setCellValue(skus.get(i).getStocknum());
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell = row.createCell(3);
			cell.setCellStyle(getUnLockStyle(wb));
			cell.setCellValue("请选择");
			CellRangeAddressList colorRegions = new CellRangeAddressList(i+2,i+2,3,3);
			DVConstraint colorConstraint = DVConstraint.createExplicitListConstraint(colorList.toArray(new String[]{}));
			HSSFDataValidation colorValidation = new HSSFDataValidation(colorRegions,colorConstraint); 
			sheet.addValidationData(colorValidation);
			cell = row.createCell(4);
			cell.setCellStyle(getUnLockStyle(wb));
			cell.setCellValue("请选择");
			CellRangeAddressList sizeRegions = new CellRangeAddressList(i+2,i+2,4,4);
			DVConstraint sizeConstraint = DVConstraint.createExplicitListConstraint(sizeList.toArray(new String[]{}));
			HSSFDataValidation sizeValidation = new HSSFDataValidation(sizeRegions,sizeConstraint); 
			sheet.addValidationData(sizeValidation);
			cell = row.createCell(5);
			cell.setCellValue(skus.get(i).getColorname());
			cell = row.createCell(6);
			cell.setCellValue(skus.get(i).getSizevalue());
			
		}
		 sheet.autoSizeColumn(( short ) 0 );
		 sheet.autoSizeColumn(( short ) 1 );
	}
	
	/**导出订单**/
	public void createOrderWb(HSSFWorkbook wb, List<JDOrderTrack> lists){
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		int i = 1;
		for (JDOrderTrack order : lists) {
			row = sheet.createRow(i);
			//京东订单ID
			if(order.getJdOrderId()!=null && !"".equals(order.getJdOrderId())){
				ExportExcelUtil.createCell(row, 0, order.getJdOrderId(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 0, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			//本地订单ID
			if(order.getLocalOrderId()!=null && !"".equals(order.getLocalOrderId())){
				ExportExcelUtil.createCell(row, 1, order.getLocalOrderId(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 1, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			//京东订单下单时间
			if(order.getPlaceTime()!=null && !"".equals(order.getPlaceTime())){
				ExportExcelUtil.createCell(row, 2, order.getPlaceTime(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 2, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			//京东订单推送给走秀OSC结果
			if(order.getPlaceResult()!=null && !"".equals(order.getPlaceResult())){
				if("0".equals(order.getPlaceResult())){
					ExportExcelUtil.createCell(row, 3, "未推送",ExportExcelUtil.getBorderCellStyle(wb));
				}else if("1".equals(order.getPlaceResult())){
					ExportExcelUtil.createCell(row, 3, "推送成功",ExportExcelUtil.getBorderCellStyle(wb));
				}else if("2".equals(order.getPlaceResult())){
					ExportExcelUtil.createCell(row, 3, "推送失败",ExportExcelUtil.getBorderCellStyle(wb));
				}else if("3".equals(order.getPlaceResult())){
					ExportExcelUtil.createCell(row, 3, "库存扣减失败",ExportExcelUtil.getBorderCellStyle(wb));
				}
			}else{
				ExportExcelUtil.createCell(row, 3, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			//订单信息推送给京东结果
			if(order.getIsSyncPlace()!=null && !"".equals(order.getIsSyncPlace())){
				if("0".equals(order.getIsSyncPlace())){
					ExportExcelUtil.createCell(row, 4, "未推送包裹给京东",ExportExcelUtil.getBorderCellStyle(wb));
				}else if("1".equals(order.getIsSyncPlace())){
					ExportExcelUtil.createCell(row, 4, "推送包裹给京东成功",ExportExcelUtil.getBorderCellStyle(wb));
				}else if("2".equals(order.getIsSyncPlace())){
					ExportExcelUtil.createCell(row, 4, "推送包裹给京东失败",ExportExcelUtil.getBorderCellStyle(wb));
				}
			}else{
				ExportExcelUtil.createCell(row, 4, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			//本地订单号
			if(order.getOrderCode()!=null && !"".equals(order.getOrderCode())){
				ExportExcelUtil.createCell(row, 5, order.getOrderCode(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 5, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			//失败原因
			if(order.getFailDescri()!=null && !"".equals(order.getFailDescri())){
				ExportExcelUtil.createCell(row, 6, order.getFailDescri(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 6, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			
			i++;
		}
	}
	
	/**
	 * 设置单元格的字体颜色为红色
	 * @param wb
	 * @return
	 */
	public HSSFCellStyle getFontStyle(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.RED.index);
		style.setFont(font);
		return style;
	}
	
	/**
	 * 设置单元格为文本格式
	 */
	public HSSFCellStyle getTextStyle(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		HSSFDataFormat format = wb.createDataFormat();
		style.setDataFormat(format.getFormat("@")); 
		return style;
		
	}
	
	/**
	 * 设置单元格为可编辑状态
	 */
	public HSSFCellStyle getUnLockStyle(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		style.setLocked(false);
		return style;
	}

	/**
	 * 导出订单项
	 * @param wb
	 * @param jdOrderItemInfos
	 */
	public void createOrderItemWb(HSSFWorkbook wb,
			List<JDOrderItemInfo> jdOrderItemInfos) {

		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		int i = 1;
		for (JDOrderItemInfo item : jdOrderItemInfos) {
			row = sheet.createRow(i);
			//京东订单ID
			if(item.getJdOrderId()!=null && !"".equals(item.getJdOrderId())){
				ExportExcelUtil.createCell(row, 0, item.getJdOrderId(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 0, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			//走秀订单ID
			if(item.getLocalOrderId()!=null && !"".equals(item.getLocalOrderId())){
				ExportExcelUtil.createCell(row, 1, item.getLocalOrderId(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 1, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			
			//走秀订单号
			if(item.getOrderCode()!=null && !"".equals(item.getOrderCode())){
				ExportExcelUtil.createCell(row, 2, item.getOrderCode(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 2, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			
			//下单时间
			if(item.getPlaceTime()!=null && !"".equals(item.getPlaceTime())){
				ExportExcelUtil.createCell(row, 3, item.getPlaceTime(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 3, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			
			//商品名称
			if(item.getSkuName()!=null && !"".equals(item.getSkuName())){
				ExportExcelUtil.createCell(row, 4, item.getSkuName(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 4, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			
			//商品SKU码
			if(item.getOuterSkuId()!=null && !"".equals(item.getOuterSkuId())){
				ExportExcelUtil.createCell(row, 5, item.getOuterSkuId(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 5, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			
			//商品单价
			if(item.getJdPrice()!=null && !"".equals(item.getJdPrice())){
				ExportExcelUtil.createCell(row, 6, item.getJdPrice(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 6, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			//购买数量
			if(item.getItemTotal()!=null && !"".equals(item.getItemTotal())){
				ExportExcelUtil.createCell(row, 7, item.getItemTotal(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 7, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
			
			//下单失败原因
			if(item.getFailDescri()!=null && !"".equals(item.getFailDescri())){
				ExportExcelUtil.createCell(row, 8, item.getFailDescri(),ExportExcelUtil.getBorderCellStyle(wb));
			}else{
				ExportExcelUtil.createCell(row, 8, "",ExportExcelUtil.getBorderCellStyle(wb));
			}
	
			
			i++;
		}
	
		
	}
	public int writeDataToExcel(HSSFSheet sheet,int rowIndex,
			List<OnLineBlackProductBean> productBeans) {
		int index =0;
		 rowIndex=rowIndex+1;//从Excel第二行开始写入数据
		for (;index<productBeans.size();index++) {
			HSSFRow row = sheet.createRow(rowIndex);
			OnLineBlackProductBean blackProductBean=productBeans.get(index);
			if(blackProductBean==null){
			   continue;
			}
			//走秀码
			String xiuCode=blackProductBean.getXiuCode();
			if(xiuCode!= null && !"".equals(xiuCode)){
				ExportExcelUtil.createCell(row, 0, xiuCode);
			}else{
				ExportExcelUtil.createCell(row, 0, "");
			}
			//商品名称
			String title=blackProductBean.getTitle();
			if(title!= null && !"".equals(title)){
				ExportExcelUtil.createCell(row, 1, title);
			}else{
				ExportExcelUtil.createCell(row, 1, "");
			}
			//京东上下架状态(商品销售状态：0:未上架,1:在售,2:下架)
			Integer onLineStatus=blackProductBean.getOnLineStatus();
			if(onLineStatus==null){
				ExportExcelUtil.createCell(row, 2, "未定义");
			}
			else if(onLineStatus==0){
				ExportExcelUtil.createCell(row, 2, "未上架");
			}else if(onLineStatus==1){
				ExportExcelUtil.createCell(row, 2, "在售");
			}else if(onLineStatus==2){
				ExportExcelUtil.createCell(row, 2, "下架");
			}
			//上下架对接状态,默认为1,未对接,2:已对接(走秀商品上下架是否与京东对接)
			
			Integer isButtJoint=blackProductBean.getIsButtJoint();
			if(isButtJoint==null){
				ExportExcelUtil.createCell(row, 3, "未定义");
			}
			else if(isButtJoint==1){
				ExportExcelUtil.createCell(row, 3, "未对接");
			}else{
				ExportExcelUtil.createCell(row, 3, "已对接");
			}
			//确认状态:1:已确认,2:待确认,3:已过期(第一次导入确认状态为:1已确认是对未对接的确认)
			Integer confirmStatus= blackProductBean.getConfirmStatus();
			
			if(confirmStatus==null){
				ExportExcelUtil.createCell(row, 2, "未定义");
			}
			else if(confirmStatus==1){
				ExportExcelUtil.createCell(row, 4, "已确认");
			}else if(confirmStatus==2){
				ExportExcelUtil.createCell(row, 4, "待确认");
			}else if(confirmStatus==3){
				ExportExcelUtil.createCell(row, 4, "已过期");
			}
			//确认时间
			String confirmDate=blackProductBean.getConfirmDate();
			if(confirmDate!= null && !"".equals(confirmDate)){
				ExportExcelUtil.createCell(row, 5, confirmDate);
			}else{
				ExportExcelUtil.createCell(row, 5, "");
			}
			//创建时间
			String createDate=blackProductBean.getCreateDate();
			if(createDate!= null && !"".equals(createDate)){
				ExportExcelUtil.createCell(row, 6, createDate);
			}else{
				ExportExcelUtil.createCell(row, 6, "");
			}
			//操作人
			String importUserName= blackProductBean.getImportUserName();
			if(importUserName!= null && !"".equals(importUserName)){
				ExportExcelUtil.createCell(row, 7, importUserName);
			}else{
				ExportExcelUtil.createCell(row, 7, "");
			}
			
			rowIndex++;
		}
		rowIndex=rowIndex-1;
		return rowIndex;
	}
	public void createWareSkuBrand(HSSFWorkbook wb, List<JDWareSkuBrand> lists) {
		if(lists!=null && lists.size()>0){
			int execleTotalSize=lists.size();
			int line=65000;
			int index =0;
			int wordTableTaotal=execleTotalSize/line;
			for(int count=0;count<=wordTableTaotal;count++){
				HSSFSheet sheet = wb.getSheetAt(count);
				int i=1;
				//for (JDWareSkuBrand jsb : lists) {
				for (;index<execleTotalSize;index++) {
					HSSFRow row = sheet.createRow(i);
					JDWareSkuBrand jsb=lists.get(index);
					if(jsb==null){
						continue;
					}
					//走秀SKU编码
					if(jsb.getSkuCode() != null && !"".equals(jsb.getSkuCode())){
						ExportExcelUtil.createCell(row, 0, jsb.getSkuCode());
					}else{
						ExportExcelUtil.createCell(row, 0, "");
					}
					//京东商品SKU编码
					if(jsb.getJdSkuId() != null && !"".equals(jsb.getJdSkuId())){
						ExportExcelUtil.createCell(row, 1, jsb.getJdSkuId());
					}else{
						ExportExcelUtil.createCell(row, 1, "");
					}
					//走秀码
					if(jsb.getXiuCode() != null && !"".equals(jsb.getXiuCode())){
						ExportExcelUtil.createCell(row, 2, jsb.getXiuCode());
					}else{
						ExportExcelUtil.createCell(row, 2, "");
					}
					//京东商品ID
					if(jsb.getJdWareId() != null && !"".equals(jsb.getJdWareId())){
						ExportExcelUtil.createCell(row, 3, jsb.getJdWareId());
					}else{
						ExportExcelUtil.createCell(row, 3, "");
					}
					//sku库存量
					if(jsb.getSkuStock() != null && !"".equals(jsb.getSkuStock())){
						ExportExcelUtil.createCell(row, 4, jsb.getSkuStock());
					}else{
						ExportExcelUtil.createCell(row, 4, "");
					}
					//京东价
					if(jsb.getJdPrice() != null && !"".equals(jsb.getJdPrice())){
						ExportExcelUtil.createCell(row, 5, jsb.getJdPrice());
					}else{
						ExportExcelUtil.createCell(row, 5, "");
					}
					//走秀价
					if(jsb.getCostPrice() != null && !"".equals(jsb.getCostPrice())){
						ExportExcelUtil.createCell(row, 6, jsb.getCostPrice());
					}else{
						ExportExcelUtil.createCell(row, 6, "");
					}
					//市场价
					if(jsb.getMarketPrice() != null && !"".equals(jsb.getMarketPrice())){
						ExportExcelUtil.createCell(row, 7, jsb.getMarketPrice());
					}else{
						ExportExcelUtil.createCell(row, 7, "");
					}
				
					//品牌
					if(jsb.getXiuBrand()!= null && !"".equals(jsb.getXiuBrand())){
						ExportExcelUtil.createCell(row, 8, jsb.getXiuBrand());
					}else{
						ExportExcelUtil.createCell(row, 8, "");
					}
					//商品名
					if(jsb.getJdWareName()!= null && !"".equals(jsb.getJdWareName())){
						ExportExcelUtil.createCell(row, 9, jsb.getJdWareName());
					}else{
						ExportExcelUtil.createCell(row, 9, "");
					}
					//商品供应商编码
					if(jsb.getSupplierCode()!= null && !"".equals(jsb.getSupplierCode())){
						ExportExcelUtil.createCell(row, 10, jsb.getSupplierCode());
					}else{
						ExportExcelUtil.createCell(row, 10, "");
					}
					//京东一级分类
					if(jsb.getJdFirCname()!= null && !"".equals(jsb.getJdFirCname())){
						ExportExcelUtil.createCell(row, 11, jsb.getJdFirCname());
					}else{
						ExportExcelUtil.createCell(row, 11, "");
					}
					//京东二级分类
					if(jsb.getJdSecCname()!= null && !"".equals(jsb.getJdSecCname())){
						ExportExcelUtil.createCell(row, 12, jsb.getJdSecCname());
					}else{
						ExportExcelUtil.createCell(row, 12, "");
					}
					//京东三级分类
					if(jsb.getJdThiCname()!= null && !"".equals(jsb.getJdThiCname())){
						ExportExcelUtil.createCell(row, 13, jsb.getJdThiCname());
					}else{
						ExportExcelUtil.createCell(row, 13, "");
					}
					//走秀基础分类
					if(jsb.getXiuSuperName()!= null && !"".equals(jsb.getXiuSuperName())){
						ExportExcelUtil.createCell(row, 14, jsb.getXiuSuperName());
					}else{
						ExportExcelUtil.createCell(row, 14, "");
					}
					//走秀管理分类
					if(jsb.getXiuFamliyName()!= null && !"".equals(jsb.getXiuFamliyName())){
						ExportExcelUtil.createCell(row, 15, jsb.getXiuFamliyName());
					}else{
						ExportExcelUtil.createCell(row, 15, "");
					}
					//走秀三级分类
					if(jsb.getXiuChildName()!= null && !"".equals(jsb.getXiuChildName())){
						ExportExcelUtil.createCell(row, 16, jsb.getXiuChildName());
					}else{
						ExportExcelUtil.createCell(row, 16, "");
					}
					//销售状态
					if(jsb.getOnLineStatus()==0){
						ExportExcelUtil.createCell(row, 17, "未上架");
					}else if(jsb.getOnLineStatus()==1){
						ExportExcelUtil.createCell(row, 17, "在售");
					}else if(jsb.getOnLineStatus()==2){
						ExportExcelUtil.createCell(row, 17, "下架");
					}
						
					i++;
					if(line<=i){
						index++;
						break;
					}
				} 
			}
			
		}
		
	
	}
	
	
	
	public void createWareSkuBrandPage(HSSFWorkbook wb, List<JDWareSkuBrand> lists,int count,int line) {
		if(lists!=null && lists.size()>0){
			   int execleTotalSize=lists.size();
			    int index =0;
				HSSFSheet sheet = wb.getSheetAt(count);
				if(sheet==null){
					sheet=wb.createSheet("商品信息"+count);
				}
				int i=1;
				//for (JDWareSkuBrand jsb : lists) {
				for (;index<execleTotalSize;index++) {
					HSSFRow row = sheet.createRow(i);
					JDWareSkuBrand jsb=lists.get(index);
					if(jsb==null){
						continue;
					}
					//走秀SKU编码
					if(jsb.getSkuCode() != null && !"".equals(jsb.getSkuCode())){
						ExportExcelUtil.createCell(row, 0, jsb.getSkuCode());
					}else{
						ExportExcelUtil.createCell(row, 0, "");
					}
					//京东商品SKU编码
					if(jsb.getJdSkuId() != null && !"".equals(jsb.getJdSkuId())){
						ExportExcelUtil.createCell(row, 1, jsb.getJdSkuId());
					}else{
						ExportExcelUtil.createCell(row, 1, "");
					}
					//走秀码
					if(jsb.getXiuCode() != null && !"".equals(jsb.getXiuCode())){
						ExportExcelUtil.createCell(row, 2, jsb.getXiuCode());
					}else{
						ExportExcelUtil.createCell(row, 2, "");
					}
					//京东商品ID
					if(jsb.getJdWareId() != null && !"".equals(jsb.getJdWareId())){
						ExportExcelUtil.createCell(row, 3, jsb.getJdWareId());
					}else{
						ExportExcelUtil.createCell(row, 3, "");
					}
					//sku库存量
					if(jsb.getSkuStock() != null && !"".equals(jsb.getSkuStock())){
						ExportExcelUtil.createCell(row, 4, jsb.getSkuStock());
					}else{
						ExportExcelUtil.createCell(row, 4, "");
					}
					//京东价
					if(jsb.getJdPrice() != null && !"".equals(jsb.getJdPrice())){
						ExportExcelUtil.createCell(row, 5, jsb.getJdPrice());
					}else{
						ExportExcelUtil.createCell(row, 5, "");
					}
					//走秀价
					if(jsb.getCostPrice() != null && !"".equals(jsb.getCostPrice())){
						ExportExcelUtil.createCell(row, 6, jsb.getCostPrice());
					}else{
						ExportExcelUtil.createCell(row, 6, "");
					}
					//市场价
					if(jsb.getMarketPrice() != null && !"".equals(jsb.getMarketPrice())){
						ExportExcelUtil.createCell(row, 7, jsb.getMarketPrice());
					}else{
						ExportExcelUtil.createCell(row, 7, "");
					}
				
					//品牌
					if(jsb.getXiuBrand()!= null && !"".equals(jsb.getXiuBrand())){
						ExportExcelUtil.createCell(row, 8, jsb.getXiuBrand());
					}else{
						ExportExcelUtil.createCell(row, 8, "");
					}
					//商品名
					if(jsb.getJdWareName()!= null && !"".equals(jsb.getJdWareName())){
						ExportExcelUtil.createCell(row, 9, jsb.getJdWareName());
					}else{
						ExportExcelUtil.createCell(row, 9, "");
					}
					//商品供应商编码
					if(jsb.getSupplierCode()!= null && !"".equals(jsb.getSupplierCode())){
						ExportExcelUtil.createCell(row, 10, jsb.getSupplierCode());
					}else{
						ExportExcelUtil.createCell(row, 10, "");
					}
					//京东一级分类
					if(jsb.getJdFirCname()!= null && !"".equals(jsb.getJdFirCname())){
						ExportExcelUtil.createCell(row, 11, jsb.getJdFirCname());
					}else{
						ExportExcelUtil.createCell(row, 11, "");
					}
					//京东二级分类
					if(jsb.getJdSecCname()!= null && !"".equals(jsb.getJdSecCname())){
						ExportExcelUtil.createCell(row, 12, jsb.getJdSecCname());
					}else{
						ExportExcelUtil.createCell(row, 12, "");
					}
					//京东三级分类
					if(jsb.getJdThiCname()!= null && !"".equals(jsb.getJdThiCname())){
						ExportExcelUtil.createCell(row, 13, jsb.getJdThiCname());
					}else{
						ExportExcelUtil.createCell(row, 13, "");
					}
					//走秀基础分类
					if(jsb.getXiuSuperName()!= null && !"".equals(jsb.getXiuSuperName())){
						ExportExcelUtil.createCell(row, 14, jsb.getXiuSuperName());
					}else{
						ExportExcelUtil.createCell(row, 14, "");
					}
					//走秀管理分类
					if(jsb.getXiuFamliyName()!= null && !"".equals(jsb.getXiuFamliyName())){
						ExportExcelUtil.createCell(row, 15, jsb.getXiuFamliyName());
					}else{
						ExportExcelUtil.createCell(row, 15, "");
					}
					//走秀三级分类
					if(jsb.getXiuChildName()!= null && !"".equals(jsb.getXiuChildName())){
						ExportExcelUtil.createCell(row, 16, jsb.getXiuChildName());
					}else{
						ExportExcelUtil.createCell(row, 16, "");
					}
					//销售状态
					if(jsb.getOnLineStatus()==0){
						ExportExcelUtil.createCell(row, 17, "未上架");
					}else if(jsb.getOnLineStatus()==1){
						ExportExcelUtil.createCell(row, 17, "在售");
					}else if(jsb.getOnLineStatus()==2){
						ExportExcelUtil.createCell(row, 17, "下架");
					}
					if(jsb.getCreateDate()!= null && !"".equals(jsb.getCreateDate())){
						ExportExcelUtil.createCell(row, 18, jsb.getCreateDate());
					}else{
						ExportExcelUtil.createCell(row, 18, "");
					}	
					i++;
					/*if(line<=i){
						System.out.println("index="+index);
						index++;
						break;
					}*/
				} 
			
			
		}
		
	
	}

	public void createOrderItemWb(HSSFWorkbook wb, List<JDOrderItemInfo> lists,
			int count, int pageSize) {
		if(lists!=null && lists.size()>0){
			   int execleTotalSize=lists.size();
			    int index =0;
			    HSSFSheet sheet = null;
			    try{
			    	sheet=wb.getSheetAt(count);
				if(sheet==null){
					sheet=wb.createSheet("订单项"+count);
					System.out.println("==============="+count);
				}
			    }catch(Exception e){
			    	if(sheet==null){
			    	sheet=wb.createSheet("订单项==>"+count);
			    	}
			    	System.out.println("异常===============");
			    }
				int i=1;
				Object object=ExportExcelUtil.getBorderCellStyle(wb);
				for (;index<execleTotalSize;index++) {
					HSSFRow row = sheet.createRow(i);
					JDOrderItemInfo item=lists.get(index);
					if(item==null){
						continue;
					}
					//京东订单ID
					if(item.getJdOrderId()!=null && !"".equals(item.getJdOrderId())){
						ExportExcelUtil.createCell(row, 0, item.getJdOrderId(),object);
					}else{
						ExportExcelUtil.createCell(row, 0, "",object);
					}
					//走秀订单ID
					if(item.getLocalOrderId()!=null && !"".equals(item.getLocalOrderId())){
						ExportExcelUtil.createCell(row, 1, item.getLocalOrderId(),object);
					}else{
						ExportExcelUtil.createCell(row, 1, "",object);
					}
					
					//走秀订单号
					if(item.getOrderCode()!=null && !"".equals(item.getOrderCode())){
						ExportExcelUtil.createCell(row, 2, item.getOrderCode(),object);
					}else{
						ExportExcelUtil.createCell(row, 2, "",object);
					}
					
					//下单时间
					if(item.getPlaceTime()!=null && !"".equals(item.getPlaceTime())){
						ExportExcelUtil.createCell(row, 3, item.getPlaceTime(),object);
					}else{
						ExportExcelUtil.createCell(row, 3, "",object);
					}
					
					//商品名称
					if(item.getSkuName()!=null && !"".equals(item.getSkuName())){
						ExportExcelUtil.createCell(row, 4, item.getSkuName(),object);
					}else{
						ExportExcelUtil.createCell(row, 4, "",object);
					}
					
					//商品SKU码
					if(item.getOuterSkuId()!=null && !"".equals(item.getOuterSkuId())){
						ExportExcelUtil.createCell(row, 5, item.getOuterSkuId(),object);
					}else{
						ExportExcelUtil.createCell(row, 5, "",ExportExcelUtil.getBorderCellStyle(wb));
					}
					
					//商品单价
					if(item.getJdPrice()!=null && !"".equals(item.getJdPrice())){
						ExportExcelUtil.createCell(row, 6, item.getJdPrice(),object);
					}else{
						ExportExcelUtil.createCell(row, 6, "",object);
					}
					//购买数量
					if(item.getItemTotal()!=null && !"".equals(item.getItemTotal())){
						ExportExcelUtil.createCell(row, 7, item.getItemTotal(),object);
					}else{
						ExportExcelUtil.createCell(row, 7, "",object);
					}
					
					//下单失败原因
					if(item.getFailDescri()!=null && !"".equals(item.getFailDescri())){
						ExportExcelUtil.createCell(row, 8, item.getFailDescri(),object);
					}else{
						ExportExcelUtil.createCell(row, 8, "",object);
					}
			
					
					i++;
				}
			
			
		}
		
	
	
		
	}


}
