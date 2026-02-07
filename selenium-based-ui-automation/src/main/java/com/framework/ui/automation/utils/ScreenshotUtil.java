package com.framework.ui.automation.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenshotUtil {

	private ScreenshotUtil() {
		/* This utility class should not be instantiated */
	}

	public static String captureScreenshot(String testName) {

		TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
		File source = ts.getScreenshotAs(OutputType.FILE);

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String path = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + timestamp + ".png";

		File destination = new File(path);

		try {
			FileUtils.copyFile(source, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

}
