package app.hivenote.component.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;

public class ComponentProperties implements Serializable {
  private String title;
  private Boolean isChecked;
  private String href;
  private LinkedList<UUID> content;
  private UUID parent;

  public String getTitle() {
    return title;
  }

  public ComponentProperties setTitle(String title) {
    this.title = title;
    return this;
  }

  public Boolean getIsChecked() {
    return isChecked;
  }

  public ComponentProperties setIsChecked(Boolean isChecked) {
    this.isChecked = isChecked;
    return this;
  }

  public String getHref() {
    return href;
  }

  public ComponentProperties setHref(String href) {
    this.href = href;
    return this;
  }

  public LinkedList<UUID> getContent() {
    return content;
  }

  public ComponentProperties setContent(LinkedList<UUID> content) {
    this.content = content;
    return this;
  }

  public UUID getParent() {
    return parent;
  }

  public ComponentProperties setParent(UUID parent) {
    this.parent = parent;
    return this;
  }
}
