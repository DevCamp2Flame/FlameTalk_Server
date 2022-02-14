package com.devcamp.flametalk.chatroom.domain.file.domain;

import com.devcamp.flametalk.chatroom.domain.chatroom.domain.Chatroom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * File 엔티티의 저장소 Repository 입니다.
 */
public interface FileRepository extends JpaRepository<File, Long> {

  List<File> findAllByChatroom(Chatroom chatroom);
}
