package app.hivenote.comment.entity;

import app.hivenote.account.entity.AccountEntity;
import app.hivenote.note.entity.NoteEntity;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "comment")
public class CommentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String body;
  private Integer noteLine;
  private Boolean isResolved;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", referencedColumnName = "id")
  private AccountEntity account;

  @ManyToOne
  @JoinColumn(name = "note_id", referencedColumnName = "id")
  private NoteEntity note;

  @ManyToOne
  @JoinColumn(name = "parent_id", referencedColumnName = "id")
  private CommentEntity parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CommentEntity> children;

  public UUID getId() {
    return id;
  }

  public CommentEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getBody() {
    return body;
  }

  public CommentEntity setBody(String body) {
    this.body = body;
    return this;
  }

  public Integer getNoteLine() {
    return noteLine;
  }

  public CommentEntity setNoteLine(Integer noteLine) {
    this.noteLine = noteLine;
    return this;
  }

  public Boolean getIsResolved() {
    return this.isResolved;
  }

  public CommentEntity setIsResolved(Boolean resolved) {
    this.isResolved = resolved;
    return this;
  }

  public AccountEntity getAccount() {
    return account;
  }

  public CommentEntity setAccount(AccountEntity account) {
    this.account = account;
    return this;
  }

  public NoteEntity getNote() {
    return note;
  }

  public CommentEntity setNote(NoteEntity note) {
    this.note = note;
    return this;
  }

  public CommentEntity getParent() {
    return parent;
  }

  public CommentEntity setParent(CommentEntity parent) {
    this.parent = parent;
    return this;
  }

  public List<CommentEntity> getChildren() {
    return children;
  }

  public CommentEntity setChildren(List<CommentEntity> children) {
    this.children = children;
    return this;
  }
}
