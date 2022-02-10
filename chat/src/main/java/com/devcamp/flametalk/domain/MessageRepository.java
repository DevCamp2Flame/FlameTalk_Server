package com.devcamp.flametalk.domain;

import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

/**
 * Message 엔티티의 저장소입니다.
 */
public interface MessageRepository extends CassandraRepository<Message, String> {

  @Query("select * from message where room_id = ?0 allow filtering")
  List<Message> findByRoomId(String roomId);

  // todo: Paging
  @Query("select * from message where room_id = ?0 and message_id > ?1 allow filtering")
  List<Message> findByRoomIdAndMessageId(String roomId, String lastReadMessageId);
}