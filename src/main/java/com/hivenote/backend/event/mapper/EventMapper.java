package com.hivenote.backend.event.mapper;

import com.hivenote.backend.account.mapper.AccountMapper;
import com.hivenote.backend.event.entity.EventEntity;
import com.hivenote.backend.event.dto.response.EventResponse;

public class EventMapper {
  public static EventResponse toResponse(EventEntity entity) {
    return new EventResponse()
        .setId(entity.getId())
        .setTitle(entity.getTitle())
        .setDescription(entity.getDescription())
        .setLocation(entity.getLocation())
        .setEventStart(entity.getEventStart())
        .setEventEnd(entity.getEventEnd())
        .setCreatedBy(AccountMapper.toPublicResponse(entity.getCreatedBy()));
  }
}
