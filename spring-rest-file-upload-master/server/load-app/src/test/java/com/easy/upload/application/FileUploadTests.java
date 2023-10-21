package com.easy.upload.application;

import com.easy.upload.common.exceptions.StorageFileNotFoundException;
import com.easy.upload.common.interfaces.services.FileUploadFacade;
import com.easy.upload.common.interfaces.services.StorageService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FileUploadTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private StorageService storageService;

  @MockBean
  private FileUploadFacade fileUploadFacade;


  @Test
  public void shouldListAllFiles() throws Exception {
    BDDMockito.given(this.storageService.loadAll())
        .willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));

    this.mvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attribute("files",
            Matchers.contains("http://localhost/files/first.txt",
                "http://localhost/files/second.txt")));
  }

  @Test
  public void shouldSaveUploadedFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("files", "test.txt", "text/plain", "Spring Framework".getBytes());
    MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", "{\"files\":[{\"filename\":\"google.png\",\"body\": \"123=\"}]}".getBytes());
    MockMultipartFile fileUrl = new MockMultipartFile("fileUrl", "", "text/plain", "https://www.google.ru/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png".getBytes());

    MockMultipartFile[] arr = {multipartFile};

    this.mvc.perform(
        MockMvcRequestBuilders.multipart("/")
            .file(multipartFile)
            .file(jsonFile)
            .file(fileUrl)
            .contentType(MediaType.MULTIPART_FORM_DATA)
    )
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andExpect(MockMvcResultMatchers.header().string("Location", "/"));

    BDDMockito.then(this.fileUploadFacade).should().upload(any(), any(), any());
  }

  @Test
  public void shouldSaveUploadedFile1() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("files", "test.txt", "text/plain", "Spring Framework".getBytes());

    MockMultipartFile[] arr = {multipartFile};

    this.mvc.perform(
        MockMvcRequestBuilders.multipart("/")
            .file(multipartFile)
            .contentType(MediaType.MULTIPART_FORM_DATA)
    )
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andExpect(MockMvcResultMatchers.header().string("Location", "/"));

    BDDMockito.then(this.fileUploadFacade).should().upload(any(), any(), any());
  }

  @Test
  public void shouldSaveUploadedFile2() throws Exception {
    MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", "{\"files\":[{\"filename\":\"google.png\",\"body\": \"123=\"}]}".getBytes());


    this.mvc.perform(
        MockMvcRequestBuilders.multipart("/")
            .file(jsonFile)
            .contentType(MediaType.MULTIPART_FORM_DATA)
    )
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andExpect(MockMvcResultMatchers.header().string("Location", "/"));

    BDDMockito.then(this.fileUploadFacade).should().upload(any(), any(), any());
  }

  @Test
  public void shouldSaveUploadedFile3() throws Exception {
    MockMultipartFile fileUrl = new MockMultipartFile("fileUrl", "", "text/plain", "https://www.google.ru/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png".getBytes());


    this.mvc.perform(
        MockMvcRequestBuilders.multipart("/")
            .file(fileUrl)
            .contentType(MediaType.MULTIPART_FORM_DATA)
    )
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andExpect(MockMvcResultMatchers.header().string("Location", "/"));

    BDDMockito.then(this.fileUploadFacade).should().upload(any(), any(), any());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void should404WhenMissingFile() throws Exception {
    BDDMockito.given(this.storageService.loadAsResource("test.txt"))
        .willThrow(StorageFileNotFoundException.class);

    this.mvc.perform(MockMvcRequestBuilders.get("/files/test.txt")).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

}
