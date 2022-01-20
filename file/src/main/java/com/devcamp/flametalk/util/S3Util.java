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
public class S3Util {

  private final AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  /**
   * S3로 파일 업로드 시퀀스를 담당하는 함수입니다.
   *
   * @param file    s3에 업로드 되어야하는 파일 정보
   * @param dirName s3 폴더링을 위한 디렉토리명
   * @return s3에 업로드된 파일의 url
   * @throws IOException TODO::예외처리
   */
  public String upload(S3UploadedFile file, String dirName) throws IOException {
    File uploadFile = convert(file.getMultipartFile())
        .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
    String s3FileKey = dirName + "/" + file.getTitle() + "." + file.getExtension();
    return upload(uploadFile, s3FileKey);
  }

  private String upload(File uploadFile, String s3FileKey) {
    String uploadImageUrl = putS3(uploadFile, s3FileKey);
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

  public void deleteS3File(String key) {
    amazonS3Client.deleteObject(bucket, key);
  }
}
