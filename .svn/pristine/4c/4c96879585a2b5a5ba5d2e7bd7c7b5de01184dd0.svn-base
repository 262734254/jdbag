package com.xiu.jd.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.Region;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

/*
 * 生成导出Excel文件的工具类
 * 
 * @author kevin liu 
 */
public class ExportExcelUtil {
	// 定制日期格式
	private static String DATE_FORMAT = "yyyy-MM-dd"; // "m/d/yy h:mm"
	// 定制浮点数格式
	private static String NUMBER_FORMAT = "#,##0.00";

	/**
	 * 导出Excel文件
	 * 
	 * @throws IOException
	 */
	public static void exportExcel(Object wb, String fileName)
			throws FileNotFoundException, IOException {
		FileOutputStream fos = null;
		fos = new FileOutputStream(new File(fileName));
		if (wb instanceof HSSFWorkbook) {
			HSSFWorkbook hssWb = (HSSFWorkbook) wb;
			hssWb.write(fos);
		}
		if (wb instanceof org.apache.poi.xssf.usermodel.XSSFWorkbook) {
			XSSFWorkbook xssWb = (XSSFWorkbook) wb;
			xssWb.write(fos);
		}
		try {

			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			throw new IOException(" 生成导出Excel文件出错! ", e);
		} catch (IOException e) {
			throw new IOException(" 写入Excel文件出错! ", e);
		}
	}

	/**
	 * 导出Excel下载文件
	 * 
	 * @throws IOException
	 * 
	 * @throws IOException
	 */

	public static void downloadExcel(Object wb, String fileName)
			throws IOException {

		// 告诉Struts 执行完 Action 就行了，不用再去调用结果响应的操作
		ActionContext.getContext().getActionInvocation().getProxy()
				.setExecuteResult(false);

		// 取得 HttpServletResponse
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setHeader("Content-Disposition", "attachment; filename="
				+ ExportExcelUtil.getDownloadFileName(fileName));

		OutputStream os = response.getOutputStream();

		if (wb instanceof HSSFWorkbook) {
			HSSFWorkbook hssWb = (HSSFWorkbook) wb;
			response.setContentType("xls");
			hssWb.write(os);
		}
		if (wb instanceof org.apache.poi.xssf.usermodel.XSSFWorkbook) {
			XSSFWorkbook xssWb = (XSSFWorkbook) wb;
			response.setContentType("xlsx");
			xssWb.write(os);
		}
		os.flush();
		os.close();
	}

	/**
	 * 设置单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充整数值
	 * @return 新生成的单元格
	 */
	public static Object setCell(Object row, int colIndex, int value) {
		Object cell = null;
		if (row instanceof HSSFRow) {
			HSSFRow hssWb = (HSSFRow) row;
			cell = hssWb.getCell(colIndex);
			((HSSFCell) cell).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			((HSSFCell) cell).setCellValue(value);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow xssWb = (XSSFRow) row;
			cell = xssWb.getCell(colIndex);
			((XSSFCell) cell).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			((XSSFCell) cell).setCellValue(value);
		}
		// XSSFCell cell = row.getCell(colIndex);

		return cell;
	}

	/**
	 * 设置单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充整数值
	 * @param cellStyle
	 *            单元格样式
	 * @return 新生成的单元格
	 */
	public static Object setCell(Object row, int colIndex, int value,
			Object cellStyle) {
		Object cell = null;

		if (row instanceof HSSFRow) {
			HSSFRow objRow = (HSSFRow) row;
			cell = setCell(objRow, colIndex, value);
			HSSFCellStyle objCellStyle = (HSSFCellStyle) cellStyle;
			((HSSFCell) cell).setCellStyle(objCellStyle);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow objRow = (XSSFRow) row;
			cell = setCell(objRow, colIndex, value);
			XSSFCellStyle objCellStyle = (XSSFCellStyle) cellStyle;
			((XSSFCell) cell).setCellStyle(objCellStyle);
		}
		return cell;
	}

