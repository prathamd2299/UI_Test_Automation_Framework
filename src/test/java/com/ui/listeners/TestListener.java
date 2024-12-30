package com.ui.listeners;

import static com.ui.constants.Constants.*;

import java.awt.Desktop;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.testng.*;
import org.testng.annotations.*;
import org.apache.commons.io.*;
import org.apache.commons.mail.*;
import org.apache.commons.mail.resolver.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.*;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.ui.base.*;
import com.ui.utilities.*;

public class TestListener implements ITestListener, IAnnotationTransformer {
	public ExtentSparkReporter extentSparkReporter;
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;

	String reportName;

	@Override
	public void onStart(ITestContext context) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		reportName = "Test-Report-" + timeStamp + ".html";
		extentSparkReporter = new ExtentSparkReporter(".\\Reports\\" + reportName); // location of report

		extentSparkReporter.config().setDocumentTitle(EXTENT_REPORT_DOCUMENT_TITLE);
		extentSparkReporter.config().setReportName(EXTENT_REPORT_NAME);
		extentSparkReporter.config().setTheme(Theme.STANDARD);

		extentReports = new ExtentReports();
		extentReports.attachReporter(extentSparkReporter);

		extentReports.setSystemInfo("Tester", EXTENT_REPORT_TESTER);
		extentReports.setSystemInfo("Environment", EXTENT_REPORT_ENVIRONMENT);

		String operatingSystem = context.getCurrentXmlTest().getParameter("os");
		extentReports.setSystemInfo("Operating System", operatingSystem);

		String browser = context.getCurrentXmlTest().getParameter("browser");
		extentReports.setSystemInfo("Browser", browser);

		List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extentReports.setSystemInfo("Groups", includedGroups.toString());
		}

		ExtentSparkReporter sparkFail = new ExtentSparkReporter(".\\target\\SparkFail.html").filter().statusFilter()
				.as(new Status[] { Status.FAIL }).apply();
		// will contain all tests
		ExtentSparkReporter sparkPass = new ExtentSparkReporter(".\\target\\SparkPass.html").filter().statusFilter()
				.as(new Status[] { Status.PASS }).apply();
		extentReports.attachReporter(sparkFail, sparkPass);

		LoggerUtility.info("Test Suite Started: " + context.getName());
	}

	@Override
	public void onTestStart(ITestResult result) {
		LoggerUtility.info("Test Started: " + result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest = extentReports.createTest(
				"Test Name: " + result.getTestClass().getName() + " - " + result.getMethod().getMethodName());
		extentTest.assignCategory(result.getMethod().getGroups()); // To display groups in report
		extentTest.log(Status.PASS,
				MarkupHelper.createLabel(result.getName() + " - Test Case Passed", ExtentColor.GREEN));

		LoggerUtility.info("Test passed: " + result.getName() + "\n");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentTest = extentReports.createTest(
				"Test Name: " + result.getTestClass().getName() + " - " + result.getMethod().getMethodName());
		extentTest.assignCategory(result.getMethod().getGroups()); // To display groups in report
		extentTest.log(Status.FAIL,
				MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
//		extentTest.log(Status.INFO,
//				MarkupHelper.createLabel(result.getThrowable().getMessage() + " - Test Case Failed", ExtentColor.RED));
		extentTest.log(Status.INFO, result.getThrowable().getMessage());

		String fileName = System.getProperty("user.dir") + File.separator + "Screenshots" + File.separator
				+ result.getMethod().getMethodName() + ".png";

		try {
			File file = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File(fileName));

			extentTest.addScreenCaptureFromPath(fileName);
		} catch (Exception e) {
			LoggerUtility.error("Failed to take screenshot");
		}

		LoggerUtility.info("Test failed: " + result.getName() + "\n");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest = extentReports.createTest(
				"Test Name: " + result.getTestClass().getName() + " - " + result.getMethod().getMethodName());
		extentTest.assignCategory(result.getMethod().getGroups()); // To display groups in report
		extentTest.log(Status.SKIP,
				MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
		extentTest.log(Status.INFO, result.getThrowable().getMessage());

		LoggerUtility.info("Test skipped: " + result.getName() + "\n");
	}

	@Override
	public void onFinish(ITestContext context) {
		extentReports.flush();

		String pathOfExtentReport = System.getProperty("user.dir") + File.separator + "Reports" + File.separator
				+ reportName;
		File extentReport = new File(pathOfExtentReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
			LoggerUtility.error("Failed to open report file");
		}

		LoggerUtility.info("Test Suite Finished: " + context.getName());

//		try {
////			URL url = new URL("file:///" + System.getProperty("user.dir") + "\\Reports\\" + reportName);
//
//			Path reportPath = Paths.get(System.getProperty("user.dir"), "Reports", reportName);
//			URL url = reportPath.toUri().toURL();
//
//			// Create the email message
//			ImageHtmlEmail email = new ImageHtmlEmail();
//			email.setDataSourceResolver(new DataSourceUrlResolver(url));
//			email.setHostName("smtp.googlemail.com");
//			email.setSmtpPort(465);
//			email.setAuthenticator(new DefaultAuthenticator("prathameshdhasade99@gmail.com", "Pratham@2299"));
//			email.setSSLOnConnect(true);
//			email.setFrom("prathameshdhasade99@gmail.com"); // Sender
//			email.setSubject("Test execution report");
//			email.setMsg("Please find attached report...");
//			email.addTo("prathameshdhasade2015@gmail.com"); // Receiver
//			email.attach(url, "extent report", "please check report...");
//			email.send();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}
}
