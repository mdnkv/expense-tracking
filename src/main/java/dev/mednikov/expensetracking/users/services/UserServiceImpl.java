package dev.mednikov.expensetracking.users.services;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import dev.mednikov.expensetracking.users.dto.*;
import dev.mednikov.expensetracking.users.events.NewUserCreatedEvent;
import dev.mednikov.expensetracking.users.exceptions.UserAlreadyExistsException;
import dev.mednikov.expensetracking.users.exceptions.UserNotFoundException;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final static UserDtoMapper mapper = new UserDtoMapper();
    private final static SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public CreateUserResponseDto createUser(CreateUserRequestDto request) {
        String email = request.getEmail();
        if (this.userRepository.findByEmail(email).isPresent()){
            // user with this email does already exist
            throw new UserAlreadyExistsException(email);
        }
        // hash password
        String password = this.passwordEncoder.encode(request.getPassword());

        // check if the user is superuser
        // if the user is 1st created, then the user becomes a superuser by default
        boolean superuser = this.userRepository.count() == 0;

        // create user entity
        User user = new User.UserBuilder()
                .withId(snowflakeGenerator.next())
                .withEmail(email)
                .withPassword(password)
                .withSuperuser(superuser)
                .withFirstName(request.getFirstName())
                .withLastName(request.getLastName())
                .build();

        // save user into db
        User result = this.userRepository.save(user);

        // Publish new user created event
        NewUserCreatedEvent event = new NewUserCreatedEvent(this, result);
        this.eventPublisher.publishEvent(event);

        // return result
        return new CreateUserResponseDto(result.getId().toString());
    }

    @Override
    public Optional<UserDto> getUser(Long userId) {
        return this.userRepository.findById(userId).map(mapper);
    }

    @Override
    public UserDto updateUser(UserDto request) {
        // get the user from db or throw an exception if user does not exist
        Long userId = Long.parseLong(request.getId());
        User user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        // update user
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        // save user into db
        User result = this.userRepository.save(user);

        // return result
        return mapper.apply(result);
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Transactional
    @Override
    public void changePassword(ChangePasswordRequestDto request) {
        // hash password before saving
        String password = this.passwordEncoder.encode(request.getPassword());
        Long userId = Long.parseLong(request.getId());
        this.userRepository.updatePassword(userId, password);
    }
}
