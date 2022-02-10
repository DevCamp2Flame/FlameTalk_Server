package com.devcamp.flametalk.domain;

import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

public interface MessageRepository extends CassandraRepository<Message, String> {

  // todo: Paging
  @Query(value = "select * from message where room_id = ?0 and message_id > ?1")
  List<Message> findByRoomIdAndMessageId(String roomId, String lastReadMessageId);
}