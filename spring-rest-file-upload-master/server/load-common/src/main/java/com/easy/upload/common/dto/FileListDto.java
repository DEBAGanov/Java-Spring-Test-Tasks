package com.easy.upload.common.dto;

import lombok.Data;

import java.util.List;

/**
 * JSON with list of files
 */
@Data
public class FileListDto {

  List<FileDto> files;

}
