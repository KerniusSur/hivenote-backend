package app.hivenote.socket;

import app.hivenote.component.entity.ComponentType;
import java.io.Serializable;
import lombok.*;

public class MessageData implements Serializable {
  private String id;
  private ComponentType type;
  private Data data;

  public String getId() {
    return id;
  }

  public MessageData setId(String id) {
    this.id = id;
    return this;
  }

  public ComponentType getType() {
    return type;
  }

  public MessageData setType(ComponentType type) {
    this.type = type;
    return this;
  }

  public Data getData() {
    return data;
  }

  public MessageData setData(Data data) {
    this.data = data;
    return this;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @ToString
  @EqualsAndHashCode
  public static class Data {
    private String text;
    private Integer level;
    private Object items;
    private String title;
    private String message;
    private String alignment;
    private String caption;
    private String html;
    private String link;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @ToString
  @EqualsAndHashCode
  public static class NoteDataItem {
    private String id;
    private String type;
    private Data data;
  }
}