	/**
	 * 设置单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格double值
	 * @return 新生成的单元格
	 */
	public static Object setCell(Object row, int colIndex, double value) {
		Object cell = null;
		if (row instanceof HSSFRow) {
			HSSFRow hssWb = (HSSFRow) row;
			cell = hssWb.getCell(colIndex);
			((HSSFCell) cell).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			((HSSFCell) cell).setCellValue(value);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow xssWb = (XSSFRow) row;
			cell = xssWb.getCell(colIndex);
			((XSSFCell) cell).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			((XSSFCell) cell).setCellValue(value);
		}
		// XSSFCell cell = row.getCell(colIndex);

		return cell;
	}

	/**
	 * 设置单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格double值
	 * @param cellStyle
	 *            单元格样式
	 * @return 新生成的单元格
	 */
	public static Object setCell(Object row, int colIndex, double value,
			Object cellStyle) {
		Object cell = null;

		if (row instanceof HSSFRow) {
			HSSFRow objRow = (HSSFRow) row;
			cell = setCell(objRow, colIndex, value);
			HSSFCellStyle objCellStyle = (HSSFCellStyle) cellStyle;
			((HSSFCell) cell).setCellStyle(objCellStyle);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow objRow = (XSSFRow) row;
			cell = setCell(objRow, colIndex, value);
			XSSFCellStyle objCellStyle = (XSSFCellStyle) cellStyle;
			((XSSFCell) cell).setCellStyle(objCellStyle);
		}
		return cell;
	}

	/**
	 * 设置单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充字符串值
	 * @return 新生成的单元格
	 */
	public static Object setCell(Object row, int colIndex, String value) {
		Object cell = null;
		if (row instanceof HSSFRow) {
			HSSFRow hssWb = (HSSFRow) row;
			cell = hssWb.getCell(colIndex);
			((HSSFCell) cell).setCellType(HSSFCell.CELL_TYPE_STRING);
			((HSSFCell) cell).setCellType(HSSFCell.ENCODING_UTF_16);
			((HSSFCell) cell).setCellValue(value);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow xssWb = (XSSFRow) row;
			cell = xssWb.getCell(colIndex);
			((XSSFCell) cell).setCellType(XSSFCell.CELL_TYPE_STRING);
			((XSSFCell) cell).setCellValue(value);
		}
		return cell;
	}

	/**
	 * 设置单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充字符串值
	 * @param cellStyle
	 *            单元格样式
	 * @return 新生成的单元格
	 */
	public static Object setCell(Object row, int colIndex, String value,
			Object cellStyle) {
		Object cell = null;

		if (row instanceof HSSFRow) {
			HSSFRow objRow = (HSSFRow) row;
			cell = setCell(objRow, colIndex, value);
			HSSFCellStyle objCellStyle = (HSSFCellStyle) cellStyle;
			((HSSFCell) cell).setCellStyle(objCellStyle);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow objRow = (XSSFRow) row;
			cell = setCell(objRow, colIndex, value);
			XSSFCellStyle objCellStyle = (XSSFCellStyle) cellStyle;
			((XSSFCell) cell).setCellStyle(objCellStyle);
		}
		return cell;
	}

	/**
	 * 设置单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充日期值
	 * @return 新生成的单元格
	 */
	public static Object setCell(Object row, int colIndex, Date value) {
		Object cell = null;
		if (row instanceof HSSFRow) {
			HSSFRow hssWb = (HSSFRow) row;
			cell = hssWb.getCell(colIndex);
			if (value == null) {
				((HSSFCell) cell).setCellValue("");
			} else {
				((HSSFCell) cell).setCellValue(value);
			}
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow xssWb = (XSSFRow) row;
			cell = xssWb.getCell(colIndex);
			if (value == null) {
				((XSSFCell) cell).setCellValue("");
			} else {
				((XSSFCell) cell).setCellValue(value);
			}
		}
		return cell;
	}

