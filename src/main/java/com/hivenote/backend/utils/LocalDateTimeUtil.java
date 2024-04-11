package com.hivenote.backend.utils;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class LocalDateTimeUtil {
  public static boolean isBeforeOrEqual(
      @NotNull LocalDateTime date1, @NotNull LocalDateTime date2) {
    return date1.isBefore(date2) || date1.isEqual(date2);
  }

  public static boolean isColliding(
      @NotNull LocalDateTime start1,
      @NotNull LocalDateTime end1,
      @NotNull LocalDateTime start2,
      @NotNull LocalDateTime end2) {
    // Two collision possibilities (using math symbols):
    // start1 <= start2 < end1 <= end2   <- end1 is inside
    // start2 <= start1 < end2 <= end1   <- start1 is inside
    return (isBeforeOrEqual(start1, start2) && start2.isBefore(end1) && isBeforeOrEqual(end1, end2))
        || (isBeforeOrEqual(start2, start1)
            && start1.isBefore(end2)
            && isBeforeOrEqual(end2, end1));
  }
}
