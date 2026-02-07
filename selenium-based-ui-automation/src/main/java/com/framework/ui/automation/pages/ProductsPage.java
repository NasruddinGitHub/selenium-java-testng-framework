package com.framework.ui.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {

	private final By productsTitle = By.xpath("//span[text()='Products']");
	private final By productSortContainer = By.xpath("//select[@data-test='product-sort-container']");
	private final By productsNames = By.xpath(
			"//div[@data-test='inventory-list']/div[@class='inventory_item']/descendant::div[@data-test='inventory-item-name']");

	public ProductsPage(WebDriver driver) {
		super(driver);
	}

	public boolean isProductsTitleVisible() {
		return isElementVisibleUsingExplictWait(productsTitle, 10);
	}

	public void selectSortOption(String sortOption) {
		selectOptionByTextOfSelectElement(productSortContainer, 10, sortOption);
	}
	
	public String getSelectedSortOption() {
		return getSelectedOptionOfSelectElement(productSortContainer, 10);
	}

	public List<String> getProductsNames() {
		return getAllElementsText(productsNames, 6, 10);
	}

}
