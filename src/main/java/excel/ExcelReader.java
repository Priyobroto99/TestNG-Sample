package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * 
 *priobroto99@gmail.com 
 * 
 */

public class ExcelReader {
	File file =null ;
	FileInputStream inputStream = null;
	Workbook wb = null;
	
	int sheetindex = 0;

	public ExcelReader(String filePath,int sheetIndex){

		try {
			file = new File(filePath);
			inputStream = new FileInputStream(file);
			wb = new XSSFWorkbook(inputStream);
			this.sheetindex = sheetIndex;
			String sheetName = wb.getSheetName(sheetindex);
			System.out.println("Datasheet '"+sheetName+"' Found!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getNoOfRows() {
		return (wb.getSheetAt(sheetindex).getLastRowNum() - wb.getSheetAt(sheetindex).getFirstRowNum());
	}

	public String getDataAt(int row, int col) {
		return wb.getSheetAt(sheetindex).getRow(row).getCell(col).getStringCellValue();
	}

}
