package app.hivenote.notebook.entity;

import app.hivenote.account.entity.AccountEntity;
import app.hivenote.note.entity.NoteEntity;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "notebook")
public class NotebookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  private Boolean isArchived;
  private Boolean isDeleted;

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

  public Boolean getIsArchived() {
    return isArchived;
  }

  public NotebookEntity setIsArchived(Boolean isArchived) {
    this.isArchived = isArchived;
    return this;
  }

  public Boolean getIsDeleted() {
    return isDeleted;
  }

  public NotebookEntity setIsDeleted(Boolean isDeleted) {
    this.isDeleted = isDeleted;
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
}