	/**
	 * 设置单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充日期值
	 * @param cellStyle
	 *            单元格样式
	 * @return 新生成的单元格
	 */
	public static Object setCell(Object row, int colIndex, Date value,
			Object cellStyle) {
		Object cell = null;

		if (row instanceof HSSFRow) {
			HSSFRow objRow = (HSSFRow) row;
			cell = objRow.getCell(colIndex);
			HSSFCellStyle objCellStyle = (HSSFCellStyle) cellStyle;
			((HSSFCell) cell).setCellStyle(objCellStyle);
			((HSSFCell) cell).setCellValue(value);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow objRow = (XSSFRow) row;
			cell = objRow.getCell(colIndex);
			XSSFCellStyle objCellStyle = (XSSFCellStyle) cellStyle;
			((XSSFCell) cell).setCellStyle(objCellStyle);
			((HSSFCell) cell).setCellValue(value);
		}
		return cell;
	}

	/**
	 * 设置公式单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充公式
	 * @return 新生成的单元格
	 */
	public static Object setFormulaCell(Object row, int colIndex,
			String formulaString) {
		Object cell = null;
		if (row instanceof HSSFRow) {
			HSSFRow hssWb = (HSSFRow) row;
			cell = (HSSFCell) hssWb.getCell(colIndex);
			((HSSFCell) cell).setCellFormula(formulaString);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow xssWb = (XSSFRow) row;
			cell = xssWb.getCell(colIndex);
			((XSSFCell) cell).setCellFormula(formulaString);
		}
		return cell;

	}

	/**
	 * 设置公式单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充公式
	 * @param cellStyle
	 *            单元格样式
	 * @return 新生成的单元格
	 */
	public static Object setFormulaCell(Object row, int colIndex,
			String formulaString, Object cellStyle) {
		Object cell = null;

		if (row instanceof HSSFRow) {
			HSSFRow objRow = (HSSFRow) row;
			cell = objRow.getCell(colIndex);
			((HSSFCell) cell).setCellFormula(formulaString);
			HSSFCellStyle objCellStyle = (HSSFCellStyle) cellStyle;
			((HSSFCell) cell).setCellStyle(objCellStyle);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow objRow = (XSSFRow) row;
			cell = objRow.getCell(colIndex);
			((XSSFCell) cell).setCellFormula(formulaString);
			XSSFCellStyle objCellStyle = (XSSFCellStyle) cellStyle;
			((XSSFCell) cell).setCellStyle(objCellStyle);
		}
		return cell;
	}

	/**
	 * 创建单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充整数值
	 * @return 新生成的单元格
	 */
	public static Object createCell(Object row, int colIndex, int value) {
		Object cell = null;
		if (row instanceof HSSFRow) {
			HSSFRow hssWb = (HSSFRow) row;
			cell = hssWb.createCell(colIndex);
			((HSSFCell) cell).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			((HSSFCell) cell).setCellValue(value);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow xssWb = (XSSFRow) row;
			cell = xssWb.createCell(colIndex);
			((XSSFCell) cell).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			((XSSFCell) cell).setCellValue(value);
		}
		return cell;

	}

	/**
	 * 创建单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充整数值
	 * @param cellStyle
	 *            单元格样式
	 * @return 新生成的单元格
	 */
	public static Object createCell(Object row, int colIndex, int value,
			Object cellStyle) {
		Object cell = null;

		if (row instanceof HSSFRow) {
			HSSFRow objRow = (HSSFRow) row;
			cell = createCell(objRow, colIndex, value);
			HSSFCellStyle objCellStyle = (HSSFCellStyle) cellStyle;
			((HSSFCell) cell).setCellStyle(objCellStyle);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow objRow = (XSSFRow) row;
			cell = createCell(objRow, colIndex, value);
			XSSFCellStyle objCellStyle = (XSSFCellStyle) cellStyle;
			((XSSFCell) cell).setCellStyle(objCellStyle);
		}
		return cell;
	}

