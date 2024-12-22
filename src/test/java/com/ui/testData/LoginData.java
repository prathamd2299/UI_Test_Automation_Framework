package com.ui.testData;

import org.testng.annotations.DataProvider;

public class LoginData {
	@DataProvider(name = "loginData")
	public Object[][] loginData() {
		return new Object[][] { { "standard_user", "secret_sauce" },
//			{ "locked_out_user", "secret_sauce" },
				{ "problem_user", "secret_sauce" }, { "performance_glitch_user", "secret_sauce" }, };
	}
}
