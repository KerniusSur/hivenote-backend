package app.hivenote.account.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "account")
@Table(name = "account")
public class AccountEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(unique = true)
  private String email;

  private String name;
  private String lastName;
  private String password;
  private String phoneNumber;
  private Boolean isActive = false;
  private Boolean isEmailConfirmed = false;
  private ZonedDateTime lastLogin;

  @Column(unique = true)
  private String passwordResetToken;

  private LocalDateTime passwordResetTokenExpirationDate;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", orphanRemoval = true)
  private List<AccountRoleEntity> roles = new ArrayList<>();

  public UUID getId() {
    return id;
  }

  public AccountEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public AccountEntity setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getName() {
    return name;
  }

  public AccountEntity setName(String name) {
    this.name = name;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public AccountEntity setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public AccountEntity setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public AccountEntity setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public AccountEntity setIsActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  public Boolean getIsEmailConfirmed() {
    return isEmailConfirmed;
  }

  public AccountEntity setIsEmailConfirmed(Boolean isEmailConfirmed) {
    this.isEmailConfirmed = isEmailConfirmed;
    return this;
  }

  public ZonedDateTime getLastLogin() {
    return lastLogin;
  }

  public AccountEntity setLastLogin(ZonedDateTime lastLogin) {
    this.lastLogin = lastLogin;
    return this;
  }

  public String getPasswordResetToken() {
    return passwordResetToken;
  }

  public AccountEntity setPasswordResetToken(String passwordResetToken) {
    this.passwordResetToken = passwordResetToken;
    return this;
  }

  public LocalDateTime getPasswordResetTokenExpirationDate() {
    return passwordResetTokenExpirationDate;
  }

  public AccountEntity setPasswordResetTokenExpirationDate(
      LocalDateTime passwordResetTokenExpirationDate) {
    this.passwordResetTokenExpirationDate = passwordResetTokenExpirationDate;
    return this;
  }

  public List<AccountRoleEntity> getRoles() {
    return roles;
  }

  public AccountEntity setRoles(List<AccountRoleEntity> roles) {
    this.roles = roles;
    return this;
  }
}
