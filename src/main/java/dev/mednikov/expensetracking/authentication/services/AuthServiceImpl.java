package dev.mednikov.expensetracking.authentication.services;

import dev.mednikov.expensetracking.authentication.models.AuthenticationToken;
import dev.mednikov.expensetracking.authentication.repositories.AuthenticationTokenRepository;
import dev.mednikov.expensetracking.authentication.utils.AuthTokenUtils;
import dev.mednikov.expensetracking.authentication.dto.LoginRequestDto;
import dev.mednikov.expensetracking.authentication.dto.LoginResponseDto;
import dev.mednikov.expensetracking.authentication.exceptions.BadCredentialsException;
import dev.mednikov.expensetracking.authentication.models.AuthenticatedUser;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationTokenRepository authenticationTokenRepository;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationTokenRepository authenticationTokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationTokenRepository = authenticationTokenRepository;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        String email = request.getEmail();
        String password = request.getPassword();
        User user = this.userRepository.findByEmail(email).orElseThrow(BadCredentialsException::new);
        if (!this.passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException();
        }
        String token = AuthTokenUtils.generateNewToken();
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setToken(token);
        authenticationToken.setUser(user);
        authenticationToken.setExpiresAt(LocalDateTime.now().plusHours(24));
        this.authenticationTokenRepository.save(authenticationToken);

        LoginResponseDto result = new LoginResponseDto();
        result.setToken(token);
        result.setId(user.getId());
        return result;
    }

    @Override
    public Optional<AuthenticatedUser> authorize(String credentials) {
        Optional<AuthenticationToken> result = this.authenticationTokenRepository.findByToken(credentials);
        if (result.isPresent()){
            AuthenticationToken authenticationToken = result.get();
            if (authenticationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
                return Optional.empty();
            }
            User user = authenticationToken.getUser();
            AuthenticatedUser principal = new AuthenticatedUser(user);
            return Optional.of(principal);
        }
        return Optional.empty();
    }

    @Override
    public void logout(AuthenticatedUser principal) {
        Long userId = principal.getId();
        List<AuthenticationToken> tokens = this.authenticationTokenRepository.findAllByUserId(userId);
        if (!tokens.isEmpty()){
            this.authenticationTokenRepository.deleteAll(tokens);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username)
                .map(AuthenticatedUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
