import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;


public class ExcelTest {
	
	public List<String> readExcel(String excelfilePath,
            int startRow, int startCol) throws IOException {
        // 读取xls文件
        InputStream ins = new FileInputStream(excelfilePath);
        // 设置读文件编码
        WorkbookSettings setEncode = new WorkbookSettings();
        setEncode.setEncoding("UTF-8");
        Workbook rwb=null;
		try {
			rwb = Workbook.getWorkbook(ins, setEncode);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        List<String> alldata = new ArrayList<String>();
        ArrayList<String> data = null;
        alldata.clear();
        // 获得当前Excel表共有几个sheet
        // Sheet[] sheets = rwb.getSheets();
        // 获得表数
        // int pages = sheets.length;
        // 将excel表中的数据读取出来
        // 在从Excel中读取数据的时候不需要知道每个sheet有几行，有那多少列
        // for (int i = 0; i < pages; i++) {
        Sheet sheet = rwb.getSheet(0);
        int cols = sheet.getColumns(); // 列
        // 读取每一行对应的列数目
        // 循环读取每一行的全部列数目的内容
        int rows = sheet.getRows(); // 行
        for (int r = startRow; r < rows; r++) {
        	Cell excelRows=sheet.getCell(0, r);
        	 String strRow = excelRows.getContents();
             alldata.add(strRow);
           
            
        }
        // }
        ins.close();
        return alldata;
    }
	
	
	public static void main(String[] args) {
		
		
		ExcelTest t = new ExcelTest();
		try {
			List<String> a=t.readExcel("D://Users/Administrator/Desktop/HK140_2.xls", 0, 0);
			System.out.println(a.get(2));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
