package com.openmrs.stepDefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.openmrs.driverFactory.BrowserDriverManager;
import com.openmrs.utils.CommonUtils;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class CommonSteps {

	private static final Logger logger = LogManager.getLogger(RegisterPatientSteps.class);

	@Before
	public void initialization() {
		logger.info("GETTING BROWSER NAME");
		String property = CommonUtils.getInstance().getProperty("browserName");
		logger.info("LAUNCHING THE BROWSER");
		BrowserDriverManager.launchBrowser(property);
		logger.info("PAGE INITIALIZATION");
		CommonUtils.getInstance().pageInitialization();
	}

	@After
	public void tearDown() {
		BrowserDriverManager.quitBrowser();
	}

	@AfterStep
	public void tearDown1(Scenario scenario) {

		if (scenario.isFailed()) {
			logger.info("TAKING SCREENSHOT FOR FAILED SCENARIOS");
			try {
				TakesScreenshot ts = (TakesScreenshot) BrowserDriverManager.getDriver();
				byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
				logger.info("ATTCHING SCREENSHOT FOR FAILED SCENARIOS");

				scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
			} catch (WebDriverException e) {
				e.printStackTrace();
			}
		}
	}
	/*
	 * @AfterStep public void captureScreenshot(Scenario scenario) { if
	 * (scenario.isFailed()) { try { if (BrowserDriverManager.getDriver() != null) {
	 * // Take Screenshot TakesScreenshot ts = (TakesScreenshot)
	 * BrowserDriverManager.getDriver(); byte[] screenshot =
	 * ts.getScreenshotAs(OutputType.BYTES);
	 * 
	 * // Attach Screenshot to Cucumber Report scenario.attach(screenshot,
	 * "image/png", "Failed Step Screenshot");
	 * 
	 * // Ensure Screenshot Directory Exists File screenshotDir = new
	 * File("test-output/SparkReport/screenshots/"); if (!screenshotDir.exists()) {
	 * screenshotDir.mkdirs(); // Create folder if not exists }
	 * 
	 * // Save Screenshot Locally for Extent Report File srcFile =
	 * ts.getScreenshotAs(OutputType.FILE); String screenshotName =
	 * scenario.getName().replaceAll(" ", "_") + ".png"; File destFile = new
	 * File(screenshotDir, screenshotName); FileUtils.copyFile(srcFile, destFile);
	 * 
	 * // Log Screenshot Path (For Debugging)
	 * System.out.println("Screenshot saved at: " + destFile.getAbsolutePath()); }
	 * else { System.out.println("WebDriver is NULL, cannot capture screenshot!"); }
	 * } catch (IOException e) { e.printStackTrace(); } } }
	 */

}
