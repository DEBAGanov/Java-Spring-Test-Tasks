package com.easy.upload.services;

import com.easy.upload.common.interfaces.services.StorageProperties;
import org.springframework.stereotype.Service;

/**
 * Created by syurov on 12/2/2018.
 * Class stores properties
 */
@Service
public class StoragePropertiesImpl implements StorageProperties {

  /**
   * Folder location for storing files
   */
  private String location = "upload-dir";

  @Override
  public String getLocation() {
    return location;
  }

  @Override
  public void setLocation(String location) {
    this.location = location;
  }

}
