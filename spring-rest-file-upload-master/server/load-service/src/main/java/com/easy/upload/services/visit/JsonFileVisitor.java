package com.easy.upload.services.visit;

import com.easy.upload.common.dto.visit.JsonFileDto;
import com.easy.upload.common.interfaces.services.StorageService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * UploadService by JSON
 */
@Component
public class JsonFileVisitor extends FileVisitorBase {

  StorageService storageService;

  @Autowired
  public JsonFileVisitor(StorageService storageService) {
    this.storageService = storageService;
  }

  @Override
  public String visit(JsonFileDto file) throws IOException {
    if (file == null) return "";
    byte[] imageBytes = Base64.decodeBase64(file.getFile().getBody());
    storageService.store(imageBytes, file.getFile().getFilename());
    storageService.createThumbnail(file.getFile().getFilename());
    return file.getFile().getFilename();
  }
}
