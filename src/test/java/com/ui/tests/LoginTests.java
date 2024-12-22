package com.ui.tests;

import static org.testng.Assert.*;

import org.testng.annotations.Listeners;
import org.testng.annotations.*;

import com.ui.base.BaseTest;
import com.ui.listeners.*;
import com.ui.testData.LoginData;

@Listeners(TestListener.class)
public class LoginTests extends BaseTest {
	@Test(priority = 1, dataProvider = "loginData", dataProviderClass = LoginData.class)
	public void verifyLoginWithInvalidData(String username, String password) {
		login.enterUsername(username);
		login.enterPassword(password);
		login.clickOnLoginButton();
		
		login.goOnPreviousPage();

//		String errorText = login.getErrorText();
//		System.out.println(errorText);
//		assertEquals(errorText, "Epic sadface: Username and password do not match any user in this service");
	}

	@Test(priority = 2)
	public void verifyLoginWithValidData() {
		login.enterUsername("standard_user");
		login.enterPassword("secret_sauce");
		login.clickOnLoginButton();

		String productsPageTitle = products.getPageTitle();
		assertEquals(productsPageTitle, "Products");
	}
}
