package app.hivenote.config;

import app.hivenote.auth.entity.AuthenticatedProfile;
import app.hivenote.auth.utils.JwtUtil;
import app.hivenote.exception.ApiException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilter implements Filter {

  private final JwtUtil jwtUtil;

  public JwtAuthFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  public void init(FilterConfig filterConfig) {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String token = jwtUtil.parseJwt((HttpServletRequest) request);
    if (token != null && jwtUtil.isValid(token)) {
      Authentication profile = buildProfile(token);
      SecurityContextHolder.getContext().setAuthentication(profile);
    }
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {}

  private AuthenticatedProfile buildProfile(String token) throws ApiException {
    AuthenticatedProfile profile = new AuthenticatedProfile();
    UUID userId = jwtUtil.getUserId(token);
    profile.setId(userId).setAuthorityList(jwtUtil.getAuthorities(token)).setAuthenticated(true);
    return profile;
  }
}
