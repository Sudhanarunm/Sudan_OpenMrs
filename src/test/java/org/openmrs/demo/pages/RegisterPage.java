package org.openmrs.demo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {
	
	WebDriver driver;
	WebDriverWait wait;
	
	public RegisterPage(WebDriver driver) {
		this.driver=driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Timeout in seconds
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name="givenName")
	WebElement givenName;
	
	@FindBy(name="familyName")
	WebElement familyName;
	
	@FindBy(id="gender-field")
	WebElement gender;
	
	@FindBy(id="birthdateMonth-field")
	WebElement birth_month;
	
	@FindBy(id="birthdateDay-field")
	WebElement birth_day;
	
	@FindBy(id="birthdateYear-field")
	WebElement birth_year;
	
	@FindBy(id="next-button")
	WebElement nextBtn;
	
	@FindBy(id="cityVillage")
	WebElement cityVillage;
	
	@FindBy(css ="#address1")
	WebElement address1;
	
	@FindBy(css ="#address2")
	WebElement address2;
	
	@FindBy(css ="#stateProvince")
	WebElement stateProvince;
	
	@FindBy(css ="#country")
	WebElement country;
	
	@FindBy(css ="#postalCode")
	WebElement postalCode;
	
	@FindBy(name ="phoneNumber")
	WebElement phoneNumber;
	
	@FindBy(id ="submit")
	WebElement submit;
	
	
	
	
	

	public void enterPatentDetails(String fname,String lname,String gen,String day,String month,String year,
		String address_1,String address_2,String village,String state,String con,String postal,String mobile) {
		
		givenName.clear();
		givenName.sendKeys(fname);
		
		familyName.clear();
		familyName.sendKeys(lname);
		nextBtn.click();
		
		Select sel=new Select(gender);
		sel.selectByVisibleText(gen);
		nextBtn.click();
		
		birth_day.clear();
		birth_day.sendKeys(day);
		
		Select birth=new Select(birth_month);
		birth.selectByVisibleText(month);
		
		birth_year.clear();
		birth_year.sendKeys(year);
		
		nextBtn.click();
		
		address1.clear();
		address1.sendKeys(address_1);
		
		address2.clear();
		address2.sendKeys(address_2);
		
		cityVillage.clear();
		cityVillage.sendKeys(village);
		
		stateProvince.clear();
		stateProvince.sendKeys(state);
		
		country.clear();
		country.sendKeys(con);
		
		postalCode.clear();
		postalCode.sendKeys(postal);
		
		nextBtn.click();
		
		phoneNumber.clear();
		phoneNumber.sendKeys(mobile);
		
		nextBtn.click();
		nextBtn.click();
		
		
		
	}
	
	
	public boolean verifyPatentDetails(String fname,String lname,String gen,String day,String month,String year,
			String address_1,String address_2,String village,String state,String con,String postal,String mobile) {
		
		boolean flag=false;
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(),'"+gen+"')]")));
		driver.findElement(By.xpath("//p[contains(text(),'"+fname+", "+lname+"')]")).isDisplayed();
		driver.findElement(By.xpath("//p[contains(text(),'"+gen+"')]")).isDisplayed();
		driver.findElement(By.xpath("//p[contains(text(),'"+day+", "+month+", "+year+"')]")).isDisplayed();
		driver.findElement(By.xpath("//p[contains(text(),'"+address_1+", "+address_2+", "+village+", "+state+", "+con+", "+postal+"')]")).isDisplayed();
		driver.findElement(By.xpath("//p[contains(text(),'"+mobile+"')]")).isDisplayed();
		flag=true;
		submit.click();
		
		
		return flag;
	}
	
	
}
