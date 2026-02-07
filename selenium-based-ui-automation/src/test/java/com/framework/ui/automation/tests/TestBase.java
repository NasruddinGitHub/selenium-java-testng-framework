package com.framework.ui.automation.tests;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.framework.ui.automation.config.ConfigReader;
import com.framework.ui.automation.utils.DriverManager;
import com.framework.ui.automation.utils.ExcelUtil;
import com.framework.ui.automation.utils.ExtentManager;
import com.framework.ui.automation.utils.ExtentTestManager;

public class TestBase {

	protected WebDriver driver;
	protected static ExtentReports extent;

	@BeforeSuite(alwaysRun = true)
	public void setupReport() {
		extent = ExtentManager.getInstance();
	}

	@BeforeMethod(alwaysRun = true)
	public void startTest(Method method) {
		ExtentTest test = extent.createTest(method.getName());
		ExtentTestManager.setTest(test);
	}

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		String browser = ConfigReader.getProperty("browser");
		invokeBrowser(browser);
		getTdDriver().get(ConfigReader.getProperty("url"));
	}

	@AfterMethod(alwaysRun = true)
	public void quitBrowser() {
		getTdDriver().quit();
	}

	@AfterMethod(alwaysRun = true)
	public void endTest(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			ExtentTestManager.getTest().fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentTestManager.getTest().pass("Test Passed");
		} else {
			ExtentTestManager.getTest().skip("Test Skipped");
		}

		ExtentTestManager.unload();
	}

	public WebDriver getTdDriver() {
		return DriverManager.getDriver();
	}

	private void invokeBrowser(String browser) {
		// 🔹 Disable password manager & breach popup
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.password_manager_leak_detection", false);
		switch (browser.toLowerCase()) {
		case "chrome":
			ChromeOptions co = new ChromeOptions();
			co.setExperimentalOption("prefs", prefs);
			// Optional but useful in automation
			co.addArguments("--disable-notifications");
			co.addArguments("--start-maximized");
			driver = new ChromeDriver(co);
			DriverManager.setDriver(driver);
			break;
		case "edge":
			EdgeOptions eo = new EdgeOptions();
			eo.setExperimentalOption("prefs", prefs);
			// Optional but useful in automation
			eo.addArguments("--disable-notifications");
			eo.addArguments("--start-maximized");
			driver = new EdgeDriver(eo);
			DriverManager.setDriver(driver);
			break;
		case "firefox":
			FirefoxProfile profile = new FirefoxProfile();
			// 🔹 Disable password manager & breach alert popup
			profile.setPreference("signon.rememberSignons", false);
			profile.setPreference("signon.autofillForms", false);
			profile.setPreference("signon.management.page.breach-alerts.enabled", false);
			// 🔹 Disable notifications
			profile.setPreference("dom.webnotifications.enabled", false);
			profile.setPreference("dom.push.enabled", false);
			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(profile);
			// Optional but useful in automation
			options.addArguments("--start-maximized"); // may not always work, so maximize after launch
			driver = new FirefoxDriver(options);
			DriverManager.setDriver(driver);
			// Firefox maximize best practice
			// threadLocalDriver.get().manage().window().maximize();
			break;

		default:
			throw new IllegalArgumentException("Given " + browser + " is invalid browser. Please give valid browser.");

		}
	}

	@AfterSuite(alwaysRun = true)
	public void flushReport() {
		extent.flush();
	}
	
	@AfterSuite(alwaysRun = true)
	public void tearDown() {
	    ExcelUtil.closeExcel();
	}

}
