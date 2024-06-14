package com.hivenote.backend.note.dto.response;

import com.hivenote.backend.comment.dto.response.CommentResponse;
import com.hivenote.backend.component.dto.response.ComponentResponse;
import com.hivenote.backend.event.dto.response.EventResponse;
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
public class NoteResponse {
  @NotNull private UUID id;
  private String title;
  private String coverUrl;
  @NotNull private Boolean isArchived;
  @NotNull private Boolean isDeleted;
  private List<ComponentResponse> components;
  @NotNull private List<NoteAccessResponse> collaborators;
  private List<CommentResponse> comments;
  private List<NoteResponse> children;
  private List<EventResponse> events;
}
