package com.dime.wadiag.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.read.ListAppender;

class LogbackConfigurationTest {
	private LoggerContext context;
	private ListAppender<ILoggingEvent> listAppender;

	@BeforeEach
	void setup() throws JoranException {
		// Initialize the Logback logger context
		context = (LoggerContext) LoggerFactory.getILoggerFactory();

		// Configure Logback with your XML configuration file
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(context);
		context.reset();
		configurator.doConfigure("src/main/resources/logback-spring.xml");

		// Create and add a ListAppender to capture log output
		listAppender = new ListAppender<ILoggingEvent>();
		listAppender.start();
		Logger logger = context.getLogger("com.dime");
		logger.addAppender(listAppender);
	}

	@DisplayName("Should see log from logback configuration")
	@Test
	void test_logback_configuration() {
		// Get a logger instance
		Logger logger = context.getLogger("com.dime");
		assertEquals("com.dime", logger.getName());
		// Log some messages
		logger.info("Test message");
		logger.error("Error message");

	}
}
