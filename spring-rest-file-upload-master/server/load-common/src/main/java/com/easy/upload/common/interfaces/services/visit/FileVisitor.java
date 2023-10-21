package com.easy.upload.common.interfaces.services.visit;

import com.easy.upload.common.dto.visit.JsonFileDto;
import com.easy.upload.common.dto.visit.MultipartFileDto;
import com.easy.upload.common.dto.visit.UrlFileDto;

import java.io.IOException;

/**
 * Created by syurov on 12/3/2018.
 */
public interface FileVisitor {

  String visit(JsonFileDto file) throws IOException;

  String visit(MultipartFileDto file) throws IOException;

  String visit(UrlFileDto file) throws IOException;

}
