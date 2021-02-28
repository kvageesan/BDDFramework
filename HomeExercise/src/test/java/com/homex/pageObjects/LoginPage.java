package com.homex.pageObjects;

import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.homex.framework.ProjectStarter;
import com.homex.uilities.Utilities;
import com.homex.uilities.UserWebDriverWait;


public class LoginPage {

	ProjectStarter frmwrkStarter;
	Utilities configReader;
	UserWebDriverWait userWait;
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
	
	
	private enum Environment{
		APPURL, APPWORKSPACE, USERNAME, PASSWORD
	}

	public void launchWebApplication() throws Exception {

		ArrayList<String> envDetails = configReader.getEnvironment();
		ldriver.get(envDetails.get(Environment.APPURL.ordinal()));

		txtSigninDomain.sendKeys(envDetails.get(Environment.APPWORKSPACE.ordinal()));
		btnDomainSubmit.click();
		userWait.waitForElementToAppear(txtEmail, 10); //Method returns after making sure page is loaded

	}
	
	public void loginWebApplication() throws Exception {

		ArrayList<String> envDetails = configReader.getEnvironment();

		userWait.waitForElementToAppear(txtEmail, 10);
		txtEmail.sendKeys(envDetails.get(Environment.USERNAME.ordinal()));
		txtPassword.sendKeys(envDetails.get(Environment.PASSWORD.ordinal()));
		btnSignin.click();
		
		Thread.sleep(2000);
		ldriver.switchTo().window(frmwrkStarter.getParentWindowHandle());
		
		userWait.waitForElementToAppear(linkSlackBrowser, 10);
		linkSlackBrowser.click();
		userWait.waitForElementToAppear(tabGeneral, 10); //Method returns after making sure page is loaded

	}
	
}
