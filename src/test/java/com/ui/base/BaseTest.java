package com.ui.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ui.utilities.ConfigManager;
import com.ui.utilities.LoggerUtility;

public class BaseTest {
	public static WebDriver driver;

	@BeforeSuite
	public void initializeDriver() {
		DriverFactory driverFactory = new DriverFactory();

		String web_url = ConfigManager.getConfigProperties("web_url");
		String browser = ConfigManager.getConfigProperties("browser");

		driver = driverFactory.getDriver(browser);
		LoggerUtility.info(browser + " launched successfully");

		driver.get(web_url);
		LoggerUtility.info(web_url + " opened successfully");

		driver.manage().window().maximize();
		LoggerUtility.info("Browser window maximized");
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
