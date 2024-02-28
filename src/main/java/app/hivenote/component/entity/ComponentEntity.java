package app.hivenote.component.entity;

import app.hivenote.note.entity.NoteEntity;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "component")
public class ComponentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Enumerated(EnumType.STRING)
  private ComponentType type;

  @JdbcTypeCode(SqlTypes.JSON)
  private ComponentProperties properties;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id", referencedColumnName = "id")
  private ComponentEntity parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ComponentEntity> children;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "note_id", referencedColumnName = "id")
  private NoteEntity note;

  public UUID getId() {
    return id;
  }

  public ComponentEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public ComponentType getType() {
    return type;
  }

  public ComponentEntity setType(ComponentType type) {
    this.type = type;
    return this;
  }

  public ComponentProperties getProperties() {
    return properties;
  }

  public ComponentEntity setProperties(ComponentProperties properties) {
    this.properties = properties;
    return this;
  }

  public ComponentEntity getParent() {
    return parent;
  }

  public ComponentEntity setParent(ComponentEntity parent) {
    this.parent = parent;
    return this;
  }

  public List<ComponentEntity> getChildren() {
    return children;
  }

  public ComponentEntity setChildren(List<ComponentEntity> children) {
    this.children = children;
    return this;
  }

  public NoteEntity getNote() {
    return note;
  }

  public ComponentEntity setNote(NoteEntity note) {
    this.note = note;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ComponentEntity that = (ComponentEntity) o;
    return getId().equals(that.getId())
        && getType() == that.getType()
        && getProperties().equals(that.getProperties())
        && getParent().equals(that.getParent())
        && getChildren().equals(that.getChildren())
        && getNote().equals(that.getNote());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getType(), getProperties(), getParent(), getChildren(), getNote());
  }
}
