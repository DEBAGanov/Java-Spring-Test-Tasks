package com.easy.upload.api;

import com.easy.upload.common.exceptions.StorageFileNotFoundException;
import com.easy.upload.common.interfaces.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * FileDownloadController is used to view files after upload
 */
@Controller
public class FileDownloadController {

  private StorageService storageService;

  /**
   * Creates controller FileDownloadController
   *
   * @param storageService
   */
  @Autowired
  public FileDownloadController(StorageService storageService) {
    this.storageService = storageService;
  }

  /**
   * Returns a list of uploaded files
   *
   * @param model model
   * @return a list of uploaded files
   * @throws IOException
   */
  @GetMapping("/")
  public String listUploadedFiles(Model model) throws IOException {

    model.addAttribute("files", storageService
        .loadAll()
        .map(
            path ->
                MvcUriComponentsBuilder
                    .fromMethodName(FileDownloadController.class, "serveFile",
                        path.getFileName().toString())
                    .build()
                    .toString()
        )
        .collect(Collectors.toList()));

    return "uploadForm";
  }

  /**
   * serves file
   *
   * @param filename
   * @return a file
   */
  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

    Resource file = storageService.loadAsResource(filename);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  /**
   * handle exception of StorageFileNotFoundException
   *
   * @param exc
   * @return error's response
   */
  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }

}