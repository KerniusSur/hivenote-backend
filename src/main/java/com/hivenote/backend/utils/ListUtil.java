package com.hivenote.backend.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListUtil {
  public static <From, To> List<To> map(List<From> items, Function<From, To> mapper) {
    return items.stream().map(mapper).collect(Collectors.toList());
  }

  /**
   * This method casts object to an arraylist of the specified class. It returns null if object is
   * not an instance of a list.
   *
   * @param maybeList object which might contain a list.
   * @param elementClass expected class of list elements.
   * @return List of specified class or null.
   */
  public static <T> List<T> objectToList(Object maybeList, Class<T> elementClass) {
    List<T> result = null;
    if (maybeList instanceof List<?>) {
      result = new ArrayList<>();
      for (Object element : (List<?>) maybeList) {
        result.add(elementClass.cast(element));
      }
    }
    return result;
  }
}
