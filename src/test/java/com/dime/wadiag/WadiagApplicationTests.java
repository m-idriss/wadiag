package com.dime.wadiag;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.UnknownHostException;

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

    @Test
    @DisplayName("Test main")

    void testMain() {
        // Test the main method without throwing exceptions
        Assertions.assertDoesNotThrow(() -> WadiagApplication.main(new String[]{}));
    }

    @Test
    @DisplayName("Test load context")
    void contextLoads() {
        assertThat(context).isNotNull();
    }

}
