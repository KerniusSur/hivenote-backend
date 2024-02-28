package app.hivenote.auth.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AuthenticatedProfile implements Authentication {
  private UUID id;
  private boolean authenticated;
  private List<String> authorityList = new ArrayList<>();

  public AuthenticatedProfile() {}

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorityList.stream().map((SimpleGrantedAuthority::new)).collect(Collectors.toList());
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getDetails() {
    return this.id;
  }

  @Override
  public Object getPrincipal() {
    return this.id;
  }

  @Override
  public boolean isAuthenticated() {
    return this.authenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.authenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return null;
  }

  public UUID getId() {
    return id;
  }

  public AuthenticatedProfile setId(UUID userId) {
    this.id = userId;
    return this;
  }

  public AuthenticatedProfile setAuthorityList(List<String> authorityList) {
    this.authorityList = authorityList;
    return this;
  }
}
