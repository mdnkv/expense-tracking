package dev.mednikov.expensetracking.users.services;

import com.password4j.Password;

import dev.mednikov.expensetracking.users.dto.*;
import dev.mednikov.expensetracking.users.exceptions.UserAlreadyExistsException;
import dev.mednikov.expensetracking.users.exceptions.UserNotFoundException;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final static UserDtoMapper mapper = new UserDtoMapper();

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CreateUserResponseDto createUser(CreateUserRequestDto request) {
        String email = request.email();
        if (this.userRepository.findByEmail(email).isPresent()){
            // user with this email does already exist
            throw new UserAlreadyExistsException(email);
        }
        // hash password
        String password = Password.hash(request.password()).addRandomSalt().withScrypt().getResult();

        // check if the user is superuser
        // if the user is 1st created, then the user becomes a superuser by default
        boolean superuser = this.userRepository.count() == 0;

        // create user entity
        User user = new User.UserBuilder()
                .withEmail(email)
                .withPassword(password)
                .withSuperuser(superuser)
                .withFirstName(request.firstName())
                .withLastName(request.lastName())
                .build();

        // save user into db
        User result = this.userRepository.save(user);

        // return result
        return new CreateUserResponseDto(result.getId());
    }

    @Override
    public Optional<UserDto> getUser(Long userId) {
        return this.userRepository.findById(userId).map(mapper);
    }

    @Override
    public UserDto updateUser(UserDto request) {
        // get the user from db or throw an exception if user does not exist
        User user = this.userRepository.findById(request.id()).orElseThrow(UserNotFoundException::new);

        // update user
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());

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
        String password = Password.hash(request.password()).addRandomSalt().withScrypt().getResult();

        this.userRepository.updatePassword(request.id(), password);
    }
}
