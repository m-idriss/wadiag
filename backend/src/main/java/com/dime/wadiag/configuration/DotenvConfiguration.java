package com.dime.wadiag.configuration;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfiguration {

  @Bean
  public Dotenv dotenv() {
    return Dotenv.configure().load();
  }

}
