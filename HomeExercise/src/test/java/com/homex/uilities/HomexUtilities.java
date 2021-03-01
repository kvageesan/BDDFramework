package com.homex.uilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomexUtilities{
	
	private final String propertyFilePath = "configs/GlobalConfiguration.properties";
	private static final int TIMEOUT = 10; //in seconds
	private static final int POLLING = 100; //in milliseconds
	public Properties properties;

	WebDriver driver;

	public HomexUtilities(WebDriver driver) {
		this.driver = driver;
	}

	
	public HomexUtilities() {
		// TODO Auto-generated constructor stub
	}


	public void waitForElementToAppear(WebElement element, int timeout) {
		setDriverWait(timeout).until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementToBeClickable(WebElement element, int timeout) {
		setDriverWait(timeout).until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void waitForElementNotBeingStale(WebElement element, int timeout) {
		setDriverWait(timeout).until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
	}
	
	public void waitForElementNotBeingStaleForClick(WebElement element, int timeout) {
		setDriverWait(timeout).until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
	}

    public void waitForPageLoad(WebDriver driver, int timeout) {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        setDriverWait(timeout).until(pageLoadCondition);
    }
	
	private WebDriverWait setDriverWait(int timeout) {
		if (timeout ==0 ) timeout = TIMEOUT;
		WebDriverWait wait = new WebDriverWait(driver, timeout, POLLING);
		return wait;
	}

	public void loadProperty(){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("GlobalConfiguration.properties not found at " + propertyFilePath);
		} 
		
	}

	//Read driver details from property file
	public String getDriverName() {	
		loadProperty();
		return properties.getProperty("driver");		
	}

	//Read environment details from property file
	public ArrayList<String> getEnvironment() {

		ArrayList<String> envDetails = new ArrayList<String>();
		envDetails.add(properties.getProperty("appurl"));
		envDetails.add(properties.getProperty("appworkspace"));
		envDetails.add(properties.getProperty("username"));
		envDetails.add(properties.getProperty("password"));		
		return envDetails;

	}
	
	//This class can be extended to add more global configs like implicit wait, results config..etc
}
