package com.homex.pageObjects;

import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.homex.uilities.HomexUtilities;

public class LoginPage{

	WebDriver driver;
	private String parentWindowHandle;
	/*
	ProjectStarter frmwrkStarter;
	Utilities configReader;
	public WebDriver ldriver;

	//Method detail: Invoking dependency injection using pico-container
	//Notes: For a complex project i can move all these to a base page and extend base page in all child pages
	public LoginPage(ProjectStarter frmwrkStarter, Utilities configReader, UserWebDriverWait userWait) {
		this.frmwrkStarter = frmwrkStarter;
		this.configReader = configReader;
		this.userWait = userWait;
		ldriver = frmwrkStarter.driver;
		PageFactory.initElements(ldriver, this);
	}
	
	*/
	
	HomexUtilities utility = null;
	
	@FindBy(css = "input[data-qa='signin_domain_input']")
	WebElement txtSigninDomain;

	@FindBy(css = "button[data-qa='submit_team_domain_button']")
	WebElement btnDomainSubmit;

	@FindBy(id = "email")
	WebElement txtEmail;

	@FindBy(id = "password")
	WebElement txtPassword;

	@FindBy(id = "signin_btn")
	WebElement btnSignin;

	@FindBy(linkText = "use Slack in your browser")
	WebElement linkSlackBrowser;
	
	@FindBy(css = "span[data-qa='channel_sidebar_name_general']")
	WebElement tabGeneral;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
        PageFactory.initElements(driver, this);

	}
	
	private enum Environment{
		APPURL, APPWORKSPACE, USERNAME, PASSWORD
	}

	public void launchWebApplication() throws InterruptedException{
		utility = new HomexUtilities(driver);
		utility.loadProperty();
		ArrayList<String> envDetails = utility.getEnvironment();
		driver.get(envDetails.get(Environment.APPURL.ordinal()));
		
		//Thread.sleep(5000);	//This can be replaced 
		utility.waitForElementToAppear(txtSigninDomain, 20); 
		parentWindowHandle = driver.getWindowHandle();
		txtSigninDomain.sendKeys(envDetails.get(Environment.APPWORKSPACE.ordinal()));
		btnDomainSubmit.click();
		utility.waitForElementToAppear(txtEmail, 10); //Method returns after making sure page is loaded

	}
	
	public void loginWebApplication() throws 
	 InterruptedException{

		ArrayList<String> envDetails = utility.getEnvironment();

		utility.waitForElementToAppear(txtEmail, 10);
		txtEmail.sendKeys(envDetails.get(Environment.USERNAME.ordinal()));
		txtPassword.sendKeys(envDetails.get(Environment.PASSWORD.ordinal()));
		btnSignin.click();
		
		driver.switchTo().window(parentWindowHandle);
		
		utility.waitForElementToAppear(linkSlackBrowser, 10);
		linkSlackBrowser.click();
		
		Thread.sleep(3000);
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1)); 
	    
		utility.waitForElementToAppear(tabGeneral, 10); //Method returns after making sure page is loaded

	}
	
}
