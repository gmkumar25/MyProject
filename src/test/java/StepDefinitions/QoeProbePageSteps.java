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
import PageObjects.TestBase;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;

public class QoeProbePageSteps {

	WebDriver driver;
	HomePage homePage;
	Scenario scenario;
	String Neuron_Fleet_Name;
	String Username;
	String Password;
	String BrowserType;
	QoeProbePage qoeProbePage;

	public QoeProbePageSteps(TestBase testBase) throws IOException {
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

	/**
	@AfterStep
	public void takeScreenshot(Scenario scenario) {
		if (scenario.isFailed()) {
			final byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "image");
		}
	}
	 **/

	@Given("I access the Neuron Fleet application")
	public void i_access_the_neuron_fleet_application() throws Exception {
		try {
			homePage = new HomePage(driver);
			qoeProbePage =  new QoeProbePage(driver);
			homePage.Launch_NeuronFleet(Neuron_Fleet_Name);
			homePage.login_NeuronFleet(Username, Password);
			homePage.validate_loginSuccess();
			scenario.log("User is able to login the Neuron Fleet successfully and see the homepage");
		} catch (Exception e) {
			scenario.log("User could not login the Neuron Fleet Application");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@When("I navigate to the QOE Probe page")
	public void i_navigate_to_the_qoe_probe_page() throws Exception {
		try {
			Thread.sleep(5000);
			homePage.navigateToQoeProbePage();
			scenario.log("User is able to navigate to the QOE Probe Page");
		} catch (Exception e) {
			scenario.log("User is not able to navigate to the QOE Probe Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate the UI of the QOE Probe page")
	public void i_should_be_able_to_validate_the_ui_of_the_qoe_probe_page() throws Exception {
		try {
			qoeProbePage.validateQoeProbePageUI();
			scenario.log("User successfully validated the UI of the QOE Probe page");
		} catch (Exception e) {
			scenario.log("User could not validate the UI of the QOE Probe page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}

	}

	@Then("I should be able to validate the presence of data in the Sites section of QoeProbe Page")
	public void i_should_be_able_to_validate_the_presence_of_data_in_the_sites_section_of_qoe_probe_page() throws Exception {
		try {
			homePage.waitForPageLoad();
			qoeProbePage.ValidatePresenceOfDatainSites();
			scenario.log("User successfully validated the presence of data in the Sites section of QoeProbe Page");
		} catch (Exception e) {
			scenario.log("Expected data in the Sites section of QoeProbe Page is missing");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate the colour coding of data in the Sites section of QoeProbe Page")
	public void i_should_be_able_to_validate_the_colour_coding_of_data_in_the_sites_section_of_qoe_probe_page() throws Exception {
		try {
			homePage.waitForPageLoad();
			qoeProbePage.validateColourCodingOfSitesData();
			scenario.log("User successfully validated the colour coding of data in the Sites section of QoeProbe Page");
		} catch (Exception e) {
			scenario.log("Found Invalid Colour coding of data in the Sites section of QoeProbe Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate the Dropdown filter options in the Sites section of QoeProbe Page")
	public void i_should_be_able_to_validate_the_dropdown_filter_options_in_the_sites_section_of_qoe_probe_page() throws Exception {
		try {
			homePage.waitForPageLoad();
			qoeProbePage.ValidateSiteFilterDropdownOptions();
			scenario.log("User successfully validated the Dropdown Filter Options in the Sites section of QoeProbe Page");
		} catch (Exception e) {
			scenario.log("Missing one or more Dropdown Filter Options in the Sites section of QoeProbe Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate the Quick Select Filter options in the Sites section of QoeProbe Page")
	public void i_should_be_able_to_validate_the_quick_select_filter_options_in_the_sites_section_of_qoe_probe_page() throws Exception {
		try {
			homePage.waitForPageLoad();
			qoeProbePage.validateQuickSelectOptions();
			scenario.log("User successfully validated the Quick Select Filter Options in the Sites section of QoeProbe Page");
		} catch (Exception e) {
			scenario.log("Missing one or more Quick Select Filter Options in the Sites section of QoeProbe Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate the Default StartDate and EndDate Filter options in the Sites section of QoeProbe Page")
	public void i_should_be_able_to_validate_the_default_start_date_and_end_date_filter_options_in_the_sites_section_of_qoe_probe_page() throws Exception {
		try {
			qoeProbePage.validateStartDateEndDateData();
			scenario.log("User successfully validated the Default StartDate and EndDate Filter Options in the Sites section of QoeProbe Page");
		} catch (Exception e) {
			scenario.log("Missing one or more Default StartDate and EndDate Filter Options in the Sites section of QoeProbe Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate that data is displayed in the QOE Score Analysis section of QoeProbe Page")
	public void i_should_be_able_to_validate_that_data_is_displayed_in_the_qoe_score_analysis_section_of_qoe_probe_page() throws Exception {
		try {
			qoeProbePage.validateQoeScoreAnalysisData();
			scenario.log("Expected data is displayed in the QOE Score Analysis section of QoeProbe Page");
		} catch (Exception e) {
			scenario.log("Expected data is not displayed in the QOE Score Analysis section of QoeProbe Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate that data is displayed in the QOE Score Analysis Page Load Time section of QoeProbe Page")
	public void i_should_be_able_to_validate_that_data_is_displayed_in_the_qoe_score_analysis_page_load_time_section_of_qoe_probe_page() throws Exception {
		try {
			qoeProbePage.validateQoeScoreAnalysis_PageLoadTime_Data();
			scenario.log("Expected data is displayed in the QOE Score Analysis Page Load Time section of QoeProbe Page");
		} catch (Exception e) {
			scenario.log("Expected data is not displayed in the QOE Score Analysis Page Load Time section of QoeProbe Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to validate that data is displayed in the QOE probe Metrics section of QoeProbe Page")
	public void i_should_be_able_to_validate_that_data_is_displayed_in_the_qoe_probe_metrics_section_of_qoe_probe_page() throws Exception {
		try {
			qoeProbePage.validateQoeProbeMetrics_Data();
			scenario.log("Expected data is displayed in the QOE Probe Metrics section of QoeProbe Page");
		} catch (Exception e) {
			scenario.log("Expected data is not displayed in the QOE Probe Metrics section of QoeProbe Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@When("I do a POST with required inputs in EndPoint URL of the QOE score Analysis")
	public void i_do_a_post_with_required_inputs_in_end_point_url_of_the_qoe_score_analysis() throws Exception {
		try {
			qoeProbePage = new QoeProbePage(driver);
			qoeProbePage.waitForPageLoad();
			qoeProbePage.prepareQoeProbeAPI_Details();
			scenario.log("User successfully got details to perform API Testing of QoeProbe Page");
		} catch (Exception e) {
			scenario.log("Missing one or more details to perform API Testing of QoeProbe Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}

	@Then("I should be able to verify the response")
	public void i_should_be_able_to_verify_the_response() throws Exception {
		try {
			qoeProbePage.waitForPageLoad();
			qoeProbePage.validateQoeScoreAnalysisAPI(Neuron_Fleet_Name, Username, Password);
			scenario.log("User successfully completed the API Testing of QoeProbe Page");
		} catch (Exception e) {
			scenario.log("User could not complete the API Testing of QoeProbe Page");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****" +e.getMessage());
		}
	}




}
