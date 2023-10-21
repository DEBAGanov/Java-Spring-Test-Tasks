package com.easy.upload.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LongProcessController {

  @RequestMapping("/long-process")
  public String pause() throws InterruptedException {
    Thread.sleep(100000);
    return "Process finished";
  }

}