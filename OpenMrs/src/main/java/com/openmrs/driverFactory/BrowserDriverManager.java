package com.openmrs.driverFactory;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserDriverManager {
	private static final Logger logger = LogManager.getLogger(BrowserDriverManager.class);

	private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

	public static WebDriver getDriver() {
		return driverThreadLocal.get(); // Retrieves the WebDriver for the current thread
	}

	public static void launchBrowser(String browserName) {
		WebDriver driver = null;
		logger.info("Setting Browser " + browserName);
		switch (browserName.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			logger.info("Launching Chrome Browser");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			logger.info("Launching Firefox Browser");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			logger.info("Launching Edge Browser");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			break;

		default:
			throw new IllegalArgumentException("Invalid browser name: " + browserName);
		}
		logger.info("Setting driver to the ThreadLocal Driver");
		driverThreadLocal.set(driver);
	}

	public static void quitBrowser() {
		// WebDriver driver = driverThreadLocal.get();
		if (getDriver() != null) {
			logger.info("Closing The Browser");
			getDriver().quit();
			logger.info("Removing ThreadLocal Driver");
			driverThreadLocal.remove();
			logger.info("Test Ended");

		}
	}
}
