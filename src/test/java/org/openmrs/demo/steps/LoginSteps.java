package org.openmrs.demo.steps;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.sql.Driver;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.openmrs.demo.pages.DashboardPage;
import org.openmrs.demo.pages.DetailsPage;
import org.openmrs.demo.pages.LoginPage;
import org.openmrs.demo.pages.RegisterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginSteps {
	
	static WebDriver driver;
	LoginPage loginPage;
	DashboardPage dashboardPage;
	RegisterPage registerPage;
	DetailsPage detailsPage;
	
	@Given("User loads the url")
	public void user_loads_url() {
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://demo.openmrs.org/openmrs/login.htm");
		driver.manage().window().maximize();
		
		loginPage=new LoginPage(driver);
		dashboardPage=new DashboardPage(driver);
		registerPage=new RegisterPage(driver);
		detailsPage=new DetailsPage(driver);
	}
	
	
	@When("User logs into the application")
	public void user_logs_into_application() throws Exception {
		loginPage.enterUserDetails("Admin","Admin123");
	}
	
	@Then("Verify the sucessfull login")
	public void verify_login_sucessful() throws Exception {
		boolean actual=loginPage.sucessMessage();
		Assert.assertTrue(actual);
	}
	
	@Then("user navigates to patient register page")
	public void navigates_patient_page() throws Exception {
		dashboardPage.clickRegisterPatient();
	}
	
	@Then("User Register a patient {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
	public void navigates_patient_dpage(String fname,String lname,String gender,String day,String month,String year,String address1,String address2,String village,String state,String country,String postal,String mobile) throws Exception {
		registerPage.enterPatentDetails(fname, lname, gender, day, month, year, address1, address2, village, state, country, postal, mobile);
	}
	
	@Then("Verify Registered patient details {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
	public void verify_patient_details(String fname,String lname,String gender,String day,String month,String year,String address1,String address2,String village,String state,String country,String postal,String mobile) throws Exception {
		boolean actual=registerPage.verifyPatentDetails(fname, lname, gender, day, month, year, address1, address2, village, state, country, postal, mobile);
		Assert.assertTrue(actual);
	}
	
	@Then("Verify the age is calculated correctly based on the date of birth provided {string} {string} {string}")
	public void verify_age(String day,String month,String year) throws Exception {
		boolean actual=detailsPage.verifyAge(day, month, year);
		Assert.assertTrue(actual);
	}
	
	@Then("User upload the image and verify the attachment")
	public void uploadAndVerifyAttachment() throws Exception {
		boolean actual=detailsPage.uploadAndVerifyAttachment();
		Assert.assertTrue(actual);
	}
	
	@Then("User navigates to profile page")
	public void navigateProfilePage() throws Exception {
		detailsPage.navigatesToProfilePage();
	}
	
	@Then("User verifies attachment and end visit")
	public void verifyAndEndVisit() throws Exception {
		boolean actual=detailsPage.verifyAndEndVisit();
		Assert.assertTrue(actual);
	}
	
	@Then("User start visit and verified updated the bmi {string} {string}")
	public void startAndVerifyBmis(String height,String weight) throws Exception {
		boolean actual=detailsPage.startAndVerifyBMI(height,weight);
		Assert.assertTrue(actual);
	}
	
	@Then("User end visit and verified updated the bmi in personal page")
	public void endAndVerifyBmis() throws Exception {
		boolean actual=detailsPage.endAndverifyBMI();
		Assert.assertTrue(actual);
	}
	
	@Then("User verifies recent visit dates and attachment upload tag")
	public void recentVisitAndVerifyAttachmentTag() throws Exception {
		boolean actual=detailsPage.recentVisitAndVerifyAttachment();
		Assert.assertTrue(actual);
	}
	
	@Then("User Click on Add Past Visit and verify the future date is not clickable in the date picker")
	public void pastVisitDatePicker() throws Exception {
		boolean actual=detailsPage.pastVisitAndVerifyDisableDate();
		Assert.assertTrue(actual);
	}
	
	@Then("User delete patient and verify the deleted user not listed in search")
	public void deleteVerifySearchResult() throws Exception {
		boolean actual=detailsPage.deleteVerifySearchResults();
		Assert.assertTrue(actual);
	}
	
	
	
	@When("User clicks on shop menu")
	public void user_clicks_on_shop_menu() throws Exception {
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("Admin");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("Admin123");
		driver.findElement(By.id("Laboratory")).click();
		driver.findElement(By.id("loginButton")).click();
		
		boolean actual=driver.findElement(By.xpath("//h4[contains(text(),'Logged in')]")).isDisplayed();
		Assert.assertTrue(actual);
		
		//name
		driver.findElement(By.xpath("//a[contains(@id,'registerPatient')]")).click();
		driver.findElement(By.name("givenName")).sendKeys("Test");
		driver.findElement(By.name("familyName")).sendKeys("Colab");
		driver.findElement(By.id("next-button")).click();
		
		//gender
		WebElement ele=driver.findElement(By.id("gender-field"));
		Select sel=new Select(ele);
		sel.selectByVisibleText("Male");
		driver.findElement(By.id("next-button")).click();
		
		//
		WebElement ele_month=driver.findElement(By.id("birthdateMonth-field"));
		driver.findElement(By.id("birthdateDay-field")).sendKeys("10");
		Select sel_month=new Select(ele_month);
		sel_month.selectByVisibleText("February");
		driver.findElement(By.id("birthdateYear-field")).sendKeys("2000");
		driver.findElement(By.id("next-button")).click();
		
		//
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Timeout in seconds
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("address1")));
		
		

		
		WebElement element1=driver.findElement(By.id("cityVillage"));
		driver.findElement(By.cssSelector("#address1")).sendKeys("Test");
		driver.findElement(By.cssSelector("#address2")).sendKeys("Main Street");
		driver.findElement(By.cssSelector("#cityVillage")).sendKeys("Newyork");
		driver.findElement(By.cssSelector("#stateProvince")).sendKeys("NY");
		driver.findElement(By.cssSelector("#country")).sendKeys("USA");
		driver.findElement(By.cssSelector("#postalCode")).sendKeys("10022");
		driver.findElement(By.id("next-button")).click();
		
		
		//
		driver.findElement(By.name("phoneNumber")).sendKeys("8877665544");
		driver.findElement(By.id("next-button")).click();
		
		//
		driver.findElement(By.id("next-button")).click();
		
		
		
		try {Thread.sleep(10000);}catch(Exception e) {}
		
		//p[contains(text(),'Test, Colab')]
		//p[contains(text(),'Male')]
		//p[contains(text(),'10, February, 2000')]
		//p[contains(text(),'Test, Main Street, Newyork, NY, USA, 10022')]
		//p[contains(text(),'8877665544')]
		
		driver.findElement(By.xpath("//p[contains(text(),'Test, Colab')]")).isDisplayed();
		driver.findElement(By.xpath("//p[contains(text(),'Male')]")).isDisplayed();
		driver.findElement(By.xpath("//p[contains(text(),'10, February, 2000')]")).isDisplayed();
		driver.findElement(By.xpath("//p[contains(text(),'Test, Main Street, Newyork, NY, USA, 10022')]")).isDisplayed();
		driver.findElement(By.xpath("//p[contains(text(),'8877665544')]")).isDisplayed();
		
		
		//
		driver.findElement(By.id("submit")).click();
		try {Thread.sleep(10000);}catch(Exception e) {}
		
		LocalDate currentDate = LocalDate.now();
		 // Input date string
        String val = "10, February, 2000";

        // Define the formatter for the input date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd, MMMM, yyyy");

        // Parse the date string into a LocalDate object
        LocalDate birthDate = LocalDate.parse(val, formatter);

        // Get the current date
       // LocalDate currentDate = LocalDate.now();

        // Calculate the period (difference) between the two dates
        Period age = Period.between(birthDate, currentDate);

        // Get the number of years
        int years = age.getYears();

        // Print the age in years
        System.out.println("Age: " + years + " years");
        
      
        driver.findElement(By.xpath("//div[@class='gender-age col-auto']/span[contains(text(),'"+years+" year(s)')]")).isDisplayed();
		
		driver.findElement(By.xpath("//div[contains(text(),'Start Visit')]")).click();
		
		driver.findElement(By.id("start-visit-with-visittype-confirm")).click();
		
		
		try {Thread.sleep(10000);}catch(Exception e) {}
		
		
		driver.findElement(By.id("attachments.attachments.visitActions.default")).click();
		try {Thread.sleep(10000);}catch(Exception e) {}
		
		driver.findElement(By.xpath("//div[text()='Click or drop a file here.']")).click();
		
		try {Thread.sleep(5000);}catch(Exception e) {}
		
		 // Path to the file to be uploaded
        String filePath = "C:\\Users\\User\\eclipse-workspace\\OpenMRS\\upload_file\\image.jpg";

        // Use Robot class to handle file chooser dialog
        Robot robot = new Robot();

        // Copy the file path to the clipboard
        StringSelection fileSelection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);

        // Paste the file path into the file chooser dialog
        robot.delay(2000); // Wait for the dialog to open
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // Press Enter to confirm the file selection
        robot.delay(1000); // Optional delay for stability
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        // Additional steps can be added to confirm upload or submit the form
		
		
		
		
		
		driver.findElement(By.xpath("//textarea[@placeholder='Enter a caption']")).clear();
		driver.findElement(By.xpath("//textarea[@placeholder='Enter a caption']")).sendKeys("notes");
		try {Thread.sleep(5000);}catch(Exception e) {}
		driver.findElement(By.xpath("//button[text()='Upload file']")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='The attachment was successfully uploaded.']")));
		driver.findElement(By.xpath("//*[text()='The attachment was successfully uploaded.']")).isDisplayed();
		
		try {Thread.sleep(5000);}catch(Exception e) {}
		
		driver.findElement(By.xpath("//ul[@id='breadcrumbs']/li[2]/a")).click();
		
		try {Thread.sleep(5000);}catch(Exception e) {}
		
		

        // Define the desired date format
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd.MMM.yyyy");

        // Format the current date
        String formattedDate = currentDate.format(formatter1);
		
		String value=driver.findElement(By.xpath("//h3[text()='RECENT VISITS']/../../div[2]//ul/li/a")).getText();
		if(value.contains(formattedDate)) {
			System.out.println("1111");
		}
		
		driver.findElement(By.xpath("//h3[text()='ATTACHMENTS']/../../div[2]//img")).isDisplayed();
		
		
		driver.findElement(By.xpath("//p[@ng-show='obs.comment']")).isDisplayed();
		
		
		try {Thread.sleep(5000);}catch(Exception e) {}
		driver.findElement(By.xpath("//div[@class='action-section']//div[contains(text(),'End Visit')]")).click();
		driver.findElement(By.xpath("//div[@id='end-visit-dialog']//button[text()='Yes']")).click();
		
		try {Thread.sleep(5000);}catch(Exception e) {}
		
		
		
		
		////
		driver.findElement(By.xpath("//div[contains(text(),'Start Visit')]")).click();
		driver.findElement(By.id("start-visit-with-visittype-confirm")).click();
		try {Thread.sleep(5000);}catch(Exception e) {}
		driver.findElement(By.id("referenceapplication.realTime.vitals")).click();
		try {Thread.sleep(3000);}catch(Exception e) {}
		driver.findElement(By.id("w8")).sendKeys("172");
		driver.findElement(By.id("next-button")).click();
		driver.findElement(By.id("w10")).sendKeys("72");
		driver.findElement(By.id("next-button")).click();
		
		String bmi=driver.findElement(By.id("calculated-bmi")).getText();
		
		
		try {Thread.sleep(10000);}catch(Exception e) {}
		
		double weight = 72;
		double height = 172;
		Double bmi_calculated = weight / (height * height);
		
		String bmi_calculated_final=bmi_calculated.toString();
		
		System.out.println("===>"+bmi);
		System.out.println("=bmi_calculated_final==>"+bmi_calculated_final);
		
		if(bmi_calculated_final.replace(".","").contains(bmi.replace(".",""))) {
			System.out.println("pass");
		}else {
			System.out.println("FAIL BMI");
		}
		
		
		driver.findElement(By.id("save-form")).click();
		driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
		
		try {Thread.sleep(5000);}catch(Exception e) {}
		
		driver.findElement(By.xpath("//ul[@id='breadcrumbs']/li[2]/a")).click();
		
		try {Thread.sleep(5000);}catch(Exception e) {}
		
		driver.findElement(By.xpath("//div[@class='action-section']//div[contains(text(),'End Visit')]")).click();
		driver.findElement(By.xpath("//div[@id='end-visit-dialog']//button[text()='Yes']")).click();
		
		String height_vitals=driver.findElement(By.xpath("//span[@id='height']/span")).getText();
		String weight_vitals=driver.findElement(By.xpath("//span[@id='weight']/span")).getText();
		String bmi_vitals=driver.findElement(By.id("calculated-bmi")).getText();
		
		if(bmi.equals(bmi_vitals)) {
			System.out.println("pass");
		}else {
			System.out.println("FAIL list");
		}
		
		
		
		
		int date_row_count=driver.findElements(By.xpath("//h3[text()='RECENT VISITS']/../../div[2]//ul/li/a")).size();
		
		if(date_row_count==2)  {
			System.out.println("pass");
		}else {
			System.out.println("FAIL list COUNT");
		}
		

		int attachment_upload=driver.findElements(By.xpath("//h3[text()='RECENT VISITS']/../../div[2]//ul/li/div[text()='Attachment Upload']")).size();
		
		if(attachment_upload==1)  {
			System.out.println("pass");
		}else {
			System.out.println("FAIL list COUATTACHEMNTNT");
		}
		
		
		
		
		
		
		
		
		
		
		
		
		//
		driver.findElement(By.xpath("//div[contains(text(),'Add Past Visit')]")).click();
		
		
		 LocalDate nextDay = currentDate.plusDays(1);
		 int date=nextDay.getDayOfMonth();
		 System.out.println("==>"+date);
		int count=driver.findElements(By.xpath("(//div[@class='datetimepicker-days' and @style='display: block;'])[1]//td[contains(@class,'day') and text()='"+date+"']")).size();
		System.out.println("==>"+count);
		String classVal="";
		if(count==2) {
			classVal=driver.findElement(By.xpath("((//div[@class='datetimepicker-days' and @style='display: block;'])[1]//td[contains(@class,'day') and text()='"+date+"'])[2]")).getAttribute("class");
		}else {
			classVal=driver.findElement(By.xpath("(//div[@class='datetimepicker-days' and @style='display: block;'])[1]//td[contains(@class,'day') and text()='"+date+"']")).getAttribute("class");
		}
		System.out.println("==>"+classVal);
		if(classVal.equals("day disabled")) {
			Assert.assertTrue(true);
		}else {
			Assert.assertTrue(false);
		}
		
		try {Thread.sleep(5000);}catch(Exception e) {}
		driver.findElement(By.xpath("//div[contains( text(),'Delete Patient')]")).click();
		
		driver.findElement(By.id("delete-reason")).sendKeys("Testing Done");
		
		driver.findElement(By.xpath("(//button[text()='Confirm'])[3]")).click();
		
		try {Thread.sleep(2000);}catch(Exception e) {}
		driver.findElement(By.xpath("//*[text()='Patient has been deleted successfully']")).isDisplayed();
		 
		try {Thread.sleep(5000);}catch(Exception e) {}
		
		driver.findElement(By.id("patient-search")).sendKeys("Test Colab");
		try {Thread.sleep(5000);}catch(Exception e) {}
		driver.findElement(By.xpath("//td[text()='No matching records found']")).isDisplayed();
		 
		
		

	}
	
	

	@Then("I quit the browser")
	public void i_quit_the_browser() {
		driver.quit();
	}
	
}
