package com.easy.upload.common.interfaces.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Interface for StorageService
 */
public interface StorageService {
  void init();

  void store(MultipartFile file);

  void store(byte[] file, String filename);

  void createThumbnail(String filename) throws IOException;

  String getThumbnailName(String filename);

  Stream<Path> loadAll();

  Path load(String filename);

  void delete(String filename);

  Resource loadAsResource(String filename);

  void deleteAll();
}
