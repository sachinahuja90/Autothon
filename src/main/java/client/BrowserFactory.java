package client;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserFactory {

	private static BrowserFactory instance = null;

	private BrowserFactory() {

	}

	public static BrowserFactory getInstance() {
		if (instance == null) {
			instance = new BrowserFactory();
		}
		return instance;
	}

	public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();

	public RemoteWebDriver getDriver() {
		return driver.get();
	}

	public final void launchBrowser(String browser,String URL) throws MalformedURLException {
		try {
			driver.set(
					new RemoteWebDriver(new URL(URL), getBrowserCapabilities(browser)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static DesiredCapabilities getBrowserCapabilities(String browserType) {
		if (browserType.equalsIgnoreCase("firefox"))
			return DesiredCapabilities.firefox();
		else if (browserType.equalsIgnoreCase("chrome"))
			return DesiredCapabilities.chrome();
		else if (browserType.equalsIgnoreCase("IE"))
			return DesiredCapabilities.internetExplorer();
		else {
			System.out.println("browser : " + browserType + " is invalid, Launching Firefox as browser of choice..");
			return DesiredCapabilities.firefox();
		}
	}

	public void quitDriver() {
		driver.get().quit();
	}

}
