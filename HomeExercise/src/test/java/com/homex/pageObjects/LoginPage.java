package com.homex.pageObjects;

import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.homex.uilities.HomexUtilities;

public class LoginPage{

	WebDriver driver;
	private String parentWindowHandle;
	
	HomexUtilities utility = null;
	
	@FindBy(css = "input[data-qa='signin_domain_input']")
	@CacheLookup
	WebElement txtSigninDomain;

	@FindBy(css = "button[data-qa='submit_team_domain_button']")
	WebElement btnDomainSubmit;

	@FindBy(id = "email")
	@CacheLookup
	WebElement txtEmail;

	@FindBy(id = "password")
	WebElement txtPassword;

	@FindBy(id = "signin_btn")
	WebElement btnSignin;

	@FindBy(linkText = "use Slack in your browser")
	@CacheLookup
	WebElement linkSlackBrowser;
	
	@FindBy(css = "span[data-qa='channel_sidebar_name_general']")
	@CacheLookup
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
		
		utility.waitForPageLoad(driver, 15);
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
		
		utility.waitForPageLoad(driver, 15);
		utility.waitForElementToAppear(linkSlackBrowser, 10);
		linkSlackBrowser.click();
		
		Thread.sleep(3000);
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1)); 
	    
		utility.waitForElementToAppear(tabGeneral, 10); //Method returns after making sure page is loaded

	}
	
}
