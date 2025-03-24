package com.openmrs.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.openmrs.driverFactory.BrowserDriverManager;

public class LoginPage {

	private static LoginPage loginPage;
	private WebDriver driver;

	private LoginPage() {
		this.driver = BrowserDriverManager.getDriver();

	}

	public static LoginPage getInstance() {
		if (loginPage == null) {
			loginPage = new LoginPage();
		}
		return loginPage;
	}

	@FindBy(id = "username")
	private WebElement txtUserName;

	@FindBy(id = "password")
	private WebElement txtPassword;

	@FindBy(id = "loginButton")
	private WebElement btnLogin;

	@FindBy(id = "sessionLocation")
	public WebElement listLocation;

	private void clickLocation(String location) {
		driver.findElement(By.xpath("//li[contains(normalize-space(text()), '" + location + "')]")).click();
	}

	public void login(String userName, String password, String location) {
		txtUserName.sendKeys(userName);
		txtPassword.sendKeys(password);
		clickLocation(location);
		btnLogin.click();
	}

}
