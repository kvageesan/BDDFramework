package com.homex.pageObjects;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.homex.uilities.HomexUtilities;

public class SlackHomePage {

	WebDriver driver;
	HomexUtilities utility = null;


	@FindBy(css = "div.ql-editor.ql-blank")
	WebElement txtGeneral;

	@FindBy(css = "button[data-qa='texty_send_button']")
	WebElement btnSend;

	//With FindBy annotation, parameterization is not allowed.
	//This can be solved by using custom library or another approach(specifying locator individually) of POM. 
	//For simplicity of this project would like to do through FindBy and keep the value hard coded here.
	//@FindBy(xpath = "//div[contains(text(),'Hello there')]")  
	@FindBy(xpath = "//div[@data-qa='message_content']//div[@class='p-rich_text_section']")  
	List<WebElement> btnSearchTextList;

	@FindBy(css = "button[data-qa='save_message']")
	WebElement btnSaveMessage;

	@FindBy(css = "button[data-qa='top_nav_search']")
	WebElement btnTopNavSearch;

	@FindBy(xpath = "//div[@data-qa='focusable_search_input']/div[@class='ql-editor ql-blank']")  
	WebElement txtTopNavSearch;

	@FindBy(css = "button[data-qa='search_input_clear']")
	WebElement txtTopNavClear;

	//Unique search elements are always first to appear. If not we can get collection of elements and choose what's required
	//In this case, search input was unique
	@FindBy(xpath = "//div[@data-id='c-search_autcomplete__suggestion_0']//span[@class='c-search_query_entity__token']")  
	WebElement txtTopNavAutoComplete;

	@FindBy(xpath = "(//div[@data-qa='search_message_body']/span)[1]")  
	WebElement lblSavedText;

	@FindBy(css = "button[data-qa='search_input_close']")
	WebElement btnSearchClose;

	@FindBy(css = "span[data-qa='channel_sidebar_name_general']")
	WebElement tabGeneral;

	@FindBy(css = "span[data-qa='channel_sidebar_name_page_psaved']")
	WebElement btnSavedItems;

	@FindBy(css = "button[data-qa='more_message_actions']")
	WebElement btnMoreAction;

	@FindBy(css = "button[data-qa='delete_message']")
	WebElement btnDeleteMessage;

	@FindBy(css = "button[data-qa='dialog_go']")
	WebElement btnDelete;


	public SlackHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void sendAndSaveText(String text) {
		utility = new HomexUtilities(driver);
		WebElement btnSearchText = null;
		utility.waitForElementToAppear(txtGeneral, 10); 
		txtGeneral.sendKeys(text);
		btnSend.click();
		for(WebElement e: btnSearchTextList) {
			if(e.getText().equals(text)) {
				btnSearchText = e;
				break;
			}
		}
		if(btnSearchText == null) {
			Assert.fail("Method: sendAndSaveText; Message: Could not find entered string " + text);
		}
		utility.waitForElementToBeClickable(btnSearchText, 10); 
		btnSearchText.click();
		btnSaveMessage.click();
	}

	public void searchStarredText(String text) {
		btnTopNavSearch.click();
		utility.waitForElementToAppear(txtTopNavSearch, 10); 
		txtTopNavSearch.sendKeys(text);
		txtTopNavAutoComplete.click();
	}

	public void verifySearchText(String text) throws InterruptedException {
		String savedText = null;
		try {
			savedText = lblSavedText.getText();
			int cntr = 1;
			while((savedText == null || !savedText.equals(text)) && cntr++ < 15) {
				Thread.sleep(2000);
				txtTopNavClear.click();
				txtTopNavSearch.sendKeys("has:star");
				txtTopNavAutoComplete.click();
				savedText = lblSavedText.getText();
			}

		}catch(NotFoundException e) {
			if (savedText == null) {
				Assert.fail("Exception in method verifySearchText : Could not load or search for starred message: " + text);
			}		
		}

		if(!savedText.equals(text)) {
			Assert.fail("Saved message does not appear in has:star search results. Checking for string: " + text);
		}
		btnSearchClose.click();
	}

	public void verifySavedMessage(String text) {
		WebElement btnSearchText = null;

		utility.waitForElementToAppear(btnSavedItems, 10); 
		btnSavedItems.click();

		for(WebElement e: btnSearchTextList) {
			if(e.getText().equals(text)) {
				btnSearchText = e;
				break;
			}
		}
		if(btnSearchText == null) {
			Assert.fail("Method: verifySavedMessage; Message: Could not find starred string in saved messages" + text);
		}
		if(!btnSearchText.getText().equals(text)) {
			Assert.fail("Saved message does not appear under Saved items. Checking for string: " + text);
		}
	}

	//Just written the clean up method for this specific assignment to rerun multiple times. 
	//On an actual implementation, we would not need clean for test like the current user flow. 
	/*
	//It can be very well handled in script through element iteration.
	public void cleanupStarredDate() {
		tabGeneral.click();
		utility.waitForElementToAppear(btnSearchText, 10); 
		btnSearchText.click();
		btnMoreAction.click();
		utility.waitForElementToAppear(btnDelete, 10); 
		btnDelete.click();
	}
	 */


}