	/**
	 * 创建单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格double值
	 * @return 新生成的单元格
	 */
	public static Object createCell(Object row, int colIndex, double value) {
		Object cell = null;
		if (row instanceof HSSFRow) {
			HSSFRow hssWb = (HSSFRow) row;
			cell = hssWb.createCell(colIndex);
			((HSSFCell) cell).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			((HSSFCell) cell).setCellValue(value);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow xssWb = (XSSFRow) row;
			cell = xssWb.createCell(colIndex);
			((XSSFCell) cell).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			((XSSFCell) cell).setCellValue(value);
		}
		return cell;
	}

	/**
	 * 创建单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格double值
	 * @param cellStyle
	 *            单元格样式
	 * @return 新生成的单元格
	 */
	public static Object createCell(Object row, int colIndex, double value,
			Object cellStyle) {
		Object cell = null;

		if (row instanceof HSSFRow) {
			HSSFRow objRow = (HSSFRow) row;
			cell = createCell(objRow, colIndex, value);
			HSSFCellStyle objCellStyle = (HSSFCellStyle) cellStyle;
			((HSSFCell) cell).setCellStyle(objCellStyle);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow objRow = (XSSFRow) row;
			cell = createCell(objRow, colIndex, value);
			XSSFCellStyle objCellStyle = (XSSFCellStyle) cellStyle;
			((XSSFCell) cell).setCellStyle(objCellStyle);
		}
		return cell;

	}

	/**
	 * 创建单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充字符串值
	 * @return 新生成的单元格
	 */
	public static Object createCell(Object row, int colIndex, String value) {
		Object cell = null;
		if (row instanceof HSSFRow) {
			HSSFRow hssWb = (HSSFRow) row;
			cell = hssWb.createCell(colIndex);
			((HSSFCell) cell).setCellType(HSSFCell.CELL_TYPE_STRING);
			((HSSFCell) cell).setCellType(HSSFCell.ENCODING_UTF_16);
			((HSSFCell) cell).setCellValue(value);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow xssWb = (XSSFRow) row;
			cell = xssWb.createCell(colIndex);
			((XSSFCell) cell).setCellType(XSSFCell.CELL_TYPE_STRING);
			((XSSFCell) cell).setCellValue(value);
		}
		return cell;
	}

	/**
	 * 创建单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充字符串值
	 * @param cellStyle
	 *            单元格样式
	 * @return 新生成的单元格
	 */
	public static Object createCell(Object row, int colIndex, String value,
			Object cellStyle) {
		Object cell = null;

		if (row instanceof HSSFRow) {
			HSSFRow objRow = (HSSFRow) row;
			cell = createCell(objRow, colIndex, value);
			HSSFCellStyle objCellStyle = (HSSFCellStyle) cellStyle;
			((HSSFCell) cell).setCellStyle(objCellStyle);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow objRow = (XSSFRow) row;
			cell = createCell(objRow, colIndex, value);
			XSSFCellStyle objCellStyle = (XSSFCellStyle) cellStyle;
			((XSSFCell) cell).setCellStyle(objCellStyle);
		}
		return cell;
	}

