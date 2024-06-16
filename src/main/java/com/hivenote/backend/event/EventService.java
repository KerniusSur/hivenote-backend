package com.hivenote.backend.event;

import static com.hivenote.backend.event.specifications.EventSpecifications.getSpecifications;

import com.hivenote.backend.account.entity.AccountEntity;
import com.hivenote.backend.event.dto.request.EventCreateRequest;
import com.hivenote.backend.event.dto.request.EventUpdateRequest;
import com.hivenote.backend.event.entity.EventEntity;
import com.hivenote.backend.event.entity.EventToNoteEntity;
import com.hivenote.backend.exception.ApiException;
import com.hivenote.backend.note.NoteService;
import com.hivenote.backend.note.entity.NoteEntity;
import com.hivenote.backend.utils.SpecificationUtil;
import io.micrometer.common.lang.Nullable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventService {
  private final EventRepository eventRepository;
  private final NoteService noteService;

  public EventService(EventRepository eventRepository, NoteService noteService) {
    this.eventRepository = eventRepository;
    this.noteService = noteService;
  }

  public EventEntity findById(UUID id) {
    return eventRepository
        .findById(id)
        .orElseThrow(() -> ApiException.notFound("Event was not found"));
  }

  public EventEntity findUserEventById(UUID id, UUID accountId) {
    return eventRepository
        .findByIdAndCreatedById(id, accountId)
        .orElseThrow(() -> ApiException.notFound("Event was not found"));
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
    if (request.getEventStart().isAfter(request.getEventEnd())) {
      throw ApiException.bad("The event start date must be before the event end date");
    }

    EventEntity entity =
        new EventEntity()
            .setTitle(request.getTitle())
            .setDescription(request.getDescription())
            .setLocation(request.getLocation())
            .setEventStart(request.getEventStart())
            .setEventEnd(request.getEventEnd())
            .setCreatedBy(new AccountEntity().setId(accountId));

    EventEntity savedEntity = eventRepository.save(entity);

    if (request.getNoteIds() != null) {
      List<EventToNoteEntity> links =
          new ArrayList<>(
              request.getNoteIds().stream()
                  .map(
                      noteId ->
                          new EventToNoteEntity()
                              .setEvent(savedEntity)
                              .setNote(noteService.findById(noteId)))
                  .toList());

      entity.setNotes(links);
    }

    return eventRepository.save(entity);
  }

  public EventEntity update(EventUpdateRequest request, UUID accountId) {
    EventEntity entity = findById(request.getId());

    if (request.getEventStart().isAfter(request.getEventEnd())) {
      throw ApiException.bad("The event start date must be before the event end date");
    }

    if (!entity.getCreatedBy().getId().equals(accountId)) {
      throw ApiException.unauthorized("You are not authorized to update this event");
    }

    List<UUID> newNoteIds = new ArrayList<>();
    List<UUID> removedNoteIds = new ArrayList<>();

    if (request.getNoteIds() != null) {
      //      newNoteIds =
      //          request.getNoteIds().stream()
      //              .filter(
      //                  (noteId) ->
      //                      entity.getNotes().stream()
      //                          .noneMatch((eventToNote) ->
      // eventToNote.getNote().getId().equals(noteId)))
      //              .toList();
      request
          .getNoteIds()
          .forEach(
              noteId -> {
                if (entity.getNotes().stream()
                    .noneMatch((eventToNote) -> eventToNote.getNote().getId().equals(noteId))) {
                  newNoteIds.add(noteId);
                }
              });

      entity
          .getNotes()
          .forEach(
              eventToNote -> {
                if (request.getNoteIds().stream()
                    .noneMatch((noteId) -> eventToNote.getNote().getId().equals(noteId))) {
                  removedNoteIds.add(eventToNote.getNote().getId());
                }
              });

      if (newNoteIds.size() > 5) {
        throw ApiException.bad("An event can only be linked to a maximum of 5 notes");
      }
    }

    entity
        .setTitle(request.getTitle())
        .setDescription(request.getDescription())
        .setLocation(request.getLocation())
        .setEventStart(request.getEventStart())
        .setEventEnd(request.getEventEnd());

    if (request.getNoteIds() != null) {
      List<EventToNoteEntity> links =
          new ArrayList<>(
              newNoteIds.stream()
                  .map(
                      noteId ->
                          new EventToNoteEntity()
                              .setEvent(entity)
                              .setNote(noteService.findById(noteId)))
                  .toList());

      entity
          .getNotes()
          .removeIf(eventToNote -> removedNoteIds.contains(eventToNote.getNote().getId()));
      entity.getNotes().addAll(links);
    }

    return eventRepository.save(entity);
  }

  public void delete(UUID id, UUID accountId) {
    EventEntity entity = findById(id);

    if (!entity.getCreatedBy().getId().equals(accountId)) {
      throw ApiException.unauthorized("You are not authorized to delete this event");
    }

    eventRepository.deleteById(id);
  }

  public void linkToNote(UUID eventId, UUID noteId, UUID accountId, @Nullable Integer order) {
    EventEntity event = findById(eventId);
    NoteEntity note = noteService.findById(noteId);

    if (!event.getCreatedBy().getId().equals(accountId)
        || note.getAccountAccess().stream()
            .map(access -> access.getAccount().getId())
            .noneMatch(id -> id.equals(accountId))) {
      throw ApiException.unauthorized("You are not authorized to link this event to a note");
    }

    if (note.getEvents().size() >= 5) {
      throw ApiException.bad("The maximum number of events linked to a note has been reached");
    }

    EventToNoteEntity eventToNoteEntity = new EventToNoteEntity().setEvent(event).setNote(note);

    event.getNotes().add(eventToNoteEntity);

    eventRepository.save(event);
  }
}
