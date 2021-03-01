package com.homex.framework;

import org.openqa.selenium.WebDriver;

import com.homex.pageObjects.*;

//Dependency injection
public class TestConext {
	
	private WebDriver driver;
	private LoginPage loginPage;
	private SlackHomePage slackHome;
	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public LoginPage getLoginPage() {
		return loginPage;
	}

	public SlackHomePage getSlackHome() {
		return slackHome;
	}

	public void initializePageObjectClasses() {
		loginPage = new LoginPage(driver);
		slackHome = new SlackHomePage(driver);
	}

}
