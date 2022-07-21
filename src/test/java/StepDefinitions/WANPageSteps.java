package StepDefinitions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import PageObjects.HomePage;
import PageObjects.QoeProbePage;
import PageObjects.RFSystemPage;
import PageObjects.TestBase;
import PageObjects.WANPage;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;

public class WANPageSteps {

	WebDriver driver;
	HomePage homePage;
	QoeProbePage qoeProbePage;
	RFSystemPage rfStatisticsPage;
	WANPage wanPage;
	String Neuron_Fleet_Name;
	String BrowserType;
	String Username;
	String Password;
	Scenario scenario;

	public WANPageSteps(TestBase testBase) throws IOException {
		driver = testBase.openBrowser();
	}

	@Before
	public void testDataSetup(Scenario scenario) throws IOException {
		this.scenario = scenario;
		BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\Config\\App_Config.properties"));
		Properties properties = new Properties();
		properties.load(reader);
		Neuron_Fleet_Name = properties.getProperty("NeuronFleet_Name");
		Username = properties.getProperty("Username"); 
		Password = properties.getProperty("Password");
		BrowserType = properties.getProperty("BrowserType");
	}


	@When("I navigate to the Wan Performance page")
	public void i_navigate_to_the_wan_performance_page() throws Exception {
		try {
			Thread.sleep(5000);
			rfStatisticsPage = new RFSystemPage(driver);
			homePage = new HomePage(driver);
			wanPage = new WANPage(driver);
			wanPage.accessWANPerformancePage();
			wanPage.waitForPageLoad();
			scenario.log("User is able to navigate to the WAN Performance Page");
		} catch (Exception e) {
			scenario.log("User is not able to navigate to the WAN Performance Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate the UI of the Wan Performance page")
	public void i_should_be_able_to_validate_the_ui_of_the_wan_performance_page() throws Exception {
		try {
			wanPage.waitForPageLoad();
			wanPage.validateWANPerformancePageUI();
			scenario.log("User successfully validated the UI of the WAN Performance page");
		} catch (Exception e) {
			scenario.log("User could not validate the UI of the WAN Performance page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate the presence of various graphs under WAN Performance Page")
	public void i_should_be_able_to_validate_the_presence_of_various_graphs_under_wan_performance_page() throws Exception {
		try {
			wanPage.waitForPageLoad();
			wanPage.validateWANPerformancePageGraphs();
			scenario.log("User successfully validated the presence of various graphs under WAN Performance Page");
		} catch (Exception e) {
			scenario.log("User could not validate the presence of various graphs under WAN Performance Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}
	
	@Then("I should be able to validate the presence of graphical data under WAN Performance Page")
	public void i_should_be_able_to_validate_the_presence_of_graphical_data_under_wan_performance_page() throws Exception {
	    try {
				wanPage.waitForPageLoad();
				wanPage.validate_WANPerformance_GraphicalData();
				scenario.log("User successfully validated the presence of graphical data under WAN Performance Page");
			} catch (Exception e) {
				scenario.log("User could not validate the presence of graphical data under WAN Performance Page");
				throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
			}
	}








}