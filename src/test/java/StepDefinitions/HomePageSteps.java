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
import PageObjects.TestBase;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;

public class HomePageSteps {
	WebDriver driver;
	HomePage homePage;
	Scenario scenario;
	String Neuron_Fleet_Name;
	String Username;
	String Password;
	String BrowserType;

	public HomePageSteps(TestBase testBase) throws IOException {
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

	@AfterStep
	public void takeScreenshot(Scenario scenario) {
		if (scenario.isFailed()) {
			final byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "image");
		}
	}

	@After
	public void tearDown() {
		try {
			driver.quit();
			driver = null;
		}
		catch(Exception e) {
		}
	}
	
	@Given("I have the URL of Neuron Fleet application")
	public void i_have_the_url_of_neuron_fleet_application() throws Exception {
		try {
			homePage = new HomePage(driver);
			homePage.Launch_NeuronFleet(Neuron_Fleet_Name);
			scenario.log("User has launched the Neuron Fleet application Successfully");
		} catch (Exception e) {
			scenario.log("User provided an invalid URL");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****");
		}
	}

	@When("I login the Neuron Fleet application with valid username and password")
	public void i_login_the_neuron_fleet_application_with_valid_username_and_password() throws Exception {
		try {
			homePage.login_NeuronFleet(Username, Password);
			scenario.log("User has entered the username :"+Username+ " and the password :"+Password+ " successfully");
		} catch (Exception e) {
			scenario.log("User could not enter the username and password successfully");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****");
		}
	}

	@Then("I should be able to login into the Neuron Fleet application")
	public void i_should_be_able_to_login_into_the_neuron_fleet_application() throws Exception {
		try {
			homePage.validate_loginSuccess();
			scenario.log("User is able to login successfully and see the homepage");
		} catch (Exception e) {
			scenario.log("User could not login");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****");
		}
	}

	@Then("I should be able to close the Neuron Fleet application")
	public void i_should_be_able_to_close_the_neuron_fleet_application() throws Exception {
		try {
			Thread.sleep(2000);
			driver.close();
			scenario.log("User is able to close the application");
		} catch (Exception e) {
			scenario.log("User is not able to close the application");
			throw new Exception("****  This step has failed - Please refer to the bottom for screenshot  ****");
		}
	}











}
