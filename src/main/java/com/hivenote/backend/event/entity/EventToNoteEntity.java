package com.hivenote.backend.event.entity;

import com.hivenote.backend.note.entity.NoteEntity;
import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "event_to_note")
public class EventToNoteEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", referencedColumnName = "id")
  private EventEntity event;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "note_id", referencedColumnName = "id")
  private NoteEntity note;

  public UUID getId() {
    return id;
  }

  public EventToNoteEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public EventEntity getEvent() {
    return event;
  }

  public EventToNoteEntity setEvent(EventEntity event) {
    this.event = event;
    return this;
  }

  public NoteEntity getNote() {
    return note;
  }

  public EventToNoteEntity setNote(NoteEntity note) {
    this.note = note;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EventToNoteEntity that = (EventToNoteEntity) o;
    return getId().equals(that.getId())
        && getEvent().equals(that.getEvent())
        && getNote().equals(that.getNote());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getEvent(), getNote());
  }
}
