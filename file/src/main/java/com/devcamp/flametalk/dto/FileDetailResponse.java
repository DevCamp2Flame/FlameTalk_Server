package com.devcamp.flametalk.dto;

import com.devcamp.flametalk.domain.File;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FileDetailResponse {
  private Long fileId;
  private String title;
  private String extension;
  private String url;
  private LocalDateTime createdDate;

  public FileDetailResponse(File file) {
    this.fileId = file.getId();
    this.title = file.getTitle();
    this.extension = file.getExtension();
    this.url = file.getUrl();
    this.createdDate = file.getCreatedAt();
  }
}
