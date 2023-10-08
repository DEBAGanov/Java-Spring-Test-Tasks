package org.example.mybookmarks.model;

import java.util.Objects;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * error details
 */

@Schema(name = "Error", description = "error details")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-07-30T12:15:38.128035+03:00[Europe/Moscow]")
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

  @JsonProperty("error")
  private String error;

  public ErrorResponse error(String error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   * @return error
  */
  
  @Schema(name = "error", required = false)
  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse error = (ErrorResponse) o;
    return Objects.equals(this.error, error.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
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

