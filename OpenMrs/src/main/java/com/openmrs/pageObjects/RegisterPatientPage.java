package com.openmrs.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.openmrs.driverFactory.BrowserDriverManager;

public class RegisterPatientPage {

	private WebDriver driver;
	private static RegisterPatientPage registerPatientPage;
	private String text;

	private RegisterPatientPage() {
		this.driver = BrowserDriverManager.getDriver();

	}

	public static RegisterPatientPage getInstance() {
		if (registerPatientPage == null) {
			registerPatientPage = new RegisterPatientPage();
		}
		return registerPatientPage;
	}

	@FindBy(id = "next-button")
	private WebElement btnNext;

	@FindBy(xpath = "//input[@name='givenName']") // fr1608-field
	private WebElement txtGiven;

	@FindBy(xpath = "//input[@name='familyName']")
	private WebElement txtFamilName;

	@FindBy(id = "gender-field")
	private WebElement listGender;

	@FindBy(id = "birthdateDay-field")
	private WebElement txtBirthDay;

	@FindBy(id = "birthdateMonth-field")
	private WebElement listBirthMonth;

	@FindBy(id = "birthdateYear-field")
	private WebElement txtBirthYear;

	@FindBy(id = "address1")
	private WebElement txtAddress1;

	@FindBy(id = "address2")
	private WebElement txtAddress2;

	@FindBy(id = "cityVillage")
	private WebElement txtCityVillage;

	@FindBy(id = "stateProvince")
	private WebElement txtStateProvince;

	@FindBy(id = "country")
	private WebElement txtCountry;

	@FindBy(id = "postalCode")
	private WebElement txtPostalCode;

	@FindBy(id = "submit")
	private WebElement btnSubmit;

	@FindBy(xpath = "//input[@name='phoneNumber']")
	private WebElement txtPatientPhoneNumber;

	@FindBy(xpath = "//div[@id='dataCanvas']//p[1]")
	private WebElement labelPatientName;

	@FindBy(xpath = "//div[@id='dataCanvas']//p[2]")
	private WebElement labelPatientGender;

	@FindBy(xpath = "//div[@id='dataCanvas']//p[3]")
	private WebElement labelPatientBirthDate;

	@FindBy(xpath = "//div[@id='dataCanvas']//p[4]")
	private WebElement labelPatientAddress;

	@FindBy(xpath = "//div[@id='dataCanvas']//p[5]")
	private WebElement labelPatientPhoneNumber;

	private void selectGender(String gender) {
		driver.findElement(By.xpath("//option[contains(text(),'" + gender + "')]")).click();
	}

	private void clickNextButton() {
		btnNext.click();

	}

	public void getPatientName() {
		text = labelPatientName.getText().trim().replace("Name: ", "");
	}

	public String getPatientFirstName() {
		getPatientName();
		return text.split(",")[0];

	}

	public String getPatientFamilyName() {
		return text.split(",")[1].trim();

	}

	public String getPatientGender() {
		return labelPatientGender.getText().split(": ")[1];

	}

	public String getPatientBirthdate() {
		return labelPatientBirthDate.getText().split(": ")[1];
	}

	public String getPatientAddress() {
		return labelPatientAddress.getText().split(": ")[1];
	}

	public String getPatientPhoneNumber() {
		return labelPatientPhoneNumber.getText().split(": ")[1];

	}

	public void clickSubmitButton() {
		btnSubmit.click();
	}
	public void registerPatient(String given, String FamilName, String gender, String birthDay, String birthMonth,
			String birthYear, String address1, String address2, String cityVillage, String stateProvince,
			String country, String postalCode, String phoneNumber) {
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
//		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='fr1608-field']")));

		txtGiven.sendKeys(given);
		txtFamilName.sendKeys(FamilName);
		clickNextButton();
		selectGender(gender);
		clickNextButton();
		txtBirthDay.sendKeys(birthDay);
		Select select = new Select(listBirthMonth);
		// select.selectByValue("11");
		select.selectByVisibleText(birthMonth);
		txtBirthYear.sendKeys(birthYear);
		clickNextButton();
		txtAddress1.sendKeys(address1);
		txtAddress2.sendKeys(address2);
		txtCityVillage.sendKeys(cityVillage);
		txtStateProvince.sendKeys(stateProvince);

		txtCountry.sendKeys(country);

		txtPostalCode.sendKeys(postalCode);
		clickNextButton();
		txtPatientPhoneNumber.sendKeys(phoneNumber);
		clickNextButton();
		clickNextButton();
		

	}

}
