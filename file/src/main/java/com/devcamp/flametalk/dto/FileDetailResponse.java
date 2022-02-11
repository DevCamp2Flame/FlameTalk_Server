package com.devcamp.flametalk.dto;

import com.devcamp.flametalk.domain.File;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 파일 상세 정보 응답을 위한 클래스입니다.
 */
@NoArgsConstructor
@Getter
public class FileDetailResponse {

  private Long fileId;
  private String title;
  private String extension;
  private String url;
  private LocalDateTime createdDate;

  /**
   * 엔티티인 File 객체를 응답 dto 객체로 매핑하는 생성자입니다.
   *
   * @param file 파일 entity
   */
  public FileDetailResponse(File file) {
    this.fileId = file.getId();
    this.title = file.getTitle();
    this.extension = file.getExtension();
    this.url = file.getUrl();
    this.createdDate = file.getCreatedAt();
  }
}
