package com.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ui.utilities.BrowserUtil;

public class LoginPage extends BrowserUtil {
	private By usernameTextfield = By.id("user-name");
	private By passwordTextfield = By.id("password");
	private By loginButton = By.id("login-button");
	private By errorText = By.xpath("//h3[@data-test='error']");

	public LoginPage(WebDriver driver1) {
		super(driver1);
	}

	public void enterUsername(String username) {
		enterText(usernameTextfield, username);
	}

	public void enterPassword(String password) {
		enterText(passwordTextfield, password);
	}

	public void clickOnLoginButton() {
		clickOn(loginButton);
	}

	public String getErrorText() {
		return getText(errorText);
	}
}
