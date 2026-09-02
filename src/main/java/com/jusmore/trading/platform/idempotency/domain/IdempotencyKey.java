package com.jusmore.trading.platform.idempotency.domain;

import com.jusmore.trading.platform.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
      name = "idempotency_records",
      uniqueConstraints = {
            @UniqueConstraint(
                  columnNames = {"idempotency_key", "user_id"}
            )
      },
      indexes = {
            @Index(
                  name = "idx_idempotency_key_user_id",
                  columnList = "idempotency_key, user_id"
            )
      }
)
@Getter
@NoArgsConstructor
public class IdempotencyKey extends BaseEntity {

  @Setter
  @Column(name = "idempotency_key", nullable = false, length = 100)
  private String idempotencyKey;

  @Setter
  @Column(name = "user_id", nullable = false, length = 100)
  private String userId;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "locked_at", nullable = false)
  private LocalDateTime lockedAt;

  @PrePersist
  public void prePersist() {
    createdAt = LocalDateTime.now();
    lockedAt = createdAt;
  }
}
