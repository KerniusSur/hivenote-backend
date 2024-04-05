package app.hivenote.note.dto.response;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NoteMinResponse {
    private UUID id;
    private String title;
}
