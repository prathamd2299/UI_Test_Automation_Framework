package com.ui.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtility {
	public static final Logger logger = LogManager.getLogger(LoggerUtility.class);

	public static void info(String message) {
		logger.info(message + "\n");
	}

	public static void error(String message) {
		logger.error(message + "\n");
	}

	public static void debug(String message) {
		logger.debug(message + "\n");
	}
}
