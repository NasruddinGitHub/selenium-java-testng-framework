package com.framework.ui.automation.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import com.framework.ui.automation.exceptions.UiFrameworkException;
import com.framework.ui.automation.utils.Log;

public class BasePage {

	protected WebDriver driver;
	private static final Logger log = Log.getLogger(BasePage.class);

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	private WebDriverWait getWait(int timeout) {
		return new WebDriverWait(driver, Duration.ofSeconds(timeout));
	}

	public WebElement waitUntilElementIsVisible(By locator, int timeout) {
		return getWait(timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitUntilElementIsPresent(By locator, int timeout) {
		return getWait(timeout).until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public WebElement waitUntilElementIsClickable(By locator, int timeout) {
		return getWait(timeout).until(ExpectedConditions.elementToBeClickable(locator));
	}

	public Boolean waitUntilElementIsInvisible(By locator, int timeout) {
		return getWait(timeout).until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public List<WebElement> waitUntilNumberOfElementsToBe(By locator, int expectedNumberOfElements, int timeout) {
		return getWait(timeout).until(ExpectedConditions.numberOfElementsToBe(locator, expectedNumberOfElements));
	}

	public List<WebElement> waitUntilNumberOfElementsToBeLessThan(By locator, int number, int timeout) {
		return getWait(timeout).until(ExpectedConditions.numberOfElementsToBeLessThan(locator, number));
	}

	public List<WebElement> waitUntilNumberOfElementsToBeMoreThan(By locator, int number, int timeout) {
		return getWait(timeout).until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, number));
	}

	public WebElement findElement(By locator, int timeout) {
		return waitUntilElementIsVisible(locator, timeout);
	}

	public List<WebElement> findElements(By locator, int timeout) {
		waitUntilElementIsVisible(locator, timeout);
		return driver.findElements(locator);
	}

	// Retry mechanism
	public void type(By locator, int timeout, CharSequence... keysToSend) {
		int maxAttempts = 5; // keep retries realistic
		int retryDelayMs = 500;

		for (int attempt = 1; attempt <= maxAttempts; attempt++) {
			try {
				findElement(locator, timeout).sendKeys(keysToSend);
				String typedData = String.join("", keysToSend);
				log.info("Typed '{}' into element {} on attempt {}", typedData, locator, attempt);
				return; // success → exit method
			} catch (StaleElementReferenceException | ElementNotInteractableException e) {
				log.warn("Attempt {} to type into {} failed due to {}", attempt, locator, e.getClass().getSimpleName());
				if (attempt == maxAttempts) {
					log.error("Typing into {} failed after {} attempts", locator, maxAttempts);
					throw e;
				}
				sleep(retryDelayMs);
			}
		}
	}

	// Retry mechanism
	public void click(By locator, int timeout) {
		int maxAttempts = 10;
		int retryDelayMs = 500;
		for (int attempt = 1; attempt <= maxAttempts; attempt++) {
			try {
				waitUntilElementIsClickable(locator, timeout).click();
				log.info("Clicked on element {} on attempt {}", locator, attempt);
				return;
			} catch (StaleElementReferenceException | ElementNotInteractableException e) {
				log.warn("Attempt {} to click {} failed due to {}", attempt, locator, e.getClass().getSimpleName());
				if (attempt == maxAttempts) {
					log.error("Click action failed on {} after {} attempts", locator, maxAttempts);
					throw e;
				}
				sleep(retryDelayMs);
			}
		}
	}

	public boolean isElementVisibleUsingExplictWait(By locator, int timeout) {
		try {
			waitUntilElementIsVisible(locator, timeout);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getText(By locator, int timeout) {
		int maxAttempts = 5;
		int retryDelayMs = 500;
		for (int attempt = 1; attempt <= maxAttempts; attempt++) {
			try {
				String text = waitUntilElementIsVisible(locator, timeout).getText().trim();
				log.info("Fetched text '{}' from {} on attempt {}", text, locator, attempt);
				return text;
			} catch (StaleElementReferenceException | ElementNotInteractableException e) {
				log.warn("Attempt {} to get text from {} failed due to {}", attempt, locator,
						e.getClass().getSimpleName());
				if (attempt == maxAttempts) {
					log.error("Failed to get text from {} after {} attempts", locator, maxAttempts);
					throw e;
				}
				sleep(retryDelayMs);
			}
		}
		// Should never reach here
		throw new UiFrameworkException("Unexpected error getting text from: " + locator);
	}

	private void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new UiFrameworkException("Thread interrupted", e);
		}
	}

	public String getSelectedOptionOfSelectElement(By locator, int timeout) {
		Select select = new Select(waitUntilElementIsVisible(locator, timeout));
		return select.getFirstSelectedOption().getText().trim();
	}

	public void selectOptionByTextOfSelectElement(By locator, int timeout, String visibleText) {
		Select select = new Select(waitUntilElementIsVisible(locator, timeout));
		select.selectByVisibleText(visibleText);
	}

	public void selectOptionByIndexOfSelectElement(By locator, int timeout, int index) {
		Select select = new Select(waitUntilElementIsVisible(locator, timeout));
		select.selectByIndex(index);
	}

	public void selectOptionByValueOfSelectElement(By locator, int timeout, String value) {
		Select select = new Select(waitUntilElementIsVisible(locator, timeout));
		select.selectByValue(value);
	}

	public List<String> getAllOptionsOfSelectElement(By locator, int timeout) {
		Select select = new Select(waitUntilElementIsVisible(locator, timeout));
		List<WebElement> allOptions = select.getOptions();
		List<String> allOptionsText = new LinkedList<>();
		for (WebElement element : allOptions) {
			allOptionsText.add(element.getText().trim());
		}
		return allOptionsText;
	}

	public List<String> getAllElementsText(By locator, int expectedNumberOfElements, int timeout) {
		List<WebElement> allElements = waitUntilNumberOfElementsToBe(locator, expectedNumberOfElements, timeout);
		List<String> allElementsText = new LinkedList<>();
		for (WebElement element : allElements) {
			allElementsText.add(element.getText().trim());
		}
		return allElementsText;
	}
	
	public <T> List<T> getAllElements(
	        By locator,
	        int expectedNumberOfElements,
	        int timeout,
	        Function<String, T> mapper) {

	    List<WebElement> allElements =
	            waitUntilNumberOfElementsToBe(locator, expectedNumberOfElements, timeout);

	    List<T> result = new ArrayList<>();

	    for (WebElement element : allElements) {
	        String text = element.getText().trim();
	        result.add(mapper.apply(text));
	    }
	    return result;
	}


}
