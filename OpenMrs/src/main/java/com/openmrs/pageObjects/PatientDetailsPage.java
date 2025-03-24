package com.openmrs.pageObjects;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.openmrs.driverFactory.BrowserDriverManager;

public class PatientDetailsPage {

	public static PatientDetailsPage patientDetailsPage;

	private PatientDetailsPage() {

	}

	public static PatientDetailsPage getInstance() {
		if (patientDetailsPage == null) {
			patientDetailsPage = new PatientDetailsPage();

		}
		return patientDetailsPage;
	}

	@FindBy(xpath = "(//div[@class='col-11 col-lg-10'])[1]")
	private WebElement linkStartVisit;

	@FindBy(xpath = "(//button[@class='confirm right'])[6]")
	private WebElement popupBtnConfirm;

	@FindBy(xpath = "(//a[@class='button task activelist'])[5]")
	private WebElement btnAttachments;

	@FindBy(xpath = "//form[@id='visit-documents-dropzone']")
	private WebElement fileUpload;

	@FindBy(xpath = "//textarea[@placeholder='Enter a caption']")
	private WebElement txtCaption;

	@FindBy(xpath = "//button[@class='confirm ng-binding']")
	private WebElement btnUploadFile;

	@FindBy(xpath = "//div[@class='toast-container toast-position-top-right']")
	private WebElement toasterMessage;

	@FindBy(xpath = "(//div[@class='ng-scope'])[5]")
	private WebElement uploadedImage;

	@FindBy(xpath = "//a[contains(@href, 'clinicianfacing/patient.page')]")
	private WebElement linkPatientDetailsPage;

	@FindBy(xpath = "//li[@ng-if='$ctrl.visits.length != 0']")
	private WebElement recentVisitsEntry;

	@FindBy(xpath = "(//*[contains(text(),'End Visit')])[3]")
	private WebElement linkEndVisit;

	@FindBy(xpath = "(//button[@class='confirm right'])[5]")
	private WebElement popupEndVisitBtnConfirm;

	@FindBy(xpath = "(//div[@class='col-11 col-lg-10'])[8]")
	private WebElement linkDeletePatient;

	@FindBy(xpath = "//input[@id='delete-reason']")
	private WebElement txtDeletePatientReason;

	@FindBy(xpath = "(//button[@class='confirm right'])[6]")
	private WebElement popupDeletePatientBtnConfirm;

	@FindBy(xpath = "//div[@class='float-sm-right']")
	private WebElement labelPatientID;

	@FindBy(xpath = "//input[@id='patient-search']")
	private WebElement searchPatientRecords;

	@FindBy(xpath = "//td[@class='dataTables_empty']")
	private WebElement messageNoMatchingPatient;

	public void clickStartVisit() {
		linkStartVisit.click();
	}

	public void linkEndVisit() {
		linkEndVisit.click();
	}

	public String getNoMatchingPatientMessage() {
		return messageNoMatchingPatient.getText().trim();
	}

	public void clickAndDeletePatient(String reasonForDelete) {

		// linkDeletePatient.click();
//		WebDriverWait wait = new WebDriverWait(BrowserDriverManager.getDriver(), Duration.ofSeconds(20));
//		WebElement deleteLink = wait.until(ExpectedConditions.elementToBeClickable(linkDeletePatient));

		// WebElement deleteLink =
		// wait.until(ExpectedConditions.visibilityOf(linkDeletePatient));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		linkDeletePatient.click();

		txtDeletePatientReason.sendKeys(reasonForDelete);
	}

	public void clickPopupConfirmButton() {
		popupDeletePatientBtnConfirm.click();

	}

	public void clickPopupConfirm() {
		popupBtnConfirm.click();
	}

	public void clickPopupEndVisitConfirm() {
		popupEndVisitBtnConfirm.click();
	}

	public void clickAttachments() {
		btnAttachments.click();
	}

	public void fileUpload(String filePath, String caption) {
		fileUpload.click();
		uploadFile(filePath);
		txtCaption.sendKeys(caption);
		btnUploadFile.click();
	}

	public void isToasterMessageIsDisplayed(String toasterMessageText) {
		String toasterMessageContent = toasterMessage.getText();
		if (toasterMessageContent.contains(toasterMessageText)) {
			// System.out.println("Toaster message verified!");
		} else {
			System.err.println("Toaster message verification failed!");
		}

	}

	public Boolean isImageDisplay() {
		return uploadedImage.isDisplayed();

	}

	public void goToPatientDetailsPage() {
		linkPatientDetailsPage.click();

	}

	public String getPatientID() {
		return labelPatientID.getText().trim().replace("Patient ID ", "");
	}

	public void searchPatientUsinID(String patientID) {
		searchPatientRecords.sendKeys(patientID);
	}

	public String getrecentVisitsEntry() {
		return recentVisitsEntry.getText().trim().replaceAll("\\s+", " ");
	}

	public void uploadFile(String filePath) {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {

			e.printStackTrace();
		}

		StringSelection selection = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

}
