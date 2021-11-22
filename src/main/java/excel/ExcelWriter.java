package excel;

import java.io.FileOutputStream;



import org.apache.poi.hssf.util.HSSFColor;

import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.CellStyle;

import org.apache.poi.ss.usermodel.Font;

import org.apache.poi.ss.usermodel.IndexedColors;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelWriter {

	private String FilePath=null;

	private Workbook workbook=null;

	private Sheet sheet= null;

	private Row row1=null;

	private int noOfColumns=0;

	private int rowCounter=0;
	
	public int getRowNumber() {
		return rowCounter;
	}


	private static ExcelWriter singleInstance=null;



	public static ExcelWriter getInstance() {
		try {
		if(singleInstance==null) {
			singleInstance = new ExcelWriter();
			return singleInstance;
		}else {
			return singleInstance;
		}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void mergeSteps(int from , int to) {
		int firstRow = from;
		int firstCol = 0;
		int lastRow = to;	
		int lastCol = 0;
		
		System.out.println("from : "+from+" to : "+to);
				
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol+1, lastCol+1));

	}
	
	public void removeRow(int row) {
		sheet.removeRow(sheet.getRow(row));
		rowCounter=rowCounter-1;
	}


	private ExcelWriter() {
		super();
	}



	public void createFile(String sheetName,String filePath)

	{

		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet(sheetName);
		FilePath = filePath;

	}
	
	public void createNewSheet(String SheetName) {
		sheet = workbook.createSheet(SheetName);
		rowCounter = 0;
	}



	public void header(String columns[]) {


		try {
			noOfColumns=columns.length;

			Font headerFont = workbook.createFont();

			headerFont.setFontHeightInPoints((short) 14);

			headerFont.setColor(IndexedColors.WHITE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();

			headerCellStyle.setFont(headerFont);

			headerCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
			headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			headerCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THICK);

			// Create a Row

			Row headerRow = sheet.createRow(rowCounter);


			for (int i = 0; i < columns.length; i++)

			{

				Cell cell = headerRow.createCell(i);

				cell.setCellValue(columns[i]);

				cell.setCellStyle(headerCellStyle);
				sheet.autoSizeColumn(i);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	// Create Other rows and cells with contacts data



	public void createRow()

	{
		rowCounter++;
		this.row1 = sheet.createRow(rowCounter);

	}


	public void setData(String data,int column)

	{

		this.row1.createCell(column).setCellValue(data);

		sheet.autoSizeColumn(column);
	}



	public void closeSheet() {


		try {

			// Write the output to a file

			FileOutputStream fileOut = new FileOutputStream(FilePath);

			workbook.write(fileOut);

			fileOut.close();

		}

		catch(Exception e) {

			e.printStackTrace();

		}

	}

}

