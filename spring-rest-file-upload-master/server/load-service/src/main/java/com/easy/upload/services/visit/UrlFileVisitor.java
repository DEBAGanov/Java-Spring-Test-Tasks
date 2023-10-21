package com.easy.upload.services.visit;

import com.easy.upload.common.dto.visit.UrlFileDto;
import com.easy.upload.common.interfaces.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.UUID;

/**
 * Interface for UploadService by URL file
 */
@Component
public class UrlFileVisitor extends FileVisitorBase {

  StorageService storageService;
  RestTemplate restTemplate;

  @Autowired
  public UrlFileVisitor(StorageService storageService, RestTemplate restTemplate) {
    this.storageService = storageService;
    this.restTemplate = restTemplate;
  }

  @Override
  public String visit(UrlFileDto file) throws IOException {
    if (file == null) return "";

    byte[] imageBytes = restTemplate.getForObject(file.getFile(), byte[].class);
    String extension = file.getFile().substring(file.getFile().lastIndexOf(".") + 1);
    String filename = String.format("%s.%s", UUID.randomUUID().toString(), extension);
    storageService.store(imageBytes, filename);
    storageService.createThumbnail(filename);
    return filename;
  }
}
