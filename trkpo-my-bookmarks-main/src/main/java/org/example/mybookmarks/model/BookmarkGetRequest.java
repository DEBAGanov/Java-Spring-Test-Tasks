package org.example.mybookmarks.model;

import java.util.Objects;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

/**
 * BookmarkGetRequest
 */

@JsonTypeName("_bookmark_get_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-07-30T12:15:38.128035+03:00[Europe/Moscow]")
@AllArgsConstructor
public class BookmarkGetRequest {

  @JsonProperty("url")
  private String url;

  @JsonProperty("tag")
  private String tag;

  /**
   * url to save as a bookmark
   * @return url
  */
  @NotNull 
  @Schema(name = "url", description = "url to save as a bookmark", required = true)
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public BookmarkGetRequest tag(String tag) {
    this.tag = tag;
    return this;
  }

  /**
   * tag of the bookmark
   * @return tag
  */
  
  @Schema(name = "tag", description = "tag of the bookmark", required = false)
  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookmarkGetRequest bookmarkGetRequest = (BookmarkGetRequest) o;
    return Objects.equals(this.url, bookmarkGetRequest.url) &&
        Objects.equals(this.tag, bookmarkGetRequest.tag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, tag);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BookmarkGetRequest {\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
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