	/**
	 * 创建单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充日期值
	 * @return 新生成的单元格
	 */
	public static Object createCell(Object wb, Object row, int colIndex,
			Date value) {
		Object cell = null;
		if (wb instanceof HSSFWorkbook) {
			HSSFRow hssWb = (HSSFRow) row;
			HSSFDataFormat format = ((HSSFWorkbook) wb).createDataFormat();
			cell = ((HSSFRow) row).createCell(colIndex);
			HSSFCellStyle style = ((HSSFWorkbook) wb).createCellStyle();
			style.setDataFormat(format.getFormat(DATE_FORMAT));

			((HSSFCell) cell).setCellValue(value);
			((HSSFCell) cell).setCellStyle(style);
		}
		if (wb instanceof org.apache.poi.xssf.usermodel.XSSFWorkbook) {
			XSSFRow xssWb = (XSSFRow) row;
			XSSFDataFormat format = ((XSSFWorkbook) wb).createDataFormat();
			cell = ((XSSFRow) row).createCell(colIndex);
			XSSFCellStyle style = ((XSSFWorkbook) wb).createCellStyle();
			style.setDataFormat(format.getFormat(DATE_FORMAT));

			((XSSFCell) cell).setCellValue(value);
			((XSSFCell) cell).setCellStyle(style);
		}
		return cell;
	}

	/**
	 * 创建单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充日期值
	 * @return 新生成的单元格
	 */
	public static Object createCell(Object wb, Object row, int colIndex,
			Date value, Object cellStyle) {
		Object cell = null;
		if (wb instanceof HSSFWorkbook) {
			HSSFDataFormat format = ((HSSFWorkbook) wb).createDataFormat();
			cell = ((HSSFRow) row).createCell(colIndex);
			HSSFCellStyle style = ((HSSFWorkbook) wb).createCellStyle();
			style.cloneStyleFrom((HSSFCellStyle) cellStyle);
			style.setDataFormat(format.getFormat(DATE_FORMAT));

			((HSSFCell) cell).setCellValue(value);
			((HSSFCell) cell).setCellStyle(style);
		}
		if (wb instanceof org.apache.poi.xssf.usermodel.XSSFWorkbook) {
			XSSFDataFormat format = ((XSSFWorkbook) wb).createDataFormat();
			cell = ((XSSFRow) row).createCell(colIndex);
			XSSFCellStyle style = ((XSSFWorkbook) wb).createCellStyle();
			style.cloneStyleFrom((XSSFCellStyle) cellStyle);
			style.setDataFormat(format.getFormat(DATE_FORMAT));

			((XSSFCell) cell).setCellValue(value);
			((XSSFCell) cell).setCellStyle(style);
		}
		return cell;
	}

	/**
	 * 创建单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充日期值
	 * @return 新生成的单元格
	 */
	public static Object createCell(Object wb, Object row, int colIndex,
			Date value, String dateFormat) {
		Object cell = null;
		if (wb instanceof HSSFWorkbook) {
			HSSFRow hssWb = (HSSFRow) row;
			HSSFDataFormat format = ((HSSFWorkbook) wb).createDataFormat();
			cell = ((HSSFRow) row).createCell(colIndex);
			HSSFCellStyle style = ((HSSFWorkbook) wb).createCellStyle();
			style.setDataFormat(format.getFormat(dateFormat));

			((HSSFCell) cell).setCellValue(value);
			((HSSFCell) cell).setCellStyle(style);
		}
		if (wb instanceof org.apache.poi.xssf.usermodel.XSSFWorkbook) {
			XSSFRow xssWb = (XSSFRow) row;
			XSSFDataFormat format = ((XSSFWorkbook) wb).createDataFormat();
			cell = ((XSSFRow) row).createCell(colIndex);
			XSSFCellStyle style = ((XSSFWorkbook) wb).createCellStyle();
			style.setDataFormat(format.getFormat(dateFormat));

			((XSSFCell) cell).setCellValue(value);
			((XSSFCell) cell).setCellStyle(style);
		}
		return cell;
	}

