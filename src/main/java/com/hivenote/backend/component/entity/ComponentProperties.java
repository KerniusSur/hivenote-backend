package com.hivenote.backend.component.entity;

import io.vavr.control.Either;
import java.io.Serializable;
import java.util.List;
import lombok.*;

public class ComponentProperties implements Serializable {
  private String title;
  private String text;
  private Integer level;
  private List<Either<ComponentItems, String>> items;
  private String message;
  private String alignment;
  private String caption;
  private String html;
  private String link;

  public String getTitle() {
    return title;
  }

  public ComponentProperties setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getText() {
    return text;
  }

  public ComponentProperties setText(String text) {
    this.text = text;
    return this;
  }

  public Integer getLevel() {
    return level;
  }

  public ComponentProperties setLevel(Integer level) {
    this.level = level;
    return this;
  }

  public Object getItems() {
    return items;
  }

  public ComponentProperties setItems(List<Either<ComponentItems, String>> items) {
    this.items = items;
    return this;
  }

  public List<ComponentItems> getItemsList() {
    if (items == null) return List.of();
    return items.stream().map(either -> either.isLeft() ? either.getLeft() : null).toList();
  }

  public List<String> getItemsString() {
    if (items == null) return List.of();
    return items.stream().map(either -> either.isRight() ? either.get() : null).toList();
  }

  public String getMessage() {
    return message;
  }

  public ComponentProperties setMessage(String message) {
    this.message = message;
    return this;
  }

  public String getAlignment() {
    return alignment;
  }

  public ComponentProperties setAlignment(String alignment) {
    this.alignment = alignment;
    return this;
  }

  public String getCaption() {
    return caption;
  }

  public ComponentProperties setCaption(String caption) {
    this.caption = caption;
    return this;
  }

  public String getHtml() {
    return html;
  }

  public ComponentProperties setHtml(String html) {
    this.html = html;
    return this;
  }

  public String getLink() {
    return link;
  }

  public ComponentProperties setLink(String link) {
    this.link = link;
    return this;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @ToString
  @EqualsAndHashCode
  public static class ComponentItems {
    private String text;
    private Boolean checked;
  }
}
