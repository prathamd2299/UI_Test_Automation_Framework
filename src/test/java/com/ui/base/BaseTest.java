package com.ui.base;

import java.time.LocalDate;
import org.openqa.selenium.*;
import org.testng.annotations.*;

import com.ui.utilities.*;

public class BaseTest {
	public static WebDriver driver;

	@BeforeTest
	@Parameters("browser")
	public void initializeDriver(String browser) {
		String web_url = ConfigManager.getConfigProperties("web_url");
//		String browser = ConfigManager.getConfigProperties("browser");

		LoggerUtility
				.info("\n -------------------- Test execution started on " + LocalDate.now() + " --------------------");

		driver = DriverFactory.getInstance().getDriver(browser);
		LoggerUtility.info(browser + " launched successfully");

		driver.get(web_url);
		LoggerUtility.info(web_url + " opened successfully");

		driver.manage().window().maximize();
		LoggerUtility.info("Browser window maximized");

		// extentReportSetup();
	}

	@AfterTest
	public void tearDown() {
		DriverFactory.getInstance().quitDriver();
	}
}
