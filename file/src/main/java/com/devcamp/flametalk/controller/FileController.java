package com.devcamp.flametalk.controller;

import com.devcamp.flametalk.service.FileService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * File 서버의 컨트롤러입니다.
 */
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/file")
@RestController
public class FileController {

  private final FileService fileService;

  /**
   * 파일 정보를 서버에 등록합니다.
   *
   * @param file 사용자로부터 전달받은 파일
   * @return 함수 성공 여부
   * @throws IOException File 처리 실패한 경우
   */
  @PostMapping
  public ResponseEntity<Void> create(@RequestPart MultipartFile file,
      @RequestPart(required = false) String chatroomId) throws IOException {
    Long id = fileService.create(file, chatroomId);
    log.info("[File Saved] " + id);

    //TODO: Exception 처리

    return ResponseEntity.ok().build();
  }
}
