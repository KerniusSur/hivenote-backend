package app.hivenote.note.dto.request;

import app.hivenote.note.entity.NoteType;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NoteCreateRequest {
    private NoteType type;
    private String title;
    private String coverUrl;
    private Boolean isArchived;
    private Boolean isDeleted;
    private UUID notebookId;
}
