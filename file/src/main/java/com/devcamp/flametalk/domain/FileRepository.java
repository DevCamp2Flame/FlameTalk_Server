package com.devcamp.flametalk.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * File 엔티티의 저장소 Repository 입니다.
 */
public interface FileRepository extends JpaRepository<File, Long> {

  Optional<File> findByUrl(String url);

  List<File> findAllByChatroom(Chatroom chatroom);
}
