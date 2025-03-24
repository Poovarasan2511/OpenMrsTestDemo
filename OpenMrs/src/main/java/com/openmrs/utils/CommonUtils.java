package com.openmrs.utils;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.openmrs.driverFactory.BrowserDriverManager;
import com.openmrs.pageObjects.HomePage;
import com.openmrs.pageObjects.LoginPage;
import com.openmrs.pageObjects.PatientDetailsPage;
import com.openmrs.pageObjects.RegisterPatientPage;

public class CommonUtils {

	private WebDriver driver;
	private WebDriverWait wait;
	private static CommonUtils commonUtils;
	private static final Logger logger = LogManager.getLogger(CommonUtils.class);

	private CommonUtils() {
	}

	public static CommonUtils getInstance() {
		if (commonUtils == null) {
			commonUtils = new CommonUtils();
		}
		return commonUtils;
	}

	public void pageInitialization() {
		logger.info("Test Started");
		this.driver = BrowserDriverManager.getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		logger.info("Initializaion Of Page Objects");

		PageFactory.initElements(driver, LoginPage.getInstance());
		PageFactory.initElements(driver, HomePage.getInstance());
		PageFactory.initElements(driver, RegisterPatientPage.getInstance());
		PageFactory.initElements(driver, PatientDetailsPage.getInstance());
	}

	public String getProperty(String key) {
//		String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\ConfigFiles\\config.properties";
		FileReader file = null;
		Properties prop = null;
		try {

			file = new FileReader(OpenMRSConstants.propertyFilePath);
			prop = new Properties();

			prop.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return prop.getProperty(key);
	}

	public String getJsonValue(String jsonFilePath, String jsonArrayvalue, String key) {
		String string = null;
		try {
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(jsonFilePath);
			Object object = parser.parse(reader);
			JSONObject jsonObject = (JSONObject) object;
			JSONObject jsonArray = (JSONObject) jsonObject.get(jsonArrayvalue);
			string = (String) jsonArray.get(key);
		} catch (IOException | ParseException e) {

		}
		return string;
	}

	public void getJsonValue1() {
		JSONParser parser = new JSONParser();

		try (FileReader reader = new FileReader(OpenMRSConstants.loginDetails)) {

			Object obj = parser.parse(reader);
			JSONObject jsonObject = (JSONObject) obj;

			// Extract "AdminLogin" object
			JSONObject adminLogin = (JSONObject) jsonObject.get("AdminLogin");

			// Extract values inside "AdminLogin"
			String username = (String) adminLogin.get("UserName");
			String password = (String) adminLogin.get("password");

			// Print values
			System.out.println("Username: " + username);
			System.out.println("Password: " + password);

		} catch (IOException | org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
	}

//    public void waitUntilExpectedTitle(String expectedTitle) {
//        new WebDriverWait(driver, Duration.ofSeconds(20))
//            .until(ExpectedConditions.titleIs(expectedTitle));
//    }
	// WebDriverWait wait ;
	public void waitUntilExpectedTitle(String expectedTitle) {
		wait.until(ExpectedConditions.titleIs(expectedTitle));
	}

	public void selectByText(WebElement element, String gender) {
		Select select = new Select(element);
		select.selectByVisibleText(gender);

	}

}
