package app.hivenote.auth;

import app.hivenote.account.AccountService;
import app.hivenote.auth.dto.request.EmailPasswordLoginRequest;
import app.hivenote.auth.utils.CookieUtil;
import app.hivenote.auth.utils.JwtUtil;
import app.hivenote.validation.ValidationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final AccountService accountService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final PasswordEncoder encoder;
    private final ValidationService validationService;


    public AuthService(AccountService accountService, JwtUtil jwtUtil, CookieUtil cookieUtil, PasswordEncoder encoder, ValidationService validationService) {
        this.accountService = accountService;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
        this.encoder = encoder;
        this.validationService = validationService;

    }

    public void login(EmailPasswordLoginRequest request, HttpServletResponse response) {
    }

}
