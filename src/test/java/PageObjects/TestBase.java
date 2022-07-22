package PageObjects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;

/**
 * 
 * @author Muthukumar Gandhi - 
 * This class will initialize and share the webdriver across multiple step definition files
 * This class has implements data injection using picocontainer dependencies 
 *
 */

public class TestBase {
	WebDriver driver;
	String BrowserType;
	String HeadlessMode;

	public WebDriver openBrowser() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\Config\\App_Config.properties"));
		Properties properties = new Properties();
		properties.load(reader);
		BrowserType = properties.getProperty("BrowserType");
		HeadlessMode = properties.getProperty("HeadlessMode");
		if(driver == null) {
			if(BrowserType.equalsIgnoreCase("chrome")){
				String projectPath = System.getProperty("user.dir");
				System.setProperty("webdriver.chrome.driver", projectPath+"\\src\\test\\resources\\Drivers\\chromedriver.exe");
				if(HeadlessMode.equalsIgnoreCase("true")) {
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.addArguments("--headless");
					driver = new ChromeDriver(chromeOptions);
				}
				else if(HeadlessMode.equalsIgnoreCase("false")) {
					driver = new ChromeDriver();
				}
			}
			
			else if(BrowserType.equalsIgnoreCase("firefox")){
				String projectPath = System.getProperty("user.dir");
				System.setProperty("webdriver.gecko.driver", projectPath+"\\src\\test\\resources\\Drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
			}


driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
		}
		return driver;
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


}
