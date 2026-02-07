package com.framework.ui.automation.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.framework.ui.automation.config.ConfigReader;
import com.framework.ui.automation.utils.ExtentTestManager;
import com.framework.ui.automation.utils.ScreenshotUtil;

public class TestListener implements ITestListener {
	@Override
	public void onTestSuccess(ITestResult result) {
		String needScreenshot = ConfigReader.getProperty("screenshotOnPass");
		boolean isScreenshotRequiredForPassTests = Boolean.parseBoolean(needScreenshot);
		if (isScreenshotRequiredForPassTests) {
			String testName = result.getMethod().getMethodName();
			String path = ScreenshotUtil.captureScreenshot(testName + "_PASSED");
			ExtentTestManager.getTest().addScreenCaptureFromPath(path);
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String needScreenshot = ConfigReader.getProperty("screenshotOnFail");
		boolean isScreenshotRequiredForFailTests = Boolean.parseBoolean(needScreenshot);
		if (isScreenshotRequiredForFailTests) {
			String testName = result.getMethod().getMethodName();
			String path = ScreenshotUtil.captureScreenshot(testName + "_FAILED");
			ExtentTestManager.getTest().addScreenCaptureFromPath(path);
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String needScreenshot = ConfigReader.getProperty("screenshotOnSkip");
		boolean isScreenshotRequiredForSkipTests = Boolean.parseBoolean(needScreenshot);
		if (isScreenshotRequiredForSkipTests) {
			String testName = result.getMethod().getMethodName();
			String path = ScreenshotUtil.captureScreenshot(testName + "_SKIPPED");
			ExtentTestManager.getTest().addScreenCaptureFromPath(path);
		}
	}
}
