package StepDefinitions;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber-reports/TestReport.html",
                "json:target/cucumber-reports/TestReport.json",
                "rerun:target/failedscenariosrerun.txt"
                              
        },
        features = {"src/test/resources/Features"},
        glue = {"StepDefinitions"},
        tags= "@smoke"
        
)
public class TestRunner extends AbstractTestNGCucumberTests {
	
	@Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
