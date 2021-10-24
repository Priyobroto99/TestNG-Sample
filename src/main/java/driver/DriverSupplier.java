package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverSupplier {
	static WebDriver driver;


	public static WebDriver getChromeDriver(){
		
		if(driver==null){
			System.setProperty("webdriver.chrome.driver",".\\Drivers\\chromedriver.exe");
			ChromeOptions co = new ChromeOptions();
			co.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
			driver= new ChromeDriver(co);
			driver.manage().window().maximize();

			return driver;
		}else{
			return driver;
		}
		
	}

	public static WebDriver getGechoDriver(){	
		if(driver==null){
		System.setProperty("webdriver.gecko.driver",".\\Drivers\\geckodriver.exe");
		driver =  new FirefoxDriver();
		driver.manage().window().maximize();
		return driver;
		}else
		return driver;
	}

}
