package pom;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.PropertyFileReader;


public class HomePage {
	
	public static WebDriver wd;
	PropertyFileReader data;
	private static WebDriverWait wait;

	//private OnlineReportManager oReport;

	private HomePage (WebDriver WD) 
	{
		wd = WD;
		PageFactory.initElements(wd, this);
		data = new PropertyFileReader();
		long num = new Long(data.getConfigval("waittime"));
		wait = new WebDriverWait(wd,num);
		//oReport = OnlineReportManager.getInstance();
	}


	public static HomePage using(WebDriver driver) 
	{
		return new HomePage(driver);
	}


	@CacheLookup
	@FindAll({ 
		@FindBy(id = "gosuggest_inputSrc") 
	})
	private WebElement sourceTexBox;
	
	@CacheLookup
	@FindAll({ 
		@FindBy(id = "gosuggest_inputDest") 
	})
	private WebElement destTexBox;

	@CacheLookup
	@FindAll({ 
		@FindBy(css = "span[aria-label='Next Month']")
	})
	private WebElement nxtMnthBtn;

	@CacheLookup
	@FindAll({ 
		@FindBy(xpath = "//div[@aria-disabled='false']")
	})
	private WebElement nextDate;

	@CacheLookup
	@FindAll({ 
		@FindBy(id = "gi_search_btn")
	})
	private WebElement searchBtn;

	@CacheLookup
	@FindAll({ 
		@FindBy(xpath = "//button[contains(.,'BOOK')]")
	})
	private WebElement bookBtn;
	
	@CacheLookup
	@FindAll({ 
		@FindBy(xpath = "//span[@class='font20']")
	})
	private WebElement priceText;
	
	
	public HomePage launchUrl() {

		try {
			
			wd.get(data.getConfigval("url"));
			wd.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			//Report.updateTestLog("Navigate to url", "User is able to navigate to url "+wd.getCurrentUrl(), true);
			
		} catch (Exception e) {

			//Report.updateTestLog("Navigate to url", "User is unable to navigate to specified url ", false);
		}
		return this;
	}

	
	
	
	public HomePage verifyElements() {

		try {
			
			if(this.sourceTexBox.isDisplayed() && this.destTexBox.isDisplayed() && this.searchBtn.isDisplayed()) {

				//Report.updateTestLog("Verify the presence of Input Element", "Input elements visible", true);
			}else {
				//Report.updateTestLog("Verify the presence of Input Element", "Input elements not present", false);
			}
			
		} catch (Exception e) {

			//Report.updateTestLog("Verify the presence of Input Element", "Input elements not present", false);e.printStackTrace();
		}
		return this;
	}



	public HomePage clickNxtMnthBtn() {

		try {
			
			wait.until(ExpectedConditions.visibilityOf(this.nxtMnthBtn));
			wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			clickJS((this.nxtMnthBtn));
			//Report.updateTestLog("Click '>' icon", "User is able to click '>' icon in Home page", true);
		} catch (Exception e) {
			//Report.updateTestLog("Click '>' icon", "User is unable to click '>' icon in Home page", false);
			e.printStackTrace();
		}
		return this;
	}
	
	public HomePage clickNextDate() {

		try {
			
			wait.until(ExpectedConditions.visibilityOf(this.nextDate));
			wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			clickJS((this.nextDate));
			//Report.updateTestLog("Select earliest date", "User is able to select the first date of the month", true);
		} catch (Exception e) {
			//Report.updateTestLog("Select earliest date", "User is unable to select the first date of the month", false);
			e.printStackTrace();
		}
		return this;
	}
	public HomePage clickSearchBtn() {
		
		try {
			
			wait.until(ExpectedConditions.visibilityOf(this.searchBtn));
			wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			clickJS((this.searchBtn));
			//Report.updateTestLog("Click Search Button", "User is able to click on search Flights Button", true);
		} catch (Exception e) {
			//Report.updateTestLog("Click Search Button", "User is unable to click on search Flights Button", false);
			e.printStackTrace();
		}
		return this;
	}
	public HomePage clickBookBtn() {
		
		try {
			
			wait.until(ExpectedConditions.visibilityOf(this.bookBtn));
			wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			clickJS((this.bookBtn));
			//Report.updateTestLog("Click Book Button", "User is able to click on Book Flights Button", true);
		} catch (Exception e) {
			//Report.updateTestLog("Click Book Button", "User is unable to click on Book Flights Button", false);
			e.printStackTrace();
		}
		return this;
	}
	
	public HomePage getFlightpriceDisplayed() {

		try {
			wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			wd.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			String s = this.priceText.getText().toString().replaceAll("[^0-9]", "");
			
			//Report.updateTestLog("Get price of earliest flight next month", "Price of Flight "+s, true);
		
		} catch (Exception e) {
			//Report.updateTestLog("Get price of earliest flight next month", "Price of Flight unavailable", false);
			e.printStackTrace();
		}
		return this;
	}

	
	public HomePage enterSourceLocation() {

		try {
			wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			this.sourceTexBox.click();
			this.sourceTexBox.sendKeys(data.getConfigval("src"));
			wd.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
			//Report.updateTestLog("Enter Source Location", "Source Location "+data.getConfigval("src")+" entered", true);
		
		} catch (Exception e) {
			//Report.updateTestLog("Enter Source Location", "Unable to enter "+data.getConfigval("src"), false);
			e.printStackTrace();
		}
		return this;
	}
	public HomePage enterDestLocation() {
		
		try {
			wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			this.destTexBox.click();
			this.destTexBox.sendKeys(data.getConfigval("dst"));
			wd.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
			//Report.updateTestLog("Enter Destination Location", "Destination Location "+data.getConfigval("dst")+" entered", true);
			
		} catch (Exception e) {
			//Report.updateTestLog("Enter Destination Location", "Unable to enter "+data.getConfigval("dst"), false);
			e.printStackTrace();
		}
		return this;
	}

	
	void clickJS(WebElement e) {
		wd.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor)wd;
		js.executeScript("arguments[0].click();", e);
	}

	void delay(int i) {
		try {
			Thread.sleep(i*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
