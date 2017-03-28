package threads.test.poi;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ParseExcel {

	@SuppressWarnings("deprecation")
	private void parseExcel(String excelFile)throws IOException {
        POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(excelFile));//打开Excel文件
        HSSFWorkbook wbHssfWorkbook=new HSSFWorkbook(fs);//打开工作薄
        HSSFSheet sheet=wbHssfWorkbook.getSheetAt(0);//打开工作表

        HSSFRow row=null;
        String data=null;
        for (int i = 0; i <=sheet.getLastRowNum(); i++) {//循环读取每一行
            row =sheet.getRow(i);
            if(row !=null){
	            for (int j = 0; j <= row.getLastCellNum(); j++) {//循环读取每一列
	                switch (row.getCell(j).getCellType()) {//判断单元格的数据类型
	                case HSSFCell.CELL_TYPE_BLANK:
	                    data="";
	                    break;
	                case HSSFCell.CELL_TYPE_NUMERIC:
	                    data=(long)row.getCell(j).getNumericCellValue()+"";
	                    break;
	                default:
	                    data=row.getCell(j).getStringCellValue();
	                    break;
	                }
	                System.out.print(data+"\t");
	            }
            }
            System.out.println();
        }
    }
    /**
     * @param args
     *2012-10-23
     *void
     */
    public static void main(String[] args)throws IOException {
        new ParseExcel().parseExcel("C:/Documents and Settings/Administrator/桌面/wrok/ZF20140107-20140108001.xls");
    }
}
