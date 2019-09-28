package excelReaderWriter;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.io.Files;

import customException.IncorrectFileExtensionException;

public class ExcelReader{
	
	public static ArrayList<String> headers;
	
	
	private ArrayList<ArrayList<String>> list;
	public static ArrayList<ArrayList<String>> excelReader(String filePath,int sheetIndex) throws FileNotFoundException,IOException,IncorrectFileExtensionException {
		
		ExcelReader ex=new ExcelReader();
		
		String ext = Files.getFileExtension(filePath);
		if(ext.equals("xls")) {
			ex.list=ex.ReadXLSFile(filePath,sheetIndex);
		}
		else if(ext.equals("xlsx")) { 
			ex.list=ex.ReadXLSXFile(filePath,sheetIndex);
		}
		else {
			throw new IncorrectFileExtensionException("Incorrect file Extension : " + ext );
		}
		return ex.list;
	}
	
	private ArrayList<ArrayList<String>> ReadXLSFile(String filePath,int sheetIndex) throws FileNotFoundException, IOException{
		
		//Create Workbook instance holding reference to .xls file
		HSSFWorkbook oWb = new HSSFWorkbook(new FileInputStream(new File(filePath)));
		
		//Get first/desired sheet from the workbook
		HSSFSheet oWs=oWb.getSheetAt(sheetIndex);
		
		Iterator<Row> rowIterator = oWs.iterator();
		return getExcelData(rowIterator);
		
	}
	
	
	
	
	private ArrayList<ArrayList<String>> ReadXLSXFile(String filePath, int sheetIndex) throws FileNotFoundException, IOException{
		
		//Create Workbook instance holding reference to .xls file
		XSSFWorkbook oWb = new XSSFWorkbook(new FileInputStream(new File(filePath)));
		
		//Get first/desired sheet from the workbook
		XSSFSheet oWs=oWb.getSheetAt(sheetIndex);
		
		//Iterate through each rows one by one
        Iterator<Row> rowIterator = oWs.iterator();
        return getExcelData(rowIterator);
        
        
	}
	
	private ArrayList<ArrayList<String>> getExcelData( Iterator<Row> rowIterator) {
		
		ArrayList<ArrayList<String>> list =null;
        Row rw = rowIterator.next();
        headers= getRowData(rw);
        
		 while (rowIterator.hasNext())
         {
			 list= new ArrayList<ArrayList<String>>();
             Row row = rowIterator.next();
             ArrayList<String> rowData= getRowData(row);
             list.add(rowData);
             rowData=null;
         }
		 return list;
	}
	
	
	

	private ArrayList<String> getRowData(Row row) {
		ArrayList<String> rowData=new ArrayList<String>();
		String temp=null;
		//For each row, iterate through all the columns
		Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext())
        {
            Cell cell = cellIterator.next();
            //Check the cell type and format accordingly
            switch (cell.getCellType())
            {
                case Cell.CELL_TYPE_NUMERIC:
                    temp=cell.getNumericCellValue() + "";
                    break;
                case Cell.CELL_TYPE_STRING:
                    temp=cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                	temp=cell.getBooleanCellValue()+"";
                    break;
                case Cell.CELL_TYPE_BLANK:
                	temp="";
                    break;
                case Cell.CELL_TYPE_FORMULA:
                	temp=cell.getCellFormula();
                    break;
            }
            rowData.add(temp);
        }
        temp=null;
        cellIterator=null;
		return rowData;
	}
	

	/* Function Decription - Function will read Excel file, path provided in parameter and feed the Data provider    
	 * Created by - Sachin Ahuja
	 * Modified by
	 * Modified on
	 * */
	public static Object[][] excelDataProvider(String filePath,int sheetIndex) throws FileNotFoundException, IOException, IncorrectFileExtensionException {
		
		ArrayList<ArrayList<String>> excelList=ExcelReader.excelReader(filePath,sheetIndex);
		Object[][] obj = new Object[excelList.size()][2];
		int i=0;
		for(ArrayList<String> excelRow:excelList) {
			for(int j=0;j<excelRow.size();j++) {
				obj[i][0]=ExcelReader.headers;
				obj[i][1]=excelRow;			
			}
			i++;
		}
		return obj;
	}
	
	
	

}
