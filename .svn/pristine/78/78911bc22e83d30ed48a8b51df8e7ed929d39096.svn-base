package com.xiu.jd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ImportExcelUtil
{
	@SuppressWarnings("deprecation")
	public static XSSFWorkbook getWorkbook2007(String fileName)
	{
		try
		{
			XSSFWorkbook wb = new XSSFWorkbook(fileName);
			return wb;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static HSSFWorkbook getWorkbook(String fileName) throws IOException
	{
		File file = new File(fileName);

		try
		{	
			FileInputStream fis = new FileInputStream(file);
			HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(fis));
			// new XSSFWorkbook(fileName);
			fis.close();
			return wb;
		}
		catch (IOException e)
		{
			throw new IOException("获取文件出错");
		}
	}

	public static HSSFWorkbook getWorkbook(File file) throws IOException
	{
		try
		{
			FileInputStream fis = new FileInputStream(file);
			HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(fis));
			fis.close();
			return wb;
		}
		catch (IOException e)
		{
			throw new IOException("获取文件出错");
		}
	}

	public static Object getWorkbook(File file, String fileName)
			throws IOException
	{
		try
		{
			FileInputStream fis = new FileInputStream(file);
			String fileType = fileName.substring(fileName.indexOf(".") + 1);
			Object object = null;
			if ("xls".equalsIgnoreCase(fileType))
			{

				object = new HSSFWorkbook(new POIFSFileSystem(fis));
				fis.close();

			}
			if ("xlsx".equalsIgnoreCase(fileType))
			{
				object = new XSSFWorkbook(fis);
			}
			return object;
		}
		catch (IOException e)
		{
			throw new IOException("获取文件出错");
		}
	}

	public static String getCellValueStr(Object cell)
	{
		if (cell != null)
		{
			try
			{
				if (cell instanceof HSSFCell)
				{
					return ((HSSFCell) cell).getStringCellValue();
				}
				if (cell instanceof XSSFCell)
				{
					return ((XSSFCell) cell).getStringCellValue();
				}
				return null;
			}
			catch (Exception e)
			{
				return null;
			}
		}

		return null;
	}

	public static String getCellValueStrTrim(Object cell)
	{
		String value = getCellValueStr(cell);
		if (!StringUtil.isEmpty(value))
		{
			return value.trim();
		}

		return null;
	}

	public static String getCellValueDou(Object cell)
	{
		if (cell != null)
		{
			try
			{
				if (cell instanceof HSSFCell)
				{
					return ""+(int)((HSSFCell) cell).getNumericCellValue();
				}
				if (cell instanceof XSSFCell)
				{
					return ""+(int)((XSSFCell) cell).getNumericCellValue();
				}
				return null;
			}
			catch (Exception e)
			{
				return null;
			}
		}

		return null;
	}

	public static int getCellValueInt(Object cell)
	{
		if (cell != null)
		{
			try
			{
				if (cell instanceof HSSFCell)
				{
					return (int) ((HSSFCell) cell).getNumericCellValue();
				}
				if (cell instanceof XSSFCell)
				{
					return (int) ((XSSFCell) cell).getNumericCellValue();
				}
			}
			catch (Exception e)
			{
				return 0;
			}
		}
		return 0;
	}

	public static Date getCellValueDate(Object cell)
	{
		if (cell != null)
		{
			try
			{
				if (cell instanceof HSSFCell)
				{
					return ((HSSFCell) cell).getDateCellValue();
				}
				if (cell instanceof XSSFCell)
				{
					return ((XSSFCell) cell).getDateCellValue();
				}
				return null;
			}
			catch (Exception e)
			{
				return null;
			}
		}

		return null;
	}
	
	public static String getCellValue(Object cell){
		if(cell != null){
			String value = "";
			if(cell instanceof HSSFCell){
				switch (((HSSFCell) cell).getCellType()) {		 
		        case HSSFCell.CELL_TYPE_NUMERIC: // 数字           
		        	value=""+((HSSFCell) cell).getNumericCellValue();
		        	if(value.contains(".")){
		        		String[] attrs = value.split("\\.");
		        		if("0".equals(attrs[1])){
		        			value = attrs[0];
		        		}
		        	}
		        	break;
		        case HSSFCell.CELL_TYPE_STRING: // 字符串             
		            value=((HSSFCell) cell).getStringCellValue();
		            break;  
		        default:  
		            break;  
		        }  	
			}
			if(cell instanceof XSSFCell){
				switch (((XSSFCell) cell).getCellType()) {		 
		        case HSSFCell.CELL_TYPE_NUMERIC: // 数字           
		        	value=""+((XSSFCell) cell).getNumericCellValue();
		        	break;
		        case HSSFCell.CELL_TYPE_STRING: // 字符串             
		            value=((XSSFCell) cell).getStringCellValue();
		            break;  
		        default:  
		            break;  
		        }  	
			}
			return value;
		}else{
			return null;
		}
	}
	
	
	public static String getCellValue2(Object cell){
		if(cell != null){
			String value = "";
			if(cell instanceof HSSFCell){
				switch (((HSSFCell) cell).getCellType()) {		 
		        case HSSFCell.CELL_TYPE_NUMERIC: // 数字           
		        	value=""+((HSSFCell) cell).getNumericCellValue();
		        	break;
		        case HSSFCell.CELL_TYPE_STRING: // 字符串             
		            value=((HSSFCell) cell).getStringCellValue();
		            break;  
		        default:  
		            break;  
		        }  	
			}
			if(cell instanceof XSSFCell){
				switch (((XSSFCell) cell).getCellType()) {		 
		        case HSSFCell.CELL_TYPE_NUMERIC: // 数字           
		        	value=""+((XSSFCell) cell).getNumericCellValue();
		        	break;
		        case HSSFCell.CELL_TYPE_STRING: // 字符串             
		            value=((XSSFCell) cell).getStringCellValue();
		            break;  
		        default:  
		            break;  
		        }  	
			}
			return value;
		}else{
			return null;
		}
	}
	
	public static Object getCell(Object row,int rowNum){
		Object cell = null;
		if (row != null) {
			if (row instanceof HSSFRow) {
				cell = ((HSSFRow) row).getCell(rowNum);
			}
			if (row instanceof XSSFRow) {
				cell = ((XSSFRow) row).getCell(rowNum);
			}
		}
		return cell;
	}
	
	public static String getCellValue3(Object cell) {
		DecimalFormat df = new DecimalFormat("#");
		String value=null;
		if(cell != null){
			
			if(cell instanceof HSSFCell){
				switch ( ((HSSFCell) cell).getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC:
					if(HSSFDateUtil.isCellDateFormatted(((HSSFCell) cell))){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						return sdf.format(HSSFDateUtil.getJavaDate(((HSSFCell) cell).getNumericCellValue())).toString();
					}
					value= df.format(((HSSFCell) cell).getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_STRING:
					System.out.println(((HSSFCell) cell).getStringCellValue());
					value= ((HSSFCell) cell).getStringCellValue();
					break;
				case HSSFCell.CELL_TYPE_FORMULA:
					value= ((HSSFCell) cell).getCellFormula();
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					value= "";
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					value= ((HSSFCell) cell).getBooleanCellValue() + "";
					break;
				case HSSFCell.CELL_TYPE_ERROR:
					value= ((HSSFCell) cell).getErrorCellValue() + "";
					break;
			    default:
					 break;
				}
				
			}
			if(cell instanceof XSSFCell){
				switch (((XSSFCell) cell).getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC:
					if(HSSFDateUtil.isCellDateFormatted(((XSSFCell) cell))){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						value= sdf.format(HSSFDateUtil.getJavaDate(((XSSFCell) cell).getNumericCellValue())).toString();
					}
					value= df.format(((XSSFCell) cell).getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_STRING:
					System.out.println(((XSSFCell) cell).getStringCellValue());
					value= ((XSSFCell) cell).getStringCellValue();
					break;
				case HSSFCell.CELL_TYPE_FORMULA:
					value= ((XSSFCell) cell).getCellFormula();
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					value= "";
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					value= ((XSSFCell) cell).getBooleanCellValue() + "";
					break;
				case HSSFCell.CELL_TYPE_ERROR:
					value= ((XSSFCell) cell).getErrorCellValue() + "";
					break;
				default:
				break;
				}
				
				
				
			}
			
		}
			return value;
	
		
	}

}
