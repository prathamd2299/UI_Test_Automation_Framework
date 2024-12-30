package com.ui.base;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.*;
import org.openqa.selenium.firefox.*;

public class DriverFactory {
	// Factory design pattern
	private static DriverFactory instance = null; // Singleton instance
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>(); // Thread-safe WebDriver

	// Private constructor to prevent instantiation
	private DriverFactory() {
	}

	// Public method to get the single instance of DriverFactory
	public static DriverFactory getInstance() {
		if (instance == null) {
			synchronized (DriverFactory.class) {
				if (instance == null) {
					instance = new DriverFactory();
				}
			}
		}
		return instance;
	}

	// Method to initialize WebDriver
	public WebDriver getDriver(String browser) {
		if (driver.get() == null) {
			switch (browser.toLowerCase()) {
			case "chrome":
				driver.set(new ChromeDriver());
				break;
			case "firefox":
				driver.set(new FirefoxDriver());
				break;
			case "edge":
				driver.set(new EdgeDriver());
				break;
			default:
				throw new IllegalArgumentException("Unsupported browser: " + browser);
			}
		}
		return driver.get();
	}

	// Method to quit the driver
	public void quitDriver() {
		if (driver.get() != null) {
			driver.get().manage().deleteAllCookies();
			driver.get().quit();
			driver.remove(); // Remove reference for thread safety
		}
	}
}
