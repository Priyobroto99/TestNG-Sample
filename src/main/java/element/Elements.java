package element;
import org.openqa.selenium.By;

public class Elements {
	
	public final By sourceTexBox = By.id("gosuggest_inputSrc");
	public final By destTexBox = By.id("gosuggest_inputDest");
	public final By nxtMnthBtn = By.cssSelector("span[aria-label='Next Month']");
	public final By nextDate = By.xpath("//div[@aria-disabled='false']");
	public final By searchBtn = By.id("gi_search_btn");
	public final By bookBtn = By.xpath("//button[contains(.,'BOOK')]");
	public final By priceText = By.xpath("//span[@class='font20']");

}
