package app.hivenote.config;

import app.hivenote.auth.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Profile("!test")
@Configuration
public class WebSecurityConfig {
  public static final String[] AUTH_WHITELIST = {
    "/v3/api-docs/**",
    "/swagger-ui/**",
    "/swagger-ui.html",
    "/api/auth/*/login",
    "/api/auth/*/logoff",
    "/api/v1/public/**",
    "/socket.io/**",
    "/socket.io",
    "/error",
  };

  public static final String[] USER_URL_LIST = {"/api/v1/user/**"};
  public static final String[] SPECIALIST_URL_LIST = {"/api/v1/specialist/**"};
  public static final String[] ADMIN_URL_LIST = {"/api/v1/admin/**"};

  private final AuthEntryPointJwt unauthorizedHandler;
  private final JwtAuthFilter jwtAuthFilter;

  public WebSecurityConfig(AuthEntryPointJwt unauthorizedHandler, JwtAuthFilter jwtAuthFilter) {
    this.unauthorizedHandler = unauthorizedHandler;
    this.jwtAuthFilter = jwtAuthFilter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(
            (exceptionHandling) -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(
            (sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // TODO: remove permitAll for production
        .authorizeHttpRequests(
            (authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/**").permitAll())
        .authorizeHttpRequests(
            (authorizeHttpRequests) ->
                authorizeHttpRequests.requestMatchers(AUTH_WHITELIST).permitAll())
        .authorizeHttpRequests(
            (authorizeHttpRequests) ->
                authorizeHttpRequests.requestMatchers(USER_URL_LIST).authenticated())
        .authorizeHttpRequests(
            (authorizeHttpRequests) ->
                authorizeHttpRequests
                    .requestMatchers(SPECIALIST_URL_LIST)
                    .hasAnyAuthority(JwtUtil.SPECIALIST, JwtUtil.ADMIN))
        .authorizeHttpRequests(
            (authorizeHttpRequests) ->
                authorizeHttpRequests
                    .requestMatchers(ADMIN_URL_LIST)
                    .hasAnyAuthority(JwtUtil.ADMIN))
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
