package com.devcamp.flametalk.dto;

import com.devcamp.flametalk.domain.Chatroom;
import com.devcamp.flametalk.domain.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * S3에 업로드된 파일 정보에 대한 DTO 입니다.
 */
@Getter
public class S3UploadedFile {

  private MultipartFile multipartFile;
  private String title;
  private String extension;
  private String url;
  private Chatroom chatroom;

  /**
   * 생성자입니다.
   *
   * @param multipartFile S3에 업로드된 파일의 이름(파일명 + 확장자)
   * @param chatroom 파일이 채팅방에서 업로드된 경우의 해당 채팅방 정보
   */
  public S3UploadedFile(MultipartFile multipartFile, Chatroom chatroom) {
    String originFileName = multipartFile.getOriginalFilename();
    SimpleDateFormat date = new SimpleDateFormat("yyyymmddHHmmss");
    this.multipartFile = multipartFile;
    this.title =
        "flametalk_" + FilenameUtils.getBaseName(originFileName) + "_" + date.format(new Date());
    this.extension = FilenameUtils.getExtension(originFileName);
    this.chatroom = chatroom;
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
        .chatroom(chatroom)
        .build();
  }

  public void updateUrl(String url) {
    this.url = url;
  }
}
