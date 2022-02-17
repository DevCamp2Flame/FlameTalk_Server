package com.devcamp.flametalk.controller;

import com.devcamp.flametalk.domain.FileResponseMessage;
import com.devcamp.flametalk.dto.ChatroomFilesResponse;
import com.devcamp.flametalk.dto.CommonResponse;
import com.devcamp.flametalk.dto.FileDetailResponse;
import com.devcamp.flametalk.dto.SingleDataResponse;
import com.devcamp.flametalk.service.FileService;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<SingleDataResponse<FileDetailResponse>> create(
      @RequestParam(name = "file") MultipartFile file,
      @RequestParam(required = false, name = "chatroomId") String chatroomId)
      throws IOException {
    FileDetailResponse fileDetail = fileService.create(file, chatroomId);
    log.info("[File Saved] " + fileDetail.toString());

    //TODO: Exception 처리

    SingleDataResponse<FileDetailResponse> response = new SingleDataResponse<>(fileDetail);
    response.success(FileResponseMessage.FILE_CREATE_SUCCESS.getMessage());
    return ResponseEntity.created(URI.create("api/file/" + fileDetail.getFileId())).body(response);
  }

  /**
   * 요청받은 파일에 대한 상세 정보를 조회합니다.
   *
   * @param fileId DB에 저장된 파일 ID
   * @return 파일 상세 정보
   */
  @GetMapping("/{fileId}")
  public ResponseEntity<SingleDataResponse<FileDetailResponse>> findById(
      @PathVariable Long fileId) {
    FileDetailResponse fileDetail = fileService.findById(fileId);
    log.info("[File Searched]" + fileDetail.toString());

    SingleDataResponse<FileDetailResponse> response = new SingleDataResponse<>(fileDetail);
    response.success(FileResponseMessage.FILE_DETAIL_SUCCESS.getMessage());
    return ResponseEntity.ok().body(response);
  }

  /**
   * 채팅방에 업로드된 파일 정보 리스트를 조회합니다.
   *
   * @param chatroomId 채팅방 id
   * @return 업로드된 파일 정보에 대한 리스트
   */
  @GetMapping("/chatroom/{chatroomId}")
  public ResponseEntity<SingleDataResponse<List<ChatroomFilesResponse>>> findAllFilesByChatroomId(
      @PathVariable String chatroomId) {
    List<ChatroomFilesResponse> chatroomFiles = fileService
        .findAllFilesByChatroomId(chatroomId);
    SingleDataResponse<List<ChatroomFilesResponse>> response = new SingleDataResponse<>(chatroomFiles);
    response.success(FileResponseMessage.CHATROOM_FILES_SUCCESS.getMessage());
    log.info("find all files uploaded in chatroom {}", chatroomId);
    return ResponseEntity.ok().body(response);
  }

  /**
   * 요청받은 파일을 삭제합니다.
   *
   * @param fileId DB에 저장된 파일 ID
   * @return 삭제 결과에 따른 응답 정보
   */
  @DeleteMapping("/{fileId}")
  public ResponseEntity<CommonResponse> deleteById(@PathVariable Long fileId) {
    fileService.deleteById(fileId);
    log.info("[File Deleted] fileId: {}", fileId);

    CommonResponse response = new CommonResponse();
    response.success(FileResponseMessage.FILE_DELETE_SUCCESS.getMessage());
    return ResponseEntity.ok().body(response);
  }

  /**
   * 요청받은 url에 해당하는 파일을 삭제합니다.
   *
   * @param fileUrl DB에 저장된 파일 ID
   * @return 삭제 결과에 따른 응답 정보
   */
  @DeleteMapping
  public ResponseEntity<CommonResponse> deleteByUrl(@RequestParam String fileUrl) {
    fileService.deleteByUrl(fileUrl);
    log.info("[File Deleted] fileId: {}", fileUrl);

    CommonResponse response = new CommonResponse();
    response.success(FileResponseMessage.FILE_DELETE_SUCCESS.getMessage());
    return ResponseEntity.ok().body(response);
  }
}
