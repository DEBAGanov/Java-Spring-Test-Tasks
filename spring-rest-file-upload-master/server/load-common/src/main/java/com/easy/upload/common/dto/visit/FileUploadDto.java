package com.easy.upload.common.dto.visit;

import com.easy.upload.common.interfaces.services.visit.FileVisitor;

import java.io.IOException;

public abstract class FileUploadDto {

  abstract String accept(FileVisitor visitor) throws IOException;

}
