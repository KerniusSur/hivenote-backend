package com.hivenote.backend.event;

import static com.hivenote.backend.event.specifications.EventSpecifications.getSpecifications;

import com.hivenote.backend.account.entity.AccountEntity;
import com.hivenote.backend.event.entity.EventEntity;
import com.hivenote.backend.exception.ApiException;
import com.hivenote.backend.event.dto.request.EventCreateRequest;
import com.hivenote.backend.event.dto.request.EventUpdateRequest;
import com.hivenote.backend.utils.SpecificationUtil;
import io.micrometer.common.lang.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class EventService {
  private final EventRepository eventRepository;
  private final String ERROR_PREFIX = "err.event.";

  public EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public EventEntity findById(UUID id) {
    return eventRepository
        .findById(id)
        .orElseThrow(() -> ApiException.notFound(ERROR_PREFIX + "notFound"));
  }

  public EventEntity findUserEventById(UUID id, UUID accountId) {
    return eventRepository
        .findByIdAndCreatedById(id, accountId)
        .orElseThrow(() -> ApiException.notFound(ERROR_PREFIX + "notFound"));
  }

  public List<EventEntity> findAllFilteredBy(
      @Nullable UUID accountId,
      @Nullable ZonedDateTime dateFrom,
      @Nullable ZonedDateTime dateTo,
      @Nullable String searchString) {

    return eventRepository.findAll(
        SpecificationUtil.toANDSpecification(
            getSpecifications(accountId, dateFrom, dateTo, searchString)));
  }

  public List<EventEntity> findAllUserEvents(UUID accountId) {
    return eventRepository.findAllByCreatedById(accountId);
  }

  public EventEntity create(EventCreateRequest request, UUID accountId) {
    return new EventEntity()
        .setTitle(request.getTitle())
        .setDescription(request.getDescription())
        .setLocation(request.getLocation())
        .setEventStart(request.getEventStart())
        .setEventEnd(request.getEventEnd())
        .setCreatedBy(new AccountEntity().setId(accountId));
  }

  public EventEntity update(EventUpdateRequest request, UUID accountId) {
    EventEntity entity = findById(request.getId());

    if (!entity.getCreatedBy().getId().equals(accountId)) {
      throw ApiException.unauthorized(ERROR_PREFIX + "unauthorized");
    }

    return new EventEntity()
        .setId(request.getId())
        .setTitle(request.getTitle())
        .setDescription(request.getDescription())
        .setLocation(request.getLocation())
        .setEventStart(request.getEventStart())
        .setEventEnd(request.getEventEnd())
        .setCreatedBy(new AccountEntity().setId(accountId));
  }

  public void delete(UUID id, UUID accountId) {
    EventEntity entity = findById(id);

    if (!entity.getCreatedBy().getId().equals(accountId)) {
      throw ApiException.unauthorized(ERROR_PREFIX + "unauthorized");
    }

    eventRepository.deleteById(id);
  }
}
