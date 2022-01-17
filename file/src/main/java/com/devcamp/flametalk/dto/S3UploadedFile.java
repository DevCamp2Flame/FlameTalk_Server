package com.devcamp.flametalk.dto;

import com.devcamp.flametalk.domain.File;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;

/**
 * S3에 업로드된 파일 정보에 대한 DTO 입니다.
 */
@Getter
public class S3UploadedFile {

  private String title;
  private String extension;
  private String url;

  /**
   * 생성자입니다.
   *
   * @param name S3에 업로드된 파일의 이름(파일명 + 확장자)
   * @param url  S3에 업로드된 파일의 url
   */
  public S3UploadedFile(String name, String url) {
    this.title = FilenameUtils.getBaseName(name);
    this.extension = FilenameUtils.getExtension(name);
    this.url = url;
  }

  /**
   * 빌더 패턴을 사용하여 파일 엔티티로 변환하는 함수입니다.
   *
   * @return File Entity
   */
  public File toFile() {
    return File.builder()
        .title(title)
        .extension(extension)
        .url(url)
        .build();
  }
}
