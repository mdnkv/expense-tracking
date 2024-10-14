package dev.mednikov.expensetracking.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String email){
        super(String.format("User with email %s already exists", email));
    }

}
