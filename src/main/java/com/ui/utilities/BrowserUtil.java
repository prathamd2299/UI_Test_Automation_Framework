package com.ui.utilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserUtil {
	private WebDriver driver;
	private WebDriverWait wait;

	public BrowserUtil(WebDriver driver1) {
		this.driver = driver1;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	public void goOnPreviousPage() {
		driver.navigate().back();
	}

	public void goOnNextPage() {
		driver.navigate().forward();
	}

	public void clickOn(By locator) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		element.click();
	}

	public void clickOn(WebElement element) {
		element.click();
	}

	public void enterText(By locator, String text) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		element.clear();
		element.sendKeys(text);
	}

	public void enterText(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}

	public String getText(By locator) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element.getText();
	}

	public String getText(WebElement element) {
		return element.getText();
	}
}
