package com.devcamp.flametalk.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devcamp.flametalk.dto.S3UploadedFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * S3에 정적 파일을 올리는 클래스입니다.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

  private final AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  /**
   * S3로 파일 업로드 시퀀스를 담당하는 함수입니다.
   *
   * @param multipartFile client 로부터 전달받은 파일
   * @param dirName S3에 생성된 디렉토리
   * @return 전환된 File S3로 upload
   * @throws IOException MultipartFile 을 File 로 변환할 수 없는 경우
   */
  public S3UploadedFile upload(MultipartFile multipartFile, String dirName) throws IOException {
    File uploadFile = convert(multipartFile)
        .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
    String url = upload(uploadFile, dirName);
    S3UploadedFile fileRequest = new S3UploadedFile(uploadFile.getName(), url);
    return fileRequest;
  }

  private String upload(File uploadFile, String dirName) {
    String fileName = dirName + "/" + uploadFile.getName();
    String uploadImageUrl = putS3(uploadFile, fileName);
    removeNewFile(uploadFile);
    return uploadImageUrl;
  }

  private String putS3(File uploadFile, String fileName) {
    amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
        CannedAccessControlList.PublicRead));
    return amazonS3Client.getUrl(bucket, fileName).toString();
  }

  private void removeNewFile(File targetFile) {
    if (targetFile.delete()) {
      log.info("임시 파일 삭제 성공");
    } else {
      log.info("임시 파일 삭제 실패");
    }
  }

  private Optional<File> convert(MultipartFile file) throws IOException {
    File convertFile = new File(file.getOriginalFilename());
    if (convertFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        fos.write(file.getBytes());
      }
      return Optional.of(convertFile);
    }
    return Optional.empty();
  }
}
