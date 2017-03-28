package threads.test.poi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ReadExcel {

	public static void main(String[] args) throws Exception {
	    HSSFWorkbook wb = null;
	    POIFSFileSystem fs = null;
	    try {
	      fs = new POIFSFileSystem(new FileInputStream("e:\\workbook.xls"));
	      wb = new HSSFWorkbook(fs);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    if(wb!=null){
		    HSSFSheet sheet = wb.getSheetAt(0)!=null?wb.getSheetAt(0):null;
		    if(sheet != null){
			    HSSFRow row = sheet.getRow(0);
			    HSSFCell cell = row.getCell(0);
			    String msg = cell.getStringCellValue();
			    System.out.println(msg);
		    }
	    }
	}
	public static void method2() throws Exception {

	    InputStream is = new FileInputStream("e:\\workbook.xls");
	    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));

	    ExcelExtractor extractor = new ExcelExtractor(wb);
	    extractor.setIncludeSheetNames(false);
	    extractor.setFormulasNotResults(false);
	    extractor.setIncludeCellComments(true);

	    String text = extractor.getText();
	    System.out.println(text);
	}

	public static void method3() throws Exception {
	    HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream("e:\\workbook.xls"));
	    HSSFSheet sheet = wb.getSheetAt(0);

//	    for (Iterator<Row> iter = (Iterator<Row>) sheet.rowIterator(); iter.hasNext();) {
//	      Row row = iter.next();
//	      for (Iterator<Cell> iter2 = (Iterator<Cell>) row.cellIterator(); iter2.hasNext();) {
//	        Cell cell = iter2.next();
//	        String content = cell.getStringCellValue();// 除非是sring类型，否则这样迭代读取会有错误
//	        System.out.println(content);
//	      }
//	    }
//	  }
	}
}
