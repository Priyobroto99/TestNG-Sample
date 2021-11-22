package tst;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.common.base.Verify;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Enums.DriverOptions;
import configReader.PropertyFileReader;
import driver.DriverSupplier;
import element.Elements;
import excel.ExcelReader;


public class Runner extends Elements {

	WebDriver driver=null;
	WebDriverWait wait =null;
	PropertyFileReader propReader;
	ExcelReader er = null;
	ExtentReports reports = null;
	ExtentTest test = null;
	int i;

	@BeforeClass
	public void setUp() throws Exception
	{
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		reports = new ExtentReports(".\\TestReports\\Report_"+date+".html", true);
		propReader = new PropertyFileReader();
		i = Integer.parseInt(propReader.getConfigval("waittime"));
		er  = new ExcelReader(".\\TestDataSet\\testdatafile.xlsx",0);
		String drvselected = propReader.getConfigval("DriverName").toLowerCase();
		if(drvselected.equals(DriverOptions.chrome.toString())){
			driver = DriverSupplier.getChromeDriver();
			wait = new WebDriverWait(driver,30);
		}else if(drvselected.equals(DriverOptions.firefox.toString())){
			driver = DriverSupplier.getGechoDriver();
			wait = new WebDriverWait(driver,30);
		}else{
			driver = DriverSupplier.getChromeDriver();
			wait = new WebDriverWait(driver,30);
		}


	}

	@Test(priority=0,description="validate Ticket price",dataProvider="testData")
	public void validateTicketPrice(String src,String dest)
	{
		test = reports.startTest("Validate user is able to book tickets from "+src+" to "+dest);
		driver.get(propReader.getConfigval("url"));
		driver.manage().timeouts().pageLoadTimeout(i, TimeUnit.SECONDS);
		Verify.verify(driver.getCurrentUrl().equalsIgnoreCase(propReader.getConfigval("url")));	
		test.log(LogStatus.PASS, "Navigated to the specified URL");
		
		
		//entering source location
		sendKeysP(driver.findElement(sourceTexBox),src);
		try {
			sendKeysP(driver.findElement(sourceTexBox),Keys.ARROW_DOWN);
			sendKeysP(driver.findElement(sourceTexBox),Keys.RETURN);
			test.log(LogStatus.PASS, "Entered Soruce data :"+src);
			//Assert.assertEquals(driver.findElement(sourceTexBox).getText(),src);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			test.log(LogStatus.FAIL, "Entered Soruce data :"+src);
			e.printStackTrace();
		}

		//entering destination location
		try {
			sendKeysP(driver.findElement(destTexBox),dest);
			sendKeysP(driver.findElement(destTexBox),Keys.ARROW_DOWN);
			sendKeysP(driver.findElement(destTexBox),Keys.RETURN);
			test.log(LogStatus.PASS, "Entered Destination data :"+dest);
		} catch (Exception e1) {
			test.log(LogStatus.FAIL, "Entered Destination data :"+dest);
			e1.printStackTrace();
		}
		//Assert.assertEquals(driver.findElement(destTexBox).getText(),dest);


		//clicking first day of next month
		try {
			clickP(driver.findElement(nxtMnthBtn));
			clickP(driver.findElement(nextDate));
			test.log(LogStatus.PASS, "Clicking first day of next month");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			test.log(LogStatus.FAIL, "Clicking first day of next month");
			e1.printStackTrace();
		}

		//clicking search button
		try {
			clickP(driver.findElement(searchBtn));
			test.log(LogStatus.PASS, "Searching for flights");
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			test.log(LogStatus.FAIL, "earching for flights");
			e1.printStackTrace();
		}

		//clicking on book button
		try {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(bookBtn)).build().perform();
			clickP(driver.findElement(bookBtn));
			test.log(LogStatus.PASS, "Booking the first flight");
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			test.log(LogStatus.FAIL, "Booking the first flight");
			e1.printStackTrace();
		}

		//displaying the price of the tickets
		try{
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			String s = driver.findElement(priceText).getText().replaceAll("[^0-9]","");
			test.log(LogStatus.PASS, "Price of ticket fetched :Rs"+s);
			takeSnapShot(".\\SS\\test.png");
			System.out.println("Price of earliest ticket next month Rs."+s);
		}catch(Exception e){
			test.log(LogStatus.FAIL, "Price of tickets");
			System.out.println("No Tickets available :(");

		}
	}

	@DataProvider(name="testData")
	public String[][] getSourceDestinationData(){
		int datasets = er.getNoOfRows();
		System.out.println("datasets :"+datasets);
		String[][] srcDest = new String[datasets+1][2];

		for(int i=0;i<=datasets;i++) {
			System.out.println("value of i :"+i);
			srcDest[i][0] = er.getDataAt(i, 0);
			srcDest[i][1] = er.getDataAt(i, 1);
		}
		return srcDest;

	}

	@AfterClass
	public void tearDown(){
		driver.quit();
		reports.endTest(test);
		reports.flush();
	}




	public void sendKeysP(WebElement e,String s){

		try{
			wait.until(ExpectedConditions.elementToBeClickable(e));

			e.sendKeys(s);
		}catch(Exception e1){
			e1.printStackTrace();
		}

	}

	public void sendKeysP(WebElement e,Keys k){

		try{
			delay(1);
			wait.until(ExpectedConditions.elementToBeClickable(e));
			e.sendKeys(k);
		}catch(Exception e1){
			e1.printStackTrace();
		}

	}

	public void clickP(WebElement e){

		try{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.elementToBeClickable(e));
			e.click();
		}catch(Exception e1){
			e1.printStackTrace();
		}

	}

	static void delay(double d){
		try {
			Thread.sleep((long) (d*1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void takeSnapShot(String fileWithPath) throws Exception{

		TakesScreenshot scrShot =((TakesScreenshot)driver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile=new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);

	}

	public void takess(String fpath) {

		TakesScreenshot tss = ((TakesScreenshot)driver);
		File src = tss.getScreenshotAs(OutputType.FILE);
		File dst = new File(fpath);

		try {
			FileUtils.copyFile(src, dst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
