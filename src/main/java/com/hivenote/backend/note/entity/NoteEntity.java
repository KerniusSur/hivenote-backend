package com.hivenote.backend.note.entity;

import com.hivenote.backend.comment.entity.CommentEntity;
import com.hivenote.backend.component.entity.ComponentEntity;
import com.hivenote.backend.event.entity.EventToNoteEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "note")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NoteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String title;
  private String coverUrl;
  private Boolean isArchived;
  private Boolean isDeleted;

  @OneToMany(
      mappedBy = "note",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true)
  private List<ComponentEntity> components;

  @OneToMany(
      mappedBy = "note",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<NoteAccessEntity> accountAccess = new ArrayList<>();

  @OneToMany(
      mappedBy = "note",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<CommentEntity> comments = new ArrayList<>();

  @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<EventToNoteEntity> events = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "parent_id", referencedColumnName = "id")
  private NoteEntity parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<NoteEntity> children = new ArrayList<>();

  @PrePersist
  public void init() {
    if (this.isArchived == null) {
      this.isArchived = false;
    }
    if (this.isDeleted == null) {
      this.isDeleted = false;
    }
  }

  public UUID getId() {
    return id;
  }

  public NoteEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public NoteEntity setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public NoteEntity setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
    return this;
  }

  public Boolean getIsArchived() {
    return this.isArchived;
  }

  public NoteEntity setIsArchived(Boolean archived) {
    this.isArchived = archived;
    return this;
  }

  public Boolean getIsDeleted() {
    return this.isDeleted;
  }

  public NoteEntity setIsDeleted(Boolean deleted) {
    this.isDeleted = deleted;
    return this;
  }

  public List<ComponentEntity> getComponents() {
    return components;
  }

  public NoteEntity setComponents(List<ComponentEntity> components) {
    this.components = components;
    return this;
  }

  public List<NoteAccessEntity> getAccountAccess() {
    return accountAccess;
  }

  public NoteEntity setAccountAccess(List<NoteAccessEntity> accountAccess) {
    this.accountAccess = accountAccess;
    return this;
  }

  public List<CommentEntity> getComments() {
    return comments;
  }

  public NoteEntity setComments(List<CommentEntity> comments) {
    this.comments = comments;
    return this;
  }

  public List<EventToNoteEntity> getEvents() {
    return events;
  }

  public NoteEntity setEvents(List<EventToNoteEntity> events) {
    this.events = events;
    return this;
  }

  public NoteEntity getParent() {
    return parent;
  }

  public NoteEntity setParent(NoteEntity parent) {
    this.parent = parent;
    return this;
  }

  public List<NoteEntity> getChildren() {
    return children;
  }

  public NoteEntity setChildren(List<NoteEntity> children) {
    this.children = children;
    return this;
  }
}
