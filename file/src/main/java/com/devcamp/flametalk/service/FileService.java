package com.devcamp.flametalk.service;

import com.devcamp.flametalk.domain.File;
import com.devcamp.flametalk.domain.FileRepository;
import com.devcamp.flametalk.dto.S3UploadedFile;
import com.devcamp.flametalk.util.S3Uploader;
import java.io.IOException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * File 서버의 Service 입니다.
 */
@RequiredArgsConstructor
@Service
public class FileService {

  private final FileRepository fileRepository;
  private final S3Uploader s3Uploader;

  /**
   * 파일을 S3에 업로드하고 DB에 해당 정보를 저장합니다.
   *
   * @param multipartFile controller 로부터 전달받은 파일
   * @return DB에 저장된 파일의 ID
   * @throws IOException File 처리 실패한 경우
   */
  @Transactional
  public Long create(MultipartFile multipartFile) throws IOException {
    S3UploadedFile file = s3Uploader.upload(multipartFile, "static");
    File savedFile = fileRepository.save(file.toFile());
    return savedFile.getId();
  }
}
