package com.hivenote.backend.note.dto.response;

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
