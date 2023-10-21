package com.easy.upload.services;

import com.easy.upload.common.exceptions.StorageException;
import com.easy.upload.common.exceptions.StorageFileNotFoundException;
import com.easy.upload.common.interfaces.services.StorageProperties;
import com.easy.upload.common.interfaces.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**
 * Created by syurov on 12/2/2018.
 * Implementation of StorageService for file system storage
 */
@Service
public class FileSystemStorageService implements StorageService {
  private final Path rootLocation;

  @Autowired
  public FileSystemStorageService(StorageProperties properties) {
    this.rootLocation = Paths.get(properties.getLocation());
  }

  @Override
  public void store(MultipartFile file) {
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file " + filename);
      }
      if (filename.contains("..")) {
        // This is a security check
        throw new StorageException(
            "Cannot store file with relative path outside current directory "
                + filename);
      }
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, this.rootLocation.resolve(filename),
            StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException e) {
      throw new StorageException("Failed to store file " + filename, e);
    }
  }

  @Override
  public void store(byte[] file, String filename) {
    try {
      try (InputStream inputStream = new ByteArrayInputStream(file)) {
        Files.copy(inputStream, this.rootLocation.resolve(filename),
            StandardCopyOption.REPLACE_EXISTING);


      }
    } catch (IOException e) {
      throw new StorageException("Failed to store file " + filename, e);
    }
  }

  @Override
  public void createThumbnail(String filename) throws IOException {
    BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

    File input = this.rootLocation.resolve(filename).toFile();
    BufferedImage read = ImageIO.read(input);
    img.createGraphics().drawImage(read.getScaledInstance(100, 100, Image.SCALE_SMOOTH), 0, 0, null);
    String extension = filename.substring(filename.lastIndexOf(".") + 1);
    String other = getThumbnailName(filename);
    ImageIO.write(img, extension, this.rootLocation.resolve(other).toFile());
  }

  @Override
  public String getThumbnailName(String filename){
    String name = filename.replaceFirst("[.][^.]+$", "");
    String extension = filename.substring(filename.lastIndexOf(".") + 1);
    return name + "_thumbnail." + extension;
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.rootLocation, 1)
          .filter(path -> !path.equals(this.rootLocation))
          .map(this.rootLocation::relativize);
    } catch (IOException e) {
      throw new StorageException("Failed to read stored files", e);
    }

  }

  @Override
  public Path load(String filename) {
    return rootLocation.resolve(filename);
  }

  @Override
  public void delete(String filename) {
    rootLocation.resolve(filename).toFile().delete();
  }

  @Override
  public Resource loadAsResource(String filename) {
    try {
      Path file = load(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageFileNotFoundException(
            "Could not read file: " + filename);

      }
    } catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read file: " + filename, e);
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(rootLocation.toFile());
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }

}
