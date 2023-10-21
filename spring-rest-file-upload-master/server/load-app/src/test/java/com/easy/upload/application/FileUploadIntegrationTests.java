package com.easy.upload.application;

import com.easy.upload.common.interfaces.services.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploadIntegrationTests {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private StorageService storageService;

  @LocalServerPort
  private int port;

  @Test
  public void shouldUploadFile() throws Exception {
    ClassPathResource resource = new ClassPathResource("t1.jpg", getClass());

    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("files", resource);
    ResponseEntity<String> response = this.restTemplate.postForEntity("/", map,
        String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.FOUND);
    assertThat(response.getHeaders().getLocation().toString())
        .startsWith("http://localhost:" + this.port + "/");
    then(storageService).should().store(any(MultipartFile.class));
  }

  @Test
  public void shouldUploadTwoFile() throws Exception {
    ClassPathResource resource = new ClassPathResource("t1.jpg", getClass());
    ClassPathResource resource2 = new ClassPathResource("t2.jpg", getClass());

    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("files", resource);
    map.add("files", resource2);
    ResponseEntity<String> response = this.restTemplate.postForEntity("/", map,
        String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.FOUND);
    assertThat(response.getHeaders().getLocation().toString())
        .startsWith("http://localhost:" + this.port + "/");
    then(storageService).should(times(2)).store(any(MultipartFile.class));
  }

  @Test
  public void shouldUploadAll() throws Exception {
    ClassPathResource resource = new ClassPathResource("t1.jpg", getClass());
    ClassPathResource resource2 = new ClassPathResource("t2.jpg", getClass());
    ClassPathResource resourceJson = new ClassPathResource("test.json", getClass());

    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("files", resource);
    map.add("files", resource2);
    map.add("json", resourceJson);
    map.add("fileUrl", "https://www.google.ru/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png".getBytes());
    ResponseEntity<String> response = this.restTemplate.postForEntity("/", map,
        String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.FOUND);
    assertThat(response.getHeaders().getLocation().toString())
        .startsWith("http://localhost:" + this.port + "/");
    then(storageService).should(times(2)).store(any(MultipartFile.class));
    then(storageService).should(times(2)).store(any(), any());
  }


  @Test
  public void shouldDownloadFile() throws Exception {
    ClassPathResource resource = new ClassPathResource("t1.jpg", getClass());
    given(this.storageService.loadAsResource("t1.jpg")).willReturn(resource);

    ResponseEntity<String> response = this.restTemplate
        .getForEntity("/files/{filename}", String.class, "t1.jpg");

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION))
        .isEqualTo("attachment; filename=\"t1.jpg\"");
  }

}
