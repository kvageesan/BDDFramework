package com.homex.framework;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.homex.uilities.HomexUtilities;


public class InitializeDriver {

	private WebDriver driver;

	public WebDriver launchBrowser(){
		 HomexUtilities configReader = new HomexUtilities();
		String driverType = configReader.getDriverName();
		String path = System.getProperty("user.dir");

		if(driverType.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", path + "/drivers/geckodriver");
			driver = new FirefoxDriver();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			return driver;
		}else {
			throw new RuntimeException("DriverType: "+ driverType + " not recognized");
		}
	}
	
	
	//	**** DELETE - Change location	********
	
	public void tearDown() {
		driver.quit();
	}
	
}
