package com.dime.wadiag;

import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
public class WadiagApplication {

	public static void main(String[] args) throws UnknownHostException {
	    var app = new SpringApplication(WadiagApplication.class);
	    final Environment env = app.run(args).getEnvironment();
	    var protocol = "http";
	    if (env.getProperty("server.ssl.key-store") != null) {
	      protocol = "https";
	    }
	    log.info("\n");
	    log.info("""
	        
	         ----------------------------------------------------------
	        \t Application '{}' is running!
	        \t Access URLs:
	        \t - Local: \t\t{}://localhost:{}
	        \t - External: \t{}://{}:{}
	        \t Sawgger UI: \t{}://localhost:{}/swagger-ui.html
	        \t Profile(s): \t{}
	         ----------------------------------------------------------
	        """,
	        //
	        env.getProperty("spring.application.name"),
	        protocol, env.getProperty("server.port"),
	        protocol, InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"),
	        protocol, env.getProperty("server.port"),
	        env.getActiveProfiles());
	  }

}
