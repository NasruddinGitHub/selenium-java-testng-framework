package com.framework.ui.automation.tests;

import java.io.IOException;

import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.framework.ui.automation.config.ConfigReader;
import com.framework.ui.automation.pages.BasePage;
import com.framework.ui.automation.pages.LoginPage;
import com.framework.ui.automation.pages.ProductsPage;
import com.framework.ui.automation.utils.ExcelUtil;
import com.framework.ui.automation.utils.ExtentTestManager;
import com.framework.ui.automation.utils.Log;

public class LoginTest extends TestBase {
	private static final Logger log = Log.getLogger(BasePage.class);

	@Test(groups = {
			"Smoke" }, description = "validate if username, password and login button fields are shown when visiting login page")
	public void checkIfUsernamePasswordLoginButtonAreShown() {
		LoginPage loginPage = new LoginPage(getTdDriver());
		boolean isUsernameFieldShown = loginPage.isUsernameFieldShown();
		boolean isPasswordFieldShown = loginPage.isPasswordFieldShown();
		boolean isLoginButtonFieldShown = loginPage.isLoginButtonFieldShown();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(isUsernameFieldShown);
		ExtentTestManager.getTest().info("Username field is shown.");
		log.info("Username field is shown");
		softAssert.assertTrue(isPasswordFieldShown);
		ExtentTestManager.getTest().info("Password field is shown.");
		log.info("Password field is shown");
		softAssert.assertTrue(isLoginButtonFieldShown);
		ExtentTestManager.getTest().info("Login button is shown.");
		log.info("Login button is shown");
		softAssert.assertAll();
		Assert.fail();
	}

	// Happy Path
	@Test(groups = { "Smoke",
			"Regression" }, description = "Validate if the user is able to login with valid username and valid password")
	public void loginWithValidUsernameAndValidPassword() {
		LoginPage loginPage = new LoginPage(getTdDriver());
		loginPage.typeUsername(ConfigReader.getProperty("standardUsername"));
		loginPage.typePassword(ConfigReader.getProperty("password"));
		loginPage.clickLoginButton();
		ProductsPage productsPage = new ProductsPage(getTdDriver());
		boolean isProductsTitleVisible = productsPage.isProductsTitleVisible();
		// Expected outcome - true
		Assert.assertTrue(isProductsTitleVisible);
	}

	@Test(groups = {
			"Regression" }, description = "Validate if the user is able to login with invalid username and valid password")
	public void loginWithInvalidUsernameAndValidPassword() {
		LoginPage loginPage = new LoginPage(getTdDriver());
		loginPage.typeUsername(ConfigReader.getProperty("invalidUsername"));
		loginPage.typePassword(ConfigReader.getProperty("password"));
		loginPage.clickLoginButton();
		boolean isUsernamePasswordMismatchErrorShown = loginPage.isUsernamePasswordMismatchErrorShown();
		String usernamePasswordMismatchActualtext = loginPage.getUsernamePasswordMismatchError();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(isUsernamePasswordMismatchErrorShown);
		String expectedUsernamePasswordMismatchText = ExcelUtil.getData("LoginTests", "TC_003", 1);
		softAssert.assertEquals(usernamePasswordMismatchActualtext, expectedUsernamePasswordMismatchText);
		softAssert.assertAll();
	}

	@Test(groups = {
			"Regression" }, description = "Validate if the user is able to login with valid username and invalid password")
	public void loginWithValidUsernameAndInvalidPassword() {
		LoginPage loginPage = new LoginPage(getTdDriver());
		loginPage.typeUsername(ConfigReader.getProperty("standardUsername"));
		loginPage.typePassword(ConfigReader.getProperty("invalidPassword"));
		loginPage.clickLoginButton();
		boolean isUsernamePasswordMismatchErrorShown = loginPage.isUsernamePasswordMismatchErrorShown();
		String usernamePasswordMismatchActualtext = loginPage.getUsernamePasswordMismatchError();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(isUsernamePasswordMismatchErrorShown);
		String expectedUsernamePasswordMismatchText = ExcelUtil.getData("LoginTests", "TC_004", 1);
		softAssert.assertEquals(usernamePasswordMismatchActualtext, expectedUsernamePasswordMismatchText);
		softAssert.assertAll();
		Assert.fail();
	}

