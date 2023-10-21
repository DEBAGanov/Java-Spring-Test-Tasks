package com.easy.upload.services;

import com.easy.upload.common.dto.visit.UrlFileDto;
import com.easy.upload.common.interfaces.services.StorageProperties;
import com.easy.upload.services.visit.UrlFileVisitor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for UploadService
 */
public class UrlFileVisitorTests {

  private StorageProperties properties = new StoragePropertiesImpl();
  private FileSystemStorageService serviceStorage;
  private UrlFileVisitor visitor;
  private RestTemplate restTemplate;

  @Before
  public void init() {
    properties.setLocation("target/files/" + Math.abs(new Random().nextLong()));
    serviceStorage = new FileSystemStorageService(properties);
    serviceStorage.init();
    restTemplate = new RestTemplate();
    visitor = new UrlFileVisitor(serviceStorage, restTemplate);
  }

  @Test
  public void uploadTest() throws IOException {

    visitor.visit(new UrlFileDto("https://www.google.ru/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"));
    assertThat(serviceStorage.loadAll().count()).isEqualTo(2);
  }
}
