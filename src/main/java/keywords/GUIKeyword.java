package keywords;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import client.BrowserFactory;
import client.BrowserFactory_;

import client.IBrowserFactory;
import customException.ElementNotClickable;
import customException.ElementNotFound;
import reportLogger.ReportFactory;

public class GUIKeyword {

	public static String ATTR_DATE = "datetime";
	public static String ATTR_CHAR = "varchar";
	public static String ATTR_VAL = "VALUE";
	public static String ATTR_TD ="td";
	public static String ATTR_ID ="id";
	public static String ATTR_TR ="tr";
	public static String ATTR_TITLE ="title";
	public static String ATTR_ONCLICK ="onclick";
	public static String ATTR_COL ="columnname";
	public static String ALT_STR1 ="REPLACE1";
	public static String ALT_STR2 ="REPLACE2";
	public static String ALT_STR3 ="REPLACE3";
	public static String ATTR_SEL ="selected";
	public static String ALTER_STR="#REPLACE"; 
	
	public String getScreenshot_1(String screenshotName) {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String destination = null;
        try {
        	File src= ((TakesScreenshot)new BrowserFactory_().getDriver()).getScreenshotAs(OutputType.FILE);
        	destination = ReportFactory.reportFolder+"\\"+screenshotName+dateName+".png";
        	FileUtils.copyFile(src, new File(destination));
			return destination;
		} catch (IOException e) {
			ReportFactory.getInstance().info("Error in taking Screen shots");
		}
		return destination;
        
   	}
	
	
	public WebDriverWait explicitWait(WebDriver driver) {
		WebDriverWait w1=new WebDriverWait(driver, 40);
		return w1;
	}
	
	
	
	/**
	 * Returns the immediate child element of a given element
	 *
	 * @param driver
	 * @param ele
	 * @author - Shashank Jain
	 */

	public WebElement getSuccessor(RemoteWebDriver driver, By ele) {
	    try{
	        WebElement child = null;
	        if(ele != null){
	            child = driver.findElement(ele).findElement(By.xpath(".//"));
	        }
	        return child;
	    }
	    catch (Exception e){
	        e.getMessage();
	        throw e;
	    }
	}

	/**
	 * Returns a list of all child elements of a given element
	 *
	 * @param driver
	 * @param ele
	 * @author - Shashank Jain
	 */

	public List<WebElement> getSuccessors(WebDriver driver, By ele) {
	    try{
	       List<WebElement> childList = null;
	       if(ele != null){
	           childList = driver.findElement(ele).findElements(By.xpath(".//*"));
	       }
	       return childList;
	    }
	    catch (Exception e){
	        e.getMessage();
	        throw e;
	    }
	}

	/**
	 * This method is used, to get text of Webelement and store into the String varriable.
	 *
	 * @param getDriver
	 * @param ele
	 * @return
	 * @throws Throwable
	 * @author Sachin Ahuja
	 */


	public String getText(WebDriver getDriver, WebElement ele) throws Throwable {
	    try {
	    	ReportFactory.getInstance().debug("element text is" + ele.getText());
	        return ele.getText();
	    } catch (Throwable e) {
	    	ReportFactory.getInstance().error("Error while getting text from :" + ele);
	        throw e;
	    }
	}

	public void doubleClick(WebDriver driver,By by) {
		Actions actions=new Actions(driver);
		WebElement locator = driver.findElement(by);
		actions.doubleClick(locator).perform();
		ReportFactory.getInstance().debug("Double clicked on element"+ locator);
	}
	

	
	/**
	 * Wait for the element to have specified property value
	 *
	 * @param driver
	 * @param ele
	 * @author- Sachin Ahuja
	 */

	public void waitForElementToLoadPropert(WebDriver driver,By ele,String property,String value) {
	    try{
	        WebDriverWait wait = new WebDriverWait(driver,40);
	        wait.until(ExpectedConditions.attributeContains(ele,property,value));
	        ReportFactory.getInstance().debug("Element =>"+ele +" loaded with property "+property+" = >"+value);
	    }
	    catch (Exception e){
	    	ReportFactory.getInstance().debug("Element =>"+ele +" not loaded with property "+property+" = >"+value);
	        throw e;
	    }
	}


	/**
	 * this will check element is clickable by validating mouse cursor
	 *
	 * @param driver
	 * @param ele
	 * @author- Sachin Ahuja
	 */

