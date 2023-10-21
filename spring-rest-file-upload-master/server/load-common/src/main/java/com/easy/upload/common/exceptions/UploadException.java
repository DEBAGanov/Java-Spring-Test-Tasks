package com.easy.upload.common.exceptions;

/**
 * Exception storage
 */
public class UploadException extends RuntimeException {

  public UploadException(String message) {
    super(message);
  }

  public UploadException(String message, Throwable cause) {
    super(message, cause);
  }
}
