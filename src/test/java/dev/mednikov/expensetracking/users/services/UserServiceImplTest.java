package dev.mednikov.expensetracking.users.services;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import dev.mednikov.expensetracking.users.dto.CreateUserRequestDto;
import dev.mednikov.expensetracking.users.dto.CreateUserResponseDto;
import dev.mednikov.expensetracking.users.dto.UserDto;
import dev.mednikov.expensetracking.users.exceptions.UserAlreadyExistsException;
import dev.mednikov.expensetracking.users.exceptions.UserNotFoundException;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private final static SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();

    @Mock private PasswordEncoder passwordEncoder;
    @Mock private UserRepository userRepository;
    @Mock private ApplicationEventPublisher eventPublisher;
    @InjectMocks private UserServiceImpl userService;

    @Test
    void createUser_alreadyExistsTest(){
        String email = "11x54x6v4ksllg14g1@ymail.com";
        User user = new User.UserBuilder().withEmail(email).build();

        CreateUserRequestDto request = new CreateUserRequestDto(
                email, "secret", "Sophie","Hofmann-Stutz"
        );

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Assertions.assertThatThrownBy(() -> userService.createUser(request)).isInstanceOf(UserAlreadyExistsException.class);

    }

    @Test
    void createUser_successTest(){
        Long userId = snowflakeGenerator.next();
        String email = "cwnbookyuuytsdc85by@ymail.com";
        String firstName = "Theresia";
        String lastName = "Kuster";
        String password = "secret";
        User user = new User.UserBuilder()
                .withId(userId)
                .withEmail(email)
                .withFirstName(firstName)
                .withLastName(lastName)
                .build();

        Mockito.when(passwordEncoder.encode(password)).thenReturn(password);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        CreateUserRequestDto request = new CreateUserRequestDto(email, password, firstName, lastName);
        CreateUserResponseDto result = userService.createUser(request);
        Assertions.assertThat(result).isNotNull();

    }

    @Test
    void updateUser_doesNotExistTest(){
        Long userId =  snowflakeGenerator.next();
        String email = "3fhmrmopo4mosckwz@googlemail.com";
        String firstName = "Leonie";
        String lastName = "Bürki";

        UserDto request = new UserDto(userId.toString(), email, firstName, lastName);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.updateUser(request)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void updateUser_successTest(){
        Long userId =  snowflakeGenerator.next();
        String email = "ab5ihd7pptj@hotmail.com";
        String firstName = "Selina";
        String lastName = "Mathys";
        User user = new User.UserBuilder()
                .withId(userId)
                .withEmail(email)
                .withFirstName(firstName)
                .withLastName(lastName)
                .build();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDto request = new UserDto(userId.toString(), email, firstName, lastName);

        UserDto result = userService.updateUser(request);
        Assertions.assertThat(result)
                .hasFieldOrPropertyWithValue("email", email)
                .hasFieldOrPropertyWithValue("firstName", firstName)
                .hasFieldOrPropertyWithValue("lastName", lastName);
    }

    @Test
    void getUser_doesNotExistTest(){
        Long userId = snowflakeGenerator.next();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Optional<UserDto> result = userService.getUser(userId);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void getUser_existsTest(){
        Long userId = snowflakeGenerator.next();
        String email = "k53v3u1lr0q10@msn.com";
        String firstName = "Julia";
        String lastName = "Wirth-Tanner";
        User user = new User.UserBuilder()
                .withId(userId)
                .withEmail(email)
                .withFirstName(firstName)
                .withLastName(lastName)
                .build();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<UserDto> result = userService.getUser(userId);
        Assertions.assertThat(result).isPresent();
    }

}
