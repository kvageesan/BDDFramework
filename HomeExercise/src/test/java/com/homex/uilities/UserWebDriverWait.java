package com.homex.uilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.homex.framework.ProjectStarter;

public class UserWebDriverWait extends ProjectStarter{

	WebDriver ldriver = driver;
	private static final int TIMEOUT = 10; //in seconds
	private static final int POLLING = 100; //in milliseconds

	
	public void waitForElementToAppear(WebElement element, int timeout) {
		setDriverWait(timeout).until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementToBeClickable(WebElement element, int timeout) {
		setDriverWait(timeout).until(ExpectedConditions.elementToBeClickable(element));
	}
	
	private WebDriverWait setDriverWait(int timeout) {
		if (timeout ==0 ) timeout = TIMEOUT;
		WebDriverWait wait = new WebDriverWait(ldriver, timeout, POLLING);
		return wait;
	}
	
}
