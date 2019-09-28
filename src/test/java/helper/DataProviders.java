package helper;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.DataProvider;

import customException.IncorrectFileExtensionException;
import excelReaderWriter.ExcelReader;

public class DataProviders {
	

	/* Function Decription - Function will generate Data provider for Google Add Place API    
	 * Created by - Sachin Ahuja
	 * Created on - 21st March
	 * Modified by
	 * Modified on
	 * */
	@DataProvider(name = "Add Place Google Map")
	static Object[][] addPlaceGoogleMap() throws FileNotFoundException, IOException, IncorrectFileExtensionException {
		Object[][] obj=ExcelReader.excelDataProvider(System.getProperty("user.dir")+"\\src\\test\\resources\\GoogleMap_TestData.xlsx",0);
		return obj;
	}
	

}
