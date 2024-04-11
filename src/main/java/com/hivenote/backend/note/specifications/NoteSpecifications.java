package com.hivenote.backend.note.specifications;

import com.hivenote.backend.note.entity.NoteEntity;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.AccessType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class NoteSpecifications {
  public static Specification<NoteEntity> hasAccountAccess(UUID accountId, AccessType accessType) {
    return (Root<NoteEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      query.distinct(true);
      root.join("accountAccess");
      root.join("accountAccess").join("account");

      return criteriaBuilder.and(
          criteriaBuilder.equal(root.get("accountAccess").get("account").get("id"), accountId),
          criteriaBuilder.equal(root.get("accountAccess").get("accessType"), accessType));
    };
  }

  public static Specification<NoteEntity> containsString(String searchString) {
    return (Root<NoteEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      query.distinct(true);
      root.join("components");
      root.join("components").join("properties");

      return criteriaBuilder.or(
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("title")), "%" + searchString.toLowerCase() + "%"),
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("components").get("properties").get("text")),
              "%" + searchString.toLowerCase() + "%"),
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("components").get("properties").get("title")),
              "%" + searchString.toLowerCase() + "%"),
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("components").get("properties").get("message")),
              "%" + searchString.toLowerCase() + "%"));
    };
  }

  public static Specification<NoteEntity> isArchived(Boolean isArchived) {
    return (Root<NoteEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      return criteriaBuilder.equal(root.get("isArchived"), isArchived);
    };
  }

  public static Specification<NoteEntity> isDeleted(Boolean isDeleted) {
    return (Root<NoteEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      return criteriaBuilder.equal(root.get("isDeleted"), isDeleted);
    };
  }

  public static List<Specification<NoteEntity>> getSpecifications(
      @Nullable UUID accountId,
      @Nullable AccessType accessType,
      @Nullable String searchString,
      @Nullable Boolean isArchived,
      @Nullable Boolean isDeleted) {
    List<Specification<NoteEntity>> specifications = new ArrayList<>();

    if (accountId != null && accessType != null) {
      specifications.add(hasAccountAccess(accountId, accessType));
    }

    if (searchString != null) {
      specifications.add(containsString(searchString));
    }

    if (isArchived != null) {
      specifications.add(isArchived(isArchived));
    }

    if (isDeleted != null) {
      specifications.add(isDeleted(isDeleted));
    }

    return specifications;
  }
}
