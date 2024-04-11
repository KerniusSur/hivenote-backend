package com.hivenote.backend.socket.messages;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class NoteRequestMessage extends Message {
  private String id;
}
