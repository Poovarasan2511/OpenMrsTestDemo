package com.openmrs.stepDefinitions;

import java.awt.AWTException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.openmrs.driverFactory.BrowserDriverManager;
import com.openmrs.pageObjects.HomePage;
import com.openmrs.pageObjects.LoginPage;
import com.openmrs.pageObjects.PatientDetailsPage;
import com.openmrs.pageObjects.RegisterPatientPage;
import com.openmrs.utils.CommonUtils;
import com.openmrs.utils.OpenMRSConstants;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class RegisterPatientSteps {

	// driver
	private WebDriver driver;

	// Objects
	private CommonUtils objCommonUtils;
	private LoginPage objLoginPage;
	private HomePage objHomePage;
	private RegisterPatientPage objRegisterPatientPage;
	private PatientDetailsPage objPatientDetailsPage;

	// variables
	private String givenName;
	private String familyName;
	private String gender;
	private String birthDay;
	private String birthMonth;
	private String birthYear;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String phoneNumber;
	private String patientID;

	// Assert
	SoftAssert softAssert;
	// Logger
	private static final Logger logger = LogManager.getLogger(RegisterPatientSteps.class);

	public RegisterPatientSteps() {
		logger.info("Log Starterd");
		driver = BrowserDriverManager.getDriver();
		objCommonUtils = CommonUtils.getInstance();
		objLoginPage = LoginPage.getInstance();
		objHomePage = HomePage.getInstance();
		objRegisterPatientPage = RegisterPatientPage.getInstance();
		objPatientDetailsPage = PatientDetailsPage.getInstance();
		softAssert = new SoftAssert();
	}

	@Given("User open the browser and hit the site url")
	public void user_open_the_browser_and_hit_the_site_url() {
		String siteUrl = objCommonUtils.getProperty("siteUrl");
		logger.info("Enter the site url");
		driver.get(siteUrl);
	}

	@Given("User enters the Admin username and password, selects the location, and clicks on the login button")
	public void user_enters_the_admin_username_and_password_selects_the_location_and_clicks_on_the_login_button()
			throws IOException {
		String UserName = objCommonUtils.getJsonValue(OpenMRSConstants.loginDetails, "AdminLogin", "userName");
		String password = objCommonUtils.getJsonValue(OpenMRSConstants.loginDetails, "AdminLogin", "password");
		String location = objCommonUtils.getJsonValue(OpenMRSConstants.loginDetails, "AdminLogin", "location");
		logger.info("Login As Admin");
		objLoginPage.login(UserName, password, location);
	}

	@Then("User verify the dashboard page is redirected")
	public void user_verify_the_dashboard_page_is_redirected() throws InterruptedException {
		logger.info("Login Successfull");
		objCommonUtils.waitUntilExpectedTitle(OpenMRSConstants.dashBoardPageTitle);
		String dashboardPageTitle = driver.getTitle();
		Assert.assertEquals(dashboardPageTitle, OpenMRSConstants.dashBoardPageTitle);
	}

	@Then("User register a patient using detail of Demographics")
	public void user_register_a_patient_using_detail_of_demographics() {
		logger.info("Clicking On Register A Patient");

		objHomePage.btnRegisterPatient.click();

		logger.info("Retrieves Values From Json File FOr Patient Registration");
		// retrieve patient details from Patient Details json file
		givenName = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "GivenName");
		familyName = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "FamilyName");
		gender = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "Gender");
		birthDay = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "BirthDay");
		birthMonth = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "BirthMonth");
		birthYear = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "BirthYear");
		address1 = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "Address1");
		address2 = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "Address2");
		city = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "City");
		state = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "State");
		country = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "Country");
		postalCode = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "PostalCode");
		phoneNumber = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "PhoneNumber");

		// Register Patient using data from json file
		logger.info("Register The Patient");

		objRegisterPatientPage.registerPatient(givenName, familyName, gender, birthDay, birthMonth, birthYear, address1,
				address2, city, state, country, postalCode, phoneNumber);
	}

	@Then("User verify the demographic details at confirm page")
	public void user_verify_the_demographic_details_at_confirm_page() {

		// patient BirthDate & Address concatinate from json
		String ExpectedPatientBirthDate = birthDay + ", " + birthMonth + ", " + birthYear;
		String ExpectedPatientAddress = address1 + ", " + address2 + ", " + city + ", " + state + ", " + country + ", "
				+ postalCode;

		// retrieve patient details from patient confirm page

		String actualPatientFirstName = objRegisterPatientPage.getPatientFirstName();
		String actualPatientFamilyName = objRegisterPatientPage.getPatientFamilyName();
		String actualPatientGender = objRegisterPatientPage.getPatientGender();
		String actualPatientBirthdate = objRegisterPatientPage.getPatientBirthdate();
		String actualPatientAddress = objRegisterPatientPage.getPatientAddress();
		String actualPatientPhoneNumber = objRegisterPatientPage.getPatientPhoneNumber();

		logger.info("Verifying the patient details using assertion");

		// Assertion for patient details
		Assert.assertEquals(givenName, actualPatientFirstName);
		Assert.assertEquals(familyName, actualPatientFamilyName);
		Assert.assertEquals(gender, actualPatientGender);
		Assert.assertEquals(actualPatientBirthdate, ExpectedPatientBirthDate);
		Assert.assertEquals(actualPatientAddress, ExpectedPatientAddress);
		Assert.assertEquals(actualPatientPhoneNumber, phoneNumber);

	}

	@Then("User clicks on confirm and verify the patient details page is redirected")
	public void user_clicks_on_confirm_and_verify_the_patient_details_page_is_redirected() {
		logger.info("Clicks On Submit Button After Verifying The Details");
		// User Click the submit button after verifying the details
		objRegisterPatientPage.clickSubmitButton();
		String ActualpatientDetailsPageTitle = driver.getTitle();
		Assert.assertEquals(OpenMRSConstants.PatientDetailsPageTitle, ActualpatientDetailsPageTitle);
	}

	@Then("User clicks on start visit and clicks on confirm visit")
	public void user_clicks_on_start_visit_and_clicks_on_confirm_visit()
			throws IOException, InterruptedException, AWTException {
		logger.info("Clicks On Submit Button After Verifying The Details");
		patientID = objPatientDetailsPage.getPatientID();
		logger.info("Clicks On Start Visit");
		objPatientDetailsPage.clickStartVisit();
		objPatientDetailsPage.clickPopupConfirm();

	}

	@Then("User uploads the file and verify the toaster message")
	public void user_uploads_the_file_and_verify_the_toaster_message() throws InterruptedException {
		logger.info("Clicks On Attachments");
		objPatientDetailsPage.clickAttachments();
		String filePath = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1", "FilePath");
		String caption = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1",
				"CaptionForFileUpload");
		logger.info("Uploading The Image");

		objPatientDetailsPage.fileUpload(filePath, caption);
		logger.info("Verify The Upload Image Toaster Message");

		objPatientDetailsPage.isToasterMessageIsDisplayed(OpenMRSConstants.toasterMessageContentForAttachmentUpload);
	}

	@Then("User redirecting to Patient details screen and verify the attachment")
	public void user_redirecting_to_patient_details_screen_and_verify_the_attachment() {
		logger.info("Verify The Uploaded Image Is uploaded successfully");

		Boolean imageDisplay = objPatientDetailsPage.isImageDisplay();
		if (imageDisplay == true) {
			softAssert.assertTrue(imageDisplay, "Image Is Uploaded Successfully!");
		} else {
			softAssert.assertTrue(imageDisplay, "Uploaded image is not displaying!");
		}
		objPatientDetailsPage.goToPatientDetailsPage();
	}

	@Then("User verify the Recent Visit has one entry for the current date with the Attachment Upload")
	public void user_verify_the_recent_visit_has_one_entry_for_the_current_date_with_the_attachment_upload() {
		String actualrecentVisitsEntry = objPatientDetailsPage.getrecentVisitsEntry();

		// date formatter
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
		String formattedDate = currentDate.format(formatter);

		String expectedText = "" + formattedDate + " Attachment Upload";
		
		logger.info("Verify The Recent Entry as current date and Attachment Upload text");

		Assert.assertEquals(actualrecentVisitsEntry, expectedText);
	}

	@Then("User clicks on end visit and delete the patient by providing the reason")
	public void user_clicks_on_end_visit_and_delete_the_patient_by_providing_the_reason() {
		logger.info("Clicks on End Visit Link");

		objPatientDetailsPage.linkEndVisit();
		objPatientDetailsPage.clickPopupEndVisitConfirm();

		String reasonForPatientDelete = objCommonUtils.getJsonValue(OpenMRSConstants.patientDetails, "PatientDetails_1",
				"ReasonForPatientDelete");
		logger.info("Clicks on Delete patient Link");

		objPatientDetailsPage.clickAndDeletePatient(reasonForPatientDelete);
	}

	@Then("User clicks on confirm button and verify the toaster message")
	public void user_clicks_on_confirm_button_and_verify_the_toaster_message() {
		objPatientDetailsPage.clickPopupConfirmButton();
		objPatientDetailsPage.isToasterMessageIsDisplayed(OpenMRSConstants.toasterMessageContentForPatientDelete);
	}

	@Then("User verify the deleted patient is not showing by using the search box")
	public void user_verify_the_deleted_patient_is_not_showing_by_using_the_search_box() throws InterruptedException {
		logger.info("Searching for patient using patientID" + patientID);

		objPatientDetailsPage.searchPatientUsinID(patientID);
		Thread.sleep(2000);
		String noMatchingPatientMessage = objPatientDetailsPage.getNoMatchingPatientMessage();
		Assert.assertEquals(noMatchingPatientMessage, "No matching records found");

	}
}
