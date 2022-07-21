package PageObjects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Headers;
import org.openqa.selenium.devtools.v85.network.model.RequestId;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class WANPage {
	WebDriver driver;
	HomePage homePage;

	public WANPage(WebDriver driver) {
		this.driver = driver;
	}

	/**  ****** All the Locators goes here ******  **/
	By img_LinkPerformanceStats_Graph  = By.xpath("//span[text()='Link Performance Stats']//ancestor::div[contains(@class, 'MuiPaper-rounded')]//*//*[local-name()='canvas']");
	By img_LinkLatency_Graph  = By.xpath("//span[text()='Link Latency']//ancestor::div[contains(@class, 'MuiPaper-rounded')]//*//*[local-name()='canvas']");
	By img_LinkPLDownstream_Graph  = By.xpath("//span[text()='Link Packet Loss - Downstream']//ancestor::div[contains(@class, 'MuiPaper-rounded')]//*//*[local-name()='canvas']");
	By img_LinkPLUpstream_Graph  = By.xpath("//span[text()='Link Packet Loss - Upstream']//ancestor::div[contains(@class, 'MuiPaper-rounded')]//*//*[local-name()='canvas']");
	By img_LinkEstimatedAllocation_Graph  = By.xpath("//span[text()='Link Estimated Allocation']//ancestor::div[contains(@class, 'MuiPaper-rounded')]//*//*[local-name()='canvas']");
	By img_WANUtilization_Graph  = By.xpath("//span[text()='WAN Utilization']//ancestor::div[contains(@class, 'MuiPaper-rounded')]//*//*[local-name()='canvas']");
	By img_CIRvsEstDownstream_Graph  = By.xpath("//span[text()='CIR vs Est Allocation vs Throughput (Downstream)']//ancestor::div[contains(@class, 'MuiPaper-rounded')]//*//*[local-name()='canvas']");
	By img_CIRvsEstUpstream_Graph  = By.xpath("//span[text()='CIR vs Est Allocation vs Throughput (Upstream)']//ancestor::div[contains(@class, 'MuiPaper-rounded')]//*//*[local-name()='canvas']");
	By btn_Apply = By.xpath("//span[text()='APPLY']");
	
	
	
	
	/**  ****** All the Methods goes here ******  **/
	public void accessWANPerformancePage() throws InterruptedException {
		String URL = driver.getCurrentUrl();
		String[] splitted_URL = URL.split(".io/");
		String expected_URL = splitted_URL[0].trim()+".io/site/wan-performance";
		waitForPageLoad();
		driver.navigate().to(expected_URL);
		waitForPageLoad();
		waitForPageLoad();
	}

	public void waitForPageLoad() throws InterruptedException {
		Thread.sleep(4000);
	}
	
	public void validateWANPerformancePageUI() throws InterruptedException {
		waitForPageLoad();
		String[] ExpText = {"WAN Performance", "APPLY", "Link Performance Stats", "Link Latency", "Link Packet Loss - Downstream", "Link Packet Loss - Upstream", "Link Estimated Allocation", "WAN Utilization", "CIR vs Est Allocation vs Throughput (Downstream)", "CIR vs Est Allocation vs Throughput (Upstream)"};
		String ActText = driver.getPageSource();
		for(String s:ExpText) {
			Assert.assertTrue(ActText.contains(s),"User could not launch WAN Performance page");
		}
	}
	
	public void validateWANPerformancePageGraphs() throws InterruptedException {
		waitForPageLoad();
		waitForPageLoad();
		ArrayList<WebElement> graphs = new ArrayList<WebElement>();
		graphs.add(driver.findElement(img_LinkPerformanceStats_Graph));
		graphs.add(driver.findElement(img_LinkLatency_Graph));
		graphs.add(driver.findElement(img_LinkPLDownstream_Graph));
		graphs.add(driver.findElement(img_LinkPLUpstream_Graph));
		graphs.add(driver.findElement(img_LinkEstimatedAllocation_Graph));
		graphs.add(driver.findElement(img_WANUtilization_Graph));
		graphs.add(driver.findElement(img_CIRvsEstDownstream_Graph));
		graphs.add(driver.findElement(img_CIRvsEstUpstream_Graph));
		Actions action = new Actions(driver);
		for(WebElement ele : graphs) {
			action.moveToElement(ele).perform();
			Assert.assertTrue((ele.isDisplayed()), "One or more graph is missing in the WAN Performance page");
		}
		
	}
	
	public void validate_WANPerformance_GraphicalData() throws InterruptedException {
		waitForPageLoad();
		//Validating the Graph Data from Chrome's Networks tab 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd @ HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		String localDateTime = dtf.format(now);
		String[] localDateTime_Split = localDateTime.split("@");
		String localDate = localDateTime_Split[0].trim();
		System.out.println("***  Local Date :" +localDate);
		DevTools devTools = ((ChromeDriver) driver).getDevTools();
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.addListener(Network.requestWillBeSent(), 
				request -> {
					if(request.getRequest().getUrl().contains("neuron-api/visualization/api/neuron/channel/get")){
						Headers headers = request.getRequest().getHeaders();
						System.out.println("Authorization value :" + headers.get("Authorization"));
						String BearerToken_initial = headers.get("Authorization").toString();
						String[] BeBearerToken_Array = BearerToken_initial.split("Bearer");
						String BearerToken = BeBearerToken_Array[1].trim();
						String Payload = request.getRequest().getPostData().toString();
						System.out.println("The Bearer Token is : "+ BearerToken);
						System.out.println("The Payload used is:"  +Payload );
						Assert.assertTrue(Payload.contains(localDate),"Improper Payload is sent");
					}
				});

		String[] expResponse = {localDate, "timestamp", "channel_id", "chn_rtt_ping_ms", "rx_kbps", "tx_kbps", "geo_location", "tx_wan_utilization_percent", "rx_wan_utilization_percent", "total_wan_utilization_percent", "wan_circuit_tx_cir_kbps", "wan_circuit_rx_cir_kbps", "wan_circuit_tx_mir_kbps", "wan_circuit_rx_mir_kbps", "rx_pkts_lost_perc_fg", "rx_pkts_lost_perc_bg", "tx_pkts_lost_perc_fg", "tx_pkts_lost_perc_bg", "tx_estimated_alloc_kbps", "rx_estimated_alloc_kbps", "total_estimated_alloc_kbps", "tx_estimated_bw_kbps", "rx_estimated_bw_kbps"};
		//Validating Response Code
		RequestId[] requestIds = new RequestId[1];
		devTools.addListener(Network.responseReceived(), 
				response -> {
					if(response.getResponse().getUrl().contains("neuron-api/visualization/api/neuron/channel/get")) {
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
		waitForPageLoad();
	}
	
	
	
	
}
