package com.homex.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.homex.uilities.Utilities;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class ProjectStarter {

	public WebDriver driver;
	private Utilities configReader;

	// ***** 	DELETE	*****
	
	//   private static final int TIMEOUT = 5; //seconds
	//   private static final int POLLING = 100; //milliseconds
	//private WebDriverWait wait;

	@Before
	public void testSetup(){
		configReader = new Utilities();
		String driverType = configReader.getDriverName();
		String path = System.getProperty("user.dir");

		if(driverType.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", path + "/Driver/geckodriver");
			driver = new FirefoxDriver();
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
		}else {
			throw new RuntimeException("DriverType: "+ driverType + " not recognized");
		}
	}

	public String getParentWindowHandle(){
		return driver.getWindowHandle();
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	
	// ***** CHECK AND DELETE LATER ******
	
	
	/*
	public ProjectStarter(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, TIMEOUT, POLLING);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
	}

	protected void waitForElementToAppear(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	protected void waitForElementToDisappear(By locator) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	protected void waitForTextToDisappear(By locator, String text) {
		wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, text)));
	}
	
	*/
	
}
