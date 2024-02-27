package app.hivenote.auth.utils;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class CookieUtil {
  private static final int TWO_DAYS = (int) TimeUnit.SECONDS.convert(2, TimeUnit.DAYS);

  /**
   * This method adds an authorization cookie to the given response.
   *
   * @param value Cookie value.
   * @param response Response, which will contain the authorization cookie.
   */
  public void addAuthorizationCookie(@NotNull String value, HttpServletResponse response) {
    addCookie(AUTHORIZATION, value, response);
  }

  /**
   * This method deletes authorization cookie from the given response.
   *
   * @param response Response, which might contain the authorization cookie.
   */
  public void deleteAuthorizationCookie(HttpServletResponse response) {
    deleteCookies(AUTHORIZATION, response);
  }

  /**
   * This method parses the given request and returns authorization cookie value.
   *
   * @param request Response, which might contain the authorization cookie.
   * @return String or null.
   */
  @Nullable
  public String getAuthorizationCookieValue(HttpServletRequest request) {
    return getCookieValue(AUTHORIZATION, request);
  }

  /**
   * This method adds given name and value cookie to the response.
   *
   * @param name cookie name.
   * @param value cookie value.
   * @param response response, which will contain the cookie.
   */
  private void addCookie(
      @NotNull String name, @NotNull String value, HttpServletResponse response) {
    var cookie = new Cookie(name, value);
    adjustNewCookie(cookie);
    response.addCookie(cookie);
  }

  /**
   * This method parses request and returns the given name cookie value.
   *
   * @param name cookie name.
   * @param request request, which might contain the cookie.
   * @return String or null.
   */
  @Nullable
  private String getCookieValue(@NotNull String name, HttpServletRequest request) {
    if (request.getCookies() == null) {
      return null;
    }
    return Arrays.stream(request.getCookies())
        .filter(c -> c.getName().equals(name))
        .findFirst()
        .map(Cookie::getValue)
        .orElse(null);
  }

  /**
   * This method deletes given name cookie from the response.
   *
   * @param name cookie name.
   * @param response response, which might contain the cookie.
   */
  private void deleteCookies(@NotNull String name, HttpServletResponse response) {
    var cookie = new Cookie(name, null);
    adjustDeleteCookie(cookie);
    response.addCookie(cookie);
  }

  /**
   * This method sets initial parameters for a newly created cookie.
   *
   * @param cookie cookie object.
   */
  protected void adjustNewCookie(Cookie cookie) {
    adjustGenericCookie(cookie);
    cookie.setMaxAge(TWO_DAYS);
  }

  /**
   * This method prepares cookie to be deleted.
   *
   * @param cookie cookie object.
   */
  protected void adjustDeleteCookie(Cookie cookie) {
    adjustGenericCookie(cookie);
    cookie.setMaxAge(0);
  }

  /**
   * This method sets cookie to be http only and its path to "/"
   *
   * @param cookie cookie object.
   */
  protected void adjustGenericCookie(Cookie cookie) {
    cookie.setHttpOnly(true);
    cookie.setPath("/");
  }
}
