package com.easy.upload.services.visit;

import com.easy.upload.common.dto.visit.JsonFileDto;
import com.easy.upload.common.dto.visit.MultipartFileDto;
import com.easy.upload.common.dto.visit.UrlFileDto;
import com.easy.upload.common.interfaces.services.visit.FileVisitor;

import java.io.IOException;

/**
 * Created by syurov on 12/3/2018.
 */

public abstract class FileVisitorBase implements FileVisitor {

  @Override
  public String visit(JsonFileDto file) throws IOException {
    return "";
  }

  @Override
  public String visit(MultipartFileDto file) throws IOException {
    return "";
  }

  @Override
  public String visit(UrlFileDto file) throws IOException {
    return "";
  }
}
