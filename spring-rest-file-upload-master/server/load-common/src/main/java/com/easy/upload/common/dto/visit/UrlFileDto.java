package com.easy.upload.common.dto.visit;

import com.easy.upload.common.interfaces.services.visit.FileVisitor;
import lombok.Data;

import java.io.IOException;

@Data
public class UrlFileDto extends FileUploadDto {

  String file;

  public UrlFileDto() {
  }

  public UrlFileDto(String file) {
    this.file = file;
  }

  @Override
  String accept(FileVisitor visitor) throws IOException {
    return visitor.visit(this);
  }
}
