package threads.test.poi;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
/**  
 * 测试对Excel的读取  
 * @author http://www.lookhan.com  
 *  
 */  
public class ParseExcel2 {   
  
    public static void main(String[] args){   
        String src = "D:\\lookhan.xlsx";   
        try {   
        	ParseExcel2 test = new ParseExcel2();   
            test.getExcel(src);   
        } catch (Exception e) {   
            ExcelFormatException excelException = (ExcelFormatException)e;   
            System.out.println(excelException.getMessage()+"行："+excelException.getColumn()+"列："+excelException.getRow());   
        }   
    }   
  
    public void getExcel(String src) throws Exception{   
           
        InputStream inp;   
        inp = new FileInputStream(src);   
        HSSFWorkbook wbHssfWorkbook=new HSSFWorkbook(inp);//打开工作薄
        HSSFSheet sheet=wbHssfWorkbook.getSheetAt(0);//打开工作表
//        Workbook wb = WorkbookFactory.create(inp);   
        //读取第一页   
//        Sheet sheet = wb.getSheetAt(0);   
        //从第二行开始到最后一行(第一行是标题)   
        for(int i=1; ;i++){   
            Row row = sheet.getRow(i);   
            //循环四列(Excel是四列)   
            for (int j=0; j<4; j++){   
                Cell cell = row.getCell(j);   
                if(j == 0){   
                    switch (cell.getCellType()){   
                        case Cell.CELL_TYPE_NUMERIC:   
                            if (DateUtil.isCellDateFormatted(cell)){   
                                System.out.println(cell.getDateCellValue());   
                            } else {   
                                throw new ExcelFormatException("格式错误",(cell.getRowIndex()+1),(cell.getColumnIndex()+1));   
                            }   
                            break;   
                        default:   
                            throw new ExcelFormatException("格式错误",(cell.getRowIndex()+1),(cell.getColumnIndex()+1));   
                    }   
                }   
                if(j == 1){   
                    switch (cell.getCellType()){   
                        case Cell.CELL_TYPE_STRING:   
                            System.out.println(cell.getRichStringCellValue());   
                            break;   
                        case Cell.CELL_TYPE_NUMERIC:   
                            if (DateUtil.isCellDateFormatted(cell)){   
                                System.out.println(cell.getDateCellValue().toString());   
                            } else {   
                                System.out.println(cell.getNumericCellValue());   
                            }   
                            break;   
                        case Cell.CELL_TYPE_BOOLEAN:   
                            System.out.println(cell.getBooleanCellValue());   
                            break;   
                        case Cell.CELL_TYPE_FORMULA:   
                            System.out.println(cell.getCellFormula());   
                            break;   
                        default:   
                            throw new ExcelFormatException("格式错误",(cell.getRowIndex()+1),(cell.getColumnIndex()+1));   
                    }   
                }   
                if(j == 2){   
                    switch (cell.getCellType()){   
                        case Cell.CELL_TYPE_STRING:   
                            System.out.println(cell.getRichStringCellValue());   
                            break;   
                        case Cell.CELL_TYPE_NUMERIC:   
                            if (DateUtil.isCellDateFormatted(cell)){   
                                System.out.println(cell.getDateCellValue());   
                            } else {   
                                System.out.println(cell.getNumericCellValue());   
                            }   
                            break;   
                        case Cell.CELL_TYPE_BOOLEAN:   
                            System.out.println(cell.getBooleanCellValue());   
                            break;   
                        case Cell.CELL_TYPE_FORMULA:   
                            System.out.println(cell.getCellFormula());   
                            break;   
                        default:   
                            throw new ExcelFormatException("格式错误",(cell.getRowIndex()+1),(cell.getColumnIndex()+1));   
                    }   
                }   
                if(j == 3){   
                    switch (cell.getCellType()){   
                        case Cell.CELL_TYPE_STRING:   
                            System.out.println(cell.getRichStringCellValue());   
                            break;   
                        case Cell.CELL_TYPE_NUMERIC:   
                            if (DateUtil.isCellDateFormatted(cell)){   
                                throw new ExcelFormatException("格式错误",(cell.getRowIndex()+1),(cell.getColumnIndex()+1));   
                            } else {   
                                System.out.println(cell.getNumericCellValue());   
                            }   
                            break;   
                        case Cell.CELL_TYPE_BOOLEAN:   
                            throw new ExcelFormatException("格式错误",(cell.getRowIndex()+1),(cell.getColumnIndex()+1));   
                        case Cell.CELL_TYPE_FORMULA:   
                            throw new ExcelFormatException("格式错误",(cell.getRowIndex()+1),(cell.getColumnIndex()+1));   
                        default:   
                            throw new ExcelFormatException("格式错误",(cell.getRowIndex()+1),(cell.getColumnIndex()+1));   
                    }   
                }   
            }   
        }   
           
    }   
       
} 