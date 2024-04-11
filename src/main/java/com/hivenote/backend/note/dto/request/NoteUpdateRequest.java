package com.hivenote.backend.note.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NoteUpdateRequest {
  @NotNull private UUID id;
  @NotNull private String title;
  private String coverUrl;
  private Boolean isArchived;
  private Boolean isDeleted;
}
