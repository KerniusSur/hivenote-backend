package com.hivenote.backend.socket.messages;

import com.hivenote.backend.socket.MessageType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Message {
  private MessageType type;
  private String room;
}
