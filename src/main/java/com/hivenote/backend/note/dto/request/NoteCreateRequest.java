package com.hivenote.backend.note.dto.request;

import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NoteCreateRequest {
  private String title;
  private String coverUrl;
  private Boolean isArchived;
  private Boolean isDeleted;
  private UUID parentId;
}
