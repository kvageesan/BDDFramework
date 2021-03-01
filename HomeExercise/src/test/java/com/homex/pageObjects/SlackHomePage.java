package com.homex.pageObjects;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.PageFactory;
import com.homex.uilities.HomexUtilities;

public class SlackHomePage {

	WebDriver driver;
	HomexUtilities utility = null;


	@FindBy(css = "div.ql-editor.ql-blank")
	@CacheLookup
	WebElement txtGeneral;

	@FindBy(css = "button[data-qa='texty_send_button']")
	WebElement btnSend;

	@FindBy(xpath = "//div[@data-qa='message_content']//div[@class='p-rich_text_section']")
	@CacheLookup
	List<WebElement> btnSearchTextList;

	@FindBy(css = "button[data-qa='save_message']")
	WebElement btnSaveMessage;

	@FindBy(css = "button[data-qa='texty_mention_button']")
	WebElement btnTextyMention;

	@FindBy(css = "button[data-qa='top_nav_search']")
	WebElement btnTopNavSearch;

	@FindBy(xpath = "//div[@data-qa='focusable_search_input']/div[@class='ql-editor ql-blank']") 
	@CacheLookup
	WebElement txtTopNavSearch;
	
	@FindBy(xpath = "//div[@data-qa='focusable_search_input']/div[@class='ql-editor']") 
	@CacheLookup
	WebElement txtTopNavSearchEdited;

	@FindBy(css = "button[data-qa='search_input_clear']")
	WebElement txtTopNavClear;

	//Unique search elements are always first to appear. If not we can get collection of elements and choose what's required
	//In this case, search input was unique
	@FindBy(xpath = "//div[@data-id='c-search_autcomplete__suggestion_0']//span[@class='c-search_query_entity__token']")
	@CacheLookup
	WebElement txtTopNavAutoComplete;

	@FindBy(xpath = "(//div[@data-qa='search_message_body']/span)[1]")  
	@CacheLookup
	WebElement lblSavedText;

	@FindBy(css = "button[data-qa='search_input_close']")
	WebElement btnSearchClose;

	@FindBy(css = "span[data-qa='channel_sidebar_name_general']")
	WebElement tabGeneral;

	@FindBy(css = "span[data-qa='channel_sidebar_name_page_psaved']")
	@CacheLookup
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

	public void sendAndSaveText(String text) throws InterruptedException{
		utility = new HomexUtilities(driver);
		WebElement btnSearchText = null;
		
		utility.waitForElementToAppear(txtGeneral, 10); 
		
		txtGeneral.sendKeys(text);
		btnSend.click();
		utility.waitForElementToBeClickable(btnSearchTextList.get(0), 10); 
		for(WebElement e: btnSearchTextList) {
			if(e.getText().equals(text)) {
				btnSearchText = e;
				break;
			}
		}
		if(btnSearchText == null) {
			Assert.fail("Method: sendAndSaveText; Message: Could not find entered string " + text);
		}
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);",btnSearchText);
		
		txtGeneral.click();
		
		utility.waitForPageLoad(driver, 10);
		utility.waitForElementNotBeingStaleForClick(btnSearchText, 10); 
		
		btnSearchText.click();
		btnSaveMessage.click();
	}

	public void searchStarredText(String text) {
		
		driver.navigate().refresh();
		utility.waitForPageLoad(driver, 15);
		btnTopNavSearch.click();
		utility.waitForElementToAppear(txtTopNavSearch, 10); 
		txtTopNavSearch.sendKeys(text);
		
		utility.waitForPageLoad(driver, 10);
		utility.waitForElementToBeClickable(txtTopNavSearchEdited, 10); 
		
		txtTopNavSearchEdited.click();
		txtTopNavSearchEdited.sendKeys(Keys.ENTER);
		
	}

	public void verifySearchText(String text) {
		
		String savedText = null;
		savedText = lblSavedText.getText();
		
		int cntr = 0;
		while(!savedText.equals(text) && cntr++ < 10) {
			try {
				txtTopNavClear.click();
				txtTopNavSearch.sendKeys("has:star");
				txtTopNavSearchEdited.click();
				txtTopNavSearchEdited.sendKeys(Keys.ENTER);
				
				utility.waitForPageLoad(driver, 3);
				utility.waitForElementNotBeingStaleForClick(lblSavedText, 5);
				utility.waitForElementToAppear(lblSavedText, 5);
				
				savedText = lblSavedText.getText();

			}catch(Exception e) {
				//Log.info("Retrying to avoid any stale issues or saved text not appearing");	
			}
		}

		if(!savedText.equals(text)) {
			Assert.fail("Saved message does not appear in has:star search results. Checking for string: " + text);
		}
		btnSearchClose.click();
		utility.waitForPageLoad(driver, 5);

		
	}

	public void verifySavedMessage(String text) {
		WebElement btnSearchText = null;
		int cntr = 0;
		do {
			try {
				utility.waitForElementToAppear(btnSavedItems, 5); 
				btnSavedItems.click();
				utility.waitForPageLoad(driver, 3);
				utility.waitForElementNotBeingStaleForClick(btnSearchTextList.get(0), 5); 
				for(WebElement e: btnSearchTextList) {
					if(e.getText().equals(text)) {
						btnSearchText = e;
						break;
					}
				}
			}catch(Exception e) {
				//Log.info("Retrying to avoid any stale issues or saved text not appearing");	
			}			
		}while(cntr++ < 10);

		if(!btnSearchText.getText().equals(text)) {
			Assert.fail("Saved message does not appear under Saved items. Checking for string: " + text);
		}
		
	}


}
