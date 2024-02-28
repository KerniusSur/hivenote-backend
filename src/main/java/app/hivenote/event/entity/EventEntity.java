package app.hivenote.event.entity;

import app.hivenote.account.entity.AccountEntity;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "event")
public class EventEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String title;
  private String description;

  @Column(name = "event_start")
  private ZonedDateTime eventStart;

  @Column(name = "event_end")
  private ZonedDateTime eventEnd;

  @ManyToOne
  @JoinColumn(name = "account_id", referencedColumnName = "id")
  private AccountEntity createdBy;

  public UUID getId() {
    return id;
  }

  public EventEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public EventEntity setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public EventEntity setDescription(String description) {
    this.description = description;
    return this;
  }

  public ZonedDateTime getEventStart() {
    return eventStart;
  }

  public EventEntity setEventStart(ZonedDateTime eventStart) {
    this.eventStart = eventStart;
    return this;
  }

  public ZonedDateTime getEventEnd() {
    return eventEnd;
  }

  public EventEntity setEventEnd(ZonedDateTime eventEnd) {
    this.eventEnd = eventEnd;
    return this;
  }

  public AccountEntity getCreatedBy() {
    return createdBy;
  }

  public EventEntity setCreatedBy(AccountEntity createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EventEntity that = (EventEntity) o;
    return getId().equals(that.getId())
        && getTitle().equals(that.getTitle())
        && getDescription().equals(that.getDescription())
        && getEventStart().equals(that.getEventStart())
        && getEventEnd().equals(that.getEventEnd())
        && getCreatedBy().equals(that.getCreatedBy());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(), getTitle(), getDescription(), getEventStart(), getEventEnd(), getCreatedBy());
  }
}
