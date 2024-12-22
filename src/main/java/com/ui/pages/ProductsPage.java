package com.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ui.utilities.BrowserUtil;

public class ProductsPage extends BrowserUtil {
	private By productPageTitle = By.xpath("//div[@class='product_label']");

	public ProductsPage(WebDriver driver) {
		super(driver);
	}

	public String getPageTitle() {
		return getText(productPageTitle);
	}
}
