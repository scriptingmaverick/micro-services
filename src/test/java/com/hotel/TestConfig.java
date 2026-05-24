package com.hotel;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.WebTestClient;

@TestConfiguration
public class TestConfig {

  @Bean
  WebTestClient webTestClient(@LocalServerPort int port) {
    return WebTestClient
            .bindToServer()
            .baseUrl("http://localhost:" + port)
            .build();
  }
}