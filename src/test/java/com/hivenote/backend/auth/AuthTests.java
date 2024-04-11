package com.hivenote.backend.auth;

import com.hivenote.backend.account.AccountService;
import com.hivenote.backend.account.RoleService;
import com.hivenote.backend.account.entity.AccountEntity;
import com.hivenote.backend.auth.utils.CookieUtil;
import com.hivenote.backend.auth.utils.JwtUtil;
import com.hivenote.backend.validation.ValidationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthTests {

    public static final String AUTHORIZATION_CODE = "somecode";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    public static final int ONE_HOUR = 60 * 60;
    public static final String USER_EXAMPLE_COM = "user@example.com";
    public static final String SUPER_SECRET_PASSWORD = "123456123¶§ﬁ%7878876/.;'[P678";

    @InjectMocks
    AuthService authService;

    @Mock
    AccountService accountService;

    @Mock
    JwtUtil jwtUtil;

    @Mock
    CookieUtil cookieUtil;

    @Mock
    RoleService roleService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    ValidationService validationService;

    @Captor
    ArgumentCaptor<AccountEntity> accountEntityCaptor = ArgumentCaptor.forClass(AccountEntity.class);
}
