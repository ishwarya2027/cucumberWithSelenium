import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@CucumberOptions(features = { "src/test/resource/featureFile" }, glue = {
		"stepDefinition" }, tags = "@2020feature", plugin = { "pretty", "html:target/HtmlReports" })
public class TestRunner {


}