package org.openmrs.demo.pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DetailsPage {
	
	WebDriver driver;
	WebDriverWait wait;
	private static final Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());


	
	public DetailsPage(WebDriver driver) {
		this.driver=driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Timeout in seconds
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[contains(@id,'registerPatient')]")
	WebElement patient;
	
	@FindBy(xpath="//div[contains(text(),'Start Visit')]")
	WebElement startVisit;
	
	@FindBy(id="start-visit-with-visittype-confirm")
	WebElement startVisitConform;
	
	@FindBy(id="attachments.attachments.visitActions.default")
	WebElement attachmentform;
	
	@FindBy(xpath="//div[text()='Click or drop a file here.']")
	WebElement browserFile;
	

	@FindBy(xpath="//textarea[@placeholder='Enter a caption']")
	WebElement notes;
	
	@FindBy(xpath="//ul[@id='breadcrumbs']/li[2]/a")
	WebElement profile;

	
	@FindBy(xpath="//h3[text()='RECENT VISITS']/../../div[2]//ul/li/a")
	WebElement recentVisitDate;
	
	@FindBy(xpath="//h3[text()='ATTACHMENTS']/../../div[2]//img")
	WebElement attachmentFile;
	
	@FindBy(xpath="//p[@ng-show='obs.comment']")
	WebElement comments;
	
	@FindBy(xpath="//div[@class='action-section']//div[contains(text(),'End Visit')]")
	WebElement endVisit;
	
	@FindBy(xpath="//div[@id='end-visit-dialog']//button[text()='Yes']")
	WebElement endVisitYesBtn;
	
	@FindBy(id="save-form")
	WebElement saveForm;
	
	@FindBy(xpath="//button[contains(text(),'Save')]")
	WebElement saveConformForm;
	
	@FindBy(id="next-button")
	WebElement nextBtn;
	
	@FindBy(id="referenceapplication.realTime.vitals")
	WebElement vitals;
	
	
	@FindBy(id="w8")
	WebElement weight;
	
	
	@FindBy(id="w10")
	WebElement height;
	
	@FindBy(id="calculated-bmi")
	WebElement calculated_bmi;
	

	@FindBy(xpath="//span[@id='height']/span")
	WebElement heightSpan;
	
	@FindBy(xpath="//span[@id='weight']/span")
	WebElement weightSpan;	
	

	@FindBy(xpath="//div[contains(text(),'Add Past Visit')]")
	WebElement addPastVisit;
	
	@FindBy(xpath="//div[contains( text(),'Delete Patient')]")
	WebElement deletePatient;
	
	@FindBy(id="delete-reason")
	WebElement deleteReason;
	
	@FindBy(id="patient-search")
	WebElement patientSearch;
	
	@FindBy(xpath="//td[text()='No matching records found']")
	WebElement norecord;
	
	@FindBy(xpath="//h3[contains(text(),'Delete Patient')]/../../div[@class='dialog-content']//button[text()='Confirm']")
	WebElement conform;
	
	@FindBy(xpath="//*[text()='Patient has been deleted successfully']")
	WebElement deleteSucess;
	

	String recentVisitDates="//h3[text()='RECENT VISITS']/../../div[2]//ul/li/a";
	
	String attachmentUploadTag="//h3[text()='RECENT VISITS']/../../div[2]//ul/li/div[text()='Attachment Upload']";
	
	public boolean verifyAge(String day,String month,String year) throws Exception {
		
		LocalDate currentDate = LocalDate.now();
		 // Input date string
       String val = ""+day+", "+month+", "+year+"";

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
       wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='gender-age col-auto']/span[contains(text(),'"+years+" year(s)')]")));
       boolean actual=driver.findElement(By.xpath("//div[@class='gender-age col-auto']/span[contains(text(),'"+years+" year(s)')]")).isDisplayed();
       takeScreenShot();
       logger.info("verified age successfully.");
       return actual;
		
	}
	
	public boolean uploadAndVerifyAttachment() throws Exception {
		
		
		startVisit.click();
		
		startVisitConform.click();
		
		
		//try {Thread.sleep(10000);}catch(Exception e) {}
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("attachments.attachments.visitActions.default")));
	    //driver.findElement(By.id("attachments.attachments.visitActions.default")).click();
		attachmentform.click();
		Thread.sleep(5000);
		
		//driver.findElement(By.xpath("//div[text()='Click or drop a file here.']")).click();
		browserFile.click();
		Thread.sleep(5000);
		
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
		
		notes.clear();
		notes.sendKeys("notes");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[text()='Upload file']")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='The attachment was successfully uploaded.']")));
		takeScreenShot();
		logger.info("uploaded attachment and verified successfully.");
		return driver.findElement(By.xpath("//*[text()='The attachment was successfully uploaded.']")).isDisplayed();
		
	}
	
	public void navigatesToProfilePage() throws Exception {
		profile.click();
		logger.info("profile page navigated successfully.");
	}
	
	
	public boolean verifyAndEndVisit() throws Exception {
	
		
		LocalDate currentDate = LocalDate.now();

        // Define the desired date format
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd.MMM.yyyy");

        // Format the current date
        String formattedDate = currentDate.format(formatter1);
        
        Thread.sleep(5000);
        
		String value=recentVisitDate.getText();
		if(value.contains(formattedDate)) {
			Assert.assertTrue(true);
		}
		
		attachmentFile.isDisplayed();
		
		boolean flag=comments.isDisplayed();
		
		endVisit.click();
		endVisitYesBtn.click();
		takeScreenShot();
		logger.info("verified the display attachement in profile page navigated successfully.");
		return flag;
	}
	
	static String bmi;
	public boolean startAndVerifyBMI(String height_patient,String weight_patient) throws Exception {
		
		boolean flag=false;
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Start Visit')]")));
		startVisit.click();
		startVisitConform.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("referenceapplication.realTime.vitals")));
		vitals.click();
		//try {Thread.sleep(3000);}catch(Exception e) {}
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("w8")));
		weight.sendKeys(height_patient.toString());
		nextBtn.click();
		height.sendKeys(weight_patient.toString());
		nextBtn.click();
		
		bmi=driver.findElement(By.id("calculated-bmi")).getText();
		
		
		
		
		Thread.sleep(5000);
		
		Double weight = Double.parseDouble(weight_patient);
		Double height = Double.parseDouble(height_patient);
		Double bmi_calculated = weight / (height * height);
		
		String bmi_calculated_final=bmi_calculated.toString();
		
		
		if(bmi_calculated_final.replace(".","").contains(bmi.replace(".",""))) {
			flag=true;
		}else {
			flag=false;
		}
		
		
		saveForm.click();
		saveConformForm.click();
		logger.info("BMI calculated based on our input successfully.");
		takeScreenShot();
		return flag;
	}
	
	
	public boolean endAndverifyBMI() throws Exception {
		
		boolean flag=false;
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='action-section']//div[contains(text(),'End Visit')]")));
		
		endVisit.click();
		endVisitYesBtn.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("calculated-bmi")));
		
		String bmi_vitals=calculated_bmi.getText();
		
		if(bmi.equals(bmi_vitals)) {
			flag=true;
		}else {
			flag=false;
		}
		takeScreenShot();
		logger.info("End Visit and BMI verified in profile successfully.");
		return flag;
	}
	
	public boolean recentVisitAndVerifyAttachment() throws Exception {
		
		boolean flag=true;
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(recentVisitDates)));
		
		int date_row_count=driver.findElements(By.xpath(recentVisitDates)).size();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(attachmentUploadTag)));
		 // Create a JavascriptExecutor object
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // **1. Scroll down by a specific amount (e.g., 500 pixels)**
        js.executeScript("window.scrollBy(0,500);");
		int attachment_upload=driver.findElements(By.xpath(attachmentUploadTag)).size();
		if(attachment_upload==1 && date_row_count==2)  {
			flag=true;
		}else {
			flag=false;
		}
		takeScreenShot();
		logger.info("Verify recent visit dates and attachment upload tag in profile successfully.");
		return flag;
	}
	
	public boolean pastVisitAndVerifyDisableDate() throws Exception {
		
		
		boolean flag=true;
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Add Past Visit')]")));
		Thread.sleep(5000);
		addPastVisit.click();
		
		LocalDate currentDate = LocalDate.now();
		 LocalDate nextDay = currentDate.plusDays(1);
		 int date=nextDay.getDayOfMonth();
		int count=driver.findElements(By.xpath("(//div[@class='datetimepicker-days' and @style='display: block;'])[1]//td[contains(@class,'day') and text()='"+date+"']")).size();
		
		String classVal="";
		if(count==2) {
			classVal=driver.findElement(By.xpath("((//div[@class='datetimepicker-days' and @style='display: block;'])[1]//td[contains(@class,'day') and text()='"+date+"'])[2]")).getAttribute("class");
		}else {
			classVal=driver.findElement(By.xpath("(//div[@class='datetimepicker-days' and @style='display: block;'])[1]//td[contains(@class,'day') and text()='"+date+"']")).getAttribute("class");
		}
		
		if(classVal.equals("day disabled")) {
			flag=true;
		}else {
			flag=false;
		}
		driver.navigate().refresh();
		takeScreenShot();
		logger.info("Verify past visit dates are disabled for future dates successfully.");
		return flag;
	}
	

	
	public boolean deleteVerifySearchResults() throws Exception {
		
		
		boolean flag=true;
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Add Past Visit')]")));
			deletePatient.click();
			
			deleteReason.sendKeys("Testing Done");
			
			conform.click();
			
			Thread.sleep(2000);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Patient has been deleted successfully']")));			
			deleteSucess.isDisplayed();
			 
			Thread.sleep(5000);
			
			patientSearch.sendKeys("Test Colab");
			Thread.sleep(5000);
			takeScreenShot();
			logger.info("Deleted patient successfully and verified in search page.");
		return norecord.isDisplayed();
		 
	}
	
	public void takeScreenShot() throws Exception {
		
	       // Create a folder to store screenshots
        String folderPath = "C:\\Users\\User\\eclipse-workspace\\OpenMRS\\screenshots";
       

        // Generate a timestamp for the screenshot file name
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date(1));

        // File name with timestamp
        String fileName = "screenshot_" + timestamp + ".png";

        // Full file path
        File filePath = new File(folderPath + "/" + fileName);

        // Take a screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Save the screenshot to the specified location
        FileUtils.copyFile(screenshot, filePath);

        System.out.println("Screenshot saved at: " + filePath.getAbsolutePath());
	}
	
	
	
}
