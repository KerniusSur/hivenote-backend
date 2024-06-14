package com.hivenote.backend.note.dto.response;

import com.hivenote.backend.account.dto.response.AccountPublicResponse;
import com.hivenote.backend.note.entity.NoteAccessType;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NoteAccessResponse {
  @NotNull private UUID noteId;
  @NotNull private AccountPublicResponse account;
  @NotNull private NoteAccessType accessType;
}
