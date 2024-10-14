package dev.mednikov.expensetracking.users.services;

import com.password4j.Password;
import dev.mednikov.expensetracking.users.dto.LoginRequestDto;
import dev.mednikov.expensetracking.users.dto.LoginResponseDto;
import dev.mednikov.expensetracking.users.exceptions.BadCredentialsException;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthServiceImpl(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        String email = request.email();
        String password = request.password();
        User user = this.userRepository.findByEmail(email).orElseThrow(BadCredentialsException::new);
        if (!Password.check(password, user.getPassword()).withScrypt()){
            throw new BadCredentialsException();
        }
        String token = this.tokenService.getToken(user);
        Long id = user.getId();
        return new LoginResponseDto(id, token);
    }
}
