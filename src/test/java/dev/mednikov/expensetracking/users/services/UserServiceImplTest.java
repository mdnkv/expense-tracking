package dev.mednikov.expensetracking.users.services;

import com.github.javafaker.Faker;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private final static Faker faker = new Faker();

    @Mock private PasswordEncoder passwordEncoder;
    @Mock private UserRepository userRepository;
    @InjectMocks private UserServiceImpl userService;

    @Test
    void createUser_alreadyExistsTest(){
        String email = faker.internet().emailAddress();
        User user = new User.UserBuilder().withEmail(email).build();

        CreateUserRequestDto request = new CreateUserRequestDto(
                email, faker.internet().password(), faker.name().firstName(), faker.name().lastName()
        );

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Assertions.assertThatThrownBy(() -> userService.createUser(request)).isInstanceOf(UserAlreadyExistsException.class);

    }

    @Test
    void createUser_successTest(){
        Long userId = faker.number().randomNumber();
        String email = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String password = faker.internet().password();
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
        Assertions.assertThat(result.id()).isEqualTo(userId);

    }

    @Test
    void updateUser_doesNotExistTest(){
        Long userId = faker.number().randomNumber();
        String email = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        UserDto request = new UserDto(userId, email, firstName, lastName);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.updateUser(request)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void updateUser_successTest(){
        Long userId = faker.number().randomNumber();
        String email = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        User user = new User.UserBuilder()
                .withId(userId)
                .withEmail(email)
                .withFirstName(firstName)
                .withLastName(lastName)
                .build();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDto request = new UserDto(userId, email, firstName, lastName);

        UserDto result = userService.updateUser(request);
        Assertions.assertThat(result)
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("email", email)
                .hasFieldOrPropertyWithValue("firstName", firstName)
                .hasFieldOrPropertyWithValue("lastName", lastName);
    }

    @Test
    void getUser_doesNotExistTest(){
        Long userId = faker.number().randomNumber();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Optional<UserDto> result = userService.getUser(userId);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void getUser_existsTest(){
        Long userId = faker.number().randomNumber();
        String email = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
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
