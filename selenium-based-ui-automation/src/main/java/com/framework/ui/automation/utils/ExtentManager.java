package com.framework.ui.automation.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.framework.ui.automation.config.ConfigReader;

public class ExtentManager {

	private ExtentManager() {
		/* This utility class should not be instantiated */
	}

	private static ExtentReports extent;

	public static ExtentReports getInstance() {
		if (extent == null) {
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			ExtentSparkReporter spark = new ExtentSparkReporter("reports/AutomationReport" + timestamp + ".html");
			spark.config().setReportName(ConfigReader.getProperty("extentReportName"));
			spark.config().setDocumentTitle(ConfigReader.getProperty("extentDocumentTitle"));
			extent = new ExtentReports();
			extent.attachReporter(spark);
		}
		return extent;
	}
}
