package com.homex.stepDefinition;

import com.homex.framework.InitializeDriver;
import com.homex.pageObjects.LoginPage;
import com.homex.pageObjects.SlackHomePage;

import java.util.Random;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinition {
	
	WebDriver driver;
	LoginPage loginPage =  null;
	SlackHomePage slackHomePage =  null;
	private String string;


	@Given("I login to snap web application")
	public void i_login_to_snap_web_application() throws InterruptedException {
		InitializeDriver initDriver = new InitializeDriver();
		driver = initDriver.launchBrowser();
		loginPage = new LoginPage(driver);
		loginPage.launchWebApplication();
		loginPage.loginWebApplication();
	}
	
	@Given("I send and save a random message")
	public void i_send_and_save_a_message() {
		string = "test"+Integer.toString(10000 + new Random().nextInt(99999));
		slackHomePage = new SlackHomePage(driver);
		slackHomePage.sendAndSaveText(string);
	}

	@When("I search for starred message using {string}")
	public void i_search_for_starred_message_using(String string) {
		slackHomePage.searchStarredText(string);
	}

	@Then("I verify the message is displayed")
	public void i_verify_message_is_displayed() throws InterruptedException {
		slackHomePage.verifySearchText(string);
	}

	@And("I verify the message  is displayed under saved message section")
	public void i_verify_search_string_is_displayed_under_saved_messages_section() {
		slackHomePage.verifySavedMessage(string);
	}

}
