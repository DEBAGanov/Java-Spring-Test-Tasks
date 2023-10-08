package org.example.mybookmarks.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * RegisterPost200Response
 */

@Data
public class AuthOkResponse {

  @JsonProperty("authToken")
  private String authToken;

  public AuthOkResponse authToken(String authToken) {
    this.authToken = authToken;
    return this;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthOkResponse authOkResponse = (AuthOkResponse) o;
    return Objects.equals(this.authToken, authOkResponse.authToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authToken);
  }

}

