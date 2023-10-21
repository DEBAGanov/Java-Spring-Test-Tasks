package com.easy.upload.common.dto.visit;

import com.easy.upload.common.dto.FileDto;
import com.easy.upload.common.interfaces.services.visit.FileVisitor;
import lombok.Data;

import java.io.IOException;

@Data
public class JsonFileDto extends FileUploadDto {

  FileDto file;

  public JsonFileDto() {
  }

  public JsonFileDto(FileDto file) {
    this.file = file;
  }

  @Override
  String accept(FileVisitor visitor) throws IOException {
    return visitor.visit(this);
  }
}
