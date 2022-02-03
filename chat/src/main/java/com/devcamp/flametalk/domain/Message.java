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
  private String sender_id;

  // todo : nickname

  @Column
  private String room_id;

  @Column
  private String contents;

  @Column
  private LocalDateTime created_at;
}