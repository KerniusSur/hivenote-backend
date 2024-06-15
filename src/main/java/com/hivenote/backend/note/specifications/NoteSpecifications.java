package com.hivenote.backend.note.specifications;

import com.hivenote.backend.component.entity.ComponentEntity;
import com.hivenote.backend.note.entity.NoteAccessType;
import com.hivenote.backend.note.entity.NoteEntity;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class NoteSpecifications {
  public static Specification<NoteEntity> hasAccountId(UUID accountId) {
    return (root, query, cb) ->
        cb.equal(root.join("accountAccess").get("account").get("id"), accountId);
  }

  public static Specification<NoteEntity> hasAccessType(NoteAccessType accessType) {
    return (root, query, cb) -> cb.equal(root.join("accountAccess").get("accessType"), accessType);
  }

  public static Specification<NoteEntity> hasEitherAccessType(
      NoteAccessType accessType, NoteAccessType accessType2) {
    return (root, query, cb) -> {
      Predicate accessTypePredicate =
          cb.equal(root.join("accountAccess").get("accessType"), accessType);
      Predicate accessType2Predicate =
          cb.equal(root.join("accountAccess").get("accessType"), accessType2);
      return cb.or(accessTypePredicate, accessType2Predicate);
    };
  }

  public static Specification<NoteEntity> containsSearchString(String searchString) {
    return (root, query, criteriaBuilder) -> {
      Predicate notesTitlePredicate =
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get("title")), "%" + searchString.toLowerCase() + "%");

      Join<NoteEntity, ComponentEntity> componentsJoin = root.join("components", JoinType.LEFT);
      Expression<String> componentTitleExpr =
          criteriaBuilder.function(
              "jsonb_extract_path_text",
              String.class,
              componentsJoin.get("properties"),
              criteriaBuilder.literal("title"));
      Expression<String> componentTextExpr =
          criteriaBuilder.function(
              "jsonb_extract_path_text",
              String.class,
              componentsJoin.get("properties"),
              criteriaBuilder.literal("text"));

      Predicate componentsTitlePredicate =
          criteriaBuilder.like(
              criteriaBuilder.lower(componentTitleExpr), "%" + searchString.toLowerCase() + "%");
      Predicate componentsTextPredicate =
          criteriaBuilder.like(
              criteriaBuilder.lower(componentTextExpr), "%" + searchString.toLowerCase() + "%");

      return criteriaBuilder.or(
          notesTitlePredicate, componentsTitlePredicate, componentsTextPredicate);
    };
  }

  public static Specification<NoteEntity> isArchived(boolean isArchived) {
    return (root, query, cb) -> cb.equal(root.get("isArchived"), isArchived);
  }

  public static Specification<NoteEntity> isDeleted(boolean isDeleted) {
    return (root, query, cb) -> cb.equal(root.get("isDeleted"), isDeleted);
  }

  public static List<Specification<NoteEntity>> getSpecifications(
      UUID accountId,
      NoteAccessType accessType,
      NoteAccessType accessType2,
      String searchString,
      Boolean isArchived,
      Boolean isDeleted) {
    List<Specification<NoteEntity>> specifications = new ArrayList<>();

    if (accountId != null) {
      specifications.add(hasAccountId(accountId));
    }

    if (accessType != null && accessType2 != null) {
      specifications.add(hasEitherAccessType(accessType, accessType2));
    } else if (accessType != null) {
      specifications.add(hasAccessType(accessType));
    } else if (accessType2 != null) {
      specifications.add(hasAccessType(accessType2));
    }

    if (accessType2 != null) {
      specifications.add(hasAccessType(accessType2));
    }

    if (searchString != null) {
      specifications.add(containsSearchString(searchString));
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
