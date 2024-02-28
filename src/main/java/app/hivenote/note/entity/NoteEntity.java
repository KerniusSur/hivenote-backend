package app.hivenote.note.entity;

import app.hivenote.component.entity.ComponentEntity;
import app.hivenote.notebook.entity.NotebookEntity;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NoteEntity that = (NoteEntity) o;
    return getId().equals(that.getId())
        && getType() == that.getType()
        && getTitle().equals(that.getTitle())
        && getCoverUrl().equals(that.getCoverUrl())
        && getIsArchived().equals(that.getIsArchived())
        && getIsDeleted().equals(that.getIsDeleted())
        && getNotebook().equals(that.getNotebook())
        && getComponents().equals(that.getComponents());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getType(),
        getTitle(),
        getCoverUrl(),
        getIsArchived(),
        getIsDeleted(),
        getNotebook(),
        getComponents());
  }
}
