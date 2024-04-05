package app.hivenote.comment.dto.response;

import app.hivenote.account.dto.response.AccountPublicResponse;
import java.util.List;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CommentResponse {
  private UUID id;
  private String body;
  private Integer noteLine;
  private Boolean isResolved;
  private AccountPublicResponse account;
  private CommentResponse parent;
  private List<CommentResponse> children;
}
