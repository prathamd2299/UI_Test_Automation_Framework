package com.ui.base;

import java.io.File;
import java.time.LocalDate;

import static com.ui.constants.Constants.*;
import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;

import com.aventstack.extentreports.markuputils.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.ui.pages.*;
import com.ui.utilities.*;

public class BaseTest {
	public static WebDriver driver;
	public ExtentSparkReporter sparkReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;

	public LoginPage login;
	public ProductsPage products;

	@BeforeSuite
	public void initializeDriver() {
		DriverFactory driverFactory = new DriverFactory();

		String web_url = ConfigManager.getConfigProperties("web_url");
		String browser = ConfigManager.getConfigProperties("browser");

		LoggerUtility.info(
				"\n -------------------- Test execution started on " + LocalDate.now() + " --------------------");

		driver = driverFactory.getDriver(browser);
		LoggerUtility.info(browser + " launched successfully");

		driver.get(web_url);
		LoggerUtility.info(web_url + " opened successfully");

		driver.manage().window().maximize();
		LoggerUtility.info("Browser window maximized");

		extentReportSetup();
	}

	@BeforeClass
	public void initializeDriverToPages() {
		login = new LoginPage(driver);
		products = new ProductsPage(driver);
	}

	public void extentReportSetup() {
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "Reports"
				+ File.separator + EXTENT_REPORT_HTML_FILE_NAME);
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		sparkReporter.config().setTheme(Theme.STANDARD);
		extent.setSystemInfo("OS", EXTENT_REPORT_OS);
		extent.setSystemInfo("Tester", EXTENT_REPORT_TESTER);
		sparkReporter.config().setDocumentTitle(EXTENT_REPORT_DOCUMENT_TITLE);
		sparkReporter.config().setReportName(EXTENT_REPORT_NAME);

		ExtentSparkReporter sparkFail = new ExtentSparkReporter("target/SparkFail.html").filter().statusFilter()
				.as(new Status[] { Status.FAIL }).apply();
		// will contain all tests
		ExtentSparkReporter sparkPass = new ExtentSparkReporter("target/SparkPass.html").filter().statusFilter()
				.as(new Status[] { Status.PASS }).apply();
		extent.attachReporter(sparkFail, sparkPass);
	}

	@BeforeMethod
	public void createExtentReportForEachTestMethod(ITestResult result) {
		logger = extent.createTest(
				"Test Name: " + result.getTestClass().getName() + " - " + result.getMethod().getMethodName());
	}

	@AfterMethod
	public void testMethodExecutionResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			logger.log(Status.FAIL,
					MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
		} else {
			logger.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " - Test Case Passed", ExtentColor.GREEN));
		}
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.manage().deleteAllCookies();
			driver.quit();
		}

		extent.flush();
	}
}
