package com.framework.ui.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.framework.ui.automation.exceptions.UiFrameworkException;

public class ConfigReader {

	private static Properties properties;

	private ConfigReader() {
		// Do not allow to create instance
	}

	static {
		properties = new Properties();
		String path = System.getProperty("user.dir") + "/src/test/resources/config/config.properties";
		try (FileInputStream fis = new FileInputStream(path)) { // AUTO CLOSE
			properties.load(fis);
		} catch (IOException e) {
			throw new UiFrameworkException("Failed to load config.properties", e);
		}
	}

	public static String getProperty(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			throw new UiFrameworkException("Key not found: " + key);
		}
		return value.trim();
	}

}
