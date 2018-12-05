package utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import executionEngine.DriverScript;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;



public class ExcelUtils {
	
		private static XSSFSheet ExcelWSheet;
		private static XSSFWorkbook ExcelWBook;
		private static XSSFCell Cell;
		private static CellStyle style1;
		private static CellStyle style2;
		private static Font passFont;
		private static Font failFont;
		
//		Method to set the path of excel file...
		public static void setExcelFile(String path)throws Exception{
			FileInputStream ExcelFile=new FileInputStream(path);
			ExcelWBook=new XSSFWorkbook(ExcelFile);
			style1 = ExcelWBook.createCellStyle();
			style2 = ExcelWBook.createCellStyle();
			passFont = ExcelWBook.createFont();
			failFont= ExcelWBook.createFont();
			passFont.setColor(IndexedColors.GREEN.getIndex());
			failFont.setColor(IndexedColors.RED.getIndex());
			
			List<String> sheetNames = new ArrayList<String>();
			for (int i=0; i<ExcelWBook.getNumberOfSheets(); i++) {
			    sheetNames.add( ExcelWBook.getSheetName(i) );
			}
			
		}
		
//		Method to give the row count...
		public static int getRowCount(String SheetName){
			ExcelWSheet=ExcelWBook.getSheet(SheetName);
			int number=ExcelWSheet.getLastRowNum()+1;
			//System.out.println(number);
			return number;
		}
		
//		Method to get cell data...		
		 public static String getCellData(int RowNum, int ColNum, String SheetName ) throws Exception{
             try{
             	ExcelWSheet = ExcelWBook.getSheet(SheetName);
                Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
                String CellData = Cell.getStringCellValue();
                return CellData;
              }
             catch (Exception e){
            	 return "";                
              }
			
		 }

//		Method to set the cell data...
		 public static void setCellData(int rowNum,int colNum,String result,String SheetName)  {	
		 try{	   
			 	ExcelWSheet = ExcelWBook.getSheet(SheetName);
	    	   XSSFRow Row  = ExcelWSheet.getRow(rowNum);
	           Cell = Row.getCell(colNum);
	           if (Cell == null) {
	        	   Cell = Row.createCell(colNum);
	        	   //Cell.setCellValue(result);
	            }
//	           else {
//	                Cell.setCellValue(result);
//	            }
	           if(result.equals("Pass")){
	        	   Cell.setCellValue(result);
	        	   style1.setFont(passFont);
	        	   Cell.setCellStyle(style1);
	           }
	           else{
	        	   Cell.setCellValue(result);
	        	   style2.setFont(failFont);
	        	   Cell.setCellStyle(style2);
	        	   
	           }
	           	           
	             FileOutputStream fileOut = new FileOutputStream(new File(DriverScript.testCasesPath));
	             ExcelWBook.write(fileOut);	             
	             fileOut.close();	            
	         }
		 catch(Exception e){
	         }
		 
		 }	 
		 
//		Method to get the first row where from particular test case start in Test Step sheet...
		public static int getRowContains(String testCaseId,int colNum,String SheetName) throws Exception{
			int rowCount=ExcelUtils.getRowCount(SheetName);
			//System.out.println("Total test steps :"+(rowCount-1));
			int i;
			for(i=0;i<rowCount;i++)
			{
				if(ExcelUtils.getCellData(i, colNum, SheetName).equalsIgnoreCase(testCaseId)){
					break;
				}				
			}
			return i;
		}
		 
		 
//		Method to get the count of test steps of particular test case in Test Steps Sheet...
		public static int getTestStepsCount(int iTestCaseStart,String sTestCaseId,String SheetName) throws Exception{
			for(int i=iTestCaseStart;i<=ExcelUtils.getRowCount(SheetName);i++)
			{
				//System.out.println("getTestStepCount Loop"+i+getCellData(i,1,SheetName));
				if(!sTestCaseId.equals(ExcelUtils.getCellData(i, 0, SheetName))){
					//System.out.println("getTestStepCount Loop if condition"+i);
					int number=i;
					return number-1;
				}
			}
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int number=ExcelWSheet.getLastRowNum();
			return number;
			
			
		}
		
		 
		 
		 
		 
		 		 		 
}

