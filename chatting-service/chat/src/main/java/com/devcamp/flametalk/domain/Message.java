package com.devcamp.flametalk.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Message 엔티티 입니다.
 */
@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("message")
public class Message {

  @Column
  @PrimaryKey
  private String message_id;
  
  @Column
  private String message_type;

  @Column
  private String sender_id;

  @Column
  private String nickname;

  @Column
  private String room_id;

  @Column
  private String contents;

  @Column
  private String file_url;

  @Column
  private LocalDateTime created_at;
}