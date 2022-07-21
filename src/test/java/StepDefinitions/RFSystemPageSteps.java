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
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;

public class RFSystemPageSteps {

	WebDriver driver;
	HomePage homePage;
	QoeProbePage qoeProbePage;
	RFSystemPage rfStatisticsPage;
	String Neuron_Fleet_Name;
	String BrowserType;
	String Username;
	String Password;
	Scenario scenario;

	public RFSystemPageSteps(TestBase testBase) throws IOException {
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


	@When("I navigate to the RF Statistics page")
	public void i_navigate_to_the_rf_statistics_page() throws Exception {
		try {
			Thread.sleep(5000);
			rfStatisticsPage = new RFSystemPage(driver);
			homePage = new HomePage(driver);
			rfStatisticsPage.accessRfStatisticsPage(Neuron_Fleet_Name);
			rfStatisticsPage.waitForPageLoad();
			scenario.log("User is able to navigate to the QOE Probe Page");
		} catch (Exception e) {
			scenario.log("User is not able to navigate to the QOE Probe Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate the UI of the RF Statistics page")
	public void i_should_be_able_to_validate_the_ui_of_the_rf_statistics_page() throws Exception {
		try {
			rfStatisticsPage.validateRFStatisticsPageUI();
			scenario.log("User successfully validated the UI of the RF Statistics page");
		} catch (Exception e) {
			scenario.log("User could not validate the UI of the RF Statistics page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate the Default StartDate and EndDate Filter options in the RF Statistics Page")
	public void i_should_be_able_to_validate_the_default_start_date_and_end_date_filter_options_in_the_rf_statistics_page() throws Exception {
		try {
			rfStatisticsPage.validateStartDateEndDateData_RFStatistics();
			scenario.log("User successfully validated the Default StartDate and EndDate Filter options in the RF Statistics Page");
		} catch (Exception e) {
			scenario.log("Missing one or more Default StartDate and EndDate Filter options in the RF Statistics Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate the Quick Select Filter options in the RF Statistics Page")
	public void i_should_be_able_to_validate_the_quick_select_filter_options_in_the_rf_statistics_page() throws Exception {
		try {
			homePage.waitForPageLoad();
			rfStatisticsPage.validateQuickSelectOptions_RFStatistics();
			scenario.log("User successfully validated the Quick Select Filter Options in the RF Statistics Page");
		} catch (Exception e) {
			scenario.log("Missing one or more Quick Select Filter Options in the RF Statistics Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate that data is displayed in the RF Statistics Page")
	public void i_should_be_able_to_validate_that_data_is_displayed_in_the_rf_statistics_page() throws Exception {
		try {
			rfStatisticsPage.validateRFStaisticsData();
			scenario.log("Expected data is displayed in the RF Statistics Page");
		} catch (Exception e) {
			scenario.log("Expected data is not displayed in the RF Statistics Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}


}
