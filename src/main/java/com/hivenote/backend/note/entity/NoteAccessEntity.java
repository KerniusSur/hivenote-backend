package com.hivenote.backend.note.entity;

import com.hivenote.backend.account.entity.AccountEntity;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "note_access")
public class NoteAccessEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "note_id", referencedColumnName = "id")
  private NoteEntity note;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", referencedColumnName = "id")
  private AccountEntity account;

  @Enumerated(EnumType.STRING)
  private NoteAccessType accessType;

  public UUID getId() {
    return id;
  }

  public NoteAccessEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public NoteEntity getNote() {
    return note;
  }

  public NoteAccessEntity setNote(NoteEntity note) {
    this.note = note;
    return this;
  }

  public AccountEntity getAccount() {
    return account;
  }

  public NoteAccessEntity setAccount(AccountEntity account) {
    this.account = account;
    return this;
  }

  public NoteAccessType getAccessType() {
    return accessType;
  }

  public NoteAccessEntity setAccessType(NoteAccessType accessType) {
    this.accessType = accessType;
    return this;
  }
}
