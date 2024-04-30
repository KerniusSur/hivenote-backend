package com.hivenote.backend.note.dto.response;

import com.hivenote.backend.note.entity.NoteAccessType;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NoteAccessResponse {
  private UUID noteId;
  private UUID accountId;
  private NoteAccessType accessType;
  private String accountEmail;
}
