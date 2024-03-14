package app.hivenote.note.dto.request;

import app.hivenote.note.entity.NoteType;
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
  private NoteType type;
  @NotNull private String title;
  private String coverUrl;
  private Boolean isArchived;
  private Boolean isDeleted;
  private UUID notebookId;
}
