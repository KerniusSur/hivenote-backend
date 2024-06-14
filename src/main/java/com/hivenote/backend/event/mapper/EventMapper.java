package com.hivenote.backend.event.mapper;

import com.hivenote.backend.account.mapper.AccountMapper;
import com.hivenote.backend.event.dto.response.EventResponse;
import com.hivenote.backend.event.entity.EventEntity;
import com.hivenote.backend.event.entity.EventToNoteEntity;
import com.hivenote.backend.note.mapper.NoteMapper;
import com.hivenote.backend.utils.ListUtil;
import java.util.stream.Collectors;

public class EventMapper {
  public static EventResponse toResponse(EventEntity entity) {
    return new EventResponse()
        .setId(entity.getId())
        .setTitle(entity.getTitle())
        .setDescription(entity.getDescription())
        .setLocation(entity.getLocation())
        .setEventStart(entity.getEventStart())
        .setEventEnd(entity.getEventEnd())
        .setNotes(
            ListUtil.map(
                entity.getNotes().stream()
                    .map((EventToNoteEntity::getNote))
                    .collect(Collectors.toList()),
                NoteMapper::toMinResponse))
        .setCreatedBy(AccountMapper.toPublicResponse(entity.getCreatedBy()));
  }
}
