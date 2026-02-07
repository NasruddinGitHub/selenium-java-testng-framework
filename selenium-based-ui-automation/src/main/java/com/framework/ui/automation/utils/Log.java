package com.framework.ui.automation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	
	private Log() {
		// to restrict object creation
	}
	public static Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}
}
