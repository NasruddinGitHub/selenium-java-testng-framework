package com.framework.ui.automation.tests;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.ui.automation.config.ConfigReader;
import com.framework.ui.automation.pages.BasePage;
import com.framework.ui.automation.pages.LoginPage;
import com.framework.ui.automation.pages.ProductsPage;
import com.framework.ui.automation.utils.ExcelUtil;
import com.framework.ui.automation.utils.Log;

public class ProductsTest extends TestBase {

	private static final Logger log = Log.getLogger(ProductsTest.class);

	@Test(groups = { "Smoke",
			"Regression" }, description = "Validate that the products are shown in ascending order (Name(A to Z)) by default")
	public void verifyIfProductsAreShownInAscendingOrderByNameByDefault() throws IOException {
		LoginPage loginPage = new LoginPage(getTdDriver());
		loginPage.typeUsername(ConfigReader.getProperty("standardUsername"));
		loginPage.typePassword(ConfigReader.getProperty("password"));
		loginPage.clickLoginButton();

		ProductsPage productsPage = new ProductsPage(getTdDriver());
		String selectedSortOption = productsPage.getSelectedSortOption();
		String expectedSortOption = ExcelUtil.getData("ProductsTests", "1235431", 1);
		List<String> productsInUi = productsPage.getProductsNames();
		Assert.assertEquals(selectedSortOption, expectedSortOption);
		Assert.assertTrue(
				productsInUi.equals(List.of("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt",
						"Sauce Labs Fleece Jacket", "Sauce Labs Onesie", "Test.allTheThings() T-Shirt (Red)")));
	}

	@Test(groups = {
			"Regression" }, description = "Validate that the products are shown in descending order (Name(Z to A)) when selecting Name (Z to A)")
	public void verifyIfProductsAreShownInDescendingOrderByNameWhenSelectingNameZToA() {
		LoginPage loginPage = new LoginPage(getTdDriver());
		loginPage.typeUsername(ConfigReader.getProperty("standardUsername"));
		loginPage.typePassword(ConfigReader.getProperty("password"));
		loginPage.clickLoginButton();

		ProductsPage productsPage = new ProductsPage(getTdDriver());
		productsPage.selectSortOption("Name (Z to A)");
		List<String> productsInUi = productsPage.getProductsNames();
		Assert.assertTrue(productsInUi
				.equals(List.of("Test.allTheThings() T-Shirt (Red)", "Sauce Labs Onesie", "Sauce Labs Fleece Jacket",
						"Sauce Labs Bolt T-Shirt", "Sauce Labs Bike Light", "Sauce Labs Backpack")));
	}

	@Test(groups = { "Regression",
			"E2E" }, description = "Validate that all the sort options are available in sort container")
	public void verifyIfAllSortOptionsAreShown() {
		LoginPage loginPage = new LoginPage(getTdDriver());
		loginPage.typeUsername(ConfigReader.getProperty("standardUsername"));
		loginPage.typePassword(ConfigReader.getProperty("password"));
		loginPage.clickLoginButton();

		ProductsPage productsPage = new ProductsPage(getTdDriver());
		List<String> allSortOptions = productsPage.getAllSortOptions();
		Assert.assertTrue(allSortOptions
				.equals(List.of("Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)")));
	}

}
