package com.devcamp.flametalk.service;

import com.devcamp.flametalk.domain.Chatroom;
import com.devcamp.flametalk.domain.ChatroomRepository;
import com.devcamp.flametalk.domain.File;
import com.devcamp.flametalk.domain.FileRepository;
import com.devcamp.flametalk.dto.S3UploadedFile;
import com.devcamp.flametalk.util.S3Uploader;
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
  private final S3Uploader s3Uploader;

  /**
   * 파일을 S3에 업로드하고 DB에 해당 정보를 저장합니다.
   *
   * @param file       controller 로부터 전달받은 파일
   * @param chatroomId controller 로부터 전달받은 채팅방 id
   * @return DB에 저장된 파일의 ID
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
    uploadFile.updateUrl(s3Uploader.upload(uploadFile, dirName));

    File savedFile = fileRepository.save(uploadFile.toFile());
    return savedFile.getId();
  }
}
