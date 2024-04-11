package com.hivenote.backend.account.specifications;

import com.hivenote.backend.account.entity.AccountEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecifications {
  public static Specification<AccountEntity> containsString(String searchString) {
    return (Root<AccountEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      query.distinct(true);

      // Add conditions to search by appointment properties
      Predicate condition =
          criteriaBuilder.or(
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("name")), "%" + searchString.toLowerCase() + "%"),
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("lastName")),
                  "%" + searchString.toLowerCase() + "%"),
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("email")),
                  "%" + searchString.toLowerCase() + "%"));

      return condition;
    };
  }

  public static List<Specification<AccountEntity>> getSpecifications(
      @Nullable String searchString, @Nullable Long specialistId, @Nullable Long patientId) {
    List<Specification<AccountEntity>> specifications = new ArrayList<>();

    if (searchString != null) {
      specifications.add(containsString(searchString));
    }
    //
    //    if (specialistId != null) {
    //      specifications.add(hasEqualSpecialistId(specialistId));
    //    }
    //
    //    if (patientId != null) {
    //      specifications.add(hasEqualPatientId(patientId));
    //    }

    return specifications;
  }
}