	@Test(groups = {
			"Regression" }, description = "Validate if the user is able to login with invalid username and invalid password")
	public void loginWithInvalidUsernameAndInvalidPassword() {
		LoginPage loginPage = new LoginPage(getTdDriver());
		loginPage.typeUsername(ConfigReader.getProperty("invalidUsername"));
		loginPage.typePassword(ConfigReader.getProperty("invalidPassword"));
		loginPage.clickLoginButton();
		boolean isUsernamePasswordMismatchErrorShown = loginPage.isUsernamePasswordMismatchErrorShown();
		String usernamePasswordMismatchActualtext = loginPage.getUsernamePasswordMismatchError();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(isUsernamePasswordMismatchErrorShown);
		String expectedUsernamePasswordMismatchText = ExcelUtil.getData("LoginTests", "TC_005", 1);
		softAssert.assertEquals(usernamePasswordMismatchActualtext, expectedUsernamePasswordMismatchText);
		softAssert.assertAll();
	}

	@Test(groups = {
			"Regression" }, description = "Validate if the user is able to view error message when username is not given")
	public void verifyIfErrorMessageShownWhenUsernameIsNotGiven() {
		LoginPage loginPage = new LoginPage(getTdDriver());
		loginPage.typePassword(ConfigReader.getProperty("password"));
		loginPage.clickLoginButton();
		boolean isUsernameRequiredErrorShown = loginPage.isUsernameRequiredErrorShown();
		String usernameRequiredErrorActualtext = loginPage.getUsernameRequiredError();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(isUsernameRequiredErrorShown);
		String expectedUsernameRequiredErrorText = ExcelUtil.getData("LoginTests", "TC_006", 1);
		softAssert.assertEquals(usernameRequiredErrorActualtext, expectedUsernameRequiredErrorText);
		softAssert.assertAll();
	}

	@Test(groups = { "Regression",
			"E2E" }, description = "Validate if the user is able to view error message when password is not given")
	public void verifyIfErrorMessageShownWhenPasswordIsNotGiven() {
		LoginPage loginPage = new LoginPage(getTdDriver());
		loginPage.typeUsername(ConfigReader.getProperty("standardUsername"));
		loginPage.clickLoginButton();
		boolean isPasswordRequiredErrorShown = loginPage.isPasswordRequiredErrorShown();
		String passwordRequiredErrorActualtext = loginPage.getPasswordRequiredError();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(isPasswordRequiredErrorShown);
		String expectedPasswordRequiredErrorText = ExcelUtil.getData("LoginTests", "TC_007", 1);
		softAssert.assertEquals(passwordRequiredErrorActualtext, expectedPasswordRequiredErrorText);
		softAssert.assertAll();
	}

	@Test(groups = { "Regression",
			"E2E" }, description = "Validate if the user is able to view error message when username and password is not given")
	public void verifyIfErrorMessageShownWhenUsernameAndPasswordIsNotGiven() {
		LoginPage loginPage = new LoginPage(getTdDriver());
		loginPage.clickLoginButton();
		boolean isUsernameRequiredErrorShown = loginPage.isUsernameRequiredErrorShown();
		String usernameRequiredErrorActualtext = loginPage.getUsernameRequiredError();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(isUsernameRequiredErrorShown);
		String expectedUsernameRequiredErrorText = ExcelUtil.getData("LoginTests", "TC_008", 1);
		softAssert.assertEquals(usernameRequiredErrorActualtext, expectedUsernameRequiredErrorText);
		softAssert.assertAll();
	}

}
