package app.hivenote.note.dto.response;

import app.hivenote.account.dto.response.AccountPublicResponse;
import app.hivenote.comment.dto.response.CommentResponse;
import app.hivenote.component.dto.response.ComponentResponse;
import app.hivenote.note.entity.NoteType;
import java.util.List;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NoteResponse {
  private UUID id;
  private NoteType type;
  private String title;
  private String coverUrl;
  private Boolean isArchived;
  private Boolean isDeleted;
  private List<ComponentResponse> components;
  private List<AccountPublicResponse> collaborators;
  private List<CommentResponse> comments;
}
