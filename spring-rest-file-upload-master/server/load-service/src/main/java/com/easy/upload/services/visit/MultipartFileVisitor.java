package com.easy.upload.services.visit;

import com.easy.upload.common.dto.visit.MultipartFileDto;
import com.easy.upload.common.interfaces.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * UploadService by list of MultipartFile
 */
@Component
public class MultipartFileVisitor extends FileVisitorBase {

  StorageService storageService;

  @Autowired
  public MultipartFileVisitor(StorageService storageService) {
    this.storageService = storageService;
  }

  @Override
  public String visit(MultipartFileDto file) throws IOException {
    if (file == null) return "";
    storageService.store(file.getFile());
    storageService.createThumbnail(file.getFile().getOriginalFilename());
    return file.getFile().getOriginalFilename();
  }
}
