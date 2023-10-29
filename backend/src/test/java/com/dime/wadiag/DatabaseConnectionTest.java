package com.dime.wadiag;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.junit.jupiter.api.Test;

@SpringBootTest
class DatabaseConnectionTest {

    @Autowired
    private Environment env;

    @Test
    void connection() throws Exception {
        String jdbcUrl = env.getProperty("spring.datasource.url"); // Replace with your database URL
        String username = env.getProperty("spring.datasource.username"); // Replace with your database username
        String password = env.getProperty("spring.datasource.password"); // Replace with your database password

        // Establish a connection to the database
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

        assertThat(connection).isNotNull();
    }
}
