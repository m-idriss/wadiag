package com.dime.wadiag;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class WadiagApplicationTests {

	@Autowired
	private ApplicationContext context;

	@DisplayName("Should load application")
	@Test
	void testMain() {
		Assertions.assertDoesNotThrow(() -> WadiagApplication.main(new String[] {}));
	}

	@DisplayName("Should load context")
	@Test
	void contextLoads() {
		assertThat(context).isNotNull();
	}

}