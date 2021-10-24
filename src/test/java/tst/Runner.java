package tst;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Enums.DriverOptions;
import configReader.PropertyFileReader;
import driver.DriverSupplier;
import element.Elements;


public class Runner extends Elements {

	WebDriver driver=null;
	WebDriverWait wait =null;
	PropertyFileReader propReader;

	@BeforeClass
	public void setUpDriver() throws Exception
	{
		propReader = new PropertyFileReader();
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

	@Test
	public void launchApplication()
	{
		System.out.println(propReader.getConfigval("url"));
		driver.get(propReader.getConfigval("url"));
		int i = Integer.parseInt(propReader.getConfigval("waittime"));
		driver.manage().timeouts().pageLoadTimeout(i, TimeUnit.SECONDS);
	}

	@Test(dependsOnMethods="launchApplication",description="Feed Source Location Data")
	public void enterSourceLocation()
	{
		sendKeysP(driver.findElement(sourceTexBox),propReader.getConfigval("src"));
		delay(1);
		sendKeysP(driver.findElement(sourceTexBox),Keys.ARROW_DOWN);
		sendKeysP(driver.findElement(sourceTexBox),Keys.RETURN);
	}

	@Test(dependsOnMethods="enterSourceLocation",description="Feed Destination Location Data")
	public void enterDestLocation()
	{
		sendKeysP(driver.findElement(destTexBox),propReader.getConfigval("dst"));
		delay(1);
		sendKeysP(driver.findElement(destTexBox),Keys.ARROW_DOWN);
		sendKeysP(driver.findElement(destTexBox),Keys.RETURN);
	}

	@Test(dependsOnMethods="enterDestLocation",description="Click first day of next month")
	public void clickDate()
	{
		clickP(driver.findElement(nxtMnthBtn));
		clickP(driver.findElement(nextDate));
	}

	@Test(dependsOnMethods="clickDate",description="Searching for Flights")
	public void clickSearchButton()
	{
		clickP(driver.findElement(searchBtn));
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	@Test(dependsOnMethods="clickSearchButton",description="Booking ticket")
	public void bookTicket()
	{

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		clickP(driver.findElement(bookBtn));
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	@Test(dependsOnMethods="bookTicket",description="Displaying Approx ticket price for next month")
	public void displayTicketPrice()
	{
		try{
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String s = driver.findElement(priceText).getText();
		System.out.println("Price of earliest ticket next month Rs."+s.replaceAll("[^0-9]", ""));
		}catch(NoSuchElementException e){
			System.out.println("No Tickets available :(");
			
		}
	}
	
	@AfterClass
	public void tearDown(){
		driver.quit();
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
			wait.until(ExpectedConditions.elementToBeClickable(e));
			e.sendKeys(k);
		}catch(Exception e1){
			e1.printStackTrace();
		}

	}

	public void clickP(WebElement e){

		try{
			wait.until(ExpectedConditions.elementToBeClickable(e));
			e.click();
		}catch(Exception e1){
			e1.printStackTrace();
		}

	}

	static void delay(int i){
		try {
			Thread.sleep(i*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
