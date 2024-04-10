package app.hivenote.event.mapper;

import app.hivenote.account.mapper.AccountMapper;
import app.hivenote.event.dto.response.EventResponse;
import app.hivenote.event.entity.EventEntity;

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
