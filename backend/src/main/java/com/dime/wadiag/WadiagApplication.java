package com.dime.wadiag;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class WadiagApplication {

	public static void main(String[] args) throws UnknownHostException {
		var app = new SpringApplication(WadiagApplication.class);
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
						 Application \t: {}
						 Spring \t: Version {}

						 Access URLs:
						 - External \t: {}://{}:{}
						 - Index \t: {}://localhost:{}/index.html
						 - Local \t: {}://localhost:{}
						 - Swagger UI \t: {}://localhost:{}/swagger-ui.html

						 Actuator Endpoints:
						 - Health \t: {}://localhost:{}/actuator/health

						 Profile(s) \t: {}
						----------------------------------------------------------+""",
				env.getProperty("spring.application.name"),
				SpringVersion.getVersion(),
				protocol,
				InetAddress.getLocalHost().getHostAddress(),
				port,
				protocol,
				port,
				protocol,
				port,
				protocol,
				port,
				protocol,
				port,
				env.getActiveProfiles());
	}
}