package com.hivenote.backend.utils;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtil {
  public static <T> Specification<T> toANDSpecification(List<Specification<T>> specifications) {
    if (specifications.size() == 1) {
      return specifications.get(0);
    } else {
      Specification<T> finalSpecification = null;
      for (int i = 0; i < specifications.size(); i++) {
        if (i == 0) {
          finalSpecification = specifications.get(i);
        } else {
          finalSpecification = finalSpecification.and(specifications.get(i));
        }
      }
      return finalSpecification;
    }
  }

  public static Sort sortByIdAsc() {
    return Sort.by(Sort.Direction.ASC, "id");
  }
}
