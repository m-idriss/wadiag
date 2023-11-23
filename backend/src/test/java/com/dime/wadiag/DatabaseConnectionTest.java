package com.dime.wadiag;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SpringBootTest
@Slf4j
class DatabaseConnectionTest {

    @Autowired
    private Environment env;

    @DisplayName("Test connection to database and get somes tables")
    @Test
    void test_connection() throws Exception {
        String jdbcUrl = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");

        // Establish a connection to the database
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

        assertThat(connection).isNotNull();

        // Get a list of all table names
        List<String> tableNames = getAllTableNames(connection);
        assertThat(tableNames).contains("databasechangelog", "word", "word_theme_category");
    }

    private List<String> getAllTableNames(Connection connection) {
        List<String> tableNames = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' ORDER BY table_name;";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                tableNames.add(resultSet.getString(1));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error("Error retrieving table names: " + e.getMessage());
        }
        return tableNames;
    }
}
