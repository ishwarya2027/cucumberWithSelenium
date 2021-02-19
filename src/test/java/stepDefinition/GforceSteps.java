package stepDefinition;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GforceSteps {

	WebDriver driver;
	WebDriverWait wait;

	// Reusable functions
	public void openBrowser() {
		System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 10);

	}

	public void openDatabaseUrl() {
		driver.get("http://computer-database.herokuapp.com/computers");
		driver.manage().window().maximize();
	}

	public WebElement addNewComputerButton() {
		return driver.findElement(By.id("add"));
	}

	public void verifyAddNewComputerButtonText() {
		Assert.assertEquals(addNewComputerButton().getText(), "Add a new computer");
	}

	public String getFieldLabel(String locator) {
		return driver.findElement(By.xpath("//*[@id=\"" + locator + "\"]/../preceding-sibling::label")).getText();
	}

	public String getFieldTagName(String locator) {
		return driver.findElement(By.id(locator)).getTagName();
	}

	// step definitions
	@Given("I am using a web browser")
	public void webBrowswer() {
		this.openBrowser();
	}

	@When("I navigate to the computer database app")
	public void navigateToDataBaseApp() {
		this.openBrowser();
		this.openDatabaseUrl();
	}

	@Then("I should see a new button called \"Add a New Computer\"")
	public void verifyAddNewButton() {
		Assert.assertEquals(addNewComputerButton().getText(), "Add a new computer", "Add a new button text mismatch!");
	}

	@Given("I am already on the database application")
	public void presentOnDatabaseApp() {
		this.openBrowser();
		this.openDatabaseUrl();
	}

	@And("the link to the new section is present")
	public void linkToNewSection() {
		this.verifyAddNewComputerButtonText();
	}

	@When("I click on the link")
	public void clickNewComputerLink() {
		this.addNewComputerButton().click();
	}

	@Then("I should be taken to the new section to update the database")
	public void newSectionPage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section/h1")));
		Assert.assertTrue(driver.findElement(By.xpath("//section/h1")).isDisplayed());
	}

	@And("The new section should have a heading of \"Add a New Computer\"")
	public void verifyNewSectionHeading() {
		String header = driver.findElement(By.xpath("//*[@id='main']/h1")).getText();
		Assert.assertEquals(header, "Add a computer");
	}

	@When("I navigate to the new section using the button link")
	public void clickNewComputer() {
		this.addNewComputerButton().click();
	}

	@Then("^I should see four fields labelled with a type below")
	public void fieldValidation(DataTable fieldList) throws Exception {
		List<Map<String, String>> data = fieldList.asMaps();
		for (Map<String, String> field : data) {
			String fieldLabel;
			String tagName;
			switch (field.get("fields")) {

			case "Computer name":
				fieldLabel = this.getFieldLabel("name");
				tagName = this.getFieldTagName("name");
				break;
			case "Introduced date":
				fieldLabel = this.getFieldLabel("introduced");
				tagName = this.getFieldTagName("introduced");
				break;
			case "Discontinued date":
				fieldLabel = this.getFieldLabel("discontinued");
				tagName = this.getFieldTagName("discontinued");
				break;
			case "Company":
				fieldLabel = this.getFieldLabel("company");
				tagName = this.getFieldTagName("company");
				break;
			default:
				throw new Exception("No fields matching");
			}
			Assert.assertEquals(fieldLabel, field.get("fields"), "Field label not as per requirement");
			Assert.assertTrue(field.get("type").contains(tagName),
					"Field - " + field.get("fields") + " is not free input!!");
		}
	}

	@And("^I should see two buttons named below")
	public void buttonValidation(DataTable buttons) throws Exception {
		List<Map<String, String>> data = buttons.asMaps();
		for (Map<String, String> field : data) {
			String buttonLabel;
			switch (field.get("buttons")) {
			case "Add Computer":
				buttonLabel = driver.findElement(By.xpath("//div[@class='actions']//input")).getAttribute("value");
				Assert.assertEquals(buttonLabel, "Create this computer", "Button label not as per requirement!!");
				break;
			case "Cancel":
				buttonLabel = driver.findElement(By.xpath("//div[@class='actions']//a")).getText();
				Assert.assertEquals(buttonLabel, "Cancel", "Button label not as per requirement!!");
				break;
			default:
				throw new Exception("No button fields matching");
			}
		}
	}

	@Given("I am already adding a computer in the Add a New Computer view")
	public void addNewComputerButtonPage() {
		this.openBrowser();
		this.openDatabaseUrl();
		this.addNewComputerButton().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section/h1")));
	}

	@Given("I have already entered a name for a computer")
	public void enterComputerName() {
		driver.findElement(By.id("name")).sendKeys("testName");
	}

	@But("Have not entered data into other fields")
	public void notEnterAnyField() {
		driver.findElement(By.id("introduced")).isDisplayed();
	}

	@When("I click \"Add This Computer\"")
	public void clickAddNewComputerButton() {
		driver.findElement(By.xpath("//div[@class='actions']//input")).click();
	}

	@When("It should not allow you to save")
	public void shouldNotSave() throws InterruptedException {
		Thread.sleep(3000);
		String header = driver.findElement(By.xpath("//*[@id='main']/h1")).getText();
		Assert.assertEquals(header, "Add a computer",
				"Error - The new computer is allowed to save though all fields are not entered!");
	}
	
	@When("I have already entered all fields for a new computer")
	public void enterAllFields() {
		driver.findElement(By.id("name")).sendKeys("testName");
		driver.findElement(By.id("introduced")).sendKeys("2008-04-01");
		driver.findElement(By.id("discontinued")).sendKeys("2008-12-20");
		Select companyDropDown = new Select(driver.findElement(By.id("company")));
		companyDropDown.selectByIndex(1);
	}
	
	@When("It should be allowed to save")
	public void allowedToSave() {
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='alert-message warning']")).isDisplayed());
	}

	@When("I am already entering a new computer into the system")
	public void enterNameForNewComputer() {
		this.openBrowser();
		this.openDatabaseUrl();
		this.addNewComputerButton().click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section/h1")));
		driver.findElement(By.id("name")).sendKeys("testName");
	}

	@When("I need to stop what I am doing and go back to the main view")
	public void stopEntering() {
		driver.findElement(By.id("introduced")).isDisplayed();
	}

	@When("I click cancel")
	public void clickCancel() {
		driver.findElement(By.xpath("//div[@class='actions']//a")).click();
	}

	@When("It should take me back to the main computer database view")
	public void shouldBeInMainPage() {
		Assert.assertTrue(driver.findElement(By.id("searchbox")).isDisplayed());
	}

	// Cucumber hooks
	@After
	public void afterScenario(Scenario scenario) {
		driver.quit();
	}

}