package com.hivenote.backend.event.specifications;

import com.hivenote.backend.event.entity.EventEntity;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecifications {
  public static Specification<EventEntity> hasEqualCreatedById(UUID accountId) {
    return (root, query, cb) -> cb.equal(root.get("createdBy").get("id"), accountId);
  }

  public static Specification<EventEntity> existsInTimeRange(
      UUID accountId, ZonedDateTime dateFrom, ZonedDateTime dateTo) {
    return (root, query, cb) ->
        cb.and(
            cb.equal(root.get("createdBy").get("id"), accountId),
            cb.or(
                cb.between(root.get("eventStart"), dateFrom, dateTo),
                cb.between(root.get("eventEnd"), dateFrom, dateTo)));
  }

  public static Specification<EventEntity> dateFrom(ZonedDateTime dateFrom) {
    return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("eventStart"), dateFrom);
  }

  public static Specification<EventEntity> dateTo(ZonedDateTime dateTo) {
    return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("eventEnd"), dateTo);
  }

  public static Specification<EventEntity> containsString(String searchString) {
    return (Root<EventEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      Predicate condition =
          criteriaBuilder.or(
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("title").get("name")),
                  "%" + searchString.toLowerCase() + "%"),
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("description").get("lastName")),
                  "%" + searchString.toLowerCase() + "%"),
              criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("location").get("name")),
                  "%" + searchString.toLowerCase() + "%"));

      return condition;
    };
  }

  public static List<Specification<EventEntity>> getSpecifications(
      @Nullable UUID accountId,
      @Nullable ZonedDateTime dateFrom,
      @Nullable ZonedDateTime dateTo,
      @Nullable String searchString) {
    List<Specification<EventEntity>> specifications = new ArrayList<>();

    if (accountId != null) {
      specifications.add(hasEqualCreatedById(accountId));
    }

    if (dateFrom != null) {
      specifications.add(dateFrom(dateFrom));
    }

    if (dateTo != null) {
      specifications.add(dateTo(dateTo));
    }

    if (searchString != null) {
      specifications.add(containsString(searchString));
    }

    return specifications;
  }
}
