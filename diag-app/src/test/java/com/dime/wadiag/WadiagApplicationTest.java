package com.dime.wadiag;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.UnknownHostException;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@SpringBootTest
class WadiagApplicationTest {

	@Autowired
	private ApplicationContext context;

	@Test
	void test_method_runs_without_exceptions() {
		assertDoesNotThrow(() -> {
			String[] args = { "" };
			WadiagApplication.main(args);
		});
	}

	@DisplayName("Should load context")
	@Test
	void test_context_loads() {
		assertThat(context).isNotNull();
	}

	@DisplayName("Should generate correct's log message from dev environnement")
	@Test
	void test_log_message_for_dev() throws UnknownHostException {

		Environment env = mock(Environment.class);
		when(env.getActiveProfiles()).thenReturn(new String[] { "dev" });
		String logMessage = getLogMessage(env);

		// Assert specific words in the log message
		assertThat(logMessage)
				.contains("http")
				.contains("dev");
	}

	@DisplayName("Should generate correct's log message from environnement")
	@Test
	void test_log_message() throws UnknownHostException {

		Environment env = mock(Environment.class);
		when(env.getProperty("spring.application.name")).thenReturn("Test Application");
		when(env.getActiveProfiles()).thenReturn(new String[] { "profile1", "profile2" });
		when(env.getProperty("server.port")).thenReturn("8080");
		when(env.getProperty("server.ssl.key-store")).thenReturn("keystore");

		String logMessage = getLogMessage(env);

		// Assert specific words in the log message
		assertThat(logMessage)
				.contains("Application")
				.contains("Spring")
				.contains("Access URLs")
				.contains("Actuator Endpoints")
				.contains("Test Application")
				.contains("8080")
				.contains("https:")
				.contains("profile1")
				.contains("profile2");
	}

	@DisplayName("Should generate correct's log message from prod environnement")
	@Test
	void test_log_message_for_prod() throws UnknownHostException {

		Environment env = mock(Environment.class);
		when(env.getActiveProfiles()).thenReturn(new String[] { "prod", "profile2" });
		String logMessage = getLogMessage(env);

		// Assert specific words in the log message
		assertThat(logMessage)
				.contains("http:")
				.contains("prod")
				.contains("profile2");
	}

	private String getLogMessage(Environment env) throws UnknownHostException {
		// Create a logger instance for the class you want to test
		Logger logger = LoggerFactory.getLogger(WadiagApplication.class);

		// Create a ListAppender to capture log messages
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();

		// Add the ListAppender to the logger
		ch.qos.logback.classic.Logger loggerImpl = (ch.qos.logback.classic.Logger) logger;
		loggerImpl.addAppender(listAppender);

		// Call the logMessage method
		WadiagApplication.logMessage(env);

		// Stop the ListAppender to prevent further modifications
		listAppender.stop();

		// Retrieve the entire log message as a single string
		String logMessage = String.join("\n", listAppender.list.stream()
				.map(ILoggingEvent::getFormattedMessage)
				.collect(Collectors.toList()));
		return logMessage;
	}
}