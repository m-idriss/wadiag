package com.dime.wadiag;

import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;

import com.dime.wadiag.configuration.SwaggerRedirectFilter;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class DiagLogApplication {

	public static void main(String[] args) throws UnknownHostException {
		var app = new SpringApplication(DiagLogApplication.class);
		final Environment env = app.run(args).getEnvironment();
		logMessage(env);
	}

	public static void logMessage(Environment env) throws UnknownHostException {
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		final String port = env.getProperty("server.port");
		log.info(
				"\n" + """
						----------------------------------------------------------+
						 Application \t : {}
						 Spring \t\t : Version {}
						 Profile(s) \t : {}

						 Access URLs:
						 - Local \t\t : {}://localhost:{}

						 Actuator Endpoints:
						 - Health \t: {}://localhost:{}/actuator/health
						----------------------------------------------------------+""",
				env.getProperty("spring.application.name"),
				SpringVersion.getVersion(),
				env.getActiveProfiles(),
				protocol,
				port);
	}

	@Bean
	FilterRegistrationBean<SwaggerRedirectFilter> filterRegistrationBean() {
		FilterRegistrationBean<SwaggerRedirectFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new SwaggerRedirectFilter());
		return filterRegistrationBean;
	}
}
