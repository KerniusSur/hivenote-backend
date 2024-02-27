package app.hivenote.validation;

import app.hivenote.validation.entity.ValidationEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationRepository extends JpaRepository<ValidationEntity, Long> {
  Boolean existsByValidationValueAndValue(String validationValue, String value);

  Boolean existsByValidationValueAndValueAndUsed(
      String validationValue, String value, Boolean used);

  Optional<ValidationEntity> findByValidationValueAndValue(String validationValue, String value);

  List<ValidationEntity> findAllByValue(String valueToValidate);
}
