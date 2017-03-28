package threads.test.poi;
/**  
 * 读取Excel时格式错误的自定义Exception  
 * @author http://www.lookhan.com  
 *  
 */  
public class ExcelFormatException extends Exception {   
  
    private static final long serialVersionUID = 3435456589196458401L;   
    private int row;   
    private int column;   
       
    public ExcelFormatException(String message, int row, int column){   
        super(message);   
        this.row = row;   
        this.column = column;   
    }   
    //出错的行   
    public int getRow() {   
        return row;   
    }   
    //出错的列   
    public int getColumn() {   
        return column;   
    }   
       
} 