	/**
	 * 创建单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充日期值
	 * @return 新生成的单元格
	 */
	public static Object createCell(Object wb, Object row, int colIndex,
			Date value, Object cellStyle, String dateFormat) {
		Object cell = null;
		if (wb instanceof HSSFWorkbook) {
			HSSFDataFormat format = ((HSSFWorkbook) wb).createDataFormat();
			cell = ((HSSFRow) row).createCell(colIndex);
			HSSFCellStyle style = ((HSSFWorkbook) wb).createCellStyle();
			style.cloneStyleFrom((HSSFCellStyle) cellStyle);
			style.setDataFormat(format.getFormat(dateFormat));

			((HSSFCell) cell).setCellValue(value);
			((HSSFCell) cell).setCellStyle(style);
		}
		if (wb instanceof org.apache.poi.xssf.usermodel.XSSFWorkbook) {
			XSSFDataFormat format = ((XSSFWorkbook) wb).createDataFormat();
			cell = ((XSSFRow) row).createCell(colIndex);
			XSSFCellStyle style = ((XSSFWorkbook) wb).createCellStyle();
			style.cloneStyleFrom((XSSFCellStyle) cellStyle);
			style.setDataFormat(format.getFormat(dateFormat));

			((XSSFCell) cell).setCellValue(value);
			((XSSFCell) cell).setCellStyle(style);
		}
		return cell;
	}

	/**
	 * 创建单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充日期值
	 * @param cellStyle
	 *            单元格样式
	 * @return 新生成的单元格
	 */
	public static Object createCell(Object row, int colIndex, Date value,
			Object cellStyle) {
		Object cell = null;
		if (row instanceof HSSFRow) {
			HSSFRow objRow = (HSSFRow) row;
			cell = objRow.createCell(colIndex);
			HSSFCellStyle objCellStyle = (HSSFCellStyle) cellStyle;
			((HSSFCell) cell).setCellStyle(objCellStyle);
			((HSSFCell) cell).setCellValue(value);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow objRow = (XSSFRow) row;
			cell = objRow.createCell(colIndex);
			XSSFCellStyle objCellStyle = (XSSFCellStyle) cellStyle;
			((XSSFCell) cell).setCellStyle(objCellStyle);
			((XSSFCell) cell).setCellValue(value);
		}
		return cell;
	}

	/**
	 * 创建公式单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充公式
	 * @return 新生成的单元格
	 */
	public static Object createFormulaCell(Object row, int colIndex,
			String formulaString) {
		Object cell = null;
		if (row instanceof HSSFRow) {
			HSSFRow hssWb = (HSSFRow) row;
			cell = hssWb.createCell(colIndex);
			((HSSFCell) cell).setCellFormula(formulaString);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow xssWb = (XSSFRow) row;
			cell = xssWb.createCell(colIndex);
			((XSSFCell) cell).setCellFormula(formulaString);
		}
		return cell;
	}

	/**
	 * 创建公式单元格
	 * 
	 * @param row
	 *            单元格所在的行
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充公式
	 * @param cellStyle
	 *            单元格样式
	 * @return 新生成的单元格
	 */
	public static Object createFormulaCell(Object row, int colIndex,
			String formulaString, Object cellStyle) {
		Object cell = null;
		if (row instanceof HSSFRow) {
			HSSFRow hssWb = (HSSFRow) row;
			cell = hssWb.createCell(colIndex);
			((HSSFCell) cell).setCellFormula(formulaString);
			HSSFCellStyle objCellStyle = (HSSFCellStyle) cellStyle;
			((HSSFCell) cell).setCellStyle(objCellStyle);
		}
		if (row instanceof org.apache.poi.xssf.usermodel.XSSFRow) {
			XSSFRow xssWb = (XSSFRow) row;
			cell = xssWb.createCell(colIndex);
			((XSSFCell) cell).setCellFormula(formulaString);
			XSSFCellStyle objCellStyle = (XSSFCellStyle) cellStyle;
			((XSSFCell) cell).setCellStyle(objCellStyle);
		}
		return cell;
	}

