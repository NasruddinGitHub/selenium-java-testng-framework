package com.framework.ui.automation.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.framework.ui.automation.config.ConfigReader;

public class RetryAnalyzer implements IRetryAnalyzer {

	int initialRetry = 1;
	int maxRetry = Integer.parseInt(ConfigReader.getProperty("retryFailedTestAttempts"));

	@Override
	public boolean retry(ITestResult result) {
		if (initialRetry <= maxRetry) {
			initialRetry++;
			return true;
		}
		return false;
	}
}
