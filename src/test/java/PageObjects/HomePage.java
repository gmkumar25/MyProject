package PageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePage {
	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	/**  ****** All the Locators goes here ******  **/
	By txtBox_UserName = By.name("username");
	By txtBox_Password = By.name("password");
	By btn_Login = By.xpath("//span[text()='LOGIN']");
	By btn_Menu = By.xpath("(//span[@class='MuiIconButton-label'])[1]");
	By link_ActiveProbe = By.xpath("//li[text()='Active Probe']");



	/**  ****** All the Methods goes here ****** **/ 
	public void Launch_NeuronFleet(String NeuronFleetName) throws InterruptedException {
		String URL = null;
		if(NeuronFleetName.equalsIgnoreCase("horizon")) {
			URL = "https://horizon.neuron.espacenetworks.io";
		}
		else if(NeuronFleetName.equalsIgnoreCase("valor")) {
			URL = "https://valor.neuron.espacenetworks.io";
		}
		else if(NeuronFleetName.equalsIgnoreCase("freedom")) {
			URL = "https://freedom.neuron.espacenetworks.io";
		}
		waitForPageLoad();
		driver.get(URL);
		waitForPageLoad();
		driver.navigate().refresh();
		waitForPageLoad();
		Thread.sleep(10000);
		waitForPageLoad();
		driver.navigate().refresh();
		waitForPageLoad();
		Thread.sleep(10000);
		
	}

	public void login_NeuronFleet(String Username, String Password) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(txtBox_UserName)));
		driver.findElement(txtBox_UserName).sendKeys(Username);
		driver.findElement(txtBox_Password).sendKeys(Password);
		driver.findElement(btn_Login).click();
		waitForPageLoad();
	}

	public void validate_loginSuccess() throws InterruptedException{
		waitForPageLoad();
		String ExpText = "Neuron Console";
		String ActText = driver.getPageSource();
		Assert.assertTrue(ActText.contains(ExpText),"Login is unsuccessful");
	}


	public void waitForPageLoad() throws InterruptedException {
		Thread.sleep(3000);
	}
	
	public void navigateToQoeProbePage() throws InterruptedException {
		String URL = driver.getCurrentUrl();
		String[] splitted_URL = URL.split(".io/");
		String expected_URL = splitted_URL[0].trim()+".io/site/qoe-probe";
		waitForPageLoad();
		driver.navigate().to(expected_URL);
		waitForPageLoad();
		waitForPageLoad();
	}



}

