package main;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Run {
	static WebDriver driver=null;
	static WebDriverWait wait =null;

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver.exe");

		ChromeOptions co = new ChromeOptions();
		co.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
		co.addArguments("disable-infobars");
		driver = new ChromeDriver(co);
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver,30);

		driver.get("https://www.goibibo.com/");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		//clickP(driver.findElement(By.id("gosuggest_inputSrc")));
		sendKeysP(driver.findElement(By.id("gosuggest_inputSrc")),"Kolkata (CCU)");
		sleep(1);
		sendKeysP(driver.findElement(By.id("gosuggest_inputSrc")),Keys.ARROW_DOWN);
		sendKeysP(driver.findElement(By.id("gosuggest_inputSrc")),Keys.RETURN);
		//clickP(driver.findElement(By.id("gosuggest_inputDest")));
		sendKeysP(driver.findElement(By.id("gosuggest_inputDest")),"Jammu (IXJ)");
		sleep(1);
		sendKeysP(driver.findElement(By.id("gosuggest_inputDest")),Keys.ARROW_DOWN);
		sendKeysP(driver.findElement(By.id("gosuggest_inputDest")),Keys.RETURN);;
		

		clickP(driver.findElement(By.cssSelector("span[aria-label='Next Month']")));
		clickP(driver.findElement(By.xpath("//div[@aria-disabled='false']")));

		//clicking search button
		clickP(driver.findElement(By.id("gi_search_btn")));
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		
		clickP(driver.findElement(By.xpath("//button[contains(.,'BOOK')]")));
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		
		String s = driver.findElement(By.xpath("//span[@class='font20']")).getText();
		System.out.println("Price of earliest ticket next month Rs."+s.replaceAll("[^0-9]", ""));
		
		driver.quit();
		
		/*String flightsList= "//div[@class='width100 flexCol']";
		//wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath(flightsList))));
		sleep(10);
		int i = driver.findElements(By.xpath(flightsList)).size();

		System.out.println("# of flights available :"+i);
		
		for(int j =0;j<i; j++){
			System.out.println(" Fligt price "+driver.findElements(By.xpath(flightsList)).get(j).getText());
		}

		//
*/


		 
	}

	public static void sendKeysP(WebElement e,String s){

		try{
			wait.until(ExpectedConditions.elementToBeClickable(e));
			e.sendKeys(s);
		}catch(Exception e1){
			e1.printStackTrace();
		}

	}

	public static void sendKeysP(WebElement e,Keys k){

		try{
			wait.until(ExpectedConditions.elementToBeClickable(e));
			e.sendKeys(k);
		}catch(Exception e1){
			e1.printStackTrace();
		}

	}

	public static void clickP(WebElement e){

		try{
			wait.until(ExpectedConditions.elementToBeClickable(e));
			e.click();
		}catch(Exception e1){
			e1.printStackTrace();
		}

	}

	static void sleep(int i){
		try {
			Thread.sleep(i*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
