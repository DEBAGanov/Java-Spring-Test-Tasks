package com.easy.upload.services;

import com.easy.upload.common.dto.FileDto;
import com.easy.upload.common.dto.FileListDto;
import com.easy.upload.common.dto.visit.FileUploadDto;
import com.easy.upload.common.dto.visit.JsonFileDto;
import com.easy.upload.common.dto.visit.MultipartFileDto;
import com.easy.upload.common.dto.visit.UrlFileDto;
import com.easy.upload.common.exceptions.UploadException;
import com.easy.upload.common.interfaces.services.FileUploadFacade;
import com.easy.upload.common.interfaces.services.StorageService;
import com.easy.upload.common.interfaces.services.visit.FileVisitor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by syurov on 12/3/2018.
 */
@Service
@Log4j
public class FileUploadFacadeImpl implements FileUploadFacade {

  List<FileVisitor> fileVisitorList;
  StorageService storageService;

  @Autowired
  public FileUploadFacadeImpl(List<FileVisitor> fileVisitorList, StorageService storageService) {
    this.fileVisitorList = fileVisitorList;
    this.storageService = storageService;
  }

  @Override
  public String upload(MultipartFile[] files, FileListDto fileListDto, String fileUrl) {

    List<FileUploadDto> fileUploadDtoList = new LinkedList<>();

    List<String> fileNames = new LinkedList<>();

    boolean isError = false;
    if (files != null) {
      for (MultipartFile file : files) {
        for (FileVisitor visitor : fileVisitorList) {
          try {
            String visitResult = visitor.visit(new MultipartFileDto(file));
            if (!StringUtils.isEmpty(visitResult))
              fileNames.add(visitResult);
          } catch (IOException e) {
            log.error(e, e);
            isError = true;
          }
        }
      }
    }

    if (fileListDto != null && !isError) {
      for (FileDto fileDto : fileListDto.getFiles()) {
        for (FileVisitor visitor : fileVisitorList) {
          try {
            String visitResult = visitor.visit(new JsonFileDto(fileDto));
            if (!StringUtils.isEmpty(visitResult))
              fileNames.add(visitResult);
          } catch (IOException e) {
            log.error(e, e);
            isError = true;
          }
        }
      }
    }

    if (!StringUtils.isEmpty(fileUrl) && !isError) {
      for (FileVisitor visitor : fileVisitorList) {
        try {
          String visitResult = visitor.visit(new UrlFileDto(fileUrl));
          if (!StringUtils.isEmpty(visitResult))
            fileNames.add(visitResult);
        } catch (IOException e) {
          log.error(e, e);
          isError = true;
        }
      }
    }

    // if was error then delete file
    if (isError) {

      for (String fileName : fileNames)
        storageService.delete(storageService.getThumbnailName(fileName));

      fileNames.forEach(storageService::delete);

      throw new UploadException("One or more file hasn't uploaded");
    }

    return fileNames.stream().collect(Collectors.joining(";"));
  }

}
