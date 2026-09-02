package com.jusmore.trading.platform.common.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private T id;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BaseEntity<?> that = (BaseEntity<?>) o;
    return id != null && Objects.equals(id, that.id);
  }

  public int hashCode() {
    return getClass().hashCode();
  }

}
