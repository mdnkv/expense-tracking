package dev.mednikov.expensetracking.authentication.services;

import dev.mednikov.expensetracking.authentication.dto.LoginRequestDto;
import dev.mednikov.expensetracking.authentication.dto.LoginResponseDto;
import dev.mednikov.expensetracking.authentication.exceptions.BadCredentialsException;
import dev.mednikov.expensetracking.authentication.models.AuthenticatedUser;
import dev.mednikov.expensetracking.authentication.models.AuthenticationToken;
import dev.mednikov.expensetracking.authentication.repositories.AuthenticationTokenRepository;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock private AuthenticationTokenRepository authenticationTokenRepository;
    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private AuthServiceImpl authService;

    @Test
    void login_invalidUserEmailTest(){
        String email = "fj7d4yqz40hm4@googlemail.com";

        LoginRequestDto payload = new LoginRequestDto();
        payload.setEmail(email);
        payload.setPassword("password");

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(()->authService.login(payload)).isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void login_invalidPasswordTest(){
        String email = "24o8o1m5q7h2lr20df6q@comcast.net";
        String password = "secret";

        Long userId = 1L;
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setId(userId);

        LoginRequestDto payload = new LoginRequestDto();
        payload.setEmail(email);
        payload.setPassword(password);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        Assertions.assertThatThrownBy(()->authService.login(payload)).isInstanceOf(BadCredentialsException.class);

    }

    @Test
    void login_successTest(){
        String email = "celik.bass@rediffmail.com";
        String password = "password";

        Long userId = 1L;
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setId(userId);

        LoginRequestDto payload = new LoginRequestDto();
        payload.setEmail(email);
        payload.setPassword(password);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        LoginResponseDto result = authService.login(payload);
        Assertions.assertThat(result)
                .isNotNull()
                .hasFieldOrProperty("token")
                .hasFieldOrPropertyWithValue("id", userId.toString());
    }

    @Test
    void authorize_invalidTokenTest(){
        String credentials = "token";
        Mockito.when(authenticationTokenRepository.findByToken(credentials)).thenReturn(Optional.empty());
        Optional<AuthenticatedUser> result = authService.authorize(credentials);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void authorize_successTest(){
        String credentials = "token";
        Long userId = 1L;

        User user = new User();
        user.setEmail("6arl8lxgn9@hotmail.com");
        user.setId(userId);

        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setToken(credentials);
        authenticationToken.setUser(user);
        authenticationToken.setExpiresAt(LocalDateTime.now().plusMinutes(10));

        Mockito.when(authenticationTokenRepository.findByToken(credentials)).thenReturn(Optional.of(authenticationToken));
        Optional<AuthenticatedUser> result = authService.authorize(credentials);
        Assertions.assertThat(result).isPresent();
    }

}
