package com.hivenote.backend.validation.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity(name = "validation")
public class ValidationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  private ValidationType type;

  private String validationValue;

  @Column(name = "value_to_validate")
  private String value;

  private Boolean used;

  private ZonedDateTime createdAt;
  private ZonedDateTime expiresAt;

  public Long getId() {
    return id;
  }

  public ValidationEntity setId(Long id) {
    this.id = id;
    return this;
  }

  public ValidationType getType() {
    return type;
  }

  public ValidationEntity setType(ValidationType type) {
    this.type = type;
    return this;
  }

  public String getValidationValue() {
    return validationValue;
  }

  public ValidationEntity setValidationValue(String validationValue) {
    this.validationValue = validationValue;
    return this;
  }

  public String getValue() {
    return value;
  }

  public ValidationEntity setValue(String value) {
    this.value = value;
    return this;
  }

  public Boolean getUsed() {
    return used;
  }

  public ValidationEntity setUsed(Boolean used) {
    this.used = used;
    return this;
  }
  
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
    
    public ValidationEntity setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    
    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }
    
    public ValidationEntity setExpiresAt(ZonedDateTime expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ValidationEntity that = (ValidationEntity) o;
    return getId().equals(that.getId())
        && getType().equals(that.getType())
        && getValidationValue().equals(that.getValidationValue())
        && getValue().equals(that.getValue())
        && getUsed().equals(that.getUsed()
            && getCreatedAt().equals(that.getCreatedAt())
            && getExpiresAt().equals(that.getExpiresAt()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getType(), getValidationValue(), getValue(), getUsed(), getCreatedAt(), getExpiresAt());
  }
}
