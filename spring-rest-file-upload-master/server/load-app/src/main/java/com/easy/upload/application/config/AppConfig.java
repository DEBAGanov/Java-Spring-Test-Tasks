package com.easy.upload.application.config;

import com.easy.upload.application.GracefulShutdown;
import com.easy.upload.common.interfaces.services.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by syurov on 12/2/2018.
 */
@Configuration
@ComponentScan({
    "com.easy.upload.api"})
public class AppConfig {

  @Bean
  CommandLineRunner init(StorageService storageService) {
    return (args) -> {
      storageService.deleteAll();
      storageService.init();
    };
  }

  @Bean
  public GracefulShutdown gracefulShutdown() {
    return new GracefulShutdown();
  }

  @Bean
  public ConfigurableServletWebServerFactory webServerFactory(final GracefulShutdown gracefulShutdown) {
    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
    factory.addConnectorCustomizers(gracefulShutdown);
    return factory;
  }

}
