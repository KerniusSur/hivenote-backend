package app.hivenote.notebook.entity;

import app.hivenote.account.entity.AccountEntity;
import app.hivenote.note.entity.NoteEntity;
import jakarta.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "notebook")
public class NotebookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "account_id", referencedColumnName = "id")
  private AccountEntity account;

  @OneToMany(mappedBy = "notebook", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<NoteEntity> notes;

  public UUID getId() {
    return id;
  }

  public NotebookEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public NotebookEntity setName(String name) {
    this.name = name;
    return this;
  }

  public AccountEntity getAccount() {
    return account;
  }

  public NotebookEntity setAccount(AccountEntity createdBy) {
    this.account = createdBy;
    return this;
  }

  public Set<NoteEntity> getNotes() {
    return notes;
  }

  public NotebookEntity setNotes(Set<NoteEntity> notes) {
    this.notes = notes;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NotebookEntity that = (NotebookEntity) o;
    return getId().equals(that.getId())
        && getName().equals(that.getName())
        && getAccount().equals(that.getAccount())
        && getNotes().equals(that.getNotes());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getAccount(), getNotes());
  }
}
