package com.framework.ui.automation.tests;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.ui.automation.config.ConfigReader;
import com.framework.ui.automation.pages.LoginPage;
import com.framework.ui.automation.pages.ProductsPage;
import com.framework.ui.automation.utils.CommonUtil;
import com.framework.ui.automation.utils.ExcelUtil;
import com.framework.ui.automation.utils.Log;

public class ProductsTest extends TestBase {

	private static final Logger log = Log.getLogger(ProductsTest.class);

	@Test(groups = { "Smoke",
			"Regression" }, description = "Validate that the products are shown in ascending order (Name(A to Z)) by default")
	public void verifyIfProductsAreShownInAscendingOrderByNameByDefault() throws IOException {
		LoginPage loginPage = new LoginPage(getTdDriver());
		loginPage.login(ConfigReader.getProperty("standardUsername"), ConfigReader.getProperty("password"));

		ProductsPage productsPage = new ProductsPage(getTdDriver());
		String selectedSortOption = productsPage.getSelectedSortOption();
		String expectedSortOption = ExcelUtil.getData("ProductsTests", "1235431", 1);
		List<String> productsInUi = productsPage.getProductsNames();
		Assert.assertEquals(selectedSortOption, expectedSortOption);
		List<String> productsInTestData = CommonUtil
				.converCommaSeperatedValueToList(ExcelUtil.getData("ProductsTests", "1235431", 2));
		Assert.assertEquals(productsInUi, productsInTestData, "Product comparision is failed");
	}

	@Test(groups = {
			"Regression" }, description = "Validate that the products are shown in descending order (Name(Z to A)) when selecting Name (Z to A)")
	public void verifyIfProductsAreShownInDescendingOrderByNameWhenSelectingNameZToA() {
		LoginPage loginPage = new LoginPage(getTdDriver());
		loginPage.login(ConfigReader.getProperty("standardUsername"), ConfigReader.getProperty("password"));

		ProductsPage productsPage = new ProductsPage(getTdDriver());
		productsPage.selectSortOption("Name (Z to A)");
		List<String> productsInUi = productsPage.getProductsNames();
		List<String> productsInTestData = CommonUtil
				.converCommaSeperatedValueToList(ExcelUtil.getData("ProductsTests", "TC_009", 1));
		Assert.assertEquals(productsInUi, productsInTestData, "Product comparision is failed");
	}

	@Test(groups = { "Regression",
			"E2E" }, description = "Validate that all the sort options are available in sort container")
	public void verifyIfAllSortOptionsAreShown() {
		LoginPage loginPage = new LoginPage(getTdDriver());
		loginPage.login(ConfigReader.getProperty("standardUsername"), ConfigReader.getProperty("password"));

		ProductsPage productsPage = new ProductsPage(getTdDriver());
		List<String> allSortOptionsInUi = productsPage.getAllSortOptions();
		List<String> allSortOptionsInTestData = CommonUtil
				.converCommaSeperatedValueToList(ExcelUtil.getData("ProductsTests", "TC_010", 1));
		Assert.assertEquals(allSortOptionsInUi, allSortOptionsInTestData, "Sort options comparision is failed");
	}

}
