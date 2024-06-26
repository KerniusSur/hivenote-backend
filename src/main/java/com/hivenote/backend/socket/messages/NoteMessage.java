package com.hivenote.backend.socket.messages;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class NoteMessage extends Message {
  private String id;
  private String title;
  private String coverUrl;
  private List<ComponentMessage> components;
  private List<CommentMessage> comments;
}
