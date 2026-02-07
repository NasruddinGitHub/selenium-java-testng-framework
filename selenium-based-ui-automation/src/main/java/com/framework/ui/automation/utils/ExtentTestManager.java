package com.framework.ui.automation.utils;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
	
	private ExtentTestManager() {
		/* This utility class should not be instantiated */
	}

	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	public static void setTest(ExtentTest extentTest) {
		test.set(extentTest);
	}

	public static ExtentTest getTest() {
		return test.get();
	}

	public static void unload() {
		test.remove();
	}
}
