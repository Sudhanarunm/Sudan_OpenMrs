package org.openmrs.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="username")
	WebElement username;
	
	@FindBy(id="password")
	WebElement password;
	
	@FindBy(id="Laboratory")
	WebElement area;
	
	@FindBy(id="loginButton")
	WebElement loginBtn;
	
	@FindBy(xpath="//a[text()='My Account']")
	WebElement myAccount;
	
	@FindBy(xpath="//h4[contains(text(),'Logged in')]")
	WebElement sucessMessage;

	public void enterUserDetails(String userName,String user_password) {
		
		username.clear();
		username.sendKeys(userName);
		password.clear();
		password.sendKeys(user_password);
		area.click();
		loginBtn.click();
		
	}
	
	public boolean sucessMessage() {
		return sucessMessage.isDisplayed();
	}
	
}