	/**
	 * 合并单元格
	 * 
	 * @param rowFrom
	 *            开始行号
	 * @param colFrom
	 *            开始列号
	 * @param rowTo
	 *            结束行号
	 * @param colTo
	 *            结束列号
	 */
	public static void mergeRegion(Object sheet, int rowFrom, int colFrom,
			int rowTo, int colTo) {

		if (sheet instanceof HSSFSheet) {
			((HSSFSheet) sheet).addMergedRegion(new Region(rowFrom,
					(short) colFrom, rowTo, (short) colTo));
		}
		if (sheet instanceof org.apache.poi.xssf.usermodel.XSSFSheet) {
			((XSSFSheet) sheet).addMergedRegion(new CellRangeAddress(rowFrom,
					colFrom, rowTo, colTo));
		}
	}

	public static Object getCStyleTemp(Object wb) {
		Object cellStyle = null;
		if (wb instanceof HSSFWorkbook) {
			HSSFWorkbook hssWb = (HSSFWorkbook) wb;
			// 创建单元格样式
			cellStyle = hssWb.createCellStyle();
			// 指定单元格居中对齐
			((HSSFCellStyle) cellStyle)
					.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			// 指定单元格垂直居中对齐
			((HSSFCellStyle) cellStyle)
					.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

			// 指定当单元格内容显示不下时自动换行
			((HSSFCellStyle) cellStyle).setWrapText(true);

			// 设置单元格字体
			HSSFFont font = hssWb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setFontName("宋体");
			font.setFontHeight((short) 200);
			((HSSFCellStyle) cellStyle).setFont(font);
		}
		if (wb instanceof XSSFWorkbook) {
			XSSFWorkbook xssWb = (XSSFWorkbook) wb;
			// 创建单元格样式
			cellStyle = xssWb.createCellStyle();
			// 指定单元格居中对齐
			((XSSFCellStyle) cellStyle)
					.setAlignment(XSSFCellStyle.ALIGN_CENTER);

			// 指定单元格垂直居中对齐
			((XSSFCellStyle) cellStyle)
					.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

			// 指定当单元格内容显示不下时自动换行
			((XSSFCellStyle) cellStyle).setWrapText(true);

			// 设置单元格字体
			XSSFFont font = xssWb.createFont();
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			font.setFontName("宋体");
			font.setFontHeight((short) 200);
			((XSSFCellStyle) cellStyle).setFont(font);
		}

		return cellStyle;
	}

	public static void main(String[] args) {
		// System.out.println(setTest("2") instanceof String);
	}

	/** 提供转换编码后的供下载用的文件名 */

	public static String getDownloadFileName(String fileName) {

		String downFileName = fileName;
		if (downFileName.length() < 1 || downFileName.equals("undefined")) {
			downFileName = fileName;
		}
		try {
			downFileName = new String(downFileName.getBytes(), "ISO8859-1");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return downFileName;
	}

	/** 获取全边框样式 */
	public static Object getBorderCellStyle(Object wb) {
		Object cellStyle = null;
		if (wb instanceof HSSFWorkbook) {
			HSSFWorkbook hssWb = (HSSFWorkbook) wb;
			// 创建单元格样式
			cellStyle = hssWb.createCellStyle();

			((HSSFCellStyle) cellStyle)
					.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			((HSSFCellStyle) cellStyle)
					.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			((HSSFCellStyle) cellStyle)
					.setBorderRight(HSSFCellStyle.BORDER_THIN);
			((HSSFCellStyle) cellStyle).setBorderTop(HSSFCellStyle.BORDER_THIN);
		}
		if (wb instanceof XSSFWorkbook) {
			XSSFWorkbook xssWb = (XSSFWorkbook) wb;
			// 创建单元格样式
			cellStyle = xssWb.createCellStyle();

			((XSSFCellStyle) cellStyle)
					.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			((XSSFCellStyle) cellStyle)
					.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			((XSSFCellStyle) cellStyle)
					.setBorderRight(XSSFCellStyle.BORDER_THIN);
			((XSSFCellStyle) cellStyle).setBorderTop(XSSFCellStyle.BORDER_THIN);
		}
		return cellStyle;

	}
}
