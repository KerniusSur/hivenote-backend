package com.hivenote.backend.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.Locale;
import java.util.Objects;

public class EmailPasswordLoginRequest {
  @Email @NotEmpty private String email;
  @NotEmpty private String password;
  private String ipAddress;

  public String getEmail() {
    return email;
  }

  public EmailPasswordLoginRequest setEmail(String email) {
    this.email = email.toLowerCase(Locale.ROOT);
    return this;
  }

  public String getPassword() {
    return password;
  }

  public EmailPasswordLoginRequest setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public EmailPasswordLoginRequest setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EmailPasswordLoginRequest)) return false;
    EmailPasswordLoginRequest that = (EmailPasswordLoginRequest) o;
    return getEmail().equals(that.getEmail())
        && getPassword().equals(that.getPassword())
        && getIpAddress().equals(that.getIpAddress());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getEmail(), getPassword(), getIpAddress());
  }
}
