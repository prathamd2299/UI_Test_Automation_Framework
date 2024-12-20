package com.ui.listeners;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.commons.io.FileUtils;
import com.ui.base.BaseTest;
import com.ui.utilities.LoggerUtility;

public class TestListener implements ITestListener {

	public void onStart(ITestResult result) {
		LoggerUtility.info("Test started: " + result.getName() + "\n");
	}

	public void onTestSuccess(ITestResult result) {
		LoggerUtility.info("Test passed: " + result.getName() + "\n");
	}

	public void onTestFailure(ITestResult result) {
		String fileName = System.getProperty("user.dir") + File.separator + "Screenshots" + File.separator
				+ result.getMethod().getMethodName();

		try {
			File file = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File(fileName + ".png"));
		} catch (Exception e) {
			LoggerUtility.error("Failed to take screenshot");
		}

		LoggerUtility.info("Test failed: " + result.getName() + "\n");
	}

	public void onTestSkipped(ITestResult result) {
		LoggerUtility.info("Test skipped: " + result.getName() + "\n");
	}

	public void onFinish(ITestResult result) {
		LoggerUtility.info("Test finished: " + result.getName() + "\n");
	}
}
