package com.easy.upload.common.dto.visit;

import com.easy.upload.common.interfaces.services.visit.FileVisitor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
public class MultipartFileDto extends FileUploadDto {

  MultipartFile file;

  public MultipartFileDto() {
  }

  public MultipartFileDto(MultipartFile file) {
    this.file = file;
  }

  @Override
  String accept(FileVisitor visitor) throws IOException {
    return visitor.visit(this);
  }
}
