package app.hivenote.note.entity;

import app.hivenote.comment.entity.CommentEntity;
import app.hivenote.component.entity.ComponentEntity;
import app.hivenote.notebook.entity.NotebookEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "note")
public class NoteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Enumerated(EnumType.STRING)
  private NoteType type;

  private String title;
  private String coverUrl;
  private Boolean isArchived;
  private Boolean isDeleted;

  @ManyToOne
  @JoinColumn(name = "notebook_id", referencedColumnName = "id")
  private NotebookEntity notebook;

  @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ComponentEntity> components;

  @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<NoteAccessEntity> accountAccess = new ArrayList<>();

  @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CommentEntity> comments;

  public UUID getId() {
    return id;
  }

  public NoteEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public NoteType getType() {
    return type;
  }

  public NoteEntity setType(NoteType type) {
    this.type = type;
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

  public NotebookEntity getNotebook() {
    return notebook;
  }

  public NoteEntity setNotebook(NotebookEntity notebook) {
    this.notebook = notebook;
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
}
