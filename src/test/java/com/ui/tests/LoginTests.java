package com.ui.tests;

import static org.testng.Assert.*;

import org.testng.annotations.*;

import com.ui.base.*;
import com.ui.listeners.*;
import com.ui.pages.*;
import com.ui.testData.*;

@Listeners(TestListener.class)
public class LoginTests extends BaseTest {
	private LoginPage login;
	private ProductsPage products;

//	@BeforeClass
//	public void initializeDriverToPages() {
//		login = new LoginPage(driver);
//		products = new ProductsPage(driver);
//	}

	@Test(priority = 1, groups = { "sanity", "regression" })
	public void verifyLoginWithInvalidData() {
		login = new LoginPage(driver);
		products = new ProductsPage(driver);
		
		login.enterUsername("pratham");
		login.enterPassword("Pratham");
		login.clickOnLoginButton();

		String errorText = login.getErrorText();
		System.out.println(errorText);
		assertEquals(errorText, "Epic sadface: Username and password do not match any user in this service");
	}

	@Test(priority = 2, dataProvider = "loginData", dataProviderClass = LoginData.class, groups = { "smoke",
			"regression" })
	public void verifyLoginWithMoreData(String username, String password) {
		login = new LoginPage(driver);
		products = new ProductsPage(driver);
		
		login.enterUsername(username);
		login.enterPassword(password);
		login.clickOnLoginButton();

		String productsPageTitle = products.getPageTitle();
		assertEquals(productsPageTitle, "Products");

		login.goOnPreviousPage();
	}
}
