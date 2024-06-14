package com.hivenote.backend.validation;

import com.hivenote.backend.exception.ApiException;
import com.hivenote.backend.validation.entity.ValidationEntity;
import com.hivenote.backend.validation.entity.ValidationType;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
  private final ValidationRepository repository;

  public ValidationService(ValidationRepository repository) {
    this.repository = repository;
  }

  public ValidationEntity findBy(String validationValue, String value) {
    if (!existsBy(validationValue, value)) {
      throw ApiException.notFound("Validation was not found")
          .addLabel("validationValue", validationValue)
          .addLabel("value", value);
    }
    return repository.findByValidationValueAndValue(validationValue, value).orElse(null);
  }

  public List<ValidationEntity> findAllByValueToValidate(String valueToValidate) {
    return repository.findAllByValue(valueToValidate);
  }

  public ValidationEntity create(
      ValidationType type, String validationValue, String value, ZonedDateTime expirationDate) {
    ValidationEntity entity =
        new ValidationEntity()
            .setType(type)
            .setValidationValue(validationValue)
            .setValue(value)
            .setExpiresAt(expirationDate)
            .setUsed(false);
    return repository.save(entity);
  }

  public void setUsedToTrue(String validationValue, String value) {
    ValidationEntity entity = findBy(validationValue, value);
    entity.setUsed(true);
  }

  public Boolean existsBy(String validationValue, String value, Boolean used) {
    return repository.existsByValidationValueAndValueAndUsed(validationValue, value, used);
  }

  public Boolean existsBy(String validationValue, String value) {
    return repository.existsByValidationValueAndValue(validationValue, value);
  }
}
