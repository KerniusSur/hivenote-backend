package app.hivenote.account.entity;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "role")
@Table(name = "role")
public class RoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  @Enumerated(EnumType.STRING)
  private Role name;

  public UUID getId() {
    return id;
  }

  public RoleEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public Role getName() {
    return name;
  }

  public RoleEntity setName(Role name) {
    this.name = name;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RoleEntity that = (RoleEntity) o;
    return getId().equals(that.getId()) && getName() == that.getName();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName());
  }

  @Override
  public String toString() {
    return "RoleEntity{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
