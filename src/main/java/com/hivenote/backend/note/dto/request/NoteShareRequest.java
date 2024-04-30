package com.hivenote.backend.note.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NoteShareRequest {
  @NotNull private UUID noteId;
  @NotNull private String accessType;
  private List<String> emails;
}
