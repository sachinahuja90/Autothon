package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	WebDriver driver;
//	objects for landing page
	
	
	
	@FindBy(how = How.XPATH, using = "//*[@id='main']/span/button")
	WebElement BTN_sideMenu;
	
	@FindBy(how = How.XPATH, using = "//*[@id='navLink']")
	WebElement LNK_login;
	
	@FindBy(how = How.XPATH, using = "//input[@name='username']")
	WebElement EDT_userName;
	
	@FindBy(how = How.XPATH, using = "//input[@name='password']")
	WebElement EDT_passWord;
	
	@FindBy(how = How.XPATH, using = "//input[@name='login']")
	WebElement BTN_logIn;

// objects for admin page
	
	//a[@href="/addMovie"]


	public LoginPage(WebDriver driver) 
	{
		this.driver = driver;
	}

	public void doLogin(String userName, String password) 
	{
		System.out.println("Before Login: "+driver.getTitle());
		BTN_sideMenu.click();
		LNK_login.click();
		EDT_userName.sendKeys(userName);
		EDT_passWord.sendKeys(password);
		BTN_logIn.click();
		System.out.println("After Login: "+driver.getTitle());
//		return PageFactory.initElements(driver, FlightFinderPage.class);
	}

}
