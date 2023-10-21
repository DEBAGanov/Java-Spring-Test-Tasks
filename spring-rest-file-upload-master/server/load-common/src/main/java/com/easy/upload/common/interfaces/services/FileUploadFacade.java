package com.easy.upload.common.interfaces.services;

import com.easy.upload.common.dto.FileListDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by syurov on 12/3/2018.
 */
public interface FileUploadFacade {
  String upload(MultipartFile[] files, FileListDto fileListDto, String fileUrl);
}
