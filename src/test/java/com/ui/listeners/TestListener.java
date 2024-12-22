package com.ui.listeners;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import org.apache.commons.io.FileUtils;
import com.ui.base.BaseTest;
import com.ui.utilities.LoggerUtility;

public class TestListener implements ITestListener, IAnnotationTransformer {
	@Override
	public void onTestStart(ITestResult result) {
		LoggerUtility.info("Test Started: " + result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		LoggerUtility.info("Test passed: " + result.getName() + "\n");
	}

	@Override
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

	@Override
	public void onTestSkipped(ITestResult result) {
		LoggerUtility.info("Test skipped: " + result.getName() + "\n");
	}

	@Override
	public void onStart(ITestContext context) {
		LoggerUtility.info("Test Suite Started: " + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		LoggerUtility.info("Test Suite Finished: " + context.getName());
	}

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}
}
