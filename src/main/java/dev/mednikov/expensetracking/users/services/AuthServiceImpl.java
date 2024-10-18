package dev.mednikov.expensetracking.users.services;

import dev.mednikov.expensetracking.users.dto.LoginRequestDto;
import dev.mednikov.expensetracking.users.dto.LoginResponseDto;
import dev.mednikov.expensetracking.users.exceptions.BadCredentialsException;
import dev.mednikov.expensetracking.users.models.AuthenticatedUser;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, TokenService tokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        String email = request.email();
        String password = request.password();
        User user = this.userRepository.findByEmail(email).orElseThrow(BadCredentialsException::new);
        if (!this.passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException();
        }
        String token = this.tokenService.getToken(user);
        Long id = user.getId();
        return new LoginResponseDto(id, token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username)
                .map(AuthenticatedUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
