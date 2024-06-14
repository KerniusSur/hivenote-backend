package com.hivenote.backend.note.dto.response;

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
public class NoteMinResponse {
  @NotNull private UUID id;
  @NotNull private String title;
  @NotNull private List<NoteAccessResponse> collaborators;
}
