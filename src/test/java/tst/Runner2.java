package tst;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


import Enums.DriverOptions;
import driver.DriverSupplier;
import pom.HomePage;
import util.PropertyFileReader;

public class Runner2 {
	WebDriver driver=null;
	WebDriverWait wait =null;
	PropertyFileReader propReader;
	ExtentReports reports = null;
	ExtentTest test = null;
	int i;
	String reportP;
	HomePage ghome=null;
	
	
	@BeforeClass
	void setup() {
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		reportP = ".\\Report_"+date+".html";
		reports = new ExtentReports(reportP, true);
		propReader = new PropertyFileReader();
		i = Integer.parseInt(propReader.getConfigval("waittime"));
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
		ghome = HomePage.using(driver);
		
	}
	
	@Test
	void test1() {
		ghome.launchUrl()
		.verifyElements();
	}
	
	@AfterClass
	void tearDown() {
		driver.close();
		driver.quit();
	}
	
	

}
