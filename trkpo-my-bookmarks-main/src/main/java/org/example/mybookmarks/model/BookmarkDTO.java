package org.example.mybookmarks.model;

import java.time.Instant;

import lombok.Data;

/**
 * Bookmark information
 */

@Data
public class BookmarkDTO {
  private Long id;

  private String url;

  private Instant createdDate;

  private String title;

  private String tag;


}

