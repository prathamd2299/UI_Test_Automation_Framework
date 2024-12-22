package com.ui.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigManager {
	private static Properties properties;

	public static String getConfigProperties(String key) {
		try {
			FileInputStream fileInputStream = new FileInputStream("src\\test\\resource\\config.properties");
			properties = new Properties();
			properties.load(fileInputStream);
		} catch (Exception e) {
			System.out.println("Failed to load properties file");
		}

		String value = properties.getProperty(key);

		if (value != null) {
			return value;
		} else {
			System.out.println("Searching key is not found in properties file");
			return null;
		}
	}

}
