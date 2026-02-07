package com.framework.ui.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

	private final By loginLogo = By.xpath("//div[text()='Swag Labs']");
	private final By username = By.id("user-name");
	private final By password = By.name("password");
	private final By loginButton = By.xpath("//input[@value='Login']");
	private final By usernameRequiredError = By.xpath("//h3[text()='Epic sadface: Username is required']");
	private final By passwordRequiredError = By.xpath("//h3[text()='Epic sadface: Password is required']");
	private final By usernamePasswordMismatchError = By
			.xpath("//h3[contains(text(),'Username and password do not match')]");

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void typeUsername(String inputUsername) {
		type(username, 10, inputUsername);
	}

	public void typePassword(String inputPassword) {
		type(password, 10, inputPassword);
	}

	public void clickLoginButton() {
		click(loginButton, 10);
	}

	public boolean isUsernameFieldShown() {
		return isElementVisibleUsingExplictWait(username, 10);
	}

	public boolean isPasswordFieldShown() {
		return isElementVisibleUsingExplictWait(password, 10);
	}

	public boolean isLoginButtonFieldShown() {
		return isElementVisibleUsingExplictWait(loginButton, 10);
	}

	public boolean isUsernameRequiredErrorShown() {
		return isElementVisibleUsingExplictWait(usernameRequiredError, 10);
	}

	public String getUsernameRequiredError() {
		return getText(usernameRequiredError, 10);
	}

	public boolean isPasswordRequiredErrorShown() {
		return isElementVisibleUsingExplictWait(passwordRequiredError, 10);
	}

	public String getPasswordRequiredError() {
		return getText(passwordRequiredError, 10);
	}

	public boolean isUsernamePasswordMismatchErrorShown() {
		return isElementVisibleUsingExplictWait(usernamePasswordMismatchError, 10);
	}

	public String getUsernamePasswordMismatchError() {
		return getText(usernamePasswordMismatchError, 10);
	}
	
	public void login(String username, String password) {
	    typeUsername(username);
	    typePassword(password);
	    clickLoginButton();
	}

}
