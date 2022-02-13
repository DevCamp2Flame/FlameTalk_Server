package com.devcamp.flametalk.chatroom.domain.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 엔티티 공통으로 필요한 생성날짜와 갱신날짜를 자동으로 주입하는 클래스입니다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {

  @CreatedDate
  @Column(nullable = false, columnDefinition = "DATETIME")
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(nullable = false, columnDefinition = "DATETIME")
  private LocalDateTime updatedAt;
}