	public boolean isClickable(WebDriver driver,By ele) {
	    try{
	        WebDriverWait wait = new WebDriverWait(driver,20);
	        wait.until(d -> d.findElement(ele).getCssValue("cursor").equalsIgnoreCase("pointer"));
	        ReportFactory.getInstance().debug("Element "+ele+" is clickable");
	        return true;
	    }
	    catch (Exception e){
	    	ReportFactory.getInstance().debug("Element =>"+ele+" is not clickable");
	        return false;
	    }

	}

	/**
	 * Function will check the presence of Alert on the page.
	 *
	 * @param driver
	 * @author- Sachin Ahuja
	 */



	public boolean isAlertPresent(WebDriver driver){
	    boolean foundAlert = false;
	    WebDriverWait wait = new WebDriverWait(driver, 10/*timeout in seconds*/);
	    try {
	        wait.until(ExpectedConditions.alertIsPresent());
	        foundAlert = true;
	    } catch (Exception e) {
	        foundAlert = false;
	    }
	    return foundAlert;
	}

	
	WebElement we;
	public static int waitTime;
	public boolean verifyElementPresence(String nameofObject,By selector) throws ElementNotFound{
		
		try {
			getElement(selector);
			we.isDisplayed();
			ReportFactory.getInstance().info("WebElement Present : "+nameofObject+" ["+selector+"]");
			return true;
		}
		catch(Exception e) {
			throw new ElementNotFound(nameofObject+" ["+selector+"]");
		}
		
	}
	
