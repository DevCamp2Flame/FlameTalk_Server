package com.devcamp.flametalk.service;

import com.devcamp.flametalk.domain.Chatroom;
import com.devcamp.flametalk.domain.ChatroomRepository;
import com.devcamp.flametalk.domain.File;
import com.devcamp.flametalk.domain.FileRepository;
import com.devcamp.flametalk.domain.ResponseMessage;
import com.devcamp.flametalk.dto.FileDetailResponse;
import com.devcamp.flametalk.dto.S3UploadedFile;
import com.devcamp.flametalk.dto.SimpleResponse;
import com.devcamp.flametalk.dto.SingleResponse;
import com.devcamp.flametalk.util.S3Util;
import java.io.IOException;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
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
  private final ChatroomRepository chatroomRepository;
  private final S3Util s3Util;

  /**
   * 파일을 S3에 업로드하고 DB에 해당 정보를 저장합니다.
   *
   * @param file       controller 로부터 전달받은 파일
   * @param chatroomId controller 로부터 전달받은 채팅방 id
   * @return DB에 저장된 파일 ID
   * @throws IOException File 처리 실패한 경우
   */
  @Transactional
  public Long create(MultipartFile file, String chatroomId) throws IOException {
    Optional<Chatroom> chatroom = Optional.ofNullable(chatroomId)
        .map(id -> chatroomRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅방입니다.")));
    String dirName = chatroom.map(room -> "chatroom/" + room.getId()).orElse("profile");

    S3UploadedFile uploadFile = new S3UploadedFile(file,
        chatroom.orElse(null));
    uploadFile.updateUrl(s3Util.upload(uploadFile, dirName));

    File savedFile = fileRepository.save(uploadFile.toFile());
    return savedFile.getId();
  }

  /**
   * DB에 저장된 파일의 상세정보를 조회합니다.
   *
   * @param id  DB에 저장된 파일 ID
   * @param <T> FileDetailResponse
   * @return 파일 조회 결과
   */
  public <T> SingleResponse<T> findById(Long id) {
    File file = fileRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 파일입니다."));

    FileDetailResponse fileDetail = new FileDetailResponse(file);
    SingleResponse singleResponse = new SingleResponse(fileDetail);
    singleResponse.setSuccessResponse("파일 조회 성공");
    return singleResponse;
  }

  /**
   * id에 해당하는 파일을 DB 에서 삭제하고 해당 파일을 S3에서도 삭제합니다.
   *
   * @param id DB에 저장된 파일 ID
   * @return 파일 삭제 성공 여부
   */
  public SimpleResponse deleteById(Long id) {
    File file = fileRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 파일입니다."));
    fileRepository.deleteById(id);

    String fileKey = Optional.ofNullable(file.getChatroom().getId())
        .map(roomId -> "chatroom/" + roomId + "/")
        .orElse("profile/") + file.getTitle() + "." + file.getExtension();
    s3Util.deleteS3File(fileKey);

    SimpleResponse response = new SimpleResponse();
    response.success(ResponseMessage.FILE_DELETE_SUCCESS);
    return response;
  }
}
