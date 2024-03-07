package app.hivenote.notebook.dto.response;

import app.hivenote.account.dto.response.AccountPublicResponse;

import java.util.List;
import java.util.UUID;

import app.hivenote.note.dto.response.NoteMinResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NotebookResponse {
  private UUID id;
  private String name;
  private Boolean isArchived;
  private Boolean isDeleted;
  private AccountPublicResponse account;
  private List<NoteMinResponse> notes;
}