	public boolean verifyElementEnabled(String nameofObject,By selector) {
		we=null;
		try {
			getElement(selector);
			we.isEnabled();
			ReportFactory.getInstance().info("WebElement Enabled : "+nameofObject+" ["+selector+"]");
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	public void navigateTo(String URL) {
		BrowserFactory.getInstance().getDriver().get(URL);
		ReportFactory.getInstance().info("Navigated to URL : "+ URL);
	}
	
	
	public void navigateToPreviousPage() throws Exception {
		BrowserFactory.getInstance().getDriver().navigate().back();
		ReportFactory.getInstance().info("Navigated back to Prevoius page: "+ BrowserFactory.getInstance().getDriver().getCurrentUrl());
	}
	
	
	public void click(String nameofObject,By selector) throws ElementNotFound, ElementNotClickable {
		verifyElementPresence(nameofObject,selector);
		try {
			we.click();
			waitForWindowToLoad();
		}
		catch (Exception e) {
			throw new ElementNotClickable("WebElement "+nameofObject+" ["+selector+ "]");
		}
		ReportFactory.getInstance().info("WebElement Clicked : "+nameofObject+" ["+selector+"]");
		
	}
	
	public void mouseHover(String nameofObject,By selector) throws ElementNotFound {
		we=null;
		verifyElementPresence(nameofObject,selector);
		new Actions(BrowserFactory.getInstance().getDriver()).moveToElement(we).build().perform();;
		ReportFactory.getInstance().info("Mouse hovered Webelement : "+nameofObject+" ["+selector+"]");
	}
	
	
	
	public void typeText(String nameofObject, By selector,String text) throws ElementNotFound{
		we=null;
		verifyElementPresence(nameofObject, selector);
		we.sendKeys(text);
		ReportFactory.getInstance().info(text +" enterted in WebElement : "+nameofObject+" ["+selector+"]");
	}
	
	public void enterKey(String nameofObject, By selector) throws ElementNotFound{
		we=null;
		verifyElementPresence(nameofObject, selector);
		we.sendKeys(Keys.RETURN);
		ReportFactory.getInstance().info("Enter key Pressed for WebElement : "+nameofObject+" ["+selector+"]");
	}
	
	
	public String getBrowserLogs() {
		LogEntries logEntries = BrowserFactory.getInstance().getDriver().manage().logs().get(LogType.BROWSER);
		String logs="";
		for (LogEntry log : logEntries) {
			logs=logs+(new Date(log.getTimestamp()) + " " + log.getLevel() + " " + log.getMessage())+"\n";
		}
		//ReportFactory.getInstance().info("Browser console logs fetched : "+logs);
		return logs;
	}
	

	public void pressEnterKey(String nameofObject, By selector) throws ElementNotFound {
		we = null;
		verifyElementPresence(nameofObject, selector);
		we.sendKeys(Keys.RETURN);
		ReportFactory.getInstance().info("Enter key pressed in WebElement : " + nameofObject + " [" + selector + "]");
	}
	
	
	public boolean verifyElementWithTextPresence(String text) throws ElementNotFound{
		we=null;
		try {
			BrowserFactory.getInstance().getDriver().findElement(By.xpath("//*[text()='"+text+"']")).isDisplayed();
			return true;
		}
		catch(Exception e) {
			throw new ElementNotFound("Object with Text : \""+text+"\"");
		}
	}
	

   	public String getScreenshot(String screenshotName) {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String destination = null;
        try {
        	File src= ((TakesScreenshot)BrowserFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
        	destination = ReportFactory.reportFolder+"\\"+screenshotName+dateName+".png";
        	FileUtils.copyFile(src, new File(destination));
			return destination;
		} catch (IOException e) {
			ReportFactory.getInstance().info("Error in taking Screen shots");
		}
		return destination;
        
   	}
   	
   	public void getElement(By by) {
   		we=null;
   		List<WebElement> lst= BrowserFactory.getInstance().getDriver().findElements(by);
   		we= lst.get(0);
   	}
   	
   	public String getText(String nameofObject, By selector) throws ElementNotFound {
   		we=null;
   		verifyElementPresence(nameofObject,selector);
   		String str=we.getText();
   		ReportFactory.getInstance().info("Text : "+str +" fetched from Webelement : "+nameofObject+" ["+selector+"]");
   		return str;
   	}
   	
   	public String getText(String nameofObject, WebElement we) throws ElementNotFound {
   		String str=we.getText();
   		ReportFactory.getInstance().info("Text : "+str +" fetched from Webelement : "+we);
   		return str;
   	}
	
   	public List<WebElement> getChildElementList(String nameofObject, By parentSelector,By childSelector) throws ElementNotFound {
   		verifyElementPresence(nameofObject,parentSelector);
   		implicitWait();
   		List<WebElement> lst=we.findElements(childSelector);
   		ReportFactory.getInstance().info("Child element fetched from Webelement : "+nameofObject+" ["+parentSelector+"  "+childSelector+" ]");
   		return lst;
   	}
	
   	public List<WebElement> getElementList(String nameofObject, By selector) throws ElementNotFound {
   		List<WebElement> lst=we.findElements(selector);
   		ReportFactory.getInstance().info("Webelement list fetched : "+nameofObject+" ["+selector+"]");
   		return lst;
   	}
	
   	public WebElement getChildElement(String nameofObject, By parentSelector, By childSelector) throws ElementNotFound {
   		getElement(parentSelector);
   		return we.findElement(childSelector);
   	}
   	
   	public boolean verifyElementPresence(WebElement wb) throws ElementNotFound{
		try {
			wb.isDisplayed();
			ReportFactory.getInstance().info("WebElement Present : "+wb);
			return true;
		}
		catch(Exception e) {
			throw new ElementNotFound(wb.toString());
		}
		
	}
	
   	public void selectByVisibleText(String nameOfObject,By selector, String text) throws ElementNotFound {
   		verifyElementPresence(nameOfObject,selector);
		new Select(we).selectByVisibleText(text);
		ReportFactory.getInstance().info("Visible text : "+text+" selected in Selectionbox : "+nameOfObject+" ["+selector+"]");
   	}
   	
   	public void selectByValue(String nameOfObject,By selector, String value) throws ElementNotFound {
   		verifyElementPresence(nameOfObject,selector);
		new Select(we).selectByVisibleText(value);
		ReportFactory.getInstance().info("Value : "+value+" selected in Selectionbox : "+nameOfObject+" ["+selector+"]");
   	}
   	
   	
   	public void implicitWait() {
   		BrowserFactory.getInstance().getDriver().manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
   		
   	}
	
   	public void explicitWait(By by) {
   		WebDriverWait wait = new WebDriverWait(BrowserFactory.getInstance().getDriver(),waitTime);
   	    wait.until(ExpectedConditions.elementToBeClickable(by));
   	}
   	
   	
   	public void scrollWindow(int x, int y) {
   		getJsExecutorObject().executeScript("window.scrollBy("+x+","+y+")");
   	}
   	
   	public void scrollWindow(WebElement we) {
   		getJsExecutorObject().executeScript("arguments[0].scrollIntoView();", we);
   	}
   	
   	public void scrollWindow(By by) {
   		waitForElement(by);
   		getElement(by);
   		getJsExecutorObject().executeScript("arguments[0].scrollIntoView();", we);
   		implicitWait();
   		waitForWindowToLoad();
   	}
   	
   	private static JavascriptExecutor js=null;
   	
   	private static JavascriptExecutor getJsExecutorObject() {
   		if(js==null)
   			js=(JavascriptExecutor) BrowserFactory.getInstance().getDriver();
   		return js;
   	}
   	


   	public void click(String nameofObject,String selector) throws ElementNotFound {
		
		verifyElementPresence(nameofObject,By.xpath(selector));
		if(!we.isDisplayed()) {
			scrollWindow(we);
		}
		highlightElement(we);
		we.click();
		ReportFactory.getInstance().info("WebElement Clicked : "+nameofObject+" [xpath :"+selector+"]");
	}
   	
	public void highlightElement(WebElement element) {
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor js = (JavascriptExecutor) BrowserFactory.getInstance().getDriver();
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: yellow; border: 2px solid yellow;");
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
		}
	}

	public void waitForWindowToLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(BrowserFactory.getInstance().getDriver(), waitTime);
        wait.until(pageLoadCondition);
    }
	
	public void waitForElement(By by) {
   		WebDriverWait wait = new WebDriverWait(BrowserFactory.getInstance().getDriver(),waitTime);
   	    wait.until(ExpectedConditions.elementToBeClickable(by));
   	}
	

	public boolean verifyPageTitle(String expected) throws ElementNotFound {
		String actual = "";
		try {
			actual = BrowserFactory.getInstance().getDriver().getTitle();
			Assert.assertEquals(actual, expected);
			ReportFactory.getInstance().info("Current Page title : " + actual + " matches with " + expected);
			return true;
		} catch (Exception e) {
			ReportFactory.getInstance().info("Current Page title : " + actual + " doesn't matches with " + expected);
			return false;

		}
	}

	/**
	 * Wait for an element to load
	 *
	 * @param driver
	 * @param ele
	 * @author- Sachin Ahuja
	 */

	public void waitForElementLoadWithText(WebDriver driver, By ele,String text) throws Throwable
	{
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, 40);
	        wait.until(ExpectedConditions.textToBe(ele,text));
	    }
	    catch (Throwable e){
	    	ReportFactory.getInstance().debug("Element =>"+ele+" not loaded after wait for 40 seconds.");
	    }

	}

	/**
	 * Wait for element to load
	 *
	 * @param driver
	 * @param ele
	 * @author- Sachin Ahuja
	 */

	public void waitForElementLoad(WebDriver driver,By ele) throws Throwable
	{
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, 40);
	        wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
	        ReportFactory.getInstance().debug("Child Element =>"+ele+" loaded.");
	    }
	    catch (Throwable e){
	    	ReportFactory.getInstance().debug("Element =>"+ele+" not found after wait for 40 seconds.");
	        throw e;
	    }

	}
	/**
	 * This method will scroll down the main browser window to the bottom of the page
	 * @param driver
	 * @author Sachin Ahuja
	 */

	public void scrollDownPage(WebDriver driver) throws Throwable {
	    try {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        Integer intPageHeight = Integer.valueOf(js.executeScript("return document.body.scrollHeight").toString());
	        for (int i = 0; i <= intPageHeight; i = i + 100) {
	            js.executeScript("window.scrollBy(0,100)");
	        }
	        ReportFactory.getInstance().debug("Scrolled down to the bottom of the page." );
	    }
	    catch (Throwable e){
	    	ReportFactory.getInstance().debug("Error while scrolling down the page.");
	        throw e;
	    }
	}

	/**
	 * get Attribute of WebElement
	 * @param ele WebElement
	 * @param attribute String
	 * @return String
	 * @author Sachin Ahuja
	 */
	public String getAttribute(WebElement ele, String attribute) throws Throwable{
	    try {
//	        logger.debug("getAttribute== " + ele.getAttribute(attribute));
	        return ele.getAttribute(attribute);
	    } catch (Throwable e) {
//	        logger.error("Error while getting attribute : "+attribute+" of element =>"+ele);
	        throw e;
	    }
	}
	/**
	 * This method is used to click on element
	 *
	 * @param getDriver
	 * @param ele
	 * @param timeout
	 * @throws Throwable
	 * @author Jayendra Kumar
	 */
	public void click(WebDriver getDriver, By ele) throws Throwable {
	    try {
	        WebElement element = getDriver.findElement(ele);
	        new WebDriverWait(getDriver, 60).until(ExpectedConditions.elementToBeClickable(element));
	        element.click();
//	        logger.info("Clicked on Element");
	    } catch (Throwable e) {
//	        logger.info("Element is not clickable" + e.getMessage());
	        throw e;
	    }
	}
	/**
	 * This method is used to send keys.
	 *
	 * @param getDriver
	 * @param ele
	 * @param timeout
	 * @param value
	 * @throws Throwable
	 * @author Jayendra Kumar
	 */
	public void sendKeys(WebDriver getDriver, By ele, int timeout, String value) throws Throwable {
	    try {
	        WebElement element = getDriver.findElement(ele);
	        new WebDriverWait(getDriver, timeout).until(ExpectedConditions.visibilityOf(element));
	        element.clear();
	        element.sendKeys(value);
//	        logger.info("send keys");
	    } catch (Throwable e) {
//	        logger.info("error message" + e.getMessage());
	        throw e;
	    }
	}

	/**
	 * This method is used, to get text of Webelement and store into the String varriable.
	 *
	 * @param getDriver
	 * @param ele
	 * @return
	 * @throws Throwable
	 * @author Jayendra Kumar
	 */


	public String getText(WebDriver getDriver, By ele) throws Throwable {
	    try {
	        explicitWait(getDriver).until(ExpectedConditions.visibilityOfElementLocated(ele));
	        WebElement element = getDriver.findElement(ele);
//	        logger.info("element text is" + element.getText());
	        return element.getText();
	    } catch (Throwable e) {
//	        logger.debug("common.getText " + e);
	        throw e;

	    }
	}

	/**
	 * This method will scroll down the Grid for which element is provided
	 * get Attribute of WebElement
	 * @param ele WebElement
	 * @param driver Webdriver
	 * @author Sachin Ahuja
	 */


	public void scrollDownGrid(WebDriver driver,By ele) throws  Throwable{
	    try {
	        Actions action = new Actions(driver);
	        WebElement we=driver.findElement(ele);
	        we.click();
	        action.sendKeys(Keys.PAGE_DOWN).build().perform();
	        action.sendKeys(Keys.PAGE_DOWN).build().perform();
	        Thread.sleep(3000);
	        ReportFactory.getInstance().debug("Webelement => "+we+" scrolled down.");
	    }
	    catch (Throwable e){
	    	ReportFactory.getInstance().error("Error while scrolling down on element =>"+ele);
	        throw e;
	    }
	}
	
	public static WebElement getPredecessor(WebElement elem) {
		WebElement wElem = null;
		if (elem != null) {
			wElem = elem.findElement(By.xpath(".."));
		}
		return wElem;
	}
	
	
	public static List<WebElement> getSuccessor(WebElement elem) {
		List<WebElement> chldList = null;
		if (elem != null) {
			chldList = elem.findElements(By.xpath(".//*"));
		}
		return chldList;
	}
	
	
	public static List<WebElement> getSuccessorByTagName(WebElement elem, String tag){
		List<WebElement> chldList=null;
		if(elem!=null){
			chldList=elem.findElements(By.tagName(tag));
		}
		return chldList;
	}
	
	public static WebElement getImmediateSuccessor(WebElement elem) {

		WebElement chldList = null;
		if (elem != null) {
			chldList = elem.findElement(By.xpath("./*"));
		}
		return chldList;
	}
	
	public static WebElement getSibling(WebElement elem, int idx) {
		WebElement element = null;
		element = getPredecessor(elem);
		List<WebElement> childList = getSuccessor(element);
		return childList.get(idx);
	}
	
	
	public static List<WebElement> getAllSiblingChildren(WebElement elem) {
		WebElement predecessor = getPredecessor(elem);
		return (getSuccessor(predecessor));
	}
	
	
	public static WebElement getNxtSib(WebElement elem) {
		WebElement element = null;
		element = getPredecessor(elem);
		List<WebElement> childList = getSuccessor(element);
		int currIdx = childList.indexOf(elem);
		return childList.get(currIdx + 1);
	}
	
	
	public static WebElement getPrevSib(WebElement elem) {
		WebElement element = null;
		element = getPredecessor(elem);
		ReportFactory.getInstance().info("element :" + element.getTagName());
		List<WebElement> childList = getSuccessor(element);
		ReportFactory.getInstance().info("Size : " + childList.size());
		int currIdx = childList.indexOf(elem);
		ReportFactory.getInstance().info("currIdx : " + currIdx);
		return childList.get(currIdx - 1);
	}

	
	public static List<WebElement> getTdElem(WebElement elem) {
		List<WebElement> chldList = null;
		if (elem != null) {
			chldList = elem.findElements(By.tagName(ATTR_TD));
		}
		return chldList;
	}
	
	public static List<WebElement> getTrElem(WebElement elem) {
		List<WebElement> chldList = null;
		if (elem != null) {
			chldList = elem.findElements(By.tagName(ATTR_TR));
		}
		return chldList;
	}
}
