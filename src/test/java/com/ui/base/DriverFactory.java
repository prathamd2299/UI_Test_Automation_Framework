package com.ui.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {
	// Factory design pattern
	public WebDriver getDriver(String browser) {
		switch (browser.toLowerCase()) {
		case "chrome":
			return new ChromeDriver();
		case "edge":
			return new EdgeDriver();

		default:
			throw new IllegalArgumentException("Unexpected value: " + browser);
		}
	}
}
