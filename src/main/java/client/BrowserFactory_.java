package client;

import org.openqa.selenium.WebDriver;

public class BrowserFactory_ {
	public IBrowserFactory ibf;
	
	/*
	 * This method will launch browser based on app type and open URL
	 * param : browserName
	 * param : URL 
	 */
	public void launchApplication(String browserName,String URL) {
		
		
	}
	
	
	
	
	public WebDriver getDriver() {		
		return ibf.getDriver();
	}

	

}
