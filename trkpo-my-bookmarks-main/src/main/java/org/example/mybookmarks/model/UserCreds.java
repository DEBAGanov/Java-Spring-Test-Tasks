package org.example.mybookmarks.model;

import java.util.Objects;

import javax.annotation.Generated;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * RegisterPostRequest
 */

@JsonTypeName("_register_post_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-07-30T12:15:38.128035+03:00[Europe/Moscow]")
public class UserCreds {

  @JsonProperty("email")
  private String email;

  @JsonProperty("password")
  private String password;

  public UserCreds email(String email) {
    this.email = email;
    return this;
  }

  /**
   * user email used as a login name
   * @return email
  */
  @Email
  @Schema(name = "email", description = "user email used as a login name", required = false)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserCreds password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
  */
  
  @Schema(name = "password", required = false)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserCreds userCreds = (UserCreds) o;
    return Objects.equals(this.email, userCreds.email) &&
        Objects.equals(this.password, userCreds.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RegisterPostRequest {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

