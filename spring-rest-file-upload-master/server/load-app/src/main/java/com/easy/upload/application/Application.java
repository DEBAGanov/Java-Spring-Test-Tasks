package com.easy.upload.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class for start and configure Spring Application
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.easy.upload.application")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
