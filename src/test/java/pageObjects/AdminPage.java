package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class AdminPage {
	
	WebDriver driver;
//	objects for landing page
	
	
	
	@FindBy(how = How.XPATH, using = "//a[@href='/addMovie']")
	WebElement LNK_addMovie;
	
	@FindBy(how = How.CSS, using = "input[name='title']")
	WebElement EDT_movieTitle;
	
	@FindBy(how = How.CSS, using = "input[name='director']")
	WebElement EDT_movieDirector;
	
	@FindBy(how = How.CSS, using = "div#main textarea")
	WebElement EDT_movieDescription;
	
	@FindBy(how = How.CSS, using = "div#main select")
	WebElement EDT_movieCategories;
	
	@FindBy(how = How.CSS, using = "input[name='file']")
	WebElement EDT_movieURL;
	
	@FindBy(how = How.CSS, using = "div#main div:nth-child(7) > div > div > button[type='button'].btn.btn-success")
	WebElement BTN_saveMovie;

// objects for admin page
	
	//a[@href="/addMovie"]


	public AdminPage(WebDriver driver) 
	{
		this.driver = driver;
	}

	public void addMovie(String movieTitle, String movieDirector, String movieDescription, String movieCategories, String movieURL) 
	{
		System.out.println("Before Movie addition: "+driver.getTitle());
		LNK_addMovie.click();
		EDT_movieTitle.sendKeys(movieTitle);
		EDT_movieDirector.sendKeys(movieDirector);
		EDT_movieDescription.sendKeys(movieDescription);
//		.selectDropDownValue(EDT_movieCategories, movieCategories);
		EDT_movieURL.sendKeys(movieURL);
	
		BTN_saveMovie.click();
		System.out.println("After Movie addition: "+driver.getTitle());
//		return PageFactory.initElements(driver, FlightFinderPage.class);
	}


}
