package com.easy.upload.api;


import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Application config
 */
@Log4j
@Configuration
@ComponentScan({
    "com.easy.upload.services"})
public class ApiConfig {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
