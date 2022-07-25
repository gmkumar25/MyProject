package PageObjects;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Headers;
import org.openqa.selenium.devtools.v85.network.model.RequestId;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonMappingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class QoeProbePage {
	WebDriver driver;
	HomePage homePage;

	public QoeProbePage(WebDriver driver) {
		this.driver = driver;
	}

	/**  ****** All the Locators goes here ******  **/
	By lable_Sites = By.xpath("//*[text()='Sites']");
	By lable_QoEScore_Crew_Social = By.xpath("(//*[@id='active-probe-qoe']//*//div[contains(@class, 'MuiPaper-rounded')]/div[2]/div[2]/div)[1]");
	By lable_QoEScore_Pax = By.xpath("(//*[@id='active-probe-qoe']//*//div[contains(@class, 'MuiPaper-rounded')]/div[2]/div[2]/div)[2]");
	By lable_QoEScore_Corporate = By.xpath("(//*[@id='active-probe-qoe']//*//div[contains(@class, 'MuiPaper-rounded')]/div[2]/div[2]/div)[3]");
	By lable_QoEScore_Crew_Premium = By.xpath("(//*[@id='active-probe-qoe']//*//div[contains(@class, 'MuiPaper-rounded')]/div[2]/div[2]/div)[4]");
	By lable_Sites_Ago_options = By.xpath("//div[contains(text(), 'Ago')]");
	By lable_QOE_Score = By.xpath("//div[contains(text(), 'Ago')]/following-sibling::div/p");
	By lable_QOE_Score_Difference = By.xpath("//div[contains(text(), 'Ago')]/following-sibling::div/div");
	By lable_QOE_Score_Difference_UpDown = By.xpath("//div[contains(text(), 'Ago')]/following-sibling::div//*[local-name()='svg']");
	By dropDown_Vlan = By.xpath("(//div[contains(@class, 'MuiSelect-select')])[1]");
	By dropDown_Url = By.xpath("(//div[contains(@class, 'MuiSelect-select')])[2]");
	By dropDown_Options = By.xpath("//ul[contains(@class, 'MuiMenu-list')]/li");
	By input_StartDate = By.xpath("(//div[@class='react-datepicker__input-container']/input)[1]");
	By input_EndDate = By.xpath("(//div[@class='react-datepicker__input-container']/input)[2]");
	By btn_QuickSelect = By.xpath("(//*[text()='VLAN']//parent::div//parent::div//*//*[local-name()='svg'])[3]");
	By lable_QuickSelect_Options = By.xpath("//*[text()='Presets']/following-sibling::div/ul/li");
	By img_QoeScoreAnalysis = By.xpath("(//*[local-name()='canvas'])[1]");
	By img_QoeScore_Map = By.xpath("//*[contains(@class, 'leaflet-touch-drag')]");
	By btn_Sites_Apply = By.xpath("//span[text()='APPLY']");
	By img_QoeScoreAnalysis_PageLoadTime = By.xpath("(//*[local-name()='canvas'])[2]");
	By img_QoeProbeMetrics = By.xpath("(//*[local-name()='canvas'])[3]");
	By checkBox_Tcp = By.xpath("//input[@value='tcp_connection_time']");
	By checkBox_Ssl = By.xpath("//input[@value='ssl_handshake_time']");
	By checkBox_Dns = By.xpath("//input[@value='dns_time']");
	By checkBox_TTFB = By.xpath("//input[@value='ttfb']");


	/**  ****** All the Methods goes here ******  **/


	public void validateQoeProbePageUI() throws InterruptedException {
		waitForPageLoad();
		String[] ExpText = {"Sites", "QoE Score Analysis", "QoE Score Analysis- Page Load Time", "QoE Probe Metrics"};
		String ActText = driver.getPageSource();
		for(String s:ExpText) {
			Assert.assertTrue(ActText.contains(s),"User could not launch ActiveProbe page");
		}
	}

	public void validateColourCodingOfSitesData() throws InterruptedException {
		waitForPageLoad();
		List<WebElement> list = driver.findElements(By.xpath("//*[@id='active-probe-qoe']//*//div[contains(@class, 'MuiPaper-rounded')]//div//*//*[contains(@style, 'color')]"));
		for(WebElement ele:list) {
			String DataValue = ele.getText();
			Float DataValue_float = Float.parseFloat(DataValue);
			String DataColor =  ele.getAttribute("style");
			System.out.println("******  Data :" +DataValue+ " ****  Colour Value :" +DataColor);
			if(DataValue_float >= 80) {
				Assert.assertTrue(DataColor.contains("rgb(104, 188, 0)"), "Invalid Color Coding");
			}
			else if(DataValue_float >=60 && DataValue_float <80) {
				Assert.assertTrue(DataColor.contains("rgb(254, 166, 7)"), "Invalid Color Coding");
			}
			else if(DataValue_float >=0 && DataValue_float  <60) {
				//Assert.assertTrue(DataColor.contains("rgb(104, 188, 0)"), "Invalid Color Coding");
			}
		}
	}

	public void ValidatePresenceOfDatainSites() {
		String Actual_CrewSocial_Text = driver.findElement(lable_QoEScore_Crew_Social).getText();
		String Actual_Pax_Text = driver.findElement(lable_QoEScore_Pax).getText();
		String Actual_Corporate_Text = driver.findElement(lable_QoEScore_Corporate).getText();
		String Actual_CrewPremium_Text = driver.findElement(lable_QoEScore_Crew_Premium).getText();
		Assert.assertTrue((Actual_CrewSocial_Text.equalsIgnoreCase("QoE Score - Crew Social")), "The Data for QOE Score Crew Social is missing");
		Assert.assertTrue((Actual_Pax_Text.equalsIgnoreCase("QoE Score - Pax")), "The Data for QoE Score - Pax is missing");
		Assert.assertTrue((Actual_Corporate_Text.equalsIgnoreCase("QoE Score - Corporate")), "The Data for QoE Score - Corporate is missing");
		Assert.assertTrue((Actual_CrewPremium_Text.equalsIgnoreCase("QoE Score - Crew Premium")), "The Data for QoE Score - Crew Premium is missing");
		List<WebElement> Ago_Lables = driver.findElements(lable_Sites_Ago_options);
		Assert.assertTrue((Ago_Lables.size()==8), "One or more expected Ago options is missing");
		for(WebElement ele : Ago_Lables) {
			String lable = ele.getText();
			Boolean flag = ((lable.contains("15 Min Ago"))||(lable.contains("2 Hrs Ago")));
			Assert.assertTrue(flag, "One or more expected Ago options is missing");
		}
		List<WebElement> QoeScore_Lables = driver.findElements(lable_QOE_Score);
		Assert.assertTrue((QoeScore_Lables.size()==8), "One or more expected QoeScore Lables options is missing");
		for(WebElement ele : QoeScore_Lables) {
			String lable = ele.getText();
			Boolean flag = (lable.length()>0);
			Assert.assertTrue(flag, "One or more expected QOE Score is missing");
		}
		List<WebElement> QoeScoreDifference_Lables = driver.findElements(lable_QOE_Score_Difference);
		Assert.assertTrue((QoeScoreDifference_Lables.size()==8), "One or more expected QoeScoreDifference Lables options is missing");
		for(WebElement ele : QoeScoreDifference_Lables) {
			String lable = ele.getText();
			Boolean flag = (lable.length()>0);
			Assert.assertTrue(flag, "One or more expected QOE Score Difference is missing");
		}
		List<WebElement> QoeScoreDifferenceUpDown_Lables = driver.findElements(lable_QOE_Score_Difference_UpDown);
		Assert.assertTrue((QoeScoreDifferenceUpDown_Lables.size()==8), "One or more expected QoeScoreDifference UpDown Lables options is missing");
	}

	public void ValidateSiteFilterDropdownOptions() throws InterruptedException {
		String Actual_Vlan_Dropdown_Default_Value = driver.findElement(dropDown_Vlan).getText();
		String Actual_Url_Dropdown_Default_Value = driver.findElement(dropDown_Url).getText();
		Assert.assertTrue((Actual_Vlan_Dropdown_Default_Value.equals("All")),"Vlan Dropdown Default Value is not All");
		Assert.assertTrue((Actual_Url_Dropdown_Default_Value.equals("All")),"URL Dropdown Default Value is not All");
		driver.findElement(dropDown_Vlan).click();
		waitForPageLoad();
		List<WebElement> Vlan_Dropdown_Options = driver.findElements(dropDown_Options);
		String[] Exp_VlanOptions_Array = {"All", "Crew Social", "Pax", "Corporate", "Crew Premium"};
		ArrayList<String> Exp_VlanOptions = new ArrayList<String>();
		Collections.addAll(Exp_VlanOptions, Exp_VlanOptions_Array);
		ArrayList<String> ActualVlanOptions = new ArrayList<String>();
		for(WebElement  ele:Vlan_Dropdown_Options ) {
			ActualVlanOptions.add(ele.getText());
		}
		Boolean Vlan_flag = ActualVlanOptions.equals(Exp_VlanOptions);
		Assert.assertTrue(Vlan_flag, "One or More expected option is missing in the VLAN Dropdown");
		waitForPageLoad();
		driver.findElement(dropDown_Options).click();
		waitForPageLoad();
		driver.findElement(dropDown_Url).click();
		waitForPageLoad();
		List<WebElement> URL_Dropdown_Options = driver.findElements(dropDown_Options);
		String[] Exp_URLOptions_Array = {"All", "Netflix", "Google", "CNN", "Amazon", "ESPN", "Facebook", "Whatsapp"};
		ArrayList<String> Exp_URLOptions = new ArrayList<String>();
		Collections.addAll(Exp_URLOptions, Exp_URLOptions_Array);
		ArrayList<String> ActualURLOptions = new ArrayList<String>();
		for(WebElement  ele:URL_Dropdown_Options ) {
			ActualURLOptions.add(ele.getText());
		}
		Boolean URL_flag = ActualURLOptions.equals(Exp_URLOptions);
		Assert.assertTrue(URL_flag, "One or More expected option is missing in the URL Dropdown");
		waitForPageLoad();
		driver.findElement(dropDown_Options).click();
		waitForPageLoad();
	}

	public void validateQuickSelectOptions() throws InterruptedException {
		waitForPageLoad();
		driver.findElement(By.xpath("(//*[text()='VLAN']//parent::div//parent::div//*//*[local-name()='svg'])[3]")).click();
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

	public void waitForPageLoad() throws InterruptedException {
		Thread.sleep(3000);
	}

	public void validateStartDateEndDateData() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd @ HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		String localDateTime = dtf.format(now);
		System.out.println("***  Local Date & Time :" +localDateTime);
		String[] localDateTime_Split = localDateTime.split("@");
		String localDate = localDateTime_Split[0];
		String localTime = ((localDateTime_Split[1].split(":"))[0]).trim();
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

	public void validateQoeScoreAnalysisData() throws InterruptedException {
		waitForPageLoad();
		Boolean QoeScoreAnalysisGraph_flag = driver.findElement(img_QoeScoreAnalysis).isDisplayed();
		Assert.assertTrue(QoeScoreAnalysisGraph_flag, "The QOE Score Analysis graph is not displayed");
		Boolean QoeScoreAnalysisMap_flag = driver.findElement(img_QoeScore_Map).isDisplayed();
		Assert.assertTrue(QoeScoreAnalysisMap_flag, "The QOE Score page Map is not displayed");

		//Validating the Graph Data from Chrome's Networks tab 
		String Actual_StartDateTime = driver.findElement(input_StartDate).getAttribute("value");
		String[] Actual_StartDate_Split = Actual_StartDateTime.split("@");
		String Actual_StartDate = Actual_StartDate_Split[0].trim();
		String Actual_EndDateTime = driver.findElement(input_EndDate).getAttribute("value");
		String[] Actual_EndDate_Split = Actual_EndDateTime.split("@");
		String Actual_EndDate = Actual_EndDate_Split[0].trim();
		DevTools devTools = ((ChromeDriver) driver).getDevTools();
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.addListener(Network.requestWillBeSent(), 
				request -> {
					if(request.getRequest().getUrl().contains("neuron-api/dpi/api/activeProbe/getAggregated")){
						System.out.println("Exp Data :" + request.getRequest().getHeaders());
						Headers headers = request.getRequest().getHeaders();
						System.out.println("Authorization value :" + headers.get("Authorization"));
						String BearerToken_initial = headers.get("Authorization").toString();
						String[] BeBearerToken_Array = BearerToken_initial.split("Bearer");
						String BearerToken = BeBearerToken_Array[1].trim();
						String Payload = request.getRequest().getPostData().toString();
						System.out.println("The Bearer Token is : "+ BearerToken);
						System.out.println("The Payload used is:"  +Payload );
						System.out.println("*** Actual Start/End Date :" +Actual_StartDate+ " *** " +Actual_EndDate);
						Assert.assertTrue(Payload.contains(Actual_StartDate),"Improper Payload is sent");
						Assert.assertTrue(Payload.contains(Actual_EndDate),"Improper Payload is sent");
					}
				});

		String[] expResponse = {Actual_StartDate, Actual_EndDate, "dns_time", "page_load_time", "qoe_score", "ssl_handshake_time", "tcp_connection_time", "throughput", "timestamp", "total_time", "ttfb", "url", "geo_location" };
		//Validating Response Code
		RequestId[] requestIds = new RequestId[1];
		devTools.addListener(Network.responseReceived(), 
				response -> {
					if(response.getResponse().getUrl().contains("neuron-api/dpi/api/activeProbe/getAggregated")) {
						int statusCode = response.getResponse().getStatus();
						System.out.println("*** Response Status Codes :" +statusCode);
						Assert.assertTrue(statusCode==200, "Invalid Status Code - No proper data returned to the graph");
						requestIds[0] = response.getRequestId();
						String  responseBody = devTools.send(Network.getResponseBody(requestIds[0])).getBody();
						System.out.println("*** Response :" +responseBody);
						for(String data: expResponse) {
							Assert.assertTrue((responseBody.contains(data)),"One or more data is missing in the QOE Score Analysis Graph");
						}
					}
				}
				);
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(btn_Sites_Apply)).perform();
		driver.findElement(btn_Sites_Apply).click();
		waitForPageLoad();
	}

	public void validateQoeScoreAnalysis_PageLoadTime_Data() throws InterruptedException {
		waitForPageLoad();
		WebElement QoeScoreAnalysisPageLoadTimeGraph =   driver.findElement(img_QoeScoreAnalysis_PageLoadTime);
		Actions actions = new Actions(driver);
		actions.moveToElement(QoeScoreAnalysisPageLoadTimeGraph).perform();
		Boolean QoeScoreAnalysisPageLoadTimeGraph_flag = QoeScoreAnalysisPageLoadTimeGraph.isDisplayed();
		Assert.assertTrue(QoeScoreAnalysisPageLoadTimeGraph_flag, "The QOE Score Analysis Page Load Time graph is not displayed");
	}
	
	public void validateQoeProbeMetrics_Data() throws InterruptedException {
		waitForPageLoad();
		WebElement QoeProbeMetricsGraph =   driver.findElement(img_QoeProbeMetrics);
		waitForPageLoad();
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(checkBox_Ssl)).perform();
		Boolean QoeProbeMetricsGraph_flag = QoeProbeMetricsGraph.isDisplayed();
		Assert.assertTrue(QoeProbeMetricsGraph_flag, "The QOE Probe Metrics graph is not displayed");
		driver.findElement(checkBox_Ssl).click();
		waitForPageLoad();
		Assert.assertTrue((driver.getPageSource().contains("The time it took from DNS resolution until the SSL connect/handshake with the remote host was completed")),"SSL Handshake Time is not selected");
		driver.findElement(checkBox_Dns).click();
		waitForPageLoad();
		Assert.assertTrue((driver.getPageSource().contains("The time it took from the start until the name resolving was completed")),"DNS Time is not selected");
		driver.findElement(checkBox_TTFB).click();
		waitForPageLoad();
		Assert.assertTrue((driver.getPageSource().contains("The time it took from TCP Connection until the first byte is received")),"TTFB is not selected");
		driver.findElement(checkBox_Tcp).click();
		waitForPageLoad();
		Assert.assertTrue((driver.getPageSource().contains("The time it took from SSL Handshake until the connect to the remote host was completed")),"TCP Connection Time is not selected");
	}
	
	public void prepareQoeProbeAPI_Details() {
		System.out.println("*** Let us test the API of QOE Score Analysis");
	}
	
	public void validateQoeScoreAnalysisAPI( String NeuronFleetName, String Username, String Password) {
		String baseURI = null;
		if(NeuronFleetName.equalsIgnoreCase("horizon")) {
			baseURI = "https://horizon.neuron.espacenetworks.io/neuron-api/authentication/api/user/login?username="+Username+"&password="+Password;
		}
		else if(NeuronFleetName.equalsIgnoreCase("valor")) {
			baseURI = "https://valor.neuron.espacenetworks.io/neuron-api/authentication/api/user/login?username="+Username+"&password="+Password;
		}
		else if(NeuronFleetName.equalsIgnoreCase("freedom")) {
			baseURI = "https://freedom.neuron.espacenetworks.io/neuron-api/authentication/api/user/login?username="+Username+"&password="+Password;
		}
		RestAssured.baseURI ="https://horizon.neuron.espacenetworks.io/neuron-api/authentication/api/user/login?username=admin&password=neuron$22"; 
	    RequestSpecification request = RestAssured.given(); 
	    JSONObject requestParams = new JSONObject();
	    requestParams.put("username", "admin");
	    requestParams.put("password", "neuron$22"); 
	    request.body(requestParams.toJSONString());
		Response response = request.relaxedHTTPSValidation().accept(ContentType.JSON)
                .contentType(ContentType.JSON).post();
	    ResponseBody body = response.getBody();
	    System.out.println("*** Initial Response of login API " +body.asString());
	    String RefreshToken = response.jsonPath().getString("refreshToken");
	    String AccessToken = response.jsonPath().getString("accessToken");
	    
	    System.out.println("***  RefreshToken is :" +RefreshToken+ "  *** Access Token is :" +AccessToken);

	    System.out.println("****************** -------- *****************************");
	    
	    RestAssured.baseURI = "https://horizon.neuron.espacenetworks.io/neuron-api/dpi/api/activeProbe/getAggregated?username=admin&password=neuron";
	    RequestSpecification request1 = RestAssured.given(); 
	    
	    //Find End Date
	    Instant instant = Instant.now(Clock.systemUTC());
	    String data = instant.toString();
	    String EndDate = data.substring(0,19);
	    
	    //find Start date
	    String prevDate = instant.minusSeconds(3600).toString();
	    String StartDate = prevDate.substring(0,19);
	    
	    JSONObject requestParams1 = new JSONObject();
	    requestParams1.put("fromTimestamp", StartDate);
	    requestParams1.put("toTimestamp", EndDate);
	    requestParams1.put("configSiteId", "1");
	    requestParams1.put("configCustomerId", "1");
	    request1.body(requestParams1.toJSONString());
	    
	    Response response1 = request1.header("authorization", "Bearer "+AccessToken).relaxedHTTPSValidation().accept(ContentType.JSON)
                .contentType(ContentType.JSON).post();
	    ResponseBody body1 = response1.getBody();
	    System.out.println("**************** Active QOE Data ***************");
	    System.out.println("*** Response Contents :" +body1.asString());
	    
	    try {
			ObjectMapper mapper = new ObjectMapper();
			HashMap map = mapper.readValue(response1.asString(), HashMap.class);
			HashMap responseData = (HashMap) map.get("data");  //here data is the root node found in the response
			
			responseData.forEach((k,v)->{
				System.out.println("*** Key of the Response Map is :" +k);
				System.out.println("*** Value of the Response Map is :" +v.toString());
				System.out.println("**************** Now Printing the Individual Data from the response ***************");
				((ArrayList)v).forEach(v1->{
					Map<String, Object> d1= (HashMap)v1;
					d1.forEach((k2,v2)->{
						System.out.println("The value of " +k2+"  is :" +v2);
						
					});
					
			});
			});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
