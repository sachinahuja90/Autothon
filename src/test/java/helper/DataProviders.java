package helper;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.DataProvider;

import customException.IncorrectFileExtensionException;
import excelReaderWriter.ExcelReader;

public class DataProviders {
	

	@DataProvider(name = "Login Data")
	static Object[][] addPlaceGoogleMap() throws FileNotFoundException, IOException, IncorrectFileExtensionException {
		Object[][] obj=ExcelReader.excelDataProvider(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData.xlsx",0);
		return obj;
	}

	

}
