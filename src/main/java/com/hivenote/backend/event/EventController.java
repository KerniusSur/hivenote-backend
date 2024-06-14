package com.hivenote.backend.event;

import com.hivenote.backend.auth.entity.AuthenticatedProfile;
import com.hivenote.backend.event.dto.request.EventCreateRequest;
import com.hivenote.backend.event.dto.request.EventUpdateRequest;
import com.hivenote.backend.event.dto.response.EventResponse;
import com.hivenote.backend.event.entity.EventEntity;
import com.hivenote.backend.event.mapper.EventMapper;
import com.hivenote.backend.utils.ListUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@Tag(name = "events")
@Transactional
@RestController
@RequestMapping("/api/v1/user/events")
public class EventController {
  private final EventService eventService;

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping("/{id}")
  public EventResponse findById(@PathVariable String id, AuthenticatedProfile profile) {
    return EventMapper.toResponse(
        eventService.findUserEventById(UUID.fromString(id), profile.getId()));
  }

  @GetMapping("/all")
  public List<EventResponse> findAllUserEvents(AuthenticatedProfile profile) {
    return ListUtil.map(eventService.findAllUserEvents(profile.getId()), EventMapper::toResponse);
  }

  @GetMapping("/filter")
  public List<EventResponse> findAllUserEventsFilteredBy(
      @RequestParam(required = false, name = "dateFrom")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          ZonedDateTime dateFrom,
      @RequestParam(required = false, name = "dateTo")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          ZonedDateTime dateTo,
      @RequestParam(required = false, name = "searchString") String searchString,
      AuthenticatedProfile profile) {

    return ListUtil.map(
        eventService.findAllFilteredBy(profile.getId(), dateFrom, dateTo, searchString),
        EventMapper::toResponse);
  }

  @PostMapping
  public EventResponse create(
      @RequestBody @Valid EventCreateRequest request, AuthenticatedProfile profile) {
    EventEntity event = eventService.create(request, profile.getId());
    return EventMapper.toResponse(event);
  }

  @PutMapping
  public EventResponse update(
      @RequestBody @Valid EventUpdateRequest request, AuthenticatedProfile profile) {
    EventEntity event = eventService.update(request, profile.getId());
    return EventMapper.toResponse(event);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id, AuthenticatedProfile profile) {
    eventService.delete(UUID.fromString(id), profile.getId());
  }

  @PutMapping("/{id}/link/note/{noteId}")
  public void linkToNote(
      @PathVariable String id, @PathVariable String noteId, AuthenticatedProfile profile) {
    eventService.linkToNote(UUID.fromString(id), UUID.fromString(noteId), profile.getId(), null);
  }
}
