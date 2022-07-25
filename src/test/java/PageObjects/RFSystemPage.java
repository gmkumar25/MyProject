package PageObjects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Headers;
import org.openqa.selenium.devtools.v85.network.model.RequestId;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class RFSystemPage {
	WebDriver driver;
	HomePage homePage;

	public RFSystemPage(WebDriver driver) {
		this.driver = driver;
	}

	/**  ****** All the Locators goes here ******  **/
	By dropDown_System = By.xpath("(//div[contains(@class, 'MuiSelect-select')])[1]");
	By dropDown_Modem = By.xpath("(//div[contains(@class, 'MuiSelect-select')])[2]");
	By input_StartDate = By.xpath("(//div[@class='react-datepicker__input-container']/input)[1]");
	By input_EndDate = By.xpath("(//div[@class='react-datepicker__input-container']/input)[2]");
	By btn_QuickSelect = By.xpath("(//*[text()='System']//parent::div//parent::div//*//*[local-name()='svg'])[3]");
	By lable_QuickSelect_Options = By.xpath("//*[text()='Presets']/following-sibling::div/ul/li");
	By img_RFStatistics_Graph = By.xpath("(//*[local-name()='canvas'])[1]");
	By img_RFStatistics_Map = By.xpath("//*[contains(@class, 'leaflet-touch-drag')]");
	By btn_Apply = By.xpath("//span[text()='APPLY']");



	/**  ****** All the Methods goes here ******  **/ 
	public void accessRfStatisticsPage(String NeuronFleetName) throws InterruptedException {
		String URL = driver.getCurrentUrl();
		String[] splitted_URL = URL.split(".io/");
		String expected_URL = splitted_URL[0].trim()+".io/site/rf-stats";
		waitForPageLoad();
		driver.navigate().to(expected_URL);
		waitForPageLoad();
		waitForPageLoad();
	}

	public void waitForPageLoad() throws InterruptedException {
		Thread.sleep(4000);
	}

	public void validateRFStatisticsPageUI() throws InterruptedException {
		waitForPageLoad();
		String[] ExpText = {"RF Statistics", "System", "Modem", "Start Date", "End Date", "APPLY"};
		String ActText = driver.getPageSource();
		for(String s:ExpText) {
			Assert.assertTrue(ActText.contains(s),"User could not launch RF Statistics page");
		}
	}

	public void validateStartDateEndDateData_RFStatistics() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd @ HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		String localDateTime = dtf.format(now);
		System.out.println("***  Local Date & Time :" +localDateTime);
		String[] localDateTime_Split = localDateTime.split("@");
		String localDate = localDateTime_Split[0];
		String localTime = ((localDateTime_Split[1].split(":"))[0]).trim();
		System.out.println("*** locTime :" +localTime);
		int localTime_int = Integer.parseInt(localTime);
		int localTime_1hourAgo_int = localTime_int-1;
		String Actual_StartDateTime = driver.findElement(input_StartDate).getAttribute("value");
		String[] Actual_StartDate_Split = Actual_StartDateTime.split("@");
		String Actual_StartDate = Actual_StartDate_Split[0];
		String Actual_StartTime = ((Actual_StartDate_Split[1].split(":"))[0]).trim();
		int Actual_StartTime_int = Integer.parseInt(Actual_StartTime);
		Assert.assertTrue((Actual_StartDate.equals(localDate)), "Default Start Date is wrong");
		Assert.assertTrue((Actual_StartTime_int==localTime_1hourAgo_int), "Default Start Time is wrong");
		String Actual_EndDateTime = driver.findElement(input_EndDate).getAttribute("value");
		String[] Actual_EndDate_Split = Actual_EndDateTime.split("@");
		String Actual_EndDate = Actual_EndDate_Split[0];
		String Actual_EndTime = ((Actual_EndDate_Split[1].split(":"))[0]).trim();
		int Actual_EndTime_int = Integer.parseInt(Actual_EndTime);
		Assert.assertTrue((Actual_EndDate.equals(localDate)), "Default End Date is wrong");
		Assert.assertTrue((Actual_EndTime_int==localTime_int), "Default End Time is wrong");
	}

	public void validateQuickSelectOptions_RFStatistics() throws InterruptedException {
		waitForPageLoad();
		driver.findElement(btn_QuickSelect).click();
		String[] Actual_QuickSelect_Options_Array = {"Today", "This week", "Last 15 minutes", "Last 30 minutes", "Last 1 hour", "Last 24 hours", "Last 7 days", "Last 30 days", "Last 90 days", "Last 1 year"};
		ArrayList<String> Exp_QuickSelectOptions = new ArrayList<String>();
		Collections.addAll(Exp_QuickSelectOptions, Actual_QuickSelect_Options_Array);
		List<WebElement> QucikSelect_Options = driver.findElements(lable_QuickSelect_Options);
		ArrayList<String> ActualQuickSelectOptions = new ArrayList<String>();
		for(WebElement ele : QucikSelect_Options) {
			ActualQuickSelectOptions.add(ele.getText());
		}
		System.out.println("****  List :" +ActualQuickSelectOptions);
		Boolean QuickSlect_flag = ActualQuickSelectOptions.equals(Exp_QuickSelectOptions);
		Assert.assertTrue(QuickSlect_flag, "One or More expected option is missing in the Quick Select Presets");
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.ESCAPE).perform();
	}

	public void validateRFStaisticsData() throws InterruptedException {
		waitForPageLoad();
		Boolean QoeScoreAnalysisGraph_flag = driver.findElement(img_RFStatistics_Graph).isDisplayed();
		Assert.assertTrue(QoeScoreAnalysisGraph_flag, "The RF Statistics graph is not displayed");
		Boolean QoeScoreAnalysisMap_flag = driver.findElement(img_RFStatistics_Map).isDisplayed();
		Assert.assertTrue(QoeScoreAnalysisMap_flag, "The RF Statistics page Map is not displayed");
		
		//Validating the Graph Data from Chrome's Networks tab 
				String Actual_StartDateTime = driver.findElement(input_StartDate).getAttribute("value");
				String[] Actual_StartDate_Split = Actual_StartDateTime.split("@");
				String Actual_StartDate = Actual_StartDate_Split[0].trim();
				String Actual_EndDateTime = driver.findElement(input_EndDate).getAttribute("value");
				String[] Actual_EndDate_Split = Actual_EndDateTime.split("@");
				String Actual_EndDate = Actual_EndDate_Split[0].trim();
				Actions actions = new Actions(driver);
				actions.moveToElement(driver.findElement(btn_Apply)).perform();
				DevTools devTools = ((ChromeDriver) driver).getDevTools();
				devTools.createSession();
				devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
				devTools.addListener(Network.requestWillBeSent(), 
						request -> {
							if(request.getRequest().getUrl().contains("neuron-api/visualization/api/antennaStats/search")){
								Headers headers = request.getRequest().getHeaders();
								System.out.println("Authorization value :" + headers.get("Authorization"));
								String BearerToken_initial = headers.get("Authorization").toString();
								String[] BeBearerToken_Array = BearerToken_initial.split("Bearer");
								String BearerToken = BeBearerToken_Array[1].trim();
								String Payload = request.getRequest().getPostData().toString();
								System.out.println("The Bearer Token is : "+ BearerToken);
								System.out.println("The Payload used is:"  +Payload );
								Assert.assertTrue(Payload.contains(Actual_StartDate),"Improper Payload is sent");
								Assert.assertTrue(Payload.contains(Actual_EndDate),"Improper Payload is sent");
							}
						});

				String[] expResponse = {Actual_StartDate, Actual_EndDate, "antennaStatsMap", "FWD PORT", "azimuth", "elevation", "relative_azimuth", "sat_lng",  "signal_strength", "satelliteName", "snr", "rx_lock", "location", "lat", "lon" };
				//Validating Response Code
				RequestId[] requestIds = new RequestId[1];
				devTools.addListener(Network.responseReceived(), 
						response -> {
							if(response.getResponse().getUrl().contains("neuron-api/visualization/api/antennaStats/search")) {
								int statusCode = response.getResponse().getStatus();
								System.out.println("*** Response Status Code is :" +statusCode);
								Assert.assertTrue(statusCode==200, "Invalid Status Code - No proper data returned to the graph");
								requestIds[0] = response.getRequestId();
								String  responseBody = devTools.send(Network.getResponseBody(requestIds[0])).getBody();
								System.out.println("*** Response :" +responseBody);
								for(String data: expResponse) {
									Assert.assertTrue((responseBody.contains(data)),"One or more data - " +data+ " is missing in the RF Statistics Graph");
								}
							}
						}
						);
				driver.findElement(btn_Apply).click();
				waitForPageLoad();
	}



}
