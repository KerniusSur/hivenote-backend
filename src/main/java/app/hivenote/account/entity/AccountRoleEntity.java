package app.hivenote.account.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity(name = "account_role")
@Table(name = "account_role")
public class AccountRoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", referencedColumnName = "id")
  private AccountEntity account;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  private RoleEntity role;

  public UUID getId() {
    return id;
  }

  public AccountRoleEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public AccountEntity getAccount() {
    return account;
  }

  public AccountRoleEntity setAccount(AccountEntity account) {
    this.account = account;
    return this;
  }

  public RoleEntity getRole() {
    return role;
  }

  public AccountRoleEntity setRole(RoleEntity role) {
    this.role = role;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AccountRoleEntity that)) return false;
    return Objects.equals(getAccount(), that.getAccount())
        && Objects.equals(getRole(), that.getRole());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAccount(), getRole());
  }

  @Override
  public String toString() {
    return "AccountRoleEntity{"
        + "id="
        + id
        + ", accountId="
        + account.getId()
        + ", role="
        + role
        + '}';
  }
}
