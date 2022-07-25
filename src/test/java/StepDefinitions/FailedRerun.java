package StepDefinitions;

/**
 * This File - Similar to FailedTestNG.xml, will pick the failed test scenarios
 * from the mentioned file and rerun them.
 * 
 */

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber-reports/TestReport.html",
                "json:target/cucumber-reports/TestReport.json",
                "rerun:target/failedscenariosrerun.txt"
                              
        },
        features = {"@target/failedscenariosrerun.txt"},
        glue = {"StepDefinitions"},
        tags= "@smoke"
        
)
public class FailedRerun extends AbstractTestNGCucumberTests {
	
	@Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
